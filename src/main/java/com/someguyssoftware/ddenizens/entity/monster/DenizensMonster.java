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

import com.someguyssoftware.ddenizens.config.Config;
import com.someguyssoftware.ddenizens.config.Config.CommonSpawnConfig;
import com.someguyssoftware.ddenizens.config.Config.IMobConfig;
import com.someguyssoftware.ddenizens.config.Config.INetherMobConfig;
import com.someguyssoftware.ddenizens.config.Config.NetherSpawnConfig;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BiomeTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;

import java.util.UUID;
import java.util.function.Predicate;

// TODO could implement vanilla TraceableEntity for getOwner()
/**
 * @author Mark Gottschling on Apr 13, 2022
 *
 */
public abstract class DenizensMonster extends Monster implements IDenizensMonster {

	private static final int UNDERGROUND_HEIGHT = 60;

	private MonsterSize size;

	public final Predicate<LivingEntity> playerNotOwner = (entity) -> {
		if (entity instanceof Player) {
			return getSummonedOwner() == null || !(getSummonedOwner() instanceof Player);
		}
		return true;
	};

	protected DenizensMonster(EntityType<? extends Monster> mob, Level level, MonsterSize size) {
		super(mob, level);
		setMonsterSize(size);
	}

	public static boolean checkDDMonsterSpawnRules(EntityType<? extends Mob> mob, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
		IMobConfig mobConfig = Config.Mobs.MOBS.get(EntityType.getKey(mob));		
		CommonSpawnConfig config = mobConfig.getSpawnConfig();
		return config.enabled.get()
				&& level.getDifficulty() != Difficulty.PEACEFUL
				&& isValidHeight(pos, config)
				&& isDarkEnoughToSpawn(level, pos, random)
				&& checkMobSpawnRules(mob, level, spawnType, pos, random);
	}

	/**
	 * Underground-only spawn rule: requires the position to be below the configured
	 * max height (64 for the Margoyle) AND unable to see the sky, so the mob only
	 * appears in caves / sewers / dungeons rather than on the dark surface.
	 */
	public static boolean checkDDMonsterUndergroundSpawnRules(EntityType<? extends Mob> mob, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
		IMobConfig mobConfig = Config.Mobs.MOBS.get(EntityType.getKey(mob));
		CommonSpawnConfig config = mobConfig.getSpawnConfig();
		return config.enabled.get()
				&& level.getDifficulty() != Difficulty.PEACEFUL
				&& isValidHeight(pos, config)
				&& !level.canSeeSky(pos)
				&& isDarkEnoughToSpawn(level, pos, random)
				&& checkMobSpawnRules(mob, level, spawnType, pos, random);
	}

	public static boolean checkDDMonsterCanSeeSkySpawnRules(EntityType<? extends Mob> mob, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
		Config.IMobConfig mobConfig = Config.Mobs.MOBS.get(EntityType.getKey(mob));
		Config.CommonSpawnConfig config = mobConfig.getSpawnConfig();
		return config.enabled.get()
				&& level.getDifficulty() != Difficulty.PEACEFUL
				&& isValidHeight(pos, config)
				&& level.canSeeSky(pos)
				&& checkMobSpawnRules(mob, level, spawnType, pos, random);
	}

	public static boolean isValidHeight(BlockPos pos, CommonSpawnConfig config) {
		return pos.getY() > config.minHeight.get() && pos.getY() < config.maxHeight.get();
	}
	
	public static boolean checkDDMonsterNetherSpawnRules(EntityType<? extends Mob> mob, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
		IMobConfig mobConfig = Config.Mobs.MOBS.get(EntityType.getKey(mob));
		NetherSpawnConfig config = ((INetherMobConfig)mobConfig).getNetherSpawn();
		return config.enabled.get()
				&& level.getDifficulty() != Difficulty.PEACEFUL
				&& isValidHeight(pos, config)
				&& isDarkEnoughToSpawn(level, pos, random)
				&& checkMobSpawnRules(mob, level, spawnType, pos, random);
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
	public static boolean checkIsNetherSpawnRules(EntityType<? extends Mob> mob, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
		if (level.getBiome(pos).is(BiomeTags.IS_NETHER) ) {
			return DenizensMonster.checkDDMonsterNetherSpawnRules(mob, level, spawnType, pos, random);
		}
		else {
			return DenizensMonster.checkDDMonsterSpawnRules(mob, level, spawnType, pos, random);
		}
	}

	@Override
	public void addAdditionalSaveData(CompoundTag tag) {
		super.addAdditionalSaveData(tag);

		if (this.getSummonedOwner() != null) {
			if (this.getSummonedOwner() instanceof Player) {
				tag.putBoolean(PLAYER_OWNER, true);
			} else {
				tag.putBoolean(PLAYER_OWNER, false);
			}
			tag.putUUID(SUMMONED_OWNER, getSummonedOwner().getUUID());
		}
	}

	@Override
	public void readAdditionalSaveData(CompoundTag tag) {
		super.readAdditionalSaveData(tag);

		if (tag.hasUUID(SUMMONED_OWNER)) {
			UUID uuid = tag.getUUID(SUMMONED_OWNER);

			LivingEntity owner = null;
			if (tag.contains(PLAYER_OWNER) && tag.getBoolean(PLAYER_OWNER)) {
				owner = this.level().getPlayerByUUID(uuid);
			} else {
				owner = (LivingEntity)((ServerLevel)this.level()).getEntity(uuid);
			}
			this.setSummonedOwner(owner);
		}
	}

	public void faceTarget(LivingEntity target) {
		if (target != null) {
			if (target.distanceToSqr(this) < 4096.0D) {
				double deltaX = target.getX() - getX();
				double deltaY = target.getZ() - getZ();
				setYRot(-((float) Mth.atan2(deltaX, deltaY)) * (180F / (float)Math.PI));
				yBodyRot = getYRot();
			}
		}
	}

	public MonsterSize getMonsterSize() {
		return size;
	}

	public void setMonsterSize(MonsterSize size) {
		this.size = size;
	}

}
