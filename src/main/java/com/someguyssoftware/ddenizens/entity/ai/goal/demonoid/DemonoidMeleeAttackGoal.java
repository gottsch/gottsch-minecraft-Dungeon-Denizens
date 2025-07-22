package com.someguyssoftware.ddenizens.entity.ai.goal.demonoid;

import com.someguyssoftware.ddenizens.entity.monster.DemonoidMonster;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;

/**
 *
 * @author by Mark Gottschling on 7/18/2025
 */
public class DemonoidMeleeAttackGoal extends MeleeAttackGoal {
    private double distanceSq;
    private boolean vanillaStop;

    public DemonoidMeleeAttackGoal(DemonoidMonster mob, double distance, double speedModifier, boolean followNotSeen) {
        super(mob, speedModifier, followNotSeen);
        distanceSq = distance * distance;
    }

    @Override
    public boolean canUse() {
        return super.canUse()
                && !((DemonoidMonster)this.mob).isFlying()
                && this.mob.distanceToSqr(mob.getTarget()) <= distanceSq;
    }

    @Override
    public boolean canContinueToUse() {
        vanillaStop = !super.canContinueToUse();
                return !vanillaStop && !((DemonoidMonster)this.mob).isFlying()
                && this.mob.distanceToSqr(mob.getTarget()) <= distanceSq;
    }

    @Override
    public void stop() {
//        if (mob.getTarget() == null) {
        if (vanillaStop) {
            super.stop();
        }
        mob.getNavigation().stop();
    }
}
