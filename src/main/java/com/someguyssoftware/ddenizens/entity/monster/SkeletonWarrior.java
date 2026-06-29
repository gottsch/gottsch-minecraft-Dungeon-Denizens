/*
 * This file is part of  Dungeon Denizens.
 * Copyright (c) 2024 Mark Gottschling (gottsch)
 *
 * All rights reserved.
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
package com.someguyssoftware.ddenizens.entity.monster;

import com.google.common.collect.Maps;
import com.someguyssoftware.ddenizens.entity.ai.goal.target.SummonedOwnerTargetGoal;
import com.someguyssoftware.ddenizens.setup.Registration;
import com.someguyssoftware.ddenizens.util.EquipmentUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Is not extended from Skeleton/AbstractSkeleton
 * but most of the functionality is the same.
 * @author Mark Gottschling on Jan 19, 2024
 *
 */
public class SkeletonWarrior extends DenizensMonster {

    private LivingEntity owner;

    private static final Map<EquipmentSlot, List<Item>> EQUIPMENT_MAP = Maps.newHashMap();

    // TODO use custom tags instead and register on tags load
    static {
        EQUIPMENT_MAP.put(EquipmentSlot.HEAD, Arrays.asList(Items.LEATHER_HELMET, Items.CHAINMAIL_HELMET, Items.GOLDEN_HELMET));
        EQUIPMENT_MAP.put(EquipmentSlot.CHEST, Arrays.asList(Items.LEATHER_CHESTPLATE, Items.CHAINMAIL_CHESTPLATE));
        EQUIPMENT_MAP.put(EquipmentSlot.LEGS, Arrays.asList(Items.LEATHER_LEGGINGS, Items.CHAINMAIL_LEGGINGS));
        EQUIPMENT_MAP.put(EquipmentSlot.FEET, Arrays.asList(Items.LEATHER_BOOTS, Items.CHAINMAIL_BOOTS));
        EQUIPMENT_MAP.put(EquipmentSlot.MAINHAND, Arrays.asList(
                Items.STONE_SWORD,
                Items.STONE_AXE,
                Items.GOLDEN_SWORD,
                Items.GOLDEN_AXE,
                Items.IRON_SWORD,
                Items.IRON_AXE,
                Registration.RUSTY_IRON_AXE1.get(),
                Registration.RUSTY_IRON_AXE2.get(),
                Registration.RUSTY_IRON_SWORD1.get(),
                Registration.RUSTY_IRON_SWORD2.get(),
                Registration.RUSTY_IRON_SWORD3.get(),
                Registration.RUSTY_IRON_SWORD4.get()
                ));
    }

    public SkeletonWarrior(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level, MonsterSize.MEDIUM);
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(2, new RestrictSunGoal(this));
        this.goalSelector.addGoal(3, new FleeSunGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new AvoidEntityGoal<>(this, Boulder.class, 6.0F, 1.0D, 1.2D, IDenizensMonster.avoidBoulder));
        this.goalSelector.addGoal(4, new MeleeAttackGoal(this, 1.0D, false));
        this.goalSelector.addGoal(5, new MoveThroughVillageGoal(this, 1.0D, true, 4, () -> true));
        this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new SummonedOwnerTargetGoal(this));
        this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true, playerNotOwner));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMonsterAttributes().add(Attributes.MOVEMENT_SPEED, 0.25D);
    }

    /**
     *
     * @param randomSource
     * @param difficultyInstance
     */
    protected void populateDefaultEquipmentSlots(RandomSource randomSource, DifficultyInstance difficultyInstance) {
        super.populateDefaultEquipmentSlots(randomSource, difficultyInstance);

        // randomize armor
        List<EquipmentSlot> availableSlots = new ArrayList<EquipmentSlot>(List.of(EquipmentUtil.ARMOR_SLOTS));
        for (int num = 0; num < random.nextInt(availableSlots.size()); num++) {
            EquipmentSlot slot = availableSlots.get(random.nextInt(availableSlots.size()));
            setRandomEquipment(slot);
            availableSlots.remove(slot); // can't remove ??
        }

        // randomize weapons
        setRandomEquipment(EquipmentSlot.MAINHAND);
    }

    /**
     *
     * @param slot
     */
    protected void setRandomEquipment(EquipmentSlot slot) {
        /* NOTE
         * as setItemSlotAndDropWhenKilled() makes the entity persistent (not despawnable),
         * don't want that for spawned equipment.
         */
        if (slot.isArmor()) {
            this.armorDropChances[slot.getIndex()] = 0.75F;
        } else {
            this.handDropChances[slot.getIndex()] = 0.75F;
        }
        this.setItemSlot(slot, selectRandomEquipment(slot));
    }

    protected ItemStack selectRandomEquipment(EquipmentSlot slot) {
        Item equipment = EQUIPMENT_MAP.get(slot).get(random.nextInt(EQUIPMENT_MAP.get(slot).size()));
        ItemStack equipmentStack = new ItemStack(equipment, 1);
        // spawn with worn (randomly damaged) gear
        if (equipmentStack.isDamageableItem()) {
            equipmentStack.setDamageValue(this.random.nextInt(equipmentStack.getMaxDamage() - (int)(equipmentStack.getMaxDamage() * 0.1)));
        }
        return equipmentStack;
    }

    @Nullable
    public SpawnGroupData finalizeSpawn(@NotNull ServerLevelAccessor levelAccessor, @NotNull DifficultyInstance difficultyInstance, @NotNull MobSpawnType spawnType, @Nullable SpawnGroupData groupData, @Nullable CompoundTag tag) {
        groupData = super.finalizeSpawn(levelAccessor, difficultyInstance, spawnType, groupData, tag);

        RandomSource randomSource = levelAccessor.getRandom();
        this.populateDefaultEquipmentSlots(randomSource, difficultyInstance);
        this.populateDefaultEquipmentEnchantments(randomSource, difficultyInstance);

        // TODO FUTURE if EnemyEcholonsAPI is present, apply rules

        // pick up loot
        this.setCanPickUpLoot(true);

        return groupData;
    }

    @Override
    public boolean wantsToPickUp(ItemStack stack) {
        // will pick up any armor slot item or swords and axes or custom items
        EquipmentSlot slot = getEquipmentSlotForItem(stack);
        return EquipmentUtil.ARMOR_LIST.contains(slot)
                || stack.getItem() instanceof SwordItem
                || stack.getItem() instanceof AxeItem;
    }

    @Override
    public @NotNull ItemStack equipItemIfPossible(@NotNull ItemStack pickedUpStack) {
        EquipmentSlot slot = getEquipmentSlotForItem(pickedUpStack);
        ItemStack currentEquipmentStack = this.getItemBySlot(slot);

        if (currentEquipmentStack.isEmpty()) {
            // equip itItem
            ItemStack pickedUpStackCopy = pickedUpStack.copyWithCount(1);
            this.setItemSlotAndDropWhenKilled(slot, pickedUpStackCopy);
            return pickedUpStackCopy;
        }
        return ItemStack.EMPTY;

    }

    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(this.getStepSound(), 0.15F, 1.0F);
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.SKELETON_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource p_33579_) {
        return SoundEvents.SKELETON_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.SKELETON_DEATH;
    }

    protected SoundEvent getStepSound() {
        return SoundEvents.SKELETON_STEP;
    }

    @Override
    public @NotNull MobType getMobType() {
        return MobType.UNDEAD;
    }

    // NOTE helmet doesn't get damaged by the sun
    @Override
    public void aiStep() {
        boolean flag = this.isSunBurnTick();
        if (flag) {
            ItemStack itemstack = this.getItemBySlot(EquipmentSlot.HEAD);
            if (!itemstack.isEmpty()) {
                flag = false;
            }

            if (flag) {
                this.setSecondsOnFire(8);
            }
        }
        super.aiStep();
    }

    protected float getStandingEyeHeight(Pose p_32154_, EntityDimensions p_32155_) {
        return 1.74F;
    }

}
