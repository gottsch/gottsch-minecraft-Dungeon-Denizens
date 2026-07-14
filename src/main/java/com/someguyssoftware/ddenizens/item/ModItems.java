package com.someguyssoftware.ddenizens.item;

import com.someguyssoftware.ddenizens.DD;
import com.someguyssoftware.ddenizens.entity.ModEntities;
import com.someguyssoftware.ddenizens.util.LangUtil;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static com.someguyssoftware.ddenizens.entity.ModEntities.*;

/**
 * Item registration: owns the {@link #ITEMS} and {@link #CREATIVE_MODE_TABS} deferred registries
 * plus every spawn egg, weapon, and spell/projectile item, and the single Dungeon Denizens creative
 * tab (displays only items).
 *
 * @author by Mark Gottschling on 7/3/2025
 */
public class ModItems {
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, DD.MODID);
	public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, DD.MODID);

	// mod eggs
	public static final RegistryObject<Item> HEADLESS_EGG = ITEMS.register(HEADLESS + "_egg", () -> new ForgeSpawnEggItem(ModEntities.HEADLESS_ENTITY_TYPE, 0xc8b486, 0x6f5e48, new Item.Properties()));
	public static final RegistryObject<Item> ORC_EGG = ITEMS.register(ORC + "_egg", () -> new ForgeSpawnEggItem(ModEntities.ORC_ENTITY_TYPE, 0x6b8e4e, 0x3f5c2c, new Item.Properties()));
	public static final RegistryObject<Item> ORC_SHAMAN_EGG = ITEMS.register(ORC_SHAMAN + "_egg", () -> new ForgeSpawnEggItem(ModEntities.ORC_SHAMAN_ENTITY_TYPE, 0x6b8e4e, 0x4b2e83, new Item.Properties()));
	public static final RegistryObject<Item> GHOUL_EGG = ITEMS.register(GHOUL + "_egg", () -> new ForgeSpawnEggItem(ModEntities.GHOUL_ENTITY_TYPE, 0x9fc2b8, 0x4f5b56, new Item.Properties()));
	public static final RegistryObject<Item> SEWER_GHOUL_EGG = ITEMS.register(SEWER_GHOUL + "_egg", () -> new ForgeSpawnEggItem(ModEntities.SEWER_GHOUL_ENTITY_TYPE, 0x5a6650, 0x3d4636, new Item.Properties()));
	public static final RegistryObject<Item> RAT_EGG = ITEMS.register(RAT + "_egg", () -> new ForgeSpawnEggItem(ModEntities.RAT_ENTITY_TYPE, 0x7a5c3e, 0x4a3521, new Item.Properties()));
	public static final RegistryObject<Item> ALLIGATOR_GAR_EGG = ITEMS.register(ALLIGATOR_GAR + "_egg", () -> new ForgeSpawnEggItem(ModEntities.ALLIGATOR_GAR_ENTITY_TYPE, 0x4a5c3a, 0x8f9f6b, new Item.Properties()));

	public static final RegistryObject<Item> BEHOLDER_EGG = ITEMS.register(BEHOLDER + "_egg", () -> new BeholderEggItem(ModEntities.BEHOLDER_ENTITY_TYPE, 0x871e00, 0xc15227, new Item.Properties()));
	public static final RegistryObject<Item> DEATH_TYRANT_EGG = ITEMS.register(DEATH_TYRANT + "_egg", () -> new DeathTyrantEggItem(ModEntities.DEATH_TYRANT_TYPE, 0x86765a, 0xcdc3bb, new Item.Properties()));

	public static final RegistryObject<Item> GAZER_EGG = ITEMS.register(GAZER + "_egg", () -> new GazerEggItem(ModEntities.GAZER_ENTITY_TYPE, 0x7a2e2f, 0xd4af37, new Item.Properties()));
	public static final RegistryObject<Item> SPECTATOR_EGG = ITEMS.register(SPECTATOR + "_egg", () -> new SpectatorEggItem(ModEntities.SPECTATOR_TYPE, 0x344133, 0xabb685, new Item.Properties()));

	public static final RegistryObject<Item> BOULDER_EGG = ITEMS.register(BOULDER + "_egg", () -> new ForgeSpawnEggItem(ModEntities.BOULDER_ENTITY_TYPE, 0x5c5c5c, 0x8f8f8f, new Item.Properties()));
	public static final RegistryObject<Item> SHADOW_EGG = ITEMS.register(SHADOW + "_egg", () -> new ShadowEggItem(ModEntities.SHADOW_ENTITY_TYPE, 0x000000, 0x2b2b2b, new Item.Properties()));
	public static final RegistryObject<Item> SHADOWLORD_EGG = ITEMS.register(SHADOWLORD + "_egg", () -> new ShadowlordEggItem(ModEntities.SHADOWLORD_ENTITY_TYPE, 0x000000, 0x6c6c6c, new Item.Properties()));
	public static final RegistryObject<Item> DAEMON_EGG = ITEMS.register(DAEMON + "_egg", () -> new DaemonEggItem(ModEntities.DAEMON_ENTITY_TYPE, 0xff0000, 0xff8c00, new Item.Properties()));

	public static final RegistryObject<Item> SKELETON_WARRIOR_EGG = ITEMS.register(SKELETON_WARRIOR + "_egg", () -> new SkeletonWarriorEggItem(ModEntities.SKELETON_WARRIOR_TYPE, 0xf5f6d2, 0xcdc3bb, new Item.Properties()));
	public static final RegistryObject<Item> WINGED_SKELETON_EGG = ITEMS.register(WINGED_SKELETON + "_egg", () -> new WingedSkeletonEggItem(ModEntities.WINGED_SKELETON_TYPE, 0xe2ded0, 0x8a8478, new Item.Properties()));

	public static final RegistryObject<Item> IRON_SKELETON_EGG = ITEMS.register(IRON_SKELETON + "_egg", () -> new IronSkeletonEggItem(ModEntities.IRON_SKELETON_TYPE, 0xc8c8d0, 0x6e6e78, new Item.Properties()));
	public static final RegistryObject<Item> MAGMA_SKELETON_EGG = ITEMS.register(MAGMA_SKELETON + "_egg", () -> new MagmaSkeletonEggItem(ModEntities.MAGMA_SKELETON_TYPE, 0x4b0000, 0xff7900, new Item.Properties()));
	public static final RegistryObject<Item> FROST_SKELETON_EGG = ITEMS.register(FROST_SKELETON + "_egg", () -> new ForgeSpawnEggItem(ModEntities.FROST_SKELETON_TYPE, 0x3d5878, 0xd6f4ff, new Item.Properties()));
	public static final RegistryObject<Item> TAINTED_SKELETON_EGG = ITEMS.register(TAINTED_SKELETON + "_egg", () -> new ForgeSpawnEggItem(ModEntities.TAINTED_SKELETON_TYPE, 0xa8a596, 0x8a1420, new Item.Properties()));
	public static final RegistryObject<Item> ACID_SKELETON_EGG = ITEMS.register(ACID_SKELETON + "_egg", () -> new ForgeSpawnEggItem(ModEntities.ACID_SKELETON_TYPE, 0xb2e278, 0x2c4e1c, new Item.Properties()));
	public static final RegistryObject<Item> ELECTRIC_SKELETON_EGG = ITEMS.register(ELECTRIC_SKELETON + "_egg", () -> new ForgeSpawnEggItem(ModEntities.ELECTRIC_SKELETON_TYPE, 0x2d3a6e, 0xf5e642, new Item.Properties()));
	public static final RegistryObject<Item> BURNING_SKELETON_EGG = ITEMS.register(BURNING_SKELETON + "_egg", () -> new ForgeSpawnEggItem(ModEntities.BURNING_SKELETON_TYPE, 0x1c1512, 0xffb02e, new Item.Properties()));
	public static final RegistryObject<Item> BLOODY_BONES_EGG = ITEMS.register(BLOODY_BONES + "_egg", () -> new ForgeSpawnEggItem(ModEntities.BLOODY_BONES_TYPE, 0xe8e0d0, 0x8a1420, new Item.Properties()));
	public static final RegistryObject<Item> BLOATER_EGG = ITEMS.register(BLOATER + "_egg", () -> new ForgeSpawnEggItem(ModEntities.BLOATER_TYPE, 0x6c8a2c, 0x3a4a1e, new Item.Properties()));
	public static final RegistryObject<Item> GRAVE_ZOMBIE_EGG = ITEMS.register(GRAVE_ZOMBIE + "_egg", () -> new ForgeSpawnEggItem(ModEntities.GRAVE_ZOMBIE_TYPE, 0x8b5a2b, 0x3e2711, new Item.Properties()));
	public static final RegistryObject<Item> WIGHT_EGG = ITEMS.register(WIGHT + "_egg", () -> new ForgeSpawnEggItem(ModEntities.WIGHT_TYPE, 0xced1d6, 0x2a2c33, new Item.Properties()));
	public static final RegistryObject<Item> BODAK_EGG = ITEMS.register(BODAK + "_egg", () -> new ForgeSpawnEggItem(ModEntities.BODAK_TYPE, 0x6b6a63, 0x2b2a26, new Item.Properties()));
	public static final RegistryObject<Item> SHRIEKER_EGG = ITEMS.register(SHRIEKER + "_egg", () -> new ForgeSpawnEggItem(ModEntities.SHRIEKER_TYPE, 0x8f4f8f, 0xc9a24a, new Item.Properties()));
	public static final RegistryObject<Item> VIOLET_FUNGUS_EGG = ITEMS.register(VIOLET_FUNGUS + "_egg", () -> new ForgeSpawnEggItem(ModEntities.VIOLET_FUNGUS_TYPE, 0x6e2878, 0x231528, new Item.Properties()));
	public static final RegistryObject<Item> GELATINOUS_CUBE_EGG = ITEMS.register(GELATINOUS_CUBE + "_egg", () -> new ForgeSpawnEggItem(ModEntities.GELATINOUS_CUBE_TYPE, 0x9adfc7, 0x5fae95, new Item.Properties()));
	public static final RegistryObject<Item> OCHRE_JELLY_EGG = ITEMS.register(OCHRE_JELLY + "_egg", () -> new ForgeSpawnEggItem(ModEntities.OCHRE_JELLY_TYPE, 0xadb83d, 0x5f6b1f, new Item.Properties()));
	public static final RegistryObject<Item> GRAY_OOZE_EGG = ITEMS.register(GRAY_OOZE + "_egg", () -> new ForgeSpawnEggItem(ModEntities.GRAY_OOZE_TYPE, 0x7d8a8f, 0x4a5459, new Item.Properties()));
	public static final RegistryObject<Item> VANILLA_CHEST_MIMIC_EGG = ITEMS.register(VANILLA_CHEST_MIMIC + "_egg", () -> new ForgeSpawnEggItem(ModEntities.VANILLA_CHEST_MIMIC_TYPE, 0x8f691d, 0x2f3a24, new Item.Properties()));
	public static final RegistryObject<Item> BARREL_MIMIC_EGG = ITEMS.register(BARREL_MIMIC + "_egg", () -> new ForgeSpawnEggItem(ModEntities.BARREL_MIMIC_TYPE, 0x9f854d, 0x54452c, new Item.Properties()));
	public static final RegistryObject<Item> SKELETON_CHAMPION_EGG = ITEMS.register(SKELETON_CHAMPION + "_egg", () -> new ForgeSpawnEggItem(ModEntities.SKELETON_CHAMPION_TYPE, 0xc9c9c9, 0x17171a, new Item.Properties()));

	public static final RegistryObject<Item> GARGOYLE_EGG = ITEMS.register(GARGOYLE + "_egg", () -> new GargoyleEggItem(ModEntities.GARGOYLE_TYPE, 0x6d6d81, 0x373b41, new Item.Properties()));
	public static final RegistryObject<Item> MARGOYLE_EGG = ITEMS.register(MARGOYLE + "_egg", () -> new MargoyleEggItem(ModEntities.MARGOYLE_TYPE, 0x7f7f7f, 0x5a6d41, new Item.Properties()));

	public static final RegistryObject<Item> ANIMATED_ARMOR_EGG = ITEMS.register(ANIMATED_ARMOR + "_egg", () -> new ForgeSpawnEggItem(ModEntities.ANIMATED_ARMOR_TYPE, 0xc0c0c8, 0x4a4a52, new Item.Properties()));
	public static final RegistryObject<Item> ANIMATED_WEAPON_EGG = ITEMS.register(ANIMATED_WEAPON + "_egg", () -> new ForgeSpawnEggItem(ModEntities.ANIMATED_WEAPON_TYPE, 0xa8adb5, 0xc9a227, new Item.Properties()));

	// projectiles
	public static final RegistryObject<Item> PARALYSIS_SPELL_ITEM = ITEMS.register(PARALYSIS_SPELL, () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> HARM_SPELL_ITEM = ITEMS.register(HARM_SPELL, () -> new Item(new Item.Properties()));
	// icon is a hand-authored model (assets/ddenizens/models/item/withering_gaze.json) referencing
	// gmm's textures/item/withering_gaze_spell.png directly -- no ItemModelProvider entry needed.
	// That texture began as one of gmm's orb_black particle-palette candidates (textures/particle/),
	// renamed into gmm's item textures once claimed here so the remaining orb_* colors stay available
	// as an unclaimed palette for future use.
	public static final RegistryObject<Item> WITHERING_GAZE_SPELL_ITEM = ITEMS.register(WITHERING_GAZE_SPELL, () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> DISINTEGRATE_SPELL_ITEM = ITEMS.register(DISINTEGRATE_SPELL, () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> DISARM_SPELL_ITEM = ITEMS.register(DISARM_SPELL, () -> new Item(new Item.Properties()));

	public static final RegistryObject<Item> ROCK_ITEM = ITEMS.register(ROCK, () -> new Item(new Item.Properties()));

	// creative tab icon (not displayed in the tab itself)
	public static final RegistryObject<Item> TAB_ICON = ITEMS.register("tab", () -> new Item(new Item.Properties()));

	/*
	 * weapons
	 */
	// club is equal to an stone sword but slower
	public static final RegistryObject<Item> CLUB = ITEMS.register("club", () -> new SwordItem(Tiers.WOOD, 4, -3.0F, new Item.Properties()));
	// spiked club is equal to an iron sword but slower
	public static final RegistryObject<Item> SPIKED_CLUB = ITEMS.register("spiked_club", () -> new SwordItem(Tiers.WOOD, 5, -3.0F, new Item.Properties()));

	public static final RegistryObject<Item> RUSTY_IRON_SWORD1 = ITEMS.register("rusty_iron_sword", () -> new SwordItem(Tiers.IRON, 3, -2.4F, new Item.Properties()));
	public static final RegistryObject<Item> RUSTY_IRON_SWORD2 = ITEMS.register("rusty_iron_sword_2", () -> new SwordItem(Tiers.IRON, 3, -2.4F, new Item.Properties()));
	public static final RegistryObject<Item> RUSTY_IRON_SWORD3 = ITEMS.register("rusty_iron_sword_3", () -> new SwordItem(Tiers.IRON, 3, -2.4F, new Item.Properties()));
	public static final RegistryObject<Item> RUSTY_IRON_SWORD4 = ITEMS.register("rusty_iron_sword_4", () -> new SwordItem(Tiers.IRON, 3, -2.4F, new Item.Properties()));
	public static final RegistryObject<Item> RUSTY_IRON_AXE1 = ITEMS.register("rusty_iron_axe", () -> new AxeItem(Tiers.IRON, 6F, -3F, new Item.Properties()));
	public static final RegistryObject<Item> RUSTY_IRON_AXE2 = ITEMS.register("rusty_iron_axe_2", () -> new AxeItem(Tiers.IRON, 6F, -3F, new Item.Properties()));
	public static final RegistryObject<Item> SHADOW_BLADE = ITEMS.register("shadow_blade", () -> new SwordItem(Tiers.IRON, 3, -2F, new Item.Properties()) {
		@Override
		public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
			super.appendHoverText(stack, level, tooltip, flag);
			tooltip.add(Component.literal(""));
			tooltip.add(Component.translatable(LangUtil.tooltip("shadow_blade.bonus_damage")));
		}
	});
	public static final RegistryObject<Item> SHADOW_FALCHION = ITEMS.register("shadow_falchion", () -> new SwordItem(Tiers.IRON, 3, -2F, new Item.Properties()) {
		@Override
		public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
			super.appendHoverText(stack, level, tooltip, flag);
			tooltip.add(Component.literal(""));
			tooltip.add(Component.translatable(LangUtil.tooltip("shadow_falchion.bonus_damage")));


		}
	});

	// skeleton champion's blade pool -- pristine steel, same stats as a vanilla iron sword
	public static final RegistryObject<Item> ROYAL_OATH = ITEMS.register("royal_oath", () -> new SwordItem(Tiers.IRON, 3, -2.4F, new Item.Properties()));
	public static final RegistryObject<Item> KINGSHIVER = ITEMS.register("kingshiver", () -> new SwordItem(Tiers.IRON, 3, -2.4F, new Item.Properties()));
	public static final RegistryObject<Item> BLACKFANG_SWORD = ITEMS.register("blackfang_sword", () -> new SwordItem(Tiers.IRON, 3, -2.4F, new Item.Properties()));

	/*
	 * a single creative tab holding all Dungeon Denizens content (spawn eggs, weapons, projectile items),
	 * so everything is available in one place (handy for testing all mobs).
	 */
	public static final RegistryObject<CreativeModeTab> DD_TAB = CREATIVE_MODE_TABS.register("dungeon_denizens", () -> CreativeModeTab.builder()
			.title(Component.translatable("itemGroup.ddenizens.dungeon_denizens"))
			.icon(() -> new ItemStack(TAB_ICON.get()))
			.displayItems((params, output) -> {
				// spawn eggs
				output.accept(HEADLESS_EGG.get());
				output.accept(ORC_EGG.get());
					output.accept(ORC_SHAMAN_EGG.get());
				output.accept(GHOUL_EGG.get());
				output.accept(SEWER_GHOUL_EGG.get());
				output.accept(RAT_EGG.get());
				output.accept(ALLIGATOR_GAR_EGG.get());
				output.accept(BOULDER_EGG.get());
				output.accept(SHADOW_EGG.get());
				output.accept(SHADOWLORD_EGG.get());
				output.accept(BEHOLDER_EGG.get());
				output.accept(DEATH_TYRANT_EGG.get());
				output.accept(GAZER_EGG.get());
				output.accept(SPECTATOR_EGG.get());
				output.accept(DAEMON_EGG.get());
				output.accept(SKELETON_WARRIOR_EGG.get());
				output.accept(WINGED_SKELETON_EGG.get());
				output.accept(IRON_SKELETON_EGG.get());
				output.accept(MAGMA_SKELETON_EGG.get());
				output.accept(FROST_SKELETON_EGG.get());
				output.accept(TAINTED_SKELETON_EGG.get());
				output.accept(ACID_SKELETON_EGG.get());
				output.accept(ELECTRIC_SKELETON_EGG.get());
				output.accept(BURNING_SKELETON_EGG.get());
				output.accept(BLOODY_BONES_EGG.get());
				output.accept(BLOATER_EGG.get());
				output.accept(GRAVE_ZOMBIE_EGG.get());
				output.accept(WIGHT_EGG.get());
				output.accept(BODAK_EGG.get());
				output.accept(SHRIEKER_EGG.get());
				output.accept(VIOLET_FUNGUS_EGG.get());
				output.accept(GELATINOUS_CUBE_EGG.get());
				output.accept(OCHRE_JELLY_EGG.get());
				output.accept(GRAY_OOZE_EGG.get());
				output.accept(VANILLA_CHEST_MIMIC_EGG.get());
				output.accept(BARREL_MIMIC_EGG.get());
				output.accept(GARGOYLE_EGG.get());
				output.accept(MARGOYLE_EGG.get());
				output.accept(ANIMATED_ARMOR_EGG.get());
				output.accept(ANIMATED_WEAPON_EGG.get());
				// weapons
				output.accept(CLUB.get());
				output.accept(SPIKED_CLUB.get());
				output.accept(RUSTY_IRON_SWORD1.get());
				output.accept(RUSTY_IRON_SWORD2.get());
				output.accept(RUSTY_IRON_SWORD3.get());
				output.accept(RUSTY_IRON_SWORD4.get());
				output.accept(RUSTY_IRON_AXE1.get());
				output.accept(RUSTY_IRON_AXE2.get());
				output.accept(SHADOW_BLADE.get());
				output.accept(SHADOW_FALCHION.get());
				output.accept(ROYAL_OATH.get());
				output.accept(KINGSHIVER.get());
				output.accept(BLACKFANG_SWORD.get());
				// projectile / misc items
				output.accept(ROCK_ITEM.get());
				output.accept(PARALYSIS_SPELL_ITEM.get());
				output.accept(HARM_SPELL_ITEM.get());
				output.accept(WITHERING_GAZE_SPELL_ITEM.get());
				output.accept(DISINTEGRATE_SPELL_ITEM.get());
				output.accept(DISARM_SPELL_ITEM.get());
			})
			.build());

	public static void init() {
		var eventBus = FMLJavaModLoadingContext.get().getModEventBus();
		ITEMS.register(eventBus);
		CREATIVE_MODE_TABS.register(eventBus);
	}
}
