package net.myr.createmechanicalcompanion.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;

public class MechanicalWolfLinkRenderer implements ICurioRenderer {

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

        M parentModel = renderLayerParent.getModel();
        if (!(parentModel instanceof HumanoidModel<?> humanoidModel)) {
            return;
        }

        poseStack.pushPose();
        ICurioRenderer.followHeadRotations(slotContext.entity(), humanoidModel.head);
        humanoidModel.head.translateAndRotate(poseStack);

        Minecraft.getInstance().getItemRenderer().renderStatic(
                slotContext.entity(),
                stack,
                ItemDisplayContext.HEAD,
                false,
                poseStack,
                buffer,
                slotContext.entity().level(),
                light,
                net.minecraft.client.renderer.texture.OverlayTexture.NO_OVERLAY,
                slotContext.entity().getId()
        );

        poseStack.popPose();
    }
}