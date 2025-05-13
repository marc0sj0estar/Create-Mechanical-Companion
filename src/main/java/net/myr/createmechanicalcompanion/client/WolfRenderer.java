package net.myr.createmechanicalcompanion.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.myr.createmechanicalcompanion.CreateMechanicalCompanion;
import net.myr.createmechanicalcompanion.entity.CustomWolf;
import org.jetbrains.annotations.NotNull;

public class WolfRenderer extends MobRenderer<CustomWolf, CustomWolfModel> {

    private static final ResourceLocation WOLF_TEXTURE = new ResourceLocation(CreateMechanicalCompanion.MOD_ID, "textures/entity/wolf.png");

    public WolfRenderer(EntityRendererProvider.Context context) {
        super(context, new CustomWolfModel(context.bakeLayer(ModModelLayers.CUSTOM_WOLF)), 0.5f);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull CustomWolf entity) {
        return WOLF_TEXTURE;
    }

    @Override
    public void render(CustomWolf pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight) {
        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }
}
