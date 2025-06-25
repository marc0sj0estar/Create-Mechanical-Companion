package net.myr.createmechanicalcompanion;

import com.simibubi.create.compat.curios.GogglesCurioRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.myr.createmechanicalcompanion.client.CustomWolfModel;
import net.myr.createmechanicalcompanion.client.MechanicalWolfLinkRenderer;
import net.myr.createmechanicalcompanion.client.ModModelLayers;
import net.myr.createmechanicalcompanion.sounds.ModSounds;
import net.myr.createmechanicalcompanion.entity.ModEntity;
import net.myr.createmechanicalcompanion.item.ModCreativeModeTabs;
import net.myr.createmechanicalcompanion.item.ModItems;
import net.myr.createmechanicalcompanion.screen.ModMenuTypes;
import net.myr.createmechanicalcompanion.screen.WolfScreen;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;


@Mod(CreateMechanicalCompanion.MOD_ID)
public class CreateMechanicalCompanion
{
    public static final String MOD_ID = "createmechanicalcompanion";

    public CreateMechanicalCompanion(FMLJavaModLoadingContext context)
    {
        IEventBus modEventBus = context.getModEventBus();
        ModCreativeModeTabs.register(modEventBus);
        ModItems.register(modEventBus);
        ModMenuTypes.register(modEventBus);
        ModEntity.ENTITIES.register(modEventBus);
        ModSounds.SOUND_EVENTS.register(modEventBus);
        BlueprintPaintingVariants.BLUEPRINT_PAINTINGS.register(modEventBus);
        MinecraftForge.EVENT_BUS.register(this);

        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::addCreative);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {}

    private void addCreative(BuildCreativeModeTabContentsEvent event) {}

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event){}

    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            MenuScreens.register(ModMenuTypes.WOLF_MENU.get(), WolfScreen::new);
            CuriosRendererRegistry.register(ModItems.MECHANICAL_WOLF_LINK.get(), () -> new MechanicalWolfLinkRenderer(Minecraft.getInstance().getEntityModels().bakeLayer(GogglesCurioRenderer.LAYER)));
        }

        @SubscribeEvent
        public static void onRegisterLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
            event.registerLayerDefinition(ModModelLayers.CUSTOM_WOLF, CustomWolfModel::createBodyLayer);
        }
    }

    public static ResourceLocation genRL(String name) {
        return new ResourceLocation(MOD_ID, name);
    }
}
