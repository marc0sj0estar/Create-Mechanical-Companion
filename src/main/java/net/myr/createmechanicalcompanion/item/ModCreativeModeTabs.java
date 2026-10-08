package net.myr.createmechanicalcompanion.item;

import com.tterrag.registrate.util.entry.RegistryEntry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.myr.createmechanicalcompanion.CreateMechanicalCompanion;

import java.util.function.Supplier;

public class ModCreativeModeTabs {

    public static final RegistryEntry<CreativeModeTab, CreativeModeTab> MOD_TAB =
            CreateMechanicalCompanion.REGISTRATE.defaultCreativeTab("main_tab", builder ->
                    builder
                            .title(Component.translatable("creativetab.create_mechanical_companion_tab"))
                            .displayItems((ItemDisplayParameters, output) ->
                            {
                                output.accept(ModItems.REINFORCED_PLATES.get());
                                output.accept(ModItems.NETHERITE_PLATES.get());
                                output.accept(ModItems.SMELTING_FANGS.get());
                                output.accept(ModItems.QUANTUM_DRIVE.get());
                                output.accept(ModItems.MOB_RADAR.get());
                                output.accept(ModItems.BOOSTER_ROCKET.get());
                                output.accept(ModItems.TESLA_TAIL.get());
                                output.accept(ModItems.MOUNTED_CROSSBOW.get());
                                output.accept(ModItems.MOUNTED_LIGHT.get());
                                output.accept(ModItems.REGENERATIVE_CASING.get());
                                output.accept(ModItems.MECHANICAL_WOLF_LINK.get());
                                output.accept(ModItems.MECHANICAL_WOLF_PROCESSOR.get());
                                output.accept(ModItems.MECHANICAL_WOLF_MOTHERBOARD.get());
                                output.accept(ModItems.OPTICAL_SENSOR.get());
                                output.accept(ModItems.ROSE_QUARTZ_LENS.get());
                                output.accept(ModItems.ILLAGER_ENGINEER_SPAWN_EGG.get());
                                output.accept(ModItems.POTATO_CANNON_ILLAGER_SPAWN_EGG.get());
                                output.accept(ModItems.BLUEPRINT_PAINTING_ITEM.get());
                                output.accept(ModItems.MECHANICAL_WOLF_CHASSIS.get());
                            })
                            .icon(() -> new ItemStack(ModItems.ICON_ITEM.get()))
                            .build()
            ).register();

    public static void register(){}
}
