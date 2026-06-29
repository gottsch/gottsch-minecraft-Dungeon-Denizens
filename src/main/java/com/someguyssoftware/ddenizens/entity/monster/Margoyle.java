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

import com.someguyssoftware.ddenizens.entity.ai.goal.volant.VolantCombatGoal;
import com.someguyssoftware.ddenizens.entity.ai.goal.volant.VolantLandGoal;
import com.someguyssoftware.ddenizens.setup.Registration;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 *
 * @author Mark Gottschling on July 26, 2025
 *
 */
public class Margoyle extends WingedHumanoid {
    private MonsterSize size;
    public boolean shouldFlee = false;

    /*
     *
     */
    public Margoyle(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level, MonsterSize.MEDIUM);
    }

    protected void registerGoals() {
        super.registerGoals();

        // shares the volant combat cycle; its low getMaxFlyHeight() keeps it skimming.
        this.goalSelector.addGoal(2, new VolantCombatGoal(this, 2.5D, 6.0D, 1.0D, 1.2D));
        this.goalSelector.addGoal(3, new VolantLandGoal(this, 0.8D));

        // add other goals like wandering, looking at the player, etc. with lower priorities
        this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));

        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.ATTACK_DAMAGE, 4.5)
                .add(Attributes.ARMOR, 5.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.15)
                .add(Attributes.MAX_HEALTH, 30.0)
                .add(Attributes.MOVEMENT_SPEED, 0.245D)
                .add(Attributes.FLYING_SPEED, 0.245D)
                .add(Attributes.FOLLOW_RANGE, 35D);

    }

    /**
     * Subterranean relative of the Gargoyle: it cannot really fly, only hover about
     * a foot off the ground, so it skims toward its target instead of launching
     * skyward. Kept low so it fits under tight sewer/dungeon ceilings.
     */
    @Override
    public double getMaxFlyHeight() {
        return 1.0D;
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


//    @Override
//    public void aiStep() {
//        // NOTE need these 2 lines because FlyingMob doesn't call them.
//        this.updateSwingTime();
//        this.updateNoActionTime();
//        super.aiStep();
//    }

    // TODO this needs to be abstracted
//    protected void updateNoActionTime() {
//        float f = this.getLightLevelDependentMagicValue();
//        if (f > 0.5F) {
//            this.noActionTime += 2;
//        }
//    }

    protected float getStandingEyeHeight(Pose pose, EntityDimensions entityDimensions) {
        return 1.74F;
    }


}
