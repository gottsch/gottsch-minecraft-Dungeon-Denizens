/*
 * This file is part of  Dungeon Denizens.
 * Copyright (c) 2024 Mark Gottschling (gottsch)
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

import com.someguyssoftware.ddenizens.setup.Registration;
import mod.gottsch.forge.gottschcore.random.RandomHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.animal.Turtle;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.Random;

/**
 *
 * @author Mark Gottschling on Feb 1, 2024
 *
 */
public class WingedSkeleton extends DenizensFlyingMonster implements RangedAttackMob {
    private MonsterSize size;

    public WingedSkeleton(EntityType<? extends FlyingMob> entityType, Level level) {
        super(entityType, level, MonsterSize.MEDIUM);
        this.moveControl = new WingedSkeletonMoveControl(this);
    }

    // TODO add all the same stuff from AbstractSkeleton, except any pathfinding code
    protected void registerGoals() {
        this.goalSelector.addGoal(2, new FlyToMeleeRangeGoal(this, 1));
        this.goalSelector.addGoal(2, new FlyToBowRangeGoal(this, 1, 15));
        this.goalSelector.addGoal(2, new FlyingMeleeAttackGoal(this));
        this.goalSelector.addGoal(2, new FlyingBowAttackGoal(this, 40, 20));
        this.goalSelector.addGoal(5, new WingedSkeletonRandomFloatAroundGoal(this, 2));
        // TODO revisit FleeSunGoal
        this.goalSelector.addGoal(7, new WingedSkeletonLookGoal(this));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));

        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, Turtle.class, 10, true, false, Turtle.BABY_ON_LAND_SELECTOR));

    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.MOVEMENT_SPEED, 0.25D);
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, SpawnGroupData groupData, CompoundTag tag) {

        groupData = super.finalizeSpawn(level, difficulty, spawnType, groupData, tag);

        RandomSource randomSource = level.getRandom();
        this.populateDefaultEquipmentSlots(randomSource, difficulty);

        return groupData;
    }

    protected void populateDefaultEquipmentSlots(RandomSource randomSource, DifficultyInstance difficultyInstance) {
        this.handDropChances[EquipmentSlot.MAINHAND.getIndex()] = 0.25F;
        if (RandomHelper.checkProbability(randomSource, 55)) {
            this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.BOW));
        }
        else {
            this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.WOODEN_SWORD));
        }
    }

    @Override
    public void performRangedAttack(LivingEntity p_32141_, float p_32142_) {
        ItemStack itemstack = this.getProjectile(this.getItemInHand(ProjectileUtil.getWeaponHoldingHand(this, item -> item instanceof net.minecraft.world.item.BowItem)));
        AbstractArrow abstractarrow = this.getArrow(itemstack, p_32142_);
        if (this.getMainHandItem().getItem() instanceof net.minecraft.world.item.BowItem)
            abstractarrow = ((net.minecraft.world.item.BowItem)this.getMainHandItem().getItem()).customArrow(abstractarrow);
        double d0 = p_32141_.getX() - this.getX();
        double d1 = p_32141_.getY(0.3333333333333333D) - abstractarrow.getY();
        double d2 = p_32141_.getZ() - this.getZ();
        double d3 = Math.sqrt(d0 * d0 + d2 * d2);
        abstractarrow.shoot(d0, d1 + d3 * (double)0.2F, d2, 1.6F, (float)(14 - this.level().getDifficulty().getId() * 4));
        this.playSound(SoundEvents.SKELETON_SHOOT, 1.0F, 1.0F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
        this.level().addFreshEntity(abstractarrow);
    }

    protected AbstractArrow getArrow(ItemStack p_32156_, float p_32157_) {
        return ProjectileUtil.getMobArrow(this, p_32156_, p_32157_);
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

//    @Override
//    protected SoundEvent getAmbientSound() {
//        return SoundEvents.SKELETON_AMBIENT;
//    }

    @Override
    public int getAmbientSoundInterval() {
        return 30;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return Registration.WINGED_SKELETON_FLAP.get();
    }

    protected SoundEvent getHurtSound(DamageSource p_33579_) {
        return SoundEvents.SKELETON_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.SKELETON_DEATH;
    }

    protected SoundEvent getStepSound() {
        return null;
    }

    @Override
    public @NotNull MobType getMobType() {
        return MobType.UNDEAD;
    }


    @Override
    public void aiStep() {
        boolean flag = this.isSunBurnTick();
        // NOTE winged skeletons can't wear armor so there isn't a check here for helmet
        if (flag) {
            this.setSecondsOnFire(8);
        }
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
    static class WingedSkeletonRandomFloatAroundGoal extends Goal {
        private final WingedSkeleton skeleton;
        // relative float height above ground in blocks
        private final int maxFloatHeight;
        private Random random = new Random();

        public WingedSkeletonRandomFloatAroundGoal(WingedSkeleton skeleton, int maxFloatHeight) {
            this.skeleton = skeleton;
            this.maxFloatHeight = maxFloatHeight;
            this.setFlags(EnumSet.of(Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            MoveControl moveControl = this.skeleton.getMoveControl();
            if (!moveControl.hasWanted()) {
                return true;
            } else {
                double deltaX = moveControl.getWantedX() - this.skeleton.getX();
                double deltaY = moveControl.getWantedY() - this.skeleton.getY();
                double detlaZ = moveControl.getWantedZ() - this.skeleton.getZ();
                double delta = deltaX * deltaX + deltaY * deltaY + detlaZ * detlaZ;
                return delta < 1.0D || delta > 3600.0D;
            }
        }

        public boolean canContinueToUse() {
            return false;
        }

        public void start() {
            // loop this for 20 times
            double destX = this.skeleton.getX() + (double)((random.nextFloat() * 2.0F - 1.0F) * 8.0F);
            double destY = this.skeleton.getY() + (double)((random.nextFloat() * 2.0F - 1.0F) * Math.min(maxFloatHeight, 6.0));
            double destZ = this.skeleton.getZ() + (double)((random.nextFloat() * 2.0F - 1.0F) * 8.0F);

            BlockPos pos = new BlockPos((int)Math.floor(destX), (int)Math.floor(destY), (int)Math.floor(destZ));

            if (this.skeleton.level().isFluidAtPosition(pos, (fluidState) -> {
                return fluidState.isSourceOfType(Fluids.WATER) || fluidState.isSourceOfType(Fluids.LAVA);
            })) {
                return;
            };

            // NOTE can't use level().getHeight() as that won't work underground.
            // find ground below mob; isAir() also covers cave/void air, and the
            // minBuildHeight guard bounds the loop over a void.
            double groundY = destY;
            int minY = skeleton.level().getMinBuildHeight();
            while (groundY > minY && skeleton.level().getBlockState(new BlockPos((int)destX, (int)groundY, (int)destZ)).isAir()) {
                groundY--;
            }
            destY = Math.min(destY, groundY + getMaxFloatHeight());

            this.skeleton.getMoveControl().setWantedPosition(destX, destY, destZ, 1.0D);
        }

        public int getMaxFloatHeight() {
            return maxFloatHeight;
        }
    }

    /*
     * TODO abstract out to Denizens
     */
    static class FlyToMeleeRangeGoal extends Goal {
        private final Mob mob;
        private final double speedModifier;

        public FlyToMeleeRangeGoal(Mob mob, double speedModifier) {
            this.mob = mob;
            this.speedModifier = speedModifier;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE));
        }

        public boolean canUse() {
            LivingEntity livingEntity = this.mob.getTarget();
            if (livingEntity != null && livingEntity.isAlive()
                    && !mob.getItemBySlot(EquipmentSlot.MAINHAND).is(Items.BOW)) {
                return mob.distanceToSqr(livingEntity) > mob.getMeleeAttackRangeSqr(mob.getTarget());
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
                Vec3 vec3 = livingentity.position().add(0, 0.5, 0);
                mob.getMoveControl().setWantedPosition(vec3.x, vec3.y, vec3.z, this.speedModifier);
            }
        }

        public void stop() {
            super.stop();
        }
    }

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
            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
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
                    this.mob.doHurtTarget(mob.getTarget());
                    this.cooldownCount = 0;
                }
            }
        }
    }

    /**
     *
     */
    static class FlyingBowAttackGoal extends Goal {
        private static final int DEFAULT_COOLDOWN_TIME = 20;
        private DenizensFlyingMonster mob;
        private int cooldownCount;
        @Deprecated
        private final int cooldownTime;
        private int attackIntervalMin;
        private final float attackRadiusSqr;
        private int attackTime = -1;
        private int seeTime;

        public FlyingBowAttackGoal(DenizensFlyingMonster mob, float attackRadius) {
            this(mob, DEFAULT_COOLDOWN_TIME, attackRadius);
        }

        public FlyingBowAttackGoal(DenizensFlyingMonster mob, int cooldownTime, float attackRadius) {
            this.mob = mob;
            this.cooldownTime = cooldownTime;
            this.attackIntervalMin = cooldownTime;
            this.attackRadiusSqr = attackRadius * attackRadius;
        }

        @Override
        public boolean canUse() {
            return mob.getTarget() != null && isHoldingBow();
        }

        @Override
        public void start() {
            // NOTE since cooldownCount counts up (instead of down),
            // set to the threshold so the bite can be performed right away
            // on first use.
            this.mob.setAggressive(true);
            this.cooldownCount = cooldownTime ;
        }

        @Override
        public void stop() {
            LivingEntity livingentity = this.mob.getTarget();
            if (!EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(livingentity)) {
                this.mob.setTarget((LivingEntity)null);
            }
            this.seeTime = 0;
            this.attackTime = -1;
            this.mob.setAggressive(false);
            this.mob.stopUsingItem();
        }

        @Override
        public boolean requiresUpdateEveryTick() {
            return true;
        }

        @Override
        public void tick() {

            LivingEntity livingentity = this.mob.getTarget();
            if (livingentity != null) {
                double d0 = this.mob.distanceToSqr(livingentity.getX(), livingentity.getY(), livingentity.getZ());
                boolean flag = this.mob.getSensing().hasLineOfSight(livingentity);
                boolean flag1 = this.seeTime > 0;
                if (flag != flag1) {
                    this.seeTime = 0;
                }

                if (flag) {
                    ++this.seeTime;
                } else {
                    --this.seeTime;
                }

                // look at target
                this.mob.getLookControl().setLookAt(livingentity, 30.0F, 30.0F);

                if (this.mob.isUsingItem()) {
                    if (!flag && this.seeTime < -60) {
                        this.mob.stopUsingItem();
                    } else if (flag) {
                        int i = this.mob.getTicksUsingItem();
                        if (i >= 20) {
                            this.mob.stopUsingItem();
                            ((RangedAttackMob)this.mob).performRangedAttack(livingentity, BowItem.getPowerForTime(i));
                            this.attackTime = this.attackIntervalMin;
                        }
                    }
                } else if (--this.attackTime <= 0 && this.seeTime >= -60) {
                    this.mob.startUsingItem(ProjectileUtil.getWeaponHoldingHand(this.mob, item -> item instanceof BowItem));
                }
            }

        }

        protected boolean isHoldingBow() {
            return this.mob.isHolding(is -> is.getItem() instanceof BowItem);
        }
    }

    /**
     *
     */
    // TODO abstract to DenizensFlyingMonster
    static class WingedSkeletonLookGoal extends Goal {
        private final DenizensFlyingMonster monster;

        public WingedSkeletonLookGoal(DenizensFlyingMonster monster) {
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
    static class WingedSkeletonMoveControl extends MoveControl {
        private final WingedSkeleton skeleton;
        private int floatDuration;

        public WingedSkeletonMoveControl(WingedSkeleton skeleton) {

            super(skeleton);
            this.skeleton = skeleton;
        }

        public void tick() {
            if (operation == Operation.MOVE_TO) {
                if (floatDuration-- <= 0) {
                    floatDuration += skeleton.getRandom().nextInt(5) + 2;
                    Vec3 vec3 = new Vec3(wantedX - skeleton.getX(), wantedY - skeleton.getY(), wantedZ - skeleton.getZ());
                    double distance = vec3.length();
                    vec3 = vec3.normalize();
                    if (canReach(vec3, Mth.ceil(distance))) {
                        Vec3 delta = skeleton.getDeltaMovement().add(vec3.scale(0.1D));
                        skeleton.setDeltaMovement(delta);
                    } else {
                        operation = Operation.WAIT;
                    }
                }
            }
        }

        private boolean canReach(Vec3 vec3, int distance) {
            AABB aabb = skeleton.getBoundingBox();

            for(int i = 1; i < distance; ++i) {
                aabb = aabb.move(vec3);
                if (!skeleton.level().noCollision(skeleton, aabb)) {
                    return false;
                }
            }
            return true;
        }
    }
}
