package net.myr.createmechanicalcompanion;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.PaintingRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.decoration.Painting;

public class BlueprintPaintingRenderer extends PaintingRenderer {

    ResourceLocation back_texture = new ResourceLocation(CreateMechanicalCompanion.MOD_ID, "textures/item/blueprint_back.png");


    public BlueprintPaintingRenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
    }


}
