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

import com.someguyssoftware.ddenizens.config.Config;
import com.someguyssoftware.ddenizens.entity.ai.goal.CastDisintegrateGoal;
import com.someguyssoftware.ddenizens.entity.ai.goal.CastParalysisGoal;
import com.someguyssoftware.ddenizens.entity.ai.goal.WeightedChanceSummonGoal;
import com.someguyssoftware.ddenizens.setup.Registration;
import mod.gottsch.forge.gottschcore.random.WeightedCollection;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.FlyingMob;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

/**
 * @author Mark Gottschling on Jan 11, 2024
 *
 */
public class DeathTyrant extends Beholderkin {

	private static final int MAX_FLOAT_HEIGHT = 8;

	/**
	 *
	 * @param entityType
	 * @param level
	 */
	public DeathTyrant(EntityType<? extends FlyingMob> entityType, Level level) {
		super(entityType, level, MonsterSize.LARGE);
		this.moveControl = new BeholderkinMoveControl(this);
//		setPersistenceRequired();
		this.xpReward = 20;
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(4, new BeholderkinBiteGoal(this, Config.Mobs.DEATH_TYRANT.biteCooldownTime.get()));
		this.goalSelector.addGoal(5, new BeholderkinRandomFloatAroundGoal(this, Config.Mobs.DEATH_TYRANT.maxFloatHeight.get()));
		this.goalSelector.addGoal(7, new BeholderkinLookGoal(this));

		this.goalSelector.addGoal(6, new CastParalysisGoal(this, Config.Mobs.DEATH_TYRANT.spellChargeTime.get()));

		WeightedCollection<Double, EntityType<? extends Mob>> mobs = new WeightedCollection<>();
		mobs.add(20D, EntityType.ZOMBIE);
		mobs.add(20D, EntityType.HUSK);
		mobs.add(20D, EntityType.SKELETON);
		mobs.add(60D, Registration.SKELETON_WARRIOR_TYPE.get());
		this.goalSelector.addGoal(6, new WeightedChanceSummonGoal(this, Config.Mobs.DEATH_TYRANT.summonCooldownTime.get(), 100, mobs, Config.Mobs.DEATH_TYRANT.minSummonSpawns.get(), Config.Mobs.DEATH_TYRANT.maxSummonSpawns.get()));
		this.goalSelector.addGoal(6, new WeightedChanceSummonGoal(this, Config.Mobs.DEATH_TYRANT.summonDaemonCooldownTime.get(), 40, Registration.DAEMON_ENTITY_TYPE.get(), 1, 1));
		// NOTE unaffected by Boulders
		// TODO need custom hurtbyTarget like headless
		// TODO headless hurtby needs to be become a stand alone class that any mob can use
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
	}

	/**
	 * this method needs to be assigned to the EntityType during EntityAttributeCreationEvent event.
	 * see ModSetup.onAttributeCreate()
	 * @return
	 */
	public static AttributeSupplier.Builder prepareAttributes() {
		return Mob.createMobAttributes()
				.add(Attributes.ATTACK_DAMAGE, 8.0D)
				.add(Attributes.ATTACK_KNOCKBACK, 1.5D)
				.add(Attributes.ARMOR, 3.0D)
				.add(Attributes.ARMOR_TOUGHNESS, 3.0D)
				.add(Attributes.MAX_HEALTH, 36.0)
				.add(Attributes.FOLLOW_RANGE, 100.0)
				.add(Attributes.MOVEMENT_SPEED, 0.20F);
	}

	@Override
	public boolean requiresCustomPersistence() {
		return !Config.Mobs.DEATH_TYRANT.despawn.get();
	}

	@Override
	public int getAmbientSoundInterval() {
		return 160;
	}

	@Nullable
	@Override
	protected SoundEvent getDeathSound() {
		return super.getDeathSound();
	}

	@Nullable
	@Override
	protected SoundEvent getAmbientSound() {
		return Registration.AMBIENT_DEATH_TYRANT.get();
	}
}
