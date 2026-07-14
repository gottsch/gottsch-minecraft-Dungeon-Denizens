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

        singleTexture(ModItems.TAB_ICON.getId().getPath(),
                mcLoc("item/generated"), "layer0", modLoc("item/tab"));

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

        singleTexture(ModItems.ROYAL_OATH.getId().getPath(),
                mcLoc("item/handheld"), "layer0", modLoc("item/" + ModItems.ROYAL_OATH.getId().getPath()));
        singleTexture(ModItems.KINGSHIVER.getId().getPath(),
                mcLoc("item/handheld"), "layer0", modLoc("item/" + ModItems.KINGSHIVER.getId().getPath()));
        singleTexture(ModItems.BLACKFANG_SWORD.getId().getPath(),
                mcLoc("item/handheld"), "layer0", modLoc("item/" + ModItems.BLACKFANG_SWORD.getId().getPath()));



        // eggs
        // NOTE: the orb-shaped template_gmm_egg (assets/ddenizens/models/item/template_gmm_egg.json +
        // egg_base/egg_secondary/egg_rim textures) was reverted after in-game testing — user didn't like
        // the orb look. Reverted to vanilla's item/template_spawn_egg below. The orb assets are left in
        // place (unused) for a future attempt at a different custom shape — see
        // ddenizens-forge-1.20.1-Handoff-Jul09.md §5d.
    	withExistingParent(ModItems.HEADLESS_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
    	withExistingParent(ModItems.ORC_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
    	withExistingParent(ModItems.ORC_SHAMAN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
    	withExistingParent(ModItems.GHOUL_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
    	withExistingParent(ModItems.SEWER_GHOUL_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
    	withExistingParent(ModItems.RAT_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
    	withExistingParent(ModItems.ALLIGATOR_GAR_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
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
        withExistingParent(ModItems.FROST_SKELETON_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.TAINTED_SKELETON_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.ACID_SKELETON_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.ELECTRIC_SKELETON_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.BURNING_SKELETON_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.BLOODY_BONES_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.BLOATER_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.GRAVE_ZOMBIE_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.WIGHT_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.BODAK_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.SHRIEKER_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.VIOLET_FUNGUS_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.GELATINOUS_CUBE_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.OCHRE_JELLY_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.GRAY_OOZE_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.VANILLA_CHEST_MIMIC_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.BARREL_MIMIC_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.SKELETON_CHAMPION_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.GARGOYLE_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.MARGOYLE_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.ANIMATED_ARMOR_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.ANIMATED_WEAPON_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));

    }
}
