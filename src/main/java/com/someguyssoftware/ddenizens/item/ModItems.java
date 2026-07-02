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
	public static final RegistryObject<Item> ORC_EGG = ITEMS.register(ORC + "_egg", () -> new ForgeSpawnEggItem(ModEntities.ORC_ENTITY_TYPE, 0xc8b486, 0x6f5e48, new Item.Properties()));
	public static final RegistryObject<Item> GHOUL_EGG = ITEMS.register(GHOUL + "_egg", () -> new ForgeSpawnEggItem(ModEntities.GHOUL_ENTITY_TYPE, 0x93aba3, 0x869e96, new Item.Properties()));
	public static final RegistryObject<Item> SEWER_GHOUL_EGG = ITEMS.register(SEWER_GHOUL + "_egg", () -> new ForgeSpawnEggItem(ModEntities.SEWER_GHOUL_ENTITY_TYPE, 0x5a6650, 0x3d4636, new Item.Properties()));
	public static final RegistryObject<Item> RAT_EGG = ITEMS.register(RAT + "_egg", () -> new ForgeSpawnEggItem(ModEntities.RAT_ENTITY_TYPE, 0x6e6459, 0x413a30, new Item.Properties()));

	public static final RegistryObject<Item> BEHOLDER_EGG = ITEMS.register(BEHOLDER + "_egg", () -> new BeholderEggItem(ModEntities.BEHOLDER_ENTITY_TYPE, 0x871e00, 0xc15227, new Item.Properties()));
	public static final RegistryObject<Item> DEATH_TYRANT_EGG = ITEMS.register(DEATH_TYRANT + "_egg", () -> new DeathTyrantEggItem(ModEntities.DEATH_TYRANT_TYPE, 0x86765a, 0xcdc3bb, new Item.Properties()));

	public static final RegistryObject<Item> GAZER_EGG = ITEMS.register(GAZER + "_egg", () -> new GazerEggItem(ModEntities.GAZER_ENTITY_TYPE, 0x7a2e2f, 0x63181d, new Item.Properties()));
	public static final RegistryObject<Item> SPECTATOR_EGG = ITEMS.register(SPECTATOR + "_egg", () -> new SpectatorEggItem(ModEntities.SPECTATOR_TYPE, 0x344133, 0xabb685, new Item.Properties()));

	public static final RegistryObject<Item> BOULDER_EGG = ITEMS.register(BOULDER + "_egg", () -> new ForgeSpawnEggItem(ModEntities.BOULDER_ENTITY_TYPE, 0x747474, 0x8f8f8f, new Item.Properties()));
	public static final RegistryObject<Item> SHADOW_EGG = ITEMS.register(SHADOW + "_egg", () -> new ShadowEggItem(ModEntities.SHADOW_ENTITY_TYPE, 0x000000, 0x2b2b2b, new Item.Properties()));
	public static final RegistryObject<Item> SHADOWLORD_EGG = ITEMS.register(SHADOWLORD + "_egg", () -> new ShadowlordEggItem(ModEntities.SHADOWLORD_ENTITY_TYPE, 0x000000, 0x6c6c6c, new Item.Properties()));
	public static final RegistryObject<Item> DAEMON_EGG = ITEMS.register(DAEMON + "_egg", () -> new DaemonEggItem(ModEntities.DAEMON_ENTITY_TYPE, 0xff0000, 0xfc0000, new Item.Properties()));

	public static final RegistryObject<Item> SKELETON_WARRIOR_EGG = ITEMS.register(SKELETON_WARRIOR + "_egg", () -> new SkeletonWarriorEggItem(ModEntities.SKELETON_WARRIOR_TYPE, 0xf5f6d2, 0xcdc3bb, new Item.Properties()));
	public static final RegistryObject<Item> WINGED_SKELETON_EGG = ITEMS.register(WINGED_SKELETON + "_egg", () -> new WingedSkeletonEggItem(ModEntities.WINGED_SKELETON_TYPE, 0xf5f6d2, 0xcdc3bb, new Item.Properties()));

	public static final RegistryObject<Item> IRON_SKELETON_EGG = ITEMS.register(IRON_SKELETON + "_egg", () -> new IronSkeletonEggItem(ModEntities.IRON_SKELETON_TYPE, 0xf5f6d2, 0xcdc3bb, new Item.Properties()));
	public static final RegistryObject<Item> MAGMA_SKELETON_EGG = ITEMS.register(MAGMA_SKELETON + "_egg", () -> new MagmaSkeletonEggItem(ModEntities.MAGMA_SKELETON_TYPE, 0x4b0000, 0xff7900, new Item.Properties()));

	public static final RegistryObject<Item> GARGOYLE_EGG = ITEMS.register(GARGOYLE + "_egg", () -> new GargoyleEggItem(ModEntities.GARGOYLE_TYPE, 0x6d6d81, 0x373b41, new Item.Properties()));
	public static final RegistryObject<Item> MARGOYLE_EGG = ITEMS.register(MARGOYLE + "_egg", () -> new MargoyleEggItem(ModEntities.MARGOYLE_TYPE, 0x7f7f7f, 0x5a6d41, new Item.Properties()));

	// projectiles
	public static final RegistryObject<Item> PARALYSIS_SPELL_ITEM = ITEMS.register(PARALYSIS_SPELL, () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> HARM_SPELL_ITEM = ITEMS.register(HARM_SPELL, () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> DISINTEGRATE_SPELL_ITEM = ITEMS.register(DISINTEGRATE_SPELL, () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> DISARM_SPELL_ITEM = ITEMS.register(DISARM_SPELL, () -> new Item(new Item.Properties()));

	public static final RegistryObject<Item> ROCK_ITEM = ITEMS.register(ROCK, () -> new Item(new Item.Properties()));

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

	/*
	 * a single creative tab holding all Dungeon Denizens content (spawn eggs, weapons, projectile items),
	 * so everything is available in one place (handy for testing all mobs).
	 */
	public static final RegistryObject<CreativeModeTab> DD_TAB = CREATIVE_MODE_TABS.register("dungeon_denizens", () -> CreativeModeTab.builder()
			.title(Component.translatable("itemGroup.ddenizens.dungeon_denizens"))
			.icon(() -> new ItemStack(BEHOLDER_EGG.get()))
			.displayItems((params, output) -> {
				// spawn eggs
				output.accept(HEADLESS_EGG.get());
				output.accept(ORC_EGG.get());
				output.accept(GHOUL_EGG.get());
				output.accept(SEWER_GHOUL_EGG.get());
				output.accept(RAT_EGG.get());
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
				output.accept(GARGOYLE_EGG.get());
				output.accept(MARGOYLE_EGG.get());
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
				// projectile / misc items
				output.accept(ROCK_ITEM.get());
				output.accept(PARALYSIS_SPELL_ITEM.get());
				output.accept(HARM_SPELL_ITEM.get());
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
