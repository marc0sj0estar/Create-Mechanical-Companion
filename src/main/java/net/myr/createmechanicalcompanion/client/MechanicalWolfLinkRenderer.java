package net.myr.createmechanicalcompanion.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;

public class MechanicalWolfLinkRenderer implements ICurioRenderer {

    private final HumanoidModel model;

    public MechanicalWolfLinkRenderer(ModelPart model) {
        this.model = new HumanoidModel<>(model);
    }

    @Override
    public <T extends LivingEntity, M extends EntityModel<T>> void render(ItemStack itemStack, SlotContext slotContext, PoseStack poseStack, RenderLayerParent<T, M> renderLayerParent, MultiBufferSource multiBufferSource, int light, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        model.setupAnim(slotContext.entity(), limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        model.prepareMobModel(slotContext.entity(), limbSwing, limbSwingAmount, partialTicks);
        ICurioRenderer.followHeadRotations(slotContext.entity(), model.head);

        poseStack.pushPose();
        poseStack.translate(model.head.x / 16.0, model.head.y / 16.0, model.head.z / 16.0);
        poseStack.mulPose(Axis.YP.rotation(model.head.yRot));
        poseStack.mulPose(Axis.XP.rotation(model.head.xRot));
        poseStack.translate(0, -0.25, 0);
        poseStack.mulPose(Axis.ZP.rotationDegrees(180.0f));
        poseStack.scale(0.625f, 0.625f, 0.625f);

        if(!slotContext.entity().getItemBySlot(EquipmentSlot.HEAD).isEmpty()) {
            poseStack.translate(0.1, 0, 0);
        }

        Minecraft mc = Minecraft.getInstance();
        mc.getItemRenderer()
                .renderStatic(itemStack, ItemDisplayContext.HEAD, light, OverlayTexture.NO_OVERLAY, poseStack,
                        multiBufferSource, mc.level, 0);
        poseStack.popPose();
    }

    public static MeshDefinition mesh() {
        CubeListBuilder builder = new CubeListBuilder();
        MeshDefinition mesh = HumanoidModel.createMesh(CubeDeformation.NONE, 0);
        mesh.getRoot().addOrReplaceChild("head", builder, PartPose.ZERO);
        return mesh;
    }
}
