package com.someguyssoftware.ddenizens.datagen;

import com.someguyssoftware.ddenizens.setup.Registration;
import mod.gottsch.forge.gmm.core.tag.GMMTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;

import java.util.concurrent.CompletableFuture;

/**
 * Populates gmm's (consumer-owned) ally entity-type tags with Dungeon Denizens' mobs, so gmm's
 * shared, tag-driven alert goals know which DD mobs to alert. gmm owns the tag KEYS; this provider
 * (a DD provider) contributes entries to them — output lands in data/gmm/... because the tag keys
 * are gmm-namespaced.
 *
 * @author Mark Gottschling
 */
public class DDEntityTypeTagsProvider extends EntityTypeTagsProvider {

    public DDEntityTypeTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        // a hurt Headless alerts other Headless, Gazers, and Beholders
        tag(GMMTags.EntityTypes.HEADLESS_HURT_ALLIES)
                .add(Registration.HEADLESS_ENTITY_TYPE.get(),
                     Registration.GAZER_ENTITY_TYPE.get(),
                     Registration.BEHOLDER_ENTITY_TYPE.get());
        // a Headless that acquires a target by sight alerts other Headless and Gazers
        tag(GMMTags.EntityTypes.HEADLESS_TARGET_ALLIES)
                .add(Registration.HEADLESS_ENTITY_TYPE.get(),
                     Registration.GAZER_ENTITY_TYPE.get());
    }
}
