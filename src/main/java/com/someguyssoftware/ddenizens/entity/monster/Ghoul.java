/**
 * 
 */
package com.someguyssoftware.ddenizens.entity.monster;

import com.someguyssoftware.ddenizens.config.Config;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.FleeSunGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.MoveThroughVillageGoal;
import net.minecraft.world.entity.ai.goal.OpenDoorGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RestrictSunGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.animal.Turtle;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;

public class Ghoul extends DenizensMonster {
	private boolean canOpenDoors;

	/**
	 * 
	 * @param entityType
	 * @param level
	 */
	public Ghoul(EntityType<? extends Monster> entityType, Level level) {
		super(entityType, level, MonsterSize.MEDIUM);
		this.setCanPickUpLoot(true);
		this.setCanOpenDoors(Config.Mobs.GHOUL.canOpenDoors.get());
		if (this.canOpenDoors()) {
			((GroundPathNavigation)this.getNavigation()).setCanOpenDoors(true);
		}
	}

	protected void registerGoals() {
		// RestrictSunGoal is a pathfinding goal that avoids the sun.
		this.goalSelector.addGoal(2, new RestrictSunGoal(this));
		if (this.canOpenDoors()) {
			this.goalSelector.addGoal(2, new OpenDoorGoal(this, true));
		}
		// FleeSunGoal is a pathfinding goal that seeks to get out of the sun.
		this.goalSelector.addGoal(3, new FleeSunGoal(this, 1.0D));
		this.goalSelector.addGoal(3, new AvoidEntityGoal<>(this, Boulder.class, 6.0F, 1.0D, 1.2D, IDenizensMonster.avoidBoulder));
		this.goalSelector.addGoal(4, new MeleeAttackGoal(this, 1.0D, false));
		this.goalSelector.addGoal(4, new GhoulHealGoal(this));
		this.goalSelector.addGoal(5, new MoveThroughVillageGoal(this, 1.0D, true, 4, () -> true));
		this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 1.0D));
		this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 8.0F));
		this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
		this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
		this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Villager.class, true));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Chicken.class, true));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Cow.class, true));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Pig.class, true));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Sheep.class, true));
		this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Horse.class, true));
		this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, Turtle.class, 10, true, false, Turtle.BABY_ON_LAND_SELECTOR));
	}

	public static AttributeSupplier.Builder createAttributes() {
		return Monster.createMonsterAttributes()
				.add(Attributes.ATTACK_DAMAGE, 3.0D)
				.add(Attributes.ATTACK_KNOCKBACK)
				.add(Attributes.ARMOR, 2.0D)
				.add(Attributes.MAX_HEALTH)
				.add(Attributes.FOLLOW_RANGE, 15.0)
				.add(Attributes.MOVEMENT_SPEED, 0.3F);  // faster than zombie
	}

	public MobType getMobType() {
		return MobType.UNDEAD;
	}

	@Override
	public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty,
			MobSpawnType spawnType, SpawnGroupData groupData, CompoundTag tag) {
		groupData =  super.finalizeSpawn(level, difficulty, spawnType, groupData, tag);

		this.getAttribute(Attributes.KNOCKBACK_RESISTANCE).addPermanentModifier(new AttributeModifier("Random ghoul-spawn bonus", this.random.nextDouble() * (double)0.05F, AttributeModifier.Operation.ADDITION));
		double chance = this.random.nextDouble() * 1.5D * (double)difficulty.getSpecialMultiplier();
		if (chance > 1.0D) {
			this.getAttribute(Attributes.FOLLOW_RANGE).addPermanentModifier(new AttributeModifier("Random ghoul-spawn bonus", chance, AttributeModifier.Operation.MULTIPLY_TOTAL));
		}
		if (this.random.nextFloat() < chance * 0.05F) {
			this.getAttribute(Attributes.MAX_HEALTH).addPermanentModifier(new AttributeModifier("Random ghoul-spawn bonus", this.random.nextDouble() * 3.0D + 1.0D, AttributeModifier.Operation.MULTIPLY_TOTAL));
		}
		return groupData;
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
	 * Wants to pick up meats
	 */
	// TODO use custom Tags to check against
	@Override
	public boolean wantsToPickUp(ItemStack stack) {
		return stack.is(Items.ROTTEN_FLESH)
				|| stack.is(Items.BEEF)
				|| stack.is(Items.MUTTON)
				|| stack.is(Items.RABBIT)
				|| stack.is(Items.CHICKEN)
				|| stack.is(Items.COOKED_BEEF)
				|| stack.is(Items.COOKED_MUTTON)
				|| stack.is(Items.COOKED_RABBIT)
				|| stack.is(Items.COOKED_CHICKEN);
	}

	@Override
	public ItemStack equipItemIfPossible(ItemStack stack) {
		EquipmentSlot slot = EquipmentSlot.MAINHAND;
		ItemStack heldStack = this.getItemBySlot(slot);
		if (heldStack != null && !heldStack.isEmpty()) {
			slot = EquipmentSlot.OFFHAND;
			heldStack = this.getItemBySlot(slot);
		}
		if (heldStack == null || heldStack.isEmpty()) {
			this.setItemSlotAndDropWhenKilled(slot, stack);
			// TODO this seems wrong
//			this.equipItemIfPossible(stack);
			return stack;
		}
		return ItemStack.EMPTY;
	}

	//	@Override
	//	protected void dropCustomDeathLoot(DamageSource p_21385_, int p_21386_, boolean p_21387_) {
	//		super.dropCustomDeathLoot(p_21385_, p_21386_, p_21387_);
	//	}

	public boolean hasMeatInventory() {
		return !this.getItemBySlot(EquipmentSlot.MAINHAND).isEmpty()
				|| !this.getItemBySlot(EquipmentSlot.OFFHAND).isEmpty();
	}

	protected SoundEvent getAmbientSound() {
		return SoundEvents.ZOMBIE_AMBIENT;
	}

	public boolean canOpenDoors() {
		return canOpenDoors;
	}

	public void setCanOpenDoors(boolean canOpenDoors) {
		this.canOpenDoors = canOpenDoors;
	}

	/**
	 * 
	 * @author Mark Gottschling on Apr 8, 2022
	 *
	 */
	public static class GhoulHealGoal extends Goal {
		private Ghoul ghoul;

		public GhoulHealGoal(Ghoul ghoul) {
			this.ghoul = ghoul;
		}

		@Override
		public boolean canUse() {
			if (!getGhoul().isAlive()) {
				return false;
			}
			else if (!getGhoul().onGround()) {
				return false;
			}
			else {
				// if health less than 3/4 && has some meat in inventory
				if (getGhoul().getHealth() < (0.75F * getGhoul().getMaxHealth()) && getGhoul().hasMeatInventory()) {
					return true;			
				}
			}
			return false;
		}

		@Override
		public void start() {
			// get hand with meat
			ItemStack meatStack = getGhoul().getItemBySlot(EquipmentSlot.MAINHAND);
			if (meatStack.isEmpty()) {
				meatStack = getGhoul().getItemBySlot(EquipmentSlot.OFFHAND);
			}
			if (!meatStack.isEmpty()) {
				meatStack.shrink(1);
				//				getGhoul().setHealth(Math.min(getGhoul().getHealth() + 4.0F, getGhoul().getMaxHealth()));
				getGhoul().heal(Config.Mobs.GHOUL.healAmount.get().floatValue());
			}
			super.start();
		}		

		public Ghoul getGhoul() {
			return ghoul;
		}
	}
}
