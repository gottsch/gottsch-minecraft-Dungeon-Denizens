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

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Predicate;

/**
 *
 * @author Mark Gottschling on Jan 9, 2024
 *
 */
public interface IDenizensMonster {
    public static final String SUMMONED_OWNER = "summonedOwner";
    public static final String PLAYER_OWNER = "playerOwner";

    public static Predicate<LivingEntity> avoidBoulder = (entity) -> {
        if (entity instanceof Boulder) {
            return ((Boulder)entity).isActive();
        }
        return false;
    };

    /**
     * Finds the highest ground position, useful for teleporting or spawning entities.
     * This method iterates downwards until it finds a solid block, then iterates upwards
     * until it finds the first air block (or non-solid, non-full block) on top of it.
     * This helps ensure the entity spawns *on* the ground, not inside it.
     *
     * @param level The world (Level) instance.
     * @param startPos The starting BlockPos to search from.
     * @param maxSearchDistance The maximum distance to search downwards.
     * @return The BlockPos of the block *above* the ground (where an entity would stand),
     * or null if suitable ground isn't found.
     */
    public static BlockPos findSafeGroundPos(Level level, BlockPos startPos, int maxSearchDistance) {
        BlockPos.MutableBlockPos currentPos = startPos.mutable();

        // 1. Search downwards for a solid block
        for (int i = 0; i < maxSearchDistance; i++) {
            BlockState state = level.getBlockState(currentPos);

            // If we found a block that isn't air and isn't replaceable (like tall grass)
            // and is considered solid (e.g., for collision), then this is potentially ground.
            if (!state.isAir() && !state.canBeReplaced() && state.isCollisionShapeFullBlock(level, currentPos)) {
                break; // Found the ground level
            }

            currentPos.move(0, -1, 0); // Move down
            if (currentPos.getY() < level.getMinBuildHeight()) {
                return null; // Reached bottom of world without finding ground
            }
        }

        // 2. Now that we've found a solid block (or the bottom), iterate upwards to find the first air block
        // This is where an entity would stand.
        BlockPos groundLevel = currentPos.immutable(); // This is the block *of* the ground

        // Check the block above the detected ground
        BlockPos posAboveGround = groundLevel.above();
        if (level.getBlockState(posAboveGround).isAir() && level.getBlockState(posAboveGround.above()).isAir()) {
            return posAboveGround; // Found two air blocks, safe to place entity here
        }

        // If the spot above the found ground isn't fully air, try moving up further
        // but ensure we don't go too far above the original start position.
        BlockPos.MutableBlockPos safePos = groundLevel.mutable().move(0,1,0); // Start checking above the solid block
        for (int i = 0; i < maxSearchDistance; i++) { // Limit the upward search as well
            if (level.getBlockState(safePos).isAir() && level.getBlockState(safePos.above()).isAir()) {
                return safePos.immutable();
            }
            safePos.move(0, 1, 0);
            // Don't search too far above the original start Y, otherwise we might end up way too high
            if (safePos.getY() > startPos.getY() + 10) { // arbitrary limit, adjust as needed
                break;
            }
        }


        return null; // Could not find a suitable safe spot
    }

    public MonsterSize getMonsterSize();
    public void setMonsterSize(MonsterSize size);

    default public boolean canSummonedHaveOwner() {
        return false;
    }

    default public int getSummonedLifespan() {
        return 0;
    }

    default public void setSummonedLifespan(int lifespan) { }

    default public LivingEntity getSummonedOwner() {
        return null;
    }

    default public void setSummonedOwner(LivingEntity entity) {}
}
