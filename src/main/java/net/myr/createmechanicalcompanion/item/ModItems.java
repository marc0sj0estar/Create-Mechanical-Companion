package net.myr.createmechanicalcompanion.item;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.myr.createmechanicalcompanion.CreateMechanicalCompanion;
import net.myr.createmechanicalcompanion.entity.ModEntity;

import java.util.List;
import java.util.function.Supplier;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(BuiltInRegistries.ITEM, CreateMechanicalCompanion.MOD_ID);

    public static final Supplier<Item> ICON_ITEM = ITEMS.register("tab_icon", () -> new Item(new Item.Properties().stacksTo(1)));

    public static final Supplier<Item> BLUEPRINT_PAINTING_ITEM = ITEMS.register("blueprint_painting", () -> new BlueprintPaintingItem(new Item.Properties().stacksTo(16)));


    public static final Supplier<Item> MECHANICAL_WOLF_LINK = ITEMS.register("mechanical_wolf_link",
            () -> new MechanicalWolfLink(new Item.Properties().stacksTo(1)));

    //Recipe items
    public static final Supplier<Item> MECHANICAL_WOLF_PROCESSOR = ITEMS.register("mechanical_wolf_processor", () -> new Item(new Item.Properties().stacksTo(1)){
        @Override
        public void appendHoverText(ItemStack pStack, TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
            pTooltipComponents.add(Component.translatable("item.createmechanicalcompanion.mechanical_wolf_processor.tooltip"));
            super.appendHoverText(pStack, pContext, pTooltipComponents, pIsAdvanced);
        }
    });

    public static final Supplier<Item> MECHANICAL_WOLF_MOTHERBOARD = ITEMS.register("mechanical_wolf_motherboard", () -> new Item(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> ROSE_QUARTZ_LENS = ITEMS.register("rose_quartz_lens", () -> new Item(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> OPTICAL_SENSOR = ITEMS.register("optical_sensor", () -> new Item(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> INCOMPLETE_OPTICAL_SENSOR = ITEMS.register("incomplete_optical_sensor", () -> new Item(new Item.Properties().stacksTo(1)));
    public static final Supplier<Item> INCOMPLETE_MECHANICAL_WOLF_MOTHERBOARD = ITEMS.register("incomplete_mechanical_wolf_motherboard", () -> new Item(new Item.Properties().stacksTo(1)));




    public static final Supplier<SpawnEggItem> ILLAGER_ENGINEER_SPAWN_EGG =
            ITEMS.register("illager_engineer_spawn_egg", () ->
                    new DeferredSpawnEggItem(
                            ModEntity.ILLAGER_ENGINEER,
                            0x737373,
                            0xb2b55c,
                            new Item.Properties()));

    public static final Supplier<SpawnEggItem> POTATO_CANNON_ILLAGER_SPAWN_EGG =
            ITEMS.register("potato_cannon_illager_spawn_egg", () ->
                    new DeferredSpawnEggItem(
                            ModEntity.POTATO_CANNON_ILLAGER,
                            0x41354a,
                            0xb2b55c,
                            new Item.Properties()));




    //Defensive modules
    public static final Supplier<Item> REINFORCED_PLATES = ITEMS.register("reinforced_plates", () -> new Item(new Item.Properties().stacksTo(1)){
        @Override
        public void appendHoverText(ItemStack pStack, TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
            pTooltipComponents.add(Component.translatable("item.createmechanicalcompanion.defensive_module"));
            pTooltipComponents.add(Component.translatable("item.createmechanicalcompanion.reinforced_plates.tooltip"));
            super.appendHoverText(pStack, pContext, pTooltipComponents, pIsAdvanced);
        }
    });

    public static final Supplier<Item> NETHERITE_PLATES = ITEMS.register("netherite_plates", () -> new Item(new Item.Properties().stacksTo(1)){
    @Override
    public void appendHoverText(ItemStack pStack, TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.translatable("item.createmechanicalcompanion.defensive_module"));
        pTooltipComponents.add(Component.translatable("item.createmechanicalcompanion.netherite_plates.tooltip"));
        super.appendHoverText(pStack, pContext, pTooltipComponents, pIsAdvanced);
    }
});

    //Offensive modules
    public static final Supplier<Item> MOUNTED_CROSSBOW = ITEMS.register("mounted_crossbow", () -> new Item(new Item.Properties().stacksTo(1)){
        @Override
        public void appendHoverText(ItemStack pStack, TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
            pTooltipComponents.add(Component.translatable("item.createmechanicalcompanion.offensive_module"));
            pTooltipComponents.add(Component.translatable("item.createmechanicalcompanion.mounted_crossbow.tooltip"));
            super.appendHoverText(pStack, pContext, pTooltipComponents, pIsAdvanced);
        }
    });
    public static final Supplier<Item> TESLA_TAIL = ITEMS.register("tesla_tail", () -> new Item(new Item.Properties().stacksTo(1)){
        @Override
        public void appendHoverText(ItemStack pStack, TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
            pTooltipComponents.add(Component.translatable("item.createmechanicalcompanion.offensive_module"));
            pTooltipComponents.add(Component.translatable("item.createmechanicalcompanion.tesla_tail.tooltip"));
            super.appendHoverText(pStack, pContext, pTooltipComponents, pIsAdvanced);
        }
    });
    public static final Supplier<Item> SMELTING_FANGS = ITEMS.register("smelting_fangs", () -> new Item(new Item.Properties().stacksTo(1)){
        @Override
        public void appendHoverText(ItemStack pStack, TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
            pTooltipComponents.add(Component.translatable("item.createmechanicalcompanion.offensive_module"));
            pTooltipComponents.add(Component.translatable("item.createmechanicalcompanion.smelting_fangs.tooltip"));
            super.appendHoverText(pStack, pContext, pTooltipComponents, pIsAdvanced);
        }
    });

    //Movement modules
    public static final Supplier<Item> BOOSTER_ROCKET = ITEMS.register("booster_rocket", () -> new Item(new Item.Properties().stacksTo(1)){
        @Override
        public void appendHoverText(ItemStack pStack, TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
            pTooltipComponents.add(Component.translatable("item.createmechanicalcompanion.movement_module"));
            pTooltipComponents.add(Component.translatable("item.createmechanicalcompanion.booster_rocket.tooltip"));
            super.appendHoverText(pStack, pContext, pTooltipComponents, pIsAdvanced);
        }
    });
    public static final Supplier<Item> QUANTUM_DRIVE = ITEMS.register("quantum_drive", () -> new Item(new Item.Properties().stacksTo(1)){
        @Override
        public void appendHoverText(ItemStack pStack, TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
            pTooltipComponents.add(Component.translatable("item.createmechanicalcompanion.movement_module"));
            pTooltipComponents.add(Component.translatable("item.createmechanicalcompanion.quantum_drive.tooltip"));
            super.appendHoverText(pStack, pContext, pTooltipComponents, pIsAdvanced);
        }
    });

    //Utility modules
    public static final Supplier<Item> REGENERATIVE_CASING = ITEMS.register("regenerative_casing", () -> new Item(new Item.Properties().stacksTo(1)){
        @Override
        public void appendHoverText(ItemStack pStack, TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
            pTooltipComponents.add(Component.translatable("item.createmechanicalcompanion.utility_module"));
            pTooltipComponents.add(Component.translatable("item.createmechanicalcompanion.regenerative_casing.tooltip"));
            super.appendHoverText(pStack, pContext, pTooltipComponents, pIsAdvanced);
        }
    });
    public static final Supplier<Item> MOUNTED_LIGHT= ITEMS.register("mounted_light", () -> new Item(new Item.Properties().stacksTo(1)){
        @Override
        public void appendHoverText(ItemStack pStack, TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
            pTooltipComponents.add(Component.translatable("item.createmechanicalcompanion.utility_module"));
            pTooltipComponents.add(Component.translatable("item.createmechanicalcompanion.mounted_light.tooltip"));
            super.appendHoverText(pStack, pContext, pTooltipComponents, pIsAdvanced);
        }
    });
    public static final Supplier<Item> MOB_RADAR = ITEMS.register("mob_radar", () -> new Item(new Item.Properties().stacksTo(1)){
        @Override
        public void appendHoverText(ItemStack pStack, TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
            pTooltipComponents.add(Component.translatable("item.createmechanicalcompanion.utility_module"));
            pTooltipComponents.add(Component.translatable("item.createmechanicalcompanion.mob_radar.tooltip"));
            super.appendHoverText(pStack, pContext, pTooltipComponents, pIsAdvanced);
        }
    });

    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }

}
