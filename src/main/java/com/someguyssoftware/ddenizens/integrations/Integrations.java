/*
 * This file is part of  Dungeon Denizens.
 * Copyright (c) 2024 Mark Gottschling (gottsch)
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
package com.someguyssoftware.ddenizens.integrations;

import com.someguyssoftware.ddenizens.DD;
import com.someguyssoftware.ddenizens.config.Config;
import mod.gottsch.forge.treasure2.api.TreasureApi;
import net.minecraftforge.fml.ModList;

public class Integrations {

    /**
     * @author Mark Gottschling on Feb 18, 2024
     *
     */
    public static void registerTreasure2Integration() {
        if (ModList.get().isLoaded("treasure2")) {
            DD.LOGGER.debug("treasure2 IS loaded");
            TreasureApi.registerLootTables(DD.MODID);
        }
    }
}
