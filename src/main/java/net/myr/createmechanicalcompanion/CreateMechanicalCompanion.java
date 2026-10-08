package net.myr.createmechanicalcompanion;

import com.mojang.logging.LogUtils;
import com.simibubi.create.foundation.data.CreateRegistrate;
import net.minecraft.resources.ResourceLocation;
import net.myr.createmechanicalcompanion.blocks.ModBlockEntities;
import net.myr.createmechanicalcompanion.blocks.ModBlocks;
import net.myr.createmechanicalcompanion.screen.TestBlockScreen;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.myr.createmechanicalcompanion.client.CustomWolfModel;
import net.myr.createmechanicalcompanion.client.MechanicalWolfLinkRenderer;
import net.myr.createmechanicalcompanion.client.ModModelLayers;
import net.myr.createmechanicalcompanion.sounds.ModSounds;
import net.myr.createmechanicalcompanion.entity.ModEntity;
import net.myr.createmechanicalcompanion.item.BlueprintPaintingVariants;
import net.myr.createmechanicalcompanion.item.ModCreativeModeTabs;
import net.myr.createmechanicalcompanion.item.ModItems;
import net.myr.createmechanicalcompanion.screen.ModMenuTypes;
import net.myr.createmechanicalcompanion.screen.WolfScreen;
import org.slf4j.Logger;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;


@Mod(CreateMechanicalCompanion.MOD_ID)
public class CreateMechanicalCompanion {
    public static final String MOD_ID = "createmechanicalcompanion";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static final CreateRegistrate REGISTRATE = CreateRegistrate.create(MOD_ID);


    public CreateMechanicalCompanion(IEventBus modEventBus, ModContainer modContainer) {
        ModCreativeModeTabs.register();
        ModItems.register(modEventBus);
        ModMenuTypes.register(modEventBus);
        ModEntity.register(modEventBus);
        ModSounds.register(modEventBus);
        ModBlocks.register();
        ModBlockEntities.register();
        BlueprintPaintingVariants.register(modEventBus);
        REGISTRATE.registerEventListeners(modEventBus);

        modContainer.registerConfig(ModConfig.Type.COMMON, net.myr.createmechanicalcompanion.ModConfig.COMMON_SPEC);
        modEventBus.addListener(ClientModEvents::onClientSetup);
        modEventBus.addListener(ClientModEvents::registerScreens);
        modEventBus.addListener(ClientModEvents::onRegisterLayerDefinitions);

        modEventBus.addListener(EventHandler::onEntityAttributeCreation);
        modEventBus.addListener(EventHandler::onRegisterRenderers);
        modEventBus.addListener(EventHandler::onRegisterCapabilities);

        NeoForge.EVENT_BUS.register(ForgeEventHandler.class);
    }

    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            event.enqueueWork(() -> {
                CuriosRendererRegistry.register(ModItems.MECHANICAL_WOLF_LINK.get(), MechanicalWolfLinkRenderer::new);
            });
        }

        @SubscribeEvent
        public static void registerScreens(RegisterMenuScreensEvent event) {
            event.register(ModMenuTypes.WOLF_MENU.get(), WolfScreen::new);
            event.register(ModMenuTypes.TEST_BLOCK_MENU.get(), TestBlockScreen::new);
        }

        @SubscribeEvent
        public static void onRegisterLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
            event.registerLayerDefinition(ModModelLayers.CUSTOM_WOLF, CustomWolfModel::createBodyLayer);
        }


    }

    public static ResourceLocation genRL(String name) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, name);
    }
}