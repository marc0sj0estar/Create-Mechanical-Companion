package net.myr.createmechanicalcompanion;

import com.simibubi.create.compat.curios.GogglesCurioRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
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
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;


@Mod(CreateMechanicalCompanion.MOD_ID)
public class CreateMechanicalCompanion {
    public static final String MOD_ID = "createmechanicalcompanion";

    public CreateMechanicalCompanion(IEventBus modEventBus, ModContainer modContainer) {
        ModCreativeModeTabs.register(modEventBus);
        ModItems.register(modEventBus);
        ModMenuTypes.register(modEventBus);
        ModEntity.register(modEventBus);
        ModSounds.register(modEventBus);
        BlueprintPaintingVariants.register(modEventBus);

        modContainer.registerConfig(ModConfig.Type.COMMON, net.myr.createmechanicalcompanion.ModConfig.COMMON_SPEC);

        NeoForge.EVENT_BUS.register(ForgeEventHandler.class);
    }

    @EventBusSubscriber(modid = MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            event.enqueueWork(() -> {
                CuriosRendererRegistry.register(ModItems.MECHANICAL_WOLF_LINK.get(), () -> new MechanicalWolfLinkRenderer(Minecraft.getInstance().getEntityModels().bakeLayer(GogglesCurioRenderer.LAYER)));
            });
        }

        @SubscribeEvent
        public static void registerScreens(RegisterMenuScreensEvent event) {
            event.register(ModMenuTypes.WOLF_MENU.get(), WolfScreen::new);
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
