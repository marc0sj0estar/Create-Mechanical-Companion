package net.myr.createmechanicalcompanion;

import net.minecraft.client.renderer.entity.PaintingRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.myr.createmechanicalcompanion.blocks.CompanionFoundryBlock;
import net.myr.createmechanicalcompanion.blocks.ModBlockEntities;
import net.myr.createmechanicalcompanion.blocks.CompanionFoundryEntity;
import net.myr.createmechanicalcompanion.blocks.ModBlocks;
import net.myr.createmechanicalcompanion.client.*;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.myr.createmechanicalcompanion.entity.CustomWolf;
import net.myr.createmechanicalcompanion.entity.IllagerEngineer;
import net.myr.createmechanicalcompanion.entity.ModEntities;

public class EventHandler {

    @SubscribeEvent
    public static void onRegisterParticleProviders(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(ModParticles.COG_PARTICLE.get(), CogParticle.Provider::new);
    }

    @SubscribeEvent
    public static void onEntityAttributeCreation(EntityAttributeCreationEvent event) {
        event.put(ModEntities.CUSTOM_WOLF.get(), CustomWolf.createAttributes().build());
        event.put(ModEntities.ILLAGER_ENGINEER.get(), IllagerEngineer.createAttributes().build());
        event.put(ModEntities.POTATO_CANNON_ILLAGER.get(), IllagerEngineer.createAttributes().build());
    }

    @SubscribeEvent
    public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ModEntities.CUSTOM_WOLF.get(), WolfRenderer::new);
        event.registerEntityRenderer(ModEntities.ILLAGER_ENGINEER.get(), IllagerEngineerRenderer::new);
        event.registerEntityRenderer(ModEntities.POTATO_CANNON_ILLAGER.get(), PotatoCannonIllagerRenderer::new);
        event.registerEntityRenderer(ModEntities.BLUEPRINT_ENTITY.get(), PaintingRenderer::new);
    }

    @SubscribeEvent
    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlock(
                Capabilities.ItemHandler.BLOCK,
                (Level level, BlockPos pos, BlockState state, BlockEntity blockEntity, Direction side) -> {
                    if (!(state.getBlock() instanceof CompanionFoundryBlock foundry)){
                        return null;
                    }

                    BlockPos centerPos = foundry.getBottomCenter(pos, state);
                    if(!(level.getBlockEntity(centerPos) instanceof CompanionFoundryEntity center)) {
                        return null;
                    }
                    return center.itemHandler;
                },
                ModBlocks.COMPANION_FOUNDRY.get()
        );

        event.registerBlock(
                Capabilities.FluidHandler.BLOCK,
                (Level level, BlockPos pos, BlockState state, BlockEntity blockEntity, Direction side) -> {
                    if (!(state.getBlock() instanceof CompanionFoundryBlock foundry)){
                        return null;
                    }

                    BlockPos centerPos = foundry.getBottomCenter(pos, state);

                    if(!(level.getBlockEntity(centerPos) instanceof CompanionFoundryEntity center)) {
                        return null;
                    }
                    return center.fluidTank;
                },
                ModBlocks.COMPANION_FOUNDRY.get()
        );
    }
}
