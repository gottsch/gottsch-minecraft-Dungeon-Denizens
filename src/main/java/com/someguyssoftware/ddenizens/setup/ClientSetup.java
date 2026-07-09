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
import com.someguyssoftware.ddenizens.client.model.*;
// v2.0: these models + renderers now sourced from gottsch's Monster Manual (gmm) shared library
// (DD's own client.renderer.entity package is now empty -- every renderer has migrated to gmm)
import mod.gottsch.forge.gmm.core.client.model.GhoulModel;
import mod.gottsch.forge.gmm.core.client.model.SewerGhoulModel;
import mod.gottsch.forge.gmm.core.client.model.RatModel;
import mod.gottsch.forge.gmm.core.client.model.AlligatorGarModel;
import mod.gottsch.forge.gmm.core.client.renderer.entity.GhoulRenderer;
import mod.gottsch.forge.gmm.core.client.renderer.entity.SewerGhoulRenderer;
import mod.gottsch.forge.gmm.core.client.renderer.entity.RatRenderer;
import mod.gottsch.forge.gmm.core.client.renderer.entity.AlligatorGarRenderer;
import mod.gottsch.forge.gmm.core.client.model.HeadlessModel;
import mod.gottsch.forge.gmm.core.client.renderer.entity.HeadlessRenderer;
import mod.gottsch.forge.gmm.core.client.model.SkeletonWarriorModel;
import mod.gottsch.forge.gmm.core.client.renderer.entity.SkeletonWarriorRenderer;
import mod.gottsch.forge.gmm.core.client.model.OrcModel;
import mod.gottsch.forge.gmm.core.client.renderer.entity.OrcRenderer;
import mod.gottsch.forge.gmm.core.client.model.ShadowModel;
import mod.gottsch.forge.gmm.core.client.renderer.entity.ShadowRenderer;
import mod.gottsch.forge.gmm.core.client.model.IronSkeletonModel;
import mod.gottsch.forge.gmm.core.client.renderer.entity.IronSkeletonRenderer;
import mod.gottsch.forge.gmm.core.client.model.MagmaSkeletonModel;
import mod.gottsch.forge.gmm.core.client.renderer.entity.MagmaSkeletonRenderer;
import mod.gottsch.forge.gmm.core.client.model.FrostSkeletonModel;
import mod.gottsch.forge.gmm.core.client.renderer.entity.FrostSkeletonRenderer;
import mod.gottsch.forge.gmm.core.client.model.TaintedSkeletonModel;
import mod.gottsch.forge.gmm.core.client.renderer.entity.TaintedSkeletonRenderer;
import mod.gottsch.forge.gmm.core.client.renderer.entity.AcidSkeletonRenderer;
import mod.gottsch.forge.gmm.core.client.renderer.entity.ElectricSkeletonRenderer;
import mod.gottsch.forge.gmm.core.client.renderer.entity.BurningSkeletonRenderer;
import mod.gottsch.forge.gmm.core.client.renderer.entity.BloodyBonesRenderer;
import mod.gottsch.forge.gmm.core.client.renderer.entity.BloaterRenderer;
import mod.gottsch.forge.gmm.core.client.renderer.entity.GelatinousCubeRenderer;
import mod.gottsch.forge.gmm.core.client.renderer.entity.OchreJellyRenderer;
import mod.gottsch.forge.gmm.core.client.renderer.entity.GrayOozeRenderer;
import mod.gottsch.forge.gmm.core.client.model.VanillaChestMimicModel;
import mod.gottsch.forge.gmm.core.client.renderer.entity.VanillaChestMimicRenderer;
import mod.gottsch.forge.gmm.core.client.model.BarrelMimicModel;
import mod.gottsch.forge.gmm.core.client.renderer.entity.BarrelMimicRenderer;
import mod.gottsch.forge.gmm.core.client.model.BoneShardModel;
import mod.gottsch.forge.gmm.core.client.renderer.entity.BoneShardRenderer;
import mod.gottsch.forge.gmm.core.client.model.WingedSkeletonModel;
import mod.gottsch.forge.gmm.core.client.renderer.entity.WingedSkeletonRenderer;
import mod.gottsch.forge.gmm.core.client.model.GargoyleModel;
import mod.gottsch.forge.gmm.core.client.model.MargoyleModel;
import mod.gottsch.forge.gmm.core.client.renderer.entity.GargoyleRenderer;
import mod.gottsch.forge.gmm.core.client.renderer.entity.MargoyleRenderer;
import mod.gottsch.forge.gmm.core.client.model.BeholderModel;
import mod.gottsch.forge.gmm.core.client.model.DeathTyrantModel;
import mod.gottsch.forge.gmm.core.client.model.GazerModel;
import mod.gottsch.forge.gmm.core.client.model.SpectatorModel;
import mod.gottsch.forge.gmm.core.client.renderer.entity.BeholderRenderer;
import mod.gottsch.forge.gmm.core.client.renderer.entity.DeathTyrantRenderer;
import mod.gottsch.forge.gmm.core.client.renderer.entity.GazerRenderer;
import mod.gottsch.forge.gmm.core.client.renderer.entity.SpectatorRenderer;
import mod.gottsch.forge.gmm.core.client.model.BoulderModel;
import mod.gottsch.forge.gmm.core.client.model.DaemonModel;
import mod.gottsch.forge.gmm.core.client.model.ShadowlordModel;
import mod.gottsch.forge.gmm.core.client.renderer.entity.BoulderRenderer;
import mod.gottsch.forge.gmm.core.client.renderer.entity.DaemonRenderer;
import mod.gottsch.forge.gmm.core.client.renderer.entity.ShadowlordRenderer;

import com.someguyssoftware.ddenizens.entity.ModEntities;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

/**
 * Client only event bus subscriber.
 * @author Mark Gottschling on Apr 2, 2022
 *
 */
@Mod.EventBusSubscriber(modid = DD.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ClientSetup {
    public static void init(final FMLClientSetupEvent event) {
    	
    }
    
	/**
	 * register layers
	 * @param event
	 */
	@SubscribeEvent()
	public static void onRegisterLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
		event.registerLayerDefinition(HeadlessModel.LAYER_LOCATION, HeadlessModel::createBodyLayer);
		event.registerLayerDefinition(OrcModel.LAYER_LOCATION, OrcModel::createBodyLayer);
		event.registerLayerDefinition(GhoulModel.LAYER_LOCATION, GhoulModel::createBodyLayer);
		event.registerLayerDefinition(SewerGhoulModel.LAYER_LOCATION, SewerGhoulModel::createBodyLayer);
		event.registerLayerDefinition(RatModel.LAYER_LOCATION, RatModel::createBodyLayer);
		event.registerLayerDefinition(AlligatorGarModel.LAYER_LOCATION, AlligatorGarModel::createBodyLayer);
		event.registerLayerDefinition(EttinModel.LAYER_LOCATION, EttinModel::createBodyLayer);
		event.registerLayerDefinition(BeholderModel.LAYER_LOCATION, BeholderModel::createBodyLayer);
		event.registerLayerDefinition(DeathTyrantModel.LAYER_LOCATION, DeathTyrantModel::createBodyLayer);
		event.registerLayerDefinition(GazerModel.LAYER_LOCATION, GazerModel::createBodyLayer);
		event.registerLayerDefinition(SpectatorModel.LAYER_LOCATION, SpectatorModel::createBodyLayer);
		event.registerLayerDefinition(BoulderModel.LAYER_LOCATION, BoulderModel::createBodyLayer);
		event.registerLayerDefinition(ShadowModel.LAYER_LOCATION, ShadowModel::createBodyLayer);
		event.registerLayerDefinition(ShadowlordModel.LAYER_LOCATION, ShadowlordModel::createBodyLayer);
		event.registerLayerDefinition(DaemonModel.LAYER_LOCATION, DaemonModel::createBodyLayer);
		event.registerLayerDefinition(SkeletonWarriorModel.LAYER_LOCATION, SkeletonWarriorModel::createBodyLayer);
		event.registerLayerDefinition(WingedSkeletonModel.LAYER_LOCATION, WingedSkeletonModel::createBodyLayer);
		event.registerLayerDefinition(IronSkeletonModel.LAYER_LOCATION, IronSkeletonModel::createBodyLayer);
		event.registerLayerDefinition(MagmaSkeletonModel.LAYER_LOCATION, MagmaSkeletonModel::createBodyLayer);
		event.registerLayerDefinition(FrostSkeletonModel.LAYER_LOCATION, FrostSkeletonModel::createBodyLayer);
		event.registerLayerDefinition(TaintedSkeletonModel.LAYER_LOCATION, TaintedSkeletonModel::createBodyLayer);
		for (int v = 0; v < BoneShardModel.LAYERS.length; v++) {
			final int variant = v;
			event.registerLayerDefinition(BoneShardModel.LAYERS[variant], () -> BoneShardModel.createBodyLayer(variant));
		}

		event.registerLayerDefinition(GargoyleModel.LAYER_LOCATION, GargoyleModel::createBodyLayer);
		event.registerLayerDefinition(MargoyleModel.LAYER_LOCATION, MargoyleModel::createBodyLayer);

		event.registerLayerDefinition(VanillaChestMimicModel.LAYER_LOCATION, VanillaChestMimicModel::createBodyLayer);
		event.registerLayerDefinition(BarrelMimicModel.LAYER_LOCATION, BarrelMimicModel::createBodyLayer);

	}

	/**
	 * register renderers
	 * @param event
	 */
	@SubscribeEvent
    public static void onRegisterRenderer(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntities.HEADLESS_ENTITY_TYPE.get(), HeadlessRenderer::new);
        event.registerEntityRenderer(ModEntities.ORC_ENTITY_TYPE.get(), OrcRenderer::new);
        event.registerEntityRenderer(ModEntities.GHOUL_ENTITY_TYPE.get(), GhoulRenderer::new);
		event.registerEntityRenderer(ModEntities.SEWER_GHOUL_ENTITY_TYPE.get(), SewerGhoulRenderer::new);
		event.registerEntityRenderer(ModEntities.RAT_ENTITY_TYPE.get(), RatRenderer::new);
		event.registerEntityRenderer(ModEntities.ALLIGATOR_GAR_ENTITY_TYPE.get(), AlligatorGarRenderer::new);
		event.registerEntityRenderer(ModEntities.BEHOLDER_ENTITY_TYPE.get(), BeholderRenderer::new);
		event.registerEntityRenderer(ModEntities.DEATH_TYRANT_TYPE.get(), DeathTyrantRenderer::new);
        event.registerEntityRenderer(ModEntities.GAZER_ENTITY_TYPE.get(), GazerRenderer::new);
		event.registerEntityRenderer(ModEntities.SPECTATOR_TYPE.get(), SpectatorRenderer::new);
		event.registerEntityRenderer(ModEntities.BOULDER_ENTITY_TYPE.get(), BoulderRenderer::new);
        event.registerEntityRenderer(ModEntities.SHADOW_ENTITY_TYPE.get(), ShadowRenderer::new);
        event.registerEntityRenderer(ModEntities.SHADOWLORD_ENTITY_TYPE.get(), ShadowlordRenderer::new);
        event.registerEntityRenderer(ModEntities.DAEMON_ENTITY_TYPE.get(), DaemonRenderer::new);
		event.registerEntityRenderer(ModEntities.SKELETON_WARRIOR_TYPE.get(), SkeletonWarriorRenderer::new);
		event.registerEntityRenderer(ModEntities.WINGED_SKELETON_TYPE.get(), WingedSkeletonRenderer::new);
		event.registerEntityRenderer(ModEntities.IRON_SKELETON_TYPE.get(), IronSkeletonRenderer::new);
		event.registerEntityRenderer(ModEntities.MAGMA_SKELETON_TYPE.get(), MagmaSkeletonRenderer::new);
		event.registerEntityRenderer(ModEntities.FROST_SKELETON_TYPE.get(), FrostSkeletonRenderer::new);
		event.registerEntityRenderer(ModEntities.TAINTED_SKELETON_TYPE.get(), TaintedSkeletonRenderer::new);
		// Acid reuses SkeletonWarriorModel.LAYER_LOCATION (registered above) — no new layer definition needed.
		event.registerEntityRenderer(ModEntities.ACID_SKELETON_TYPE.get(), AcidSkeletonRenderer::new);
		// Electric likewise reuses SkeletonWarriorModel.LAYER_LOCATION — no new layer definition needed.
		event.registerEntityRenderer(ModEntities.ELECTRIC_SKELETON_TYPE.get(), ElectricSkeletonRenderer::new);
		// Burning likewise reuses SkeletonWarriorModel.LAYER_LOCATION — no new layer definition needed.
		event.registerEntityRenderer(ModEntities.BURNING_SKELETON_TYPE.get(), BurningSkeletonRenderer::new);
		// Bloody Bones reuses SkeletonWarriorModel.LAYER_LOCATION (registered above) — no new layer definition needed.
		event.registerEntityRenderer(ModEntities.BLOODY_BONES_TYPE.get(), BloodyBonesRenderer::new);
		// Bloater reuses the vanilla ModelLayers.ZOMBIE rig — no new layer definition needed.
		event.registerEntityRenderer(ModEntities.BLOATER_TYPE.get(), BloaterRenderer::new);
		// Gelatinous Cube reuses the vanilla ModelLayers.SLIME rig — no new layer definition needed.
		event.registerEntityRenderer(ModEntities.GELATINOUS_CUBE_TYPE.get(), GelatinousCubeRenderer::new);
		// Ochre Jelly reuses the vanilla ModelLayers.SLIME rig — no new layer definition needed.
		event.registerEntityRenderer(ModEntities.OCHRE_JELLY_TYPE.get(), OchreJellyRenderer::new);
		// Gray Ooze reuses the vanilla ModelLayers.SLIME rig — no new layer definition needed.
		event.registerEntityRenderer(ModEntities.GRAY_OOZE_TYPE.get(), GrayOozeRenderer::new);
		event.registerEntityRenderer(ModEntities.VANILLA_CHEST_MIMIC_TYPE.get(), VanillaChestMimicRenderer::new);
		event.registerEntityRenderer(ModEntities.BARREL_MIMIC_TYPE.get(), BarrelMimicRenderer::new);

		event.registerEntityRenderer(ModEntities.GARGOYLE_TYPE.get(), GargoyleRenderer::new);
		event.registerEntityRenderer(ModEntities.MARGOYLE_TYPE.get(), MargoyleRenderer::new);


		event.registerEntityRenderer(ModEntities.PARALYSIS_SPELL_ENTITY_TYPE.get(), (provider) -> {
            // 1.0 = scale, true = full bright
        	return new ThrownItemRenderer<>(provider, 1.25F, true);
         });
        event.registerEntityRenderer(ModEntities.HARM_SPELL_ENTITY_TYPE.get(), (provider) -> {
        	return new ThrownItemRenderer<>(provider, 1.25F, true);
         });
		event.registerEntityRenderer(ModEntities.DISINTEGRATE_SPELL_ENTITY_TYPE.get(), (provider) -> {
			return new ThrownItemRenderer<>(provider, 1.25F, true);
		});
		event.registerEntityRenderer(ModEntities.DISARM_SPELL_ENTITY_TYPE.get(), (provider) -> {
			return new ThrownItemRenderer<>(provider, 1.25F, true);
		});
        event.registerEntityRenderer(ModEntities.FIRESPOUT_SPELL_ENTITY_TYPE.get(), (provider) -> {
        	return new ThrownItemRenderer<>(provider, 1.5F, true);
         });
        event.registerEntityRenderer(ModEntities.FIREWALL_COLUMN_SPELL_ENTITY_TYPE.get(), (provider) -> {
        	return new ThrownItemRenderer<>(provider, 1.0F, true);
         });
        event.registerEntityRenderer(ModEntities.ROCK_ENTITY_TYPE.get(), (provider) -> {
        	// second arg is the render scale of the thrown item (lower = smaller rock)
        	return new ThrownItemRenderer<>(provider, 0.5F, true);
         });
        event.registerEntityRenderer(ModEntities.BONE_SHARD_ENTITY_TYPE.get(), BoneShardRenderer::new);
	}
}
