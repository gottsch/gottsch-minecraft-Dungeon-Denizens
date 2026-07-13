package com.someguyssoftware.ddenizens.datagen;
import com.someguyssoftware.ddenizens.entity.ModEntities;

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
                .add(ModEntities.HEADLESS_ENTITY_TYPE.get(),
                     ModEntities.GAZER_ENTITY_TYPE.get(),
                     ModEntities.BEHOLDER_ENTITY_TYPE.get());
        // a Headless that acquires a target by sight alerts other Headless and Gazers
        tag(GMMTags.EntityTypes.HEADLESS_TARGET_ALLIES)
                .add(ModEntities.HEADLESS_ENTITY_TYPE.get(),
                     ModEntities.GAZER_ENTITY_TYPE.get());

        // a rallied skeleton champion buffs nearby tagged pack-mates -- the vanilla skeleton plus DD's
        // own rank-and-file skeleton variants, so the rally has an actual pack to lead.
        tag(GMMTags.EntityTypes.SKELETON_CHAMPION_RALLY_ALLIES)
                .add(net.minecraft.world.entity.EntityType.SKELETON,
                     ModEntities.SKELETON_WARRIOR_TYPE.get(),
                     ModEntities.WINGED_SKELETON_TYPE.get(),
                     ModEntities.IRON_SKELETON_TYPE.get(),
                     ModEntities.MAGMA_SKELETON_TYPE.get(),
                     ModEntities.FROST_SKELETON_TYPE.get(),
                     ModEntities.ACID_SKELETON_TYPE.get(),
                     ModEntities.ELECTRIC_SKELETON_TYPE.get(),
                     ModEntities.BURNING_SKELETON_TYPE.get(),
                     ModEntities.BLOODY_BONES_TYPE.get());

        // things the Burning Skeleton's flame (aura/bite/death-burst) won't ignite -- the vanilla
        // undead roster plus every GMM/DD skeleton-and-undead variant, so it doesn't cook its own
        // packmates standing next to it in a dungeon room.
        tag(GMMTags.EntityTypes.BURNING_SKELETON_IGNITE_IMMUNE)
                .add(net.minecraft.world.entity.EntityType.SKELETON,
                     net.minecraft.world.entity.EntityType.STRAY,
                     net.minecraft.world.entity.EntityType.WITHER_SKELETON,
                     net.minecraft.world.entity.EntityType.ZOMBIE,
                     net.minecraft.world.entity.EntityType.HUSK,
                     net.minecraft.world.entity.EntityType.DROWNED,
                     net.minecraft.world.entity.EntityType.ZOMBIE_VILLAGER,
                     ModEntities.SKELETON_WARRIOR_TYPE.get(),
                     ModEntities.WINGED_SKELETON_TYPE.get(),
                     ModEntities.IRON_SKELETON_TYPE.get(),
                     ModEntities.MAGMA_SKELETON_TYPE.get(),
                     ModEntities.FROST_SKELETON_TYPE.get(),
                     ModEntities.TAINTED_SKELETON_TYPE.get(),
                     ModEntities.ACID_SKELETON_TYPE.get(),
                     ModEntities.ELECTRIC_SKELETON_TYPE.get(),
                     ModEntities.BURNING_SKELETON_TYPE.get(),
                     ModEntities.BLOODY_BONES_TYPE.get(),
                     ModEntities.SKELETON_CHAMPION_TYPE.get(),
                     ModEntities.BLOATER_TYPE.get(),
                     ModEntities.GRAVE_ZOMBIE_TYPE.get(),
                     ModEntities.WIGHT_TYPE.get(),
                     ModEntities.BODAK_TYPE.get());

        // a Shrieker's pulse calls in a broad set of common combat-capable dungeon/cave hostiles --
        // deliberately excludes ambush-disguise mobs (mimics/oozes, whose whole identity is surprise)
        // and passive/ambient mobs (Boulder, Rat, Alligator Gar).
        tag(GMMTags.EntityTypes.SHRIEKER_ALLIES)
                .add(ModEntities.HEADLESS_ENTITY_TYPE.get(),
                     ModEntities.GAZER_ENTITY_TYPE.get(),
                     ModEntities.BEHOLDER_ENTITY_TYPE.get(),
                     ModEntities.GHOUL_ENTITY_TYPE.get(),
                     ModEntities.SEWER_GHOUL_ENTITY_TYPE.get(),
                     ModEntities.ORC_ENTITY_TYPE.get(),
                     ModEntities.ORC_SHAMAN_ENTITY_TYPE.get(),
                     ModEntities.SKELETON_WARRIOR_TYPE.get(),
                     ModEntities.WINGED_SKELETON_TYPE.get(),
                     ModEntities.IRON_SKELETON_TYPE.get(),
                     ModEntities.MAGMA_SKELETON_TYPE.get(),
                     ModEntities.FROST_SKELETON_TYPE.get(),
                     ModEntities.ACID_SKELETON_TYPE.get(),
                     ModEntities.ELECTRIC_SKELETON_TYPE.get(),
                     ModEntities.BURNING_SKELETON_TYPE.get(),
                     ModEntities.BLOODY_BONES_TYPE.get(),
                     ModEntities.BLOATER_TYPE.get(),
                     ModEntities.GRAVE_ZOMBIE_TYPE.get(),
                     ModEntities.WIGHT_TYPE.get(),
                     ModEntities.BODAK_TYPE.get(),
                     ModEntities.SKELETON_CHAMPION_TYPE.get());
    }
}
