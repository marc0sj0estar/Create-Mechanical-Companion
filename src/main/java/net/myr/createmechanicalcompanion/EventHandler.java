package net.myr.createmechanicalcompanion;

import net.minecraft.client.renderer.entity.PaintingRenderer;
import net.minecraft.core.Direction;
import net.myr.createmechanicalcompanion.blocks.ModBlockEntities;
import net.myr.createmechanicalcompanion.blocks.TestBlockEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.myr.createmechanicalcompanion.client.IllagerEngineerRenderer;
import net.myr.createmechanicalcompanion.client.PotatoCannonIllagerRenderer;
import net.myr.createmechanicalcompanion.client.WolfRenderer;
import net.myr.createmechanicalcompanion.entity.CustomWolf;
import net.myr.createmechanicalcompanion.entity.IllagerEngineer;
import net.myr.createmechanicalcompanion.entity.ModEntity;

public class EventHandler {

    @SubscribeEvent
    public static void onEntityAttributeCreation(EntityAttributeCreationEvent event) {
        event.put(ModEntity.CUSTOM_WOLF.get(), CustomWolf.createAttributes().build());
        event.put(ModEntity.ILLAGER_ENGINEER.get(), IllagerEngineer.createAttributes().build());
        event.put(ModEntity.POTATO_CANNON_ILLAGER.get(), IllagerEngineer.createAttributes().build());
    }

    @SubscribeEvent
    public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntity.CUSTOM_WOLF.get(), WolfRenderer::new);
        event.registerEntityRenderer(ModEntity.ILLAGER_ENGINEER.get(), IllagerEngineerRenderer::new);
        event.registerEntityRenderer(ModEntity.POTATO_CANNON_ILLAGER.get(), PotatoCannonIllagerRenderer::new);
        event.registerEntityRenderer(ModEntity.BLUEPRINT_ENTITY.get(), PaintingRenderer::new);
    }

    @SubscribeEvent
    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(
                Capabilities.FluidHandler.BLOCK, ModBlockEntities.TEST_BLOCK.get(),
                (TestBlockEntity blockEntity, Direction side) -> blockEntity.fluidTank
        );
    }
}
