package com.someguyssoftware.ddenizens.entity.ai.control;

import com.someguyssoftware.ddenizens.entity.monster.DenizensMonster;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.phys.Vec3;

/**
 * Flying move control: steers the mob straight toward its wanted position at a
 * speed derived from {@link Attributes#FLYING_SPEED}, driving the vertical axis
 * directly so it can actually climb and descend. Pure locomotion — height limits,
 * landing and pathing decisions live in the volant goals.
 *
 * TODO lift to "gottsch's Monster Manual".
 * @author by Mark Gottschling on 7/17/2025
 */
public class FlyingGottschMonsterMoveControl extends MoveControl {
    private final DenizensMonster monster;

    public FlyingGottschMonsterMoveControl(DenizensMonster monster) {
        super(monster);
        this.monster = monster;
    }

    @Override
    public void tick() {
        if (operation != Operation.MOVE_TO) {
            return;
        }

        Vec3 toWanted = new Vec3(wantedX - monster.getX(), wantedY - monster.getY(), wantedZ - monster.getZ()).normalize();
        double speed = this.speedModifier * this.mob.getAttributeValue(Attributes.FLYING_SPEED);
        this.mob.setDeltaMovement(toWanted.scale(speed));

        // drive the vertical directly so the mob climbs/descends to wantedY
        double dy = this.wantedY - monster.getY();
        if (Math.abs(dy) > 0.1D) {
            Vec3 delta = this.mob.getDeltaMovement();
            this.mob.setDeltaMovement(delta.x, Math.signum(dy) * speed, delta.z);
        }
    }
}
