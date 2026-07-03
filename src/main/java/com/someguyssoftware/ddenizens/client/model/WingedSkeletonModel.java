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
 * @author Mark Gottschling on Feb 1, 2024
 *
 */
public class WingedSkeletonModel<T extends Mob> extends HumanoidModel<T> {

	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(DD.MODID, "winged_skeleton_model"), "main");

	public final ModelPart rightFlapAxis;
	public final ModelPart leftFlapAxis;

	public Rotation rightArmRots;
	public Rotation leftArmRots;
	public Position headPos;
	public Position bodyPos;
	public Position rightArmPos;
	public Position leftArmPos;
	public Position rightLegPos;
	public Position leftLegPos;

	public Rotation rightFlapAxisRots;
	public Rotation leftFlapAxisRots;

	public WingedSkeletonModel(ModelPart root) {
		super(root);

		ModelPart body = root.getChild("body");
		ModelPart bodyWrapper = body.getChild("body_wrapper");
		rightFlapAxis = bodyWrapper.getChild("rightFlapAxis");
		leftFlapAxis = bodyWrapper.getChild("leftFlapAxis");

		rightFlapAxisRots = new Rotation(rightFlapAxis);
		leftFlapAxisRots = new Rotation(leftFlapAxis);

		rightArmRots = new Rotation(this.rightArm);
		leftArmRots = new Rotation(this.leftArm);

		// save all the original positions
		headPos = new Position(this.head);
		bodyPos = new Position(this.body);
		rightArmPos = new Position(rightArm);
		leftArmPos = new Position(leftArm);
		rightLegPos = new Position(rightLeg);
		leftLegPos = new Position(leftLeg);
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = HumanoidModel.createMesh(CubeDeformation.NONE, 0.0F);
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0436F, 0.0F, 0.0F));
		PartDefinition body_wrapper = body.addOrReplaceChild("body_wrapper", CubeListBuilder.create().texOffs(0, 17).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition leftFlapAxis = body_wrapper.addOrReplaceChild("leftFlapAxis", CubeListBuilder.create(), PartPose.offsetAndRotation(1.5F, 2.0F, 2.0F, 0.1309F, -0.3927F, 0.0F));
		PartDefinition leftWing = leftFlapAxis.addOrReplaceChild("leftWing", CubeListBuilder.create().texOffs(33, 0).addBox(-0.5F, -11.0F, 0.0F, 1.0F, 12.0F, 1.0F, new CubeDeformation(0.02F))
				.texOffs(25, 17).mirror().addBox(0.0F, -11.0F, 0.5F, 12.0F, 12.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.7854F));
		PartDefinition leftEnd = leftWing.addOrReplaceChild("leftEnd", CubeListBuilder.create().texOffs(33, 0).addBox(-1.0F, -11.0F, 0.0F, 1.0F, 12.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(11.0F, -11.0F, 0.0F, 0.0F, 0.0F, -1.5708F));

		PartDefinition rightFlapAxis = body_wrapper.addOrReplaceChild("rightFlapAxis", CubeListBuilder.create(), PartPose.offsetAndRotation(-1.5F, 2.0F, 2.0F, 0.1309F, 0.3927F, 0.0F));
		PartDefinition rightWing = rightFlapAxis.addOrReplaceChild("rightWing", CubeListBuilder.create().texOffs(33, 0).addBox(-0.5F, -11.0F, 0.0F, 1.0F, 12.0F, 1.0F, new CubeDeformation(0.02F))
				.texOffs(25, 17).addBox(-11.5F, -11.0F, 0.5F, 12.0F, 12.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.7854F));
		PartDefinition rightEnd = rightWing.addOrReplaceChild("rightEnd", CubeListBuilder.create().texOffs(33, 0).addBox(0.0F, 0.5F, -0.5F, 1.0F, 12.0F, 1.0F, new CubeDeformation(0.02F)), PartPose.offsetAndRotation(1.0F, -11.0F, 0.5F, 0.0F, 0.0F, 1.5708F));

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition right_arm = partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(25, 30).mirror().addBox(-1.0F, -2.0F, -1.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-5.0F, 2.0F, 0.0F));

		PartDefinition left_arm = partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(38, 0).addBox(-1.0F, -2.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(5.0F, 2.0F, 0.0F));
		PartDefinition leftItem = left_arm.addOrReplaceChild("leftItem", CubeListBuilder.create(), PartPose.offset(1.0F, 7.0F, 1.0F));
		PartDefinition leftFormArm = left_arm.addOrReplaceChild("leftFormArm", CubeListBuilder.create().texOffs(34, 30).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(-0.01F)), PartPose.offsetAndRotation(0.0F, 4.0F, 0.0F, -0.7854F, 0.0F, 0.0F));

		PartDefinition right_leg = partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create(), PartPose.offsetAndRotation(-2.0F, 0.0F, 0.0F, 0.0436F, 0.0F, 0.0F));
		PartDefinition rightLegtJoint = right_leg.addOrReplaceChild("rightLegtJoint", CubeListBuilder.create().texOffs(9, 34).mirror().addBox(-1.0F, 0.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 12.0F, 0.0F));
		PartDefinition rightTibia = rightLegtJoint.addOrReplaceChild("rightTibia", CubeListBuilder.create().texOffs(0, 34).mirror().addBox(-1.0F, 0.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 6.0F, 0.0F, 0.3927F, 0.0F, 0.0F));

		PartDefinition left_leg = partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create(), PartPose.offsetAndRotation(2.0F, 0.0F, 0.0F, 0.0436F, 0.0F, 0.0F));
		PartDefinition leftLegJoint = left_leg.addOrReplaceChild("leftLegJoint", CubeListBuilder.create().texOffs(9, 34).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 12.0F, 0.0F, 0.0873F, 0.0F, 0.0F));
		PartDefinition leftTibia = leftLegJoint.addOrReplaceChild("leftTibia", CubeListBuilder.create().texOffs(0, 34).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 6.0F, 0.0F, 0.3927F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
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
		ItemStack itemstack = entity.getMainHandItem();

		float armSpeed = 0.25F;
		float radians = 0.5235988F; // 30
		this.rightArm.xRot = Mth.cos(limbSwing * armSpeed) * radians * 1.4F * limbSwingAmount;
		this.leftArm.xRot = Mth.cos(limbSwing * armSpeed + (float) Math.PI) * radians * 1.4F * limbSwingAmount;

		// reset arm rotations before bobbing
		resetRotations(rightArm, rightArmRots);
		resetRotations(leftArm, leftArmRots);

		// bob entire body
		bobBody(ageInTicks);
		// flap/bob wings
		flapWings(ageInTicks);

		if ((itemstack.isEmpty() || !itemstack.is(Items.BOW))) {
			AnimationUtils.bobArms(this.rightArm, this.leftArm, ageInTicks);
			setupAttackAnimation(entity, ageInTicks);
		} else if (itemstack.is(Items.BOW) && entity.isAggressive()) {
			this.poseRightArm(entity);
		}
	}

	private void poseRightArm(T p_102876_) {
		switch (this.rightArmPose) {
			case BOW_AND_ARROW:
				this.rightArm.yRot = -0.1F + this.head.yRot;
				this.leftArm.yRot = 0.1F + this.head.yRot + 0.4F;
				this.rightArm.xRot = (-(float)Math.PI / 2F) + this.head.xRot;
				this.leftArm.xRot = (-(float)Math.PI / 2F) + this.head.xRot;
				break;
			default:
				this.rightArmPose.applyTransform(this, p_102876_, net.minecraft.world.entity.HumanoidArm.RIGHT);
		}
	}

	// TODO abstract out to DenizensSkeleton
	public void resetRotations(ModelPart part, Rotation rotations) {
		part.yRot = rotations.y();
		part.zRot = rotations.z();
	}

	public void bobBody(float age) {
		float yAdjustment = (Mth.cos(age * 0.15F) * 0.5F + 0.05F);
		body.y = bodyPos.y() + yAdjustment;
		head.y = headPos.y() + yAdjustment;
		rightArm.y = rightArmPos.y() + yAdjustment;
		leftArm.y = leftArmPos.y() + yAdjustment;
		rightLeg.y = rightLegPos.y() + yAdjustment;
		leftLeg.y = leftLegPos.y() + yAdjustment;
	}

	public void flapWings(float age) {
		float yAdjustment = (Mth.cos(age* 0.3F) * 0.5F + 0.05F);
		rightFlapAxis.yRot = rightFlapAxisRots.y() + yAdjustment;
		leftFlapAxis.yRot = leftFlapAxisRots.y() - yAdjustment;
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		// don't render the hat
		body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		head.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		rightArm.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		leftArm.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		rightLeg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		leftLeg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	public void translateToHand(HumanoidArm arm, PoseStack poseStack) {
		float f = arm == HumanoidArm.RIGHT ? 1.0F : -1.0F;
		ModelPart modelpart = this.getArm(arm);
		modelpart.x += f;
		modelpart.translateAndRotate(poseStack);
		modelpart.x -= f;
	}
}