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
        add(ModEntities.GHOUL_ENTITY_TYPE.get(), "Ghoul");
        add(ModItems.GHOUL_EGG.get(), "Ghoul Spawn Egg");
        add(ModEntities.SEWER_GHOUL_ENTITY_TYPE.get(), "Sewer Ghoul");
        add(ModItems.SEWER_GHOUL_EGG.get(), "Sewer Ghoul Spawn Egg");
        add(ModEntities.RAT_ENTITY_TYPE.get(), "Rat");
        add(ModItems.RAT_EGG.get(), "Rat Spawn Egg");

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

        add(ModEntities.GARGOYLE_TYPE.get(), "Gargoyle");
        add(ModItems.GARGOYLE_EGG.get(), "Gargoyle Spawn Egg");

        add(ModEntities.MARGOYLE_TYPE.get(), "Margoyle");
        add(ModItems.MARGOYLE_EGG.get(), "Margoyle Spawn Egg");

        add(ModItems.PARALYSIS_SPELL_ITEM.get(), "Slow Spell");
        add(ModItems.HARM_SPELL_ITEM.get(), "Harm Spell");
        add(ModItems.DISINTEGRATE_SPELL_ITEM.get(), "Disintegrate Spell");
        add(ModItems.DISARM_SPELL_ITEM.get(), "Disarm Spell");

        add(ModItems.ROCK_ITEM.get(), "Rock");
        
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
