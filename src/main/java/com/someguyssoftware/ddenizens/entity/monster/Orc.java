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

import java.util.Arrays;
import java.util.Random;

import com.someguyssoftware.ddenizens.entity.projectile.Rock;
import com.someguyssoftware.ddenizens.setup.Registration;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.MoveThroughVillageGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;

/**
 * 
 * @author Mark Gottschling on Apr 27, 2022
 *
 */
public class Orc extends DenizensMonster {
	private static final EntityDataAccessor<Byte> DATA = SynchedEntityData.defineId(Orc.class, EntityDataSerializers.BYTE);
	private static final EntityDataAccessor<Boolean> IS_RANGED = SynchedEntityData.defineId(Orc.class, EntityDataSerializers.BOOLEAN);
	private static final String DATA_TAG = "data";
	private static final String IS_RANGED_TAG = "isRanged";
	private static final byte RIGHT_SHOULDER_PAD = 0;
	private static final byte LEFT_SHOULDER_PAD = 1;
	private static final byte HAIR = 2;
	private static final byte BRACERS = 3;
//	private static final byte POWERING = 4;
//	private static final byte LAUNCHING = 5;
	
	private final MeleeAttackGoal meleeGoal = new MeleeAttackGoal(this, 1.0D, false);
	private final OrcThrowRockGoal rockGoal = new OrcThrowRockGoal(this);
	
//	private float throwTime;

	/**
	 * 
	 * @param entityType
	 * @param level
	 */
	public Orc(EntityType<? extends Monster> entityType, Level level) {
		super(entityType, level, MonsterSize.MEDIUM);
		Arrays.fill(this.handDropChances, 0.25F);
//		this.reassessWeaponGoal();
	}

	protected void registerGoals() {
		this.goalSelector.addGoal(3, new AvoidEntityGoal<>(this, Boulder.class, 6.0F, 1.0D, 1.2D, (entity) -> {
			if (entity instanceof Boulder) {
				return ((Boulder)entity).isActive();
			}
			return false;
		}));
		this.goalSelector.addGoal(4, new MeleeAttackGoal(this, 1.0D, false));
		this.goalSelector.addGoal(5, new MoveThroughVillageGoal(this, 1.0D, true, 4, () -> true));
		this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 1.0D));
		this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 8.0F));
		this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
	}

	/**
	 * this method needs to be assigned to the EntityType during EntityAttributeCreationEvent event.
	 * see ModSetup.onAttributeCreate()
	 * @return
	 */
	public static AttributeSupplier.Builder createAttributes() {
		return Monster.createMonsterAttributes()
				.add(Attributes.ATTACK_DAMAGE, 3.25D)
				.add(Attributes.ATTACK_KNOCKBACK, 0.2D)
				.add(Attributes.ARMOR, (double)2.0F)
				.add(Attributes.MAX_HEALTH, 25.0)
				.add(Attributes.FOLLOW_RANGE, 20.0)
				.add(Attributes.MOVEMENT_SPEED, 0.25F);                
	}

	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty,
			MobSpawnType spawnType, SpawnGroupData groupData, CompoundTag tag) {
		double healthBonus = round(random.nextDouble() * 10D, 2);
		this.getAttribute(Attributes.MAX_HEALTH).addPermanentModifier(new AttributeModifier("random orc-spawn bonus", healthBonus, AttributeModifier.Operation.ADDITION));

		double attackBonus = round(random.nextDouble() * 2.5D, 2);
		this.getAttribute(Attributes.ATTACK_DAMAGE).addPermanentModifier(new AttributeModifier("random orc-spawn bonus", attackBonus, AttributeModifier.Operation.ADDITION));

		double armorBonus = round(random.nextDouble() * 2D, 2);
		this.getAttribute(Attributes.ARMOR).addPermanentModifier(new AttributeModifier("random orc-spawn bonus", armorBonus, AttributeModifier.Operation.ADDITION));

		double armorToughBonus = round(random.nextDouble() * 5D, 2);
		this.getAttribute(Attributes.ARMOR_TOUGHNESS).addPermanentModifier(new AttributeModifier("random orc-spawn bonus", armorToughBonus, AttributeModifier.Operation.ADDITION));

		double knockbackResistanceBonus = random.nextDouble() * 0.1D;
		this.getAttribute(Attributes.KNOCKBACK_RESISTANCE).addPermanentModifier(new AttributeModifier("random orc-spawn bonus", knockbackResistanceBonus, AttributeModifier.Operation.ADDITION));

		double attackKnockbackBonus = round(random.nextDouble() * 1.5D, 2);
		this.getAttribute(Attributes.ATTACK_KNOCKBACK).addPermanentModifier(new AttributeModifier("random orc-spawn bonus", attackKnockbackBonus, AttributeModifier.Operation.ADDITION));

		double chance = this.random.nextDouble() * 1.5D * (double)difficulty.getSpecialMultiplier();
		if (chance > 1.0D) {
			this.getAttribute(Attributes.FOLLOW_RANGE).addPermanentModifier(new AttributeModifier("random orc-spawn bonus", chance, AttributeModifier.Operation.MULTIPLY_TOTAL));
		}

		// arm the orc
		this.populateDefaultEquipmentSlots(level.getRandom(), difficulty);
//		this.reassessWeaponGoal();

		Random random = new Random();
		byte data = 0;
		if (random.nextBoolean()) {
			data |= 1 << RIGHT_SHOULDER_PAD; // 0 = right shoulder pad
		}
		if (random.nextBoolean()) {
			data |= 1 << LEFT_SHOULDER_PAD;
		};
		if (random.nextBoolean()) {
			data |= 1 << HAIR;
		}
		if (random.nextBoolean()) {
			data |= 1 << BRACERS;
		}		
		setData(data);

		return groupData;
	}

	/**
	 * 
	 */
	public void reassessWeaponGoal() {
		if (this.level() != null && !this.level().isClientSide) {
			this.goalSelector.removeGoal(this.meleeGoal);
			this.goalSelector.removeGoal(this.rockGoal);
			ItemStack itemStack = this.getMainHandItem();
			if (itemStack == ItemStack.EMPTY) {
				this.goalSelector.addGoal(4, this.rockGoal);
			} else {
				this.goalSelector.addGoal(4, this.meleeGoal);
			}
		}
	}

	@Override
	protected void populateDefaultEquipmentSlots(RandomSource randomSource, DifficultyInstance p_217056_) {
		int i = this.random.nextInt(5);
		switch(i) {
		case 0:
			this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.IRON_SWORD));
			break;
		case 1:
			this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.IRON_AXE));
			break;
		case 2:
			this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Registration.CLUB.get()));
			break;
		case 3:
			this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Registration.SPIKED_CLUB.get()));
			break;
		case 4:
		default:
			this.setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
//			this.setRanged(true);
		}
		this.setItemSlot(EquipmentSlot.OFFHAND, ItemStack.EMPTY);
		
		// setup armor
		if (this.random.nextInt(5) == 0) {
			this.setItemSlot(EquipmentSlot.CHEST, new ItemStack(Items.LEATHER_CHESTPLATE));
		}
	}

	/**
	 * Set initial values of synced data
	 */
	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		this.entityData.define(DATA, (byte)0);
		this.entityData.define(IS_RANGED, false);
	}

	@Override
	public void addAdditionalSaveData(CompoundTag tag) {
		super.addAdditionalSaveData(tag);

		tag.putByte(DATA_TAG, getData());
		tag.putBoolean(IS_RANGED_TAG, isRanged());
	}

	@Override
	public void readAdditionalSaveData(CompoundTag tag) {
		super.readAdditionalSaveData(tag);
		if (tag.contains(DATA_TAG)) {
			this.setData(tag.getByte(DATA_TAG));
		}
		if (tag.contains(IS_RANGED_TAG)) {
			this.setRanged(tag.getBoolean(IS_RANGED_TAG));
		}
//		DD.LOGGER.debug("isRanged -> {}", this.isRanged());
	}


	/*
	 * This needs work 
	 */
	static class OrcThrowRockGoal extends Goal {
		private static final int DEFAULT_CHARGE_TIME = 40;
		private static final float DEFAULT_ATTACK_RADIUS = 16F;
		private Orc orc;
		private final double speedModifier;
		private int maxChargeTime;
		private int chargeTime;

		private float attackRadiusSqr;
		private boolean strafingClockwise;
		private boolean strafingBackwards;
		private int strafingTime = -1;

		/*
		 * 
		 */
		public OrcThrowRockGoal(Orc orc) {
			this(orc, 1.0D, DEFAULT_CHARGE_TIME, DEFAULT_ATTACK_RADIUS);
		}

		public OrcThrowRockGoal(Orc orc, double speedModifier, int maxChargeTime, float attackRadius) {
			this.orc = orc;
			this.speedModifier = speedModifier;
			this.maxChargeTime = maxChargeTime;
			this.attackRadiusSqr = attackRadius * attackRadius;
		}

		@Override
		public boolean canUse() {
			return orc.getTarget() != null && orc.isRanged();
			// TODO and has rock inventory
			//			&& !(this.getAttackReachSqr(orc.getTarget()) >= orc.distanceToSqr(orc.getTarget().getX(), orc.getTarget().getY(), orc.getTarget().getZ()));
		}

		@Override
		public boolean canContinueToUse() {
			return (this.canUse() || !orc.getNavigation().isDone());
			// TODO and has rock inventory
		}

		@Override
		public void start() {
			super.start();
		}

		@Override
		public void stop() {
			this.chargeTime = 0;
		}

		@Override
		public boolean requiresUpdateEveryTick() {
			return true;
		}

		@Override
		public void tick() {
			LivingEntity target = orc.getTarget();
			if (target != null) {
				double d0 = orc.distanceToSqr(target.getX(), target.getY(), target.getZ());
				boolean canSee = orc.getSensing().hasLineOfSight(target);
				boolean isCharging = this.chargeTime > 0;
				if (canSee != isCharging) {
					this.chargeTime = 0;
				}

				if (canSee) {
					++chargeTime;
				} else {
					--chargeTime;
				}

				// check if within the attack radius. if so, start strafing, else move closer to target
				if (!(d0 > (double)this.attackRadiusSqr) && this.chargeTime >= 20) {
					orc.getNavigation().stop();
					++this.strafingTime;
				} else {
					orc.getNavigation().moveTo(target, this.speedModifier);
					this.strafingTime = -1;
				}

				// if strafing for over a 1 sec, change directions
				if (this.strafingTime >= 20) {
					if ((double)orc.getRandom().nextFloat() < 0.3D) {
						this.strafingClockwise = !this.strafingClockwise;
					}

					if ((double)orc.getRandom().nextFloat() < 0.3D) {
						this.strafingBackwards = !this.strafingBackwards;
					}

					this.strafingTime = 0;
				}

				// if not strafing
				if (this.strafingTime > -1) {
					if (d0 > (double)(this.attackRadiusSqr * 0.75F)) {
						this.strafingBackwards = false;
					} else if (d0 < (double)(this.attackRadiusSqr * 0.25F)) {
						this.strafingBackwards = true;
					}

					orc.getMoveControl().strafe(this.strafingBackwards ? -0.5F : 0.5F, this.strafingClockwise ? 0.5F : -0.5F);
					orc.lookAt(target, 30.0F, 30.0F);
				} else {
					orc.getLookControl().setLookAt(target, 30.0F, 30.0F);
				}

				// TODO add test for x time and add weapon to hand.
				// ie. if chargeTime > 0 and hand is empty then add rock item to hand
				
				if (chargeTime >= maxChargeTime) {
					orc.swing(InteractionHand.MAIN_HAND);
					
					// TODO remove rock item from hand
					
					// view vector
					Vec3 vec3 = orc.getViewVector(1.0F);
					double x = target.getX() - (this.orc.getX() + vec3.x * 1.0D);
					double y = target.getY(0.5D) - (this.orc.getY(0.5D));
					double z = target.getZ() - (this.orc.getZ() + vec3.z * 1.0D);
					Rock rock = new Rock(Registration.ROCK_ENTITY_TYPE.get(), orc.level());
					rock.init(this.orc, x, y, z);
					rock.setPos(this.orc.getX() + vec3.x * 1.0D, this.orc.getY(0.5D), rock.getZ() + vec3.z * 1.0);
					orc.level().addFreshEntity(rock);					
					chargeTime = 0;
				}

			}
		}

		protected double getAttackReachSqr(LivingEntity entity) {
			return (double)(orc.getBbWidth() * 2.0F * orc.getBbWidth() * 2.0F + entity.getBbWidth());
		}
	}

	/**
	 * 
	 * @param target
	 * @param power
	 */
	public void performRangedAttack(LivingEntity target, float power) {
		// TODO remove power - its only use is to affect the base damage - which is moot for rocks
		// TODO refactor Rock to be a AbstractHurtingProjectile like Slowball, Harmball, but add gravity to the movement calcs.
		Rock rock = new Rock(Registration.ROCK_ENTITY_TYPE.get(), level());
		rock.setOwner(this);
		double d0 = target.getX() - this.getX();
		double d1 = target.getY(0.3333333333333333D) - rock.getY();
		double d2 = target.getZ() - this.getZ();
		double d3 = Math.sqrt(d0 * d0 + d2 * d2);
		rock.shoot(d0, d1 + d3 * (double)0.2F, d2, 1.6F, (float)(14 - this.level().getDifficulty().getId() * 4));
		this.playSound(SoundEvents.SKELETON_SHOOT, 1.0F, 1.0F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
		this.level().addFreshEntity(rock);
		
		this.swing(InteractionHand.MAIN_HAND);
	}
	
	public byte getDataBit(int position) {
		return (byte) ((getData() >> position) & 1);
	}

	public static double round(double x, long fraction) {
		return (double) Math.round(x * fraction) / fraction;
	}

	public boolean hasRightShoulderPad() {
		return getDataBit(RIGHT_SHOULDER_PAD) == 1;
	}

	public boolean hasLeftShoulderPad() {
		return getDataBit(LEFT_SHOULDER_PAD) == 1;
	}

	public boolean hasHair() {
		return getDataBit(HAIR) == 1;
	}

	public boolean hasBracers() {
		return getDataBit(BRACERS) == 1;
	}
	
//	public boolean isPowering() {
//		return getDataBit(POWERING) == 1;
//	}
//
//	public void setPowering(boolean isPowering) {
//		setData(POWERING, isPowering);
//	}
//	
//	public boolean isLaunching() {
//		return getDataBit(LAUNCHING) == 1;
//	}
//	
//	public void setLaunching(boolean isLaunching) {
//		setData(LAUNCHING, isLaunching);
//	}
	
	public byte getData() {
		return this.entityData.get(DATA);
	}

	public void setData(byte data) {
		this.entityData.set(DATA, data);
	}
	
	public void setData(int position, boolean value) {
		byte data = this.entityData.get(DATA);
		if (value) {
			data |= 1 << position;
		}
		else {
			data &= ~(1 << position);
		}
		setData(data);
	}
	
	public boolean isRanged() {
		return this.entityData.get(IS_RANGED);
	}
	
	public void setRanged(boolean isRanged) {
		this.entityData.set(IS_RANGED, isRanged);
	}

//	public float getThrowTime() {
//		return throwTime;
//	}
//
//	public void setThrowTime(float throwTime) {
//		this.throwTime = throwTime;
//	}
}
