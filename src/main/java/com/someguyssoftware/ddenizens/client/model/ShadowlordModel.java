/*
 * This file is part of  Dungeon Denizens.
 * Copyright (c) 2024 Mark Gottschling (gottsch)
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
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.Mob;

import java.util.Random;

/**
 * @author Mark Gottschling on Jan 28, 2024
 *
 */
public class ShadowlordModel<T extends Mob> extends HumanoidModel<T> {

	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(DD.MODID, "shadowlord"), "main");

	private final ModelPart frontRobes;
	private final ModelPart backRobes;
	private final ModelPart leftRobe;
	private final ModelPart rightRobe;
	private final ModelPart frontRightRobe;
	private final ModelPart frontLeftRobe;
	private final ModelPart frontRobe1;
	private final ModelPart frontRobe2;
	private final ModelPart frontRobe3;

	private final ModelPart backLeftRobe;
	private final ModelPart backRightRobe;
	private final ModelPart backRobe1;
	private final ModelPart backRobe2;
	private final ModelPart backRobe3;
	private ModelPart[] robes = new ModelPart[12];
	private int[] robeOffsets = new int[12];
	private float[] robeSpeeds = new float[12];
	private float[] robeDirections = new float[12];

	private Rotation[] robeRotations = new Rotation[12];

	protected Rotation rightArmRots;
	protected Position rightArmPos;
	protected Rotation leftArmRots;
	protected Position leftArmPos;

	public ShadowlordModel(ModelPart root) {
		super(root, RenderType::entityTranslucentCull);

		this.frontRobes = root.getChild("front_robes");
		this.backRobes = root.getChild("back_robes");
		this.frontRobe1 = frontRobes.getChild("front_robe1");
		this.frontRobe2 = frontRobes.getChild("front_robe2");
		this.frontRobe3 = frontRobes.getChild("front_robe3");
		this.backRobe1 = backRobes.getChild("back_robe1");
		this.backRobe2 = backRobes.getChild("back_robe2");
		this.backRobe3 = backRobes.getChild("back_robe3");


		this.leftRobe = root.getChild("left_robe");
		this.rightRobe = root.getChild("right_robe");
		this.frontRightRobe = root.getChild("front_right_robe");
		this.frontLeftRobe = root.getChild("front_left_robe");
		this.backLeftRobe = root.getChild("back_left_robe");
		this.backRightRobe = root.getChild("back_right_robe");

		rightArmRots = new Rotation(this.rightArm);
		rightArmPos = new Position(this.rightArm);
		leftArmRots = new Rotation(this.leftArm);
		leftArmPos = new Position(this.leftArm);

		robes[0] = frontRobe1;
		robes[1] = frontRobe2;
		robes[2] = frontRobe3;
		robes[3] = backRobe1;
		robes[4] = backRobe2;
		robes[5] = backRobe3;
		robes[6] = rightRobe;
		robes[7] = leftRobe;
		robes[8] = frontRightRobe;
		robes[9] = frontLeftRobe;
		robes[10] = backRightRobe;
		robes[11] = backLeftRobe;

		Random random = new Random();
		for (int i = 0; i < 12; i++) {
			robeRotations[i] = new Rotation(robes[i].xRot, robes[i].yRot, robes[i].zRot);
			robeOffsets[i] = random.nextInt(0, 180);
			robeSpeeds[i] = random.nextFloat(0.2F, 0.35F);
			robeDirections[i] = random.nextInt() % 2 == 0 ? 1F: -1F;
		}
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = HumanoidModel.createMesh(CubeDeformation.NONE, 0.0F);
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -5.0F, 8.0F, 8.0F, 10.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(3.9F, -8.0F, -5.0F, 0.0F, 8.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(-3.9F, -8.0F, -5.0F, 0.0F, 8.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(27, 0).addBox(-4.0F, -7.0F, -4.0F, 8.0F, 7.0F, 2.0F, new CubeDeformation(-0.01F)), PartPose.offset(0.0F, -4.0F, 0.0F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(26, 33).addBox(-4.0F, 0.0F, -1.0F, 8.0F, 10.0F, 2.0F, new CubeDeformation(0.01F)), PartPose.offset(0.0F, -4.0F, 0.0F));

		PartDefinition chest = body.addOrReplaceChild("chest", CubeListBuilder.create().texOffs(26, 19).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 10.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -1.75F, 0.0873F, 0.0F, 0.0F));

		PartDefinition back = body.addOrReplaceChild("back", CubeListBuilder.create().texOffs(26, 19).addBox(-4.0F, 0.0F, -1.0F, 8.0F, 10.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 1.75F, -0.0873F, 0.0F, 0.0F));

		PartDefinition left_arm = partdefinition.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(44, 43).addBox(1.0F, -3.0F, -2.0F, 3.0F, 14.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, -1.0F, 0.0F, 0.0F, 0.0F, -0.0873F));

		PartDefinition right_arm = partdefinition.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(0, 46).addBox(-3.0F, -3.0F, -2.0F, 3.0F, 14.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, -1.0F, 0.0F, 0.0F, 0.0F, 0.0873F));

		PartDefinition right_leg = partdefinition.addOrReplaceChild("right_leg", CubeListBuilder.create(), PartPose.offset(-1.9F, 12.0F, 0.0F));

		PartDefinition left_leg = partdefinition.addOrReplaceChild("left_leg", CubeListBuilder.create(), PartPose.offset(1.9F, 12.0F, 0.0F));

		PartDefinition front_robes = partdefinition.addOrReplaceChild("front_robes", CubeListBuilder.create(), PartPose.offset(0.0F, 4.0F, -3.0F));

		PartDefinition front_robe1 = front_robes.addOrReplaceChild("front_robe1", CubeListBuilder.create().texOffs(57, 0).addBox(-2.0F, 0.0872F, 0.0F, 4.0F, 20.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 0.0F, 0.0F));

		PartDefinition front_robe2 = front_robes.addOrReplaceChild("front_robe2", CubeListBuilder.create().texOffs(49, 21).addBox(-2.0F, 0.0F, 0.0F, 4.0F, 20.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, 0.0F, 0.0F));

		PartDefinition front_robe3 = front_robes.addOrReplaceChild("front_robe3", CubeListBuilder.create().texOffs(48, 0).addBox(-1.9F, -0.0872F, 0.0962F, 4.0F, 20.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.1F, 0.0872F, 0.0038F));

		PartDefinition back_robes = partdefinition.addOrReplaceChild("back_robes", CubeListBuilder.create(), PartPose.offset(0.0F, 4.0F, 3.0F));

		PartDefinition back_robe1 = back_robes.addOrReplaceChild("back_robe1", CubeListBuilder.create().texOffs(31, 46).addBox(-2.0F, 0.0F, 0.0F, 4.0F, 20.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 0.0F, 0.0F));

		PartDefinition back_robe2 = back_robes.addOrReplaceChild("back_robe2", CubeListBuilder.create().texOffs(22, 46).addBox(-2.0F, 0.0F, 0.0F, 4.0F, 20.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, 0.0F, 0.0F));

		PartDefinition back_robe3 = back_robes.addOrReplaceChild("back_robe3", CubeListBuilder.create().texOffs(13, 46).addBox(-2.0F, 0.0F, -0.1F, 4.0F, 20.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition left_robe = partdefinition.addOrReplaceChild("left_robe", CubeListBuilder.create().texOffs(13, 19).addBox(-0.0472F, 0.0F, -3.0F, 0.0F, 20.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, 4.0F, 0.0F, 0.0F, 0.0F, -0.0436F));

		PartDefinition right_robe = partdefinition.addOrReplaceChild("right_robe", CubeListBuilder.create().texOffs(0, 19).addBox(0.0472F, 0.0F, -3.0F, 0.0F, 20.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.0F, 4.0F, 0.0F, 0.0F, 0.0F, 0.0436F));

		PartDefinition front_right_robe = partdefinition.addOrReplaceChild("front_right_robe", CubeListBuilder.create().texOffs(45, 61).addBox(-0.75F, 0.0F, -0.2271F, 2.0F, 18.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.25F, 6.0F, -1.5F, -0.0873F, 0.7854F, 0.0F));

		PartDefinition front_left_robe = partdefinition.addOrReplaceChild("front_left_robe", CubeListBuilder.create().texOffs(40, 61).addBox(-1.25F, 0.0F, -0.2271F, 2.0F, 18.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.25F, 6.0F, -1.5F, -0.0873F, -0.7854F, 0.0F));

		PartDefinition back_left_robe = partdefinition.addOrReplaceChild("back_left_robe", CubeListBuilder.create().texOffs(58, 21).addBox(-1.25F, 0.0F, 0.2271F, 2.0F, 18.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.25F, 6.0F, 1.5F, 0.0873F, 0.7854F, 0.0F));

		PartDefinition back_right_robe = partdefinition.addOrReplaceChild("back_right_robe", CubeListBuilder.create().texOffs(57, 42).addBox(-1.0F, 0.0F, 0.2271F, 2.0F, 18.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, 6.0F, 1.5F, 0.0873F, -0.7854F, 0.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		// head
		this.head.yRot = netHeadYaw * ((float)Math.PI / 180F);
		this.head.xRot = headPitch * ((float)Math.PI / 180F);

		// reset arm rotations before bobbing
		resetArm(rightArm, rightArmRots, rightArmPos);
		resetArm(leftArm, leftArmRots, leftArmPos);

		// arms swing
		float armSpeed = 0.25F;
		float radians = 0.5235988F;
		this.rightArm.xRot = Mth.cos(limbSwing * armSpeed) * radians * 1.4F * limbSwingAmount;
		this.leftArm.xRot = Mth.cos(limbSwing * armSpeed + (float)Math.PI) * radians * 1.4F * limbSwingAmount;

		for (int i = 0; i < 12; i++) {
			resetRobe(robes[i], robeRotations[i]);
		}

		bobRobe(robes[0], ageInTicks, 0.087266f , robeDirections[0], robeOffsets[0], robeSpeeds[0]);
		bobRobe(robes[1], ageInTicks, 0.043266f , robeDirections[1], robeOffsets[1], robeSpeeds[1]);
		bobRobe(robes[2], ageInTicks, 0.087266f , robeDirections[2], robeOffsets[2], robeSpeeds[2]);

		bobRobe(robes[3], ageInTicks, 0.087266f , robeDirections[3], robeOffsets[3], robeSpeeds[3]);
		bobRobe(robes[4], ageInTicks, 0.043266f , robeDirections[4], robeOffsets[4], robeSpeeds[4]);
		bobRobe(robes[5], ageInTicks, 0.087266f , robeDirections[5], robeOffsets[5], robeSpeeds[5]);

		bobSideRobe(robes[6], ageInTicks, -0.043266f , robeDirections[6], robeOffsets[6], robeSpeeds[6]);
		bobSideRobe(robes[7], ageInTicks, 0.043266f , robeDirections[7], robeOffsets[7], robeSpeeds[7]);

		bobRobe(robes[8], ageInTicks, 0.043266f , robeDirections[8], robeOffsets[8], robeSpeeds[8]);
		bobRobe(robes[9], ageInTicks, 0.043266f , robeDirections[9], robeOffsets[9], robeSpeeds[9]);
		bobRobe(robes[10], ageInTicks, 0.043266f , robeDirections[10], robeOffsets[10], robeSpeeds[10]);
		bobRobe(robes[11], ageInTicks, 0.043266f , robeDirections[11], robeOffsets[11], robeSpeeds[11]);

		AnimationUtils.bobArms(this.rightArm, this.leftArm, ageInTicks);

		setupAttackAnimation(entity, ageInTicks);
	}

	/**
	 * Need to override to prevent the movement in position of left arm when attacking.
	 * @param entity
	 * @param p_102859_
	 */
	@Override
	protected void setupAttackAnimation(T entity, float p_102859_) {
		if (!(this.attackTime <= 0.0F)) {
			HumanoidArm humanoidarm = this.getAttackArm(entity);
			ModelPart modelpart = this.getArm(humanoidarm);
			float f = this.attackTime;
			this.body.yRot = Mth.sin(Mth.sqrt(f) * ((float)Math.PI * 2F)) * 0.2F;
			if (humanoidarm == HumanoidArm.LEFT) {
				this.body.yRot *= -1.0F;
			}

			this.rightArm.z = Mth.sin(this.body.yRot) * 5.0F;
			this.rightArm.x = -Mth.cos(this.body.yRot) * 5.0F;
//			this.leftArm.z = -Mth.sin(this.body.yRot) * 5.0F;
//			this.leftArm.x = Mth.cos(this.body.yRot) * 5.0F;
			this.rightArm.yRot += this.body.yRot;
			this.leftArm.yRot += this.body.yRot;
			this.leftArm.xRot += this.body.yRot;
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

	private HumanoidArm getAttackArm(T p_102857_) {
		return HumanoidArm.RIGHT;
	}

	// this is for arms
	public void resetArm(ModelPart part, Rotation rotation, Position position) {
		part.xRot = rotation.x();
		part.yRot = rotation.y();
		part.zRot = rotation.z();

		part.x = position.x();
		part.y = position.y();
		part.z = position.z();
	}

	private void resetRobe(ModelPart robe, Rotation rotation) {
		robe.xRot = rotation.x();
		robe.yRot = rotation.y();
		robe.zRot = rotation.z();
	}

	public void bobRobe(ModelPart part, float age, float radians, float direction, int offset, float speed) {
		part.xRot += direction * (Mth.cos((age + offset) * speed) * radians + 0.05F);
	}

	public void bobSideRobe(ModelPart part, float age, float radians, float direction, int offset, float speed) {
		part.zRot += direction * (Mth.cos((age + offset) * speed) * radians + 0.05F);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		head.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		leftArm.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		rightArm.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		//rightLeg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		//leftLeg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		frontRobes.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		backRobes.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		leftRobe.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		rightRobe.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		frontRightRobe.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		frontLeftRobe.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		backLeftRobe.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		backRightRobe.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	/**
	 * Need to override this as the shadowlord's arm is thinner than a normal
	 * humanoid and then the item won't be in the correct position.
	 * @param arm
	 * @param poseStack
	 */
//	@Override
//	public void translateToHand(HumanoidArm arm, PoseStack poseStack) {
////		float side = (arm == HumanoidArm.RIGHT) ? 2.0F : -2.0F;
//		ModelPart modelpart = this.getArm(arm);
//		modelpart.y -= 2.0f;
//		modelpart.translateAndRotate(poseStack);
//		modelpart.y += 2.0f;
//	}
}