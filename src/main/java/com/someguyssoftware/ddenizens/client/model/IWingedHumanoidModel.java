package com.someguyssoftware.ddenizens.client.model;

import net.minecraft.client.model.geom.ModelPart;

/**
 * @author by Mark Gottschling on 7/25/2025
 */
public interface IWingedHumanoidModel extends IHumanoidModel {
    // TODO change to use Speed and Swing
    static final float WING_SPEED = 0.35F;
    static final float WING_SWING = 0.225F;

    /*
     * wings
     */
    public ModelPart getRightWing();
    public ModelPart getLeftWing();

    default public void flapWings(float age) {}

    default public Rotation getRightWingOriginalRotation() {
        return ZERO_ROTATION;
    }

    default public Rotation getLeftWingOriginalRotation() {
        return ZERO_ROTATION;
    }

    default public Position getRightWingOriginalPosition() {
        return ZERO_POSITION;
    }
    default public Position getLeftWingOriginalPosition() {
        return ZERO_POSITION;
    }

    default public float getWingSwing() {
        return WING_SWING;
    }

    default public float getWingSpeed() {
        return WING_SPEED;
    }
}
