package com.someguyssoftware.ddenizens.client.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.someguyssoftware.ddenizens.DD;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;

import java.util.Random;

public class SpectatorModel<T extends Entity> extends BeholderkinModel<T> {
	public static final String MODEL_NAME = "spectator_model";
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(DD.MODID, MODEL_NAME), "main");
	private final ModelPart body;
	private final ModelPart head;
	private final ModelPart lower; // bottom jaw

	private final ModelPart eyeStalk1;
	private final ModelPart eyeStalk2;
	private final ModelPart eyeStalk3;
	private final ModelPart eyeStalk4;

	private final ModelPart eye1;
	private final ModelPart eye2;
	private final ModelPart eye3;
	private final ModelPart eye4;

	private final ModelPart tongueBase;
	private final ModelPart tongueMiddle;
	private final ModelPart tongueEnd;


	private final float bodyY;
	private final float headY;
	private final Rotation tongueBaseRots;
	private final Rotation tongueMiddleRots;
	private final Rotation tongueEndRots;

	private ModelPart[] eyeStalks = new ModelPart[4];
	private Rotation[] eyeStalkRotations = new Rotation[4];
	private int[] eyeStalkOffsets = new int[4];
	private float[] eyeStalkSpeeds = new float[4];
	private float[] directions = new float[4];

	public SpectatorModel(ModelPart root) {
		this.body = root.getChild("body");
		this.head = root.getChild("head");
		this.lower = body.getChild("lower");
		this.eyeStalk1 = body.getChild("upper").getChild("stalks").getChild("s1");
		this.eyeStalk2 = body.getChild("upper").getChild("stalks").getChild("s2");
		this.eyeStalk3 = body.getChild("upper").getChild("stalks").getChild("s3");
		this.eyeStalk4 = body.getChild("upper").getChild("stalks").getChild("s4");
		eyeStalks[0] = eyeStalk1;
		eyeStalks[1] = eyeStalk2;
		eyeStalks[2] = eyeStalk3;
		eyeStalks[3] = eyeStalk4;

		this.eye1 = eyeStalk1.getChild("s1m").getChild("s1t").getChild("eye1");
		this.eye2 = eyeStalk2.getChild("s2m").getChild("s2t").getChild("eye2");
		this.eye3 = eyeStalk3.getChild("s3m").getChild("s3t").getChild("eye3");
		this.eye4 = eyeStalk4.getChild("s4m").getChild("s4t").getChild("eye4");

		this.tongueBase = lower.getChild("tongue");
		this.tongueMiddle = tongueBase.getChild("tm");
		this.tongueEnd = tongueMiddle.getChild("te");

		tongueBaseRots = new Rotation(tongueBase.xRot, tongueBase.yRot, tongueBase.zRot);
		tongueMiddleRots = new Rotation(tongueMiddle.xRot, tongueMiddle.yRot, tongueMiddle.zRot);
		tongueEndRots = new Rotation(tongueEnd.xRot, tongueEnd.yRot, tongueEnd.zRot);

		Random random = new Random();
		for (int i = 0; i < 4; i++) {
			eyeStalkRotations[i] = new Rotation(eyeStalks[i].xRot, eyeStalks[i].yRot, eyeStalks[i].zRot);
			eyeStalkOffsets[i] = random.nextInt(0, 180);
			eyeStalkSpeeds[i] = random.nextFloat(0.02F, 0.05F);
			directions[i] = random.nextInt() % 2 == 0 ? 1F: -1F;
		}
		bodyY = body.y;
		headY = head.y;
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 16.0F, 0.0F, 0.0873F, 0.0F, 0.0F));

		PartDefinition upper = body.addOrReplaceChild("upper", CubeListBuilder.create().texOffs(0, 0).addBox(-9.0F, -18.0076F, -17.1743F, 18.0F, 12.0F, 18.0F, new CubeDeformation(0.0F))
				.texOffs(51, 40).addBox(-7.0F, -20.0076F, -15.1743F, 14.0F, 2.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.0F, 7.0F));

		PartDefinition stalks = upper.addOrReplaceChild("stalks", CubeListBuilder.create(), PartPose.offset(0.0F, -1.0F, -7.0F));

		PartDefinition s1 = stalks.addOrReplaceChild("s1", CubeListBuilder.create().texOffs(25, 54).addBox(-1.0F, -1.0F, -1.0F, 6.0F, 2.0F, 2.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(9.0F, -14.0F, 6.0F, -0.0873F, -0.3491F, -0.2618F));

		PartDefinition s1m = s1.addOrReplaceChild("s1m", CubeListBuilder.create().texOffs(49, 33).addBox(0.0F, -1.0F, -1.0F, 6.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.25F, 0.0F, 0.5F, 0.0F, 1.1345F, 0.0F));

		PartDefinition s1t = s1m.addOrReplaceChild("s1t", CubeListBuilder.create().texOffs(65, 57).addBox(-1.0F, -1.0F, -7.0F, 2.0F, 2.0F, 7.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(5.25F, 0.0F, 0.25F, 0.0F, -0.7854F, 0.0F));

		PartDefinition eye1 = s1t.addOrReplaceChild("eye1", CubeListBuilder.create().texOffs(0, 38).addBox(-1.5F, -1.5F, -3.0F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(49, 38).addBox(-1.5F, -1.5F, -2.0F, 3.0F, 3.0F, 2.0F, new CubeDeformation(0.2F)), PartPose.offset(0.0F, 0.0F, -7.0F));

		PartDefinition s2 = stalks.addOrReplaceChild("s2", CubeListBuilder.create().texOffs(25, 54).addBox(-7.0F, -1.0F, -1.0F, 6.0F, 2.0F, 2.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(-7.0F, -12.0F, 6.0F, 0.0F, 0.0873F, 0.5672F));
		PartDefinition s2m = s2.addOrReplaceChild("s2m", CubeListBuilder.create().texOffs(49, 33).addBox(-6.0F, -1.0F, -1.0F, 6.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.25F, 0.0F, 0.5F, 0.0F, -0.9599F, 0.0F));
		PartDefinition s2t = s2m.addOrReplaceChild("s2t", CubeListBuilder.create().texOffs(65, 57).addBox(-1.0F, -1.0F, -7.0F, 2.0F, 2.0F, 7.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(-5.25F, 0.0F, 0.25F, 0.0F, 0.829F, 0.0F));
		PartDefinition eye2 = s2t.addOrReplaceChild("eye2", CubeListBuilder.create().texOffs(0, 38).addBox(-1.5F, -1.5F, -3.0F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(49, 38).addBox(-1.5F, -1.5F, -2.0F, 3.0F, 3.0F, 2.0F, new CubeDeformation(0.2F)), PartPose.offset(0.0F, 0.0F, -7.0F));

		PartDefinition s3 = stalks.addOrReplaceChild("s3", CubeListBuilder.create().texOffs(25, 54).addBox(-1.0F, -1.0F, -1.0F, 6.0F, 2.0F, 2.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(7.0F, -8.0F, 2.0F, -0.0873F, -0.6545F, -0.0873F));
		PartDefinition s3m = s3.addOrReplaceChild("s3m", CubeListBuilder.create().texOffs(49, 33).addBox(0.0F, -1.0F, -1.0F, 6.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.25F, 0.0F, 0.5F, 0.0F, 1.1345F, 0.0F));
		PartDefinition s3t = s3m.addOrReplaceChild("s3t", CubeListBuilder.create().texOffs(65, 57).addBox(-1.0F, -1.0F, -7.0F, 2.0F, 2.0F, 7.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(5.25F, 0.0F, 0.25F, 0.0F, -0.7854F, 0.0F));
		PartDefinition eye3 = s3t.addOrReplaceChild("eye3", CubeListBuilder.create().texOffs(0, 38).addBox(-1.5F, -1.5F, -3.0F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(49, 38).addBox(-1.5F, -1.5F, -2.0F, 3.0F, 3.0F, 2.0F, new CubeDeformation(0.2F)), PartPose.offset(0.0F, 0.0F, -7.0F));

		PartDefinition s4 = stalks.addOrReplaceChild("s4", CubeListBuilder.create().texOffs(25, 54).addBox(-7.0F, -1.0F, -1.0F, 6.0F, 2.0F, 2.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(-5.0F, -8.0F, 1.0F, 0.0F, 0.6545F, 0.0F));
		PartDefinition s4m = s4.addOrReplaceChild("s4m", CubeListBuilder.create().texOffs(49, 33).addBox(-6.0F, -1.0F, -1.0F, 6.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.25F, 0.0F, 0.5F, 0.0F, -0.9599F, 0.0F));
		PartDefinition s4t = s4m.addOrReplaceChild("s4t", CubeListBuilder.create().texOffs(65, 57).addBox(-1.0F, -1.0F, -7.0F, 2.0F, 2.0F, 7.0F, new CubeDeformation(0.01F)), PartPose.offsetAndRotation(-5.25F, 0.0F, 0.25F, 0.0F, 0.829F, 0.0F));
		PartDefinition eye4 = s4t.addOrReplaceChild("eye4", CubeListBuilder.create().texOffs(0, 38).addBox(-1.5F, -1.5F, -3.0F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(49, 38).addBox(-1.5F, -1.5F, -2.0F, 3.0F, 3.0F, 2.0F, new CubeDeformation(0.2F)), PartPose.offset(0.0F, 0.0F, -7.0F));

		PartDefinition topTeeth = upper.addOrReplaceChild("topTeeth", CubeListBuilder.create(), PartPose.offsetAndRotation(1.0F, -8.0F, -15.0F, 0.3054F, 0.0F, 0.0F));
		PartDefinition tt1 = topTeeth.addOrReplaceChild("tt1", CubeListBuilder.create(), PartPose.offset(6.0F, 9.0F, 15.0F));
		PartDefinition cube_r1 = tt1.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(11, 0).addBox(-1.4005F, -0.4863F, -15.7654F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -8.0F, 0.0F, 0.0F, 0.0F, 0.7854F));
		PartDefinition tt2 = topTeeth.addOrReplaceChild("tt2", CubeListBuilder.create(), PartPose.offset(3.0F, 9.0F, 15.0F));
		PartDefinition cube_r2 = tt2.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(11, 0).addBox(-1.4005F, -0.4863F, -16.2654F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -8.0F, 0.0F, 0.0F, 0.0F, 0.7854F));
		PartDefinition tt4 = topTeeth.addOrReplaceChild("tt4", CubeListBuilder.create(), PartPose.offset(-2.0F, 9.0F, 15.0F));
		PartDefinition cube_r3 = tt4.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(11, 0).addBox(-1.4005F, -0.4863F, -15.7654F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -8.0F, 0.0F, 0.0F, 0.0F, 0.7854F));
		PartDefinition tt3 = topTeeth.addOrReplaceChild("tt3", CubeListBuilder.create(), PartPose.offset(1.0F, 9.0F, 15.0F));
		PartDefinition cube_r4 = tt3.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(11, 0).addBox(-1.4005F, -0.4863F, -15.7654F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -8.0F, 0.0F, 0.0F, 0.0F, 0.7854F));
		PartDefinition tt5 = topTeeth.addOrReplaceChild("tt5", CubeListBuilder.create(), PartPose.offset(-4.0F, 9.0F, 15.0F));
		PartDefinition cube_r5 = tt5.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(11, 0).addBox(-1.4005F, -0.4863F, -16.2654F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -8.0F, 0.0F, 0.0F, 0.0F, 0.7854F));
		PartDefinition tt6 = topTeeth.addOrReplaceChild("tt6", CubeListBuilder.create(), PartPose.offset(-7.0F, 9.0F, 15.0F));
		PartDefinition cube_r6 = tt6.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(11, 0).addBox(-1.4005F, -0.4863F, -15.7654F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -8.0F, 0.0F, 0.0F, 0.0F, 0.7854F));

		PartDefinition spike1 = upper.addOrReplaceChild("spike1", CubeListBuilder.create(), PartPose.offsetAndRotation(-5.5F, -19.5F, -11.5F, -0.7854F, 0.0F, 0.0F));
		PartDefinition cube_r7 = spike1.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(0, 31).addBox(-1.5F, -1.5F, -1.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.7854F));

		PartDefinition spike2 = upper.addOrReplaceChild("spike2", CubeListBuilder.create(), PartPose.offsetAndRotation(5.5F, -19.5F, -11.5F, -0.7854F, 0.0F, 0.0F));
		PartDefinition cube_r8 = spike2.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(0, 31).addBox(-1.5F, -1.5F, -1.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.7854F));

		PartDefinition spike3 = upper.addOrReplaceChild("spike3", CubeListBuilder.create(), PartPose.offsetAndRotation(-7.5F, -14.5F, -12.5F, 0.0F, 0.7854F, 0.0F));
		PartDefinition cube_r9 = spike3.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(0, 8).addBox(-2.5F, -2.5F, -2.5F, 5.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.7854F));

		PartDefinition spike4 = upper.addOrReplaceChild("spike4", CubeListBuilder.create(), PartPose.offsetAndRotation(7.5F, -14.5F, -12.5F, 0.0F, -0.7854F, 0.0F));
		PartDefinition cube_r10 = spike4.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(0, 8).addBox(-2.5F, -2.5F, -2.5F, 5.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.7854F));

		PartDefinition lower = body.addOrReplaceChild("lower", CubeListBuilder.create().texOffs(0, 31).addBox(-8.0F, -0.0872F, -16.1511F, 16.0F, 6.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -5.0F, 7.0F, 0.48F, 0.0F, 0.0F));
		PartDefinition skin2_r1 = lower.addOrReplaceChild("skin2_r1", CubeListBuilder.create().texOffs(33, 54).addBox(-1.0F, -10.0076F, -5.1743F, 0.0F, 10.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-6.0F, 3.3221F, -7.7705F, -0.48F, 0.0F, 0.0F));
		PartDefinition skin1_r1 = lower.addOrReplaceChild("skin1_r1", CubeListBuilder.create().texOffs(54, 57).addBox(-1.0F, -10.0076F, -6.1743F, 0.0F, 10.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.0F, 3.3221F, -6.7705F, -0.48F, 0.0F, 0.0F));

		PartDefinition teeth = lower.addOrReplaceChild("teeth", CubeListBuilder.create(), PartPose.offsetAndRotation(1.0F, -1.6779F, -13.7705F, -0.3054F, 0.0F, 0.0F));
		PartDefinition bt5 = teeth.addOrReplaceChild("bt5", CubeListBuilder.create(), PartPose.offsetAndRotation(6.3536F, 7.9393F, -3.0F, 0.0F, -0.7854F, 0.0F));
		PartDefinition cube_r11 = bt5.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(0, 54).addBox(-4.5635F, -7.2398F, -12.0469F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.3536F, 1.0607F, 15.0F, 0.0F, 0.0F, 0.7854F));
		PartDefinition bt1 = teeth.addOrReplaceChild("bt1", CubeListBuilder.create(), PartPose.offset(3.0F, 8.0F, 11.0F));
		PartDefinition cube_r12 = bt1.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(0, 54).addBox(-6.3588F, -5.4446F, -11.8236F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.7854F));
		PartDefinition bt2 = teeth.addOrReplaceChild("bt2", CubeListBuilder.create(), PartPose.offset(1.0F, 8.0F, 11.0F));
		PartDefinition cube_r13 = bt2.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(0, 54).addBox(-6.3588F, -5.4446F, -11.9236F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.7854F));

		PartDefinition bt3 = teeth.addOrReplaceChild("bt3", CubeListBuilder.create(), PartPose.offset(-2.0F, 8.0F, 11.0F));

		PartDefinition cube_r14 = bt3.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(0, 54).addBox(-6.3588F, -5.4446F, -11.8236F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.7854F));

		PartDefinition bt4 = teeth.addOrReplaceChild("bt4", CubeListBuilder.create(), PartPose.offset(-4.0F, 8.0F, 11.0F));

		PartDefinition cube_r15 = bt4.addOrReplaceChild("cube_r15", CubeListBuilder.create().texOffs(0, 54).addBox(-6.3588F, -5.4446F, -11.9236F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.7854F));

		PartDefinition bt6 = teeth.addOrReplaceChild("bt6", CubeListBuilder.create(), PartPose.offsetAndRotation(-6.6464F, 7.9393F, -3.0F, 0.0F, 0.7854F, 0.0F));

		PartDefinition cube_r16 = bt6.addOrReplaceChild("cube_r16", CubeListBuilder.create().texOffs(0, 54).addBox(-7.7398F, -4.0635F, -13.4611F, 3.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.3536F, 1.0607F, 15.0F, 0.0F, 0.0F, 0.7854F));

		PartDefinition tongue = lower.addOrReplaceChild("tongue", CubeListBuilder.create().texOffs(55, 0).addBox(-3.0F, -0.7027F, -9.3161F, 6.0F, 1.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0721F, -5.7705F, -0.3927F, 0.0F, 0.0F));
		PartDefinition tm = tongue.addOrReplaceChild("tm", CubeListBuilder.create().texOffs(63, 21).addBox(-2.0F, -0.75F, -10.0F, 4.0F, 1.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0473F, -9.3161F, 0.9163F, 0.0F, 0.0F));
		PartDefinition te = tm.addOrReplaceChild("te", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -1.0F, -6.0F, 2.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -10.0F, 0.1745F, 0.0F, 0.0F));

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 54).addBox(-4.0F, -3.0F, -4.0F, 8.0F, 6.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 5.0F, -8.0F, 0.0873F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		setRestrictedEyeRotations(netHeadYaw, headPitch, -20, 20, -20, 20);

		// reset eye stalk rotations before bobbing
//		resetTopEyeStalk(eyeStalk1, -0.349066F, -0.261799F);
//		resetTopEyeStalk(eyeStalk2, -0.043633F, 0.261799F); // -2.5, 15
//		resetSideEyeStalk(eyeStalk3, (0.349066F), -0.261799F); // 20, -15
//		resetSideEyeStalk(eyeStalk4, -0.087266F, 0.567232F); // -5, 32.5
		resetSideEyeStalk(eyeStalk1, eyeStalkRotations[0].y(), eyeStalkRotations[0].z());
		resetSideEyeStalk(eyeStalk2, eyeStalkRotations[1].y(), eyeStalkRotations[1].z());
		resetSideEyeStalk(eyeStalk3, eyeStalkRotations[2].y(), eyeStalkRotations[2].z());
		resetSideEyeStalk(eyeStalk4, eyeStalkRotations[3].y(), eyeStalkRotations[3].z());

		bobSideEyeStalk(eyeStalk1, eye1, ageInTicks, 0.3F, directions[0], eyeStalkOffsets[0], eyeStalkSpeeds[0], directions[3]);
		bobSideEyeStalk(eyeStalk2, eye2, ageInTicks, 0.3F, directions[1], eyeStalkOffsets[1], eyeStalkSpeeds[1], directions[2]);
		bobSideEyeStalk(eyeStalk3, eye3, ageInTicks, 0.3F, directions[2], eyeStalkOffsets[2], eyeStalkSpeeds[2], directions[1]);
		bobSideEyeStalk(eyeStalk4, eye4, ageInTicks, 0.3F, directions[3], eyeStalkOffsets[3], eyeStalkSpeeds[3], directions[0]);

		// reset mouth
		this.lower.xRot = 0.479966F;

		// bob mouth
		bobMouthPart(lower, ageInTicks, 0.15F, -0.2181662F);

		// reset tongue
		resetRotations(tongueBase, tongueBaseRots);
		resetRotations(tongueMiddle, tongueMiddleRots);
		resetRotations(tongueEnd, tongueEndRots);

		// bob tongue
		bobTongue(ageInTicks, 0.15F, 1.0F);

		// bob entire body
		body.y = bodyY + (Mth.cos(ageInTicks * 0.15F) * 0.5F + 0.05F);
		head.y = headY + (Mth.cos(ageInTicks * 0.15F) * 0.5F + 0.05F);
	}

	private void bobTongue(float age, float speed, float direction) {
		tongueBase.yRot = Mth.cos(age * (speed / 2)) * 0.785398F + 0.05F; //45o
		tongueMiddle.yRot = (Mth.cos(age * (speed / 3)) * 0.261799F + 0.05F); //15o
		tongueEnd.xRot = Mth.sin(age * (speed / 2)) * 0.261799F + 0.05F; //15o
	}

	private void resetRotations(ModelPart part, Rotation rots) {
		part.xRot = rots.x();
		part.yRot = rots.y();
		part.zRot = rots.z();
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		body.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		head.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart getBody() {
		return this.body;
	}

	@Override
	public ModelPart getEye() {
		return this.head;
	}
}