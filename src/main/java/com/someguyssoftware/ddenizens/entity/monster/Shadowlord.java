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

import java.util.Iterator;
import java.util.List;

import com.someguyssoftware.ddenizens.DD;
import com.someguyssoftware.ddenizens.config.Config;
import com.someguyssoftware.ddenizens.entity.ai.goal.CastHarmGoal;
import com.someguyssoftware.ddenizens.entity.ai.goal.WeightedChanceSummonGoal;
import com.someguyssoftware.ddenizens.setup.Registration;

import mod.gottsch.forge.gottschcore.random.RandomHelper;
import mod.gottsch.forge.gottschcore.random.WeightedCollection;
import mod.gottsch.forge.gottschcore.world.WorldInfo;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.BiomeTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FleeSunGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RestrictSunGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.Nullable;

/**
 * 
 * @author Mark Gottschling on Apr 15, 2022
 *
 */
public class Shadowlord extends DenizensMonster {
	private static final int SUN_BURN_SECONDS = 2;
	protected static final double MELEE_DISTANCE_SQUARED = 16D;
	protected static final double SUMMON_DISTANCE_SQUARED = 1024D;
	protected static final double SHOOT_DISTANCE_SQUARED = 4096D;
	protected static final int SUMMON_CHARGE_TIME = 2400;

	private double auraOfBlindessTime;
	private int drainCooldownTime;


	/**
	 * 
	 * @param entityType
	 * @param level
	 */
	public Shadowlord(EntityType<? extends Monster> entityType, Level level) {
		super(entityType, level, MonsterSize.LARGE);
		this.xpReward = 8;
	}

	/**
	 * 
	 */
	protected void registerGoals() {
		this.goalSelector.addGoal(2, new RestrictSunGoal(this));
		this.goalSelector.addGoal(3, new FleeSunGoal(this, 1.1D));
		this.goalSelector.addGoal(4, new CastHarmGoal(this, Config.Mobs.SHADOWLORD.harmChargeTime.get(), SHOOT_DISTANCE_SQUARED, MELEE_DISTANCE_SQUARED));

		// TODO change to the new WeightedSummonGoal
		WeightedCollection<Double, EntityType<? extends Mob>> mobs = new WeightedCollection<>();
		mobs.add(70D, Registration.SHADOW_ENTITY_TYPE.get());
		mobs.add(30D, Registration.GHOUL_ENTITY_TYPE.get());
		this.goalSelector.addGoal(7, new WeightedChanceSummonGoal(this, Config.Mobs.SHADOWLORD.summonCooldownTime.get(), 100, mobs, Config.Mobs.SHADOWLORD.minSummonSpawns.get(), Config.Mobs.SHADOWLORD.maxSummonSpawns.get()));

		this.goalSelector.addGoal(7, new WeightedChanceSummonGoal(this,
				Config.Mobs.SHADOWLORD.summonDaemonCooldownTime.get(),
				Config.Mobs.SHADOWLORD.summonDaemonProbability.get(),
				Registration.DAEMON_ENTITY_TYPE.get(), 1, 1));

		this.goalSelector.addGoal(5, new MeleeAttackGoal(this, 1.1D, false));
		
		this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 1.0D));
		this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 16.0F));
		this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));


		this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Boulder.class, true, avoidBoulder));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
	}

	/**
	 * 
	 * @return
	 */
	public static AttributeSupplier.Builder createAttributes() {
		return Monster.createMonsterAttributes()
				.add(Attributes.ATTACK_DAMAGE, 8.0D)
				.add(Attributes.MAX_HEALTH, 50.0D)
				.add(Attributes.ARMOR, 10.0D)
				.add(Attributes.ARMOR_TOUGHNESS, 1.0D)
				.add(Attributes.KNOCKBACK_RESISTANCE, 0.8)
				.add(Attributes.MOVEMENT_SPEED, 0.2F)
				.add(Attributes.FOLLOW_RANGE, 80D);
	}

	@Override
	public boolean requiresCustomPersistence() {
		return !Config.Mobs.SHADOWLORD.despawn.get();
	}

	@Override
	public MobType getMobType() {
		return MobType.UNDEAD;
	}

	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty,
			MobSpawnType spawnType, SpawnGroupData groupData, CompoundTag tag) {

		groupData = super.finalizeSpawn(level, difficulty, spawnType, groupData, tag);

		RandomSource randomSource = level.getRandom();
		this.populateDefaultEquipmentSlots(randomSource, difficulty);

		return groupData;
	}

	protected void populateDefaultEquipmentSlots(RandomSource randomSource, DifficultyInstance difficultyInstance) {
		this.handDropChances[EquipmentSlot.MAINHAND.getIndex()] = 0.75F;
		this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Registration.SHADOW_BLADE.get()));
	}

	@Deprecated
	public static boolean checkShadowlordSpawnRules(EntityType<Shadowlord> mob, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
		if (level.getBiome(pos).is(BiomeTags.IS_NETHER)) {
			return checkDDMonsterNetherSpawnRules(mob, level, spawnType, pos, random);
		}
		else {
			return checkDDMonsterSpawnRules(mob, level, spawnType, pos, random);
		}
	}

	/**
	 * Only executed on server side.
	 * @param entity
	 * @param amount
	 */
	public void drain(LivingEntity entity, float amount) {
		if (drainCooldownTime > 0) {
			return;
		}
		drainCooldownTime = Config.Mobs.SHADOWLORD.drainCooldownTime.get();

		// add damage to Shadowlord's health
		DD.LOGGER.debug("draining {} hp from player", amount);
		setHealth(Math.min(getMaxHealth(), getHealth() + amount));
		if (!WorldInfo.isClientSide(this.level())) {
			for (int p = 0; p < 40; p++) {
				double xSpeed = random.nextGaussian() * 0.02D;
				double ySpeed = random.nextGaussian() * 0.02D;
				double zSpeed = random.nextGaussian() * 0.02D;
				((ServerLevel)level()).sendParticles(ParticleTypes.SOUL, blockPosition().getX() + 0.5D, blockPosition().getY(), blockPosition().getZ() + 0.5D, 1, xSpeed, ySpeed, zSpeed, (double)0.15F);
				// TODO set this up better so particles are on the client side
				//				level().addParticle(ParticleTypes.SOUL,blockPosition().getX() + 0.5D, blockPosition().getY(), blockPosition().getZ() + 0.5D, xSpeed, ySpeed, zSpeed);
			}
		}
	}

	@Override
	public void aiStep() {
		/*
		 * Create a ring of smoke particles to delineate the boundary of the Aura of Blindness 
		 */
		if (WorldInfo.isClientSide(this.level())) {
			// general particles around body
			for(int i = 0; i < 2; ++i) {
				this.level().addParticle(ParticleTypes.SMOKE, this.getRandomX(0.5D), this.getRandomY(), this.getRandomZ(0.5D), 0.0D, 0.0D, 0.0D);
			}

			double x = Math.sin(auraOfBlindessTime);
			double z = Math.cos(auraOfBlindessTime);
			this.level().addParticle(ParticleTypes.SMOKE, this.position().x + x, position().y, position().z + z, 0, 0, 0);
			this.level().addParticle(ParticleTypes.SMOKE, this.position().x + (x*2D), position().y, position().z + (z*2D), 0, 0, 0);
			auraOfBlindessTime++;
			auraOfBlindessTime = auraOfBlindessTime % 360;
		}

		/*
		 * Apply Aura of Blindness to Players
		 */
		// get all entities with radius
		double distance = 2;
		AABB aabb = AABB.unitCubeFromLowerCorner(this.position()).inflate(distance, distance, distance);
		List<? extends Player> list = this.level().getEntitiesOfClass(Player.class, aabb, EntitySelector.NO_SPECTATORS);
		Iterator<? extends Player> iterator = list.iterator();
		while (iterator.hasNext()) {
			Player target = (Player)iterator.next();
			// test if player is wearing golden helmet
			ItemStack helmetStack = target.getItemBySlot(EquipmentSlot.HEAD);
			if (helmetStack.isEmpty() || helmetStack.getItem() != Items.GOLDEN_HELMET) {
				// inflict blindness for 1 second
				target.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, Config.Mobs.SHADOWLORD.blindnessDuration.get(), 0), this);
			}
		}

		// decrement the drainCooldownTime
		drainCooldownTime = Math.max(--drainCooldownTime, 0);

		// set on fire if in sun
		if (this.isSunBurnTick()) {
			this.setSecondsOnFire(SUN_BURN_SECONDS);
		}
		super.aiStep();
	}

	/**
	 * 
	 */
	@Override
	public boolean doHurtTarget(Entity target) {
		if (super.doHurtTarget(target)) {
			if (target instanceof Player) {
				// inflict poison
				if (RandomHelper.checkProbability(this.random, Config.Mobs.SHADOWLORD.poisonProbability.get())) {
					((LivingEntity) target).addEffect(new MobEffectInstance(MobEffects.POISON, Config.Mobs.SHADOWLORD.poisonDuration.get(), 0), this);
				}
			}
			return true;
		} 
		return false;
	}

	/**
	 * 
	 */
	@Override
	public boolean hurt(DamageSource damageSource, float amount) {
		if (WorldInfo.isClientSide(this.level())) {
			return false;
		}

		if (damageSource.getEntity() != null && damageSource.getEntity() instanceof Player player) {
			ItemStack heldStack = player.getItemInHand(InteractionHand.MAIN_HAND);

			if (!heldStack.isEmpty()) {
				if (heldStack.getItem() == Items.GOLDEN_SWORD) {
					// increase damage to that of iron tier
					amount += 2.0F;
					// negate the weakness from the strike power of the sword
					// gold does full damage
					if (player.hasEffect(MobEffects.WEAKNESS)) {
						amount += 4.0F * (player.getEffect(MobEffects.WEAKNESS).getAmplifier() + 1);
					}
				} else if (heldStack.getItem() == Registration.SHADOW_BLADE.get()) {
					// increase damage to that of a netherite tier
					amount += 2.0F;
					if (player.hasEffect(MobEffects.WEAKNESS)) {
						amount += 4.0F * (player.getEffect(MobEffects.WEAKNESS).getAmplifier() + 1);
					}
				}
				else {
					if (heldStack.getItem() instanceof TieredItem tieredItem) {
						int penalty = 0;
						Tier tier = tieredItem.getTier();
						if (tier == Tiers.NETHERITE) {
							penalty = -3;
						} else if (tier == Tiers.DIAMOND) {
							penalty = -2;
						} else if (tier == Tiers.IRON) {
							penalty = -1;
						} else if (tier == Tiers.STONE || tier == Tiers.WOOD) {
							// don't incur a penalty
						}
						amount += penalty;
					} else {
						// all other items
						amount = Math.max(amount, 4.0f); // same as stone sword
					}
				}
				DD.LOGGER.debug("new strike amount -> {}", amount);
			}
		}
		return super.hurt(damageSource, amount);
	}

	@Nullable
	@Override
	protected SoundEvent getAmbientSound() {
		return Registration.AMBIENT_SHADOWLORD.get();
	}

	@Override
	protected void playStepSound(BlockPos pos, BlockState state) {
		this.playSound(this.getStepSound(), 0.15F, 1.0F);
	}

	@Nullable
	protected SoundEvent getStepSound() {
		return Registration.SHADOWLORD_STEP.get();
	}
}