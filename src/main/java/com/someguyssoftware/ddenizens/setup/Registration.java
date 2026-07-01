/*
 * This file is part of  Dungeon Denizens.
 * Copyright (c) 2022 Mark Gottschling (gottsch)
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
package com.someguyssoftware.ddenizens.setup;

import com.google.common.collect.Lists;
import com.someguyssoftware.ddenizens.DD;
import com.someguyssoftware.ddenizens.entity.ModEntities;
// v2.0: every DD mob now lives in gottsch's Monster Manual (gmm); the DD entity.monster package is empty
import mod.gottsch.forge.gmm.core.entity.monster.ghoul.Ghoul;
import mod.gottsch.forge.gmm.core.entity.monster.Headless;
import mod.gottsch.forge.gmm.core.entity.monster.Orc;
import mod.gottsch.forge.gmm.core.entity.monster.Shadow;
import mod.gottsch.forge.gmm.core.entity.monster.SkeletonWarrior;
import mod.gottsch.forge.gmm.core.entity.monster.IronSkeleton;
import mod.gottsch.forge.gmm.core.entity.monster.MagmaSkeleton;
import mod.gottsch.forge.gmm.core.entity.monster.WingedSkeleton;
import mod.gottsch.forge.gmm.core.entity.monster.Beholder;
import mod.gottsch.forge.gmm.core.entity.monster.DeathTyrant;
import mod.gottsch.forge.gmm.core.entity.monster.Gazer;
import mod.gottsch.forge.gmm.core.entity.monster.Spectator;
import mod.gottsch.forge.gmm.core.entity.monster.Boulder;
import mod.gottsch.forge.gmm.core.entity.monster.Daemon;
import mod.gottsch.forge.gmm.core.entity.monster.Shadowlord;
import com.someguyssoftware.ddenizens.entity.projectile.*;
import com.someguyssoftware.ddenizens.item.*;
import com.someguyssoftware.ddenizens.util.LangUtil;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Setup deferred registries. Original developer defined all block, items, entities etc here.
 * @author Mark Gottschling on Apr 1, 2022
 *
 */
public class Registration {
	public static final String HEADLESS = "headless";
	public static final String GHOUL = "ghoul";
	public static final String SHADOW = "shadow";
	public static final String SHADOWLORD = "shadowlord";
	public static final String BEHOLDER = "beholder";
	public static final String DEATH_TYRANT = "death_tyrant";
	public static final String SPECTATOR = "spectator";
	public static final String GAZER = "gazer";
	public static final String DAEMON = "daemon";
	public static final String BOULDER = "boulder";
	public static final String ORC = "orc";
	public static final String SKELETON_WARRIOR = "skeleton_warrior";
	public static final String WINGED_SKELETON = "winged_skeleton";
	public static final String IRON_SKELETON = "iron_skeleton";
	public static final String MAGMA_SKELETON = "magma_skeleton";
	public static final String SKELETON_CHAMPION = "skeleton_champion";
	public static final String DEATH_KNIGHT = "death_knight";

	
	// projectile names
	private static final String PARALYSIS_SPELL = "slow";
	private static final String HARM_SPELL = "harm";
	private static final String DISINTEGRATE_SPELL = "disintegrate";
	private static final String DISARM_SPELL = "disarm";
	private static final String FIRESPOUT_SPELL = "firespout";
	private static final String ROCK = "rock";

	/*
	 * deferred registries
	 */
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, DD.MODID);
	public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, DD.MODID);
	public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, DD.MODID);
	public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, DD.MODID);
	public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, DD.MODID);

	// mob collections
	public static final List<RegistryObject<?>> ALL_MOBS = Lists.newArrayList();
	
	// entities
	public static final RegistryObject<EntityType<Boulder>> BOULDER_ENTITY_TYPE = Registration.ENTITIES.register(BOULDER, () -> EntityType.Builder.of(Boulder::new, MobCategory.AMBIENT)
			.sized(1F, 1F)
			.clientTrackingRange(8)
			.setShouldReceiveVelocityUpdates(false)
			.setTrackingRange(20)
			.fireImmune()
			.build(BOULDER));
	
	public static final RegistryObject<EntityType<Headless>> HEADLESS_ENTITY_TYPE = Registration.ENTITIES.register(HEADLESS, () -> EntityType.Builder.of(Headless::new, MobCategory.MONSTER)
			.sized(0.65F, 1.5F)
			.clientTrackingRange(8)
			.setShouldReceiveVelocityUpdates(false)
			.setTrackingRange(50)
			.build(HEADLESS));
	
	public static final RegistryObject<EntityType<Orc>> ORC_ENTITY_TYPE = Registration.ENTITIES.register(ORC, () -> EntityType.Builder.of(Orc::new, MobCategory.MONSTER)
			.sized(1F, 1.99F)
			.clientTrackingRange(12)
			.setShouldReceiveVelocityUpdates(false)
			.build(ORC));
	
	public static final RegistryObject<EntityType<Ghoul>> GHOUL_ENTITY_TYPE = Registration.ENTITIES.register(GHOUL, () -> EntityType.Builder.of(Ghoul::new, MobCategory.MONSTER)
			.sized(0.6F, 1.68F)
			.clientTrackingRange(8)
			.setShouldReceiveVelocityUpdates(false)
			.setTrackingRange(20)
			.build(GHOUL));

	public static final RegistryObject<EntityType<Beholder>> BEHOLDER_ENTITY_TYPE = Registration.ENTITIES.register(BEHOLDER, () -> EntityType.Builder.of(Beholder::new, MobCategory.MONSTER)
			.sized(2.25F, 3.5F)
			.clientTrackingRange(8)
			.setShouldReceiveVelocityUpdates(false)
			.setTrackingRange(20)
			.fireImmune()
			.build(BEHOLDER));

	public static final RegistryObject<EntityType<DeathTyrant>> DEATH_TYRANT_TYPE = Registration.ENTITIES.register(DEATH_TYRANT, () -> EntityType.Builder.of(DeathTyrant::new, MobCategory.MONSTER)
			.sized(2.25F, 3.5F)
			.clientTrackingRange(8)
			.setShouldReceiveVelocityUpdates(false)
			.setTrackingRange(20)
			.fireImmune()
			.build(DEATH_TYRANT));

	public static final RegistryObject<EntityType<Gazer>> GAZER_ENTITY_TYPE = Registration.ENTITIES.register(GAZER, () -> EntityType.Builder.of(Gazer::new, MobCategory.MONSTER)
			.sized(1.125F, 1.25F)
			.clientTrackingRange(8)
			.setShouldReceiveVelocityUpdates(false)
			.setTrackingRange(20)
			.fireImmune()
			.build(GAZER));

	public static final RegistryObject<EntityType<Spectator>> SPECTATOR_TYPE = Registration.ENTITIES.register(SPECTATOR, () -> EntityType.Builder.of(Spectator::new, MobCategory.MONSTER)
			.sized(0.84375F, 1.21875F)
			.clientTrackingRange(8)
			.setShouldReceiveVelocityUpdates(false)
			.setTrackingRange(20)
			.build(SPECTATOR));
	
	public static final RegistryObject<EntityType<Shadow>> SHADOW_ENTITY_TYPE = Registration.ENTITIES.register(SHADOW, () -> EntityType.Builder.of(Shadow::new, MobCategory.MONSTER)
			.sized(0.75F, 1.95F)
			.clientTrackingRange(12)
			.setShouldReceiveVelocityUpdates(false)
			.build(SHADOW));
	
	public static final RegistryObject<EntityType<Shadowlord>> SHADOWLORD_ENTITY_TYPE = Registration.ENTITIES.register(SHADOWLORD, () -> EntityType.Builder.of(Shadowlord::new, MobCategory.MONSTER)
			.sized(1.25F, 2.8125F)
			.clientTrackingRange(12)
			.setShouldReceiveVelocityUpdates(false)
			.build(SHADOWLORD));
	
	public static final RegistryObject<EntityType<Daemon>> DAEMON_ENTITY_TYPE = Registration.ENTITIES.register(DAEMON, () -> EntityType.Builder.of(Daemon::new, MobCategory.MONSTER)
			.sized(1F, 3F)
			.clientTrackingRange(12)
			.setShouldReceiveVelocityUpdates(false)
			.fireImmune()
			.build(DAEMON));

	public static final RegistryObject<EntityType<SkeletonWarrior>> SKELETON_WARRIOR_TYPE = Registration.ENTITIES.register(SKELETON_WARRIOR, () -> EntityType.Builder.of(SkeletonWarrior::new, MobCategory.MONSTER)
			.sized(0.6F, 1.95F)
			.clientTrackingRange(12)
			.setShouldReceiveVelocityUpdates(false)
			.build(SKELETON_WARRIOR));

	public static final RegistryObject<EntityType<WingedSkeleton>> WINGED_SKELETON_TYPE = Registration.ENTITIES.register(WINGED_SKELETON, () -> EntityType.Builder.of(WingedSkeleton::new, MobCategory.MONSTER)
			.sized(0.75F, 1.95F)
			.clientTrackingRange(12)
			.setShouldReceiveVelocityUpdates(false)
			.build(WINGED_SKELETON));

	public static final RegistryObject<EntityType<IronSkeleton>> IRON_SKELETON_TYPE = Registration.ENTITIES.register(IRON_SKELETON, () -> EntityType.Builder.of(IronSkeleton::new, MobCategory.MONSTER)
			.sized(0.63F, 2.1F)
			.clientTrackingRange(15)
			.setShouldReceiveVelocityUpdates(false)
			.build(IRON_SKELETON));

	public static final RegistryObject<EntityType<MagmaSkeleton>> MAGMA_SKELETON_TYPE = Registration.ENTITIES.register(MAGMA_SKELETON, () -> EntityType.Builder.of(MagmaSkeleton::new, MobCategory.MONSTER)
			.sized(0.63F, 2.1F)
			.clientTrackingRange(15)
			.setShouldReceiveVelocityUpdates(false)
			.fireImmune()
			.build(MAGMA_SKELETON));
	
	// projectile entities
	public static final RegistryObject<EntityType<ParalysisSpell>> PARALYSIS_SPELL_ENTITY_TYPE =
			Registration.ENTITIES.register(PARALYSIS_SPELL, () -> EntityType.Builder.of(ParalysisSpell::new, MobCategory.MISC)
			.sized(1F, 1F)
			.clientTrackingRange(12)
			.setShouldReceiveVelocityUpdates(false)
			.build(PARALYSIS_SPELL));
	
	public static final RegistryObject<EntityType<HarmSpell>> HARM_SPELL_ENTITY_TYPE =
			Registration.ENTITIES.register(HARM_SPELL, () -> EntityType.Builder.of(HarmSpell::new, MobCategory.MISC)
			.sized(1F, 1F)
			.clientTrackingRange(12)
			.setShouldReceiveVelocityUpdates(false)
			.build(HARM_SPELL));

	public static final RegistryObject<EntityType<DisarmSpell>> DISARM_SPELL_ENTITY_TYPE =
			Registration.ENTITIES.register(DISARM_SPELL, () -> EntityType.Builder.of(DisarmSpell::new, MobCategory.MISC)
					.sized(1F, 1F)
					.clientTrackingRange(12)
					.setShouldReceiveVelocityUpdates(false)
					.build(DISARM_SPELL));

	public static final RegistryObject<EntityType<DisintegrateSpell>> DISINTEGRATE_SPELL_ENTITY_TYPE =
			Registration.ENTITIES.register(DISINTEGRATE_SPELL, () -> EntityType.Builder.of(DisintegrateSpell::new, MobCategory.MISC)
					.sized(1F, 1F)
					.clientTrackingRange(12)
					.setShouldReceiveVelocityUpdates(false)
					.build(DISINTEGRATE_SPELL));
	
	public static final RegistryObject<EntityType<FireSpoutSpell>> FIRESPOUT_SPELL_ENTITY_TYPE =
			Registration.ENTITIES.register(FIRESPOUT_SPELL, () -> EntityType.Builder.of(FireSpoutSpell::new, MobCategory.MISC)
			.sized(0.5F, 0.5F)
			.clientTrackingRange(12)
			.setShouldReceiveVelocityUpdates(false)
			.build(FIRESPOUT_SPELL));
	
	public static final RegistryObject<EntityType<Rock>> ROCK_ENTITY_TYPE = 
			Registration.ENTITIES.register(ROCK, () -> EntityType.Builder.of(Rock::new, MobCategory.MISC)
			.sized(0.5F, 0.5F)
			.clientTrackingRange(12)
			.setShouldReceiveVelocityUpdates(false)
			.build(ROCK));
	
	/*
	 * items
	 */
	
	// mod eggs
	public static final RegistryObject<Item> HEADLESS_EGG = Registration.ITEMS.register(HEADLESS + "_egg", () -> new ForgeSpawnEggItem(HEADLESS_ENTITY_TYPE, 0xc8b486, 0x6f5e48, new Item.Properties()));
	public static final RegistryObject<Item> ORC_EGG = Registration.ITEMS.register(ORC + "_egg", () -> new ForgeSpawnEggItem(ORC_ENTITY_TYPE, 0xc8b486, 0x6f5e48, new Item.Properties()));
	public static final RegistryObject<Item> GHOUL_EGG = Registration.ITEMS.register(GHOUL + "_egg", () -> new ForgeSpawnEggItem(GHOUL_ENTITY_TYPE, 0x93aba3, 0x869e96, new Item.Properties()));

	public static final RegistryObject<Item> BEHOLDER_EGG = Registration.ITEMS.register(BEHOLDER + "_egg", () -> new BeholderEggItem(BEHOLDER_ENTITY_TYPE, 0x871e00, 0xc15227, new Item.Properties()));
	public static final RegistryObject<Item> DEATH_TYRANT_EGG = Registration.ITEMS.register(DEATH_TYRANT + "_egg", () -> new DeathTyrantEggItem(DEATH_TYRANT_TYPE, 0x86765a, 0xcdc3bb, new Item.Properties()));

	public static final RegistryObject<Item> GAZER_EGG = Registration.ITEMS.register(GAZER + "_egg", () -> new GazerEggItem(GAZER_ENTITY_TYPE, 0x7a2e2f, 0x63181d, new Item.Properties()));
	public static final RegistryObject<Item> SPECTATOR_EGG = Registration.ITEMS.register(SPECTATOR + "_egg", () -> new SpectatorEggItem(SPECTATOR_TYPE, 0x344133, 0xabb685, new Item.Properties()));

	public static final RegistryObject<Item> BOULDER_EGG = Registration.ITEMS.register(BOULDER + "_egg", () -> new ForgeSpawnEggItem(BOULDER_ENTITY_TYPE, 0x747474, 0x8f8f8f, new Item.Properties()));
	public static final RegistryObject<Item> SHADOW_EGG = Registration.ITEMS.register(SHADOW + "_egg", () -> new ShadowEggItem(SHADOW_ENTITY_TYPE, 0x000000, 0x2b2b2b, new Item.Properties()));
	public static final RegistryObject<Item> SHADOWLORD_EGG = Registration.ITEMS.register(SHADOWLORD + "_egg", () -> new ShadowlordEggItem(SHADOWLORD_ENTITY_TYPE, 0x000000, 0x6c6c6c, new Item.Properties()));
	public static final RegistryObject<Item> DAEMON_EGG = Registration.ITEMS.register(DAEMON + "_egg", () -> new DaemonEggItem(DAEMON_ENTITY_TYPE, 0xff0000, 0xfc0000, new Item.Properties()));

	public static final RegistryObject<Item> SKELETON_WARRIOR_EGG = Registration.ITEMS.register(SKELETON_WARRIOR + "_egg", () -> new SkeletonWarriorEggItem(SKELETON_WARRIOR_TYPE, 0xf5f6d2, 0xcdc3bb, new Item.Properties()));
	public static final RegistryObject<Item> WINGED_SKELETON_EGG = Registration.ITEMS.register(WINGED_SKELETON + "_egg", () -> new WingedSkeletonEggItem(WINGED_SKELETON_TYPE, 0xf5f6d2, 0xcdc3bb, new Item.Properties()));

	public static final RegistryObject<Item> IRON_SKELETON_EGG = Registration.ITEMS.register(IRON_SKELETON + "_egg", () -> new IronSkeletonEggItem(IRON_SKELETON_TYPE, 0xf5f6d2, 0xcdc3bb, new Item.Properties()));
	public static final RegistryObject<Item> MAGMA_SKELETON_EGG = Registration.ITEMS.register(MAGMA_SKELETON + "_egg", () -> new MagmaSkeletonEggItem(MAGMA_SKELETON_TYPE, 0x4b0000, 0xff7900, new Item.Properties()));

	// projectiles
	public static final RegistryObject<Item> PARALYSIS_SPELL_ITEM = Registration.ITEMS.register(PARALYSIS_SPELL, () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> HARM_SPELL_ITEM = Registration.ITEMS.register(HARM_SPELL, () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> DISINTEGRATE_SPELL_ITEM = Registration.ITEMS.register(DISINTEGRATE_SPELL, () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> DISARM_SPELL_ITEM = Registration.ITEMS.register(DISARM_SPELL, () -> new Item(new Item.Properties()));

//	public static final RegistryObject<Item> FIRESPOUT_ITEM = Registration.ITEMS.register("firespout", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> ROCK_ITEM = Registration.ITEMS.register(ROCK, () -> new Item(new Item.Properties()));
	
	/*
	 *  weapons
	 */
	// club is equal to an stone sword but slower
	public static final RegistryObject<Item> CLUB = Registration.ITEMS.register("club", () -> new SwordItem(Tiers.WOOD, 4, -3.0F, new Item.Properties()));
	// spiked club is equal to an iron sword but slower
	public static final RegistryObject<Item> SPIKED_CLUB = Registration.ITEMS.register("spiked_club", () -> new SwordItem(Tiers.WOOD, 5, -3.0F, new Item.Properties()));

	public static final RegistryObject<Item> RUSTY_IRON_SWORD1 = Registration.ITEMS.register("rusty_iron_sword", () -> new SwordItem(Tiers.IRON, 3, -2.4F, new Item.Properties()));
	public static final RegistryObject<Item> RUSTY_IRON_SWORD2 = Registration.ITEMS.register("rusty_iron_sword_2", () -> new SwordItem(Tiers.IRON, 3, -2.4F, new Item.Properties()));
	public static final RegistryObject<Item> RUSTY_IRON_SWORD3 = Registration.ITEMS.register("rusty_iron_sword_3", () -> new SwordItem(Tiers.IRON, 3, -2.4F, new Item.Properties()));
	public static final RegistryObject<Item> RUSTY_IRON_SWORD4 = Registration.ITEMS.register("rusty_iron_sword_4", () -> new SwordItem(Tiers.IRON, 3, -2.4F, new Item.Properties()));
	public static final RegistryObject<Item> RUSTY_IRON_AXE1 = Registration.ITEMS.register("rusty_iron_axe", () -> new AxeItem(Tiers.IRON, 6F, -3F, new Item.Properties()));
	public static final RegistryObject<Item> RUSTY_IRON_AXE2 = Registration.ITEMS.register("rusty_iron_axe_2", () -> new AxeItem(Tiers.IRON, 6F, -3F, new Item.Properties()));
	public static final RegistryObject<Item> SHADOW_BLADE = Registration.ITEMS.register("shadow_blade", () -> new SwordItem(Tiers.IRON, 3, -2F, new Item.Properties()) {
		@Override
		public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
			super.appendHoverText(stack, level, tooltip, flag);
			tooltip.add(Component.literal(""));
			tooltip.add(Component.translatable(LangUtil.tooltip("shadow_blade.bonus_damage")));
		}
	});
	public static final RegistryObject<Item> SHADOW_FALCHION = Registration.ITEMS.register("shadow_falchion", () -> new SwordItem(Tiers.IRON, 3, -2F, new Item.Properties()) {
		@Override
		public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
			super.appendHoverText(stack, level, tooltip, flag);
			tooltip.add(Component.literal(""));
			tooltip.add(Component.translatable(LangUtil.tooltip("shadow_falchion.bonus_damage")));


		}
	});

	public static final RegistryObject<SoundEvent> AMBIENT_DAEMON = registerSoundEvent("ambient_daemon");
	public static final RegistryObject<SoundEvent> AMBIENT_BEHOLDER = registerSoundEvent("ambient_beholder");
	public static final RegistryObject<SoundEvent> AMBIENT_DEATH_TYRANT = registerSoundEvent("ambient_death_tyrant");
	public static final RegistryObject<SoundEvent> AMBIENT_GAZER = registerSoundEvent("ambient_gazer");
	public static final RegistryObject<SoundEvent> AMBIENT_SPECTATOR = registerSoundEvent("ambient_spectator");
	public static final RegistryObject<SoundEvent> AMBIENT_SHADOWLORD = registerSoundEvent("ambient_shadowlord");
	public static final RegistryObject<SoundEvent> AMBIENT_SHADOW = registerSoundEvent("ambient_shadow");
	public static final RegistryObject<SoundEvent> SHADOWLORD_STEP = registerSoundEvent("shadowlord_step");
	public static final RegistryObject<SoundEvent> WINGED_SKELETON_FLAP = registerSoundEvent("winged_skeleton_flap");

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
				output.accept(ModItems.GARGOYLE_EGG.get());
				output.accept(ModItems.MARGOYLE_EGG.get());
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

	// NOTE must add mob to ALL_MOBS collection in order to register them to the biomes - see CommonSetup.onBiomeLoading
	// NOTE 7/3/2025 - this doesn't apply to 1.20.1+ as Biomes are handled in data files.
	static {
		ALL_MOBS.add(HEADLESS_ENTITY_TYPE);
		ALL_MOBS.add(GHOUL_ENTITY_TYPE);
		ALL_MOBS.add(BOULDER_ENTITY_TYPE);
		ALL_MOBS.add(SHADOW_ENTITY_TYPE);
		ALL_MOBS.add(BEHOLDER_ENTITY_TYPE);
		ALL_MOBS.add(DEATH_TYRANT_TYPE);
		ALL_MOBS.add(GAZER_ENTITY_TYPE);
		ALL_MOBS.add(SPECTATOR_TYPE);
		ALL_MOBS.add(SHADOWLORD_ENTITY_TYPE);
		ALL_MOBS.add(DAEMON_ENTITY_TYPE);
		ALL_MOBS.add(ORC_ENTITY_TYPE);
		ALL_MOBS.add(SKELETON_WARRIOR_TYPE);
		ALL_MOBS.add(WINGED_SKELETON_TYPE);
		ALL_MOBS.add(MAGMA_SKELETON_TYPE);
		ALL_MOBS.add(IRON_SKELETON_TYPE);
		ALL_MOBS.add(ModEntities.GARGOYLE_TYPE);
	}
	
	/**
	 * 
	 */
	public static void init() {
		IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
		ITEMS.register(eventBus);
		ENTITIES.register(eventBus);
		PARTICLES.register(eventBus);
		SOUNDS.register(eventBus);
		CREATIVE_MODE_TABS.register(eventBus);
	}

	private static RegistryObject<SoundEvent> registerSoundEvent(String name) {
		return SOUNDS.register(name, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(DD.MODID, name)));
	}
}
