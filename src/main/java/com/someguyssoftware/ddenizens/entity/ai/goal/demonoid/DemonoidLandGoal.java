package com.someguyssoftware.ddenizens.entity.ai.goal.demonoid;

import com.someguyssoftware.ddenizens.DD;
import com.someguyssoftware.ddenizens.entity.monster.DemonoidMonster;
import com.someguyssoftware.ddenizens.entity.monster.IDenizensMonster;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

/**
 * @author by Mark Gottschling on 7/17/2025
 */
public class DemonoidLandGoal extends Goal {

    private final DemonoidMonster mob;
    private Player target;
    private double landDistanceSq;
    private float speedModifier;
//    private Random random = new Random();
    private int landingTicks;

    public DemonoidLandGoal(DemonoidMonster mob, double landDistance, float speedModifier) {
        this.mob = mob;
        this.landDistanceSq = landDistance * landDistance;
        this.speedModifier = speedModifier;
        this.setFlags(EnumSet.of(Flag.MOVE));
    }

    /*
     * executes if:
     * 0] mob is flying
     * AND
     *      1] mob does not have a living target
     * OR 2] mob is flagged to land
     * OR 3] mob is flagged for land attack only
     * OR 4] target is on the ground AND mob is close enough to its target
     */
    @Override
    public boolean canUse() {
//        DD.LOGGER.info("canUse - isflying -> {}", this.mob.isFlying());
        LivingEntity target = this.mob.getTarget();
        if (this.mob.isFlying()) {
            return target == null
                    || this.mob.shouldLand()
                    || this.mob.isLandAttackOnly()
                    || (target.onGround() && this.mob.distanceToSqr(target) <= landDistanceSq);
        }
        return false;
    }

    /**
     * the goal will continue to run as long as the mob is in flying mode.
     */
    @Override
    public boolean canContinueToUse() {
        // keep this goal running as long as the mob is in "flying mode".
        // the goal's purpose is to get it OUT of flying mode safely.
        return this.mob.isFlying();
    }

    @Override
    public void start() {
        this.landingTicks = 0;
    }

    @Override
    public void stop() {
        super.stop();
        this.mob.fallDistance = 0.0F;
        this.mob.setShouldLand(false);
        // explicitly reset fall distance to prevent any lingering damage calculation.

    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    /**
     * Every tick, guide the mob towards the ground and control its speed.
     */
    @Override
    public void tick() {
        // 1. Find the landing spot
        BlockPos groundPos;
        LivingEntity target = this.mob.getTarget();
        if (target != null) {
            groundPos = target.getOnPos();
        } else {
            groundPos = IDenizensMonster.findSafeGroundPos(this.mob.level(), this.mob.getOnPos(), 16);
        }

        // If no safe spot is found, just hover by not moving.
        if (groundPos == null) {
            this.mob.getNavigation().stop();
            return;
        }

        // 2. Guide the mob towards the landing spot using MoveControl
        this.mob.getMoveControl().setWantedPosition(groundPos.getX() + 0.5D, groundPos.getY() + 1.0D, groundPos.getZ() + 0.5D, this.speedModifier);

        // 3. ⭐ The crucial part: Manually control vertical speed when close to the ground
        double distanceToGround = this.mob.position().y - (groundPos.getY() + 1.0D);

        if (distanceToGround < 3.0D && distanceToGround > 0) {
            // when we get close, override the default move controller's velocity.
            // force a gentle, constant downward speed to ensure a soft landing.
            Vec3 currentVelocity = this.mob.getDeltaMovement();
            this.mob.setDeltaMovement(currentVelocity.x, -0.15D, currentVelocity.z);
        }

        // 4. ⭐⭐ the super crucial part: when close to the ground, reset accumulated fall damage.
        if (distanceToGround < 1.0D) {
            mob.resetFallDistance();
        }

        // 5. check if we have landed.
        // use a small tick counter to ensure it's properly on the ground before stopping.
        // NOTE i believe mob.onGround() will always be false here since the mob is still using flying movement control
        if (this.mob.onGround() || distanceToGround < 0.05) {
            this.landingTicks++;
            if (this.landingTicks > 5) { // wait 5 ticks while on the ground to be sure
                this.mob.setFlyingMode(false); // this will cause canContinueToUse() to return false next tick
            }
            return;
        } else {
            this.landingTicks = 0;
        }
    }
}
