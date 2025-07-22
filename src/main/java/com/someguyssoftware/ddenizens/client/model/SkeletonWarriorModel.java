/*
 * This file is part of  Dungeon Denizens.
 * Copyright (c) 2024 Mark Gottschling (gottsch)
 *
 * All rights reserved.
 *
 * Dungeon Denizens is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Dungeon Denizens is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Dungeon Denizens.  If not, see <http://www.gnu.org/licenses/lgpl>.
 */
package com.someguyssoftware.ddenizens.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.someguyssoftware.ddenizens.DD;
import net.minecraft.client.model.AnimationUtils;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.Mob;

/**
 *
 * @author Mark Gottschling on Jan 19, 2024
 *
 */
public class SkeletonWarriorModel<T extends Mob> extends HumanoidModel<T> {

    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(DD.MODID, "skeleton_warrior_model"), "main");

    protected Rotation rightArmRots;
    protected Rotation leftArmRots;

    public SkeletonWarriorModel(ModelPart root) {
        super(root);
        rightArmRots = new Rotation(this.rightArm);
        leftArmRots = new Rotation(this.leftArm);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = HumanoidModel.createMesh(CubeDeformation.NONE, 0.0F);
        PartDefinition partdefinition = meshdefinition.getRoot();

        partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(40, 16).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 12.0F, 2.0F), PartPose.offset(-5.0F, 2.0F, 0.0F));
        partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(40, 16).mirror().addBox(-1.0F, -2.0F, -1.0F, 2.0F, 12.0F, 2.0F), PartPose.offset(5.0F, 2.0F, 0.0F));
        partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 16).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 12.0F, 2.0F), PartPose.offset(-2.0F, 12.0F, 0.0F));
        partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(0, 16).mirror().addBox(-1.0F, 0.0F, -1.0F, 2.0F, 12.0F, 2.0F), PartPose.offset(2.0F, 12.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 32);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        // head
        this.head.yRot = netHeadYaw * ((float)Math.PI / 180F);
        this.head.xRot = headPitch * ((float)Math.PI / 180F);

        // arms
        // 0.5235988F = 30 degrees
        float armSpeed = 0.25F;
        float radians = 0.5235988F; // 30
        this.rightArm.xRot = -0.7417649F + Mth.cos(limbSwing * armSpeed) * radians * 1.4F * limbSwingAmount;
        this.leftArm.xRot = -0.567232F + Mth.cos(limbSwing * armSpeed + (float)Math.PI) * radians * 1.4F * limbSwingAmount;

        // legs
        radians = 0.6F;
        float walkSpeed = 0.5F; // half speed = 0.5
        this.rightLeg.xRot = Mth.cos(limbSwing * walkSpeed) * radians  * 1.4F * limbSwingAmount;
        this.leftLeg.xRot = Mth.cos(limbSwing  * walkSpeed + (float)Math.PI) * radians * 1.4F * limbSwingAmount;

        // reset arm rotations before bobbing
        resetRotations(rightArm, rightArmRots);
        resetRotations(leftArm, leftArmRots);

        AnimationUtils.bobArms(this.rightArm, this.leftArm, ageInTicks);

        setupAttackAnimation(entity, ageInTicks);
    }

    public void resetRotations(ModelPart part, Rotation rotations) {
//        part.xRot = rotations.x();
        part.yRot = rotations.y();
        part.zRot = rotations.z();
    }

    /**
     * Need to override this as the skeleton's arm is thinner than a normal
     * humanoid and then the item won't be in the correct position.
     * @param arm
     * @param poseStack
     */
    @Override
    public void translateToHand(HumanoidArm arm, PoseStack poseStack) {
        float side = (arm == HumanoidArm.RIGHT) ? 1.0F : -1.0F;
        ModelPart modelpart = this.getArm(arm);
        modelpart.x += side;
        modelpart.translateAndRotate(poseStack);
        modelpart.x -= side;
    }
}
