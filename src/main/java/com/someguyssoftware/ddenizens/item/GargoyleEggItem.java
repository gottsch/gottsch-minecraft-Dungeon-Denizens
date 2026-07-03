/*
 * This file is part of  Dungeon Denizens.
 * Copyright (c) 2025 Mark Gottschling (gottsch)
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
package com.someguyssoftware.ddenizens.item;

import com.someguyssoftware.ddenizens.util.LangUtil;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.function.Supplier;

/**
 * @author Mark Gottschling on July 7, 2025
 */
public class GargoyleEggItem extends DDEggItem {

    public GargoyleEggItem(Supplier<? extends EntityType<? extends Mob>> type, int backgroundColor, int highlightColor, Properties props) {
        super(type, backgroundColor, highlightColor, props);
    }

    @Override
    public void appendHoverText(ItemStack stack, Level worldIn, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, worldIn, tooltip, flag);

        LangUtil.appendAdvancedHoverText(tooltip, tt -> {
            tooltip.add(Component.translatable(LangUtil.tooltip("stats.number_appearing"), "1-2"));
            tooltip.add(Component.translatable(LangUtil.tooltip("stats.level"), Component.translatable(LangUtil.tooltip("stats.level.mob"))));
            tooltip.add(Component.translatable(LangUtil.tooltip("stats.rarity"),
                    Component.translatable(LangUtil.tooltip("stats.rarity.uncommon")))
            );
            tooltip.add(Component.translatable(LangUtil.tooltip("stats.health"), 25));
            tooltip.add(Component.translatable(LangUtil.tooltip("stats.damage"), 4));
            tooltip.add(Component.translatable(LangUtil.tooltip("stats.despawn"), Component.translatable(LangUtil.tooltip("boolean.yes"))));
            tooltip.add(Component.translatable(LangUtil.tooltip("stats.spawns"),
                    Component.translatable(LangUtil.tooltip("stats.spawns.overworld"))
                            .append(", ")
                            .append(Component.translatable(LangUtil.tooltip("stats.spawns.underworld")))
            ));
//            tooltip.add(Component.translatable(LangUtil.tooltip("stats.specials")));
//            tooltip.add(Component.literal("- Equipped with wooden sword or bow."));
        });
    }
}
