/*
 * This file is part of  Dungeon Denizens.
 * Copyright (c) 2021, Mark Gottschling (gottsch)
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

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.HumanoidArm;

/**
 * 
 * @author Mark Gottschling on Apr 28, 2022
 *
 * @param <T>
 */
public class OrcModel<T extends Entity> extends EntityModel<T> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(DD.MODID, "orc"), "main");
	private final ModelPart head;
	private final ModelPart body;
	private final ModelPart leftArm;
	private final ModelPart rightArm;
	private final ModelPart leftLeg;
	private final ModelPart rightLeg;
	private final ModelPart mouth;

	private float leftArmX;
	private float rightArmX;
	private float leftArmY;
	private float rightArmY;
	
	/**
	 * 
	 * @param root
	 */
	public OrcModel(ModelPart root) {
		this.head = root.getChild("head");
		this.body = root.getChild("body");
		this.rightArm = body.getChild("right_arm");
		this.leftArm = body.getChild("left_arm");
		this.leftLeg = root.getChild("left_leg");
		this.rightLeg = root.getChild("right_leg");
		mouth = head.getChild("jaw");
		
		rightArmX = rightArm.x;
		leftArmX = leftArm.x;
		rightArmY = rightArm.y;
		leftArmY = leftArm.y;		
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		
		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(27, 22).addBox(-4.0F, -9.0F, -4.0F, 8.0F, 9.0F, 8.0F, new CubeDeformation(0.0F))
				.texOffs(0, 30).addBox(-1.5F, -10.5F, -3.0F, 3.0F, 6.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.0F, -4.0F));
				PartDefinition ear_r1 = head.addOrReplaceChild("ear_r1", CubeListBuilder.create().texOffs(0, 30).addBox(0.0F, -2.0F, 0.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, -5.0F, 0.0F, 0.4363F, -0.2618F, -0.2618F));
				PartDefinition ear_r2 = head.addOrReplaceChild("ear_r2", CubeListBuilder.create().texOffs(15, 30).addBox(-1.0F, -2.0F, 0.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, -5.0F, 0.0F, 0.4363F, 0.2618F, 0.2618F));
				PartDefinition jaw = head.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(35, 12).addBox(-4.0F, -1.5F, -4.0F, 8.0F, 3.0F, 4.0F, new CubeDeformation(0.4F)), PartPose.offsetAndRotation(0.0F, -1.5F, -0.5F, 0.1309F, 0.0F, 0.0F));
				PartDefinition teeth4_r1 = jaw.addOrReplaceChild("teeth4_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-0.2F, -1.5F, 3.5F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(8.2F, -1.5F, 3.5F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, -4.5F, -6.5F, -0.7854F, 0.0F, 0.0F));
				PartDefinition teeth2_r1 = jaw.addOrReplaceChild("teeth2_r1", CubeListBuilder.create().texOffs(0, 15).addBox(6.0F, -4.0F, 2.2F, 2.0F, 2.0F, 0.0F, new CubeDeformation(0.0F))
				.texOffs(0, 15).addBox(2.0F, 0.0F, 2.2F, 2.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, -4.5F, -6.5F, 0.0F, 0.0F, 0.7854F));
				PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));
				PartDefinition torso = body.addOrReplaceChild("torso", CubeListBuilder.create().texOffs(0, 0).addBox(-10.0F, -27.0F, -3.0F, 20.0F, 5.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(0, 12).addBox(-5.5F, -22.0F, -3.0F, 11.0F, 11.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
				PartDefinition left_arm = body.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(38, 57).addBox(-1.5F, -1.0F, -2.0F, 4.0F, 7.0F, 4.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(7.0F, -22.0F, 0.0F, 0.2182F, 0.0F, -0.0873F));
				PartDefinition left_lower_arm = left_arm.addOrReplaceChild("left_lower_arm", CubeListBuilder.create().texOffs(56, 53).addBox(-1.5F, 0.0F, -2.1F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 5.0F, 0.0F, -0.4363F, 0.0F, 0.0F));
				PartDefinition bracer = left_lower_arm.addOrReplaceChild("bracer", CubeListBuilder.create().texOffs(60, 44).addBox(4.5F, -15.0F, -2.0F, 4.0F, 3.0F, 4.0F, new CubeDeformation(0.2F))
				.texOffs(0, 12).addBox(8.0F, -14.0F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(0, 12).addBox(6.0F, -14.0F, -2.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(0, 12).addBox(6.0F, -14.0F, 1.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-6.0F, 18.0F, 0.0F));
				PartDefinition right_arm = body.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(21, 57).addBox(-2.5F, -1.0F, -2.0F, 4.0F, 7.0F, 4.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(-7.0F, -22.0F, 0.0F, 0.2182F, 0.0F, 0.0873F));
				PartDefinition right_lower_arm2 = right_arm.addOrReplaceChild("right_lower_arm2", CubeListBuilder.create().texOffs(56, 16).addBox(-2.5F, 0.0F, -2.1F, 4.0F, 8.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 5.0F, 0.0F, -0.4363F, 0.0F, 0.0F));
				PartDefinition right_bracer = right_lower_arm2.addOrReplaceChild("right_bracer", CubeListBuilder.create().texOffs(56, 36).addBox(3.5F, -15.0F, -2.0F, 4.0F, 3.0F, 4.0F, new CubeDeformation(0.2F))
				.texOffs(0, 12).addBox(3.0F, -14.0F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(0, 12).addBox(5.0F, -14.0F, -2.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(0, 12).addBox(5.0F, -14.0F, 1.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-6.0F, 18.0F, 0.0F));
				PartDefinition left_leg = partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(39, 40).addBox(-2.0F, 1.0F, -2.5F, 5.0F, 11.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(2.5F, 12.0F, 0.0F));
				PartDefinition left_boot = left_leg.addOrReplaceChild("left_boot", CubeListBuilder.create().texOffs(53, 0).addBox(0.5F, -5.0F, -2.5F, 5.0F, 5.0F, 5.0F, new CubeDeformation(0.2F)), PartPose.offset(-2.5F, 12.0F, 0.0F));
				PartDefinition right_leg = partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(18, 40).addBox(-3.0F, 1.0F, -2.5F, 5.0F, 11.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.5F, 12.0F, 0.0F));
				PartDefinition right_boot = right_leg.addOrReplaceChild("right_boot", CubeListBuilder.create().texOffs(0, 52).addBox(-0.5F, -5.0F, -2.5F, 5.0F, 5.0F, 5.0F, new CubeDeformation(0.2F)), PartPose.offset(-2.5F, 12.0F, 0.0F));
		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		// head
		this.head.yRot = netHeadYaw * ((float)Math.PI / 180F);	
		this.head.xRot = headPitch * ((float)Math.PI / 180F);
		
		// body (rotates on y-axis with head to max of 20 degrees)
		if (netHeadYaw < 0) {
			this.body.yRot = Math.max(-20, netHeadYaw) * ((float)Math.PI / 180F);
		}
		else {
			this.body.yRot = Math.min(20, netHeadYaw) * ((float)Math.PI / 180F);
		}
		
		// legs
		float f = 1.0F;
		float radians = 0.7F;
		float walkSpeed = 0.75F; // half speed = 0.5
		this.rightLeg.xRot = Mth.cos(limbSwing * walkSpeed) * radians  * 1.4F * limbSwingAmount / f;
		this.leftLeg.xRot = Mth.cos(limbSwing  * walkSpeed + (float)Math.PI) * radians * 1.4F * limbSwingAmount / f;
		this.rightLeg.yRot = 0.0F;
		this.leftLeg.yRot = 0.0F;
		this.rightLeg.zRot = 0.0F;
		this.leftLeg.zRot = 0.0F;
		
		// arms
		float armSpeed = 0.35F;
		radians = 0.8726646F; //50 //0.6981317F; // 40 //0.5235988F; // 30
		this.rightArm.xRot = 0.2181662F + Mth.cos(limbSwing * armSpeed) * radians * 1.4F * limbSwingAmount;
		this.leftArm.xRot = 0.2181662F + Mth.cos(limbSwing * armSpeed + (float)Math.PI) * radians * 1.4F * limbSwingAmount;
//	      this.rightArm.xRot = Mth.cos(limbSwing * 0.6662F + (float)Math.PI) * 2.0F * limbSwingAmount * 0.5F / f;
//	      this.leftArm.xRot = Mth.cos(limbSwing * 0.6662F) * 2.0F * limbSwingAmount * 0.5F / f;
		// reset
		rightArm.x = rightArmX;
		rightArm.zRot = 0.08726646F;
		rightArm.yRot = 0;
		leftArm.x = leftArmX;
		leftArm.zRot = -0.08726646F;
		leftArm.yRot = 0;
		
		setupAttackAnimation(entity, ageInTicks);
		
		// bob the arms
		bobArmPart(this.rightArm, ageInTicks, 1.0F);
		bobArmPart(this.leftArm, ageInTicks, -1.0F);
		
		rightArm.y = rightArmY + (Mth.cos(ageInTicks * 0.1F) * 0.5F + 0.05F);
		leftArm.y = leftArmY + (Mth.cos(ageInTicks * 0.1F) * 0.5F + 0.05F);
		
		// bob mouth
		bobMouthPart(mouth, ageInTicks);
	}

	/**
	 * 
	 * @param part
	 * @param age
	 * @param direction
	 */
	public static void bobArmPart(ModelPart part, float age, float direction) {
		part.zRot = direction * (0.08726646F + (Mth.cos(age * 0.15F) * 0.05F + 0.05F));
	}
	
	public static void bobMouthPart(ModelPart mouth, float age) {
		mouth.xRot = Math.max(0, -0.08726646F + Mth.cos(age * 0.05F) * 0.2181662F + 0.05F);
	}
	
	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		head.render(poseStack, buffer, packedLight, packedOverlay);
		body.render(poseStack, buffer, packedLight, packedOverlay);
		leftLeg.render(poseStack, buffer, packedLight, packedOverlay);
		rightLeg.render(poseStack, buffer, packedLight, packedOverlay);
	}
	
	   protected void setupAttackAnimation(T p_102858_, float p_102859_) {
		      if (!(this.attackTime <= 0.0F)) {
		         ModelPart attackArm = this.rightArm;
		         float f = this.attackTime;
		         this.body.yRot = Mth.sin(Mth.sqrt(f) * ((float)Math.PI * 2F)) * 0.2F;

		         // TODO something wrong in here the arms are resetting too close to the body after a swing
//		         this.rightArm.z = Mth.sin(this.body.yRot) * 5.0F;
//		         this.rightArm.x = -Mth.cos(this.body.yRot) * 5.0F;
//		         this.leftArm.z = -Mth.sin(this.body.yRot) * 5.0F;
//		         this.leftArm.x = Mth.cos(this.body.yRot) * 5.0F;
//		         this.rightArm.yRot += this.body.yRot;
//		         this.leftArm.yRot += this.body.yRot;
//		         this.leftArm.xRot += this.body.yRot;
		         f = 1.0F - this.attackTime;
		         f *= f;
		         f *= f;
		         f = 1.0F - f;
		         float f1 = Mth.sin(f * (float)Math.PI);
		         float f2 = Mth.sin(this.attackTime * (float)Math.PI) * -(this.head.xRot - 0.7F) * 0.75F;
		         attackArm.xRot = (float)((double)attackArm.xRot - ((double)f1 * 1.2D + (double)f2));
		         attackArm.yRot += this.body.yRot * 2.0F;
		         attackArm.zRot += Mth.sin(this.attackTime * (float)Math.PI) * -0.4F;
		      }
		   }
}