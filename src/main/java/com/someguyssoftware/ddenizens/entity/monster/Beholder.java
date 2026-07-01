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

import com.someguyssoftware.ddenizens.entity.ai.goal.WeightedCastProjectileGoal;
import com.someguyssoftware.ddenizens.entity.ai.goal.WeightedChanceSummonGoal;
import com.someguyssoftware.ddenizens.entity.projectile.*;
import com.someguyssoftware.ddenizens.setup.Registration;
import mod.gottsch.forge.gmm.core.config.MobConfig;
import mod.gottsch.forge.gmm.core.config.MobConfigHelper;
import mod.gottsch.forge.gottschcore.random.WeightedCollection;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

/**
 * @author Mark Gottschling on Jan 9, 2024
 *
 */
public class Beholder extends Beholderkin {

	/**
	 *
	 * @param entityType
	 * @param level
	 */
	public Beholder(EntityType<? extends FlyingMob> entityType, Level level) {
		super(entityType, level, MonsterSize.LARGE);
		this.moveControl = new BeholderkinMoveControl(this);
		this.xpReward = 20;
	}

	@Override
	protected void registerGoals() {
		MobConfig config = MobConfigHelper.get(this);
		this.goalSelector.addGoal(4, new BeholderkinBiteGoal(this, (int) config.number("biteCooldownTime", 40)));
		this.goalSelector.addGoal(5, new BeholderkinRandomFloatAroundGoal(this, (int) config.number("maxFloatHeight", 7)));
		this.goalSelector.addGoal(8, new BeholderkinLookGoal(this));

		WeightedCollection<Integer, AbstractDDHurtingProjectile> spells = new WeightedCollection<>();
		spells.add(3, new ParalysisSpell(Registration.PARALYSIS_SPELL_ENTITY_TYPE.get(), level()));
		spells.add(2, new HarmSpell(Registration.HARM_SPELL_ENTITY_TYPE.get(), level()));
		spells.add(1, new DisintegrateSpell(Registration.DISINTEGRATE_SPELL_ENTITY_TYPE.get(), level()));
		spells.add(1, new DisarmSpell(Registration.DISARM_SPELL_ENTITY_TYPE.get(), level()));
		this.goalSelector.addGoal(6, new WeightedCastProjectileGoal(this, (int) config.number("spellChargeTime", 80), spells));

		WeightedCollection<Double, EntityType<? extends Mob>> mobs = new WeightedCollection<>();
		mobs.add(60D, Registration.HEADLESS_ENTITY_TYPE.get());
		mobs.add(40D, Registration.ORC_ENTITY_TYPE.get());
		mobs.add(20D, Registration.SPECTATOR_TYPE.get());
		mobs.add(20D, EntityType.BLAZE);
		this.goalSelector.addGoal(7, new WeightedChanceSummonGoal(this, (int) config.number("summonCooldownTime", 1200), 100, mobs, (int) config.number("minSummonSpawns", 1), (int) config.number("maxSummonSpawns", 1)));
		this.goalSelector.addGoal(7, new WeightedChanceSummonGoal(this, (int) config.number("summonDaemonCooldownTime", 2400), 10, Registration.DAEMON_ENTITY_TYPE.get(), 1, 1));

		// NOTE unaffected by Boulders

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
		return !MobConfigHelper.get(this).flag("despawn", true);
	}

	@Override
	public int getAmbientSoundInterval() {
		return 160;
	}

	@Nullable
	@Override
	protected SoundEvent getAmbientSound() {
		return Registration.AMBIENT_BEHOLDER.get();
	}
}
