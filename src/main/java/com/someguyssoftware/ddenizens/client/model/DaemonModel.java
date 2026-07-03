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

public class DaemonModel<T extends Entity> extends HumanlikeModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(DD.MODID, "daemon"), "main");
	private final ModelPart daemon;
	private final ModelPart head;
	private final ModelPart mouth;
	private final ModelPart body;
	private final ModelPart upperBody;
	private final ModelPart rightArm;
	private final ModelPart leftArm;	
	private final ModelPart leftHand;
	private final ModelPart rightHand;
	private final ModelPart rightLeg;
	private final ModelPart leftLeg;
	
	private final ModelPart tail;
	private final ModelPart lowerTail;
	
	private float upperBodyY;	
	private float leftArmX;
	private float rightArmX;
	
	/**
	 * 
	 * @param root
	 */
	public DaemonModel(ModelPart root) {
		this.daemon = root.getChild("daemon");
		this.head = daemon.getChild("head");
		this.mouth = head.getChild("mouth");
		this.body = daemon.getChild("body");
		this.rightLeg = daemon.getChild("right_leg");
		this.leftLeg = daemon.getChild("left_leg");
		this.tail = daemon.getChild("tail");
		
		this.upperBody = body.getChild("upper_body");
		this.rightArm = upperBody.getChild("shoulders").getChild("right_arm");
		this.leftArm = upperBody.getChild("shoulders").getChild("left_arm");
		this.rightHand = rightArm.getChild("right_lower_arm").getChild("right_hand");
		this.leftHand = leftArm.getChild("left_lower_arm").getChild("left_hand");
		
		this.lowerTail = tail.getChild("lower_tail");
		
		upperBodyY = upperBody.y;
		rightArmX = rightArm.x;
		leftArmX = leftArm.x;
	}

	/**
	 * 
	 * @return
	 */
	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition daemon = partdefinition.addOrReplaceChild("daemon", CubeListBuilder.create(), PartPose.offset(0.0F, 12.0F, 2.0F));

		PartDefinition head = daemon.addOrReplaceChild("head", CubeListBuilder.create().texOffs(25, 9).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -26.0F, -7.0F, 0.0F, 0.0F, 0.2182F));

		PartDefinition mouth = head.addOrReplaceChild("mouth", CubeListBuilder.create(), PartPose.offset(0.0F, 0.5F, -1.4429F));

		PartDefinition bottom = mouth.addOrReplaceChild("bottom", CubeListBuilder.create().texOffs(21, 0).addBox(-4.0F, -1.5F, -3.5571F, 8.0F, 3.0F, 4.0F, new CubeDeformation(0.4F)), PartPose.offsetAndRotation(0.0F, -2.0F, 1.0F, 0.3054F, 0.0F, 0.0F));

		PartDefinition teeth1 = bottom.addOrReplaceChild("teeth1", CubeListBuilder.create(), PartPose.offset(1.5F, -1.5F, -3.7571F));

		PartDefinition t1_r1 = teeth1.addOrReplaceChild("t1_r1", CubeListBuilder.create().texOffs(21, 8).addBox(-1.0F, -1.5F, 0.0F, 2.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.7854F));

		PartDefinition teeth2 = bottom.addOrReplaceChild("teeth2", CubeListBuilder.create(), PartPose.offset(-2.25F, -1.5F, -3.7571F));

		PartDefinition t2_r1 = teeth2.addOrReplaceChild("t2_r1", CubeListBuilder.create().texOffs(21, 8).addBox(-1.0F, -1.5F, 0.0F, 2.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.7854F));

		PartDefinition teeth3 = bottom.addOrReplaceChild("teeth3", CubeListBuilder.create().texOffs(50, 9).addBox(-1.0F, -3.0F, 0.4429F, 2.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, -1.75F, -4.0F, 0.0F, 0.0F, -0.3927F));

		PartDefinition teeth4 = bottom.addOrReplaceChild("teeth4", CubeListBuilder.create().texOffs(46, 0).addBox(-1.0F, -3.0F, 0.4429F, 2.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, -1.75F, -4.0F, 0.0F, 0.0F, 0.3927F));

		PartDefinition left_horn = head.addOrReplaceChild("left_horn", CubeListBuilder.create().texOffs(17, 40).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 8.0F, new CubeDeformation(0.05F)), PartPose.offsetAndRotation(3.0F, -7.0F, 0.0F, -0.0873F, -1.6581F, -3.1416F));

		PartDefinition top_r1 = left_horn.addOrReplaceChild("top_r1", CubeListBuilder.create().texOffs(0, 17).addBox(-1.0F, 1.5F, 5.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.2618F, 0.0F, 0.5236F));

		PartDefinition right_horn = head.addOrReplaceChild("right_horn", CubeListBuilder.create().texOffs(17, 40).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 8.0F, new CubeDeformation(0.05F)), PartPose.offsetAndRotation(-3.0F, -7.0F, 0.0F, 3.1416F, 1.3963F, 0.0F));

		PartDefinition top_r2 = right_horn.addOrReplaceChild("top_r2", CubeListBuilder.create().texOffs(0, 17).addBox(-1.0F, 1.5F, 5.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.2618F, 0.0F, -0.48F));

		PartDefinition body = daemon.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -16.0F, 0.0F, 0.3054F, 0.0F, 0.0F));

		PartDefinition upper_body = body.addOrReplaceChild("upper_body", CubeListBuilder.create().texOffs(0, 34).addBox(-4.0F, -21.0F, -2.0F, 8.0F, 9.0F, 4.0F, new CubeDeformation(0.2F)), PartPose.offset(0.0F, 16.0F, 0.0F));

		PartDefinition right_flap_r1 = upper_body.addOrReplaceChild("right_flap_r1", CubeListBuilder.create().texOffs(58, 22).addBox(0.0F, -2.5F, -2.0F, 2.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.0F, -18.5F, 0.0F, 0.0F, 0.0F, -0.4363F));

		PartDefinition left_flap_r1 = upper_body.addOrReplaceChild("left_flap_r1", CubeListBuilder.create().texOffs(59, 40).addBox(-2.0F, -2.5F, -2.0F, 2.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.0F, -18.5F, 0.0F, 0.0F, 0.0F, 0.4363F));

		PartDefinition shoulders = upper_body.addOrReplaceChild("shoulders", CubeListBuilder.create().texOffs(48, 50).addBox(-2.0F, -6.5F, -2.0F, 4.0F, 5.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(27, 28).addBox(-6.0F, -2.5F, -2.5F, 12.0F, 5.0F, 6.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(0.0F, -22.5F, -1.0F, 0.1745F, 0.0F, 0.0F));

		PartDefinition left_arm = shoulders.addOrReplaceChild("left_arm", CubeListBuilder.create(), PartPose.offsetAndRotation(6.0F, -1.5F, 0.0F, 0.0F, 0.0F, -0.9599F));

		PartDefinition left_upper_arm = left_arm.addOrReplaceChild("left_upper_arm", CubeListBuilder.create().texOffs(58, 9).addBox(6.0F, -24.0F, -1.0F, 2.0F, 9.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-6.0F, 24.0F, 0.0F));

		PartDefinition left_lower_arm = left_arm.addOrReplaceChild("left_lower_arm", CubeListBuilder.create().texOffs(0, 63).addBox(-0.5F, -4.0F, -0.5F, 1.0F, 12.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 9.0F, 0.0F, -1.2654F, 0.0F, 0.0F));

		PartDefinition left_hand = left_lower_arm.addOrReplaceChild("left_hand", CubeListBuilder.create().texOffs(62, 57).addBox(-1.375F, -0.4848F, -1.25F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(7, 63).addBox(-2.375F, -0.4848F, -1.25F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.375F, 8.4848F, 0.25F));

		PartDefinition finger2_r1 = left_hand.addOrReplaceChild("finger2_r1", CubeListBuilder.create().texOffs(30, 40).addBox(0.0F, 0.0F, -0.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.625F, -0.4848F, 1.25F, 0.1745F, 0.0F, 0.0F));

		PartDefinition finger1_r1 = left_hand.addOrReplaceChild("finger1_r1", CubeListBuilder.create().texOffs(30, 40).addBox(0.0F, 0.0F, -0.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.625F, -0.4848F, -0.75F, -0.1745F, 0.0F, 0.0F));

		PartDefinition right_arm = shoulders.addOrReplaceChild("right_arm", CubeListBuilder.create(), PartPose.offsetAndRotation(-6.0F, -1.5F, 0.0F, 0.0F, 0.0F, 0.9599F));

		PartDefinition right_upper_arm = right_arm.addOrReplaceChild("right_upper_arm", CubeListBuilder.create().texOffs(15, 51).addBox(-8.0F, -24.0F, -1.0F, 2.0F, 9.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(6.0F, 24.0F, 0.0F));

		PartDefinition right_lower_arm = right_arm.addOrReplaceChild("right_lower_arm", CubeListBuilder.create().texOffs(48, 60).addBox(-0.5F, -4.0F, -0.5F, 1.0F, 12.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 9.0F, 0.0F, -1.2654F, 0.0F, 0.0F));

		PartDefinition right_hand = right_lower_arm.addOrReplaceChild("right_hand", CubeListBuilder.create().texOffs(60, 0).addBox(-0.625F, -1.4886F, -1.25F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(7, 63).addBox(1.375F, -1.4886F, -1.25F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.375F, 9.4886F, 0.25F));

		PartDefinition finger3_r1 = right_hand.addOrReplaceChild("finger3_r1", CubeListBuilder.create().texOffs(30, 40).addBox(-1.0F, -0.5F, -0.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.625F, -0.9886F, 1.25F, 0.1745F, 0.0F, 0.0F));

		PartDefinition finger2_r2 = right_hand.addOrReplaceChild("finger2_r2", CubeListBuilder.create().texOffs(30, 40).addBox(-1.0F, -0.5F, -0.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.625F, -0.9886F, -0.75F, -0.1745F, 0.0F, 0.0F));

		PartDefinition lower_body = body.addOrReplaceChild("lower_body", CubeListBuilder.create().texOffs(38, 40).addBox(-3.0F, -1.0F, -2.0F, 6.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 4.0F, 0.0F, -0.1745F, 0.0F, 0.0F));

		PartDefinition left_leg = daemon.addOrReplaceChild("left_leg", CubeListBuilder.create(), PartPose.offsetAndRotation(4.0F, -8.0F, 1.0F, 0.0F, -0.2182F, 0.0F));

		PartDefinition left_upper_leg = left_leg.addOrReplaceChild("left_upper_leg", CubeListBuilder.create().texOffs(0, 17).addBox(-2.0F, -2.0F, -11.0F, 4.0F, 4.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 1.0F, 0.3491F, 0.0F, 0.0F));

		PartDefinition left_lower_leg = left_leg.addOrReplaceChild("left_lower_leg", CubeListBuilder.create().texOffs(35, 50).addBox(-1.0F, -2.0F, -1.3F, 3.0F, 10.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, 6.0F, -6.5F, 0.6109F, 0.0F, 0.0F));

		PartDefinition left_calf = left_leg.addOrReplaceChild("left_calf", CubeListBuilder.create().texOffs(0, 48).addBox(-1.5F, -5.5F, -1.5F, 4.0F, 11.0F, 3.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(-0.5F, 14.5F, -3.0F, -0.3927F, 0.0F, 0.0F));

		PartDefinition foot = left_leg.addOrReplaceChild("foot", CubeListBuilder.create().texOffs(46, 0).addBox(-2.0F, -2.5554F, -4.8335F, 4.0F, 3.0F, 5.0F, new CubeDeformation(0.1F)), PartPose.offset(0.0F, 19.5554F, -4.1665F));

		PartDefinition toe2_r1 = foot.addOrReplaceChild("toe2_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -1.0F, -3.5F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.5F, -1.0554F, -4.3335F, 0.0F, 0.1745F, 0.0F));

		PartDefinition toe1_r1 = foot.addOrReplaceChild("toe1_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -1.0F, -3.5F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.5F, -1.0554F, -4.3335F, 0.0F, -0.1745F, 0.0F));

		PartDefinition right_leg = daemon.addOrReplaceChild("right_leg", CubeListBuilder.create(), PartPose.offsetAndRotation(-4.0F, -8.0F, 1.0F, 0.0F, 0.2182F, 0.0F));

		PartDefinition left_upper_leg2 = right_leg.addOrReplaceChild("left_upper_leg2", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -2.0F, -11.0F, 4.0F, 4.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 1.0F, 0.3491F, 0.0F, 0.0F));

		PartDefinition left_lower_leg2 = right_leg.addOrReplaceChild("left_lower_leg2", CubeListBuilder.create().texOffs(35, 50).addBox(-1.0F, -2.0F, -1.3F, 3.0F, 10.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, 6.0F, -6.5F, 0.6109F, 0.0F, 0.0F));

		PartDefinition left_calf2 = right_leg.addOrReplaceChild("left_calf2", CubeListBuilder.create().texOffs(0, 48).addBox(-1.5F, -5.5F, -1.5F, 4.0F, 11.0F, 3.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(-0.5F, 14.5F, -3.0F, -0.3927F, 0.0F, 0.0F));

		PartDefinition foot2 = right_leg.addOrReplaceChild("foot2", CubeListBuilder.create().texOffs(46, 0).addBox(-2.0F, -2.5554F, -4.8335F, 4.0F, 3.0F, 5.0F, new CubeDeformation(0.1F)), PartPose.offset(0.0F, 19.5554F, -4.1665F));

		PartDefinition toe3_r1 = foot2.addOrReplaceChild("toe3_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -1.0F, -3.5F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.5F, -1.0554F, -4.3335F, 0.0F, 0.1745F, 0.0F));

		PartDefinition toe2_r2 = foot2.addOrReplaceChild("toe2_r2", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -1.0F, -3.5F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.5F, -1.0554F, -4.3335F, 0.0F, -0.1745F, 0.0F));

		PartDefinition tail = daemon.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offsetAndRotation(-0.5F, -9.0F, 4.0F, 0.3927F, 0.0F, 0.0F));

		PartDefinition upper_tail = tail.addOrReplaceChild("upper_tail", CubeListBuilder.create().texOffs(26, 51).addBox(-2.0F, -10.0F, 3.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(1.5F, 8.5412F, -4.3066F));

		PartDefinition lower_tail = tail.addOrReplaceChild("lower_tail", CubeListBuilder.create().texOffs(55, 60).addBox(-1.0F, 0.0F, -1.0F, 2.0F, 10.0F, 2.0F, new CubeDeformation(0.0F))
				.texOffs(0, 6).addBox(-2.0F, 8.0F, -0.5F, 4.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, 10.5412F, -0.3066F, 0.2182F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		// head
		this.head.yRot = netHeadYaw * ((float)Math.PI / 180F);
		this.head.xRot = headPitch * ((float)Math.PI / 180F) + 0.2181662F; // +12.5 degrees because has a slight down angle
		
		// arms
		// 0.5235988F = 30 degrees
		float armSpeed = 0.25F;
		float radians = 0.5235988F; // 30
		this.rightArm.xRot = Mth.cos(limbSwing * armSpeed) * radians * 1.4F * limbSwingAmount;
		this.leftArm.xRot = Mth.cos(limbSwing * armSpeed + (float)Math.PI) * radians * 1.4F * limbSwingAmount;
		
		radians = 0.6F;
		float walkSpeed = 0.5F; // half speed = 0.5
		this.rightLeg.xRot = Mth.cos(limbSwing * walkSpeed) * radians  * 1.4F * limbSwingAmount;
		this.leftLeg.xRot = Mth.cos(limbSwing  * walkSpeed + (float)Math.PI) * radians * 1.4F * limbSwingAmount;
		
		setupAttackAnimation(entity, ageInTicks);
		
		/*
		 *  bobs
		 */
		// reset arm rotations before bobbing, because bobbing is an addition to current rotation
		this.leftArm.zRot = -0.9599311F; // -55 degrees
		this.rightArm.zRot = 0.9599311F;
		
		// body
		upperBody.y = upperBodyY + (Mth.cos(ageInTicks * 0.15F) * 0.5F + 0.05F);
		
		// arms
		bobArm(this.rightArm, ageInTicks, 1.0F);
		bobArm(this.leftArm, ageInTicks, -1.0F);
		
		bobClaw(this.rightHand, ageInTicks, 1.0F);
		bobClaw(this.leftHand, ageInTicks, 1.0F);
		
		// tail
		float speed = 0.05F; // 1/20th speed
		radians = 0.5235988F;
		this.tail.zRot = Mth.cos(ageInTicks * 0.15F) * radians;
		this.tail.xRot = 0.3926991F + Mth.cos(ageInTicks * 0.1F) * 0.12F;
		this.lowerTail.zRot = Mth.cos(ageInTicks * speed) * radians;

		// bob mouth
		bobMouth(mouth, ageInTicks);

	}

	/**
	 * 
	 * @param part
	 * @param age
	 * @param direction
	 */
	public static void bobArm(ModelPart part, float age, float direction) {
		part.zRot += direction * (Mth.cos(age * 0.15F) * 0.05F + 0.05F);
	}
	
	public static void bobClaw(ModelPart part, float age, float direction) {
		part.zRot = direction * (Mth.cos(age * 0.08F) * 0.05F + 0.05F);
	}

	public static void bobMouth(ModelPart mouth, float age) {
		mouth.xRot = Math.max(0.08726646F, 0.08726646F + Mth.cos(age * 0.07F) * -0.2617994F);
	}

	@Override
	public void resetSwing(T entity, ModelPart body, ModelPart rightArm, ModelPart leftArm) {
		body.yRot = 0;
		rightArm.x = rightArmX;
		rightArm.zRot = 0.9599311F;
		rightArm.yRot = 0;
		leftArm.x = leftArmX;
		leftArm.yRot = 0;
		leftArm.zRot = -rightArm.zRot;
	}
	
	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		daemon.render(poseStack, buffer, packedLight, packedOverlay);
	}
	
	public ModelPart getHead() {
		return head;
	}
	
	public ModelPart getBody() {
		return body;
	}
	
	public ModelPart getRightArm() {
		return rightArm;
	}
	
	public ModelPart getLeftArm() {
		return leftArm;
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