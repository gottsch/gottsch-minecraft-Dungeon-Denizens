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

import com.google.common.collect.Maps;
import com.someguyssoftware.ddenizens.DD;
import com.someguyssoftware.ddenizens.setup.Registration;
import mod.gottsch.forge.gottschcore.config.AbstractConfig;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.*;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author Mark Gottschling on Apr 25, 2022
 *
 */
@EventBusSubscriber(modid = DD.MODID, bus = EventBusSubscriber.Bus.MOD)
public final class Config extends AbstractConfig {
	protected static final ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();

	public static final String CATEGORY_DIV = "##############################";
	public static final String UNDERLINE_DIV = "------------------------------";

	public static final double MIN_HEALTH = 1D;
	public static final double MAX_HEALTH = 1024D;
	public static final int UNDERGROUND_HEIGHT = 60;
	public static final int MIN_HEIGHT = -64;
	public static final int MAX_HEIGHT = 319;

	public static ForgeConfigSpec COMMON_CONFIG;
//	public static Logging LOGGING;
	
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
		registerServerConfigs();
		registerCommonConfig();
	}

	private static void registerCommonConfig() {
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, COMMON_SPEC);
	}

	/**
	 * TODO change this to SERVER SPEC
	 */
	private static void registerServerConfigs() {
		ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
//		LOGGING = new Logging(BUILDER);
//		General.register(BUILDER);
		Mobs.register(BUILDER);
		Spells.register(BUILDER);
		ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, BUILDER.build());
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

//	public static class General {
//		public static Integration INTEGRATION;
//
//		public static void register(ForgeConfigSpec.Builder builder) {
//			INTEGRATION = new Integration(builder);
//		}
//	}

	/*
	 *
	 */
//	public static class Integration {
//		public BooleanValue enableTreasure2;
//
//		public Integration(ForgeConfigSpec.Builder builder) {
//			builder.comment(CATEGORY_DIV, " Integration properties.", CATEGORY_DIV).push("integration");
//
//			this.enableTreasure2 = builder
//					.comment(" Enable Treasure2 integration.")
//					.define("enabled", true);
//
//			builder.pop();
//		}
//	}

	public static class CommonSpawnConfig {
		public BooleanValue enabled;

		public IntValue minHeight;
		public IntValue maxHeight;
		
		public CommonSpawnConfig() {}
		
		public CommonSpawnConfig(Builder builder, boolean enabled, int weight, int minSpawn, int maxSpawn, int minHeight, int maxHeight) {
			configure(builder, enabled, weight, minSpawn, maxSpawn, minHeight, maxHeight);
		}

		/*
		 * sets the property values with a push()/pop()
		 */
		public void configure(ForgeConfigSpec.Builder builder, boolean enabled, int weight, int minSpawn, int maxSpawn,
				int minHeight, int maxHeight) {

			this.enabled = builder
					.comment(" Enable/disable the mob.")
					.define("enabled", enabled	);

			this.minHeight = builder
					.comment(" Minimum world height for spawning.")
					.defineInRange("minHeight", minHeight, MIN_HEIGHT, MAX_HEIGHT);

			this.maxHeight = builder
					.comment(" Maximum world height for spawning.")
					.defineInRange("maxHeight", maxHeight, MIN_HEIGHT, MAX_HEIGHT);
		}
	}
	
	/*
	 * 
	 */
	public static class NetherSpawnConfig extends CommonSpawnConfig {
		public NetherSpawnConfig(ForgeConfigSpec.Builder builder, boolean enabled, int weight, int minSpawn, int maxSpawn,
				int minHeight, int maxHeight) {
			builder.push("nether_spawning");
			configure(builder, enabled, weight, minSpawn, maxSpawn, minHeight, maxHeight);
			builder.pop();
		}
	}

	/*
	 * 
	 */
	public static class ParalysisSpellConfig {
		public IntValue damage;
		public IntValue duration;

		public ParalysisSpellConfig(ForgeConfigSpec.Builder builder) {
			builder.comment(CATEGORY_DIV, " Paralysis spell properties.", CATEGORY_DIV).push("paralysis");				

			damage = builder
					.comment(" The amount of damage the spell inflicts (this is in addition to the slowness/paralysis).")
					.defineInRange("damage", 2, 1, Integer.MAX_VALUE);

			duration = builder
					.comment(" The length of time in ticks that the spell lasts for.")
					.defineInRange("duration", 200, 1, Integer.MAX_VALUE);

			builder.pop();
		}		
	}

	public static class HarmSpellConfig {
		public IntValue damage;

		public HarmSpellConfig(ForgeConfigSpec.Builder builder) {
			builder.comment(CATEGORY_DIV, " Shadowlord spell properties.", CATEGORY_DIV).push("shadowlord_spell");				

			damage = builder
					.comment(" The amount of damage the spell inflicts.")
					.defineInRange("damage", 6, 1, Integer.MAX_VALUE);

			builder.pop();
		}
	}

	public static class FirespoutSpellConfig {
		public IntValue explosionRadius;
		public IntValue damage;
		public IntValue maxHeight;

		public FirespoutSpellConfig(ForgeConfigSpec.Builder builder) {
			builder.comment(CATEGORY_DIV, " Firespout spell properties.", CATEGORY_DIV).push("firespout");				

			explosionRadius = builder
					.comment(" The radius of the explosion.")
					.defineInRange("explosionRadius", 1, 1, 10);

			damage = builder
					.comment(" The amount of damage the spell inflicts (this is fire damage, not explosion damage).")
					.defineInRange("damage", 6, 1, Integer.MAX_VALUE);

			maxHeight = builder
					.comment(" Maximum height in blocks that a firespout can reach.")
					.defineInRange("damage", 5, 1, 20);

			builder.pop();
		}
	}

	public static class DisintegrateSpellConfig {
		public DoubleValue damage;

		public DisintegrateSpellConfig(ForgeConfigSpec.Builder builder) {
			builder.comment(CATEGORY_DIV, " Disintegrate spell properties.", CATEGORY_DIV).push("disintegrate_spell");

			damage = builder
					.comment(" The amount of damage the spell inflicts.")
					.defineInRange("damage", 8.0, 1, Integer.MAX_VALUE);
		}
	}

	public static class Spells {
		public static ParalysisSpellConfig PARALYSIS;
		public static HarmSpellConfig HARM;
		public static FirespoutSpellConfig FIRESPOUT;

		public static DisintegrateSpellConfig DISINTEGRATE;

		public static void register(ForgeConfigSpec.Builder builder) {
			PARALYSIS = new ParalysisSpellConfig(builder);
			HARM = new HarmSpellConfig(builder);
			FIRESPOUT = new FirespoutSpellConfig(builder);
			DISINTEGRATE = new DisintegrateSpellConfig(builder);
		}
	}

	public static class Mobs {
		public static HeadlessConfig HEADLESS;
		public static OrcConfig ORC;
		public static GhoulConfig GHOUL;
		public static BoulderConfig BOULDER;
		public static ShadowConfig SHADOW;
		public static BeholderConfig BEHOLDER;
		public static DeathTyrantConfig DEATH_TYRANT;
		public static GazerConfig GAZER;
		public static SpectatorConfig SPECTATOR;
		public static ShadowlordConfig SHADOWLORD;
		public static DaemonConfig DAEMON;
		public static SkeletonWarriorConfig SKELETON_WARRIOR;
		public static WingedSkeletonConfig WINGED_SKELETON;
		public static FossilizedSkeletonConfig FOSSILIZED_SKELETON;
		public static IronSkeletonConfig IRON_SKELETON;
		public static MagmaSkeletonConfig MAGMA_SKELETON;

		// TODO create a General Config is specific isn't found, use it. For things like spawn height
		public static Map<ResourceLocation, IMobConfig> MOBS = Maps.newHashMap();

		public static void register(ForgeConfigSpec.Builder builder) {
			HEADLESS = new HeadlessConfig(builder);
			ORC = new OrcConfig(builder);
			GHOUL = new GhoulConfig(builder);
			BOULDER = new BoulderConfig(builder);
			SHADOW = new ShadowConfig(builder);
			BEHOLDER = new BeholderConfig(builder);
			DEATH_TYRANT = new DeathTyrantConfig(builder);
			GAZER = new GazerConfig(builder);
			SPECTATOR = new SpectatorConfig(builder);
			SHADOWLORD = new ShadowlordConfig(builder);
			DAEMON = new DaemonConfig(builder);
			SKELETON_WARRIOR = new SkeletonWarriorConfig(builder);
			WINGED_SKELETON = new WingedSkeletonConfig(builder);
			FOSSILIZED_SKELETON = new FossilizedSkeletonConfig(builder);
			IRON_SKELETON = new IronSkeletonConfig(builder);
			MAGMA_SKELETON = new MagmaSkeletonConfig(builder);

			MOBS.put(new ResourceLocation(DD.MODID, Registration.HEADLESS), HEADLESS);
			MOBS.put(new ResourceLocation(DD.MODID, Registration.ORC), ORC);
			MOBS.put(new ResourceLocation(DD.MODID, Registration.GHOUL), GHOUL);
			MOBS.put(new ResourceLocation(DD.MODID, Registration.BOULDER), BOULDER);
			MOBS.put(new ResourceLocation(DD.MODID, Registration.SHADOW), SHADOW);
			MOBS.put(new ResourceLocation(DD.MODID, Registration.BEHOLDER), BEHOLDER);
			MOBS.put(new ResourceLocation(DD.MODID, Registration.DEATH_TYRANT), DEATH_TYRANT);
			MOBS.put(new ResourceLocation(DD.MODID, Registration.GAZER), GAZER);
			MOBS.put(new ResourceLocation(DD.MODID, Registration.SPECTATOR), SPECTATOR);
			MOBS.put(new ResourceLocation(DD.MODID, Registration.SHADOWLORD), SHADOWLORD);
			MOBS.put(new ResourceLocation(DD.MODID, Registration.DAEMON), DAEMON);
			MOBS.put(new ResourceLocation(DD.MODID, Registration.SKELETON_WARRIOR), SKELETON_WARRIOR);
			MOBS.put(new ResourceLocation(DD.MODID, Registration.WINGED_SKELETON), WINGED_SKELETON);
			MOBS.put(new ResourceLocation(DD.MODID, Registration.FOSSILIZED_SKELETON), FOSSILIZED_SKELETON);
			MOBS.put(new ResourceLocation(DD.MODID, Registration.IRON_SKELETON), IRON_SKELETON);
			MOBS.put(new ResourceLocation(DD.MODID, Registration.MAGMA_SKELETON), MAGMA_SKELETON);

		}
	}

	public static interface IMobConfig {
		public CommonSpawnConfig getSpawnConfig();
	}

	public static interface INetherMobConfig {
		public NetherSpawnConfig getNetherSpawn();		
	}

	public static abstract class MobConfig implements IMobConfig {
		public CommonSpawnConfig spawnConfig;
		public CommonSpawnConfig getSpawnConfig() {
			return spawnConfig;
		}
	}

	public static abstract class NetherMobConfig implements IMobConfig, INetherMobConfig {
		public CommonSpawnConfig spawnConfig;
		public NetherSpawnConfig netherSpawnConfig;
		public CommonSpawnConfig getSpawnConfig() {
			return spawnConfig;
		}
		public NetherSpawnConfig getNetherSpawn() {
			return netherSpawnConfig;
		}
	}


	/*
	 *
	 */
	public static class HeadlessConfig extends MobConfig {
		// headless specific
		public ConfigValue<List<? extends String>> injuredAlertOthersList;
		public ConfigValue<List<? extends String>> targetsAlertOthersList;

		public HeadlessConfig(ForgeConfigSpec.Builder builder) {
			builder.comment(CATEGORY_DIV, " Headless properties.", CATEGORY_DIV).push(Registration.HEADLESS);				

			spawnConfig = new CommonSpawnConfig(builder, true, 40, 1, 2, MIN_HEIGHT, MAX_HEIGHT); //,
			//		new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),  Arrays.asList(BiomeCategory.NETHER.getName(), BiomeCategory.THEEND.getName()));

			builder.pop();
		}
	}

	/*
	 *
	 */
	public static class OrcConfig extends MobConfig {
		public OrcConfig(ForgeConfigSpec.Builder builder) {
			builder.comment(CATEGORY_DIV, " Orc properties.", CATEGORY_DIV).push(Registration.ORC);
			spawnConfig = new CommonSpawnConfig(builder, true, 35, 1, 2, MIN_HEIGHT, MAX_HEIGHT);//,
//					new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), Arrays.asList(BiomeCategory.NETHER.getName(), BiomeCategory.THEEND.getName()));
			builder.pop();
		}
	}
	
	/*
	 * 
	 */
	public static class GhoulConfig extends MobConfig {
		// ghoul specific
		public DoubleValue healAmount;
		public BooleanValue canOpenDoors;

		public GhoulConfig(ForgeConfigSpec.Builder builder) {
			builder.comment(CATEGORY_DIV, " Ghoul properties.", CATEGORY_DIV).push(Registration.GHOUL);				

			spawnConfig = new CommonSpawnConfig(builder, true, 25, 1, 1,  MIN_HEIGHT, MAX_HEIGHT);//,

			healAmount = builder
					.comment(" The amount a ghoul can heal themselves when eating meat.")
					.defineInRange("healAmount", 4D, MIN_HEALTH, MAX_HEALTH);

			canOpenDoors = builder
					.comment(" Determines whether a ghoul open doors.")
					.define("canOpenDoors", true);

			builder.pop();
		}
	}

	/*
	 * 
	 */
	public static class BoulderConfig extends MobConfig {
		// boulder specific
		public BooleanValue despawn;

		public BoulderConfig(ForgeConfigSpec.Builder builder) {
			builder.comment(CATEGORY_DIV, " Boulder properties.", CATEGORY_DIV).push("boulder");				

			spawnConfig = new CommonSpawnConfig(builder, false, 35, 1, 1,  MIN_HEIGHT, 60);//,

			despawn = builder
					.comment(" Whether the mob despawns or is persistent.",
							" true = depsawns. Default.")
					.define("despawns", true);

			builder.pop();
		}
	}

	/*
	 * 
	 */
	public static class ShadowConfig extends NetherMobConfig {
		// shadow specific		
		public DoubleValue blindnessProbability;
		public IntValue blindnessDuration;
		public DoubleValue weaknessProbability;
		public IntValue weaknessDuration;

		public ShadowConfig(ForgeConfigSpec.Builder builder) {
			builder.comment(CATEGORY_DIV, " Shadow properties.", CATEGORY_DIV).push(Registration.SHADOW);				

			spawnConfig = new CommonSpawnConfig(builder, true, 30, 1, 2,  MIN_HEIGHT, 60);//,
//					new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), Arrays.asList(BiomeCategory.THEEND.getName()));

			netherSpawnConfig = new NetherSpawnConfig(builder, true, 5, 1, 2, MIN_HEIGHT, MAX_HEIGHT);

			blindnessProbability = builder
					.comment(" The probability that a Shadow will inflict blindness when striking a target. (if not wearing a golden helmet)")
					.defineInRange("blindnessProbability", 75.0, 0.0, 100.0);

			blindnessDuration = builder
					.comment(" The length of time blindness spell lasts (measured in ticks).")
					.defineInRange("blindnessDuration", 60, 1, Integer.MAX_VALUE);

			weaknessProbability = builder
					.comment(" The probability that a Shadow will inflict weakness when striking a target.")
					.defineInRange("weaknessProbability", 20.0, 0.0, 100.0);

			weaknessDuration = builder
					.comment(" The length of time weakness spell lasts (measured in ticks).")
					.defineInRange("weaknessDuration", 100, 1, Integer.MAX_VALUE);

			builder.pop();
		}
	}

	/*
	 * NOTE difference between cooldown time and charge time:
	 * Cooldown Time = time is incremented regardless of criteria ex. having a target.
	 * Charge Time = time is incremented depending on criteria ex. having a target and in line-of-sight.
	 */
	public static class BeholderConfig extends NetherMobConfig {
		// beholder specific
		public IntValue maxFloatHeight;
		public IntValue biteCooldownTime;
		public IntValue spellChargeTime;

		public IntValue summonCooldownTime;
		public IntValue minSummonSpawns;
		public IntValue maxSummonSpawns;

		public IntValue summonDaemonCooldownTime;
		public BooleanValue despawn;

		public BeholderConfig(ForgeConfigSpec.Builder builder) {
			builder.comment(CATEGORY_DIV, " Beholder properties.", CATEGORY_DIV).push(Registration.BEHOLDER);

			spawnConfig = new CommonSpawnConfig(builder, true, 15, 1, 1,  MIN_HEIGHT, UNDERGROUND_HEIGHT);//,

			netherSpawnConfig = new NetherSpawnConfig(builder, true, 10, 1, 1, MIN_HEIGHT, MAX_HEIGHT);

			maxFloatHeight = builder
					.comment("")
					.defineInRange("maxFloatHeight", 7, 1, 25);

			biteCooldownTime = builder
					.comment(" The cooldown time of a bite attack (measured in ticks).")
					.defineInRange("biteCooldownTime", 40, 1, Integer.MAX_VALUE);

			spellChargeTime = builder
					.comment(" The charge time of a paralysis spell attack (measured in ticks).")
					.defineInRange("spellChargeTime", 80, 1, Integer.MAX_VALUE);

			summonCooldownTime = builder
					.comment(" The cooldown time of a summon spell (measured in ticks).")
					.defineInRange("summonCooldownTime", 1200, 1, Integer.MAX_VALUE);

			minSummonSpawns = builder
					.comment(" Minimum spawn group size for summon spell.")
					.defineInRange("minSummonSpawns", 1, 0, Integer.MAX_VALUE);

			maxSummonSpawns = builder
					.comment(" Maximum spawn group size for summon spell.")
					.defineInRange("maxSummonSpawns", 1, 1, Integer.MAX_VALUE);

			summonDaemonCooldownTime = builder
					.comment(" The cooldown time of a summon daemon spell (measured in ticks).")
					.defineInRange("summonCooldownTime", 2400, 1, Integer.MAX_VALUE);

			despawn = builder
					.comment(" Whether the mob despawns or is persistent.",
							" true = depsawns. Default.")
					.define("despawns", true);

			builder.pop();
		}
	}

	public static class DeathTyrantConfig extends NetherMobConfig {
		public IntValue maxFloatHeight;
		// beholder specific
		public IntValue biteCooldownTime;
		// TODO rename all instance of summonCooldownTime to summonChargeTime
		public IntValue spellChargeTime;
		public IntValue summonCooldownTime;
		public IntValue minSummonSpawns;
		public IntValue maxSummonSpawns;
		public IntValue summonDaemonCooldownTime;
		public BooleanValue despawn;

		public DeathTyrantConfig(ForgeConfigSpec.Builder builder) {
			builder.comment(CATEGORY_DIV, " Death Tyrant properties.", CATEGORY_DIV).push(Registration.DEATH_TYRANT);

			spawnConfig = new CommonSpawnConfig(builder, true, 15, 1, 1,  MIN_HEIGHT, UNDERGROUND_HEIGHT);//,

			netherSpawnConfig = new NetherSpawnConfig(builder, true, 10, 1, 1, MIN_HEIGHT, MAX_HEIGHT);

			maxFloatHeight = builder
					.comment("")
					.defineInRange("maxFloatHeight", 8, 1, 25);

			biteCooldownTime = builder
					.comment(" The cooldown time of a bite attack (measured in ticks).")
					.defineInRange("biteCooldownTime", 40, 1, Integer.MAX_VALUE);

			spellChargeTime = builder
					.comment(" The charge time of a general spell attack (measured in ticks).")
					.defineInRange("spellChargeTime", 80, 1, Integer.MAX_VALUE);

			summonCooldownTime = builder
					.comment(" The cooldown time of a general summon spell (measured in ticks).")
					.defineInRange("summonCooldownTime", 1200, 1, Integer.MAX_VALUE);

			minSummonSpawns = builder
					.comment(" Minimum spawn group size for summon spell.")
					.defineInRange("minSummonSpawns", 2, 0, Integer.MAX_VALUE);

			maxSummonSpawns = builder
					.comment(" Maximum spawn group size for summon spell.")
					.defineInRange("maxSummonSpawns", 5, 1, Integer.MAX_VALUE);

			summonDaemonCooldownTime = builder
					.comment(" The cooldown time of a summon daemon spell (measured in ticks).")
					.defineInRange("summonCooldownTime", 2400, 1, Integer.MAX_VALUE);

			despawn = builder
					.comment(" Whether the mob despawns or is persistent.",
							" true = depsawns. Default.")
					.define("despawns", true);

			builder.pop();
		}
	}

	// TODO most of these properties are common to Beholderkin - abstract out to class
	/*
	 * 
	 */
	public static class GazerConfig extends NetherMobConfig {
		// gazer specific
		public IntValue maxFloatHeight;
		public IntValue biteCooldownTime;
		public IntValue spellChargeTime;
		public IntValue summonCooldownTime;
		public IntValue minSummonSpawns;
		public IntValue maxSummonSpawns;

		public GazerConfig(ForgeConfigSpec.Builder builder) {
			builder.comment(CATEGORY_DIV, " Gazer properties.", CATEGORY_DIV).push(Registration.GAZER);				

			spawnConfig = new CommonSpawnConfig(builder, true, 25, 1, 1,  MIN_HEIGHT, UNDERGROUND_HEIGHT);//,
//					new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), Arrays.asList(BiomeCategory.THEEND.getName()));

			netherSpawnConfig = new NetherSpawnConfig(builder, true, 10, 1, 1, MIN_HEIGHT, MAX_HEIGHT);

			maxFloatHeight = builder
					.comment("")
					.defineInRange("maxFloatHeight", 5, 1, 25);

			biteCooldownTime = builder
					.comment(" The cooldown time of a bite attack (measured in ticks).")
					.defineInRange("biteCooldownTime", 20, 1, Integer.MAX_VALUE);

			spellChargeTime = builder
					.comment(" The charge time of a spell attack (measured in ticks).")
					.defineInRange("spellChargeTime", 80, 1, Integer.MAX_VALUE);

			summonCooldownTime = builder
					.comment(" The cooldown time of a summon spell (measured in ticks).")
					.defineInRange("summonCooldownTime", 2400, 1, Integer.MAX_VALUE);

			minSummonSpawns = builder
					.comment(" Minimum spawn group size for summon spell.")
					.defineInRange("minSummonSpawns", 1, 0, Integer.MAX_VALUE);

			maxSummonSpawns = builder
					.comment(" Maximum spawn group size for summon spell.")
					.defineInRange("maxSummonSpawns", 1, 1, Integer.MAX_VALUE);

			builder.pop();
		}
	}

	public static class SpectatorConfig extends NetherMobConfig {
		// gazer specific
		public IntValue maxFloatHeight;
		public IntValue biteCooldownTime;
		public IntValue spellChargeTime;

		public SpectatorConfig(ForgeConfigSpec.Builder builder) {
			builder.comment(CATEGORY_DIV, " Spectator properties.", CATEGORY_DIV).push(Registration.SPECTATOR);

			spawnConfig = new CommonSpawnConfig(builder, true, 25, 1, 1,  MIN_HEIGHT, UNDERGROUND_HEIGHT);//,

			netherSpawnConfig = new NetherSpawnConfig(builder, true, 10, 1, 1, MIN_HEIGHT, MAX_HEIGHT);

			maxFloatHeight = builder
					.comment("")
					.defineInRange("maxFloatHeight", 3, 1, 25);

			biteCooldownTime = builder
					.comment(" The cooldown time of a bite attack (measured in ticks).")
					.defineInRange("biteCooldownTime", 20, 1, Integer.MAX_VALUE);

			spellChargeTime = builder
					.comment(" The charge time of a spell attack (measured in ticks).")
					.defineInRange("spellChargeTime", 80, 1, Integer.MAX_VALUE);

			builder.pop();
		}
	}

	/*
	 * 
	 */
	public static class ShadowlordConfig extends NetherMobConfig {		
		// shadowlord specific		
		public IntValue harmChargeTime;
		public IntValue drainCooldownTime;
		public IntValue summonCooldownTime;
		public IntValue minSummonSpawns;
		public IntValue maxSummonSpawns;
		public IntValue summonDaemonCooldownTime;
		public DoubleValue summonDaemonProbability;
		public DoubleValue poisonProbability;
		public IntValue poisonDuration;
		public IntValue blindnessDuration;
		public BooleanValue despawn;

		public ShadowlordConfig(ForgeConfigSpec.Builder builder) {
			builder.comment(CATEGORY_DIV, " Shadowlord properties.", CATEGORY_DIV).push(Registration.SHADOWLORD);				

			spawnConfig = new CommonSpawnConfig(builder, true, 15, 1, 1,  MIN_HEIGHT, 20);//,
//					new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), Arrays.asList(BiomeCategory.THEEND.getName()));

			netherSpawnConfig = new NetherSpawnConfig(builder, true, 15, 1, 1,  MIN_HEIGHT, MAX_HEIGHT);

			harmChargeTime = builder
					.comment(" The charge time of a harm spell attack (measured in ticks).")
					.defineInRange("harmChargeTime", 50, 1, Integer.MAX_VALUE);

			drainCooldownTime = builder
					.comment(" The cooldown time of a drain spell (measured in ticks).")
					.defineInRange("drainCooldownTime", 400, 1, Integer.MAX_VALUE);

			summonCooldownTime = builder
					.comment(" The cooldown time of a summon spell (measured in ticks).")
					.defineInRange("summonCooldownTime", 1200, 1, Integer.MAX_VALUE);

			minSummonSpawns = builder
					.comment(" Minimum spawn group size for summon spell.")
					.defineInRange("minSummonSpawns", 1, 0, Integer.MAX_VALUE);

			maxSummonSpawns = builder
					.comment(" Maximum spawn group size for summon spell.")
					.defineInRange("maxSummonSpawns", 2, 1, Integer.MAX_VALUE);

			summonDaemonCooldownTime = builder
					.comment(" The cooldown time of a summon daemon spell (measured in ticks).")
					.defineInRange("summonCooldownTime", 2400, 1, Integer.MAX_VALUE);

			summonDaemonProbability = builder
					.comment(" The probability that a Shadowlord will be able to summon a daemon.")
					.defineInRange("summonDaemonProbability", 25.0, 0.0, 100.0);

			poisonProbability = builder
					.comment(" The probability that a Shadowlord will inflict poison when striking a target.")
					.defineInRange("poisonProbability", 25.0, 0.0, 100.0);

			poisonDuration = builder
					.comment(" The length of time poisoning lasts (measured in ticks).")
					.defineInRange("poisonDuration", 200, 1, Integer.MAX_VALUE);

			blindnessDuration = builder
					.comment(" The length of time blindness from Aura of Blindess lasts (measured in ticks).")
					.defineInRange("blindnessDuration", 40, 1, Integer.MAX_VALUE);

			despawn = builder
					.comment(" Whether the mob despawns or is persistent.",
							" true = depsawns. Default.")
					.define("despawns", true);

			builder.pop();
		}
	}

	/*
	 * 
	 */
	public static class DaemonConfig extends NetherMobConfig {		
		// daemon specific		
		public IntValue firespoutCooldownTime;
		public IntValue firespoutMaxDistance;
		public IntValue summonedLifespan;
		public BooleanValue despawn;


		public DaemonConfig(ForgeConfigSpec.Builder builder) {
			builder.comment(CATEGORY_DIV, " Daemon properties.", CATEGORY_DIV).push(Registration.DAEMON);				

			spawnConfig = new CommonSpawnConfig(builder, true, 1, 1, 1,  MIN_HEIGHT, 0);//,
//					new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), Arrays.asList(BiomeCategory.THEEND.getName()));

			netherSpawnConfig = new NetherSpawnConfig(builder, true, 10, 1, 1,  MIN_HEIGHT, MAX_HEIGHT);

			firespoutCooldownTime = builder
					.comment(" The cooldown time of a firespout spell (measured in ticks).")
					.defineInRange("firespoutCooldownTime", 200, 1, Integer.MAX_VALUE);

			firespoutMaxDistance = builder
					.comment(" The max distance (in blocks) that firespout spell can travel.")
					.defineInRange("firespoutMaxDistance", 10, 3, 20);

			summonedLifespan = builder
					.comment(" The duration in ticks that a summoned daemon can remain before returning to whence it came.")
					.defineInRange("summonedLifespan", 1200
							, 600, Integer.MAX_VALUE);

			despawn = builder
					.comment(" Whether the mob despawns or is persistent.",
							" true = depsawns. Default.")
							.define("despawns", true);

			builder.pop();
		}
	}

	public static class SkeletonWarriorConfig extends MobConfig {
		public SkeletonWarriorConfig(ForgeConfigSpec.Builder builder) {
			builder.comment(CATEGORY_DIV, " Skeleton Warrior properties.", CATEGORY_DIV).push(Registration.SKELETON_WARRIOR);
			spawnConfig = new CommonSpawnConfig(builder, true, 50, 1, 2, MIN_HEIGHT, MAX_HEIGHT);//,
			builder.pop();
		}
	}

	public static class WingedSkeletonConfig extends MobConfig {
		public WingedSkeletonConfig(ForgeConfigSpec.Builder builder) {
			builder.comment(CATEGORY_DIV, " Winged Skeleton properties.", CATEGORY_DIV).push(Registration.WINGED_SKELETON);
			spawnConfig = new CommonSpawnConfig(builder, true, 50, 1, 2, MIN_HEIGHT, MAX_HEIGHT);//,
			builder.pop();
		}
	}

	public static class FossilizedSkeletonConfig extends MobConfig {
		public FossilizedSkeletonConfig(ForgeConfigSpec.Builder builder) {
			builder.comment(CATEGORY_DIV, " Fossilized Skeleton properties.", CATEGORY_DIV).push(Registration.FOSSILIZED_SKELETON);
			spawnConfig = new CommonSpawnConfig(builder, true, 30, 1, 2, MIN_HEIGHT, MAX_HEIGHT);//,
			builder.pop();
		}
	}

	public static class IronSkeletonConfig extends MobConfig {
		public IronSkeletonConfig(ForgeConfigSpec.Builder builder) {
			builder.comment(CATEGORY_DIV, " Iron Skeleton properties.", CATEGORY_DIV).push(Registration.IRON_SKELETON);
			spawnConfig = new CommonSpawnConfig(builder, true, 30, 1, 1, MIN_HEIGHT, MAX_HEIGHT);//,
			builder.pop();
		}
	}

	public static class MagmaSkeletonConfig extends NetherMobConfig {
		public MagmaSkeletonConfig(ForgeConfigSpec.Builder builder) {
			builder.comment(CATEGORY_DIV, " Magma Skeleton properties.", CATEGORY_DIV).push(Registration.IRON_SKELETON);
			spawnConfig = new CommonSpawnConfig(builder, true, 30, 1, 1, MIN_HEIGHT, MAX_HEIGHT);//,
			netherSpawnConfig = new NetherSpawnConfig(builder, true, 10, 1, 1,  MIN_HEIGHT, MAX_HEIGHT);

			builder.pop();
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
