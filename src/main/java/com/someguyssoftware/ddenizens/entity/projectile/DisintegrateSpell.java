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
package com.someguyssoftware.ddenizens.entity.projectile;
import com.someguyssoftware.ddenizens.item.ModItems;
import com.someguyssoftware.ddenizens.entity.ModEntities;

import com.someguyssoftware.ddenizens.config.Config;
import com.someguyssoftware.ddenizens.damagesource.ModDamageTypes;
import mod.gottsch.forge.gottschcore.world.WorldInfo;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

import java.util.Optional;

/**
 * 
 * @author Mark Gottschling on Jan 10, 2024
 *
 */
public class DisintegrateSpell extends AbstractDDHurtingProjectile implements ItemSupplier {
	private static final EntityDataAccessor<ItemStack> DATA_ITEM_STACK = SynchedEntityData.defineId(DisintegrateSpell.class, EntityDataSerializers.ITEM_STACK);

	private final ExplosionDamageCalculator damageCalculator = new ExplosionDamageCalculator();
	@Deprecated
	private int disintegrateCount;

	private float power;

	/**
	 *
	 * @param level
	 * @return
	 */
	public DisintegrateSpell create(Level level) {
		return new DisintegrateSpell(ModEntities.DISINTEGRATE_SPELL_ENTITY_TYPE.get(), level);
	}

	/**
	 *
	 * @param entityType
	 * @param level
	 */
	public DisintegrateSpell(EntityType<DisintegrateSpell> entityType, Level level) {

		super(entityType, level);
		this.power = Config.Spells.DISINTEGRATE.damage.get().floatValue();
	}

	/**
	 * 
	 * @param owner
	 * @param x
	 * @param y
	 * @param z
	 */
	public void init(LivingEntity owner, double x, double y, double z) {
		this.moveTo(owner.getX(), owner.getY(), owner.getZ(), this.getYRot(), this.getXRot());
		this.reapplyPosition();
		double d0 = Math.sqrt(x * x + y * y + z * z);
		if (d0 != 0.0D) {
			this.xPower = x / d0 * 0.1D;
			this.yPower = y / d0 * 0.1D;
			this.zPower = z / d0 * 0.1D;
		}
		this.setOwner(owner);
		this.setRot(owner.getYRot(), owner.getXRot());
	}

	@Override
	public void clientSideTick() {
		// TODO add a particle that is emitted in a circular (sin()) fashion as the
		// ball travels. See daemon for code of calculating circular positioning.
		super.clientSideTick();
	}

	@Override
	public boolean isOnFire() {
		return false;
	}

	@Override
	protected void onHitBlock(BlockHitResult blockHitResult) {
		super.onHitBlock(blockHitResult);
		if (WorldInfo.isClientSide(level())) {
			return;
		}
		BlockState state = level().getBlockState(blockHitResult.getBlockPos());
		FluidState fluidState = level().getFluidState(blockHitResult.getBlockPos());
		Optional<Float> resistance = damageCalculator.getBlockExplosionResistance(null, level(), blockHitResult.getBlockPos(), state, fluidState);

		if(resistance.isPresent()) {
			if (resistance.get() < getPower()) {
				// destroy the block
				level().setBlock(blockHitResult.getBlockPos(), Blocks.AIR.defaultBlockState(), 3);
				// generate particles
				for (int p = 0; p < 20; p++) {
					double xSpeed = random.nextGaussian() * 0.02D;
					double ySpeed = random.nextGaussian() * 0.02D;
					double zSpeed = random.nextGaussian() * 0.02D;
					((ServerLevel) level()).sendParticles(ParticleTypes.EXPLOSION,
							blockHitResult.getBlockPos().getX() + 0.5D,
							blockHitResult.getBlockPos().getY(),
							blockHitResult.getBlockPos().getZ() + 0.5D,
							1, xSpeed, ySpeed, zSpeed, (double) 0.15F);
				}

				// play sizzle sound
				this.playSound(SoundEvents.FIRE_EXTINGUISH, 0.4F, 2.0F + this.random.nextFloat() * 0.4F);
			}
			// reduce power by resistance
			setPower(Math.max(0, getPower() - resistance.get()));
		}
		if (getPower() <= 0) {
			this.discard();
		}
	}

	@Override
	protected void onHit(HitResult hitResult) {
		super.onHit(hitResult);
		this.playSound(SoundEvents.PLAYER_SPLASH_HIGH_SPEED, 0.4F, 2.0F + this.random.nextFloat() * 0.4F);
	}

	@Override
	protected void onHitEntity(EntityHitResult hitResult) {
		super.onHitEntity(hitResult);
		if (!this.level().isClientSide) {
			Entity target = hitResult.getEntity();
			Entity ownerEntity = this.getOwner();
			target.hurt(level().damageSources().source(ModDamageTypes.DISINTEGRATE_SPELL), Config.Spells.DISINTEGRATE.damage.get().floatValue());
			if (target instanceof LivingEntity) {
				this.doEnchantDamageEffects((LivingEntity)ownerEntity, target);
				discard();
			}
		}
	}

	public void setItem(ItemStack stack) {
		if (!stack.is(ModItems.DISINTEGRATE_SPELL_ITEM.get()) || stack.hasTag()) {
			this.getEntityData().set(DATA_ITEM_STACK, Util.make(stack.copy(), (itemStack) -> {
				itemStack.setCount(1);
			}));
		}
	}

	protected ItemStack getItemRaw() {
		return this.getEntityData().get(DATA_ITEM_STACK);
	}

	// TODO something else than smoke
	@Override
	protected ParticleOptions getTrailParticle() {
		return ParticleTypes.PORTAL;
	}

	@Override
	public ItemStack getItem() {
		ItemStack stack = this.getItemRaw();
		return stack.isEmpty() ? new ItemStack(ModItems.DISINTEGRATE_SPELL_ITEM.get()) : stack;
	}

	@Override
	protected void defineSynchedData() {
		this.getEntityData().define(DATA_ITEM_STACK, ItemStack.EMPTY);
	}

	@Override
	public void addAdditionalSaveData(CompoundTag tag) {
		super.addAdditionalSaveData(tag);
		ItemStack itemstack = this.getItemRaw();
		if (!itemstack.isEmpty()) {
			tag.put("Item", itemstack.save(new CompoundTag()));
		}

	}

	@Override
	public void readAdditionalSaveData(CompoundTag tag) {
		super.readAdditionalSaveData(tag);
		ItemStack itemStack = ItemStack.of(tag.getCompound("Item"));
		this.setItem(itemStack);
	}

	public float getPower() {
		return power;
	}

	public void setPower(float power) {
		this.power = power;
	}
}
