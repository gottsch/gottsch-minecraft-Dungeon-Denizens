/*
 * This file is part of  Dungeon Denizens.
 * Copyright (c) 2026 Mark Gottschling (gottsch)
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
package com.someguyssoftware.ddenizens.util;

import mod.gottsch.forge.gmm.core.config.MobConfig;
import mod.gottsch.forge.gmm.core.config.MobConfigHelper;
import mod.gottsch.forge.gmm.core.config.SkyVisibility;
import mod.gottsch.forge.gmm.core.entity.monster.Boulder;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;

import java.util.function.Predicate;

/**
 * DD-side spawn-rule utility: the "Config fallback bridge" for spawn-gating predicates, used by
 * {@code CommonSetup} spawn-placement registrations. GMM's mob classes stay spawn-agnostic; the
 * consumer (DD) owns the predicates.
 * <p>
 * All standard mobs share the single data-driven {@link #checkSpawnRules} predicate — the sky and
 * darkness requirements that used to be encoded in <em>which method</em> was registered are now
 * {@code gmm:mob_config} codec fields ({@code skyVisibility} / {@code requiresDarkness}). Only the
 * two mobs with genuinely bespoke gates keep their own composers ({@link #checkBoulderSpawnRules},
 * {@link #checkMagmaSkeletonSpawnRules}).
 * <p>
 * Formerly the {@code DenizensMonster} / {@code IDenizensMonster} static bridges (both were reduced
 * to pure statics once every DD mob migrated to gmm; consolidated + renamed here).
 *
 * @author Mark Gottschling on July 1, 2026
 */
public final class SpawnRulesUtil {

	private SpawnRulesUtil() {}

	/** Shared by DD's {@code EntityJoinLevelEvent} goal injections: avoid/target an active Boulder. */
	public static final Predicate<LivingEntity> avoidBoulder = (entity) -> {
		if (entity instanceof Boulder) {
			return ((Boulder) entity).isActive();
		}
		return false;
	};

	/**
	 * The single data-driven natural-spawn predicate for all standard mobs. Dispatches on biome
	 * (nether vs overworld settings; falls back to overworld when a mob defines no {@code netherSpawn},
	 * so behavior is identical to the old overworld-only path for those mobs), then applies the
	 * codec-driven gates: enabled, difficulty, height band, sky-visibility, and darkness.
	 */
	public static boolean checkSpawnRules(EntityType<? extends Mob> mob, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
		boolean nether = level.getBiome(pos).is(BiomeTags.IS_NETHER);
		MobConfig.SpawnSettings spawn = spawnSettings(level, mob, nether);
		if (!spawn.enabled() || level.getDifficulty() == Difficulty.PEACEFUL || !isValidHeight(pos, spawn)) {
			return false;
		}
		if (spawn.skyVisibility() == SkyVisibility.MUST_SEE && !level.canSeeSky(pos)) {
			return false;
		}
		if (spawn.skyVisibility() == SkyVisibility.MUST_NOT_SEE && level.canSeeSky(pos)) {
			return false;
		}
		if (spawn.requiresDarkness() && !Monster.isDarkEnoughToSpawn(level, pos, random)) {
			return false;
		}
		return Monster.checkMobSpawnRules(mob, level, spawnType, pos, random);
	}

	/**
	 * Resolves a mob's spawn-gating settings from the {@code gmm:mob_config} datapack entry (keyed by
	 * EntityType id). Every DD mob ships a JSON, so the entry is normally present; a mob without one
	 * falls back to {@link MobConfig#DEFAULT} (enabled, full height band, {@code ANY} sky,
	 * darkness-required) — the correct default for a plain monster.
	 */
	public static MobConfig.SpawnSettings spawnSettings(LevelAccessor level, EntityType<? extends Mob> mob, boolean nether) {
		ResourceLocation id = EntityType.getKey(mob);
		return MobConfigHelper.find(level, id).orElse(MobConfig.DEFAULT).spawnFor(nether);
	}

	public static boolean isValidHeight(BlockPos pos, MobConfig.SpawnSettings spawn) {
		return pos.getY() > spawn.minHeight() && pos.getY() < spawn.maxHeight();
	}

	/**
	 * Bespoke gate for gmm's MagmaSkeleton (reads DD's spawn bridge). Nether: enabled + not-peaceful
	 * + valid height only (deliberately skips darkness + base mob rules). Overworld: the standard
	 * predicate plus lava nearby.
	 */
	public static boolean checkMagmaSkeletonSpawnRules(EntityType<? extends Mob> mob, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
		if (level.getBiome(pos).is(BiomeTags.IS_NETHER)) {
			MobConfig.SpawnSettings spawn = spawnSettings(level, mob, true);
			return spawn.enabled()
					&& level.getDifficulty() != Difficulty.PEACEFUL
					&& isValidHeight(pos, spawn);
		} else {
			boolean isLavaNear = !level.getBlockStates(mob.getAABB(pos.getX(), pos.getY(), pos.getZ()).inflate(10D, 2D, 10D))
					.filter(bs -> bs.is(Blocks.LAVA)).toList().isEmpty();
			return checkSpawnRules(mob, level, spawnType, pos, random) && isLavaNear;
		}
	}

	/**
	 * Drowned-style gate for aquatic mobs (AlligatorGar, and any future water mob — piranha, croc,
	 * etc.). Like vanilla {@code Drowned.checkDrownedSpawnRules}, it requires the mob to be submerged
	 * in a genuine body of water — water in the block below (so it isn't sitting on the surface film)
	 * and water at the spawn position itself — and, by default, spawning in the dark. This replaces
	 * the standard ground-mob path ({@link #checkSpawnRules}), whose closing
	 * {@code Monster.checkMobSpawnRules} needs a solid valid-spawn surface below and therefore never
	 * fires inside a water column.
	 * <p>
	 * Rarity is governed by the {@code add_spawns} biome-modifier weight (kept low), not an internal
	 * random gate. Enabled / height band / darkness are codec-driven ({@code gmm:mob_config}); flip
	 * {@code requiresDarkness} to {@code false} there for daytime river spawns.
	 */
	public static boolean checkWaterMobSpawnRules(EntityType<? extends Mob> mob, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
		// submerged in a real body of water (water below + at the position), like the Drowned
		if (!level.getFluidState(pos.below()).is(FluidTags.WATER) || !level.getFluidState(pos).is(FluidTags.WATER)) {
			return false;
		}
		MobConfig.SpawnSettings spawn = spawnSettings(level, mob, false);
		if (!spawn.enabled() || level.getDifficulty() == Difficulty.PEACEFUL || !isValidHeight(pos, spawn)) {
			return false;
		}
		// like the Drowned, gate on darkness (tunable via the codec's requiresDarkness)
		if (spawn.requiresDarkness() && !Monster.isDarkEnoughToSpawn(level, pos, random)) {
			return false;
		}
		return true;
	}

	/**
	 * Bespoke gate for gmm's Boulder (reads DD's spawn bridge). Valid height OR mountain biome, plus
	 * vanilla's any-light check. NOTE: intentionally does not check {@code enabled} (preserved from
	 * the original — Boulder's natural spawning is governed by its {@code add_spawns} biome modifiers).
	 */
	public static boolean checkBoulderSpawnRules(EntityType<? extends Monster> mob, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
		MobConfig.SpawnSettings spawn = spawnSettings(level, mob, false);
		return ((pos.getY() > spawn.minHeight() && pos.getY() < spawn.maxHeight()) || level.getBiome(pos).is(BiomeTags.IS_MOUNTAIN))
				&& Monster.checkAnyLightMonsterSpawnRules(mob, level, spawnType, pos, random);
	}

	/**
	 * Bespoke gate for gmm's GraveZombie (reads DD's spawn bridge). It only ever digs into actual
	 * dirt ({@code minecraft:dirt} — dirt/grass/podzol/coarse dirt/mycelium/rooted dirt, never stone
	 * or other terrain — see the class doc), so natural spawning requires the same of the block it's
	 * standing on; otherwise identical to the standard {@link #checkSpawnRules}.
	 */
	public static boolean checkGraveZombieSpawnRules(EntityType<? extends Mob> mob, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
		return level.getBlockState(pos.below()).is(BlockTags.DIRT) && checkSpawnRules(mob, level, spawnType, pos, random);
	}
}
