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

import java.util.Optional;
import java.util.UUID;

import javax.annotation.Nullable;

import com.someguyssoftware.ddenizens.DD;
import com.someguyssoftware.ddenizens.config.Config;
import com.someguyssoftware.ddenizens.config.Config.CommonSpawnConfig;
import com.someguyssoftware.ddenizens.config.Config.IMobConfig;

import mod.gottsch.forge.gottschcore.world.WorldInfo;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.tags.BiomeTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;

/**
 *
 * @author Mark Gottschling on Apr 6, 2022
 *
 */
public class Boulder extends DenizensMonster implements IDenizensMonster {
	private static final EntityDataAccessor<String> DATA_STATE = SynchedEntityData.defineId(Boulder.class, EntityDataSerializers.STRING);
	private static final EntityDataAccessor<Integer> DATA_LOYAL_TICKS = SynchedEntityData.defineId(Boulder.class, EntityDataSerializers.INT);
	private static final EntityDataAccessor<Optional<UUID>> DATA_OWNER_UUID = SynchedEntityData.defineId(Boulder.class, EntityDataSerializers.OPTIONAL_UUID);
	private static final EntityDataAccessor<Float> DATA_AMOUNT = SynchedEntityData.defineId(Boulder.class, EntityDataSerializers.FLOAT);
	private static final EntityDataAccessor<Float> DATA_BODY_AMOUNT = SynchedEntityData.defineId(Boulder.class, EntityDataSerializers.FLOAT);
	
	private static final String ACTIVE = "active";
	private static final String FALLING_ASLEEP = "fallingAsleep";
	private static final String DORMANT = "dormant";
	private static final String WAKING_UP = "wakingUp";
	private static final String LOYALTY_TICKS = "loyaltyTicks";
	private static final String OWNER = "owner";
	private static final int MAX_LOYALTY_TICKS = 1200; //6000; // 5 minutes

	public static final float MAX_LEG_AMOUNT = 2F;
	public static final float MAX_BODY_AMOUNT = 2F;

	private float previousAmount;
	// the current amount of transition (between 0 and 1, ie. %)
	private float amount;

	private float previousBodyAmount;
	private float bodyAmount;

	public Boulder(EntityType<? extends Monster> entityType, Level level) {

		super(entityType, level, MonsterSize.SMALL);
	}

	/**
	 * 
	 * @param mob
	 * @param level
	 * @param spawnType
	 * @param pos
	 * @param random
	 * @return
	 */
	public static boolean checkSpawnRules(EntityType<? extends Monster> mob, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
		//		return (level().getHeight() < 60 || level().getBiome(pos).getBiomeCategory() == BiomeCategory.MOUNTAIN) && checkMobSpawnRules(mob, level, spawnType, pos, random);
		IMobConfig mobConfig = Config.Mobs.MOBS.get(EntityType.getKey(mob));	
		CommonSpawnConfig config = mobConfig.getSpawnConfig();
		return ((pos.getY() > config.minHeight.get() && pos.getY() < config.maxHeight.get()) || level.getBiome(pos).is(BiomeTags.IS_MOUNTAIN) )
				&& checkAnyLightMonsterSpawnRules(mob, level, spawnType, pos, random);
	}

	/**
	 * 
	 */
	protected void registerGoals() {
		this.goalSelector.addGoal(6, new BoulderFollowOwnerGoal(this, 5.0F, 2F));
		this.goalSelector.addGoal(7, new BoulderRandomStrollGoal(this, 1.0D));
	}

	/**
	 * 
	 * @return
	 */
	public static AttributeSupplier.Builder createAttributes() {
		return Monster.createMonsterAttributes()
				.add(Attributes.ARMOR, 2.0D)
				.add(Attributes.ARMOR_TOUGHNESS, 2.0D)
				.add(Attributes.MAX_HEALTH, 80.0)
				.add(Attributes.MOVEMENT_SPEED, 0.24F);
	}

	@Override
	public boolean requiresCustomPersistence() {
		return !Config.Mobs.BOULDER.despawn.get();
	}

	@Override
	public void aiStep() {		
		if (this.level().isClientSide) {
			if (this.level().getGameTime() % 20 == 0)
				this.level().addParticle(ParticleTypes.SPORE_BLOSSOM_AIR, this.getRandomX(0.5D), this.getRandomY(), this.getRandomZ(0.5D), 0.0D, 0.0D, 0.0D);
		}
		///////

		// decrement the loyalty ticks
		int ticks = getLoyaltyTicks();
		ticks--;
		if (ticks <= 0) {
			ticks = 0;
		}
		setLoyaltyTicks(ticks);

		// NOTE isSunBurn only works on server
		if (!WorldInfo.isClientSide(level())) {
			if (isDormant() && getLoyaltyTicks() > 0 && !isSunBurn()) {
				wakeUp();
			}
			else if (isActive() && (getLoyaltyTicks() <= 0 || isSunBurn())) {
				goToSleep();
			}
			previousAmount = amount;
			if (isDormant()) {
				amount = 1F;
				bodyAmount = 1F;
			}
			else if (isFallingAsleep()) {
				if (amount < 1F) {
					amount += 0.1;
				}
				if (amount >= 1F) {
					amount = 1F;
					if (bodyAmount < 1F) {
						bodyAmount += 0.2F;
					}
					if (bodyAmount >= 1F) {
						bodyAmount = 1F;
						hibernate();
					}
				}
			}
			else if (isWakingUp()) {
				if (bodyAmount > 0F) {
					bodyAmount -= 0.1F;
				}
				if (bodyAmount <= 0F) {
					bodyAmount = 0F;
					if (amount > 0F) {
						amount -= 0.1;
					}
					if (amount <= 0F) {
						amount = 0F;
						activate();
					}
				}
			}
			else {
				amount = 0F;
				bodyAmount = 0F;
			}
			setAmount(amount);
			setBodyAmount(bodyAmount);
		}
		/////////
		super.aiStep();
	}

	public void goToSleep() {
		setState(FALLING_ASLEEP);
	}

	public void hibernate() {
//		DD.LOGGER.debug("hibernating/dormant");
		setState(DORMANT);
		setOwnerUUID(null);
		setLoyaltyTicks(0);
	}

	public void wakeUp() {
		setState(WAKING_UP);
	}

	public void activate() {
		setState(ACTIVE);
	}

	public void feed(UUID owner) {
		setLoyaltyTicks(MAX_LOYALTY_TICKS);
		if (getOwnerUUID() == null || isDormant()) {
			setOwnerUUID(owner);
		}
	}

	/**
	 * 
	 * @return
	 */
	public String getState() {
		return this.entityData.get(DATA_STATE);
	}

	public void setState(String state) {
		this.entityData.set(DATA_STATE, state);
	}

	public boolean isDormant() {
		return getState().equals(DORMANT);
	}

	public boolean isActive() {
		return getState().equals(ACTIVE);
	}
	
	public float getAmount() {
		return this.entityData.get(DATA_AMOUNT);
	}

	public void setAmount(float amount) {
		this.entityData.set(DATA_AMOUNT, amount);
	}
	
	public float getBodyAmount() {
		return this.entityData.get(DATA_BODY_AMOUNT);
	}

	public void setBodyAmount(float amount) {
		this.entityData.set(DATA_BODY_AMOUNT, amount);
	}

	/**
	 * Set initial values of synced data
	 */
	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(DATA_STATE, DORMANT);
		this.entityData.define(DATA_LOYAL_TICKS, 0);
		this.entityData.define(DATA_OWNER_UUID, Optional.empty());
		this.entityData.define(DATA_AMOUNT, 0.0F);
		this.entityData.define(DATA_BODY_AMOUNT, 0.0F);
	}

	@Override
	public void addAdditionalSaveData(CompoundTag tag) {
		super.addAdditionalSaveData(tag);

		tag.putString("state", getState());
		tag.putInt(LOYALTY_TICKS, getLoyaltyTicks());

		if (this.getOwnerUUID() != null) {
			tag.putUUID(OWNER, this.getOwnerUUID());
		}
		
		tag.putFloat("amount", getAmount());
		tag.putFloat("bodyAmount", getBodyAmount());
	}

	@Override
	public void readAdditionalSaveData(CompoundTag tag) {
		super.readAdditionalSaveData(tag);
		this.setState(tag.getString("state"));
		if (getState() == null || getState().equals("")) {
			setState(DORMANT);
		}

		if (tag.hasUUID(OWNER)) {
			UUID uuid = tag.getUUID(OWNER);
			try {
				this.setOwnerUUID(uuid);
			} catch (Throwable throwable) {
				DD.LOGGER.warn("Unable to set owner of boulder to -> {}", uuid);
			}
		}

		if (tag.contains(LOYALTY_TICKS)) {
			this.setLoyaltyTicks(tag.getInt(LOYALTY_TICKS));
		}
		if (tag.contains("amount")) {
			this.setAmount(tag.getFloat("amount"));
		}
		if (tag.contains("bodyAmount")) {
			this.setBodyAmount(tag.getFloat("bodyAmount"));
		}
	}

	@Nullable
	public LivingEntity getOwner() {
		try {
			UUID uuid = this.getOwnerUUID();
			return uuid == null ? null : this.level().getPlayerByUUID(uuid);
		} catch (IllegalArgumentException illegalargumentexception) {
			return null;
		}
	}

	protected boolean isSunBurn() {
		//		DD.LOGGER.debug("is day -> {}", this.level().isDay());
		//		DD.LOGGER.debug("is server side -> {}", !this.level().isClientSide);
		if (this.level().isDay() && !this.level().isClientSide) {
			float brightness = this.getLightLevelDependentMagicValue();
			BlockPos pos = new BlockPos(this.blockPosition());
//			DD.LOGGER.debug("can see sky -> {}", this.level().canSeeSky(pos));
			//			boolean flag = this.isInWaterRainOrBubble() || this.isInPowderSnow || this.wasInPowderSnow;
			if (brightness > 0.5F && this.level().canSeeSky(pos)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 
	 * @author Mark Gottschling on Apr 9, 2022
	 *
	 */
	public static class BoulderRandomStrollGoal extends WaterAvoidingRandomStrollGoal {
		private Boulder boulder;
		public BoulderRandomStrollGoal(Boulder boulder, double speedModifier) {
			super(boulder, speedModifier);
			this.boulder = boulder;
		}

		@Override
		public boolean canUse() {
			if (!boulder.isActive() || boulder.getOwnerUUID() == null) {
				return false;
			}
			return super.canUse();
		}
	}

	public static class BoulderFollowOwnerGoal extends Goal {
		private Boulder boulder;
		// the distance away at which the boulder starts to follow
		private float startDistance;
		// the distance away at which the bould stops following
		private float stopDistance;
		private final LevelReader level;
		private final PathNavigation navigation;
		private LivingEntity owner;
		private int timeToRecalcPath;
		private float oldWaterCost;

		/**
		 * 
		 * @param boulder
		 * @param startDistance
		 * @param stopDistance
		 */
		public BoulderFollowOwnerGoal(Boulder boulder, float startDistance, float stopDistance) {
			this.boulder = boulder;
			this.startDistance = startDistance;
			this.stopDistance = stopDistance;
			this.level = boulder.level();
			this.navigation = boulder.getNavigation();
		}

		@Override
		public boolean canUse() {
			// state check
			if (!boulder.isActive()) {
				return false;
			}

			// owner checks
			LivingEntity entity = this.boulder.getOwner();
			if (entity == null) {
				return false;
			} else if (entity.isSpectator()) {
				return false;
			} else if (this.boulder.distanceToSqr(entity) < (double)(this.startDistance * this.startDistance)) {
				return false;
			}
			else {
				this.owner = entity;
			}		      
			return true;
		}

		@Override
		public boolean canContinueToUse() {
			if (this.navigation.isDone() || !this.boulder.isActive()) {
				return false;
			} else {
				return !(this.boulder.distanceToSqr(this.owner) <= (double)(this.stopDistance * this.stopDistance));
			}
		}

		@Override
		public void start() {
			this.timeToRecalcPath = 0;
			this.oldWaterCost = this.boulder.getPathfindingMalus(BlockPathTypes.WATER);
			this.boulder.setPathfindingMalus(BlockPathTypes.WATER, 0.0F);
		}

		@Override
		public void stop() {
			this.owner = null;
			this.navigation.stop();
			this.boulder.setPathfindingMalus(BlockPathTypes.WATER, this.oldWaterCost);
		}

		@Override
		public void tick() {
			this.boulder.getLookControl().setLookAt(this.owner, 5.0F, (float)this.boulder.getMaxHeadXRot());
			if (--this.timeToRecalcPath <= 0) {
				this.timeToRecalcPath = this.adjustedTickDelay(10);

				if (this.boulder.distanceToSqr(this.owner) >= 144.0D) {
					this.teleportToOwner();
				} else {
					this.navigation.moveTo(this.owner, 1.0); // 1.0 = speed modifier
				}
			}
		}

		private void teleportToOwner() {
			BlockPos blockpos = this.owner.blockPosition();

			for(int i = 0; i < 10; ++i) {
				int j = this.randomIntInclusive(-3, 3);
				int k = this.randomIntInclusive(-1, 1);
				int l = this.randomIntInclusive(-3, 3);
				boolean flag = this.maybeTeleportTo(blockpos.getX() + j, blockpos.getY() + k, blockpos.getZ() + l);
				if (flag) {
					return;
				}
			}

		}

		private boolean maybeTeleportTo(int x, int y, int z) {
			if (Math.abs((double)x - this.owner.getX()) < 2.0D && Math.abs((double)z - this.owner.getZ()) < 2.0D) {
				return false;
			} else if (!this.canTeleportTo(new BlockPos(x, y, z))) {
				return false;
			} else {
				this.boulder.moveTo((double)x + 0.5D, (double)y, (double)z + 0.5D, this.boulder.getYRot(), this.boulder.getXRot());
				this.navigation.stop();
				return true;
			}
		}

		private boolean canTeleportTo(BlockPos pos) {
			BlockPathTypes blockpathtypes = WalkNodeEvaluator.getBlockPathTypeStatic(this.level, pos.mutable());
			if (blockpathtypes != BlockPathTypes.WALKABLE) {
				return false;
			} else {
				BlockState blockstate = this.level.getBlockState(pos.below());
				if (blockstate.getBlock() instanceof LeavesBlock) {
					return false;
				} else {
					BlockPos blockpos = pos.subtract(this.boulder.blockPosition());
					return this.level.noCollision(this.boulder, this.boulder.getBoundingBox().move(blockpos));
				}
			}
		}

		private int randomIntInclusive(int p_25301_, int p_25302_) {
			return this.boulder.getRandom().nextInt(p_25302_ - p_25301_ + 1) + p_25301_;
		}
	}

	public float getPreviousAmount() {
		return previousAmount;
	}

	public void setPreviousAmount(float previousAmount) {
		this.previousAmount = previousAmount;
	}

	public boolean isFallingAsleep() {
		return getState().equals(FALLING_ASLEEP);
	}

	public boolean isWakingUp() {
		return getState().equals(WAKING_UP);
	}

	@Nullable
	public UUID getOwnerUUID() {
		return this.entityData.get(DATA_OWNER_UUID).orElse((UUID)null);
	}

	public void setOwnerUUID(@Nullable UUID owner) {
		this.entityData.set(DATA_OWNER_UUID, Optional.ofNullable(owner));
	}

	public int getLoyaltyTicks() {
		return this.entityData.get(DATA_LOYAL_TICKS);
	}

	public void setLoyaltyTicks(int ticks) {
		this.entityData.set(DATA_LOYAL_TICKS, ticks);
	}

	public float getPreviousBodyAmount() {
		return previousBodyAmount;
	}

	public void setPreviousBodyAmount(float previousBodyAmount) {
		this.previousBodyAmount = previousBodyAmount;
	}
}
