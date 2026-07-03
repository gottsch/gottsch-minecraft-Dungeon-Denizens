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
package com.someguyssoftware.ddenizens.setup;

import com.someguyssoftware.ddenizens.DD;
import com.someguyssoftware.ddenizens.config.Config;
import com.someguyssoftware.ddenizens.entity.ModEntities;
// v2.0: every DD mob now lives in gottsch's Monster Manual (gmm); the DD entity.monster package is empty
import mod.gottsch.forge.gmm.core.entity.monster.ghoul.Ghoul;
import mod.gottsch.forge.gmm.core.entity.monster.ghoul.SewerGhoul;
import mod.gottsch.forge.gmm.core.entity.monster.Rat;
import mod.gottsch.forge.gmm.core.entity.monster.AlligatorGar;
import mod.gottsch.forge.gmm.core.entity.monster.Headless;
import mod.gottsch.forge.gmm.core.entity.monster.Orc;
import mod.gottsch.forge.gmm.core.entity.monster.Shadow;
import mod.gottsch.forge.gmm.core.entity.monster.skeleton.SkeletonWarrior;
import mod.gottsch.forge.gmm.core.entity.monster.gargoyle.Gargoyle;
import mod.gottsch.forge.gmm.core.entity.monster.gargoyle.Margoyle;

import mod.gottsch.forge.gmm.core.entity.projectile.Rock;
import mod.gottsch.forge.gmm.core.entity.projectile.ParalysisSpell;
import mod.gottsch.forge.gmm.core.entity.projectile.HarmSpell;
import mod.gottsch.forge.gmm.core.entity.projectile.DisintegrateSpell;
import mod.gottsch.forge.gmm.core.entity.projectile.DisarmSpell;
import mod.gottsch.forge.gmm.core.entity.monster.skeleton.BowSkeleton;
import mod.gottsch.forge.gmm.core.entity.monster.skeleton.IronSkeleton;
import mod.gottsch.forge.gmm.core.entity.monster.skeleton.MagmaSkeleton;
import mod.gottsch.forge.gmm.core.entity.monster.skeleton.FrostSkeleton;
import mod.gottsch.forge.gmm.core.entity.monster.skeleton.TaintedSkeleton;
import mod.gottsch.forge.gmm.core.entity.projectile.BoneShard;
import mod.gottsch.forge.gmm.core.entity.monster.skeleton.WingedSkeleton;
import mod.gottsch.forge.gmm.core.entity.monster.beholderkin.Beholder;
import mod.gottsch.forge.gmm.core.entity.monster.beholderkin.DeathTyrant;
import mod.gottsch.forge.gmm.core.entity.monster.beholderkin.Gazer;
import mod.gottsch.forge.gmm.core.entity.monster.beholderkin.Spectator;
import mod.gottsch.forge.gmm.core.entity.monster.Boulder;
import mod.gottsch.forge.gmm.core.entity.monster.Daemon;
import mod.gottsch.forge.gmm.core.entity.monster.Shadowlord;
import mod.gottsch.forge.gmm.core.entity.projectile.FireSpoutSpell;
import mod.gottsch.forge.gmm.core.entity.ai.goal.CastSpellGoal;
import mod.gottsch.forge.gottschcore.random.WeightedCollection;
import com.someguyssoftware.ddenizens.integrations.Integrations;
import com.someguyssoftware.ddenizens.item.ModItems;
import com.someguyssoftware.ddenizens.util.SpawnRulesUtil;
import mod.gottsch.forge.gottschcore.world.WorldInfo;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.monster.ZombieVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab.TabVisibility;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

import java.util.function.Predicate;

/**
 * Common event bus subscriber.
 * @author Mark Gottschling on Apr 2, 2022
 *
 */
@Mod.EventBusSubscriber(modid = DD.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CommonSetup {
	public static void init(final FMLCommonSetupEvent event) {
		Config.instance.addRollingFileAppender(DD.MODID);
		DD.LOGGER.debug("starting Dungeon Denizens");
		Integrations.registerTreasure2Integration();

		// gmm's Orc owns no projectile; supply DD's Rock as its thrown projectile. The throw goal
		// computes the spawn point (the orc's right hand); we create + ballistically lob the Rock.
		Orc.projectileLauncher = (shooter, target, x, y, z) -> {
			Rock rock = new Rock(ModEntities.ROCK_ENTITY_TYPE.get(), shooter.level());
			rock.setPos(x, y, z);
			rock.lobTo(shooter, target.getX(), target.getY(0.5D), target.getZ(), 0.8D);
			shooter.level().addFreshEntity(rock);
		};

		// gmm's spell projectiles register no items; supply DD's in-flight visual items consumer-side
		// (same static-hook pattern as Orc.projectileLauncher). FireSpout uses the vanilla fire charge.
		ParalysisSpell.itemSupplier = () -> ModItems.PARALYSIS_SPELL_ITEM.get();
		HarmSpell.itemSupplier = () -> ModItems.HARM_SPELL_ITEM.get();
		DisintegrateSpell.itemSupplier = () -> ModItems.DISINTEGRATE_SPELL_ITEM.get();
		DisarmSpell.itemSupplier = () -> ModItems.DISARM_SPELL_ITEM.get();
		Rock.itemSupplier = () -> ModItems.ROCK_ITEM.get();

		// All GMM mob ambient/step sounds now default to gmm's own GMMSounds (shipped in gmm); no DD
		// wiring needed. A consumer may still override per-mob, e.g. Gargoyle.ambientSound = () -> ...;

		// gmm's Beholderkin family owns no concrete spell/summon-target/sound; supply DD's via the
		// same static-hook pattern as Orc.projectileLauncher / Shadow.ambientSound.
		CastSpellGoal.SpellLauncher paralysisSpell = (caster, target, x, y, z) -> {
			ParalysisSpell spell = new ParalysisSpell(ModEntities.PARALYSIS_SPELL_ENTITY_TYPE.get(), caster.level());
			spell.init(caster, target.getX() - x, target.getY(0.5D) - y, target.getZ() - z);
			spell.setPos(x, y, z);
			caster.level().addFreshEntity(spell);
		};
		CastSpellGoal.SpellLauncher harmSpell = (caster, target, x, y, z) -> {
			HarmSpell spell = new HarmSpell(ModEntities.HARM_SPELL_ENTITY_TYPE.get(), caster.level());
			spell.init(caster, target.getX() - x, target.getY(0.5D) - y, target.getZ() - z);
			spell.setPos(x, y, z);
			caster.level().addFreshEntity(spell);
		};
		CastSpellGoal.SpellLauncher disintegrateSpell = (caster, target, x, y, z) -> {
			DisintegrateSpell spell = new DisintegrateSpell(ModEntities.DISINTEGRATE_SPELL_ENTITY_TYPE.get(), caster.level());
			spell.init(caster, target.getX() - x, target.getY(0.5D) - y, target.getZ() - z);
			spell.setPos(x, y, z);
			caster.level().addFreshEntity(spell);
		};
		CastSpellGoal.SpellLauncher disarmSpell = (caster, target, x, y, z) -> {
			DisarmSpell spell = new DisarmSpell(ModEntities.DISARM_SPELL_ENTITY_TYPE.get(), caster.level());
			spell.init(caster, target.getX() - x, target.getY(0.5D) - y, target.getZ() - z);
			spell.setPos(x, y, z);
			caster.level().addFreshEntity(spell);
		};

		WeightedCollection<Integer, CastSpellGoal.SpellLauncher> beholderSpells = new WeightedCollection<>();
		beholderSpells.add(3, paralysisSpell);
		beholderSpells.add(2, harmSpell);
		beholderSpells.add(1, disintegrateSpell);
		beholderSpells.add(1, disarmSpell);
		Beholder.spellCaster = beholderSpells;

		WeightedCollection<Integer, CastSpellGoal.SpellLauncher> gazerSpells = new WeightedCollection<>();
		gazerSpells.add(3, paralysisSpell);
		gazerSpells.add(1, harmSpell);
		Gazer.spellCaster = gazerSpells;

		DeathTyrant.spellCaster = paralysisSpell;
		Spectator.spellCaster = paralysisSpell;

		WeightedCollection<Double, EntityType<? extends Mob>> beholderMobs = new WeightedCollection<>();
		beholderMobs.add(60D, ModEntities.HEADLESS_ENTITY_TYPE.get());
		beholderMobs.add(40D, ModEntities.ORC_ENTITY_TYPE.get());
		beholderMobs.add(20D, ModEntities.SPECTATOR_TYPE.get());
		beholderMobs.add(20D, EntityType.BLAZE);
		Beholder.summonMobs = beholderMobs;
		Beholder.summonDaemon = ModEntities.DAEMON_ENTITY_TYPE.get();

		WeightedCollection<Double, EntityType<? extends Mob>> gazerMobs = new WeightedCollection<>();
		gazerMobs.add(33D, ModEntities.HEADLESS_ENTITY_TYPE.get());
		gazerMobs.add(33D, ModEntities.ORC_ENTITY_TYPE.get());
		gazerMobs.add(34D, EntityType.ZOMBIE);
		gazerMobs.add(20D, EntityType.VEX);
		Gazer.summonMobs = gazerMobs;

		WeightedCollection<Double, EntityType<? extends Mob>> deathTyrantMobs = new WeightedCollection<>();
		deathTyrantMobs.add(20D, EntityType.ZOMBIE);
		deathTyrantMobs.add(20D, EntityType.HUSK);
		deathTyrantMobs.add(20D, EntityType.SKELETON);
		deathTyrantMobs.add(60D, ModEntities.SKELETON_WARRIOR_TYPE.get());
		DeathTyrant.summonMobs = deathTyrantMobs;
		DeathTyrant.summonDaemon = ModEntities.DAEMON_ENTITY_TYPE.get();

		// gmm's Daemon owns no firespout projectile; supply DD's FireSpoutSpell consumer-side.
		Daemon.fireSpoutLauncher = (daemon, x, y, z, x2, y2, z2) -> {
			FireSpoutSpell spell = new FireSpoutSpell(ModEntities.FIRESPOUT_SPELL_ENTITY_TYPE.get(), daemon.level());
			spell.init(daemon, x, y, z, x2, y2, z2);
			daemon.level().addFreshEntity(spell);
		};

		// gmm's Shadowlord shares Shadow's Harm spell + summon lists + weapon.
		Shadowlord.spellCaster = harmSpell;

		WeightedCollection<Double, EntityType<? extends Mob>> shadowlordMobs = new WeightedCollection<>();
		shadowlordMobs.add(70D, ModEntities.SHADOW_ENTITY_TYPE.get());
		shadowlordMobs.add(30D, ModEntities.GHOUL_ENTITY_TYPE.get());
		Shadowlord.summonMobs = shadowlordMobs;
		Shadowlord.summonDaemon = ModEntities.DAEMON_ENTITY_TYPE.get();

		Shadowlord.weapon = ModItems.SHADOW_BLADE;

		// gmm's TaintedSkeleton owns no projectile; supply DD-registered BoneShard as its shrapnel.
		TaintedSkeleton.shardFactory = (shooter, level) -> new BoneShard(ModEntities.BONE_SHARD_ENTITY_TYPE.get(), shooter, level);
	}

	/**
	 * attach defined attributes to the entity.
	 * @param event
	 */
	@SubscribeEvent
	public static void onAttributeCreate(EntityAttributeCreationEvent event) {
		event.put(ModEntities.HEADLESS_ENTITY_TYPE.get(), Headless.createAttributes().build());
		event.put(ModEntities.ORC_ENTITY_TYPE.get(), Orc.createAttributes().build());
		event.put(ModEntities.GHOUL_ENTITY_TYPE.get(), Ghoul.createAttributes().build());
		event.put(ModEntities.SEWER_GHOUL_ENTITY_TYPE.get(), SewerGhoul.createAttributes().build());
		event.put(ModEntities.RAT_ENTITY_TYPE.get(), Rat.createAttributes().build());
		event.put(ModEntities.ALLIGATOR_GAR_ENTITY_TYPE.get(), AlligatorGar.createAttributes().build());
		event.put(ModEntities.BEHOLDER_ENTITY_TYPE.get(), Beholder.prepareAttributes().build());
		event.put(ModEntities.DEATH_TYRANT_TYPE.get(), DeathTyrant.prepareAttributes().build());
		event.put(ModEntities.GAZER_ENTITY_TYPE.get(), Gazer.prepareAttributes().build());
		event.put(ModEntities.SPECTATOR_TYPE.get(), Spectator.prepareAttributes().build());
		event.put(ModEntities.BOULDER_ENTITY_TYPE.get(), Boulder.createAttributes().build());
		event.put(ModEntities.SHADOW_ENTITY_TYPE.get(), Shadow.createAttributes().build());
		event.put(ModEntities.SHADOWLORD_ENTITY_TYPE.get(), Shadowlord.createAttributes().build());
		event.put(ModEntities.DAEMON_ENTITY_TYPE.get(), Daemon.createAttributes().build());
		event.put(ModEntities.SKELETON_WARRIOR_TYPE.get(), SkeletonWarrior.createAttributes().build());
		event.put(ModEntities.WINGED_SKELETON_TYPE.get(), WingedSkeleton.createAttributes().build());
		event.put(ModEntities.IRON_SKELETON_TYPE.get(), IronSkeleton.createAttributes().build());
		event.put(ModEntities.MAGMA_SKELETON_TYPE.get(), MagmaSkeleton.createAttributes().build());
		event.put(ModEntities.FROST_SKELETON_TYPE.get(), FrostSkeleton.createAttributes().build());
		event.put(ModEntities.TAINTED_SKELETON_TYPE.get(), TaintedSkeleton.createAttributes().build());

		event.put(ModEntities.GARGOYLE_TYPE.get(), Gargoyle.createAttributes().build());
		event.put(ModEntities.MARGOYLE_TYPE.get(), Margoyle.createAttributes().build());

	}

	@SubscribeEvent
	public static void registerEntitySpawn(SpawnPlacementRegisterEvent event) {
		event.register(ModEntities.HEADLESS_ENTITY_TYPE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SpawnRulesUtil::checkSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
		event.register(ModEntities.ORC_ENTITY_TYPE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SpawnRulesUtil::checkSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
		event.register(ModEntities.GHOUL_ENTITY_TYPE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SpawnRulesUtil::checkSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
		event.register(ModEntities.SEWER_GHOUL_ENTITY_TYPE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SpawnRulesUtil::checkSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
		event.register(ModEntities.RAT_ENTITY_TYPE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SpawnRulesUtil::checkSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
		event.register(ModEntities.BOULDER_ENTITY_TYPE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.WORLD_SURFACE, SpawnRulesUtil::checkBoulderSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
		event.register(ModEntities.ALLIGATOR_GAR_ENTITY_TYPE.get(), SpawnPlacements.Type.IN_WATER, Heightmap.Types.OCEAN_FLOOR, SpawnRulesUtil::checkWaterMobSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);

		event.register(ModEntities.SHADOW_ENTITY_TYPE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SpawnRulesUtil::checkSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
		event.register(ModEntities.SHADOWLORD_ENTITY_TYPE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SpawnRulesUtil::checkSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
		event.register(ModEntities.BEHOLDER_ENTITY_TYPE.get(), SpawnPlacements.Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SpawnRulesUtil::checkSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
		event.register(ModEntities.DEATH_TYRANT_TYPE.get(), SpawnPlacements.Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SpawnRulesUtil::checkSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
		event.register(ModEntities.GAZER_ENTITY_TYPE.get(), SpawnPlacements.Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SpawnRulesUtil::checkSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
		event.register(ModEntities.SPECTATOR_TYPE.get(), SpawnPlacements.Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SpawnRulesUtil::checkSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
		event.register(ModEntities.DAEMON_ENTITY_TYPE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SpawnRulesUtil::checkSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
		event.register(ModEntities.SKELETON_WARRIOR_TYPE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SpawnRulesUtil::checkSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
		event.register(ModEntities.WINGED_SKELETON_TYPE.get(), SpawnPlacements.Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SpawnRulesUtil::checkSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
		event.register(ModEntities.IRON_SKELETON_TYPE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SpawnRulesUtil::checkSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
		event.register(ModEntities.MAGMA_SKELETON_TYPE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SpawnRulesUtil::checkMagmaSkeletonSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
		event.register(ModEntities.FROST_SKELETON_TYPE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SpawnRulesUtil::checkSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
		event.register(ModEntities.TAINTED_SKELETON_TYPE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SpawnRulesUtil::checkSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);

		event.register(ModEntities.GARGOYLE_TYPE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SpawnRulesUtil::checkSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
		event.register(ModEntities.MARGOYLE_TYPE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, SpawnRulesUtil::checkSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);

	}

	@SubscribeEvent
	public static void registemItemsToTab(BuildCreativeModeTabContentsEvent event) {
		if (event.getTabKey() == CreativeModeTabs.SPAWN_EGGS) {
			event.accept(ModItems.HEADLESS_EGG.get(), TabVisibility.PARENT_AND_SEARCH_TABS);
			event.accept(ModItems.ORC_EGG.get(), TabVisibility.PARENT_AND_SEARCH_TABS);
			event.accept(ModItems.GHOUL_EGG.get(), TabVisibility.PARENT_AND_SEARCH_TABS);
			event.accept(ModItems.SEWER_GHOUL_EGG.get(), TabVisibility.PARENT_AND_SEARCH_TABS);
			event.accept(ModItems.RAT_EGG.get(), TabVisibility.PARENT_AND_SEARCH_TABS);
			event.accept(ModItems.ALLIGATOR_GAR_EGG.get(), TabVisibility.PARENT_AND_SEARCH_TABS);
			event.accept(ModItems.BOULDER_EGG.get(), TabVisibility.PARENT_AND_SEARCH_TABS);

			event.accept(ModItems.SHADOW_EGG.get(), TabVisibility.PARENT_AND_SEARCH_TABS);
			event.accept(ModItems.SHADOWLORD_EGG.get(), TabVisibility.PARENT_AND_SEARCH_TABS);
			event.accept(ModItems.BEHOLDER_EGG.get(), TabVisibility.PARENT_AND_SEARCH_TABS);
			event.accept(ModItems.DEATH_TYRANT_EGG.get(), TabVisibility.PARENT_AND_SEARCH_TABS);
			event.accept(ModItems.GAZER_EGG.get(), TabVisibility.PARENT_AND_SEARCH_TABS);
			event.accept(ModItems.SPECTATOR_EGG.get(), TabVisibility.PARENT_AND_SEARCH_TABS);
			event.accept(ModItems.DAEMON_EGG.get(), TabVisibility.PARENT_AND_SEARCH_TABS);
			event.accept(ModItems.SKELETON_WARRIOR_EGG.get(), TabVisibility.PARENT_AND_SEARCH_TABS);
			event.accept(ModItems.WINGED_SKELETON_EGG.get(), TabVisibility.PARENT_AND_SEARCH_TABS);
			event.accept(ModItems.IRON_SKELETON_EGG.get(), TabVisibility.PARENT_AND_SEARCH_TABS);
			event.accept(ModItems.MAGMA_SKELETON_EGG.get(), TabVisibility.PARENT_AND_SEARCH_TABS);
			event.accept(ModItems.FROST_SKELETON_EGG.get(), TabVisibility.PARENT_AND_SEARCH_TABS);
			event.accept(ModItems.TAINTED_SKELETON_EGG.get(), TabVisibility.PARENT_AND_SEARCH_TABS);

			event.accept(ModItems.GARGOYLE_EGG.get(), TabVisibility.PARENT_AND_SEARCH_TABS);
			event.accept(ModItems.MARGOYLE_EGG.get(), TabVisibility.PARENT_AND_SEARCH_TABS);

		}
		else if (event.getTabKey() == CreativeModeTabs.COMBAT) {
			event.accept(ModItems.CLUB.get(), TabVisibility.PARENT_AND_SEARCH_TABS);
			event.accept(ModItems.SPIKED_CLUB.get(), TabVisibility.PARENT_AND_SEARCH_TABS);
			event.accept(ModItems.RUSTY_IRON_AXE1.get(), TabVisibility.PARENT_AND_SEARCH_TABS);
			event.accept(ModItems.RUSTY_IRON_AXE2.get(), TabVisibility.PARENT_AND_SEARCH_TABS);
			event.accept(ModItems.RUSTY_IRON_SWORD1.get(), TabVisibility.PARENT_AND_SEARCH_TABS);
			event.accept(ModItems.RUSTY_IRON_SWORD2.get(), TabVisibility.PARENT_AND_SEARCH_TABS);
			event.accept(ModItems.RUSTY_IRON_SWORD3.get(), TabVisibility.PARENT_AND_SEARCH_TABS);
			event.accept(ModItems.RUSTY_IRON_SWORD4.get(), TabVisibility.PARENT_AND_SEARCH_TABS);

		}
	}
	
	@Mod.EventBusSubscriber(modid = DD.MODID, bus = EventBusSubscriber.Bus.FORGE)
	public static class ForgeBusSucscriber {

		@SubscribeEvent
		public static void addGoals(final EntityJoinLevelEvent event) {
			if (event.getEntity() instanceof Zombie) {
				((Zombie)event.getEntity()).goalSelector.addGoal(3, new AvoidEntityGoal<>(((Zombie)event.getEntity()), Boulder.class, 6.0F, 1.0D, 1.2D, SpawnRulesUtil.avoidBoulder));
			}
			// v2.0: gmm mobs don't reference DD's Boulder; inject Boulder-avoidance consumer-side
			else if (event.getEntity() instanceof SkeletonWarrior) {
				((SkeletonWarrior)event.getEntity()).goalSelector.addGoal(3, new AvoidEntityGoal<>(((SkeletonWarrior)event.getEntity()), Boulder.class, 6.0F, 1.0D, 1.2D, SpawnRulesUtil.avoidBoulder));
			}
			else if (event.getEntity() instanceof Ghoul) {
				((Ghoul)event.getEntity()).goalSelector.addGoal(3, new AvoidEntityGoal<>(((Ghoul)event.getEntity()), Boulder.class, 6.0F, 1.0D, 1.2D, SpawnRulesUtil.avoidBoulder));
			}
			else if (event.getEntity() instanceof SewerGhoul) {
				((SewerGhoul)event.getEntity()).goalSelector.addGoal(3, new AvoidEntityGoal<>(((SewerGhoul)event.getEntity()), Boulder.class, 6.0F, 1.0D, 1.2D, SpawnRulesUtil.avoidBoulder));
			}
			else if (event.getEntity() instanceof Rat) {
				((Rat)event.getEntity()).goalSelector.addGoal(3, new AvoidEntityGoal<>(((Rat)event.getEntity()), Boulder.class, 6.0F, 1.0D, 1.2D, SpawnRulesUtil.avoidBoulder));
			}
			else if (event.getEntity() instanceof Orc) {
				((Orc)event.getEntity()).goalSelector.addGoal(3, new AvoidEntityGoal<>(((Orc)event.getEntity()), Boulder.class, 6.0F, 1.0D, 1.2D, SpawnRulesUtil.avoidBoulder));
			}
			else if (event.getEntity() instanceof Shadow) {
				((Shadow)event.getEntity()).goalSelector.addGoal(3, new AvoidEntityGoal<>(((Shadow)event.getEntity()), Boulder.class, 6.0F, 1.0D, 1.2D, SpawnRulesUtil.avoidBoulder));
			}
			else if (event.getEntity() instanceof BowSkeleton) {
				((BowSkeleton)event.getEntity()).goalSelector.addGoal(3, new AvoidEntityGoal<>(((BowSkeleton)event.getEntity()), Boulder.class, 6.0F, 1.0D, 1.2D, SpawnRulesUtil.avoidBoulder));
			}
			else if (event.getEntity() instanceof Skeleton) {
				((Skeleton)event.getEntity()).goalSelector.addGoal(3, new AvoidEntityGoal<>(((Skeleton)event.getEntity()), Boulder.class, 6.0F, 1.0D, 1.2D, SpawnRulesUtil.avoidBoulder));
			}
			else if (event.getEntity() instanceof ZombieVillager) {
				((ZombieVillager)event.getEntity()).goalSelector.addGoal(3, new AvoidEntityGoal<>(((ZombieVillager)event.getEntity()), Boulder.class, 6.0F, 1.0D, 1.2D, SpawnRulesUtil.avoidBoulder));
			}
			// gmm's Gazer/Spectator don't reference DD's Boulder; inject Boulder-targeting (as prey,
			// not avoidance) consumer-side, same mechanism as the avoidance branches above.
			else if (event.getEntity() instanceof Gazer) {
				((Gazer)event.getEntity()).targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(((Gazer)event.getEntity()), Boulder.class, true, (entity) -> entity instanceof Boulder boulder && boulder.isActive()));
			}
			else if (event.getEntity() instanceof Spectator) {
				((Spectator)event.getEntity()).targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(((Spectator)event.getEntity()), Boulder.class, true, (entity) -> entity instanceof Boulder boulder && boulder.isActive()));
			}
			else if (event.getEntity() instanceof Daemon) {
				Daemon daemon = (Daemon) event.getEntity();
				daemon.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(daemon, Boulder.class, true, (entity) -> {
					if (entity instanceof Boulder boulder) {
						if (daemon.getSummonedOwner() != null && daemon.getSummonedOwner() instanceof Player) {
							return false;
						}
						return boulder.isActive();
					}
					return false;
				}));
			}
			else if (event.getEntity() instanceof Shadowlord) {
				((Shadowlord)event.getEntity()).targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(((Shadowlord)event.getEntity()), Boulder.class, true, SpawnRulesUtil.avoidBoulder));
			}
		}

		@SubscribeEvent
		public static void onFeedBoulder(final PlayerInteractEvent.EntityInteract event) {
			// get item in hand
			ItemStack heldItem = event.getItemStack();

			if (!heldItem.isEmpty() && (heldItem.getItem() == Items.IRON_INGOT 
					|| heldItem.getItem() == Items.IRON_ORE 
					|| heldItem.getItem() == Items.DEEPSLATE_IRON_ORE)) {

				if (event.getTarget() instanceof Boulder) {
					Boulder boulder = (Boulder)event.getTarget();
					boulder.feed(event.getEntity().getUUID());
				}
			}
		}

		// TODO this needs to move to Shadowlord - onHurtTarget
		@SubscribeEvent
		public static void hitFromShadowlord(LivingDamageEvent event) {
			if (WorldInfo.isClientSide(event.getEntity().level())) {
				return;
			}

			if (event.getEntity() instanceof Player && event.getSource().getEntity() instanceof Shadowlord) {
				// get the player
				ServerPlayer player = (ServerPlayer) event.getEntity();
				((Shadowlord)event.getSource().getEntity()).drain(player, event.getAmount());
			}
			else if (event.getEntity() instanceof Shadowlord && event.getSource().getEntity() instanceof Player) {
				// this condition is player hitting shadowlord
			}
		}
	}
}
