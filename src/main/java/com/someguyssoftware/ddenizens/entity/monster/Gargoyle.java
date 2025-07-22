/*
 * This file is part of  Dungeon Denizens.
 * Copyright (c) 2025 Mark Gottschling (gottsch)
 *
 * All rights reserved.
 *
 * Dungeon Denizens is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Dungeon Denizens is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Dungeon Denizens.  If not, see <http://www.gnu.org/licenses/lgpl>.
 */
package com.someguyssoftware.ddenizens.entity.monster;

import com.someguyssoftware.ddenizens.entity.ai.goal.demonoid.DemonoidFlyToMeleeRangeGoal;
import com.someguyssoftware.ddenizens.entity.ai.goal.demonoid.DemonoidLandGoal;
import com.someguyssoftware.ddenizens.entity.ai.goal.demonoid.DemonoidLaunchGoal;
import com.someguyssoftware.ddenizens.entity.ai.goal.demonoid.DemonoidMeleeAttackGoal;
import com.someguyssoftware.ddenizens.setup.Registration;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.Random;

/**
 * TODO sometimes the Gargoyle launches but then moves away from the player (target)
 * TODO sometimes the Gargoyle is still hurt on landing
 * TODO sometimes after landing the Gargoyle seems to lose target and doesn't engage in melee - not high enough priority?
 * @author Mark Gottschling on July 3, 2025
 *
 */
public class Gargoyle extends DemonoidMonster {
    private MonsterSize size;
    // abstract out to Demonoid ?? will all demonoids flee on hit?
    public boolean shouldFlee = false;

    /*
     *
     */
    public Gargoyle(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level, MonsterSize.MEDIUM);
    }

    // This is the key change: we override createNavigation to provide
    // the flying navigator from the beginning.
    @Override
    protected PathNavigation createNavigation(Level level) {
        FlyingPathNavigation flyingNav = new FlyingPathNavigation(this, level);
        flyingNav.setCanOpenDoors(false);
        flyingNav.setCanFloat(true);
        flyingNav.setCanPassDoors(true);
        return flyingNav;
    }

    protected void registerGoals() {
        super.registerGoals();

        // TODO come up with a more generic name and interface ie Angels, Winged Skeletons Demonoids all walk and fly and may execute this launch goal.
        // TODO but the interface has to inlcude the isFlying(), shouldLand(), landAttackOnly() methods

        this.goalSelector.addGoal(3, new DemonoidLaunchGoal(this, 6, 3, 1.2F));
        this.goalSelector.addGoal(4, new DemonoidFlyToMeleeRangeGoal(this, 6, 1.2));
        this.goalSelector.addGoal(3, new DemonoidLandGoal(this, 6, 0.5F));

        this.goalSelector.addGoal(2, new DemonoidMeleeAttackGoal(this, 6D, 1D, true));

        // add other goals like wandering, looking at the player, etc. with lower priorities
        this.goalSelector.addGoal(1, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));

        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.ATTACK_DAMAGE, 4)
                .add(Attributes.MOVEMENT_SPEED, 0.24D)
                .add(Attributes.ARMOR, 4.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.1);
    }

    @Override
    public MonsterSize getMonsterSize() {
        return size;
    }

    @Override
    public void setMonsterSize(MonsterSize size) {
        this.size = size;
    }

    protected void playStepSound(BlockPos pos, BlockState state) {}

    @Override
    public int getAmbientSoundInterval() {
        return 30;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return Registration.WINGED_SKELETON_FLAP.get();
    }
//
//    protected SoundEvent getHurtSound(DamageSource p_33579_) {
//        return SoundEvents.SKELETON_HURT;
//    }
//
//    protected SoundEvent getDeathSound() {
//        return SoundEvents.SKELETON_DEATH;
//    }

    protected SoundEvent getStepSound() {
        return null;
    }

    @Override
    public @NotNull MobType getMobType() {
        return MobType.UNDEFINED;
    }


    @Override
    public void aiStep() {
        // NOTE need these 2 lines because FlyingMob doesn't call them.
        this.updateSwingTime();
        this.updateNoActionTime();
        super.aiStep();
    }

    // TODO this needs to be abstracted
    protected void updateNoActionTime() {
        float f = this.getLightLevelDependentMagicValue();
        if (f > 0.5F) {
            this.noActionTime += 2;
        }

    }

    protected float getStandingEyeHeight(Pose p_32154_, EntityDimensions p_32155_) {
        return 1.74F;
    }

    /**
     *
     */
    static class RandomFloatAroundGoal extends Goal {
        private final Gargoyle mob;
        // relative float height above ground in blocks
        private final int maxFloatHeight;
        private Random random = new Random();

        public RandomFloatAroundGoal(Gargoyle mob, int maxFloatHeight) {
            this.mob = mob;
            this.maxFloatHeight = maxFloatHeight;
            this.setFlags(EnumSet.of(Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            MoveControl moveControl = this.mob.getMoveControl();
            if (!moveControl.hasWanted()) {
                return true;
            } else {
                double deltaX = moveControl.getWantedX() - this.mob.getX();
                double deltaY = moveControl.getWantedY() - this.mob.getY();
                double detlaZ = moveControl.getWantedZ() - this.mob.getZ();
                double delta = deltaX * deltaX + deltaY * deltaY + detlaZ * detlaZ;
                return delta < 1.0D || delta > 3600.0D;
            }
        }

        public boolean canContinueToUse() {
            return false;
        }

        public void start() {
            // loop this for 20 times
            double destX = this.mob.getX() + (double)((random.nextFloat() * 2.0F - 1.0F) * 8.0F);
            double destY = this.mob.getY() + (double)((random.nextFloat() * 2.0F - 1.0F) * Math.min(maxFloatHeight, 6.0));
            double destZ = this.mob.getZ() + (double)((random.nextFloat() * 2.0F - 1.0F) * 8.0F);

            BlockPos pos = new BlockPos((int)Math.floor(destX), (int)Math.floor(destY), (int)Math.floor(destZ));

            if (this.mob.level().isFluidAtPosition(pos, (fluidState) -> {
                return fluidState.isSourceOfType(Fluids.WATER) || fluidState.isSourceOfType(Fluids.LAVA);
            })) {
                return;
            };

            // NOTE can't use level().getHeight() as that won't work underground
            // find ground below mob
            double groundY = destY;
            while (mob.level().getBlockState(new BlockPos((int)destX, (int)groundY, (int)destZ)).getBlock() == Blocks.AIR) {
                groundY--;
            }
            destY = Math.min(destY, groundY + getMaxFloatHeight());

            this.mob.getMoveControl().setWantedPosition(destX, destY, destZ, 1.0D);
        }

        public int getMaxFloatHeight() {
            return maxFloatHeight;
        }
    }

    /*
     * TODO abstract out to Denizens
     */
//    static class FlyToMeleeRangeGoal extends Goal {
//        // TODO should be value passed into goal
//        private static final double MELEE_DISTANCE_SQ = 36D;
//
//        private final DemonoidMonster mob;
//        private final double speedModifier;
//
//        public FlyToMeleeRangeGoal(DemonoidMonster mob, double speedModifier) {
//            this.mob = mob;
//            this.speedModifier = speedModifier;
//            this.setFlags(EnumSet.of(Flag.MOVE));
//        }
//
//        public boolean canUse() {
//            LivingEntity livingEntity = this.mob.getTarget();
//            if (livingEntity != null && livingEntity.isAlive() && this.mob.isFlying()) {
//                return mob.distanceToSqr(livingEntity) > MELEE_DISTANCE_SQ; // 6 blocks away
//            }
//            return false;
//        }
//
//        public boolean canContinueToUse() {
//            return false;
//        }
//
//        public void start() {
//            LivingEntity livingEntity = this.mob.getTarget();
//            if (livingEntity != null) {
//                // face target
//                this.mob.faceTarget(livingEntity);
////                if (livingEntity.distanceToSqr(this.mob) < 4096.0D) {
////                    double deltaX = livingEntity.getX() - mob.getX();
////                    double deltaY = livingEntity.getZ() - mob.getZ();
////                    mob.setYRot(-((float)Mth.atan2(deltaX, deltaY)) * (180F / (float)Math.PI));
////                    mob.yBodyRot = mob.getYRot();
////                }
//
//                Vec3 vec3 = livingEntity.position().add(0, 0.5, 0);
//                mob.getMoveControl().setWantedPosition(vec3.x, vec3.y, vec3.z, this.speedModifier);
//            }
//        }
//
//        public void stop() {
//            super.stop();
//        }
//    }

    /*
     * TODO abstract out to Denizens
     */
    static class FlyToBowRangeGoal extends Goal {
        private final Mob mob;
        private final double speedModifier;
        private final float attackRadiusSqr;

        public FlyToBowRangeGoal(Mob mob, double speedModifier, float attackRadius) {
            this.mob = mob;
            this.speedModifier = speedModifier;
            this.attackRadiusSqr = attackRadius * attackRadius;
            this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
        }

        public boolean canUse() {
            LivingEntity livingEntity = this.mob.getTarget();
            if (livingEntity != null && livingEntity.isAlive()
                    && mob.getItemBySlot(EquipmentSlot.MAINHAND).is(Items.BOW)) {
                return mob.distanceToSqr(livingEntity) > getBowAttackRangeSqr();
            } else {
                return false;
            }
        }

        public boolean canContinueToUse() {
            return false;
        }

        public void start() {
            LivingEntity livingentity = this.mob.getTarget();
            if (livingentity != null) {
                Vec3 vec3 = livingentity.position().add(0, 1, 0);
                mob.getMoveControl().setWantedPosition(vec3.x, vec3.y, vec3.z, this.speedModifier);
            }
        }

        public void stop() {
            super.stop();
        }

        public float getBowAttackRangeSqr() {
            return attackRadiusSqr;
        }
    }

    /**
     *
     */
    static class FlyingMeleeAttackGoal extends Goal {
        private static final int DEFAULT_COOLDOWN_TIME = 20;
        private DenizensFlyingMonster mob;
        private int cooldownCount;
        private final int cooldownTime;

        public FlyingMeleeAttackGoal(DenizensFlyingMonster mob) {
            this(mob, DEFAULT_COOLDOWN_TIME);
        }

        public FlyingMeleeAttackGoal(DenizensFlyingMonster mob, int cooldownTime) {
            this.mob = mob;
            this.cooldownTime = cooldownTime;
        }

        @Override
        public boolean canUse() {
            return mob.getTarget() != null &&
                    !mob.getItemBySlot(EquipmentSlot.MAINHAND).is(Items.BOW);
        }

        @Override
        public void start() {
            // NOTE since cooldownCount counts up (instead of down),
            // set to the threshold so the bite can be performed right away
            // on first use.
            this.cooldownCount = cooldownTime ;
        }

        @Override
        public void stop() {
            LivingEntity livingentity = this.mob.getTarget();
            if (!EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(livingentity)) {
                this.mob.setTarget((LivingEntity)null);
            }
        }

        @Override
        public boolean requiresUpdateEveryTick() {
            return true;
        }

        @Override
        public void tick() {
            // cooldown regardless of criteria
            cooldownCount = Math.min(++this.cooldownCount, cooldownTime);
            if (cooldownCount >= cooldownTime) {
                if (mob.getMeleeAttackRangeSqr(mob.getTarget()) >= this.mob.distanceToSqr(mob.getTarget())) {
                    this.mob.swing(InteractionHand.MAIN_HAND);
                    MeleeAttackGoal m;
                    this.mob.doHurtTarget(mob.getTarget());
                    this.cooldownCount = 0;
                }
            }
        }
    }

    /**
     *
     */
    // TODO abstract to DenizensFlyingMonster
    static class LookGoal extends Goal {
        private final DenizensMonster monster;

        public LookGoal(DenizensMonster monster) {
            this.monster = monster;
            this.setFlags(EnumSet.of(Flag.LOOK));
        }

        public boolean canUse() {
            return true;
        }

        public boolean requiresUpdateEveryTick() {
            return true;
        }

        @Override
        public void tick() {
            if (monster.getTarget() == null) {
                Vec3 vec3 = monster.getDeltaMovement();
                monster.setYRot(-((float)Mth.atan2(vec3.x, vec3.z)) * (180F / (float)Math.PI));
                monster.yBodyRot = monster.getYRot();
            } else {
                LivingEntity livingEntity = this.monster.getTarget();
                if (livingEntity.distanceToSqr(this.monster) < 4096.0D) {
                    double deltaX = livingEntity.getX() - monster.getX();
                    double deltaY = livingEntity.getZ() - monster.getZ();
                    monster.setYRot(-((float)Mth.atan2(deltaX, deltaY)) * (180F / (float)Math.PI));
                    monster.yBodyRot = monster.getYRot();
                }
            }
        }
    }

    /*
     *
     */
    static class GargoyleMoveControl extends MoveControl {
        private final Gargoyle gargoyle;
        private int floatDuration;

        public GargoyleMoveControl(Gargoyle gargoyle) {

            super(gargoyle);
            this.gargoyle = gargoyle;
        }

        public void tick() {
            if (operation == Operation.MOVE_TO) {
                if (floatDuration-- <= 0) {
                    floatDuration += gargoyle.getRandom().nextInt(5) + 2;
                    Vec3 vec3 = new Vec3(wantedX - gargoyle.getX(), wantedY - gargoyle.getY(), wantedZ - gargoyle.getZ());
                    double distance = vec3.length();
                    vec3 = vec3.normalize();
                    if (canReach(vec3, Mth.ceil(distance))) {
                        Vec3 delta = gargoyle.getDeltaMovement().add(vec3.scale(0.1D));
                        gargoyle.setDeltaMovement(delta);
                    } else {
                        operation = Operation.WAIT;
                    }
                }
            }
        }

        private boolean canReach(Vec3 vec3, int distance) {
            AABB aabb = gargoyle.getBoundingBox();

            for(int i = 1; i < distance; ++i) {
                aabb = aabb.move(vec3);
                if (!gargoyle.level().noCollision(gargoyle, aabb)) {
                    return false;
                }
            }
            return true;
        }
    }
}
