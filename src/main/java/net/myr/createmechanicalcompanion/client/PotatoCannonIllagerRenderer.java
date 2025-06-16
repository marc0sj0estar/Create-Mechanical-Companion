package net.myr.createmechanicalcompanion.client;

import net.minecraft.client.model.IllagerModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.IllagerRenderer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Pillager;
import net.myr.createmechanicalcompanion.CreateMechanicalCompanion;
import net.myr.createmechanicalcompanion.entity.PotatoCannonIllager;
import org.jetbrains.annotations.NotNull;

public class PotatoCannonIllagerRenderer extends MobRenderer<PotatoCannonIllager, IllagerModel<PotatoCannonIllager>> {
    private static final ResourceLocation POTATO_CANNON_ILLAGER_TEXTURE = new ResourceLocation(CreateMechanicalCompanion.MOD_ID, "textures/entity/potato_cannon_illager.png");

    public PotatoCannonIllagerRenderer(EntityRendererProvider.Context context) {
        super(context, new IllagerModel<>(context.bakeLayer(ModelLayers.PILLAGER)), 0.5f);
        this.addLayer(new ItemInHandLayer<>(this, context.getItemInHandRenderer()));
    }

    @Override
    public ResourceLocation getTextureLocation(PotatoCannonIllager entity) {
        return POTATO_CANNON_ILLAGER_TEXTURE;
    }
}
