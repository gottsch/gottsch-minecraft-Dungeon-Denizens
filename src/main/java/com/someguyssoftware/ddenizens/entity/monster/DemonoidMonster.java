package com.someguyssoftware.ddenizens.entity.monster;

import com.someguyssoftware.ddenizens.DD;
import com.someguyssoftware.ddenizens.entity.ai.control.FlyingGottschMonsterMoveControl;
import com.someguyssoftware.ddenizens.entity.monster.demonoid.IDemonoidMonster;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;

/**
 * @author by Mark Gottschling on 7/16/2025
 */
public class DemonoidMonster extends DenizensMonster implements IDemonoidMonster {
    protected static final EntityDataAccessor<Boolean> IS_FLYING_DATA = SynchedEntityData.defineId(DemonoidMonster.class, EntityDataSerializers.BOOLEAN);

    protected final MoveControl walkingMoveControl;
    protected final FlyingGottschMonsterMoveControl flyingMoveControl;

    // these two flags do not need to be saved ie can be recalculated when entity is re-spawned.
    protected boolean shouldLand;
    protected boolean landAttackOnly;

    protected DemonoidMonster(EntityType<? extends Monster> mob, Level level, MonsterSize size) {
        super(mob, level, size);
        // setup move controls
        this.walkingMoveControl = new MoveControl(this);
        this.flyingMoveControl = new FlyingGottschMonsterMoveControl(this);
        // set active move control to walking
        this.moveControl = walkingMoveControl;
        // manually set flying = false since the initial move control is walking.
        this.setFlying(false);
    }

    /**
     * Set initial values of synced data
     */
    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(IS_FLYING_DATA, false);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);

        tag.putBoolean(IS_FLYING_TAG, isFlying());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);

        if (tag.contains(IS_FLYING_TAG)) {
            this.setFlying(tag.getBoolean(IS_FLYING_TAG));
        }
    }

    @Override
    public boolean isFlying() {
        return this.entityData.get(IS_FLYING_DATA);
    }

    public void setFlying(boolean isFlying) {
        this.entityData.set(IS_FLYING_DATA, isFlying);
    }

    @Override
    public void setFlyingMode(boolean isFlying) {
        if (isFlying() == isFlying) {
            return; // No change needed
        }

        // update property
        setFlying(isFlying);

        // update move control and gravity
        if (isFlying) {
            DD.LOGGER.info("gargoyle changing to flying move control");
//            this.moveControl = new FlyingMoveControl(this, 10, true);
            this.moveControl = flyingMoveControl;
            this.setNoGravity(true);
        } else {
            DD.LOGGER.info("gargoyle changing to move control");
//            this.moveControl = new MoveControl(this);
            this.moveControl = walkingMoveControl;
            this.setNoGravity(false);
        }
    }

    @Override
    public boolean shouldLand() {
        return this.shouldLand;
    }

    public void setShouldLand(boolean shouldLand) {
        this.shouldLand = shouldLand;
    }

    @Override
    public boolean isLandAttackOnly() {
        return this.landAttackOnly;
    }

    public void setLandAttackOnly(boolean landAttackOnly) {
        this.landAttackOnly = landAttackOnly;
    }
}
