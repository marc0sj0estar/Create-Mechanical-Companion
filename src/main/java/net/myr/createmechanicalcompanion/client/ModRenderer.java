package net.myr.createmechanicalcompanion.client;


import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.myr.createmechanicalcompanion.BlueprintPaintingRenderer;
import net.myr.createmechanicalcompanion.CreateMechanicalCompanion;
import net.myr.createmechanicalcompanion.entity.ModEntity;

@Mod.EventBusSubscriber(modid = CreateMechanicalCompanion.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModRenderer {

    @SubscribeEvent
    public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntity.CUSTOM_WOLF.get(), WolfRenderer::new);
    }
}