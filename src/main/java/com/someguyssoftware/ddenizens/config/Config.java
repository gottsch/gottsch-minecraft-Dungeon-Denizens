/*
 * This file is part of  Dungeon Denizens.
 * Copyright (c) 2021 Mark Gottschling (gottsch)
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
package com.someguyssoftware.ddenizens.config;

import com.someguyssoftware.ddenizens.DD;
import mod.gottsch.forge.gottschcore.config.AbstractConfig;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.commons.lang3.tuple.Pair;

/**
 * As of v2.0 all per-mob spawn/behavior config and the spell tuning have moved to the
 * {@code gmm:mob_config} datapack codec (spell projectiles now live in gmm and read their tuning
 * from {@code data/ddenizens/gmm/mob_config/*.json}). What remains here is logging.
 *
 * @author Mark Gottschling on Apr 25, 2022
 *
 */
@EventBusSubscriber(modid = DD.MODID, bus = EventBusSubscriber.Bus.MOD)
public final class Config extends AbstractConfig {

	public static ForgeConfigSpec COMMON_CONFIG;

	public static Config instance = new Config();

	public static final ForgeConfigSpec COMMON_SPEC;
	public static final CommonConfig COMMON;

	static {
		final Pair<CommonConfig, ForgeConfigSpec> commonSpecPair = new ForgeConfigSpec.Builder()
				.configure(CommonConfig::new);
		COMMON_SPEC = commonSpecPair.getRight();
		COMMON = commonSpecPair.getLeft();
	}

	/**
	 *
	 */
	public static void register() {
		registerCommonConfig();
	}

	private static void registerCommonConfig() {
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, COMMON_SPEC);
	}

	/*
	 *
	 */
	public static class CommonConfig {
		public static Logging logging;
		public CommonConfig(ForgeConfigSpec.Builder builder) {
			logging = new Logging(builder);
		}
	}

	@Override
	public String getLogsFolder() {
		return CommonConfig.logging.folder.get();
	}

	@Override
	public String getLogSize() {
		return CommonConfig.logging.size.get();
	}

	@Override
	public String getLoggingLevel() {
		return CommonConfig.logging.level.get();
	}
}
