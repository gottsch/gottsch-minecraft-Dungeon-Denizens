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
import com.someguyssoftware.ddenizens.entity.monster.*;
// v2.0: these mobs now sourced from gottsch's Monster Manual (gmm) shared library
import mod.gottsch.forge.gmm.core.entity.monster.ghoul.Ghoul;
import mod.gottsch.forge.gmm.core.entity.monster.Headless;
import mod.gottsch.forge.gmm.core.entity.monster.SkeletonWarrior;

import com.someguyssoftware.ddenizens.entity.monster.skeleton.FossilizedSkeleton;
import com.someguyssoftware.ddenizens.entity.monster.skeleton.IronSkeleton;
import com.someguyssoftware.ddenizens.entity.monster.skeleton.MagmaSkeleton;
import com.someguyssoftware.ddenizens.integrations.Integrations;
import com.someguyssoftware.ddenizens.item.ModItems;
import mod.gottsch.forge.gottschcore.world.WorldInfo;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
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
	}

	/**
	 * attach defined attributes to the entity.
	 * @param event
	 */
	@SubscribeEvent
	public static void onAttributeCreate(EntityAttributeCreationEvent event) {
		event.put(Registration.HEADLESS_ENTITY_TYPE.get(), Headless.createAttributes().build());
		event.put(Registration.ORC_ENTITY_TYPE.get(), Orc.createAttributes().build());
		event.put(Registration.GHOUL_ENTITY_TYPE.get(), Ghoul.createAttributes().build());
		event.put(Registration.BEHOLDER_ENTITY_TYPE.get(), Beholder.prepareAttributes().build());
		event.put(Registration.DEATH_TYRANT_TYPE.get(), DeathTyrant.prepareAttributes().build());
		event.put(Registration.GAZER_ENTITY_TYPE.get(), Gazer.prepareAttributes().build());
		event.put(Registration.SPECTATOR_TYPE.get(), Spectator.prepareAttributes().build());
		event.put(Registration.BOULDER_ENTITY_TYPE.get(), Boulder.createAttributes().build());
		event.put(Registration.SHADOW_ENTITY_TYPE.get(), Shadow.createAttributes().build());
		event.put(Registration.SHADOWLORD_ENTITY_TYPE.get(), Shadowlord.createAttributes().build());
		event.put(Registration.DAEMON_ENTITY_TYPE.get(), Daemon.createAttributes().build());
		event.put(Registration.SKELETON_WARRIOR_TYPE.get(), SkeletonWarrior.createAttributes().build());
		event.put(Registration.WINGED_SKELETON_TYPE.get(), WingedSkeleton.createAttributes().build());
		event.put(Registration.FOSSILIZED_SKELETON_TYPE.get(), FossilizedSkeleton.createAttributes().build());
		event.put(Registration.IRON_SKELETON_TYPE.get(), IronSkeleton.createAttributes().build());
		event.put(Registration.MAGMA_SKELETON_TYPE.get(), MagmaSkeleton.createAttributes().build());

		event.put(ModEntities.GARGOYLE_TYPE.get(), Gargoyle.createAttributes().build());
		event.put(ModEntities.MARGOYLE_TYPE.get(), Margoyle.createAttributes().build());

	}

	@SubscribeEvent
	public static void registerEntitySpawn(SpawnPlacementRegisterEvent event) {
		event.register(Registration.HEADLESS_ENTITY_TYPE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DenizensMonster::checkDDMonsterCanSeeSkySpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
		event.register(Registration.ORC_ENTITY_TYPE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DenizensMonster::checkDDMonsterCanSeeSkySpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
		event.register(Registration.GHOUL_ENTITY_TYPE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DenizensMonster::checkDDMonsterSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
		event.register(Registration.BOULDER_ENTITY_TYPE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.WORLD_SURFACE, Boulder::checkSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);

		event.register(Registration.SHADOW_ENTITY_TYPE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DenizensMonster::checkIsNetherSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
		event.register(Registration.SHADOWLORD_ENTITY_TYPE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DenizensMonster::checkIsNetherSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
		event.register(Registration.BEHOLDER_ENTITY_TYPE.get(), SpawnPlacements.Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DenizensMonster::checkIsNetherSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
		event.register(Registration.DEATH_TYRANT_TYPE.get(), SpawnPlacements.Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DenizensMonster::checkIsNetherSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
		event.register(Registration.GAZER_ENTITY_TYPE.get(), SpawnPlacements.Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DenizensMonster::checkIsNetherSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
		event.register(Registration.SPECTATOR_TYPE.get(), SpawnPlacements.Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DenizensMonster::checkIsNetherSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
		event.register(Registration.DAEMON_ENTITY_TYPE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DenizensMonster::checkIsNetherSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
		event.register(Registration.SKELETON_WARRIOR_TYPE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DenizensMonster::checkDDMonsterSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
		event.register(Registration.WINGED_SKELETON_TYPE.get(), SpawnPlacements.Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DenizensMonster::checkDDMonsterSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
		event.register(Registration.FOSSILIZED_SKELETON_TYPE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DenizensMonster::checkDDMonsterSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
		event.register(Registration.IRON_SKELETON_TYPE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DenizensMonster::checkDDMonsterSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
		event.register(Registration.MAGMA_SKELETON_TYPE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, MagmaSkeleton::checkMagmaSkeletonSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);

		event.register(ModEntities.GARGOYLE_TYPE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DenizensMonster::checkDDMonsterSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
		event.register(ModEntities.MARGOYLE_TYPE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, DenizensMonster::checkDDMonsterUndergroundSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);

	}

	@SubscribeEvent
	public static void registemItemsToTab(BuildCreativeModeTabContentsEvent event) {
		if (event.getTabKey() == CreativeModeTabs.SPAWN_EGGS) {
			event.accept(Registration.HEADLESS_EGG.get(), TabVisibility.PARENT_AND_SEARCH_TABS);
			event.accept(Registration.ORC_EGG.get(), TabVisibility.PARENT_AND_SEARCH_TABS);
			event.accept(Registration.GHOUL_EGG.get(), TabVisibility.PARENT_AND_SEARCH_TABS);
			event.accept(Registration.BOULDER_EGG.get(), TabVisibility.PARENT_AND_SEARCH_TABS);

			event.accept(Registration.SHADOW_EGG.get(), TabVisibility.PARENT_AND_SEARCH_TABS);
			event.accept(Registration.SHADOWLORD_EGG.get(), TabVisibility.PARENT_AND_SEARCH_TABS);
			event.accept(Registration.BEHOLDER_EGG.get(), TabVisibility.PARENT_AND_SEARCH_TABS);
			event.accept(Registration.DEATH_TYRANT_EGG.get(), TabVisibility.PARENT_AND_SEARCH_TABS);
			event.accept(Registration.GAZER_EGG.get(), TabVisibility.PARENT_AND_SEARCH_TABS);
			event.accept(Registration.SPECTATOR_EGG.get(), TabVisibility.PARENT_AND_SEARCH_TABS);
			event.accept(Registration.DAEMON_EGG.get(), TabVisibility.PARENT_AND_SEARCH_TABS);
			event.accept(Registration.SKELETON_WARRIOR_EGG.get(), TabVisibility.PARENT_AND_SEARCH_TABS);
			event.accept(Registration.WINGED_SKELETON_EGG.get(), TabVisibility.PARENT_AND_SEARCH_TABS);
			event.accept(Registration.FOSSILIZED_SKELETON_EGG.get(), TabVisibility.PARENT_AND_SEARCH_TABS);
			event.accept(Registration.IRON_SKELETON_EGG.get(), TabVisibility.PARENT_AND_SEARCH_TABS);
			event.accept(Registration.MAGMA_SKELETON_EGG.get(), TabVisibility.PARENT_AND_SEARCH_TABS);

			event.accept(ModItems.GARGOYLE_EGG.get(), TabVisibility.PARENT_AND_SEARCH_TABS);
			event.accept(ModItems.MARGOYLE_EGG.get(), TabVisibility.PARENT_AND_SEARCH_TABS);

		}
		else if (event.getTabKey() == CreativeModeTabs.COMBAT) {
			event.accept(Registration.CLUB.get(), TabVisibility.PARENT_AND_SEARCH_TABS);
			event.accept(Registration.SPIKED_CLUB.get(), TabVisibility.PARENT_AND_SEARCH_TABS);
			event.accept(Registration.RUSTY_IRON_AXE1.get(), TabVisibility.PARENT_AND_SEARCH_TABS);
			event.accept(Registration.RUSTY_IRON_AXE2.get(), TabVisibility.PARENT_AND_SEARCH_TABS);
			event.accept(Registration.RUSTY_IRON_SWORD1.get(), TabVisibility.PARENT_AND_SEARCH_TABS);
			event.accept(Registration.RUSTY_IRON_SWORD2.get(), TabVisibility.PARENT_AND_SEARCH_TABS);
			event.accept(Registration.RUSTY_IRON_SWORD3.get(), TabVisibility.PARENT_AND_SEARCH_TABS);
			event.accept(Registration.RUSTY_IRON_SWORD4.get(), TabVisibility.PARENT_AND_SEARCH_TABS);

		}
	}
	
	@Mod.EventBusSubscriber(modid = DD.MODID, bus = EventBusSubscriber.Bus.FORGE)
	public static class ForgeBusSucscriber {

		@SubscribeEvent
		public static void addGoals(final EntityJoinLevelEvent event) {
			if (event.getEntity() instanceof Zombie) {
				((Zombie)event.getEntity()).goalSelector.addGoal(3, new AvoidEntityGoal<>(((Zombie)event.getEntity()), Boulder.class, 6.0F, 1.0D, 1.2D, IDenizensMonster.avoidBoulder));
			}
			// v2.0: gmm mobs don't reference DD's Boulder; inject Boulder-avoidance consumer-side
			else if (event.getEntity() instanceof SkeletonWarrior) {
				((SkeletonWarrior)event.getEntity()).goalSelector.addGoal(3, new AvoidEntityGoal<>(((SkeletonWarrior)event.getEntity()), Boulder.class, 6.0F, 1.0D, 1.2D, IDenizensMonster.avoidBoulder));
			}
			else if (event.getEntity() instanceof Ghoul) {
				((Ghoul)event.getEntity()).goalSelector.addGoal(3, new AvoidEntityGoal<>(((Ghoul)event.getEntity()), Boulder.class, 6.0F, 1.0D, 1.2D, IDenizensMonster.avoidBoulder));
			}
			else if (event.getEntity() instanceof Skeleton) {
				((Skeleton)event.getEntity()).goalSelector.addGoal(3, new AvoidEntityGoal<>(((Skeleton)event.getEntity()), Boulder.class, 6.0F, 1.0D, 1.2D, IDenizensMonster.avoidBoulder));
			}
			else if (event.getEntity() instanceof ZombieVillager) {
				((ZombieVillager)event.getEntity()).goalSelector.addGoal(3, new AvoidEntityGoal<>(((ZombieVillager)event.getEntity()), Boulder.class, 6.0F, 1.0D, 1.2D, IDenizensMonster.avoidBoulder));
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
