package com.someguyssoftware.ddenizens.entity.ai.control;

import com.someguyssoftware.ddenizens.entity.monster.DenizensFlyingMonster;
import com.someguyssoftware.ddenizens.entity.monster.DenizensMonster;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

/**
 * TODO move to Gottsch's Monster Manual
 * TODO currently will work with DenizensFlyingMonster, but will refactor to use FlyingGottschMonster from GMM.
 * Notes: remember that is just the move control - the mechanism of locomotion, moving mob to target location.
 *  This class does not check for max/min heights etc.
 *
 * @author by Mark Gottschling on 7/17/2025
 */
public class FlyingGottschMonsterMoveControl extends MoveControl {
    // TODO probably needs another interface for VolantMonster
    private final DenizensMonster monster;
    private int floatDuration;

    public FlyingGottschMonsterMoveControl(DenizensMonster monster) {

        super(monster);
        this.monster = monster;
    }

    public void tick() {
        if (operation == Operation.MOVE_TO) {
            if (floatDuration-- <= 0) {
                floatDuration += monster.getRandom().nextInt(5) + 2;
                Vec3 vec3 = new Vec3(wantedX - monster.getX(), wantedY - monster.getY(), wantedZ - monster.getZ());
                double distance = vec3.length();
                vec3 = vec3.normalize();
                if (canReach(vec3, Mth.ceil(distance))) {
                    Vec3 delta = monster.getDeltaMovement().add(vec3.scale(0.1D));
                    monster.setDeltaMovement(delta);
                } else {
                    operation = Operation.WAIT;
                }
            }
        }
    }

    private boolean canReach(Vec3 vec3, int distance) {
        AABB aabb = monster.getBoundingBox();

        for(int i = 1; i < distance; ++i) {
            aabb = aabb.move(vec3);
            if (!monster.level().noCollision(monster, aabb)) {
                return false;
            }
        }
        return true;
    }
}

