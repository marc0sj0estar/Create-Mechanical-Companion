package net.myr.createmechanicalcompanion.client;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.VindicatorRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Vindicator;
import net.myr.createmechanicalcompanion.CreateMechanicalCompanion;

public class IllagerEngineerRenderer extends VindicatorRenderer {

    private static final ResourceLocation ILLAGER_ENGINEER_TEXTURE = ResourceLocation.fromNamespaceAndPath(CreateMechanicalCompanion.MOD_ID, "textures/entity/illager_engineer.png");

    public IllagerEngineerRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(Vindicator pEntity) {
        return ILLAGER_ENGINEER_TEXTURE;
    }
}
