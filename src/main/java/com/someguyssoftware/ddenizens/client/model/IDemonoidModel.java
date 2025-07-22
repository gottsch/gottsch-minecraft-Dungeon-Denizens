package com.someguyssoftware.ddenizens.client.model;

import net.minecraft.client.model.geom.ModelPart;

/**
 * @author by Mark Gottschling on 7/7/2025
 */
public interface IDemonoidModel extends IHumanoidModel {
    // TODO change to use Speed and Swing
    static final float WING_SPEED = 0.35F;
    static final float WING_SWING = 0.225F;

    static final Speed TAIL_SPEED = new Speed(0.1F, 0, 0.05F);
    static final Swing TAIL_SWING = new Swing(0.12F, 0, 0.5235988F);

    static final Position ZERO_POSITION = new Position(0, 0, 0);
    static final Rotation ZERO_ROTATION = new Rotation(0, 0, 0);

    /*
     * tail
     */
    void swingTail(float ageInTicks);

    public ModelPart getTail();

    default public Rotation getTailOriginalRotation() {
        return ZERO_ROTATION;
    }
    default public Position getTailOriginalPosition() {
        return ZERO_POSITION;
    }

    default Swing getTailSwings() {
        return TAIL_SWING;
    }

    default public Speed getTailSpeeds() {
        return TAIL_SPEED;
    }

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
