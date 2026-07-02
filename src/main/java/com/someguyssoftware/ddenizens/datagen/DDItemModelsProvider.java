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
package com.someguyssoftware.ddenizens.datagen;

import com.someguyssoftware.ddenizens.DD;
import com.someguyssoftware.ddenizens.item.ModItems;

import net.minecraft.data.PackOutput;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

/**
 * 
 * @author Mark Gottschling on Apr 6, 2022
 *
 */
public class DDItemModelsProvider extends ItemModelProvider {

    public DDItemModelsProvider(PackOutput pack, ExistingFileHelper existingFileHelper) {
        super(pack, DD.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
    	// projectiles
        singleTexture(ModItems.PARALYSIS_SPELL_ITEM.getId().getPath(),
                mcLoc("item/generated"), "layer0", modLoc("item/slow"));
        
        singleTexture("harm",
                mcLoc("item/generated"), "layer0", modLoc("item/harm"));

        singleTexture("disarm",
                mcLoc("item/generated"), "layer0", modLoc("item/disarm"));

        singleTexture("disintegrate",
                mcLoc("item/generated"), "layer0", modLoc("item/disintegrate"));

        singleTexture("rock",
                mcLoc("item/generated"), "layer0", modLoc("item/rock"));
    	
        // weapons
        singleTexture("club",
        		mcLoc("item/handheld"), "layer0", modLoc("item/club"));
        
        singleTexture("spiked_club",
        		mcLoc("item/handheld"), "layer0", modLoc("item/spiked_club"));

        singleTexture(ModItems.RUSTY_IRON_AXE1.getId().getPath(),
                modLoc("item/single_edge_sword"), "layer0", modLoc("item/" + ModItems.RUSTY_IRON_AXE1.getId().getPath()));
        singleTexture(ModItems.RUSTY_IRON_AXE2.getId().getPath(),
                modLoc("item/single_edge_sword"), "layer0", modLoc("item/" + ModItems.RUSTY_IRON_AXE2.getId().getPath()));
        singleTexture(ModItems.RUSTY_IRON_SWORD1.getId().getPath(),
                mcLoc("item/handheld"), "layer0", modLoc("item/"
                        + ModItems.RUSTY_IRON_SWORD1.getId().getPath()));
        singleTexture(ModItems.RUSTY_IRON_SWORD2.getId().getPath(),
                mcLoc("item/handheld"), "layer0", modLoc("item/"
                        + ModItems.RUSTY_IRON_SWORD2.getId().getPath()));
        singleTexture(ModItems.RUSTY_IRON_SWORD3.getId().getPath(),
                mcLoc("item/handheld"), "layer0", modLoc("item/"
                        + ModItems.RUSTY_IRON_SWORD3.getId().getPath()));
        singleTexture(ModItems.RUSTY_IRON_SWORD4.getId().getPath(),
                modLoc("item/single_edge_sword"), "layer0", modLoc("item/"
                        + ModItems.RUSTY_IRON_SWORD4.getId().getPath()));
        singleTexture(ModItems.SHADOW_BLADE.getId().getPath(),
                modLoc("item/single_edge_sword"), "layer0", modLoc("item/" + ModItems.SHADOW_BLADE.getId().getPath()));
        singleTexture(ModItems.SHADOW_FALCHION.getId().getPath(),
                modLoc("item/single_edge_sword"), "layer0", modLoc("item/" + ModItems.SHADOW_FALCHION.getId().getPath()));



        // eggs
    	withExistingParent(ModItems.HEADLESS_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
    	withExistingParent(ModItems.ORC_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
    	withExistingParent(ModItems.GHOUL_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
    	withExistingParent(ModItems.SEWER_GHOUL_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
    	withExistingParent(ModItems.RAT_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
    	withExistingParent(ModItems.BEHOLDER_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.DEATH_TYRANT_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.GAZER_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.SPECTATOR_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
    	withExistingParent(ModItems.BOULDER_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
    	withExistingParent(ModItems.SHADOW_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
    	withExistingParent(ModItems.SHADOWLORD_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
    	withExistingParent(ModItems.DAEMON_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.SKELETON_WARRIOR_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.WINGED_SKELETON_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.IRON_SKELETON_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.MAGMA_SKELETON_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.GARGOYLE_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.MARGOYLE_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));

    }
}
