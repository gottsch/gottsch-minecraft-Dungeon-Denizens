package com.someguyssoftware.ddenizens.entity.monster;

import com.someguyssoftware.ddenizens.entity.ai.control.FlyingGottschMonsterMoveControl;
import com.someguyssoftware.ddenizens.entity.ai.goal.volant.VolantFlyAction;
import com.someguyssoftware.ddenizens.entity.ai.goal.volant.VolantMovement;
import com.someguyssoftware.ddenizens.serializer.data.ModDataSerializers;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;

/**
 * Base for a ground-walking humanoid that can launch into flight. Owns the dual
 * locomotion setup: walking ({@link MoveControl} + {@link GroundPathNavigation} +
 * gravity) and flying ({@link FlyingGottschMonsterMoveControl} +
 * {@link FlyingPathNavigation} + no gravity). The active set is swapped as a unit
 * in {@link #setMovementState(VolantMovement)}.
 *
 * TODO lift to "gottsch's Monster Manual" as VolantEntity.
 * @author by Mark Gottschling on 7/16/2025
 */
public class WingedHumanoid extends DenizensMonster implements IWingedHumanoid {
    /** default airborne ceiling (blocks above launch position) for a true flyer. */
    public static final double DEFAULT_MAX_FLY_HEIGHT = 6.0D;

    // synched so the client can drive walk vs. flight animation
    private static final EntityDataAccessor<VolantMovement> DATA_MOVEMENT_STATE =
            SynchedEntityData.defineId(WingedHumanoid.class, ModDataSerializers.VOLANT_MOVEMENT_STATE_SERIALIZER.get());

    // locomotion controllers — swapped together as a unit
    protected final MoveControl walkingMoveControl;
    protected final FlyingGottschMonsterMoveControl flyingMoveControl;
    /** ground pathfinder, created by super via {@link #createNavigation(Level)}. */
    protected final PathNavigation groundNavigation;
    /** flying pathfinder, used while airborne. */
    protected final FlyingPathNavigation flyingNavigation;

    /** server-side fine-grained flight phase (launch/fly/land); not synched. */
    protected VolantFlyAction flyAction;
    protected boolean landAttackOnly;

    protected WingedHumanoid(EntityType<? extends Monster> mob, Level level, MonsterSize size) {
        super(mob, level, size);

        // setup move controls
        this.walkingMoveControl = new MoveControl(this);
        this.flyingMoveControl = new FlyingGottschMonsterMoveControl(this);

        // super() already created the ground navigation via createNavigation();
        // keep a handle to it and build the flying counterpart.
        this.groundNavigation = this.navigation;
        this.flyingNavigation = buildFlyingNavigation(level);

        // start grounded
        this.moveControl = this.walkingMoveControl;
        setDataMovementState(VolantMovement.WALK);
        this.flyAction = VolantFlyAction.IDLE;
    }

    /**
     * The default/initial navigation is the ground pathfinder (the mob starts walking).
     */
    @Override
    protected PathNavigation createNavigation(Level level) {
        GroundPathNavigation nav = new GroundPathNavigation(this, level);
        nav.setCanOpenDoors(false);
        nav.setCanFloat(true);
        nav.setCanPassDoors(true);
        return nav;
    }

    protected FlyingPathNavigation buildFlyingNavigation(Level level) {
        FlyingPathNavigation nav = new FlyingPathNavigation(this, level);
        nav.setCanOpenDoors(false);
        nav.setCanFloat(true);
        nav.setCanPassDoors(true);
        return nav;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_MOVEMENT_STATE, VolantMovement.WALK);
    }

    // NOTE flight state is intentionally not persisted: the mob always reloads
    // grounded and re-evaluates whether to launch. This avoids reloading airborne
    // (gravity desync / fall damage) and keeps save data minimal.

    @Override
    public boolean isLaunching() {
        return this.flyAction == VolantFlyAction.LAUNCH;
    }

    public void setIsLaunching(boolean launching) {
        this.flyAction = launching ? VolantFlyAction.LAUNCH : VolantFlyAction.IDLE;
    }

    @Override
    public boolean isLanding() {
        return this.flyAction == VolantFlyAction.LAND;
    }

    public void setIsLanding(boolean landing) {
        this.flyAction = landing ? VolantFlyAction.LAND : VolantFlyAction.IDLE;
    }

    @Override
    public boolean isWalking() {
        return !isFlying();
    }

    @Override
    public boolean isFlying() {
        return getDataMovementState() == VolantMovement.FLY;
    }

    private VolantMovement getDataMovementState() {
        return this.entityData.get(DATA_MOVEMENT_STATE);
    }

    private void setDataMovementState(VolantMovement movement) {
        this.entityData.set(DATA_MOVEMENT_STATE, movement);
    }

    /**
     * Swap the entire locomotion set (move control + navigation + gravity) to match
     * the requested movement state. Server-driven; the synched state propagates to
     * the client for animation.
     */
    @Override
    public void setMovementState(VolantMovement state) {
        if (getDataMovementState() == state) {
            return;
        }
        setDataMovementState(state);

        // abandon any in-progress path before swapping controllers
        this.navigation.stop();

        if (state == VolantMovement.WALK) {
            this.moveControl = this.walkingMoveControl;
            this.navigation = this.groundNavigation;
            this.flyAction = VolantFlyAction.IDLE;
            this.setNoGravity(false);
        } else {
            this.moveControl = this.flyingMoveControl;
            this.navigation = this.flyingNavigation;
            this.setNoGravity(true);
        }
    }

    /** convenience: enter active flight. */
    public void setFlyingMovement() {
        setMovementState(VolantMovement.FLY);
        this.flyAction = VolantFlyAction.FLY;
    }

    /** convenience: return to the ground. */
    public void setWalkingMovement() {
        setMovementState(VolantMovement.WALK);
        this.flyAction = VolantFlyAction.IDLE;
    }

    @Override
    public double getMaxFlyHeight() {
        return DEFAULT_MAX_FLY_HEIGHT;
    }

    /**
     * Drive a controlled descent toward the given ground column using the (active)
     * flying move control at a reduced speed, zeroing fall distance as it nears the
     * ground so the transition back to gravity is damage-free. Returns {@code true}
     * once effectively touched down — the caller should then {@link #setWalkingMovement()}.
     *
     * <p>Velocity damping is delegated to the move control (which runs after goals
     * each tick); forcing delta in the goal would simply be overwritten.
     */
    public boolean tickDescentToward(BlockPos groundPos, double speedModifier) {
        double standY = groundPos.getY() + 1.0D;
        getMoveControl().setWantedPosition(groundPos.getX() + 0.5D, standY, groundPos.getZ() + 0.5D, speedModifier);

        double distanceToGround = position().y - standY;
        if (distanceToGround <= 1.0D) {
            // continuously clear accumulated fall distance through the final approach
            resetFallDistance();
        }
        return onGround() || distanceToGround < 0.3D;
    }

    @Override
    public boolean isLandAttackOnly() {
        return this.landAttackOnly;
    }

    public void setLandAttackOnly(boolean landAttackOnly) {
        this.landAttackOnly = landAttackOnly;
    }
}
