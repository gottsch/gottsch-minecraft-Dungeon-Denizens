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
 * @author Mark Gottschling on Apr 6, 2022
 *
 * @param <T>
 */
public class HeadlessModel<T extends Entity> extends HumanlikeModel<T> {
	public static final String MODEL_NAME = "headless_model";
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(DD.MODID, MODEL_NAME), "main");

	private final ModelPart leftLeg;
	private final ModelPart rightLeg;
	private final ModelPart body;
	private final ModelPart leftArm;
	private final ModelPart rightArm;
	private final ModelPart loinCloth;
	private final ModelPart frontCloth;
	private final ModelPart backCloth;

	private final ModelPart rightLowerArm;
	private final ModelPart leftLowerArm;

	/*
	 * created solely to fulfill the contract of IHumanlikeModel
	 */
	private final ModelPart head;
	
	private float leftArmX;
	private float rightArmX;
	
	private ModelPart attackArm;
	private boolean changeAttackArm;
	private long changeAttackArmTime;
	
	/**
	 * 
	 * @param root
	 */
	public HeadlessModel(ModelPart root) {
		this.leftLeg = root.getChild("left_leg");
		this.rightLeg = root.getChild("right_leg");
		this.body = root.getChild("body");
		this.leftArm = root.getChild("left_arm");
		this.rightArm = root.getChild("right_arm");
		this.loinCloth = root.getChild("loin_cloth");
		this.frontCloth = loinCloth.getChild("front_cloth");
		this.backCloth = loinCloth.getChild("back_cloth");
		this.leftLowerArm = leftArm.getChild("left_lower_arm");
		this.rightLowerArm = rightArm.getChild("right_lower_arm");
		
		head = root.getChild("head");
		head.visible = false;
		
		rightArmX = rightArm.x;
		leftArmX = leftArm.x;
		
		attackArm = getLeftArm();
		changeAttackArm = false;
		changeAttackArmTime = 0;
	}

	/**
	 * 
	 * @return
	 */
	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition left_leg = partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(0, 39).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 12.0F, 0.0F));
		PartDefinition right_leg = partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(34, 9).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, 12.0F, 0.0F));
		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-6.0F, -24.0F, -3.0F, 12.0F, 6.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(29, 31).addBox(-4.0F, -18.0F, -2.0F, 8.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));
		PartDefinition left_arm = partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(34, 42).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.0F, 3.0F, 0.0F, 0.0F, 0.0F, -0.8727F));
		PartDefinition left_lower_arm = left_arm.addOrReplaceChild("left_lower_arm", CubeListBuilder.create().texOffs(17, 18).addBox(-2.0F, -2.0F, -6.0F, 4.0F, 4.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 6.0F, 0.0F));
		PartDefinition right_arm = partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(17, 42).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.0F, 3.0F, 0.0F, 0.0F, 0.0F, 0.8727F));
		PartDefinition right_lower_arm = right_arm.addOrReplaceChild("right_lower_arm", CubeListBuilder.create().texOffs(0, 13).addBox(-2.0F, -2.0F, -6.0F, 4.0F, 4.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 6.0F, 0.0F));
		PartDefinition loin_cloth = partdefinition.addOrReplaceChild("loin_cloth", CubeListBuilder.create().texOffs(0, 31).addBox(-4.5F, -14.0F, -2.5F, 9.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));
		PartDefinition front_cloth = loin_cloth.addOrReplaceChild("front_cloth", CubeListBuilder.create().texOffs(44, 0).addBox(-2.5F, 0.0F, -2.5F, 5.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -12.0F, 0.0F));
		PartDefinition back_cloth = loin_cloth.addOrReplaceChild("back_cloth", CubeListBuilder.create().texOffs(31, 0).addBox(-2.5F, 0.0F, 1.5F, 5.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -12.0F, 0.0F));

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().addBox(0F, 0F, 0F, 0F, 0F, 0F, new CubeDeformation(0.0F)), PartPose.offset(0F, 0F, 0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	/* Notes
	 * 0.6662 radians = ~38.17 degrees.
	 * where are these magic degrees coming from?
	 * limbSwing = movement along the x-axis of a cosine wave. ie. time (not to be confused with ageInTicks). 
	 * 	or put another way, increases the overall arc; remember sin/cos are cyclic, so cos(0) = cos(360) = cos(720), etc.
	 * 	provided by vanilla.
	 * limbSwingAmount = determines how much of the arc to use (value = 0.0 - 1.0). provided by vanilla
	 * 1.4 = some multiplier that vanilla figured out to use.
	 * general function: cos(x * r * s) * (ease).
	 * x = position on cos wave/time (limbSwing).
	 * r = max arc in radians.
	 * s = speed. 1 = no change, 0.5 = half speed, 2 = double speed. (essentially moving further down the cos wave per n ticks).
	 * ease (1.4 * limbSwingAmount) = the easement at max swing and stand still. don't really need to touch this part.
	 * 1 radian = pi / 180;
	 * increasing 1.4 makes the arc larger.
	 * 
	 * NEW
	 * limbSwing = time (vanilla)
	 * radians = speed (vanilla)
	 * walkSpeed = speed
	 * limbSwingAmount = radians
	 * 1.4 = ?
	 */
	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		// legs
		//		Test.LOGGER.info("animation: age -> {}, limbSwing -> {}, limbSwingAmount -> {}, rot -> {}", ageInTicks, limbSwing, limbSwingAmount, (Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount));
		float f = 1.0F;
		float radians = 0.6662F;
		float walkSpeed = 1.0F; // half speed = 0.5
		this.rightLeg.xRot = Mth.cos(limbSwing * radians * walkSpeed) * 1.4F * limbSwingAmount / f;
		this.leftLeg.xRot = Mth.cos(limbSwing * radians  * walkSpeed + (float)Math.PI) * 1.4F * limbSwingAmount / f;
		this.rightLeg.yRot = 0.0F;
		this.leftLeg.yRot = 0.0F;
		this.rightLeg.zRot = 0.0F;
		this.leftLeg.zRot = 0.0F;

		// loin cloth
		this.backCloth.xRot = Math.max(Math.max(0,  this.rightLeg.xRot), this.leftLeg.xRot);
		this.frontCloth.xRot = -this.backCloth.xRot;

		// fore-arms
		// 0.5235988F = 30 degrees
		float armSpeed = 0.25F;
		radians = 0.5235988F;
		this.rightLowerArm.xRot = Mth.cos(limbSwing * armSpeed) * radians * 1.4F * limbSwingAmount;
		this.leftLowerArm.xRot = Mth.cos(limbSwing * armSpeed + (float)Math.PI) * radians * 1.4F * limbSwingAmount;

		// reset arm rotations before bobbing, because bobbing is an addition to current rotation
		this.leftArm.xRot = 0F;
		this.leftArm.zRot = -0.8726646F;

		this.rightArm.xRot = 0F;
		this.rightArm.zRot = 0.8726646F;

		setupAttackAnimation(entity, ageInTicks);
		
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
	public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		leftLeg.render(poseStack, buffer, packedLight, packedOverlay);
		rightLeg.render(poseStack, buffer, packedLight, packedOverlay);
		body.render(poseStack, buffer, packedLight, packedOverlay);
		leftArm.render(poseStack, buffer, packedLight, packedOverlay);
		rightArm.render(poseStack, buffer, packedLight, packedOverlay);
		loinCloth.render(poseStack, buffer, packedLight, packedOverlay);
	}
	
	@Override
	public void resetSwing(T entity, ModelPart body, ModelPart rightArm, ModelPart leftArm) {
		body.yRot = 0;
		rightArm.x = rightArmX;
		rightArm.zRot = 0.8726646F;
		rightArm.yRot = 0;
		leftArm.x = leftArmX;
		leftArm.yRot = 0;
		leftArm.zRot = -rightArm.zRot;
		if (this.attackTime > 0) {
			if (changeAttackArm && entity.level().getGameTime() - changeAttackArmTime > 20) {
				if (attackArm == getRightArm()) {
					attackArm = getLeftArm();
				}
				else {
					attackArm = getRightArm();
				}
				changeAttackArm = false;				
			}			
		}
		else {
			if (!changeAttackArm) {
				changeAttackArm = true;
				changeAttackArmTime = entity.level().getGameTime();
			}
		}
	}
	
	@Override
	public ModelPart getAttackArm() {
		return attackArm;
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