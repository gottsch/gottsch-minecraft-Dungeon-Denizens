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

import com.someguyssoftware.ddenizens.setup.Registration;

import net.minecraft.Util;
import net.minecraft.util.Mth;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

/**
 * 
 * @author Mark Gottschling on May 10, 2022
 *
 */
public class Rock extends AbstractDDHurtingProjectile implements ItemSupplier {
	private static final EntityDataAccessor<ItemStack> DATA_ITEM_STACK = SynchedEntityData.defineId(Rock.class, EntityDataSerializers.ITEM_STACK);

	/** downward acceleration applied to the rock's velocity each tick (ballistic arc). Tunable. */
	private static final double GRAVITY = 0.05D;

	/**
	 *
	 * @param level
	 * @return
	 */
	public Rock create(Level level) {
		return new Rock(Registration.ROCK_ENTITY_TYPE.get(), level);
	}

	/**
	 * 
	 * @param entityType
	 * @param level
	 */
	public Rock(EntityType<Rock> entityType, Level level) {
		super(entityType, level);
	}

	/**
	 * Lob the rock from its current position so it lands on the given target point.
	 * The throw is solved ballistically for the rock's per-tick {@link #GRAVITY}, so it
	 * always reaches the target (no undershoot); {@code horizontalSpeed} (blocks/tick)
	 * is the single tunable — lower is a slower, loftier, easier-to-dodge lob.
	 */
	public void lobTo(LivingEntity shooter, double targetX, double targetY, double targetZ, double horizontalSpeed) {
		double dx = targetX - this.getX();
		double dy = targetY - this.getY();
		double dz = targetZ - this.getZ();
		double horizontal = Math.sqrt(dx * dx + dz * dz);

		// flight time (ticks) chosen so the horizontal speed matches horizontalSpeed
		double t = Math.max(1.0D, horizontal / horizontalSpeed);
		double vx = dx / t;
		double vz = dz / t;
		// vertical speed needed to land on the target after t ticks of per-tick gravity
		double vy = (dy + GRAVITY * t * (t + 1.0D) / 2.0D) / t;

		this.setDeltaMovement(vx, vy, vz);
		this.setOwner(shooter);
		// arc comes purely from gravity in tick(); disable the constant-acceleration model
		this.xPower = 0.0D;
		this.yPower = 0.0D;
		this.zPower = 0.0D;

		// face the throw direction
		double horizVel = Math.sqrt(vx * vx + vz * vz);
		this.setYRot((float) (Mth.atan2(vx, vz) * (180D / Math.PI)));
		this.setXRot((float) (Mth.atan2(vy, horizVel) * (180D / Math.PI)));
		this.yRotO = this.getYRot();
		this.xRotO = this.getXRot();
	}

	@Override
	public void clientSideTick() {
		// true ballistic arc: apply gravity to the current velocity each tick.
		// (This method runs on both client and server, keeping the flight in sync.)
		this.setDeltaMovement(this.getDeltaMovement().add(0.0D, -GRAVITY, 0.0D));
		super.clientSideTick();
	}

	@Override
	protected float getInertia() {
		return 1.0F; // no air drag, so the ballistic arc lands where it's aimed
	}

	@Override
	public Packet<ClientGamePacketListener> getAddEntityPacket() {
		Entity owner = this.getOwner();
		int ownerId = owner == null ? 0 : owner.getId();
		// send the actual velocity (not the power vector) so the client reproduces the arc
		return new ClientboundAddEntityPacket(this.getId(), this.getUUID(),
				this.getX(), this.getY(), this.getZ(), this.getXRot(), this.getYRot(),
				this.getType(), ownerId, this.getDeltaMovement(), 0.0D);
	}

	@Override
	public void recreateFromPacket(ClientboundAddEntityPacket packet) {
		super.recreateFromPacket(packet);
		// rock is ballistic: ignore the parent's "power" reconstruction. The client
		// receives the launch velocity via lerpMotion from the packet above.
		this.xPower = 0.0D;
		this.yPower = 0.0D;
		this.zPower = 0.0D;
	}
	   
	@Override
	public boolean isOnFire() {
		return false;
	}

	@Override
	protected void onHitEntity(EntityHitResult result) {
		super.onHitEntity(result);
		Entity entity = result.getEntity();
		entity.hurt(level().damageSources().thrown(this, this.getOwner()), 4F);
	}

	@Override
	protected void onHit(HitResult result) {
		super.onHit(result);
		if (!this.level().isClientSide) {
			this.discard();
		}
	}
	
	@Override
	protected ParticleOptions getTrailParticle() {
		return ParticleTypes.WHITE_ASH;
	}
	
	protected ItemStack getItemRaw() {
		return this.getEntityData().get(DATA_ITEM_STACK);
	}
	
	@Override
	public ItemStack getItem() {
		ItemStack stack = this.getItemRaw();
		return stack.isEmpty() ? new ItemStack(Registration.ROCK_ITEM.get()) : stack;
	}

	public void setItem(ItemStack stack) {
		if (!stack.is(Registration.ROCK_ITEM.get()) || stack.hasTag()) {
			this.getEntityData().set(DATA_ITEM_STACK, Util.make(stack.copy(), (itemStack) -> {
				itemStack.setCount(1);
			}));
		}
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
}
