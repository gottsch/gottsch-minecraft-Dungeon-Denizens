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
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
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
 * A winged humanoid that walks on the ground and launches into flight to close
 * on distant targets, then lands to melee. Flight behaviour lives in the
 * {@link WingedHumanoid} base + the volant goal package.
 *
 * @author Mark Gottschling on July 3, 2025
 */
public class Gargoyle extends WingedHumanoid {
    private MonsterSize size;
    // abstract out to WingedHumanoid ?? will all winged humanoids flee on hit?
    public boolean shouldFlee = false;

    public Gargoyle(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level, MonsterSize.MEDIUM);
    }

    protected void registerGoals() {
        super.registerGoals();

        // combat owns the launch -> fly -> land -> melee cycle; land goal covers the
        // targetless descent at a lower priority.
        this.goalSelector.addGoal(2, new VolantCombatGoal(this, 2.5D, 6.0D, 1.0D, 1.2D));
        this.goalSelector.addGoal(3, new VolantLandGoal(this, 0.8D));
        // wandering / looking goals at lower priorities
        this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));

        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.ATTACK_DAMAGE, 4)
                .add(Attributes.ARMOR, 4.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.1)
                .add(Attributes.MAX_HEALTH, 25.0)
                .add(Attributes.MOVEMENT_SPEED, 0.24D)
                .add(Attributes.FLYING_SPEED, 0.24D)
                .add(Attributes.FOLLOW_RANGE, 50D);
    }

    @Override
    public boolean canBeAffected(MobEffectInstance effectInstance) {
        // immune to Poison
        if (effectInstance.getEffect() == MobEffects.POISON) {
            return false;
        }
        // immune to strong Slowness (closest thing to "Petrified")
        if (effectInstance.getEffect() == MobEffects.MOVEMENT_SLOWDOWN && effectInstance.getAmplifier() >= 2) {
            return false;
        }
        return super.canBeAffected(effectInstance);
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

    protected SoundEvent getStepSound() {
        return null;
    }

    @Override
    public @NotNull MobType getMobType() {
        return MobType.UNDEFINED;
    }

    protected float getStandingEyeHeight(Pose pose, EntityDimensions dimensions) {
        return 1.74F;
    }
}
