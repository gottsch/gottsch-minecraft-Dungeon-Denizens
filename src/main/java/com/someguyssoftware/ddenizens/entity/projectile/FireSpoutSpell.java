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
package com.someguyssoftware.ddenizens.entity.projectile;
import com.someguyssoftware.ddenizens.entity.ModEntities;

import com.someguyssoftware.ddenizens.config.Config;
import com.someguyssoftware.ddenizens.damagesource.ModDamageTypes;

import mod.gottsch.forge.gottschcore.world.WorldInfo;
import net.minecraft.Util;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

/**
 * 
 * @author Mark Gottschling on Apr 19, 2022
 *
 */
public class FireSpoutSpell extends AbstractDDHurtingProjectile implements ItemSupplier {
	private static final String ORIGINAL_POSITION_TAG = "originalPosition";
	
	private static final EntityDataAccessor<ItemStack> DATA_ITEM_STACK = SynchedEntityData.defineId(FireSpoutSpell.class, EntityDataSerializers.ITEM_STACK);
	private static final EntityDataAccessor<Integer> ORIGINAL_Y = SynchedEntityData.defineId(FireSpoutSpell.class, EntityDataSerializers.INT);

	private int explosionPower = Config.Spells.FIRESPOUT.explosionRadius.get();

	/*
	 * the original position of the projectile entity. 
	 */
	private Vec3 originalPosition;

	/**
	 *
	 * @param level
	 * @return
	 */
	public FireSpoutSpell create(Level level) {
		return new FireSpoutSpell(ModEntities.FIRESPOUT_SPELL_ENTITY_TYPE.get(), level);
	}


	/**
	 * 
	 * @param entityType
	 * @param level
	 */
	public FireSpoutSpell(EntityType<FireSpoutSpell> entityType, Level level) {
		super(entityType, level);
	}

	/**
	 * 
	 */
	@Override
	public void init(LivingEntity owner, double x, double y, double z, double x2, double y2, double z2) {
		super.init(owner, x, y, z, x2, y2, z2);
		setOriginalPosition(new Vec3(x, y, z));
	}

	@Override
	public void init(LivingEntity owner, double x, double y, double z) {
		super.init(owner, x, y, z);
		setOriginalPosition(new Vec3(x, y, z));
	}
	
	/**
	 * 
	 * @param origin
	 */
	public void setOrigin(double x, double y, double z) {
		setOriginalPosition(new Vec3(x, y, z));
		setPos(x, y, z);
	}

	public void setOrigin(Vec3 origin) {
		setOriginalPosition(origin);
		setPos(origin);
	}

	@Override
	public boolean isOnFire() {
		return true;
	}

	/**
	 * Instead of travelling towards the target, FireJet travels upwards for 5 blocks
	 */
	@Override
	public void clientSideTick() {

		if (this.shouldBurn()) {
			this.setSecondsOnFire(1);
		}

		HitResult hitresult = ProjectileUtil.getHitResultOnMoveVector(this, this::canHitEntity);
		if (hitresult.getType() != HitResult.Type.MISS && !net.minecraftforge.event.ForgeEventFactory.onProjectileImpact(this, hitresult)) {
			this.onHit(hitresult);
		}

		this.checkInsideBlocks();
		Vec3 vec3 = this.getDeltaMovement();
		double d0 = this.getX();
		double d1 = this.getY() + vec3.y;
		double d2 = this.getZ();

		ProjectileUtil.rotateTowardsMovement(this, 0.2F);
		float f = this.getInertia();
		this.setDeltaMovement(vec3.add(this.xPower, this.yPower, this.zPower).scale((double)f));
		this.level().addParticle(this.getTrailParticle(), d0, d1 + 0.5D, d2, 0.0D, 0.0D, 0.0D);
		for (int i = 0; i < 2; i++) {
			this.level().addParticle(ParticleTypes.LARGE_SMOKE, d0, d1, d2, 0.0D, 0.0D, 0.0D);
		}
		this.setPos(d0, d1, d2);

		if (d1 > getOriginalY() + Config.Spells.FIRESPOUT.maxHeight.get()) {
			this.discard();
		}
	}

	@Override
	protected void onHit(HitResult hitResult) {
		super.onHit(hitResult);
		if (!WorldInfo.isClientSide(level())) {
			boolean flag = net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.level(), this.getOwner());
			this.level().explode((Entity)null, this.getX(), this.getY(), this.getZ(), (float)this.explosionPower, flag, flag ? Level.ExplosionInteraction.BLOCK : Level.ExplosionInteraction.NONE);
			this.discard();
		}
	}

	@Override
	protected void onHitEntity(EntityHitResult hitResult) {
		super.onHitEntity(hitResult);
		if (!WorldInfo.isClientSide(level())) {
			Entity target = hitResult.getEntity();
			Entity ownerEntity = this.getOwner();

//			DamageSource damageSource = new IndirectEntityDamageSource("firespout", this, ownerEntity).setIsFire().setProjectile();

			target.hurt(level().damageSources().source(ModDamageTypes.FIRESPOUT_SPELL), Config.Spells.FIRESPOUT.damage.get());
//			target.hurt(damageSource, Config.Spells.FIRESPOUT.damage.get());
			if (target instanceof LivingEntity) {
				this.doEnchantDamageEffects((LivingEntity)ownerEntity, target);
			}
		}
	}

	public void setItem(ItemStack stack) {
		if (!stack.is(Items.FIRE_CHARGE) || stack.hasTag()) {
			this.getEntityData().set(DATA_ITEM_STACK, Util.make(stack.copy(), (itemStack) -> {
				itemStack.setCount(1);
			}));
		}
	}

	protected ItemStack getItemRaw() {
		return this.getEntityData().get(DATA_ITEM_STACK);
	}

	@Override
	protected ParticleOptions getTrailParticle() {
		return ParticleTypes.FLAME;
	}

	@Override
	public ItemStack getItem() {
		ItemStack stack = this.getItemRaw();
		return stack.isEmpty() ? new ItemStack(Items.FIRE_CHARGE) : stack;
	}

	@Override
	protected void defineSynchedData() {
		this.getEntityData().define(DATA_ITEM_STACK, ItemStack.EMPTY);
		this.getEntityData().define(ORIGINAL_Y, -1000);
	}

	@Override
	public void addAdditionalSaveData(CompoundTag tag) {
		super.addAdditionalSaveData(tag);
		ItemStack itemStack = this.getItemRaw();
		if (!itemStack.isEmpty()) {
			tag.put("Item", itemStack.save(new CompoundTag()));
		}
		tag.putByte("ExplosionPower", (byte)this.explosionPower);

		if (getOriginalPosition()  != null) {
			tag.put(ORIGINAL_POSITION_TAG, this.newDoubleList(new double[] { getOriginalPosition().x, getOriginalPosition().y, getOriginalPosition().z }));
		}
	}

	@Override
	public void readAdditionalSaveData(CompoundTag tag) {
		super.readAdditionalSaveData(tag);
		ItemStack itemStack = ItemStack.of(tag.getCompound("Item"));
		this.setItem(itemStack);
		if (tag.contains("ExplosionPower", 99)) {
			this.explosionPower = tag.getByte("ExplosionPower");
		}
		if (tag.contains(ORIGINAL_POSITION_TAG, 9)) {
			ListTag listTag = tag.getList(ORIGINAL_POSITION_TAG, 6);
			if (listTag.size() == 3) {
				setOriginalPosition(new Vec3(listTag.getDouble(0),  listTag.getDouble(1), listTag.getDouble(2)));
			}
		}
	}

	public int getExplosionPower() {
		return explosionPower;
	}

	public void setExplosionPower(int explosionPower) {
		this.explosionPower = explosionPower;
	}

	/**
	 * Convenience method
	 * @return
	 */
	public int getOriginalY() {
		return this.entityData.get(ORIGINAL_Y);
	}

	public Vec3 getOriginalPosition() {
		return originalPosition;
	}

	public void setOriginalPosition(Vec3 originalPosition) {		
		this.originalPosition = originalPosition;
		this.entityData.set(ORIGINAL_Y, (int)Math.floor(originalPosition.y()));
	}
}
