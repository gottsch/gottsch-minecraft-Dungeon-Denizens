package com.someguyssoftware.ddenizens.entity.ai.goal.demonoid;

import com.someguyssoftware.ddenizens.DD;
import com.someguyssoftware.ddenizens.entity.monster.DemonoidMonster;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;
import java.util.Random;

/**
 * @author by Mark Gottschling on 7/17/2025
 */
public class DemonoidLaunchGoal extends Goal {
    private final DemonoidMonster mob;
    private Player target;
    private double launchDistanceSq;
    private int maxHeight;
    private float speedModifier;
    private int launchMaxDuration;
    private double randomLaunchHeight;
    private Random random = new Random();

    public DemonoidLaunchGoal(DemonoidMonster mob, double launchDistance, int maxHeight, float speedModifier) {
        this.mob = mob;
        this.launchDistanceSq = launchDistance * launchDistance;
        this.maxHeight = maxHeight;
        this.speedModifier = speedModifier;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    /*
     * executes if:
     * 1] mob has a living target
     * 2] mob is not flagged to land
     * 3] mob is not flagged for land attack only
     * 4] distance from mob to target > 6 blocks
     * 5] is not flying
     */
    @Override
    public boolean canUse() {
        LivingEntity target = this.mob.getTarget();
        if (target == null
                || this.mob.shouldLand()
                || this.mob.isLandAttackOnly()
                || this.mob.distanceToSqr(target) <= launchDistanceSq) { // 6 blocks squared
            return false;
        }
        return !this.mob.isFlying();
    }

    /*
     * continues to execute if:
     * 0] not flying
     * 1] not flagged to land (target == null || wanted moveTo == null)
     * 2] not flagged for land attack only
     * 3] launchMaxDuration > 0
     * 4] haven't reached wanted moveTo position
     */
    @Override
    public boolean canContinueToUse() {
        if (!this.mob.isFlying()
                || this.mob.shouldLand()
                || this.mob.isLandAttackOnly()
                || this.launchMaxDuration <= 0) {
            return false;
        }
        if (Math.abs(this.mob.position().y() - this.mob.getMoveControl().getWantedY()) <= 0.5) {
            return false;
        }

        return true;
    }

    @Override
    public void start() {
        this.launchMaxDuration = 20;

        this.mob.setFlyingMode(true);

        this.randomLaunchHeight = (double)(random.nextFloat() * maxHeight);
//        DD.LOGGER.info("randomLaunchHeight -> {}", randomLaunchHeight);
        Vec3 vec3 = mob.position().add(0, randomLaunchHeight, 0);
//        DD.LOGGER.info("mob position -> {}, moveTo -> {}", mob.position(), vec3);

        this.mob.faceTarget(this.mob.getTarget());

        mob.getMoveControl().setWantedPosition(vec3.x, vec3.y, vec3.z, this.speedModifier);
    }

    @Override
    public void stop() {
        super.stop();
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    @Override
    public void tick() {
        this.launchMaxDuration--;
//        DD.LOGGER.info("launchMaxDuration -> {}", this.launchMaxDuration);

        // TODO could slow down as approach
        if (this.mob.getTarget() == null || !this.mob.getMoveControl().hasWanted()) {
//            DD.LOGGER.info("canContinue - no target or wanted -> should land");
            this.mob.setShouldLand(true);
        }
    }
}
