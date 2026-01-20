package net.myr.createmechanicalcompanion.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.myr.createmechanicalcompanion.CreateMechanicalCompanion;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;

public class MechanicalWolfLinkRenderer implements ICurioRenderer {

    private static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(CreateMechanicalCompanion.MOD_ID, "textures/item/mechanical_wolf_link_model.png");
    private final HumanoidModel<LivingEntity> model;

    public MechanicalWolfLinkRenderer(ModelPart part) {
        this.model = new HumanoidModel<>(part);
    }

    @Override
    public <T extends LivingEntity, M extends EntityModel<T>> void render(
            ItemStack stack,
            SlotContext slotContext,
            PoseStack poseStack,
            RenderLayerParent<T, M> renderLayerParent,
            MultiBufferSource buffer,
            int light,
            float limbSwing,
            float limbSwingAmount,
            float partialTicks,
            float ageInTicks,
            float netHeadYaw,
            float headPitch) {

        LivingEntity entity = slotContext.entity();
        
        poseStack.pushPose();
        
        model.head.visible = true;
        model.hat.visible = false;
        model.body.visible = false;
        model.rightArm.visible = false;
        model.leftArm.visible = false;
        model.rightLeg.visible = false;
        model.leftLeg.visible = false;

        model.young = entity.isBaby();
        model.crouching = entity.isCrouching();
        model.riding = entity.isPassenger();
        
        // Only copy head if the model supports it
        M parentModel = renderLayerParent.getModel();
        if (parentModel instanceof HumanoidModel<?> humanoidModel) {
            model.head.copyFrom(humanoidModel.head);
        }

        VertexConsumer vertexConsumer = ItemRenderer.getArmorFoilBuffer(buffer, RenderType.armorCutoutNoCull(TEXTURE), stack.hasFoil());
        model.head.render(poseStack, vertexConsumer, light, OverlayTexture.NO_OVERLAY);

        poseStack.popPose();
    }
}
