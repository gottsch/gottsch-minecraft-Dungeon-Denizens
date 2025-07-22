package com.someguyssoftware.ddenizens.entity.ai.goal.demonoid;

import com.someguyssoftware.ddenizens.entity.monster.DemonoidMonster;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

/**
 * @author by Mark Gottschling on 7/18/2025
 */
public class DemonoidFlyToMeleeRangeGoal extends Goal {

    private final DemonoidMonster mob;
    private final double speedModifier;
    private final double meleeDistanceSq;

    public DemonoidFlyToMeleeRangeGoal(DemonoidMonster mob, double meleeDistance, double speedModifier) {
        this.mob = mob;
        this.speedModifier = speedModifier;
        this.meleeDistanceSq = meleeDistance * meleeDistance;
        this.setFlags(EnumSet.of(Flag.MOVE));
    }

    public boolean canUse() {
        LivingEntity livingEntity = this.mob.getTarget();
        if (livingEntity != null && livingEntity.isAlive() && this.mob.isFlying()) {
            return mob.distanceToSqr(livingEntity) > meleeDistanceSq;
        }
        return false;
    }

    public boolean canContinueToUse() {
        return false;
    }

    public void start() {
        LivingEntity livingEntity = this.mob.getTarget();
        if (livingEntity != null) {
            // face target
            this.mob.faceTarget(livingEntity);
            Vec3 vec3 = livingEntity.position().add(0, 0.5, 0);
            mob.getMoveControl().setWantedPosition(vec3.x, vec3.y, vec3.z, this.speedModifier);
        }
    }

    public void stop() {
        super.stop();
    }
}
