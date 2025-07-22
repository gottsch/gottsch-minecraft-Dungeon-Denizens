package com.someguyssoftware.ddenizens.entity.ai.goal.demonoid;

import com.someguyssoftware.ddenizens.DD;
import com.someguyssoftware.ddenizens.entity.monster.Gargoyle;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

/**
 * @author by Mark Gottschling on 7/10/2025
 */
@Deprecated
public class DemonoidFleeOnHitGoal extends Goal {
    // TODO change to IDemonoid
    private final Gargoyle mob;

    public DemonoidFleeOnHitGoal(Gargoyle mob) {
        this.mob = mob;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        return this.mob.shouldFlee;
    }

    @Override
    public void start() {
        DD.LOGGER.info("gargoyle is starting FleeOnHitGoal");
        // Don't continue if there's no target to flee from
        LivingEntity attacker = this.mob.getTarget();
        if (attacker == null) {
            DD.LOGGER.info("gargoyle doesn't have a target to flee from");
            this.mob.shouldFlee = false; // Reset the flag and stop
            return;
        }

        this.mob.setFlyingMode(true);
        DD.LOGGER.info("gargoyle's gravity state -> {}", !this.mob.isNoGravity());
        DD.LOGGER.info("gargoyle's flying state -> {}", this.mob.isFlying());

        // TODO looks like the AI stuff using DefaultRandomPos is garbage.
        // TODO look at using Beholder flying code to move to a random location7
        // TODO after completing the move, the gargoyle needs to land and have a fly cooldown period.

        // **THE FIX:** We get the position of the attacker to flee FROM.
        Vec3 fleeFromPos = attacker.position();

        Vec3 fleeToPos = DefaultRandomPos.getPosAway(this.mob, 0, 16, fleeFromPos);
//        if (fleePos != null) {
//            this.mob.getNavigation().moveTo(fleePos.x, fleePos.y, fleePos.z, 1.0D);
//            DD.LOGGER.info("gargoyle fleeing to {} from {}", fleePos, this.mob.position());
//        }
        // **THE FAILSAFE:** Check if a valid position was found before trying to move.
        if (fleeToPos != null) {
            DD.LOGGER.info("gargoyle fleeing to {} from {}", fleeToPos, fleeFromPos);
            this.mob.getNavigation().moveTo(fleeToPos.x, fleeToPos.y, fleeToPos.z, 1.2D);
        } else {
            DD.LOGGER.info("no pos found to flee to, stopping goal");
            // If no position was found, just stop the goal.
            this.stop();
        }
    }

    @Override
    public boolean canContinueToUse() {
        return !this.mob.getNavigation().isDone();
    }

    @Override
    public void stop() {
        this.mob.shouldFlee = false;
        this.mob.setFlyingMode(false);
    }
}
