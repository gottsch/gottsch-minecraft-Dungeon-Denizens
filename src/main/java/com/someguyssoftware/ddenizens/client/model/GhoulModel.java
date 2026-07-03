/*
 * This file is part of  Dungeon Denizens.
 * Copyright (c) 2022 Mark Gottschling (gottsch)
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
import net.minecraft.world.entity.Entity;

/**
 * 
 * @author Mark Gottschling on Apr 7, 2022
 *
 * @param <T>
 */
public class GhoulModel<T extends Entity> extends HumanlikeModel<T> {
	public static final String MODEL_NAME = "ghoul_model";
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(DD.MODID, MODEL_NAME), "main");

	private final ModelPart torso;
	private final ModelPart head;
	private final ModelPart body;
//	private final ModelPart lower_body;
	private final ModelPart leftArm;
	private final ModelPart rightArm;
	private final ModelPart leftLeg;
	private final ModelPart rightLeg;
	
	private float leftArmX;
	private float rightArmX;

	/**
	 * 
	 * @param root
	 */
	public GhoulModel(ModelPart root) {
		this.head = root.getChild("head");
		this.torso = root.getChild("torso");		
		this.body = torso.getChild("body");
//		this.lower_body = torso.getChild("lower_body");
		this.leftArm = root.getChild("left_arm");
		this.rightArm = root.getChild("right_arm");
		this.leftLeg = root.getChild("left_leg");
		this.rightLeg = root.getChild("right_leg");
		
		rightArmX = rightArm.x;
		leftArmX = leftArm.x;
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();
		
		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -4.0F, -3.0F, 6.0F, 8.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.0F, -6.0F, 0.3054F, 0.0F, 0.0F));
		PartDefinition torso = partdefinition.addOrReplaceChild("torso", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 7.0F, 0.0F, 0.3054F, 0.0F, 0.0F));
		PartDefinition lower_body = torso.addOrReplaceChild("lower_body", CubeListBuilder.create().texOffs(21, 24).addBox(-3.0F, -2.5F, -2.0F, 6.0F, 5.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-0.5F, -1.5F, 1.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-0.5F, 0.5F, 1.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 5.5F, 1.0F));
		PartDefinition body = torso.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 15).addBox(-4.0F, -4.0F, -2.0F, 8.0F, 8.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-0.5F, -3.0F, 1.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-0.5F, -1.0F, 1.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-0.5F, 1.0F, 1.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-0.5F, 3.0F, 1.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.3491F, 0.0F, 0.0F));
		PartDefinition left_arm = partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(13, 34).addBox(-1.5F, -1.75F, -1.5F, 3.0F, 6.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(26, 34).addBox(-1.0F, 3.25F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(-0.05F)), PartPose.offsetAndRotation(5.5F, 4.75F, -0.5F, -0.5672F, 0.0F, 0.0F));
		PartDefinition left_lower_arm = left_arm.addOrReplaceChild("left_lower_arm", CubeListBuilder.create().texOffs(0, 28).addBox(-1.5F, 1.25F, -1.0F, 3.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 4.25F, 0.0F, -0.8727F, 0.0F, 0.0F));
		PartDefinition left_hand = left_arm.addOrReplaceChild("left_hand", CubeListBuilder.create(), PartPose.offset(-5.5F, 22.25F, 0.5F));
		PartDefinition left_top_hand = left_hand.addOrReplaceChild("left_top_hand", CubeListBuilder.create().texOffs(35, 18).addBox(5.9F, -14.75F, -8.75F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(0, 3).addBox(5.9F, -14.0F, -8.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(35, 18).addBox(4.1F, -14.75F, -8.75F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(0, 3).addBox(4.1F, -14.0F, -8.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition left_thumb_r1 = left_top_hand.addOrReplaceChild("left_thumb_r1", CubeListBuilder.create().texOffs(35, 8).addBox(-0.5F, -0.5F, -2.5F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.8F, -12.35F, -5.25F, 0.3491F, 0.0F, 0.0F));

		PartDefinition right_arm = partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(13, 34).addBox(-1.5F, -1.75F, -1.5F, 3.0F, 6.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(26, 34).addBox(-1.0F, 3.25F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(-0.05F)), PartPose.offsetAndRotation(-5.5F, 4.75F, -0.5F, -0.7418F, 0.0F, 0.0F));
		PartDefinition right_lower_arm = right_arm.addOrReplaceChild("right_lower_arm", CubeListBuilder.create().texOffs(0, 28).addBox(-1.5F, 1.25F, -1.0F, 3.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 4.25F, 0.0F, -0.8727F, 0.0F, 0.0F));
		PartDefinition right_hand = right_arm.addOrReplaceChild("right_hand", CubeListBuilder.create(), PartPose.offset(-5.5F, 22.25F, 0.5F));
		PartDefinition right_top_hand = right_hand.addOrReplaceChild("right_top_hand", CubeListBuilder.create().texOffs(35, 18).addBox(5.9F, -14.75F, -8.75F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(0, 3).addBox(5.9F, -14.0F, -8.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(35, 18).addBox(4.1F, -14.75F, -8.75F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
		.texOffs(0, 3).addBox(4.1F, -14.0F, -8.75F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition right_thumb_r1 = right_top_hand.addOrReplaceChild("right_thumb_r1", CubeListBuilder.create().texOffs(35, 8).addBox(2.9F, -0.5F, -2.5F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.8F, -12.35F, -5.25F, 0.3491F, 0.0F, 0.0F));

		PartDefinition left_leg = partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(35, 34).addBox(-1.0F, 3.8285F, -5.0601F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.3F)), PartPose.offset(2.25F, 12.5215F, 2.8601F));
		PartDefinition left_lower_leg_r1 = left_leg.addOrReplaceChild("left_lower_leg_r1", CubeListBuilder.create().texOffs(25, 11).addBox(-1.75F, -5.0F, -4.5F, 3.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.25F, 8.4785F, 0.6399F, 0.48F, 0.0F, 0.0F));
		PartDefinition left_leg_r1 = left_leg.addOrReplaceChild("left_leg_r1", CubeListBuilder.create().texOffs(25, 0).addBox(-1.75F, 0.0F, -1.5F, 3.0F, 7.0F, 3.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(0.25F, -0.5215F, 0.6399F, -0.7854F, 0.0F, 0.0F));
		PartDefinition left_toes = left_leg.addOrReplaceChild("left_toes", CubeListBuilder.create().texOffs(13, 28).addBox(-1.35F, -0.5F, -2.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(13, 28).addBox(0.35F, -0.5F, -2.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 10.9785F, -2.6101F));

		PartDefinition right_leg = partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(35, 34).addBox(-1.5F, 3.8285F, -5.0601F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.3F)), PartPose.offset(-1.75F, 12.5215F, 2.8601F));
		PartDefinition right_lower_leg_r1 = right_leg.addOrReplaceChild("right_lower_leg_r1", CubeListBuilder.create().texOffs(25, 11).addBox(-2.25F, -5.0F, -4.5F, 3.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.25F, 8.4785F, 0.6399F, 0.48F, 0.0F, 0.0F));
		PartDefinition right_leg_r1 = right_leg.addOrReplaceChild("right_leg_r1", CubeListBuilder.create().texOffs(25, 0).addBox(-2.25F, 0.0F, -1.5F, 3.0F, 7.0F, 3.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(0.25F, -0.5215F, 0.6399F, -0.7854F, 0.0F, 0.0F));
		PartDefinition right_toes = right_leg.addOrReplaceChild("right_toes", CubeListBuilder.create().texOffs(13, 28).addBox(-1.85F, -0.5F, -2.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(13, 28).addBox(-0.15F, -0.5F, -2.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 10.9785F, -2.6101F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		// head
		this.head.yRot = netHeadYaw * ((float)Math.PI / 180F);
		this.head.xRot = headPitch * ((float)Math.PI / 180F) + 0.3054326F; // +17.5 degrees because has a slight down angle
		
		// legs
		float f = 1.0F;
		float radians = 0.6F;
		float walkSpeed = 0.5F; // half speed = 0.5
		this.rightLeg.xRot = Mth.cos(limbSwing * walkSpeed) * radians  * 1.4F * limbSwingAmount / f;
		this.leftLeg.xRot = Mth.cos(limbSwing  * walkSpeed + (float)Math.PI) * radians * 1.4F * limbSwingAmount / f;
		this.rightLeg.yRot = 0.0F;
		this.leftLeg.yRot = 0.0F;
		this.rightLeg.zRot = 0.0F;
		this.leftLeg.zRot = 0.0F;
		
		// arms
		// 0.5235988F = 30 degrees
		float armSpeed = 0.25F;
		radians = 0.5235988F; // 30
		this.rightArm.xRot = -0.7417649F + Mth.cos(limbSwing * armSpeed) * radians * 1.4F * limbSwingAmount;
		this.leftArm.xRot = -0.567232F + Mth.cos(limbSwing * armSpeed + (float)Math.PI) * radians * 1.4F * limbSwingAmount;

		setupAttackAnimation(entity, ageInTicks);
		
		// reset arm rotations before bobbing, because bobbing is an addition to current rotation
		this.leftArm.zRot = -0.3926991F;
		this.rightArm.zRot = 0.3926991F;
		
		// bob the arms
		bobModelPart(this.rightArm, ageInTicks, 1.0F);
		bobModelPart(this.leftArm, ageInTicks, -1.0F);
	}

	/**
	 * 
	 * @param part
	 * @param age
	 * @param direction
	 */
	public static void bobModelPart(ModelPart part, float age, float direction) {
		part.zRot += direction * (Mth.cos(age * /*0.09F*/ 0.15F) * 0.05F + 0.05F);
	}
	
	@Override
	public void resetSwing(T entity, ModelPart body, ModelPart rightArm, ModelPart leftArm) {
		body.yRot = 0;
		rightArm.x = rightArmX;
		rightArm.xRot = -0.7417649F;
		rightArm.zRot = 0;
		rightArm.yRot = 0;
		leftArm.x = leftArmX;
		leftArm.xRot = -0.567232F;
		leftArm.yRot = 0;
		leftArm.zRot = 0;
	}
	
	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		head.render(poseStack, buffer, packedLight, packedOverlay);
		torso.render(poseStack, buffer, packedLight, packedOverlay);
		leftArm.render(poseStack, buffer, packedLight, packedOverlay);
		rightArm.render(poseStack, buffer, packedLight, packedOverlay);
		leftLeg.render(poseStack, buffer, packedLight, packedOverlay);
		rightLeg.render(poseStack, buffer, packedLight, packedOverlay);
	}

	@Override
	public ModelPart getBody() {
		return body;
	}

	@Override
	public ModelPart getRightArm() {
		return rightArm;
	}

	@Override
	public ModelPart getLeftArm() {
		return leftArm;
	}

	@Override
	public ModelPart getHead() {
		return this.head;
	}

	@Override
	public ModelPart getRightLeg() {
		return this.rightLeg;
	}

	@Override
	public ModelPart getLeftLeg() {
		return this.leftLeg;
	}
}