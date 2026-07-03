package com.someguyssoftware.ddenizens.client.model;// Made with Blockbench 4.12.5

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.someguyssoftware.ddenizens.DD;
import com.someguyssoftware.ddenizens.entity.monster.WingedHumanoid;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

// TODO for models like Demons, Cambion that will hold weapon Items,
// TODO refactor the bbmodel and parts to use the Demonoid/Humanoid model,
// TODO using the vanilla names of the model
// TODO this is so that it can act like a vanilla humanoid and hold weapons, etc.
public class GargoyleModel<T extends WingedHumanoid> extends DemonlikeModel<T> {

	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(DD.MODID, "gargoyle"), "main");

	private static final float ARM_SPEED = 0.5F;
	private static final float ARM_SWING_MODIFIER =0.8F;

	private static final float LEG_SPEED = 0.5F;
	private static final float LEG_SWING_MODIFIER = 0.6F;

	private final ModelPart gargoyle;
	private final ModelPart body;
	private final ModelPart head;
	private final ModelPart rightHorn;
	private final ModelPart leftHorn;
	private final ModelPart jaw;
	private final ModelPart torso;
	private final ModelPart chest;
	private final ModelPart arms;
	private final ModelPart leftArm;
	private final ModelPart leftForeArm;
	private final ModelPart leftHand;
	private final ModelPart rightArm;
	private final ModelPart rightForeArm;
	private final ModelPart rightHand;
	private final ModelPart legs;
	private final ModelPart rightLeg;
	private final ModelPart rightThigh;
	private final ModelPart rightCalf;
	private final ModelPart rightAnkle;
	private final ModelPart rightFoot;
	private final ModelPart leftLeg;
	private final ModelPart leftThigh;
	private final ModelPart leftCalf;
	private final ModelPart leftAnkle;
	private final ModelPart leftFoot;

	private Position chestPos;
	private Position rightArmPos;
	private Position leftArmPos;

	private Rotation leftArmRots;
	private Rotation rightArmRots;

	/*
	 *
	 */
	public GargoyleModel(ModelPart root) {
		super(root);

		this.gargoyle = root.getChild("gargoyle");
		this.body = this.gargoyle.getChild("body");
		this.head = this.body.getChild("head");
		this.rightHorn = this.head.getChild("rightHorn");
		this.leftHorn = this.head.getChild("leftHorn");
		this.jaw = this.head.getChild("jaw");
		this.torso = this.body.getChild("torso");
		this.chest = this.torso.getChild("chest");
		this.arms = this.chest.getChild("arms");
		this.leftArm = this.arms.getChild("leftArm");
		this.leftForeArm = this.leftArm.getChild("leftForeArm");
		this.leftHand = this.leftForeArm.getChild("leftHand");
		this.rightArm = this.arms.getChild("rightArm");
		this.rightForeArm = this.rightArm.getChild("rightForeArm");
		this.rightHand = this.rightForeArm.getChild("rightHand");
		this.tail = this.body.getChild("tail");
		this.upperTail = this.tail.getChild("upperTail");
		this.lowerTail = this.tail.getChild("lowerTail");
		this.rightWing = this.chest.getChild("rightWing");
		this.rightWingAxis = this.rightWing.getChild("rightWingAxis");
		this.rightWingMedius = this.rightWingAxis.getChild("rightWingMedius");
		this.leftWing = this.chest.getChild("leftWing");
		this.leftWingAxis = this.leftWing.getChild("leftWingAxis");
		this.leftWingMedius = this.leftWingAxis.getChild("leftWingMedius");
		this.legs = this.gargoyle.getChild("legs");
		this.rightLeg = this.legs.getChild("rightLeg");
		this.rightThigh = this.rightLeg.getChild("rightThigh");
		this.rightCalf = this.rightLeg.getChild("rightCalf");
		this.rightAnkle = this.rightLeg.getChild("rightAnkle");
		this.rightFoot = this.rightAnkle.getChild("rightFoot");
		this.leftLeg = this.legs.getChild("leftLeg");
		this.leftThigh = this.leftLeg.getChild("leftThigh");
		this.leftCalf = this.leftLeg.getChild("leftCalf");
		this.leftAnkle = this.leftLeg.getChild("leftAnkle");
		this.leftFoot = this.leftAnkle.getChild("leftFoot");

		chestPos = new Position(chest);
		rightArmPos = new Position(rightArm);
		leftArmPos = new Position(leftArm);

		rightArmRots = new Rotation(this.rightArm);
		leftArmRots = new Rotation(this.leftArm);

		// set the demonlike properties
		rightWingRots = new Rotation(this.rightWing);
		leftWingRots = new Rotation(this.leftWing);
		tailRots = new Rotation(this.tail);
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition gargoyle = partdefinition.addOrReplaceChild("gargoyle", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 26.0F, 0.0F, 0.2618F, 0.0F, 0.0F));
		PartDefinition body = gargoyle.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offsetAndRotation(1.0F, -16.0F, 6.5F, 0.1309F, 0.0F, 0.0F));
		PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -5.5F, -7.0F, 8.0F, 6.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, -7.0F, -3.0F, -0.3491F, 0.0F, 0.0F));
		PartDefinition topTeeth2_r1 = head.addOrReplaceChild("topTeeth2_r1", CubeListBuilder.create().texOffs(32, 64).addBox(-1.0F, -1.5F, 0.0F, 2.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.25F, 0.9F, -6.7F, 0.0F, 0.0F, 0.7854F));
		PartDefinition topTeeth1_r1 = head.addOrReplaceChild("topTeeth1_r1", CubeListBuilder.create().texOffs(32, 64).addBox(-1.0F, -1.5F, 0.0F, 2.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.25F, 0.9F, -6.7F, 0.0F, 0.0F, 0.7854F));
		PartDefinition rightEar_r1 = head.addOrReplaceChild("rightEar_r1", CubeListBuilder.create().texOffs(32, 58).mirror().addBox(0.0F, -1.0F, 0.0F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-4.0F, -1.75F, -4.0F, 0.3491F, -0.3054F, 0.0F));
		PartDefinition leftEar_r1 = head.addOrReplaceChild("leftEar_r1", CubeListBuilder.create().texOffs(32, 58).addBox(-1.0F, -1.0F, 0.0F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, -1.75F, -4.0F, 0.3491F, 0.3054F, 0.0F));
		PartDefinition rightHorn = head.addOrReplaceChild("rightHorn", CubeListBuilder.create().texOffs(32, 53).mirror().addBox(-5.0F, -0.5F, -1.0F, 5.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-3.0F, -4.5F, -3.0F, -0.7854F, 0.0F, 0.3054F));
		PartDefinition leftHorn = head.addOrReplaceChild("leftHorn", CubeListBuilder.create().texOffs(32, 53).addBox(0.0F, -0.5F, -1.0F, 5.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, -4.5F, -3.0F, -0.7854F, 0.0F, -0.3054F));
		PartDefinition jaw = head.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(25, 26).addBox(-4.0F, -0.5F, -5.75F, 8.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.0F, -1.5F, 0.3054F, 0.0F, 0.0F));
		PartDefinition smallTeeth1_r1 = jaw.addOrReplaceChild("smallTeeth1_r1", CubeListBuilder.create().texOffs(19, 43).addBox(-1.0F, -1.5F, 0.0F, 2.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.25F, 0.0F, -5.7F, 0.0F, 0.0F, 0.7854F));
		PartDefinition rightCanine_r1 = jaw.addOrReplaceChild("rightCanine_r1", CubeListBuilder.create().texOffs(19, 40).addBox(-1.0F, -1.5F, 0.0F, 2.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.25F, 0.0F, -5.7F, 0.0F, 0.0F, 0.7854F));
		PartDefinition leftCanine_r1 = jaw.addOrReplaceChild("leftCanine_r1", CubeListBuilder.create().texOffs(19, 40).addBox(-1.0F, -1.5F, 0.0F, 2.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.75F, 0.0F, -5.7F, 0.0F, 0.0F, 0.7854F));
		PartDefinition torso = body.addOrReplaceChild("torso", CubeListBuilder.create().texOffs(0, 26).addBox(-3.0F, -9.0F, -2.0F, 8.0F, 9.0F, 4.0F, new CubeDeformation(0.0F))
				.texOffs(0, 63).addBox(-3.0F, -4.0F, -2.75F, 8.0F, 2.0F, 5.0F, new CubeDeformation(0.1F)), PartPose.offset(-2.0F, 6.0F, 0.0F));
		PartDefinition loincloth_r1 = torso.addOrReplaceChild("loincloth_r1", CubeListBuilder.create().texOffs(54, 26).addBox(-3.0F, 0.0F, -0.1F, 5.0F, 7.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.5F, -3.0F, -2.0F, -0.2618F, 0.0F, 0.0F));
		PartDefinition rightAbs_r1 = torso.addOrReplaceChild("rightAbs_r1", CubeListBuilder.create().texOffs(56, 55).mirror().addBox(-0.15F, -5.0F, -0.0303F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(56, 55).addBox(2.35F, -5.0F, -0.0303F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.1F, -3.0F, -2.25F, 0.1745F, 0.0F, 0.0F));
		PartDefinition hump1_r1 = torso.addOrReplaceChild("hump1_r1", CubeListBuilder.create().texOffs(60, 14).addBox(-2.0F, -3.0F, -1.5F, 6.0F, 4.0F, 3.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(0.0F, -14.0F, -1.5F, 0.7418F, 0.0F, 0.0F));
		PartDefinition chest = torso.addOrReplaceChild("chest", CubeListBuilder.create().texOffs(0, 89).addBox(-6.5F, -7.0F, -2.5F, 13.0F, 5.0F, 5.0F, new CubeDeformation(0.2F)),
				PartPose.offsetAndRotation(1.0F, -7.0F, 0.0F, 0.3054F, 0.0F, 0.0F));
		PartDefinition rightWing = chest.addOrReplaceChild("rightWing", CubeListBuilder.create(), PartPose.offsetAndRotation(-2.5F, -4.0F, 2.5F, 0.0436F, 0.3927F, 0.7854F));
		PartDefinition rightWingAxis = rightWing.addOrReplaceChild("rightWingAxis", CubeListBuilder.create().texOffs(25, 82).addBox(-11.5F, -0.5F, 0.0F, 12.0F, 1.0F, 1.0F, new CubeDeformation(0.02F))
				.texOffs(33, 0).addBox(-11.5F, -0.5F, 0.5F, 12.0F, 12.0F, 0.0F, new CubeDeformation(0.0F))
				.texOffs(13, 49).addBox(-11.5F, 0.5F, 0.0F, 1.0F, 11.0F, 1.0F, new CubeDeformation(0.02F)), PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition rightWingClaw = rightWingAxis.addOrReplaceChild("rightWingClaw", CubeListBuilder.create().texOffs(67, 26).mirror().addBox(-0.5F, -3.0F, 0.0F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-11.0F, 0.0F, 0.5F, 0.0F, 0.7854F, 0.0F));
		PartDefinition rightWingMedius = rightWingAxis.addOrReplaceChild("rightWingMedius", CubeListBuilder.create().texOffs(35, 1).addBox(0.0F, -0.5F, -11.5F, 0.0F, 12.0F, 12.0F, new CubeDeformation(0.0F))
				.texOffs(1, 72).addBox(-0.5F, -0.5F, -11.5F, 1.0F, 1.0F, 11.0F, new CubeDeformation(0.02F)), PartPose.offsetAndRotation(-11.0F, 0.0F, 0.5F, 0.0F, 0.3054F, 0.0F));
		PartDefinition leftWing = chest.addOrReplaceChild("leftWing", CubeListBuilder.create(), PartPose.offsetAndRotation(2.5F, -4.0F, 2.5F, -0.0436F, -0.3927F, -0.7854F));
		PartDefinition leftWingAxis = leftWing.addOrReplaceChild("leftWingAxis", CubeListBuilder.create().texOffs(25, 82).addBox(-0.5F, -0.5F, 0.0F, 12.0F, 1.0F, 1.0F, new CubeDeformation(0.02F))
				.texOffs(33, 0).mirror().addBox(-0.5F, -0.5F, 0.5F, 12.0F, 12.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(13, 49).addBox(10.5F, 0.5F, 0.0F, 1.0F, 11.0F, 1.0F, new CubeDeformation(0.02F)), PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition leftWingClaw = leftWingAxis.addOrReplaceChild("leftWingClaw", CubeListBuilder.create().texOffs(67, 26).addBox(-2.5F, -3.0F, 0.0F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(11.0F, 0.0F, 0.5F, 0.0F, -0.7854F, 0.0F));
		PartDefinition leftWingMedius = leftWingAxis.addOrReplaceChild("leftWingMedius", CubeListBuilder.create().texOffs(35, 1).addBox(0.0F, -0.5F, -11.5F, 0.0F, 12.0F, 12.0F, new CubeDeformation(0.0F))
				.texOffs(1, 72).addBox(-0.5F, -0.5F, -11.5F, 1.0F, 1.0F, 11.0F, new CubeDeformation(0.02F)), PartPose.offsetAndRotation(11.0F, 0.0F, 0.5F, 0.0F, -0.3054F, 0.0F));
		PartDefinition arms = chest.addOrReplaceChild("arms", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
		PartDefinition leftArm = arms.addOrReplaceChild("leftArm", CubeListBuilder.create().texOffs(48, 35).addBox(0.0F, -1.5F, -1.5F, 3.0F, 7.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(6.0F, -5.0F, 0.5F, -0.7854F, -0.1309F, 0.0F));
		PartDefinition leftForeArm = leftArm.addOrReplaceChild("leftForeArm", CubeListBuilder.create().texOffs(0, 49).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 7.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.5F, 5.5F, 0.0F, -0.6109F, 0.0F, 0.0F));
		PartDefinition leftHand = leftForeArm.addOrReplaceChild("leftHand", CubeListBuilder.create(), PartPose.offset(-7.5F, -0.5F, -0.5F));
		PartDefinition leftThumb_r1 = leftHand.addOrReplaceChild("leftThumb_r1", CubeListBuilder.create().texOffs(25, 59).addBox(-3.0F, 0.0F, 0.0F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(7.0F, 6.5F, -0.5F, 0.0F, 0.0F, 0.2618F));
		PartDefinition leftClaw2_r1 = leftHand.addOrReplaceChild("leftClaw2_r1", CubeListBuilder.create().texOffs(18, 59).addBox(0.0F, 0.0F, 0.0F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.0F, 6.5F, 1.0F, 0.0F, -0.4363F, 0.4363F));
		PartDefinition leftClaw1_r1 = leftHand.addOrReplaceChild("leftClaw1_r1", CubeListBuilder.create().texOffs(18, 59).addBox(0.0F, 0.0F, 0.0F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.0F, 6.5F, 0.0F, 0.0F, 0.4363F, 0.6545F));
		PartDefinition rightArm = arms.addOrReplaceChild("rightArm", CubeListBuilder.create().texOffs(48, 35).mirror().addBox(-3.0F, -1.5F, -1.5F, 3.0F, 7.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-6.0F, -5.0F, 0.5F, -0.7854F, 0.1309F, 0.0F));
		PartDefinition rightForeArm = rightArm.addOrReplaceChild("rightForeArm", CubeListBuilder.create().texOffs(0, 49).mirror().addBox(-1.5F, 0.0F, -1.5F, 3.0F, 7.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-1.5F, 5.5F, 0.0F, -0.6109F, 0.0F, 0.0F));
		PartDefinition rightHand = rightForeArm.addOrReplaceChild("rightHand", CubeListBuilder.create(), PartPose.offset(-7.5F, -0.5F, -0.5F));
		PartDefinition rightThumb_r1 = rightHand.addOrReplaceChild("rightThumb_r1", CubeListBuilder.create().texOffs(25, 59).mirror().addBox(0.0F, 0.0F, 0.0F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(8.0F, 6.5F, -0.5F, 0.0F, 0.0F, 0.2618F));
		PartDefinition rightClaw2_r1 = rightHand.addOrReplaceChild("rightClaw2_r1", CubeListBuilder.create().texOffs(18, 59).mirror().addBox(-3.0F, 0.0F, 0.0F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(7.0F, 6.5F, 1.0F, 0.0F, 0.4363F, -0.2618F));
		PartDefinition rightClaw1_r1 = rightHand.addOrReplaceChild("rightClaw1_r1", CubeListBuilder.create().texOffs(18, 59).mirror().addBox(-3.0F, 0.0F, 0.0F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(7.0F, 6.5F, 0.0F, 0.0F, -0.4363F, -0.7854F));
		PartDefinition tail = body.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offsetAndRotation(-1.0F, 4.5F, 0.5F, 0.3927F, 0.0F, 0.0F));
		PartDefinition upperTail = tail.addOrReplaceChild("upperTail", CubeListBuilder.create().texOffs(19, 47).addBox(-1.5F, -0.5F, -2.0F, 3.0F, 8.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.3446F, 0.7325F));
		PartDefinition lowerTail = tail.addOrReplaceChild("lowerTail", CubeListBuilder.create().texOffs(47, 55).addBox(-1.0F, -0.1453F, -1.077F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 7.3446F, 0.2325F, 0.2182F, 0.0F, 0.0F));
		PartDefinition legs = gargoyle.addOrReplaceChild("legs", CubeListBuilder.create(), PartPose.offset(16.0F, 0.0F, 6.0F));
		PartDefinition rightLeg = legs.addOrReplaceChild("rightLeg", CubeListBuilder.create(), PartPose.offsetAndRotation(-20.0F, -11.0F, 1.0F, -0.2618F, 0.2182F, 0.0F));
		PartDefinition rightThigh = rightLeg.addOrReplaceChild("rightThigh", CubeListBuilder.create().texOffs(25, 35).mirror().addBox(-1.5F, -3.0F, 0.2F, 3.0F, 3.0F, 8.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 4.0F, -7.0F, 0.3491F, 0.0F, 0.0F));
		PartDefinition rightCalf = rightLeg.addOrReplaceChild("rightCalf", CubeListBuilder.create().texOffs(51, 46).mirror().addBox(-1.5F, -6.0F, 0.0F, 3.0F, 5.0F, 3.0F, new CubeDeformation(-0.1F)).mirror(false), PartPose.offsetAndRotation(0.0F, 9.0F, -4.0F, 0.48F, 0.0F, 0.0F));
		PartDefinition rightAnkle = rightLeg.addOrReplaceChild("rightAnkle", CubeListBuilder.create(), PartPose.offset(12.0F, 11.0F, -1.0F));
		PartDefinition rightFoot = rightAnkle.addOrReplaceChild("rightFoot", CubeListBuilder.create().texOffs(58, 8).addBox(-0.5F, 2.0F, -7.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-12.0F, -3.0F, -1.0F, -0.0436F, 0.0F, 0.0F));
		PartDefinition toe3_r1 = rightFoot.addOrReplaceChild("toe3_r1", CubeListBuilder.create().texOffs(58, 8).addBox(-0.5F, -0.5F, -3.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 2.5F, -4.0F, 0.0F, -0.2182F, 0.0F));
		PartDefinition toe4_r1 = rightFoot.addOrReplaceChild("toe4_r1", CubeListBuilder.create().texOffs(58, 8).addBox(-0.5F, -0.5F, -3.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 2.5F, -4.0F, 0.0F, 0.2182F, 0.0F));
		PartDefinition foot_r1 = rightFoot.addOrReplaceChild("foot_r1", CubeListBuilder.create().texOffs(0, 40).mirror().addBox(-2.0F, -2.0F, 0.0F, 3.0F, 2.0F, 6.0F, new CubeDeformation(-0.2F)).mirror(false), PartPose.offsetAndRotation(0.5F, 3.0F, -4.0F, 0.6109F, 0.0F, 0.0F));
		PartDefinition leftLeg = legs.addOrReplaceChild("leftLeg", CubeListBuilder.create(), PartPose.offsetAndRotation(-12.0F, -11.0F, 1.0F, -0.2618F, -0.2182F, 0.0F));
		PartDefinition leftThigh = leftLeg.addOrReplaceChild("leftThigh", CubeListBuilder.create().texOffs(25, 35).addBox(-1.5F, -3.0F, 0.2F, 3.0F, 3.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 4.0F, -7.0F, 0.3491F, 0.0F, 0.0F));
		PartDefinition leftCalf = leftLeg.addOrReplaceChild("leftCalf", CubeListBuilder.create().texOffs(51, 46).addBox(-1.5F, -6.0F, 0.0F, 3.0F, 5.0F, 3.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(0.0F, 9.0F, -4.0F, 0.48F, 0.0F, 0.0F));
		PartDefinition leftAnkle = leftLeg.addOrReplaceChild("leftAnkle", CubeListBuilder.create(), PartPose.offset(12.0F, 11.0F, -1.0F));
		PartDefinition leftFoot = leftAnkle.addOrReplaceChild("leftFoot", CubeListBuilder.create().texOffs(58, 8).addBox(-0.5F, 2.0F, -7.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-12.0F, -3.0F, -1.0F, -0.0436F, 0.0F, 0.0F));
		PartDefinition leftToe4_r1 = leftFoot.addOrReplaceChild("leftToe4_r1", CubeListBuilder.create().texOffs(58, 8).addBox(-0.5F, -0.5F, -3.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 2.5F, -4.0F, 0.0F, -0.2182F, 0.0F));
		PartDefinition leftToe5_r1 = leftFoot.addOrReplaceChild("leftToe5_r1", CubeListBuilder.create().texOffs(58, 8).addBox(-0.5F, -0.5F, -3.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 2.5F, -4.0F, 0.0F, 0.2182F, 0.0F));
		PartDefinition foot_r2 = leftFoot.addOrReplaceChild("foot_r2", CubeListBuilder.create().texOffs(0, 40).addBox(-2.0F, -2.0F, 0.0F, 3.0F, 2.0F, 6.0F, new CubeDeformation(-0.2F)), PartPose.offsetAndRotation(0.5F, 3.0F, -4.0F, 0.6109F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		// head
		swivelHead(netHeadYaw, headPitch, -20, 20, -20, 20);

		// TODO is this data that has to be synced? - YES
		if (entity.isFlying()) {
			flapWings(ageInTicks);
		} else {
			swingArms(limbSwing, limbSwingAmount, ageInTicks);
			swingLegs(limbSwing, limbSwingAmount, ageInTicks);
			swingTail(ageInTicks);
		}

		setupAttackAnimation(entity, ageInTicks);

		resetBobs();
		bobArm(getRightArm(), ageInTicks, 1);
		bobArm(getLeftArm(), ageInTicks, -1);
		bobUpperBody(ageInTicks, 0);

	}


	// @Override - has to override in Gargoyle because of the head tilt thing (might be universal to all demonlike models)
	public void swivelHead(float netHeadYaw, float headPitch, float ageInTicks) {
		getHead().yRot = netHeadYaw * ((float)Math.PI / 180F);
		getHead().xRot = headPitch * ((float)Math.PI / 180F) - 0.2181662F; // +12.5 degrees because has a slight down angle
	}

	// TODO will be part of HumanlikeModel
	public void swivelHead(float netYaw, float pitch, float minYaw, float maxYaw, float minPitch, float maxPitch) {
		if (netYaw < 0) {
			getHead().yRot = Math.max(minYaw, netYaw) * ((float)Math.PI / 180F);
		}
		else {
			getHead().yRot = Math.min(maxYaw, netYaw) * ((float)Math.PI / 180F);
		}

		// corrects the 12.5 downward angle of the head that is not present in the source bbmodel
		final var CORRECTION = 0.2181662F;
		if (pitch < 0) {
			getHead().xRot = Math.max(minPitch, pitch) * ((float)Math.PI / 180F) - CORRECTION;
		}
		else {
			getHead().xRot = Math.min(maxPitch, pitch) * ((float)Math.PI / 180F) - CORRECTION;
		}
	}

	public void swingArms(float limbSwing, float limbSwingAmount, float ageInTicks) {
		getRightArm().xRot = getRightArmOriginalRotation().x() + Mth.cos(limbSwing * getArmSpeed()) * getArmSwingModifier() * 1.4F * limbSwingAmount;
		getLeftArm().xRot = getLeftArmOriginalRotation().x() + Mth.cos(limbSwing * getArmSpeed() + (float)Math.PI) * getArmSwingModifier() * 1.4F * limbSwingAmount;
	}

	public void swingLegs(float limbSwing, float limbSwingAmount, float ageInTicks) {
		final float RESTING_LEG_RADIANS = -0.2618F; // 15 degrees
		getRightLeg().xRot = (Mth.cos(limbSwing * getLegSpeed()) * 1.4F * limbSwingAmount * getLegSwingModifier()) + RESTING_LEG_RADIANS;
		getLeftLeg().xRot = (Mth.cos(limbSwing  * getLegSpeed() + (float)Math.PI) * 1.4F * limbSwingAmount * getLegSwingModifier()) + RESTING_LEG_RADIANS;
	}

	public void resetBobs() {
//		getRightArm().zRot = rightArmRots.z();
//		getLeftArm().zRot = leftArmRots.z();
	}

	public void bobArm(ModelPart part, float age, float direction) {
		part.zRot += direction * (Mth.cos(age * 0.15F) * 0.05F + 0.05F);
	}

	public void bobUpperBody(float ageInTicks, float direction) {
		getUpperBody().y = chestPos.y() + (Mth.cos(ageInTicks * 0.15F) * 0.5F + 0.05F);
	}



	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		gargoyle.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public void resetSwing(T entity, ModelPart body, ModelPart rightArm, ModelPart leftArm) {
		body.yRot = 0;

		rightArm.x = getRightArmOriginalPosition().x();
		rightArm.zRot = getRightArmOriginalRotation().z();
		rightArm.yRot = getRightArmOriginalRotation().y();

		leftArm.x = getLeftArmOriginalPosition().x();
		leftArm.yRot = getLeftArmOriginalRotation().y();
		leftArm.zRot = getLeftArmOriginalRotation().z();
	}

	// TODO will be part of HumanlikeModel
	@Override
	public ModelPart getHead() {
		return this.head;
	}
	// TODO will be part of HumanlikeModel
	@Override
	public ModelPart getBody() {
		return this.body;
	}

	public ModelPart getUpperBody() {
		return this.chest;
	}
	// TODO will be part of HumanlikeModel
	@Override
	public ModelPart getRightArm() {
		return this.rightArm;
	}
	// TODO will be part of HumanlikeModel
	@Override
	public ModelPart getLeftArm() {
		return this.leftArm;
	}
	// TODO will be part of HumanlikeModel
	public float getArmSpeed() {
		return ARM_SPEED;
	}

	// TODO will be part of HumanlikeModel
	public float getArmSwingModifier() {
		return ARM_SWING_MODIFIER;
	}
	// TODO will be part of HumanlikeModel
	public Position getRightArmOriginalPosition() {
		return rightArmPos;
	}
	// TODO will be part of HumanlikeModel
	public Position getLeftArmOriginalPosition() {
		return leftArmPos;
	}
	// TODO will be part of HumanlikeModel
	public Rotation getRightArmOriginalRotation() {
		return rightArmRots;
	}
	// TODO will be part of HumanlikeModel
	public Rotation getLeftArmOriginalRotation() {
		return leftArmRots;
	}

	// TODO will be part of HumanlikeModel
	@Override
	public ModelPart getRightLeg() {
		return this.rightLeg;
	}
	// TODO will be part of HumanlikeModel
	@Override
	public ModelPart getLeftLeg() {
		return this.leftLeg;
	}
	// TODO will be part of HumanlikeModel
	public float getLegSpeed() {
		return LEG_SPEED;
	}
	// TODO will be part of HumanlikeModel
	public float getLegSwingModifier() {
		return LEG_SWING_MODIFIER;
	}


}