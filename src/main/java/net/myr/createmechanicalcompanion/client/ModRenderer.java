package net.myr.createmechanicalcompanion.client;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.myr.createmechanicalcompanion.CreateMechanicalCompanion;
import net.myr.createmechanicalcompanion.entity.ModEntity;

@EventBusSubscriber(modid = CreateMechanicalCompanion.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModRenderer {

    @SubscribeEvent
    public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntity.CUSTOM_WOLF.get(), WolfRenderer::new);
    }
}
