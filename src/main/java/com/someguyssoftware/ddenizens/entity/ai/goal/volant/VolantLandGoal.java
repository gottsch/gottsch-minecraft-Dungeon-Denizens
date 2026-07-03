/*
 * This file is part of  Dungeon Denizens.
 * Copyright (c) 2025 Mark Gottschling (gottsch)
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
package com.someguyssoftware.ddenizens.entity.ai.goal.volant;

import com.someguyssoftware.ddenizens.entity.monster.IDenizensMonster;
import com.someguyssoftware.ddenizens.entity.monster.WingedHumanoid;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.EnumSet;

/**
 * Brings a flying {@link WingedHumanoid} safely back to the ground when it has no
 * target — e.g. after its target dies or escapes while it is airborne. {@link
 * VolantCombatGoal} owns landing while a target exists, so this only fires for the
 * targetless case (and runs at a lower priority).
 *
 * @author Mark Gottschling on July 26, 2025 (reworked)
 */
public class VolantLandGoal extends Goal {
    private final WingedHumanoid mob;
    private final double speedModifier;

    public VolantLandGoal(WingedHumanoid mob, double speedModifier) {
        this.mob = mob;
        this.speedModifier = speedModifier;
        setFlags(EnumSet.of(Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        return mob.isFlying() && mob.getTarget() == null;
    }

    @Override
    public boolean canContinueToUse() {
        // keep running until we are safely back on the ground (out of flight mode)
        return mob.isFlying();
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    @Override
    public void stop() {
        this.mob.fallDistance = 0.0F;
    }

    @Override
    public void tick() {
        BlockPos groundPos = IDenizensMonster.findSafeGroundPos(mob.level(), mob.getOnPos(), 16);
        if (groundPos == null) {
            // nowhere safe directly below: hover in place rather than drift off
            mob.getNavigation().stop();
            return;
        }
        if (mob.tickDescentToward(groundPos, speedModifier)) {
            mob.setWalkingMovement();
        }
    }
}
