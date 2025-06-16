package net.myr.createmechanicalcompanion;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.client.renderer.entity.IllagerRenderer;
import net.minecraft.client.renderer.entity.PillagerRenderer;
import net.minecraft.client.renderer.entity.VindicatorRenderer;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.myr.createmechanicalcompanion.client.IllagerEngineerRenderer;
import net.myr.createmechanicalcompanion.client.PotatoCannonIllagerRenderer;
import net.myr.createmechanicalcompanion.client.WolfRenderer;
import net.myr.createmechanicalcompanion.entity.CustomWolf;
import net.myr.createmechanicalcompanion.entity.IllagerEngineer;
import net.myr.createmechanicalcompanion.entity.ModEntity;

import java.util.LinkedList;
import java.util.List;

@Mod.EventBusSubscriber(modid = CreateMechanicalCompanion.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
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
    }

}