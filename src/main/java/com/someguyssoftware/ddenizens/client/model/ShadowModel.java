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

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;

/**
 * 
 * @author Mark Gottschling on Apr 12, 2022
 *
 * @param <T>
 */
public class ShadowModel<T extends Mob> extends HumanoidModel<T> {

	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(DD.MODID, "shadow_model"), "main");

	public ModelPart leftHip;
	public ModelPart rightHip;
	protected Rotation rightArmRots;
	protected Rotation leftArmRots;

	protected Rotation rightHipRots;
	protected Rotation leftHipRots;
	
	/**
	 * 
	 * @param root
	 */
	public ShadowModel(ModelPart root) {
		super(root, RenderType::entityTranslucentCull);

		this.leftHip = leftLeg.getChild("leftHip");
		this.rightHip = rightLeg.getChild("rightHip");

		rightArmRots = new Rotation(this.rightArm);
		leftArmRots = new Rotation(this.leftArm);
		rightHipRots = new Rotation(this.rightHip);
		leftHipRots = new Rotation(this.leftHip);
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = HumanoidModel.createMesh(CubeDeformation.NONE, 0.0F);

		PartDefinition partdefinition = meshdefinition.getRoot();

		// overwrite base body parts
		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 17).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.3927F, 0.0F, 0.0F));

		PartDefinition right_arm = partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(0, 34).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-5.0F, 2.0F, 0.0F, -0.9599F, 0.0F, 0.0F));

		PartDefinition left_arm = partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(17, 34).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.0F, 2.0F, 0.0F, -0.8727F, 0.0F, 0.0F));

		PartDefinition right_leg = partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create(), PartPose.offsetAndRotation(-2.0F, 0.0F, 0.0F, 0.3927F, 0.0F, 0.0F));

		PartDefinition rightHip = right_leg.addOrReplaceChild("rightHip", CubeListBuilder.create().texOffs(25, 17).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 12.0F, 0.0F));

		PartDefinition left_leg = partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create(), PartPose.offsetAndRotation(2.0F, 0.0F, 0.0F, 0.3927F, 0.0F, 0.0F));

		PartDefinition leftHip = left_leg.addOrReplaceChild("leftHip", CubeListBuilder.create().texOffs(33, 0).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 12.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		// head
		this.head.yRot = netHeadYaw * ((float)Math.PI / 180F);
		this.head.xRot = headPitch * ((float)Math.PI / 180F);

		// reset arm rotations before bobbing, because bobbing is an addition to current rotation
		resetArm(rightArm, rightArmRots);
		resetArm(leftArm, leftArmRots);

		this.leftHip.xRot = leftHipRots.x();
		this.rightHip.xRot = rightHipRots.x();

		// bob the arms
		bobModelPart(this.rightArm, ageInTicks, 1.0F);
		bobModelPart(this.leftArm, ageInTicks, -1.0F);
		bobModelPart(this.rightHip, ageInTicks, 1.0F);
		bobModelPart(this.leftHip, ageInTicks, -1.0F);

		setupAttackAnimation(entity, ageInTicks);
	}

	public void resetArm(ModelPart part, Rotation rotations) {
		part.xRot = rotations.x();
		part.yRot = rotations.y();
		part.zRot = rotations.z();
	}

	/**
	 * 
	 * @param part
	 * @param age
	 * @param direction
	 */
	public static void bobModelPart(ModelPart part, float age, float direction) {
		part.xRot += direction * (Mth.cos(age * /*0.09F*/ 0.15F) * 0.15F + 0.05F);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		head.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		leftArm.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		rightArm.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		rightLeg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		leftLeg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		// NOTE do not render hat
	}

}