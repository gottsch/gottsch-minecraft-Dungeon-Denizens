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

import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.concurrent.CompletableFuture;

/**
 * 
 * @author Mark Gottschling on Apr 6, 2022
 *
 */
@Mod.EventBusSubscriber(modid = DD.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        if (event.includeServer()) {
            DDBlockTagsProvider blockTags = new DDBlockTagsProvider(output, lookupProvider, event.getExistingFileHelper());
            generator.addProvider(true, blockTags);
            generator.addProvider(true, new DDItemTagsProvider(output, lookupProvider, blockTags.contentsGetter(), event.getExistingFileHelper()));
            // populates gmm's ally entity-type tags with DD mobs (output -> data/gmm/...)
            generator.addProvider(true, new DDEntityTypeTagsProvider(output, lookupProvider));

//            generator.addProvider(new TutRecipes(generator));
//            generator.addProvider(new TutLootTables(generator));
//            TutBlockTags blockTags = new TutBlockTags(generator, event.getExistingFileHelper());
//            generator.addProvider(blockTags);
//            generator.addProvider(new DDItemTags(generator, blockTags, event.getExistingFileHelper()));
        }
        if (event.includeClient()) {
//            generator.addProvider(new TutBlockStates(generator, event.getExistingFileHelper()));
            generator.addProvider(true, new DDItemModelsProvider(output, event.getExistingFileHelper()));
            generator.addProvider(true, new DDLanguageProvider(output, "en_us"));
        }
    }
}