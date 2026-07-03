/*
 * This file is part of  Dungeon Denizens.
 * Copyright (c) 2022 Mark Gottschling (gottsch)
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import com.someguyssoftware.ddenizens.entity.ai.goal.target.HeadlessHurtByTargetGoal;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

/**
 * Headless are about 20% better at everything than Zombies
 * and are smart enough to avoid Creepers.
 * 
 * @author Mark Gottschling on Apr 1, 2022
 *
 */
public class Headless extends DenizensMonster {

	/**
	 * 
	 * @param entityType
	 * @param level
	 */
	public Headless(EntityType<? extends Monster> entityType, Level level) {

		super(entityType, level, MonsterSize.MEDIUM);
	}

	@Override
	protected void registerGoals() {
		this.goalSelector.addGoal(0, new FloatGoal(this));
		/*
		 * executes attack behaviour
		 * this = pathfinding mob, 1.0 = speed modifier, false = following even if not seen
		 */
		this.goalSelector.addGoal(4, new MeleeAttackGoal(this, 1.0D, false));
		this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.8));
		this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
		
		/*
		 * 6 = max distance, 1 = walk speed modifier, 1.2 spring speed modifier
		 */
		this.goalSelector.addGoal(3, new AvoidEntityGoal<>(this, Creeper.class, 6.0F, 1.0D, 1.2D));
		
		// target goals
		/*
		 * vanilla setAlertOthers() is opposite of what you would expect. if a value is passed in,
		 * that is the exception list, as opposed to the included list.
		 * vanilla setAlertOthers() will alert ALL mobs based on class hierarchry of instanceof Mob.
		 *
		 * Headless setAlertOthers() alerts only those listed.
		 */
		// for the included list. ie this mob will alert the other specific listed mobs.
		this.targetSelector.addGoal(1, (new HeadlessHurtByTargetGoal(this)).setAlertOthers(Headless.class, Gazer.class, Beholder.class));
		
		/*
		 * determines who to target
		 * this = pathfinding mob, Player = target, true = must see entity in order to target
		 */
		this.targetSelector.addGoal(2, new HeadlessNearestAttackableTargetGoal<>(this, Player.class, true).setAlertOthers(Headless.class, Gazer.class));
	}

	/**
	 * this method needs to be assigned to the EntityType during EntityAttributeCreationEvent event.
	 * see ModSetup.onAttributeCreate()
	 * @return
	 */
	public static AttributeSupplier.Builder createAttributes() {
		return Monster.createMonsterAttributes()
				.add(Attributes.ATTACK_DAMAGE, 3.5)
				.add(Attributes.ATTACK_KNOCKBACK, 0.5D)
				.add(Attributes.ARMOR, 1.0D)
				.add(Attributes.ARMOR_TOUGHNESS, 1.0D)
				.add(Attributes.MAX_HEALTH, 24.0)
				.add(Attributes.FOLLOW_RANGE, 36.0)
				.add(Attributes.MOVEMENT_SPEED, 0.26F);
	}

	/**
	 *
	 * @param <T>
	 */
	public static class HeadlessNearestAttackableTargetGoal<T extends LivingEntity> extends NearestAttackableTargetGoal<T> {
		private static final int ALERT_RANGE_Y = 10;
		private List<Class<?>> othersToAlert = new ArrayList<>();

		/**
		 * 
		 * @param mob
		 * @param targetType
		 * @param mustSee
		 */
		public HeadlessNearestAttackableTargetGoal(Mob mob, Class<T> targetType, boolean mustSee) {
			super(mob, targetType, mustSee);
		}

		/**
		 * 
		 * @param toAlert
		 * @return
		 */
		public HeadlessNearestAttackableTargetGoal<?> setAlertOthers(Class<?>... toAlert) {
			this.othersToAlert.addAll(Arrays.asList(toAlert));
			return this;
		}

		@Override
		public void start() {
			this.mob.setTarget(this.target);
			if (this.targetType == Player.class || this.targetType == ServerPlayer.class) {
				this.alertOthers();
			}
			super.start();
		}

		protected void alertOthers() {
			double distance = this.getFollowDistance();
			AABB aabb = AABB.unitCubeFromLowerCorner(this.mob.position()).inflate(distance, ALERT_RANGE_Y, distance);
			List<? extends Mob> list = this.mob.level().getEntitiesOfClass(Mob.class, aabb, EntitySelector.NO_SPECTATORS);
			Iterator<? extends Mob> iterator = list.iterator();
			while (iterator.hasNext()) {
				Mob otherMob = (Mob)iterator.next();
				if (this.mob != otherMob && otherMob.getTarget() == null) {
					if (this.othersToAlert.contains(otherMob.getClass())) {
						alertOther(otherMob, this.mob.getLastHurtByMob());
					}
				}
			}
		}

		protected void alertOther(Mob otherMob, LivingEntity target) {
			otherMob.setTarget(target);
		}
	}
}
