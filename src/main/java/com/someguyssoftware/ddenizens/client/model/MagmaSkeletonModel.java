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
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.someguyssoftware.ddenizens.DD;
import net.minecraft.client.model.AnimationUtils;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

/**
 *
 * @author Mark Gottschling on Feb 4, 2024
 *
 */
public class MagmaSkeletonModel<T extends Mob> extends SkeletonWarriorModel<T> {
    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(DD.MODID, "fossilized_skeleton_model"), "main");

    public ModelPart rightArm2;
    public ModelPart leftArm2;

    public Rotation rightArmRots;
    public Rotation rightArmRots2;
    public Rotation leftArmRots;
    public Rotation leftArmRots2;
    public Position headPos;
    public Position bodyPos;
    public Position rightArmPos;
    public Position rightArmPos2;
    public Position leftArmPos;
    public Position leftArmPos2;
    public Position rightLegPos;
    public Position leftLegPos;

    public MagmaSkeletonModel(ModelPart root) {
        super(root);

        ModelPart body = root.getChild("body");
        ModelPart bodyWrapper = body.getChild("body_wrapper");

        rightArm2 = root.getChild("right_arm2");
        leftArm2 = root.getChild("left_arm2");

        rightArmRots = new Rotation(this.rightArm);
        leftArmRots = new Rotation(this.leftArm);

        rightArmRots2 = new Rotation(this.rightArm2);
        leftArmRots2 = new Rotation(this.leftArm2);

        // save all the original positions
        bodyPos = new Position(this.body);
        rightArmPos = new Position(rightArm);
        rightArmPos2 = new Position(rightArm2);
        leftArmPos = new Position(leftArm);
        leftArmPos2 = new Position(leftArm2);
     }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = HumanoidModel.createMesh(CubeDeformation.NONE, 0.0F);
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 12.0F, 0.0F));
        PartDefinition body_wrapper = body.addOrReplaceChild("body_wrapper", CubeListBuilder.create().texOffs(16, 16).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -12.0F, 0.0F));
        PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition right_arm = partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(40, 16).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.0F, 2.0F, 0.0F, -0.6109F, 0.0F, 0.7418F));
        PartDefinition right_arm2 = partdefinition.addOrReplaceChild("right_arm2", CubeListBuilder.create().texOffs(40, 16).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.0F, 6.0F, 0.0F, 0.0F, 0.0F, 0.3927F));

        PartDefinition left_arm2 = partdefinition.addOrReplaceChild("left_arm2", CubeListBuilder.create().texOffs(40, 16).mirror().addBox(-1.0F, -2.0F, -1.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(5.0F, 2.0F, 0.0F, -0.6109F, 0.0F, -0.7418F));
        PartDefinition leftItem = left_arm2.addOrReplaceChild("leftItem", CubeListBuilder.create(), PartPose.offset(1.0F, 7.0F, 1.0F));
        PartDefinition left_arm = partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(40, 16).mirror().addBox(-1.0F, -2.0F, -1.0F, 2.0F, 8.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(5.0F, 6.0F, 0.0F, 0.0F, 0.0F, -0.3927F));
        PartDefinition leftItem2 = left_arm.addOrReplaceChild("leftItem2", CubeListBuilder.create(), PartPose.offset(1.0F, 4.0F, 1.0F));

        PartDefinition right_leg = partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 16).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, 12.0F, 0.0F, 0.0F, 0.0F, 0.0349F));
        PartDefinition left_leg = partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(0, 16).mirror().addBox(-1.0F, 0.0F, -1.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(2.0F, 12.0F, 0.0F, 0.0F, 0.0F, -0.0349F));

        PartDefinition hat = partdefinition.addOrReplaceChild("hat", CubeListBuilder.create().texOffs(32, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.5F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 32);
    }

    @Override
    public void prepareMobModel(T entity, float p_103794_, float p_103795_, float p_103796_) {
        this.rightArmPose = HumanoidModel.ArmPose.EMPTY;
        this.leftArmPose = HumanoidModel.ArmPose.EMPTY;
        ItemStack itemstack = entity.getItemInHand(InteractionHand.MAIN_HAND);
        if (itemstack.is(Items.BOW) && entity.isAggressive()) {
            if (entity.getMainArm() == HumanoidArm.RIGHT) {
                this.rightArmPose = HumanoidModel.ArmPose.BOW_AND_ARROW;
            } else {
                this.leftArmPose = HumanoidModel.ArmPose.BOW_AND_ARROW;
            }
        }
        super.prepareMobModel(entity, p_103794_, p_103795_, p_103796_);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        // head
        this.head.yRot = netHeadYaw * ((float)Math.PI / 180F);
        this.head.xRot = headPitch * ((float)Math.PI / 180F);

        // arms
        ItemStack itemStack = entity.getMainHandItem();

        float armSpeed = 0.25F;
        float radians = 0.5235988F; // 30
        this.rightArm.xRot = rightArmRots.x() + Mth.cos(limbSwing * armSpeed) * radians * 1.4F * limbSwingAmount;
        this.leftArm2.xRot = leftArmRots2.x() + Mth.cos(limbSwing * armSpeed + (float) Math.PI) * radians * 1.4F * limbSwingAmount;
        this.leftArm.xRot = rightArmRots.x() + Mth.cos(limbSwing * armSpeed) * radians * 1.4F * limbSwingAmount;
        this.rightArm2.xRot = leftArmRots2.x() + Mth.cos(limbSwing * armSpeed + (float) Math.PI) * radians * 1.4F * limbSwingAmount;

        // legs
        radians = 0.6F;
        float walkSpeed = 0.5F; // half speed = 0.5
        this.rightLeg.xRot = Mth.cos(limbSwing * walkSpeed) * radians  * 1.4F * limbSwingAmount;
        this.leftLeg.xRot = Mth.cos(limbSwing  * walkSpeed + (float)Math.PI) * radians * 1.4F * limbSwingAmount;

        // reset arm rotations before bobbing
        resetRotations(rightArm, rightArmRots);
        resetRotations(rightArm2, rightArmRots2);
        resetRotations(leftArm, leftArmRots);
        resetRotations(leftArm2, leftArmRots2);

        // bob entire body
        bobBody(ageInTicks);
        // this isn't working
        AnimationUtils.bobArms(this.rightArm, this.leftArm, ageInTicks);
        AnimationUtils.bobArms(this.rightArm2, this.leftArm2, ageInTicks + 180);

        if ((itemStack.isEmpty() || !itemStack.is(Items.BOW))) {
            setupAttackAnimation(entity, ageInTicks);
        } else if (itemStack.is(Items.BOW)) {
             if (entity.isAggressive()) {
                 poseRightArm(entity);
             }

            // swing arm regardless if holding item
            if (!entity.getOffhandItem().isEmpty()) {
                poseLeftArm(entity);
            }
            setupAttackAnimation(entity, ageInTicks, HumanoidArm.LEFT);
        }
    }

    /**
     * TODO abstract to a common model
     * Override as we need to be able to set the attack arm, which is private in vanilla
     * @param entity
     * @param fl
     */
    protected void setupAttackAnimation(T entity, float fl, HumanoidArm arm) {
        if (!(this.attackTime <= 0.0F)) {
            ModelPart modelpart = this.getArm(arm);
            float f = this.attackTime;
            this.body.yRot = Mth.sin(Mth.sqrt(f) * ((float)Math.PI * 2F)) * 0.2F;
            if (arm == HumanoidArm.LEFT) {
                this.body.yRot *= -1.0F;
            }

            this.rightArm.z = Mth.sin(this.body.yRot) * 5.0F;
            this.rightArm.x = -Mth.cos(this.body.yRot) * 5.0F;
            this.leftArm.z = -Mth.sin(this.body.yRot) * 5.0F;
            this.leftArm.x = Mth.cos(this.body.yRot) * 5.0F;
            this.rightArm.yRot += this.body.yRot;
            this.leftArm.yRot += this.body.yRot;
//            this.leftArm.xRot += this.body.yRot;
            f = 1.0F - this.attackTime;
            f *= f;
            f *= f;
            f = 1.0F - f;
            float f1 = Mth.sin(f * (float)Math.PI);
            float f2 = Mth.sin(this.attackTime * (float)Math.PI) * -(this.head.xRot - 0.7F) * 0.75F;
            modelpart.xRot -= f1 * 1.2F + f2;
            modelpart.yRot += this.body.yRot * 2.0F;
            modelpart.zRot += Mth.sin(this.attackTime * (float)Math.PI) * -0.4F;
        }
    }

    // TODO abstract out to a common skeleton/bow holding model
    private void poseRightArm(T entity) {
        switch (this.rightArmPose) {
            case BOW_AND_ARROW:
                this.rightArm.yRot = -0.1F + this.head.yRot;
                this.leftArm2.yRot = 0.1F + this.head.yRot + 0.4F;
                this.rightArm.xRot = (-(float)Math.PI / 2F) + this.head.xRot;
                this.leftArm2.xRot = (-(float)Math.PI / 2F) + this.head.xRot;
                break;
            default:
                this.rightArmPose.applyTransform(this, entity, net.minecraft.world.entity.HumanoidArm.RIGHT);
        }
    }

    private void poseLeftArm(T entity) {
        this.leftArm.yRot = leftArmRots.y() - 0.35F;
    }

    public void bobBody(float age) {
        float yAdjustment = (Mth.cos(age * 0.15F) * 0.35F + 0.05F);
        body.y = bodyPos.y() + yAdjustment;
        rightArm.y = rightArmPos.y() + yAdjustment;
        rightArm2.y = rightArmPos2.y() + yAdjustment;
        leftArm.y = leftArmPos.y() + yAdjustment;
        leftArm2.y = leftArmPos2.y() + yAdjustment;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        // don't render the hat
        body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        head.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        rightArm.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        leftArm.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        rightArm2.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        leftArm2.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        rightLeg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
        leftLeg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    @Override
    public void translateToHand(HumanoidArm arm, PoseStack poseStack) {
        float xAdjust = arm == HumanoidArm.RIGHT ? 1.55F : -3F; //(-3 for pose2)
        float zAdjust = arm == HumanoidArm.RIGHT ? 0F : 3F; //(3 for pose2)
        float yAdjust = arm == HumanoidArm.RIGHT ? 0F : -1F; //(-1 for pose2)
        ModelPart modelpart = this.getArm(arm);
        modelpart.x += xAdjust;
        modelpart.y += yAdjust;
        modelpart.z += zAdjust;
        modelpart.translateAndRotate(poseStack);
        modelpart.x -= xAdjust;
        modelpart.y -= yAdjust;
        modelpart.z -= zAdjust;
    }
}
