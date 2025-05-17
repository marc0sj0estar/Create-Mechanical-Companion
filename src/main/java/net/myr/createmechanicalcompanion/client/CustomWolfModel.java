package net.myr.createmechanicalcompanion.client;

import net.myr.createmechanicalcompanion.item.ModItems;
import org.jetbrains.annotations.NotNull;

import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;
import net.myr.createmechanicalcompanion.entity.CustomWolf;

public class CustomWolfModel extends HierarchicalModel<CustomWolf> {

    private final ModelPart head;

    private final ModelPart body;
    private final ModelPart rightHindLeg;
    private final ModelPart leftHindLeg;
    private final ModelPart rightFrontLeg;
    private final ModelPart leftFrontLeg;
    private final ModelPart tail;

    private final ModelPart upperBody;
    private static final int LEG_SIZE = 8;
    private final ModelPart root;

    private final ModelPart reinforcedPlates0;
    private final ModelPart reinforcedPlates1;
    private final ModelPart reinforcedPlates2;
    private final ModelPart reinforcedPlates3;
    private final ModelPart netheritePlates0;
    private final ModelPart netheritePlates1;
    private final ModelPart netheritePlates2;
    private final ModelPart netheritePlates3;
    private final ModelPart mountedCrossbow;
    private final ModelPart teslaTail;
    private final ModelPart smeltingFangs;

    private final ModelPart boosterRocket;
    private final ModelPart quantumDrive;

    private final ModelPart mountedLight;
    private final ModelPart radar;
    private final ModelPart radarSpinner;
    private final ModelPart radarSpinner2;




    public CustomWolfModel(ModelPart root) {
        this.root = root;
        this.head = root.getChild("head");
        this.body = root.getChild("body");
        this.upperBody = root.getChild("upperBody");
        this.rightHindLeg = root.getChild("leg0");
        this.leftHindLeg = root.getChild("leg1");
        this.rightFrontLeg = root.getChild("leg2");
        this.leftFrontLeg = root.getChild("leg3");
        this.tail = root.getChild("tail");

        // Defensive modules
        this.reinforcedPlates0 = rightHindLeg.getChild("reinforcedPlate0");
        this.reinforcedPlates1 = leftHindLeg.getChild("reinforcedPlate1");
        this.reinforcedPlates2 = rightFrontLeg.getChild("reinforcedPlate2");
        this.reinforcedPlates3 = leftFrontLeg.getChild("reinforcedPlate3");
        this.netheritePlates0 = rightHindLeg.getChild("netheritePlate0");
        this.netheritePlates1 = leftHindLeg.getChild("netheritePlate1");
        this.netheritePlates2 = rightFrontLeg.getChild("netheritePlate2");
        this.netheritePlates3 = leftFrontLeg.getChild("netheritePlate3");

        // Attack modules
        this.mountedCrossbow = body.getChild("crossbow");
        this.teslaTail = tail.getChild("teslaTail");
        this.smeltingFangs = head.getChild("smeltingFangs");

        // Movement modules
        this.boosterRocket = body.getChild("boosterRocket");
        this.quantumDrive = upperBody.getChild("quantumDrive");

        // Utility modules
        this.mountedLight = root.getChild("torch");
        this.radar = head.getChild("radar");
        this.radarSpinner = radar.getChild("radarSpinner");
        this.radarSpinner2 = radar.getChild("radarSpinner2");


    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();


        PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 22).mirror().addBox(-3.0F, -3.0F, -2.0F, 6.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(20, 29).addBox(-3.0F, -5.0F, 0.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(20, 29).addBox(1.0F, -5.0F, 0.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(18, 17).addBox(-2.0F, -0.0156F, -5.0F, 4.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.0F, 13.5F, -7.0F));

        PartDefinition cube_r1 = head.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(26, 10).addBox(-1.5F, -2.0F, -0.5F, 3.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 4.0F, -4.0F, 0.1745F, 0.0F, 0.0F));

        PartDefinition smeltingFangs = head.addOrReplaceChild("smeltingFangs", CubeListBuilder.create(), PartPose.offset(-1.3F, 2.4962F, -3.6082F));

        PartDefinition fangs2_r1 = smeltingFangs.addOrReplaceChild("fangs2_r1", CubeListBuilder.create().texOffs(40, 60).addBox(-0.5F, -1.5F, -1.1918F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.1309F, 0.0F, 0.0F));

        PartDefinition fangs1_r1 = smeltingFangs.addOrReplaceChild("fangs1_r1", CubeListBuilder.create().texOffs(40, 60).addBox(-0.5F, -1.5F, -0.1918F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.6F, 0.0F, -1.0F, 0.1309F, 0.0F, 0.0F));

        PartDefinition visor = head.addOrReplaceChild("visor", CubeListBuilder.create().texOffs(0, 14).addBox(-3.5F, -2.0F, 0.0F, 7.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -0.5F, -3.0F));

        PartDefinition radar = head.addOrReplaceChild("radar", CubeListBuilder.create().texOffs(40, 52).addBox(-0.5F, -7.0F, 0.0F, 1.0F, 7.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(48, 54).addBox(-1.0F, -1.5F, -0.5F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.0F, 0.0F, -1.0F));

        PartDefinition radarSpinner = radar.addOrReplaceChild("radarSpinner", CubeListBuilder.create().texOffs(44, 54).addBox(-2.0F, -0.5F, -1.0F, 4.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -7.5F, 0.5F));

        PartDefinition radarSpinner2 = radar.addOrReplaceChild("radarSpinner2", CubeListBuilder.create().texOffs(44, 54).addBox(-2.0F, -0.5F, -1.0F, 4.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -6.0F, 0.5F));

        PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 14.0F, 2.0F, 1.5708F, 0.0F, 0.0F));

        PartDefinition body_r1 = body.addOrReplaceChild("body_r1", CubeListBuilder.create().texOffs(26, 17).addBox(-4.0F, -12.0F, -1.0F, 6.0F, 9.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 10.0F, -3.0F, -0.0873F, 0.0F, 0.0F));

        PartDefinition crossbow = body.addOrReplaceChild("crossbow", CubeListBuilder.create().texOffs(16, 58).addBox(-0.5F, -0.5F, -6.0F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(16, 50).addBox(-1.5F, -5.5F, -1.0F, 3.0F, 11.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(25, 50).addBox(-2.5F, 3.5F, 0.0F, 5.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.0F, 2.5F, 8.0F));

        PartDefinition cube_r2 = crossbow.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(16, 39).mirror().addBox(-0.5F, -4.5F, -1.0F, 1.0F, 9.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(2.0F, 0.0F, 0.5F, 0.0F, 0.0F, -0.2182F));

        PartDefinition cube_r3 = crossbow.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(22, 39).addBox(-0.5F, -4.5F, -1.0F, 1.0F, 9.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, 0.0F, 0.5F, 0.0F, 0.0F, 0.2182F));

        PartDefinition arrow = crossbow.addOrReplaceChild("arrow", CubeListBuilder.create().texOffs(8, 60).addBox(0.5F, -0.5F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.0F, -7.0F, 0.5F));

        PartDefinition cube_r4 = arrow.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(28, 47).addBox(-0.5F, -0.5F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.7854F));

        PartDefinition cube_r5 = arrow.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(28, 47).addBox(-1.5F, -0.5F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.7854F));

        PartDefinition boosterRocket = body.addOrReplaceChild("boosterRocket", CubeListBuilder.create().texOffs(52, 0).addBox(4.5F, -4.5F, 0.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(56, 0).addBox(5.0F, -3.5F, 1.0F, 2.0F, 9.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(52, 0).addBox(4.5F, 4.5F, 0.5F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(54, 0).addBox(5.0F, -0.5F, -2.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(52, 2).addBox(-1.5F, 4.5F, 0.5F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(53, 0).addBox(-1.0F, -3.5F, 1.0F, 2.0F, 9.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(52, 4).addBox(-1.5F, -4.5F, 0.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(55, 0).addBox(0.0F, -0.5F, -2.0F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.0F, 5.5F, 3.0F));

        PartDefinition upperBody = partdefinition.addOrReplaceChild("upperBody", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -3.0F, -3.0F, 8.0F, 7.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 14.0F, -3.0F, 1.5708F, 0.0F, 0.0F));

        PartDefinition quantumDrive = upperBody.addOrReplaceChild("quantumDrive", CubeListBuilder.create(), PartPose.offset(0.0F, 1.485F, 4.1556F));

        PartDefinition quantumDrive2_r1 = quantumDrive.addOrReplaceChild("quantumDrive2_r1", CubeListBuilder.create().texOffs(37, 35).addBox(-3.0F, -3.985F, -0.9556F, 6.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -0.0873F, 0.0F, 0.0F));

        PartDefinition quantumDrive1_r1 = quantumDrive.addOrReplaceChild("quantumDrive1_r1", CubeListBuilder.create().texOffs(52, 36).addBox(-3.5F, -10.0F, -1.0F, 5.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 6.515F, -0.1556F, -0.0873F, 0.0F, 0.0F));

        PartDefinition leg0 = partdefinition.addOrReplaceChild("leg0", CubeListBuilder.create().texOffs(50, 26).addBox(-2.0F, 7.0F, -2.0F, 3.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.5F, 15.0F, 7.0F));

        PartDefinition cube_r6 = leg0.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(52, 11).addBox(-2.0F, -3.0F, -0.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 6.0F, 1.0F, 0.0436F, 0.0F, 0.0F));

        PartDefinition cube_r7 = leg0.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(50, 17).addBox(-1.0F, -4.0F, -2.0F, 3.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 1.0F, 0.0F, 0.1745F, 0.0F, 0.0F));

        PartDefinition leg1_r1 = leg0.addOrReplaceChild("leg1_r1", CubeListBuilder.create().texOffs(37, 44).addBox(0.0F, -4.0F, 6.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.5F, 8.0F, -3.0F, 0.6545F, 0.0F, 0.0F));

        PartDefinition leg1_r2 = leg0.addOrReplaceChild("leg1_r2", CubeListBuilder.create().texOffs(37, 44).addBox(0.0F, -5.0F, 6.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.5F, 5.0F, -6.0F, -0.5236F, 0.0F, 0.0F));

        PartDefinition reinforcedPlate0 = leg0.addOrReplaceChild("reinforcedPlate0", CubeListBuilder.create(), PartPose.offset(-2.0F, 1.0F, 0.0F));

        PartDefinition cube_r8 = reinforcedPlate0.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(0, 51).addBox(-1.0F, -5.0F, -2.5F, 1.0F, 7.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.1705F, -0.0376F, 0.215F));

        PartDefinition netheritePlate0 = leg0.addOrReplaceChild("netheritePlate0", CubeListBuilder.create(), PartPose.offset(-2.0F, 1.0F, 0.0F));

        PartDefinition cube_r9 = netheritePlate0.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(0, 38).addBox(-1.0F, -5.0F, -2.5F, 1.0F, 7.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.1705F, -0.0376F, 0.215F));

        PartDefinition leg1 = partdefinition.addOrReplaceChild("leg1", CubeListBuilder.create().texOffs(50, 26).addBox(-3.0F, 7.0F, -2.0F, 3.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(3.5F, 15.0F, 7.0F));

        PartDefinition cube_r10 = leg1.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(52, 11).addBox(-2.0F, -3.0F, -0.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 6.0F, 1.0F, 0.0436F, 0.0F, 0.0F));

        PartDefinition cube_r11 = leg1.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(50, 17).addBox(-2.0F, -4.0F, -2.0F, 3.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 1.0F, 0.0F, 0.1745F, 0.0F, 0.0F));

        PartDefinition leg1_r3 = leg1.addOrReplaceChild("leg1_r3", CubeListBuilder.create().texOffs(37, 43).addBox(0.0F, -4.0F, 6.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.5F, 8.0F, -3.0F, 0.6545F, 0.0F, 0.0F));

        PartDefinition leg1_r4 = leg1.addOrReplaceChild("leg1_r4", CubeListBuilder.create().texOffs(37, 42).addBox(0.0F, -5.0F, 6.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.5F, 5.0F, -6.0F, -0.5236F, 0.0F, 0.0F));

        PartDefinition reinforcedPlate1 = leg1.addOrReplaceChild("reinforcedPlate1", CubeListBuilder.create(), PartPose.offset(-1.0F, 1.0F, 0.0F));

        PartDefinition cube_r12 = reinforcedPlate1.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(0, 51).addBox(1.0F, -5.0F, -2.5F, 1.0F, 7.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.1705F, 0.0376F, -0.215F));

        PartDefinition netheritePlate1 = leg1.addOrReplaceChild("netheritePlate1", CubeListBuilder.create(), PartPose.offset(-1.0F, 1.0F, 0.0F));

        PartDefinition netheritePlate1_r1 = netheritePlate1.addOrReplaceChild("netheritePlate1_r1", CubeListBuilder.create().texOffs(0, 38).addBox(1.0F, -5.0F, -2.5F, 1.0F, 7.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.1705F, 0.0376F, -0.215F));

        PartDefinition leg2 = partdefinition.addOrReplaceChild("leg2", CubeListBuilder.create().texOffs(50, 26).addBox(-4.0F, 8.0F, -4.5F, 3.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.5F, 14.0F, -3.0F));

        PartDefinition cube_r13 = leg2.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(30, 0).addBox(-2.1F, -5.2F, -1.0F, 4.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.5F, 2.0F, -1.0F, 0.1309F, 0.0F, 0.0F));

        PartDefinition cube_r14 = leg2.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(52, 11).addBox(-4.0F, -3.0F, -0.1F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 5.0F, -1.0F, -0.0873F, 0.0F, 0.0F));

        PartDefinition cube_r15 = leg2.addOrReplaceChild("cube_r15", CubeListBuilder.create().texOffs(37, 45).addBox(-1.5F, -3.0F, -1.0F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, 3.0F, 0.0F, 0.0873F, 0.0F, 0.0F));

        PartDefinition leg3_r1 = leg2.addOrReplaceChild("leg3_r1", CubeListBuilder.create().texOffs(37, 42).addBox(1.0F, -8.0F, -5.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-4.5F, 12.0F, 1.0F, -0.4363F, 0.0F, 0.0F));

        PartDefinition reinforcedPlate2 = leg2.addOrReplaceChild("reinforcedPlate2", CubeListBuilder.create(), PartPose.offset(-1.5F, 2.0F, -1.0F));

        PartDefinition cube_r16 = reinforcedPlate2.addOrReplaceChild("cube_r16", CubeListBuilder.create().texOffs(0, 51).addBox(-2.1F, -6.2F, -1.0F, 1.0F, 7.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 0.0F, -1.0F, 0.0853F, -0.0227F, 0.1731F));

        PartDefinition netheritePlate2 = leg2.addOrReplaceChild("netheritePlate2", CubeListBuilder.create(), PartPose.offset(-2.5F, 2.0F, -2.0F));

        PartDefinition cube_r17 = netheritePlate2.addOrReplaceChild("cube_r17", CubeListBuilder.create().texOffs(0, 38).addBox(-2.1F, -6.2F, -1.0F, 1.0F, 7.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0853F, -0.0227F, 0.1731F));

        PartDefinition leg3 = partdefinition.addOrReplaceChild("leg3", CubeListBuilder.create().texOffs(50, 26).addBox(-1.0F, 8.0F, -4.5F, 3.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(2.5F, 14.0F, -3.0F));

        PartDefinition cube_r18 = leg3.addOrReplaceChild("cube_r18", CubeListBuilder.create().texOffs(30, 0).addBox(-3.9F, -5.2F, -1.0F, 4.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.5F, 2.0F, -1.0F, 0.1309F, 0.0F, 0.0F));

        PartDefinition cube_r19 = leg3.addOrReplaceChild("cube_r19", CubeListBuilder.create().texOffs(52, 11).addBox(-4.0F, -3.0F, -0.1F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, 5.0F, -1.0F, -0.0873F, 0.0F, 0.0F));

        PartDefinition cube_r20 = leg3.addOrReplaceChild("cube_r20", CubeListBuilder.create().texOffs(37, 45).addBox(-1.5F, -3.0F, -1.0F, 2.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 3.0F, 0.0F, 0.0873F, 0.0F, 0.0F));

        PartDefinition leg3_r2 = leg3.addOrReplaceChild("leg3_r2", CubeListBuilder.create().texOffs(37, 42).addBox(1.0F, -8.0F, -4.0F, 2.0F, 7.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.5F, 12.0F, 0.0F, -0.4363F, 0.0F, 0.0F));

        PartDefinition reinforcedPlate3 = leg3.addOrReplaceChild("reinforcedPlate3", CubeListBuilder.create(), PartPose.offset(1.5F, 2.0F, -1.0F));

        PartDefinition cube_r21 = reinforcedPlate3.addOrReplaceChild("cube_r21", CubeListBuilder.create().texOffs(0, 51).addBox(-0.9F, -6.2F, -2.0F, 1.0F, 7.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 0.0F, 0.0F, 0.0853F, 0.0227F, -0.1731F));

        PartDefinition netheritePlate3 = leg3.addOrReplaceChild("netheritePlate3", CubeListBuilder.create(), PartPose.offset(2.5F, 2.0F, -1.0F));

        PartDefinition cube_r22 = netheritePlate3.addOrReplaceChild("cube_r22", CubeListBuilder.create().texOffs(0, 38).addBox(-0.9F, -6.2F, -2.0F, 1.0F, 7.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0853F, 0.0227F, -0.1731F));

        PartDefinition tail = partdefinition.addOrReplaceChild("tail", CubeListBuilder.create().texOffs(44, 10).addBox(-1.0F, -0.5F, -1.0F, 2.0F, 9.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.0F, 14.4F, 8.3F));

        PartDefinition teslaTail = tail.addOrReplaceChild("teslaTail", CubeListBuilder.create().texOffs(28, 60).addBox(-2.5F, -9.1F, 5.8F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(28, 60).addBox(-2.5F, -7.1F, 5.8F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(28, 55).addBox(-2.5F, -5.1F, 5.8F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, 13.6F, -7.3F));

        PartDefinition torch = partdefinition.addOrReplaceChild("torch", CubeListBuilder.create(), PartPose.offset(-5.0F, 16.0F, 2.0F));

        PartDefinition torch1 = torch.addOrReplaceChild("torch1", CubeListBuilder.create().texOffs(56, 52).addBox(-1.0F, -11.0F, -1.0F, 2.0F, 10.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(44, 60).addBox(-1.5F, -5.0F, -1.5F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(44, 60).addBox(-1.5F, -1.0F, -1.5F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 1.0F, 0.0F));

        PartDefinition torch2 = torch.addOrReplaceChild("torch2", CubeListBuilder.create().texOffs(56, 52).addBox(-1.0F, -10.0F, -1.0F, 2.0F, 10.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(44, 60).addBox(-1.5F, -4.0F, -1.5F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(44, 60).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(8.0F, 0.0F, 0.0F));



























        return LayerDefinition.create(meshdefinition, 64, 64);


    }

    @Override
    public void setupAnim(CustomWolf entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.head.yRot = netHeadYaw * ((float)Math.PI / 180F);
        this.head.xRot = headPitch * ((float)Math.PI / 180F);
        this.body.xRot = ((float)Math.PI / 2F);
        this.rightHindLeg.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.leftHindLeg.xRot = Mth.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
        this.rightFrontLeg.xRot = Mth.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4F * limbSwingAmount;
        this.leftFrontLeg.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;

        float healthPercentage = entity.getHealth() / entity.getMaxHealth();
        float tailAngle = healthPercentage * 70.0F;
        tail.xRot = (float) Math.toRadians(tailAngle);


        boolean hasReinforcedPlates = entity.isModuleEquipped(ModItems.REINFORCED_PLATES.get());

        this.reinforcedPlates0.visible = hasReinforcedPlates;
        this.reinforcedPlates1.visible = hasReinforcedPlates;
        this.reinforcedPlates2.visible = hasReinforcedPlates;
        this.reinforcedPlates3.visible = hasReinforcedPlates;

        boolean hasNetheritePlates = entity.isModuleEquipped(ModItems.NETHERITE_PLATES.get());

        this.netheritePlates0.visible = hasNetheritePlates;
        this.netheritePlates1.visible = hasNetheritePlates;
        this.netheritePlates2.visible = hasNetheritePlates;
        this.netheritePlates3.visible = hasNetheritePlates;

        this.mountedLight.visible = entity.isModuleEquipped(ModItems.MOUNTED_LIGHT.get());
        this.teslaTail.visible = entity.isModuleEquipped(ModItems.TESLA_TAIL.get());
        this.radar.visible = entity.isModuleEquipped(ModItems.MOB_RADAR.get());
        this.quantumDrive.visible = entity.isModuleEquipped(ModItems.QUANTUM_DRIVE.get());
        this.smeltingFangs.visible = entity.isModuleEquipped(ModItems.SMELTING_FANGS.get());
        this.boosterRocket.visible = entity.isModuleEquipped(ModItems.BOOSTER_ROCKET.get());
        this.mountedCrossbow.visible = entity.isModuleEquipped(ModItems.MOUNTED_CROSSBOW.get());

        this.radarSpinner.yRot = this.radarSpinner.yRot + 0.01f;
        this.radarSpinner2.yRot = this.radarSpinner2.yRot - 0.01f;
    }



    @Override
    public @NotNull ModelPart root() {
        return this.root;
    }
}