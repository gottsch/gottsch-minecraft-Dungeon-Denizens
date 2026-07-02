package com.someguyssoftware.ddenizens.entity;

import com.google.common.collect.Lists;
import com.someguyssoftware.ddenizens.DD;
import mod.gottsch.forge.gmm.core.entity.projectile.ParalysisSpell;
import mod.gottsch.forge.gmm.core.entity.projectile.HarmSpell;
import mod.gottsch.forge.gmm.core.entity.projectile.DisintegrateSpell;
import mod.gottsch.forge.gmm.core.entity.projectile.DisarmSpell;
import mod.gottsch.forge.gmm.core.entity.projectile.FireSpoutSpell;
import mod.gottsch.forge.gmm.core.entity.projectile.Rock;
import mod.gottsch.forge.gmm.core.entity.monster.Boulder;
import mod.gottsch.forge.gmm.core.entity.monster.Daemon;
import mod.gottsch.forge.gmm.core.entity.monster.beholderkin.DeathTyrant;
import mod.gottsch.forge.gmm.core.entity.monster.gargoyle.Gargoyle;
import mod.gottsch.forge.gmm.core.entity.monster.beholderkin.Gazer;
import mod.gottsch.forge.gmm.core.entity.monster.gargoyle.Margoyle;
import mod.gottsch.forge.gmm.core.entity.monster.Shadow;
import mod.gottsch.forge.gmm.core.entity.monster.Shadowlord;
import mod.gottsch.forge.gmm.core.entity.monster.beholderkin.Spectator;
import mod.gottsch.forge.gmm.core.entity.monster.beholderkin.Beholder;
import mod.gottsch.forge.gmm.core.entity.monster.Headless;
import mod.gottsch.forge.gmm.core.entity.monster.Orc;
import mod.gottsch.forge.gmm.core.entity.monster.ghoul.Ghoul;
import mod.gottsch.forge.gmm.core.entity.monster.ghoul.SewerGhoul;
import mod.gottsch.forge.gmm.core.entity.monster.Rat;
import mod.gottsch.forge.gmm.core.entity.monster.skeleton.SkeletonWarrior;
import mod.gottsch.forge.gmm.core.entity.monster.skeleton.WingedSkeleton;
import mod.gottsch.forge.gmm.core.entity.monster.skeleton.IronSkeleton;
import mod.gottsch.forge.gmm.core.entity.monster.skeleton.MagmaSkeleton;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

/**
 * Entity-type registration: owns the {@link #ENTITIES} deferred registry plus every mob and
 * projectile {@link EntityType}, their name constants, and the {@link #ALL_MOBS} collection.
 *
 * @author by Mark Gottschling on 7/3/2025
 */
public class ModEntities {
	public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, DD.MODID);

	public static final String HEADLESS = "headless";
	public static final String GHOUL = "ghoul";
	public static final String SEWER_GHOUL = "sewer_ghoul";
	public static final String RAT = "rat";
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
	public static String GARGOYLE = "gargoyle";
	public static String MARGOYLE = "margoyle";

	// projectile names (also used as item ids by ModItems)
	public static final String PARALYSIS_SPELL = "slow";
	public static final String HARM_SPELL = "harm";
	public static final String DISINTEGRATE_SPELL = "disintegrate";
	public static final String DISARM_SPELL = "disarm";
	public static final String FIRESPOUT_SPELL = "firespout";
	public static final String ROCK = "rock";

	// mob collections
	public static final List<RegistryObject<?>> ALL_MOBS = Lists.newArrayList();

	// entities
	public static final RegistryObject<EntityType<Boulder>> BOULDER_ENTITY_TYPE = ENTITIES.register(BOULDER, () -> EntityType.Builder.of(Boulder::new, MobCategory.AMBIENT)
			.sized(1F, 1F)
			.clientTrackingRange(8)
			.setShouldReceiveVelocityUpdates(false)
			.setTrackingRange(20)
			.fireImmune()
			.build(BOULDER));

	public static final RegistryObject<EntityType<Headless>> HEADLESS_ENTITY_TYPE = ENTITIES.register(HEADLESS, () -> EntityType.Builder.of(Headless::new, MobCategory.MONSTER)
			.sized(0.65F, 1.5F)
			.clientTrackingRange(8)
			.setShouldReceiveVelocityUpdates(false)
			.setTrackingRange(50)
			.build(HEADLESS));

	public static final RegistryObject<EntityType<Orc>> ORC_ENTITY_TYPE = ENTITIES.register(ORC, () -> EntityType.Builder.of(Orc::new, MobCategory.MONSTER)
			.sized(1F, 1.99F)
			.clientTrackingRange(12)
			.setShouldReceiveVelocityUpdates(false)
			.build(ORC));

	public static final RegistryObject<EntityType<Ghoul>> GHOUL_ENTITY_TYPE = ENTITIES.register(GHOUL, () -> EntityType.Builder.of(Ghoul::new, MobCategory.MONSTER)
			.sized(0.6F, 1.68F)
			.clientTrackingRange(8)
			.setShouldReceiveVelocityUpdates(false)
			.setTrackingRange(20)
			.build(GHOUL));

	public static final RegistryObject<EntityType<SewerGhoul>> SEWER_GHOUL_ENTITY_TYPE = ENTITIES.register(SEWER_GHOUL, () -> EntityType.Builder.of(SewerGhoul::new, MobCategory.MONSTER)
			.sized(0.6F, 1.68F)
			.clientTrackingRange(8)
			.setShouldReceiveVelocityUpdates(false)
			.setTrackingRange(20)
			.build(SEWER_GHOUL));

	public static final RegistryObject<EntityType<Rat>> RAT_ENTITY_TYPE = ENTITIES.register(RAT, () -> EntityType.Builder.of(Rat::new, MobCategory.MONSTER)
			.sized(0.8F, 0.25F)
			.clientTrackingRange(8)
			.setShouldReceiveVelocityUpdates(false)
			.setTrackingRange(20)
			.build(RAT));

	public static final RegistryObject<EntityType<Beholder>> BEHOLDER_ENTITY_TYPE = ENTITIES.register(BEHOLDER, () -> EntityType.Builder.of(Beholder::new, MobCategory.MONSTER)
			.sized(2.25F, 3.5F)
			.clientTrackingRange(8)
			.setShouldReceiveVelocityUpdates(false)
			.setTrackingRange(20)
			.fireImmune()
			.build(BEHOLDER));

	public static final RegistryObject<EntityType<DeathTyrant>> DEATH_TYRANT_TYPE = ENTITIES.register(DEATH_TYRANT, () -> EntityType.Builder.of(DeathTyrant::new, MobCategory.MONSTER)
			.sized(2.25F, 3.5F)
			.clientTrackingRange(8)
			.setShouldReceiveVelocityUpdates(false)
			.setTrackingRange(20)
			.fireImmune()
			.build(DEATH_TYRANT));

	public static final RegistryObject<EntityType<Gazer>> GAZER_ENTITY_TYPE = ENTITIES.register(GAZER, () -> EntityType.Builder.of(Gazer::new, MobCategory.MONSTER)
			.sized(1.125F, 1.25F)
			.clientTrackingRange(8)
			.setShouldReceiveVelocityUpdates(false)
			.setTrackingRange(20)
			.fireImmune()
			.build(GAZER));

	public static final RegistryObject<EntityType<Spectator>> SPECTATOR_TYPE = ENTITIES.register(SPECTATOR, () -> EntityType.Builder.of(Spectator::new, MobCategory.MONSTER)
			.sized(0.84375F, 1.21875F)
			.clientTrackingRange(8)
			.setShouldReceiveVelocityUpdates(false)
			.setTrackingRange(20)
			.build(SPECTATOR));

	public static final RegistryObject<EntityType<Shadow>> SHADOW_ENTITY_TYPE = ENTITIES.register(SHADOW, () -> EntityType.Builder.of(Shadow::new, MobCategory.MONSTER)
			.sized(0.75F, 1.95F)
			.clientTrackingRange(12)
			.setShouldReceiveVelocityUpdates(false)
			.build(SHADOW));

	public static final RegistryObject<EntityType<Shadowlord>> SHADOWLORD_ENTITY_TYPE = ENTITIES.register(SHADOWLORD, () -> EntityType.Builder.of(Shadowlord::new, MobCategory.MONSTER)
			.sized(1.25F, 2.8125F)
			.clientTrackingRange(12)
			.setShouldReceiveVelocityUpdates(false)
			.build(SHADOWLORD));

	public static final RegistryObject<EntityType<Daemon>> DAEMON_ENTITY_TYPE = ENTITIES.register(DAEMON, () -> EntityType.Builder.of(Daemon::new, MobCategory.MONSTER)
			.sized(1F, 3F)
			.clientTrackingRange(12)
			.setShouldReceiveVelocityUpdates(false)
			.fireImmune()
			.build(DAEMON));

	public static final RegistryObject<EntityType<SkeletonWarrior>> SKELETON_WARRIOR_TYPE = ENTITIES.register(SKELETON_WARRIOR, () -> EntityType.Builder.of(SkeletonWarrior::new, MobCategory.MONSTER)
			.sized(0.6F, 1.95F)
			.clientTrackingRange(12)
			.setShouldReceiveVelocityUpdates(false)
			.build(SKELETON_WARRIOR));

	public static final RegistryObject<EntityType<WingedSkeleton>> WINGED_SKELETON_TYPE = ENTITIES.register(WINGED_SKELETON, () -> EntityType.Builder.of(WingedSkeleton::new, MobCategory.MONSTER)
			.sized(0.75F, 1.95F)
			.clientTrackingRange(12)
			.setShouldReceiveVelocityUpdates(false)
			.build(WINGED_SKELETON));

	public static final RegistryObject<EntityType<IronSkeleton>> IRON_SKELETON_TYPE = ENTITIES.register(IRON_SKELETON, () -> EntityType.Builder.of(IronSkeleton::new, MobCategory.MONSTER)
			.sized(0.63F, 2.1F)
			.clientTrackingRange(15)
			.setShouldReceiveVelocityUpdates(false)
			.build(IRON_SKELETON));

	public static final RegistryObject<EntityType<MagmaSkeleton>> MAGMA_SKELETON_TYPE = ENTITIES.register(MAGMA_SKELETON, () -> EntityType.Builder.of(MagmaSkeleton::new, MobCategory.MONSTER)
			.sized(0.63F, 2.1F)
			.clientTrackingRange(15)
			.setShouldReceiveVelocityUpdates(false)
			.fireImmune()
			.build(MAGMA_SKELETON));

	public static final RegistryObject<EntityType<Gargoyle>> GARGOYLE_TYPE = ENTITIES.register(GARGOYLE, () -> EntityType.Builder.of(Gargoyle::new, MobCategory.MONSTER)
			.sized(0.75F, 1.75F)
			.clientTrackingRange(12)
			.setShouldReceiveVelocityUpdates(false)
			.build(GARGOYLE));

	// 20% larger than a Gargoyle
	public static final RegistryObject<EntityType<Margoyle>> MARGOYLE_TYPE = ENTITIES.register(MARGOYLE, () -> EntityType.Builder.of(Margoyle::new, MobCategory.MONSTER)
			.sized(0.9F, 2.1F)
			.clientTrackingRange(12)
			.setShouldReceiveVelocityUpdates(false)
			.build(MARGOYLE));

	// projectile entities
	public static final RegistryObject<EntityType<ParalysisSpell>> PARALYSIS_SPELL_ENTITY_TYPE =
			ENTITIES.register(PARALYSIS_SPELL, () -> EntityType.Builder.of(ParalysisSpell::new, MobCategory.MISC)
			.sized(1F, 1F)
			.clientTrackingRange(12)
			.setShouldReceiveVelocityUpdates(false)
			.build(PARALYSIS_SPELL));

	public static final RegistryObject<EntityType<HarmSpell>> HARM_SPELL_ENTITY_TYPE =
			ENTITIES.register(HARM_SPELL, () -> EntityType.Builder.of(HarmSpell::new, MobCategory.MISC)
			.sized(1F, 1F)
			.clientTrackingRange(12)
			.setShouldReceiveVelocityUpdates(false)
			.build(HARM_SPELL));

	public static final RegistryObject<EntityType<DisarmSpell>> DISARM_SPELL_ENTITY_TYPE =
			ENTITIES.register(DISARM_SPELL, () -> EntityType.Builder.of(DisarmSpell::new, MobCategory.MISC)
					.sized(1F, 1F)
					.clientTrackingRange(12)
					.setShouldReceiveVelocityUpdates(false)
					.build(DISARM_SPELL));

	public static final RegistryObject<EntityType<DisintegrateSpell>> DISINTEGRATE_SPELL_ENTITY_TYPE =
			ENTITIES.register(DISINTEGRATE_SPELL, () -> EntityType.Builder.of(DisintegrateSpell::new, MobCategory.MISC)
					.sized(1F, 1F)
					.clientTrackingRange(12)
					.setShouldReceiveVelocityUpdates(false)
					.build(DISINTEGRATE_SPELL));

	public static final RegistryObject<EntityType<FireSpoutSpell>> FIRESPOUT_SPELL_ENTITY_TYPE =
			ENTITIES.register(FIRESPOUT_SPELL, () -> EntityType.Builder.of(FireSpoutSpell::new, MobCategory.MISC)
			.sized(0.5F, 0.5F)
			.clientTrackingRange(12)
			.setShouldReceiveVelocityUpdates(false)
			.build(FIRESPOUT_SPELL));

	public static final RegistryObject<EntityType<Rock>> ROCK_ENTITY_TYPE =
			ENTITIES.register(ROCK, () -> EntityType.Builder.of(Rock::new, MobCategory.MISC)
			.sized(0.5F, 0.5F)
			.clientTrackingRange(12)
			.setShouldReceiveVelocityUpdates(false)
			.build(ROCK));

	// NOTE must add mob to ALL_MOBS collection in order to register them to the biomes - see CommonSetup.onBiomeLoading
	// NOTE 7/3/2025 - this doesn't apply to 1.20.1+ as Biomes are handled in data files.
	static {
		ALL_MOBS.add(HEADLESS_ENTITY_TYPE);
		ALL_MOBS.add(GHOUL_ENTITY_TYPE);
		ALL_MOBS.add(SEWER_GHOUL_ENTITY_TYPE);
		ALL_MOBS.add(RAT_ENTITY_TYPE);
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
		ALL_MOBS.add(GARGOYLE_TYPE);
	}

	public static void init() {
		ENTITIES.register(FMLJavaModLoadingContext.get().getModEventBus());
	}
}
