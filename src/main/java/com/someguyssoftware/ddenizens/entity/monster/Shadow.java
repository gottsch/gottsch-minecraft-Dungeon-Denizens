/**
 * 
 */
package com.someguyssoftware.ddenizens.entity.monster;

import com.someguyssoftware.ddenizens.DD;
import com.someguyssoftware.ddenizens.config.Config;
import com.someguyssoftware.ddenizens.setup.Registration;
import mod.gottsch.forge.gottschcore.random.RandomHelper;
import mod.gottsch.forge.gottschcore.world.WorldInfo;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.BiomeTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.FleeSunGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RestrictSunGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

/**
 * NOTE: 	mob.level.getBrightness(LightLayer.BLOCK, pos), mob.level.getMaxLocalRawBrightness(pos));
 * @author Mark Gottschling on Apr 12, 2022
 *
 */
public class Shadow extends DenizensMonster {

	private static final int NORMAL_DIFFICULTY_SECONDS = 10;
	private static final int HARD_DIFFICULTY_SECONDS = 20;
	private static final int WEAKNESS_SECONDS = 5;

	private boolean flee;

	/**
	 * 
	 * @param entityType
	 * @param level
	 */
	public Shadow(EntityType<? extends Monster> entityType, Level level) {

		super(entityType, level, MonsterSize.MEDIUM);
	}

	protected void registerGoals() {
		this.goalSelector.addGoal(2, new RestrictSunGoal(this));
		this.goalSelector.addGoal(3, new FleeSunGoal(this, 1.0D));
		this.goalSelector.addGoal(3,  new ShadowFleeGoal<>(this, Player.class, 6.0F, 1.2D, 1.2D));
		this.goalSelector.addGoal(3, new AvoidEntityGoal<>(this, Boulder.class, 6.0F, 1.0D, 1.2D, IDenizensMonster.avoidBoulder));
		this.goalSelector.addGoal(4, new MeleeAttackGoal(this, 1.0D, false));
		this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 1.0D));
		this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 8.0F));
		this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));

		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
	}

	/**
	 * 
	 * @return
	 */
	public static AttributeSupplier.Builder createAttributes() {
		return Monster.createMonsterAttributes()
				.add(Attributes.ATTACK_DAMAGE, 2.0D)
				.add(Attributes.MAX_HEALTH)
				.add(Attributes.KNOCKBACK_RESISTANCE, 0.5)
				.add(Attributes.MOVEMENT_SPEED, 0.28F);                
	}

	@Override
	public MobType getMobType() {
		return MobType.UNDEAD;
	}

	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty,
										MobSpawnType spawnType, SpawnGroupData groupData, CompoundTag tag) {

		groupData = super.finalizeSpawn(level, difficulty, spawnType, groupData, tag);

		RandomSource randomSource = level.getRandom();
		this.populateDefaultEquipmentSlots(randomSource, difficulty);

		return groupData;
	}

	protected void populateDefaultEquipmentSlots(RandomSource randomSource, DifficultyInstance difficultyInstance) {
		this.handDropChances[EquipmentSlot.MAINHAND.getIndex()] = 0.75F;
		this.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Registration.SHADOW_FALCHION.get()));
	}

	@Override
	public void aiStep() {
		// set on fire if in sun
		boolean flag = this.isSunBurnTick();
		if (flag) {
			this.setSecondsOnFire(4);
		}
		super.aiStep();
	}
	
	/**
	 * 
	 * @param mob
	 * @param level
	 * @param spawnType
	 * @param pos
	 * @param random
	 * @return
	 */
	public static boolean checkShadowSpawnRules(EntityType<Shadow> mob, ServerLevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {

		if (level.getBiome(pos).is(BiomeTags.IS_NETHER) ) {
			return checkDDMonsterNetherSpawnRules(mob, level, spawnType, pos, random);
		}
		else {
			return checkDDMonsterSpawnRules(mob, level, spawnType, pos, random);
		}
	}

	@Override
	public boolean doHurtTarget(Entity target) {
		if (super.doHurtTarget(target)) {
			if (target instanceof Player) {

				// inflict blindness and/or weakness
				ItemStack helmetStack = ((Player)target).getItemBySlot(EquipmentSlot.HEAD);
				if (helmetStack.isEmpty() || helmetStack.getItem() != Items.GOLDEN_HELMET) {
					if (RandomHelper.checkProbability(this.random, Config.Mobs.SHADOW.blindnessProbability.get())) {
						if (Config.Mobs.SHADOW.blindnessDuration.get() > 0) {
							((LivingEntity)target).addEffect(new MobEffectInstance(MobEffects.BLINDNESS, Config.Mobs.SHADOW.blindnessDuration.get(), 0), this);
						}
					}
				}
				if (RandomHelper.checkProbability(this.random, Config.Mobs.SHADOW.weaknessProbability.get())) {
					if (Config.Mobs.SHADOW.weaknessDuration.get() > 0) {
						((LivingEntity)target).addEffect(new MobEffectInstance(MobEffects.WEAKNESS, Config.Mobs.SHADOW.weaknessDuration.get(), 0), this);
					}
				}
			}
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 
	 */
	@Override
	public boolean hurt(DamageSource damageSource, float amount) {
		if (WorldInfo.isClientSide(this.level())) {
			return false;
		}

		// cause the shadow to flee it hurt
		this.flee = true;
		
		if (damageSource.getEntity() != null && damageSource.getEntity() instanceof Player player) {
			ItemStack heldStack = player.getItemInHand(InteractionHand.MAIN_HAND);

			// TODO same as Shadowlord - abstract out to Shadowkin class
			if (!heldStack.isEmpty()) {
				if (heldStack.is(Items.GOLDEN_SWORD)) {
					// increase damage to that of iron tier
					amount += 2.0F;
					// negate the weakness from the strike power of the sword
					// gold does full damage
					if (player.hasEffect(MobEffects.WEAKNESS)) {
						amount += 4.0F * (player.getEffect(MobEffects.WEAKNESS).getAmplifier() + 1);
					}
				} else if (heldStack.is(Registration.SHADOW_BLADE.get())) {
					// increase damage to that of a netherite tier
					amount += 2.0F;
					if (player.hasEffect(MobEffects.WEAKNESS)) {
						amount += 4.0F * (player.getEffect(MobEffects.WEAKNESS).getAmplifier() + 1);
					}
				} else if (heldStack.is(Registration.SHADOW_FALCHION.get())) {
					// increase damage to that of a diamond tier
					amount += 1.0F;
					if (player.hasEffect(MobEffects.WEAKNESS)) {
						amount += 4.0F * (player.getEffect(MobEffects.WEAKNESS).getAmplifier() + 1);
					}
				} else {
					if (heldStack.getItem() instanceof TieredItem tieredItem) {
						int penalty = 0;
						Tier tier = tieredItem.getTier();
						if (tier == Tiers.NETHERITE) {
							penalty = -3;
						} else if (tier == Tiers.DIAMOND) {
							penalty = -2;
						} else if (tier == Tiers.IRON) {
							penalty = -1;
						} else if (tier == Tiers.STONE || tier == Tiers.WOOD) {
							// don't incur a penalty
						}
						amount += penalty;
					} else {
						// all other items
						amount = Math.max(amount, 4.0f); // same as stone sword
					}
				}
				DD.LOGGER.debug("new strike amount -> {}", amount);
			}
		}
		return super.hurt(damageSource, amount);
	}

	@Nullable
	@Override
	protected SoundEvent getAmbientSound() {
		return Registration.AMBIENT_SHADOW.get();
	}

	/**
	 * 
	 * @author Mark Gottschling on Apr 13, 2022
	 *
	 * @param <T>
	 */
	public static class ShadowFleeGoal<T extends LivingEntity> extends AvoidEntityGoal<T> {

		/**
		 * 
		 * @param mob
		 * @param target
		 * @param maxDistance
		 * @param walkSpeedModifier
		 * @param sprintSpeedModifier
		 */
		public ShadowFleeGoal(PathfinderMob mob, Class<T> target, float maxDistance, double walkSpeedModifier,
				double sprintSpeedModifier) {
			super(mob, target, maxDistance, walkSpeedModifier, sprintSpeedModifier);

		}

		@Override
		public boolean canUse() {
			if (((Shadow)mob).flee) {
				if(super.canUse()) {
					return true;
				}
			}
			return false;
		}

		@Override
		public void stop() {
			((Shadow)mob).flee = false;
			this.toAvoid = null;
		}

		@Override
		public boolean canContinueToUse() {
			return !this.pathNav.isDone();
		}
	}
}
