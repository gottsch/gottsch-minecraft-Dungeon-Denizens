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
import com.someguyssoftware.ddenizens.entity.ModEntities;
import com.someguyssoftware.ddenizens.item.ModItems;

import com.someguyssoftware.ddenizens.util.LangUtil;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.LanguageProvider;

/**
 * 
 * @author Mark Gottschling on Apr 6, 2022
 *
 */
public class
DDLanguageProvider extends LanguageProvider {

    public DDLanguageProvider(PackOutput pack, String locale) {
        super(pack, DD.MODID, locale);
    }

    @Override
    protected void addTranslations() {
        add("itemGroup.ddenizens.dungeon_denizens", "Dungeon Denizens");
    	add(ModEntities.HEADLESS_ENTITY_TYPE.get(), "Headless");
        add(ModItems.HEADLESS_EGG.get(), "Headless Spawn Egg");
    	add(ModEntities.ORC_ENTITY_TYPE.get(), "Orc");
        add(ModItems.ORC_EGG.get(), "Orc Spawn Egg");
        add(ModEntities.ORC_SHAMAN_ENTITY_TYPE.get(), "Orc Shaman");
        add(ModItems.ORC_SHAMAN_EGG.get(), "Orc Shaman Spawn Egg");
        add(ModEntities.GHOUL_ENTITY_TYPE.get(), "Ghoul");
        add(ModItems.GHOUL_EGG.get(), "Ghoul Spawn Egg");
        add(ModEntities.SEWER_GHOUL_ENTITY_TYPE.get(), "Sewer Ghoul");
        add(ModItems.SEWER_GHOUL_EGG.get(), "Sewer Ghoul Spawn Egg");
        add(ModEntities.RAT_ENTITY_TYPE.get(), "Rat");
        add(ModItems.RAT_EGG.get(), "Rat Spawn Egg");
        add(ModEntities.ALLIGATOR_GAR_ENTITY_TYPE.get(), "Alligator Gar");
        add(ModItems.ALLIGATOR_GAR_EGG.get(), "Alligator Gar Spawn Egg");

        add(ModEntities.BEHOLDER_ENTITY_TYPE.get(), "Beholder");
        add(ModItems.BEHOLDER_EGG.get(), "Beholder Spawn Egg");

        add(ModEntities.DEATH_TYRANT_TYPE.get(), "Death Tyrant");
        add(ModItems.DEATH_TYRANT_EGG.get(), "Death Tyrant Spawn Egg");

        add(ModEntities.GAZER_ENTITY_TYPE.get(), "Gazer");
        add(ModItems.GAZER_EGG.get(), "Gazer Spawn Egg");

        add(ModEntities.SPECTATOR_TYPE.get(), "Spectator");
        add(ModItems.SPECTATOR_EGG.get(), "Spectator Spawn Egg");
        
        add(ModEntities.BOULDER_ENTITY_TYPE.get(), "Boulder");
        add(ModItems.BOULDER_EGG.get(), "Boulder Spawn Egg");
        
        add(ModEntities.SHADOW_ENTITY_TYPE.get(), "Shadow");
        add(ModItems.SHADOW_EGG.get(), "Shadow Spawn Egg");
        
        add(ModEntities.SHADOWLORD_ENTITY_TYPE.get(), "Shadowlord");
        add(ModItems.SHADOWLORD_EGG.get(), "Shadowlord Spawn Egg");
        
        add(ModEntities.DAEMON_ENTITY_TYPE.get(), "Daemon");
        add(ModItems.DAEMON_EGG.get(), "Daemon Spawn Egg");

        add(ModEntities.SKELETON_WARRIOR_TYPE.get(), "Skeleton Warrior");
        add(ModItems.SKELETON_WARRIOR_EGG.get(), "Skeleton Warrior Spawn Egg");

        add(ModEntities.WINGED_SKELETON_TYPE.get(), "Winged Skeleton");
        add(ModItems.WINGED_SKELETON_EGG.get(), "Winged Skeleton Spawn Egg");

        add(ModEntities.IRON_SKELETON_TYPE.get(), "Iron Skeleton");
        add(ModItems.IRON_SKELETON_EGG.get(), "Iron Skeleton Spawn Egg");

        add(ModEntities.MAGMA_SKELETON_TYPE.get(), "Magma Skeleton");
        add(ModItems.MAGMA_SKELETON_EGG.get(), "Magma Skeleton Spawn Egg");

        add(ModEntities.FROST_SKELETON_TYPE.get(), "Frost Skeleton");
        add(ModItems.FROST_SKELETON_EGG.get(), "Frost Skeleton Spawn Egg");

        add(ModEntities.TAINTED_SKELETON_TYPE.get(), "Tainted Skeleton");
        add(ModItems.TAINTED_SKELETON_EGG.get(), "Tainted Skeleton Spawn Egg");

        add(ModEntities.ACID_SKELETON_TYPE.get(), "Acid Skeleton");
        add(ModItems.ACID_SKELETON_EGG.get(), "Acid Skeleton Spawn Egg");

        add(ModEntities.ELECTRIC_SKELETON_TYPE.get(), "Electric Skeleton");
        add(ModItems.ELECTRIC_SKELETON_EGG.get(), "Electric Skeleton Spawn Egg");

        add(ModEntities.BURNING_SKELETON_TYPE.get(), "Burning Skeleton");
        add(ModItems.BURNING_SKELETON_EGG.get(), "Burning Skeleton Spawn Egg");

        add(ModEntities.BLOODY_BONES_TYPE.get(), "Bloody Bones");
        add(ModItems.BLOODY_BONES_EGG.get(), "Bloody Bones Spawn Egg");

        add(ModEntities.BLOATER_TYPE.get(), "Bloater");
        add(ModItems.BLOATER_EGG.get(), "Bloater Spawn Egg");

        add(ModEntities.GRAVE_ZOMBIE_TYPE.get(), "Grave Zombie");
        add(ModItems.GRAVE_ZOMBIE_EGG.get(), "Grave Zombie Spawn Egg");

        add(ModEntities.WIGHT_TYPE.get(), "Wight");
        add(ModItems.WIGHT_EGG.get(), "Wight Spawn Egg");

        add(ModEntities.BODAK_TYPE.get(), "Bodak");
        add(ModItems.BODAK_EGG.get(), "Bodak Spawn Egg");

        add(ModEntities.SKELETON_CHAMPION_TYPE.get(), "Skeleton Champion");
        add(ModItems.SKELETON_CHAMPION_EGG.get(), "Skeleton Champion Spawn Egg");

        add(ModEntities.SHRIEKER_TYPE.get(), "Shrieker");
        add(ModItems.SHRIEKER_EGG.get(), "Shrieker Spawn Egg");

        add(ModEntities.VIOLET_FUNGUS_TYPE.get(), "Violet Fungus");
        add(ModItems.VIOLET_FUNGUS_EGG.get(), "Violet Fungus Spawn Egg");

        add(ModEntities.GELATINOUS_CUBE_TYPE.get(), "Gelatinous Cube");
        add(ModItems.GELATINOUS_CUBE_EGG.get(), "Gelatinous Cube Spawn Egg");

        add(ModEntities.OCHRE_JELLY_TYPE.get(), "Ochre Jelly");
        add(ModItems.OCHRE_JELLY_EGG.get(), "Ochre Jelly Spawn Egg");

        add(ModEntities.GRAY_OOZE_TYPE.get(), "Gray Ooze");
        add(ModItems.GRAY_OOZE_EGG.get(), "Gray Ooze Spawn Egg");

        add(ModEntities.VANILLA_CHEST_MIMIC_TYPE.get(), "Chest Mimic");
        add(ModItems.VANILLA_CHEST_MIMIC_EGG.get(), "Chest Mimic Spawn Egg");

        add(ModEntities.BARREL_MIMIC_TYPE.get(), "Barrel Mimic");
        add(ModItems.BARREL_MIMIC_EGG.get(), "Barrel Mimic Spawn Egg");

        add(ModEntities.GARGOYLE_TYPE.get(), "Gargoyle");
        add(ModItems.GARGOYLE_EGG.get(), "Gargoyle Spawn Egg");

        add(ModEntities.MARGOYLE_TYPE.get(), "Margoyle");
        add(ModItems.MARGOYLE_EGG.get(), "Margoyle Spawn Egg");

        add(ModItems.PARALYSIS_SPELL_ITEM.get(), "Slow Spell");
        add(ModItems.HARM_SPELL_ITEM.get(), "Harm Spell");
        add(ModItems.WITHERING_GAZE_SPELL_ITEM.get(), "Withering Gaze Spell");
        add(ModItems.DISINTEGRATE_SPELL_ITEM.get(), "Disintegrate Spell");
        add(ModItems.DISARM_SPELL_ITEM.get(), "Disarm Spell");

        add(ModItems.ROCK_ITEM.get(), "Rock");
        add(ModItems.TAB_ICON.get(), "Dungeon Denizens Tab Icon");

        add(ModItems.CLUB.get(), "Club");
        add(ModItems.SPIKED_CLUB.get(), "Spiked Club");
        add(ModItems.RUSTY_IRON_AXE1.get(), "Rusty Iron Axe");
        add(ModItems.RUSTY_IRON_AXE2.get(), "Rusty Iron Axe");
        add(ModItems.RUSTY_IRON_SWORD1.get(), "Rusty Iron Sword");
        add(ModItems.RUSTY_IRON_SWORD2.get(), "Rusty Iron Sword");
        add(ModItems.RUSTY_IRON_SWORD3.get(), "Rusty Iron Sword");
        add(ModItems.RUSTY_IRON_SWORD4.get(), "Rusty Iron Sword");
        add(ModItems.SHADOW_BLADE.get(), "Shadow Blade");
        add(ModItems.SHADOW_FALCHION.get(), "Shadow Falchion");
        add(ModItems.ROYAL_OATH.get(), "Royal Oath");
        add(ModItems.KINGSHIVER.get(), "Kingshiver");
        add(ModItems.BLACKFANG_SWORD.get(), "Blackfang Sword");

        add(LangUtil.tooltip("boolean.yes"), "Yes");
        add(LangUtil.tooltip("boolean.no"), "No");
        add(LangUtil.tooltip("hold_shift"), "Hold [SHIFT] to expand");
        add(LangUtil.tooltip("stats.number_appearing"), "Number: %s");
        add(LangUtil.tooltip("stats.size"), "Size: %s");
        add(LangUtil.tooltip("stats.size.small"), "Small");
        add(LangUtil.tooltip("stats.size.medium"), "Medium");
        add(LangUtil.tooltip("stats.size.large"), "Large");
        add(LangUtil.tooltip("stats.size.huge"), "Huge");
        add(LangUtil.tooltip("stats.level"), "Level: %s");
        add(LangUtil.tooltip("stats.level.mob"), "Mob");
        add(LangUtil.tooltip("stats.level.mini_boss"), "Mini-Boss");
        add(LangUtil.tooltip("stats.level.boss"), "Boss");
        add(LangUtil.tooltip("stats.rarity"), "Rarity: %s");
        add(LangUtil.tooltip("stats.rarity.common"), "Common");
        add(LangUtil.tooltip("stats.rarity.uncommon"), "Uncommon");
        add(LangUtil.tooltip("stats.rarity.scarce"), "Scarce");
        add(LangUtil.tooltip("stats.rarity.rare"), "Rare");
        add(LangUtil.tooltip("stats.rarity.very_rare"), "Very Rare");
        add(LangUtil.tooltip("stats.movement"), "Movement: %s");
        add(LangUtil.tooltip("stats.movement.flies"), "Flies/Hovers");
        add(LangUtil.tooltip("stats.movement.walks"), "Walks");
        add(LangUtil.tooltip("stats.speed"), "Speed: %s");
        add(LangUtil.tooltip("stats.speed.normal"), "Normal");
        add(LangUtil.tooltip("stats.health"), "Health: %s");
        add(LangUtil.tooltip("stats.damage"), "Damage: %s");
        add(LangUtil.tooltip("stats.daylight"), "Daylight: %s");
        add(LangUtil.tooltip("stats.despawn"), "Despawn: %s");
        add(LangUtil.tooltip("stats.spawns"), "Spawns: %s");
        add(LangUtil.tooltip("stats.spawns.overworld"), "Overworld");
        add(LangUtil.tooltip("stats.spawns.underworld"), "Underworld");
        add(LangUtil.tooltip("stats.spawns.nether"), "Nether");
        add(LangUtil.tooltip("stats.specials"), "Weapons/Specials:");
        add(LangUtil.tooltip("stats.weakness"), "Weakness:");
        add(LangUtil.tooltip("shadow_falchion.bonus_damage"), "+1 Attack Damage vs. Shadowspawn.");
        add(LangUtil.tooltip("shadow_blade.bonus_damage"), "+2 Attack Damage vs. Shadowspawn.");

    }
}
