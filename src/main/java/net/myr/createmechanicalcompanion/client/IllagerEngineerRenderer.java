package net.myr.createmechanicalcompanion.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.IllagerModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.VindicatorRenderer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Vindicator;
import net.myr.createmechanicalcompanion.CreateMechanicalCompanion;
import net.myr.createmechanicalcompanion.entity.IllagerEngineer;

public class IllagerEngineerRenderer extends VindicatorRenderer {

    private static final ResourceLocation ILLAGER_ENGINEER_TEXTURE = new ResourceLocation(CreateMechanicalCompanion.MOD_ID, "textures/entity/illager_engineer.png");

    public IllagerEngineerRenderer(EntityRendererProvider.Context context) {
        super(context);
    }


    @Override
    public ResourceLocation getTextureLocation(Vindicator pEntity) {
        return ILLAGER_ENGINEER_TEXTURE;
    }
}
