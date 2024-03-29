package com.someguyssoftware.ddenizens.setup;

import java.util.List;

import com.google.common.collect.Lists;
import com.someguyssoftware.ddenizens.DD;
import com.someguyssoftware.ddenizens.capability.GhoulCapability;
import com.someguyssoftware.ddenizens.entity.monster.Boulder;
import com.someguyssoftware.ddenizens.entity.monster.Daemon;
import com.someguyssoftware.ddenizens.entity.monster.Ettin;
import com.someguyssoftware.ddenizens.entity.monster.Gazer;
import com.someguyssoftware.ddenizens.entity.monster.Ghoul;
import com.someguyssoftware.ddenizens.entity.monster.Headless;
import com.someguyssoftware.ddenizens.entity.monster.Orc;
import com.someguyssoftware.ddenizens.entity.monster.Shadow;
import com.someguyssoftware.ddenizens.entity.monster.Shadowlord;
import com.someguyssoftware.ddenizens.entity.projectile.FireSpout;
import com.someguyssoftware.ddenizens.entity.projectile.Harmball;
import com.someguyssoftware.ddenizens.entity.projectile.Rock;
import com.someguyssoftware.ddenizens.entity.projectile.Slowball;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 * Setup deferred registries. Original developer defined all block, items, entities etc here.
 * @author Mark Gottschling on Apr 1, 2022
 *
 */
public class Registration {
	public static final String HEADLESS = "headless";
	public static final String GHOUL = "ghoul";
//	public static final String ETTIN = "ettin";
	public static final String SHADOW = "shadow";
	public static final String SHADOWLORD = "shadowlord";
	public static final String GAZER = "gazer";
	public static final String DAEMON = "daemon";
	public static final String BOULDER = "boulder";
	public static final String ORC = "orc";
	
	// projectile names
	private static final String SLOWBALL = "slowball";
	private static final String HARMBALL = "harmball";
	private static final String FIRESPOUT = "firespout";
	private static final String ROCK = "rock";
	
	// unused
	public static Capability<GhoulCapability> GHOUL_CAPABILITY = null;
	
	/*
	 * deferred registries
	 */
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, DD.MODID);
	public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, DD.MODID);
	public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, DD.MODID);

	// item properties convenience property
	public static final Item.Properties ITEM_PROPERTIES = new Item.Properties().tab(CreativeModeTab.TAB_MISC);
	
	// mob collections
	public static final List<RegistryObject<?>> ALL_MOBS = Lists.newArrayList();
	
	// entities
	public static final RegistryObject<EntityType<Headless>> HEADLESS_ENTITY_TYPE = Registration.ENTITIES.register(HEADLESS, () -> EntityType.Builder.of(Headless::new, MobCategory.MONSTER)
			.sized(0.6F, 1.5F)
			.clientTrackingRange(8)
			.setShouldReceiveVelocityUpdates(false)
			.setTrackingRange(50)
			.build(HEADLESS));
	
	public static final RegistryObject<EntityType<Orc>> ORC_ENTITY_TYPE = Registration.ENTITIES.register(ORC, () -> EntityType.Builder.of(Orc::new, MobCategory.MONSTER)
			.sized(0.6F, 1.95F)
			.clientTrackingRange(12)
			.setShouldReceiveVelocityUpdates(false)
			.build(ORC));
	
	public static final RegistryObject<EntityType<Ghoul>> GHOUL_ENTITY_TYPE = Registration.ENTITIES.register(GHOUL, () -> EntityType.Builder.of(Ghoul::new, MobCategory.MONSTER)
			.sized(0.6F, 1.68F)
			.clientTrackingRange(8)
			.setShouldReceiveVelocityUpdates(false)
			.setTrackingRange(20)
			.build(GHOUL));
	
//	public static final RegistryObject<EntityType<Ettin>> ETTIN_ENTITY_TYPE = Registration.ENTITIES.register(ETTIN, () -> EntityType.Builder.of(Ettin::new, MobCategory.MONSTER)
//			.sized(1.25F, 1.125F)
//			.clientTrackingRange(8)
//			.setShouldReceiveVelocityUpdates(false)
//			.setTrackingRange(20)
//			.fireImmune()
//			.build(ETTIN));
	
	public static final RegistryObject<EntityType<Gazer>> GAZER_ENTITY_TYPE = Registration.ENTITIES.register(GAZER, () -> EntityType.Builder.of(Gazer::new, MobCategory.MONSTER)
			.sized(1.125F, 1.125F)
			.clientTrackingRange(8)
			.setShouldReceiveVelocityUpdates(false)
			.setTrackingRange(20)
			.fireImmune()
			.build(GAZER));
	
	public static final RegistryObject<EntityType<Boulder>> BOULDER_ENTITY_TYPE = Registration.ENTITIES.register(BOULDER, () -> EntityType.Builder.of(Boulder::new, MobCategory.CREATURE)
			.sized(1F, 1F)
			.clientTrackingRange(8)
			.setShouldReceiveVelocityUpdates(false)
			.setTrackingRange(20)
			.fireImmune()
			.build(BOULDER));
	
	public static final RegistryObject<EntityType<Shadow>> SHADOW_ENTITY_TYPE = Registration.ENTITIES.register(SHADOW, () -> EntityType.Builder.of(Shadow::new, MobCategory.MONSTER)
			.sized(0.6F, 1.95F)
			.clientTrackingRange(12)
			.setShouldReceiveVelocityUpdates(false)
			.build(SHADOW));
	
	public static final RegistryObject<EntityType<Shadowlord>> SHADOWLORD_ENTITY_TYPE = Registration.ENTITIES.register(SHADOWLORD, () -> EntityType.Builder.of(Shadowlord::new, MobCategory.MONSTER)
			.sized(0.625F, 2.95F)
			.clientTrackingRange(12)
			.setShouldReceiveVelocityUpdates(false)
			.build(SHADOWLORD));
	
	public static final RegistryObject<EntityType<Daemon>> DAEMON_ENTITY_TYPE = Registration.ENTITIES.register(DAEMON, () -> EntityType.Builder.of(Daemon::new, MobCategory.MONSTER)
			.sized(1F, 3F)
			.clientTrackingRange(12)
			.setShouldReceiveVelocityUpdates(false)
			.fireImmune()
			.build(DAEMON));
	
	// projectile entities
	public static final RegistryObject<EntityType<Slowball>> SLOWBALL_ENTITY_TYPE = 
			Registration.ENTITIES.register(SLOWBALL, () -> EntityType.Builder.of(Slowball::new, MobCategory.MISC)
			.sized(0.5F, 0.5F)
			.clientTrackingRange(12)
			.setShouldReceiveVelocityUpdates(false)
			.build(SLOWBALL));
	
	public static final RegistryObject<EntityType<Harmball>> HARMBALL_ENTITY_TYPE = 
			Registration.ENTITIES.register(HARMBALL, () -> EntityType.Builder.of(Harmball::new, MobCategory.MISC)
			.sized(0.5F, 0.5F)
			.clientTrackingRange(12)
			.setShouldReceiveVelocityUpdates(false)
			.build(HARMBALL));
	
	public static final RegistryObject<EntityType<FireSpout>> FIRESPOUT_ENTITY_TYPE = 
			Registration.ENTITIES.register("firespout", () -> EntityType.Builder.of(FireSpout::new, MobCategory.MISC)
			.sized(0.5F, 0.5F)
			.clientTrackingRange(12)
			.setShouldReceiveVelocityUpdates(false)
			.build("firespout"));
	
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
	public static final RegistryObject<Item> HEADLESS_EGG = Registration.ITEMS.register(HEADLESS, () -> new ForgeSpawnEggItem(HEADLESS_ENTITY_TYPE, 0xc8b486, 0x6f5e48, Registration.ITEM_PROPERTIES));
	public static final RegistryObject<Item> ORC_EGG = Registration.ITEMS.register(ORC, () -> new ForgeSpawnEggItem(ORC_ENTITY_TYPE, 0xc8b486, 0x6f5e48, Registration.ITEM_PROPERTIES));
	public static final RegistryObject<Item> GHOUL_EGG = Registration.ITEMS.register(GHOUL, () -> new ForgeSpawnEggItem(GHOUL_ENTITY_TYPE, 0x93aba3, 0x869e96, Registration.ITEM_PROPERTIES));
//	public static final RegistryObject<Item> ETTIN_EGG = Registration.ITEMS.register(ETTIN, () -> new ForgeSpawnEggItem(ETTIN_ENTITY_TYPE, 0x626262, 0x6f5e48, Registration.ITEM_PROPERTIES));
	
	public static final RegistryObject<Item> GAZER_EGG = Registration.ITEMS.register(GAZER, () -> new ForgeSpawnEggItem(GAZER_ENTITY_TYPE, 0x7a2e2f, 0x63181d, Registration.ITEM_PROPERTIES));
	public static final RegistryObject<Item> BOULDER_EGG = Registration.ITEMS.register(BOULDER, () -> new ForgeSpawnEggItem(BOULDER_ENTITY_TYPE, 0x747474, 0x8f8f8f, Registration.ITEM_PROPERTIES));
	public static final RegistryObject<Item> SHADOW_EGG = Registration.ITEMS.register(SHADOW, () -> new ForgeSpawnEggItem(SHADOW_ENTITY_TYPE, 0x000000, 0x2b2b2b, Registration.ITEM_PROPERTIES));
	public static final RegistryObject<Item> SHADOWLORD_EGG = Registration.ITEMS.register(SHADOWLORD, () -> new ForgeSpawnEggItem(SHADOWLORD_ENTITY_TYPE, 0x000000, 0x050831, Registration.ITEM_PROPERTIES));
	public static final RegistryObject<Item> DAEMON_EGG = Registration.ITEMS.register(DAEMON, () -> new ForgeSpawnEggItem(DAEMON_ENTITY_TYPE, 0xff0000, 0xfc0000, Registration.ITEM_PROPERTIES));
	
	// projectiles
	public static final RegistryObject<Item> SLOWBALL_ITEM = Registration.ITEMS.register(SLOWBALL, () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> HARMBALL_ITEM = Registration.ITEMS.register(HARMBALL, () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> FIRESPOUT_ITEM = Registration.ITEMS.register("firespout", () -> new Item(new Item.Properties()));
	public static final RegistryObject<Item> ROCK_ITEM = Registration.ITEMS.register(ROCK, () -> new Item(new Item.Properties()));
	
	/*
	 *  weapons
	 */
	// club is equal to an stone sword but slower
	public static final RegistryObject<Item> CLUB = Registration.ITEMS.register("club", () -> new SwordItem(Tiers.WOOD, 4, -3.0F, (new Item.Properties()).tab(CreativeModeTab.TAB_COMBAT)));
	// spiked club is equal to an iron sword but slower
	public static final RegistryObject<Item> SPIKED_CLUB = Registration.ITEMS.register("spiked_club", () -> new SwordItem(Tiers.WOOD, 5, -3.0F, (new Item.Properties()).tab(CreativeModeTab.TAB_COMBAT)));
	
	// particles
//	public static final RegistryObject<SimpleParticleType> SHADOW_PARTICLE = Registration.PARTICLES.register("shadow_particle", () -> new SimpleParticleType(true));
	
	// NOTE must add mob to ALL_MOBS collection in order to register them to the biomes - see CommonSetup.onBiomeLoading
	static {
		ALL_MOBS.add(HEADLESS_ENTITY_TYPE);
		ALL_MOBS.add(GHOUL_ENTITY_TYPE);
		ALL_MOBS.add(BOULDER_ENTITY_TYPE);
		ALL_MOBS.add(SHADOW_ENTITY_TYPE);
		ALL_MOBS.add(GAZER_ENTITY_TYPE);
		ALL_MOBS.add(SHADOWLORD_ENTITY_TYPE);
		ALL_MOBS.add(DAEMON_ENTITY_TYPE);
		ALL_MOBS.add(ORC_ENTITY_TYPE);
	}
	
	/**
	 * 
	 */
	public static void init() {
		IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
		ITEMS.register(eventBus);	
		ENTITIES.register(eventBus);		
		PARTICLES.register(eventBus);		
	}
}
