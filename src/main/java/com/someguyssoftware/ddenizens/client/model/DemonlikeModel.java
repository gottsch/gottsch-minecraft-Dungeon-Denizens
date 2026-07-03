package com.someguyssoftware.ddenizens.client.model;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;

import java.util.Optional;

/**
 *  * A custom model implementing custom IDemonModel.
 *  * Models extending this cannot hold items. Additional custom entity and rendering code is necessary.
 * @author by Mark Gottschling on 7/15/2025
 */
 // TODO extends vanilla HumanoidModel
public abstract class DemonlikeModel<T extends Entity> extends HumanlikeModel<T> implements IDemonoidModel {
    // TODO add properties
    protected ModelPart tail;
    protected ModelPart upperTail;
    protected ModelPart lowerTail;
    protected ModelPart rightWing;
    protected ModelPart rightWingAxis;
    protected ModelPart rightWingMedius;
    protected ModelPart leftWing;
    protected ModelPart leftWingAxis;
    protected ModelPart leftWingMedius;

    protected Rotation rightWingRots;
    protected Rotation leftWingRots;

    protected Rotation tailRots;

    // TODO add methods
    DemonlikeModel(ModelPart root) {
    }

    @Override
    public void swingTail(float ageInTicks) {
        Optional.ofNullable(getTail()).ifPresent(tail -> {
            tail.xRot = getTailOriginalRotation().x() + Mth.cos(ageInTicks * getTailSpeeds().x()) * getTailSwings().x();
            tail.zRot = Mth.cos(ageInTicks * getTailSpeeds().z()) * getTailSwings().z();
        });
    }

    @Override
    public void flapWings(float age) {
        float flapRotation = (Mth.cos(age * getWingSpeed()) * getWingSwing() + 0.05F);
        Optional.ofNullable(getRightWing()).ifPresent(wing -> {
            wing.yRot = getRightWingOriginalRotation().y() + flapRotation;
        });
        Optional.ofNullable(getLeftWing()).ifPresent(wing -> {
            wing.yRot = getLeftWingOriginalRotation().y() - flapRotation;
        });
    }

    @Override
    public ModelPart getRightWing() {
        return this.rightWing;
    }

    @Override
    public ModelPart getLeftWing() {
        return this.leftWing;
    }

    @Override
    public Rotation getRightWingOriginalRotation() {
        return rightWingRots;
    }

    @Override
    public Rotation getLeftWingOriginalRotation() {
        return leftWingRots;
    }

    @Override
    public ModelPart getTail() {
        return this.tail;
    }

    @Override
    public Rotation getTailOriginalRotation() {
        //0.3926991F
        return this.tailRots;
    }

    public ModelPart getLowerTail() {
        return this.lowerTail;
    }
}
