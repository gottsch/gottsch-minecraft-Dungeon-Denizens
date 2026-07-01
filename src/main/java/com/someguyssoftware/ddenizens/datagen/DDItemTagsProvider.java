package com.someguyssoftware.ddenizens.datagen;

import com.someguyssoftware.ddenizens.DD;
import com.someguyssoftware.ddenizens.setup.Registration;
import com.someguyssoftware.ddenizens.tags.DDTags;
import mod.gottsch.forge.gmm.core.tag.GMMTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class DDItemTagsProvider extends ItemTagsProvider {
    public DDItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookup,
                              CompletableFuture<TagLookup<Block>> blockTagProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookup, blockTagProvider, DD.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
//        tag(DDTags.Items.EGGS).add(Registration.BEHOLDER_EGG.get());

        tag(DDTags.Items.SKELETON_WARRIOR_WEAPONS).add(Registration.RUSTY_IRON_AXE1.get());
        tag(DDTags.Items.SKELETON_WARRIOR_WEAPONS).add(Registration.RUSTY_IRON_AXE2.get());
        tag(DDTags.Items.SKELETON_WARRIOR_WEAPONS).add(Registration.RUSTY_IRON_SWORD1.get());
        tag(DDTags.Items.SKELETON_WARRIOR_WEAPONS).add(Registration.RUSTY_IRON_SWORD2.get());
        tag(DDTags.Items.SKELETON_WARRIOR_WEAPONS).add(Registration.RUSTY_IRON_SWORD3.get());
        tag(DDTags.Items.SKELETON_WARRIOR_WEAPONS).add(Registration.RUSTY_IRON_SWORD4.get());
        tag(DDTags.Items.SKELETON_WARRIOR_WEAPONS).add(Items.GOLDEN_SWORD);
        tag(DDTags.Items.SKELETON_WARRIOR_WEAPONS).add(Items.GOLDEN_AXE);
        tag(DDTags.Items.SKELETON_WARRIOR_WEAPONS).add(Items.IRON_SWORD);
        tag(DDTags.Items.SKELETON_WARRIOR_WEAPONS).add(Items.IRON_AXE);
        tag(DDTags.Items.SKELETON_WARRIOR_WEAPONS).add(Items.STONE_SWORD);
        tag(DDTags.Items.SKELETON_WARRIOR_WEAPONS).add(Items.STONE_AXE);

        // gmm SkeletonWarrior equipment pools (consumed by gmm's tag-driven equipment selection)
        tag(GMMTags.Items.SKELETON_WARRIOR_WEAPONS)
                .add(Items.STONE_SWORD, Items.STONE_AXE, Items.GOLDEN_SWORD, Items.GOLDEN_AXE, Items.IRON_SWORD, Items.IRON_AXE)
                .add(Registration.RUSTY_IRON_AXE1.get(), Registration.RUSTY_IRON_AXE2.get(),
                     Registration.RUSTY_IRON_SWORD1.get(), Registration.RUSTY_IRON_SWORD2.get(),
                     Registration.RUSTY_IRON_SWORD3.get(), Registration.RUSTY_IRON_SWORD4.get());
        tag(GMMTags.Items.SKELETON_WARRIOR_HELMETS).add(Items.LEATHER_HELMET, Items.CHAINMAIL_HELMET, Items.GOLDEN_HELMET);
        tag(GMMTags.Items.SKELETON_WARRIOR_CHESTPLATES).add(Items.LEATHER_CHESTPLATE, Items.CHAINMAIL_CHESTPLATE);
        tag(GMMTags.Items.SKELETON_WARRIOR_LEGGINGS).add(Items.LEATHER_LEGGINGS, Items.CHAINMAIL_LEGGINGS);
        tag(GMMTags.Items.SKELETON_WARRIOR_BOOTS).add(Items.LEATHER_BOOTS, Items.CHAINMAIL_BOOTS);

        // gmm Orc weapon pool (consumed by gmm's tag-driven equipment selection)
        tag(GMMTags.Items.ORC_WEAPONS)
                .add(Items.IRON_SWORD, Items.IRON_AXE)
                .add(Registration.CLUB.get(), Registration.SPIKED_CLUB.get());

        // gmm Shadow: spawn weapon + shadow-bane weapons (gold is handled intrinsically in gmm)
        tag(GMMTags.Items.SHADOW_WEAPONS).add(Registration.SHADOW_FALCHION.get());
        tag(GMMTags.Items.SHADOW_BANE).add(Registration.SHADOW_BLADE.get());
        tag(GMMTags.Items.SHADOW_MINOR_BANE).add(Registration.SHADOW_FALCHION.get());
    }
}
