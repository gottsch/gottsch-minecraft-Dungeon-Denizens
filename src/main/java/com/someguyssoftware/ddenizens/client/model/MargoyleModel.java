package com.someguyssoftware.ddenizens.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.someguyssoftware.ddenizens.entity.monster.Gargoyle;
import net.minecraft.client.model.geom.ModelPart;

/**
 * TODO probably can extend the Gargoyle model with some changes?
 * @author by Mark Gottschling on 7/16/2025
 */
public class MargoyleModel<T extends Gargoyle> extends DemonlikeModel<T> {
    MargoyleModel(ModelPart root) {
        super(root);
    }

    @Override
    public void resetSwing(T entity, ModelPart body, ModelPart rightArm, ModelPart leftArm) {

    }

    @Override
    public ModelPart getHead() {
        return null;
    }

    @Override
    public ModelPart getBody() {
        return null;
    }

    @Override
    public ModelPart getRightArm() {
        return null;
    }

    @Override
    public ModelPart getLeftArm() {
        return null;
    }

    @Override
    public ModelPart getRightLeg() {
        return null;
    }

    @Override
    public ModelPart getLeftLeg() {
        return null;
    }

    @Override
    public void setupAnim(T t, float v, float v1, float v2, float v3, float v4) {

    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int i, int i1, float v, float v1, float v2, float v3) {

    }
    // TODO
}
