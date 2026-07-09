package com.someguyssoftware.ddenizens.entity;

import com.google.common.collect.Lists;
import com.someguyssoftware.ddenizens.DD;
import mod.gottsch.forge.gmm.core.entity.projectile.ParalysisSpell;
import mod.gottsch.forge.gmm.core.entity.projectile.HarmSpell;
import mod.gottsch.forge.gmm.core.entity.projectile.DisintegrateSpell;
import mod.gottsch.forge.gmm.core.entity.projectile.DisarmSpell;
import mod.gottsch.forge.gmm.core.entity.projectile.FireSpoutSpell;
import mod.gottsch.forge.gmm.core.entity.projectile.FirewallColumnSpell;
import mod.gottsch.forge.gmm.core.entity.projectile.Rock;
import mod.gottsch.forge.gmm.core.entity.projectile.BoneShard;
import mod.gottsch.forge.gmm.core.entity.monster.AlligatorGar;
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
import mod.gottsch.forge.gmm.core.entity.monster.skeleton.FrostSkeleton;
import mod.gottsch.forge.gmm.core.entity.monster.skeleton.TaintedSkeleton;
import mod.gottsch.forge.gmm.core.entity.monster.skeleton.AcidSkeleton;
import mod.gottsch.forge.gmm.core.entity.monster.skeleton.BloodyBones;
import mod.gottsch.forge.gmm.core.entity.monster.skeleton.ElectricSkeleton;
import mod.gottsch.forge.gmm.core.entity.monster.skeleton.BurningSkeleton;
import mod.gottsch.forge.gmm.core.entity.monster.zombie.Bloater;
import mod.gottsch.forge.gmm.core.entity.monster.GelatinousCube;
import mod.gottsch.forge.gmm.core.entity.monster.OchreJelly;
import mod.gottsch.forge.gmm.core.entity.monster.GrayOoze;
import mod.gottsch.forge.gmm.core.entity.monster.mimic.VanillaChestMimic;
import mod.gottsch.forge.gmm.core.entity.monster.mimic.BarrelMimic;
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
	public static final String ALLIGATOR_GAR = "alligator_gar";
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
	public static final String FROST_SKELETON = "frost_skeleton";
	public static final String TAINTED_SKELETON = "tainted_skeleton";
	public static final String ACID_SKELETON = "acid_skeleton";
	public static final String ELECTRIC_SKELETON = "electric_skeleton";
	public static final String BURNING_SKELETON = "burning_skeleton";
	public static final String BLOODY_BONES = "bloody_bones";
	public static final String BLOATER = "bloater";
	public static final String GELATINOUS_CUBE = "gelatinous_cube";
	public static final String OCHRE_JELLY = "ochre_jelly";
	public static final String GRAY_OOZE = "gray_ooze";
	public static final String VANILLA_CHEST_MIMIC = "vanilla_chest_mimic";
	public static final String BARREL_MIMIC = "barrel_mimic";
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
	public static final String FIREWALL_COLUMN_SPELL = "firewall_column";
	public static final String ROCK = "rock";
	public static final String BONE_SHARD = "bone_shard";

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

	public static final RegistryObject<EntityType<AlligatorGar>> ALLIGATOR_GAR_ENTITY_TYPE = ENTITIES.register(ALLIGATOR_GAR, () -> EntityType.Builder.of(AlligatorGar::new, MobCategory.MONSTER)
			.sized(0.6F, 0.4F)
			.clientTrackingRange(8)
			.setShouldReceiveVelocityUpdates(false)
			.setTrackingRange(20)
			.build(ALLIGATOR_GAR));

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

	public static final RegistryObject<EntityType<FrostSkeleton>> FROST_SKELETON_TYPE = ENTITIES.register(FROST_SKELETON, () -> EntityType.Builder.of(FrostSkeleton::new, MobCategory.MONSTER)
			.sized(0.6F, 1.99F)
			.clientTrackingRange(15)
			.setShouldReceiveVelocityUpdates(false)
			.build(FROST_SKELETON));

	public static final RegistryObject<EntityType<TaintedSkeleton>> TAINTED_SKELETON_TYPE = ENTITIES.register(TAINTED_SKELETON, () -> EntityType.Builder.of(TaintedSkeleton::new, MobCategory.MONSTER)
			.sized(0.7F, 1.99F)
			.clientTrackingRange(15)
			.setShouldReceiveVelocityUpdates(false)
			.build(TAINTED_SKELETON));

	public static final RegistryObject<EntityType<AcidSkeleton>> ACID_SKELETON_TYPE = ENTITIES.register(ACID_SKELETON, () -> EntityType.Builder.of(AcidSkeleton::new, MobCategory.MONSTER)
			.sized(0.6F, 1.99F)
			.clientTrackingRange(15)
			.setShouldReceiveVelocityUpdates(false)
			.build(ACID_SKELETON));

	public static final RegistryObject<EntityType<ElectricSkeleton>> ELECTRIC_SKELETON_TYPE = ENTITIES.register(ELECTRIC_SKELETON, () -> EntityType.Builder.of(ElectricSkeleton::new, MobCategory.MONSTER)
			.sized(0.6F, 1.99F)
			.clientTrackingRange(15)
			.setShouldReceiveVelocityUpdates(false)
			.build(ELECTRIC_SKELETON));

	public static final RegistryObject<EntityType<BurningSkeleton>> BURNING_SKELETON_TYPE = ENTITIES.register(BURNING_SKELETON, () -> EntityType.Builder.of(BurningSkeleton::new, MobCategory.MONSTER)
			.sized(0.6F, 1.99F)
			.clientTrackingRange(15)
			.setShouldReceiveVelocityUpdates(false)
			// made of fire: immune to fire/lava, wades through its own death blaze unharmed
			.fireImmune()
			.build(BURNING_SKELETON));

	public static final RegistryObject<EntityType<BloodyBones>> BLOODY_BONES_TYPE = ENTITIES.register(BLOODY_BONES, () -> EntityType.Builder.of(BloodyBones::new, MobCategory.MONSTER)
			.sized(0.6F, 1.99F)
			.clientTrackingRange(15)
			.setShouldReceiveVelocityUpdates(false)
			.build(BLOODY_BONES));

	public static final RegistryObject<EntityType<Bloater>> BLOATER_TYPE = ENTITIES.register(BLOATER, () -> EntityType.Builder.of(Bloater::new, MobCategory.MONSTER)
			.sized(0.7F, 2.1F)
			.clientTrackingRange(15)
			.setShouldReceiveVelocityUpdates(false)
			.build(BLOATER));

	// smaller than a full-sized ("Big") vanilla Slime (~2.04 blocks); see GelatinousCube's class doc.
	// this is the size at gmm:mob_config's default "size": 1.0 -- GelatinousCube.getDimensions() scales
	// it further at spawn if a consumer overrides that key.
	public static final RegistryObject<EntityType<GelatinousCube>> GELATINOUS_CUBE_TYPE = ENTITIES.register(GELATINOUS_CUBE, () -> EntityType.Builder.of(GelatinousCube::new, MobCategory.MONSTER)
			.sized(1.1F, 1.1F)
			.clientTrackingRange(15)
			.setShouldReceiveVelocityUpdates(false)
			.build(GELATINOUS_CUBE));

	// smaller than the Gelatinous Cube -- its "smaller" split children are handled via reduced max
	// health rather than a separate/shrinking hitbox (see OchreJelly's class doc).
	public static final RegistryObject<EntityType<OchreJelly>> OCHRE_JELLY_TYPE = ENTITIES.register(OCHRE_JELLY, () -> EntityType.Builder.of(OchreJelly::new, MobCategory.MONSTER)
			.sized(0.85F, 0.85F)
			.clientTrackingRange(15)
			.setShouldReceiveVelocityUpdates(false)
			.build(OCHRE_JELLY));

	// ambush ooze -- spawns disguised as wet stone; see GrayOoze's class doc for the reveal mechanic
	public static final RegistryObject<EntityType<GrayOoze>> GRAY_OOZE_TYPE = ENTITIES.register(GRAY_OOZE, () -> EntityType.Builder.of(GrayOoze::new, MobCategory.MONSTER)
			.sized(0.9F, 0.9F)
			.clientTrackingRange(15)
			.setShouldReceiveVelocityUpdates(false)
			.build(GRAY_OOZE));

	// disguised as a plain chest; NOT added to ALL_MOBS -- it's a dungeon-loot-room ambusher, not a
	// biome-roaming natural spawn (see MobIdeasCatalog's "Chest Mimic" entry). Test via spawn egg / summon.
	public static final RegistryObject<EntityType<VanillaChestMimic>> VANILLA_CHEST_MIMIC_TYPE = ENTITIES.register(VANILLA_CHEST_MIMIC, () -> EntityType.Builder.of(VanillaChestMimic::new, MobCategory.MONSTER)
			.sized(1F, 1F)
			.clientTrackingRange(12)
			.setShouldReceiveVelocityUpdates(false)
			.build(VANILLA_CHEST_MIMIC));

	// same Mimic base as VANILLA_CHEST_MIMIC, disguised as a barrel instead -- also NOT in ALL_MOBS,
	// see the note above.
	public static final RegistryObject<EntityType<BarrelMimic>> BARREL_MIMIC_TYPE = ENTITIES.register(BARREL_MIMIC, () -> EntityType.Builder.of(BarrelMimic::new, MobCategory.MONSTER)
			.sized(1F, 1F)
			.clientTrackingRange(12)
			.setShouldReceiveVelocityUpdates(false)
			.build(BARREL_MIMIC));

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

	public static final RegistryObject<EntityType<FirewallColumnSpell>> FIREWALL_COLUMN_SPELL_ENTITY_TYPE =
			ENTITIES.register(FIREWALL_COLUMN_SPELL, () -> EntityType.Builder.of(FirewallColumnSpell::new, MobCategory.MISC)
			.sized(0.5F, 0.5F)
			.clientTrackingRange(12)
			.setShouldReceiveVelocityUpdates(false)
			.build(FIREWALL_COLUMN_SPELL));

	public static final RegistryObject<EntityType<Rock>> ROCK_ENTITY_TYPE =
			ENTITIES.register(ROCK, () -> EntityType.Builder.of(Rock::new, MobCategory.MISC)
			.sized(0.5F, 0.5F)
			.clientTrackingRange(12)
			.setShouldReceiveVelocityUpdates(false)
			.build(ROCK));

	// arrow-like bone-fragment projectile (Tainted Skeleton shrapnel); mirrors vanilla arrow tracking
	public static final RegistryObject<EntityType<BoneShard>> BONE_SHARD_ENTITY_TYPE =
			ENTITIES.register(BONE_SHARD, () -> EntityType.Builder.<BoneShard>of(BoneShard::new, MobCategory.MISC)
			.sized(0.5F, 0.5F)
			.clientTrackingRange(4)
			.updateInterval(20)
			.build(BONE_SHARD));

	// NOTE must add mob to ALL_MOBS collection in order to register them to the biomes - see CommonSetup.onBiomeLoading
	// NOTE 7/3/2025 - this doesn't apply to 1.20.1+ as Biomes are handled in data files.
	static {
		ALL_MOBS.add(HEADLESS_ENTITY_TYPE);
		ALL_MOBS.add(GHOUL_ENTITY_TYPE);
		ALL_MOBS.add(SEWER_GHOUL_ENTITY_TYPE);
		ALL_MOBS.add(RAT_ENTITY_TYPE);
		ALL_MOBS.add(ALLIGATOR_GAR_ENTITY_TYPE);
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
		ALL_MOBS.add(FROST_SKELETON_TYPE);
		ALL_MOBS.add(TAINTED_SKELETON_TYPE);
		ALL_MOBS.add(ACID_SKELETON_TYPE);
		ALL_MOBS.add(ELECTRIC_SKELETON_TYPE);
		ALL_MOBS.add(BURNING_SKELETON_TYPE);
		ALL_MOBS.add(BLOODY_BONES_TYPE);
		ALL_MOBS.add(BLOATER_TYPE);
		ALL_MOBS.add(GELATINOUS_CUBE_TYPE);
		ALL_MOBS.add(OCHRE_JELLY_TYPE);
		ALL_MOBS.add(GRAY_OOZE_TYPE);
		ALL_MOBS.add(IRON_SKELETON_TYPE);
		ALL_MOBS.add(GARGOYLE_TYPE);
	}

	public static void init() {
		ENTITIES.register(FMLJavaModLoadingContext.get().getModEventBus());
	}
}
