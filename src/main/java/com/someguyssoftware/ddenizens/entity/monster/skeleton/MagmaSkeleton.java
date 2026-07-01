
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
package com.someguyssoftware.ddenizens.entity.monster.skeleton;

import com.someguyssoftware.ddenizens.entity.ai.goal.PassiveMeleeAttackGoal;
import com.someguyssoftware.ddenizens.entity.monster.DenizensMonster;
import mod.gottsch.forge.gmm.core.config.MobConfig;
import mod.gottsch.forge.gottschcore.random.RandomHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FleeSunGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.monster.Strider;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.shapes.CollisionContext;

/**
 * @author Mark Gottschling on Feb 4, 2024
 *
 */
public class MagmaSkeleton extends DenizensSkeleton {
    protected Goal passiveMeleeGoal = new PassiveMeleeAttackGoal(this, 20) {
        public void stop() {
            super.stop();
            MagmaSkeleton.this.setAggressive(false);
        }

        public void start() {
            super.start();
            MagmaSkeleton.this.setAggressive(true);
        }
    };

    public MagmaSkeleton(EntityType<? extends DenizensSkeleton>entityType, Level level) {
        super(entityType, level);
        this.reassessWeaponGoal();
    }

    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.removeGoal(getFleeSunGoal());
        this.goalSelector.addGoal(3, new SkeletonFleeSunGoal(this, 1.0D));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return DenizensSkeleton.createAttributes()
                .add(Attributes.MAX_HEALTH, 30D)
                .add(Attributes.ATTACK_DAMAGE, 6.0D);
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty,
                                        MobSpawnType spawnType, SpawnGroupData groupData, CompoundTag tag) {
        this.setLeftHanded(false);
        this.setCanPickUpLoot(false);
        this.populateDefaultEquipmentSlots(level.getRandom(), difficulty);

        return groupData;
    }

    @Override
    public void reassessWeaponGoal() {
        if (this.level() != null && !this.level().isClientSide) {
            this.goalSelector.removeGoal(getMeleeAttackGoal());
            this.goalSelector.removeGoal(this.bowGoal);

            ItemStack itemStack = this.getMainHandItem();
            if (itemStack.is(Items.BOW)) {
                // setup bow goal
                int interval = 20;
                if (this.level().getDifficulty() != Difficulty.HARD) {
                    interval = 40;
                }
                this.bowGoal.setMinAttackInterval(interval);
                this.bowGoal.setPowerFactor(2.0F); // TODO can be config.
                this.goalSelector.addGoal(4, this.bowGoal);

                // check if sword in off hand
                ItemStack offhandStack = this.getOffhandItem();
                if (offhandStack.is(Items.IRON_SWORD)) {
                    this.setMeleeAttackGoal(this.passiveMeleeGoal);
                    this.goalSelector.addGoal(4, this.passiveMeleeGoal);
                }
            } else {
                this.setMeleeAttackGoal(this.meleeGoal);
                this.goalSelector.addGoal(4, this.meleeGoal);
            }

        }
    }

    @Override
    protected void populateDefaultEquipmentSlots(RandomSource randomSource, DifficultyInstance difficultyInstance) {
        this.handDropChances[EquipmentSlot.MAINHAND.getIndex()] = 0.25F;
        this.handDropChances[EquipmentSlot.OFFHAND.getIndex()] = 0.25F;

        if (RandomHelper.checkProbability(randomSource, 55)) {
            this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.BOW));
            if (RandomHelper.checkProbability(randomSource, 30)) {
                this.setItemSlot(EquipmentSlot.OFFHAND, new ItemStack(Items.IRON_SWORD));
                this.swingingArm = InteractionHand.OFF_HAND;
            }
        }
        else if (RandomHelper.checkProbability(randomSource, 30)) {
            this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.IRON_SWORD));
        }
    }

    public static boolean checkMagmaSkeletonSpawnRules(EntityType<? extends Mob> mob, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        boolean isLavaNear = !level.getBlockStates(mob.getAABB(pos.getX(), pos.getY(), pos.getZ()).inflate(10D, 2D, 10D))
                .filter(bs -> bs.is(Blocks.LAVA)).toList().isEmpty();

        if (level.getBiome(pos).is(BiomeTags.IS_NETHER) ) {
            MobConfig.SpawnSettings spawn = spawnSettings(level, mob, true);
            return spawn.enabled()
                    && level.getDifficulty() != Difficulty.PEACEFUL
                    && isValidHeight(pos, spawn);
//                    && isLavaNear;

        }
        else {
            return DenizensMonster.checkDDMonsterSpawnRules(mob, level, spawnType, pos, random)
                    && isLavaNear;
        }
    }

    /**
     * Required for floating on lava.
     */
    @Override
    public void tick() {
        super.tick();
        this.floatMagmaSkeleton();
        this.checkInsideBlocks();
    }

    /**
     * Required for floating on lava.
     */
    private void floatMagmaSkeleton() {
        if (this.isInLava()) {
            CollisionContext collisioncontext = CollisionContext.of(this);
            if (collisioncontext.isAbove(LiquidBlock.STABLE_SHAPE, this.blockPosition(), true) && !this.level().getFluidState(this.blockPosition().above()).is(FluidTags.LAVA)) {
                this.setOnGround(true);
            } else {
                this.setDeltaMovement(this.getDeltaMovement().scale(0.5D).add(0.0D, 0.05D, 0.0D));
            }
        }

    }

    /**
     * Required for floating on lava.
     * @param fluid
     * @return
     */
    @Override
    public boolean canStandOnFluid(FluidState fluid) {
        return fluid.is(FluidTags.LAVA);
    }

    protected void checkFallDamage(double p_33870_, boolean p_33871_, BlockState p_33872_, BlockPos p_33873_) {
        this.checkInsideBlocks();
        if (this.isInLava()) {
            this.resetFallDistance();
        } else {
            super.checkFallDamage(p_33870_, p_33871_, p_33872_, p_33873_);
        }
    }

    /**
     * Ensure that Skeletons don't burn in sunlight.
     * @return
     */
    @Override
    public boolean getBurnsInSun() {
        return false;
    }

    public static double round(double x, long fraction) {
        return (double) Math.round(x * fraction) / fraction;
    }

    /**
     * This Flee version doesn't require that the mob be on fire.
     */
    // TODO abstract to DenizensSkeleton or general NoBurnFleeSunGoal
    public class SkeletonFleeSunGoal extends FleeSunGoal {
        private final Level level;
        public SkeletonFleeSunGoal(PathfinderMob mob, double speedModifier) {
            super(mob, speedModifier);
            this.level = mob.level();
        }

        @Override
        public boolean canUse() {
            if (this.mob.getTarget() != null) {
                return false;
            } else if (!this.level.isDay()) {
                return false;
            } else if (!this.level.canSeeSky(this.mob.blockPosition())) {
                return false;
            } else {
                return !this.mob.getItemBySlot(EquipmentSlot.HEAD).isEmpty() ? false : this.setWantedPos();
            }
        }
    }
}
