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
import com.someguyssoftware.ddenizens.setup.Registration;

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
//        add("itemGroup." + TAB_NAME, "Tutorial");
    	add(Registration.HEADLESS_ENTITY_TYPE.get(), "Headless");
        add(Registration.HEADLESS_EGG.get(), "Headless Spawn Egg");
    	add(Registration.ORC_ENTITY_TYPE.get(), "Orc");
        add(Registration.ORC_EGG.get(), "Orc Spawn Egg");
        add(Registration.GHOUL_ENTITY_TYPE.get(), "Ghoul");
        add(Registration.GHOUL_EGG.get(), "Ghoul Spawn Egg");

        add(Registration.BEHOLDER_ENTITY_TYPE.get(), "Beholder");
        add(Registration.BEHOLDER_EGG.get(), "Beholder Spawn Egg");

        add(Registration.DEATH_TYRANT_TYPE.get(), "Death Tyrant");
        add(Registration.DEATH_TYRANT_EGG.get(), "Death Tyrant Spawn Egg");

        add(Registration.GAZER_ENTITY_TYPE.get(), "Gazer");
        add(Registration.GAZER_EGG.get(), "Gazer Spawn Egg");

        add(Registration.SPECTATOR_TYPE.get(), "Spectator");
        add(Registration.SPECTATOR_EGG.get(), "Spectator Spawn Egg");
        
        add(Registration.BOULDER_ENTITY_TYPE.get(), "Boulder");
        add(Registration.BOULDER_EGG.get(), "Boulder Spawn Egg");
        
        add(Registration.SHADOW_ENTITY_TYPE.get(), "Shadow");
        add(Registration.SHADOW_EGG.get(), "Shadow Spawn Egg");
        
        add(Registration.SHADOWLORD_ENTITY_TYPE.get(), "Shadowlord");
        add(Registration.SHADOWLORD_EGG.get(), "Shadowlord Spawn Egg");
        
        add(Registration.DAEMON_ENTITY_TYPE.get(), "Daemon");
        add(Registration.DAEMON_EGG.get(), "Daemon Spawn Egg");

        add(Registration.SKELETON_WARRIOR_TYPE.get(), "Skeleton Warrior");
        add(Registration.SKELETON_WARRIOR_EGG.get(), "Skeleton Warrior Spawn Egg");

        add(Registration.WINGED_SKELETON_TYPE.get(), "Winged Skeleton");
        add(Registration.WINGED_SKELETON_EGG.get(), "Winged Skeleton Spawn Egg");

        add(Registration.FOSSILIZED_SKELETON_TYPE.get(), "Fossilized Skeleton");
        add(Registration.FOSSILIZED_SKELETON_EGG.get(), "Fossilized Skeleton Spawn Egg");

        add(Registration.IRON_SKELETON_TYPE.get(), "Iron Skeleton");
        add(Registration.IRON_SKELETON_EGG.get(), "Iron Skeleton Spawn Egg");

        add(Registration.MAGMA_SKELETON_TYPE.get(), "Magma Skeleton");
        add(Registration.MAGMA_SKELETON_EGG.get(), "Magma Skeleton Spawn Egg");

        add(ModEntities.GARGOYLE_TYPE.get(), "Gargoyle");
        add(ModItems.GARGOYLE_EGG.get(), "Gargoyle Spawn Egg");

        add(ModEntities.MARGOYLE_TYPE.get(), "Margoyle");
        add(ModItems.MARGOYLE_EGG.get(), "Margoyle Spawn Egg");

        add(Registration.PARALYSIS_SPELL_ITEM.get(), "Slow Spell");
        add(Registration.HARM_SPELL_ITEM.get(), "Harm Spell");
        add(Registration.DISINTEGRATE_SPELL_ITEM.get(), "Disintegrate Spell");
        add(Registration.DISARM_SPELL_ITEM.get(), "Disarm Spell");

        add(Registration.ROCK_ITEM.get(), "Rock");
        
        add(Registration.CLUB.get(), "Club");
        add(Registration.SPIKED_CLUB.get(), "Spiked Club");
        add(Registration.RUSTY_IRON_AXE1.get(), "Rusty Iron Axe");
        add(Registration.RUSTY_IRON_AXE2.get(), "Rusty Iron Axe");
        add(Registration.RUSTY_IRON_SWORD1.get(), "Rusty Iron Sword");
        add(Registration.RUSTY_IRON_SWORD2.get(), "Rusty Iron Sword");
        add(Registration.RUSTY_IRON_SWORD3.get(), "Rusty Iron Sword");
        add(Registration.RUSTY_IRON_SWORD4.get(), "Rusty Iron Sword");
        add(Registration.SHADOW_BLADE.get(), "Shadow Blade");
        add(Registration.SHADOW_FALCHION.get(), "Shadow Falchion");

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
