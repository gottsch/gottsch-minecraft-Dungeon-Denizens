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
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.EnumSet;

/**
 * Single combat goal for a {@link WingedHumanoid}. Drives the full cycle:
 *
 * <pre>
 *   GROUND ── target in reach ─────────────▶ bite (on cooldown)
 *         ── target far / unreachable ─────▶ LAUNCH
 *   LAUNCH ── climbed / timed out ─────────▶ FLY
 *   FLY    ── horizontally over target ────▶ LAND
 *   LAND   ── touched down ────────────────▶ GROUND
 * </pre>
 *
 * Flight is a travel mode used to close the gap (and clear obstacles); the bite
 * itself always happens on the ground. The airborne ceiling comes from
 * {@link WingedHumanoid#getMaxFlyHeight()}, so a true flyer (Gargoyle) cruises
 * high while a low hoverer (Margoyle) merely skims.
 *
 * <p>When the target is lost mid-flight this goal stops and flags a landing;
 * {@link VolantLandGoal} then brings the mob down.
 *
 * @author Mark Gottschling on July 26, 2025 (reworked)
 */
public class VolantCombatGoal extends Goal {
    private static final int ATTACK_COOLDOWN_TICKS = 20;
    private static final int LAUNCH_MAX_TICKS = 30;
    private static final int STUCK_PATH_TICKS = 20;

    private final WingedHumanoid mob;
    private final double meleeReach;      // center distance (blocks) at which it bites
    private final double launchRange;     // ground distance beyond which it launches
    private final double walkSpeed;
    private final double flySpeed;
    private final double landSpeed;

    private int attackCooldown;
    private int launchTicks;
    private int stuckPathTicks;
    private double launchStartY;

    public VolantCombatGoal(WingedHumanoid mob, double meleeReach, double launchRange, double walkSpeed, double flySpeed) {
        this.mob = mob;
        this.meleeReach = meleeReach;
        this.launchRange = launchRange;
        this.walkSpeed = walkSpeed;
        this.flySpeed = flySpeed;
        this.landSpeed = Math.max(0.4D, flySpeed * 0.5D);
        setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        LivingEntity target = mob.getTarget();
        return target != null && target.isAlive();
    }

    @Override
    public boolean canContinueToUse() {
        LivingEntity target = mob.getTarget();
        return target != null && target.isAlive();
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    @Override
    public void start() {
        this.attackCooldown = 0;
        this.launchTicks = 0;
        this.stuckPathTicks = 0;
    }

    @Override
    public void stop() {
        mob.getNavigation().stop();
        this.attackCooldown = 0;
        // target lost while airborne: hand off to VolantLandGoal
        if (mob.isFlying()) {
            mob.setIsLanding(true);
        }
    }

    @Override
    public void tick() {
        if (attackCooldown > 0) {
            attackCooldown--;
        }
        LivingEntity target = mob.getTarget();
        if (target == null) {
            return;
        }

        if (mob.isFlying()) {
            tickFlying(target);
        } else {
            tickGrounded(target);
        }
    }

    // ───────────────────────────── grounded ─────────────────────────────

    private void tickGrounded(LivingEntity target) {
        mob.getLookControl().setLookAt(target, 30.0F, 30.0F);
        double distSq = mob.distanceToSqr(target);

        // in reach: stop and bite
        if (distSq <= meleeReachSq()) {
            mob.getNavigation().stop();
            mob.faceTarget(target);
            tryBite(target);
            return;
        }

        // too far: launch to close the gap
        if (distSq > launchRange * launchRange) {
            beginLaunch();
            return;
        }

        // within launch range: walk toward the target; launch if we can't path
        boolean pathing = mob.getNavigation().moveTo(target, walkSpeed);
        if (!pathing) {
            if (++stuckPathTicks > STUCK_PATH_TICKS) {
                stuckPathTicks = 0;
                beginLaunch();
            }
        } else {
            stuckPathTicks = 0;
        }
    }

    private void tryBite(LivingEntity target) {
        if (attackCooldown <= 0) {
            attackCooldown = ATTACK_COOLDOWN_TICKS;
            mob.swing(InteractionHand.MAIN_HAND);
            mob.doHurtTarget(target);
        }
    }

    // ───────────────────────────── airborne ─────────────────────────────

    private void beginLaunch() {
        mob.setFlyingMovement();      // FLY movement state
        mob.setIsLaunching(true);     // LAUNCH phase
        this.launchTicks = 0;
        this.launchStartY = mob.getY();
    }

    private void tickFlying(LivingEntity target) {
        mob.faceTarget(target);
        mob.getLookControl().setLookAt(target, 30.0F, 30.0F);

        if (mob.isLaunching()) {
            tickLaunch();
        } else if (mob.isLanding()) {
            tickLand(target);
        } else {
            tickCruise(target);
        }
    }

    /** Rise (mostly) in place until at altitude, then transition to cruising. */
    private void tickLaunch() {
        launchTicks++;
        double climbY = launchStartY + mob.getMaxFlyHeight();
        mob.getMoveControl().setWantedPosition(mob.getX(), climbY, mob.getZ(), flySpeed);

        boolean atAltitude = (mob.getY() - launchStartY) >= mob.getMaxFlyHeight() - 0.5D;
        if (atAltitude || launchTicks >= LAUNCH_MAX_TICKS) {
            mob.setFlyingMovement(); // -> FLY phase
        }
    }

    /** Steer horizontally toward the target at cruise altitude; trigger landing when close. */
    private void tickCruise(LivingEntity target) {
        double cruiseY = target.getY() + mob.getMaxFlyHeight();
        mob.getMoveControl().setWantedPosition(target.getX(), cruiseY, target.getZ(), flySpeed);

        double landTrigger = meleeReach + 1.0D;
        if (horizontalDistSq(mob, target) <= landTrigger * landTrigger) {
            mob.setIsLanding(true);
        }
    }

    /** Descend onto the target's ground column; on touchdown return to walking. */
    private void tickLand(LivingEntity target) {
        BlockPos groundPos = (target != null)
                ? target.getOnPos()
                : IDenizensMonster.findSafeGroundPos(mob.level(), mob.getOnPos(), 16);

        if (groundPos == null) {
            mob.getNavigation().stop();
            return;
        }
        if (mob.tickDescentToward(groundPos, landSpeed)) {
            mob.setWalkingMovement();
        }
    }

    // ───────────────────────────── helpers ─────────────────────────────

    private double meleeReachSq() {
        return meleeReach * meleeReach;
    }

    private static double horizontalDistSq(Entity a, Entity b) {
        double dx = a.getX() - b.getX();
        double dz = a.getZ() - b.getZ();
        return dx * dx + dz * dz;
    }
}
