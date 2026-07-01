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
import com.someguyssoftware.ddenizens.client.renderer.entity.*;
// v2.0: these models + renderers now sourced from gottsch's Monster Manual (gmm) shared library
import mod.gottsch.forge.gmm.core.client.model.GhoulModel;
import mod.gottsch.forge.gmm.core.client.renderer.entity.GhoulRenderer;
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

		event.registerLayerDefinition(GargoyleModel.LAYER_LOCATION, GargoyleModel::createBodyLayer);
		event.registerLayerDefinition(MargoyleModel.LAYER_LOCATION, MargoyleModel::createBodyLayer);

	}

	/**
	 * register renderers
	 * @param event
	 */
	@SubscribeEvent
    public static void onRegisterRenderer(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(Registration.HEADLESS_ENTITY_TYPE.get(), HeadlessRenderer::new);
        event.registerEntityRenderer(Registration.ORC_ENTITY_TYPE.get(), OrcRenderer::new);
        event.registerEntityRenderer(Registration.GHOUL_ENTITY_TYPE.get(), GhoulRenderer::new);
		event.registerEntityRenderer(Registration.BEHOLDER_ENTITY_TYPE.get(), BeholderRenderer::new);
		event.registerEntityRenderer(Registration.DEATH_TYRANT_TYPE.get(), DeathTyrantRenderer::new);
        event.registerEntityRenderer(Registration.GAZER_ENTITY_TYPE.get(), GazerRenderer::new);
		event.registerEntityRenderer(Registration.SPECTATOR_TYPE.get(), SpectatorRenderer::new);
		event.registerEntityRenderer(Registration.BOULDER_ENTITY_TYPE.get(), BoulderRenderer::new);
        event.registerEntityRenderer(Registration.SHADOW_ENTITY_TYPE.get(), ShadowRenderer::new);
        event.registerEntityRenderer(Registration.SHADOWLORD_ENTITY_TYPE.get(), ShadowlordRenderer::new);
        event.registerEntityRenderer(Registration.DAEMON_ENTITY_TYPE.get(), DaemonRenderer::new);
		event.registerEntityRenderer(Registration.SKELETON_WARRIOR_TYPE.get(), SkeletonWarriorRenderer::new);
		event.registerEntityRenderer(Registration.WINGED_SKELETON_TYPE.get(), WingedSkeletonRenderer::new);
		event.registerEntityRenderer(Registration.IRON_SKELETON_TYPE.get(), IronSkeletonRenderer::new);
		event.registerEntityRenderer(Registration.MAGMA_SKELETON_TYPE.get(), MagmaSkeletonRenderer::new);

		event.registerEntityRenderer(ModEntities.GARGOYLE_TYPE.get(), GargoyleRenderer::new);
		event.registerEntityRenderer(ModEntities.MARGOYLE_TYPE.get(), MargoyleRenderer::new);


		event.registerEntityRenderer(Registration.PARALYSIS_SPELL_ENTITY_TYPE.get(), (provider) -> {
            // 1.0 = scale, true = full bright
        	return new ThrownItemRenderer<>(provider, 1.25F, true);
         });
        event.registerEntityRenderer(Registration.HARM_SPELL_ENTITY_TYPE.get(), (provider) -> {
        	return new ThrownItemRenderer<>(provider, 1.25F, true);
         });
		event.registerEntityRenderer(Registration.DISINTEGRATE_SPELL_ENTITY_TYPE.get(), (provider) -> {
			return new ThrownItemRenderer<>(provider, 1.25F, true);
		});
		event.registerEntityRenderer(Registration.DISARM_SPELL_ENTITY_TYPE.get(), (provider) -> {
			return new ThrownItemRenderer<>(provider, 1.25F, true);
		});
        event.registerEntityRenderer(Registration.FIRESPOUT_SPELL_ENTITY_TYPE.get(), (provider) -> {
        	return new ThrownItemRenderer<>(provider, 1.5F, true);
         });
        event.registerEntityRenderer(Registration.ROCK_ENTITY_TYPE.get(), (provider) -> {
        	return new ThrownItemRenderer<>(provider, 1.0F, true);
         });
	}
}
