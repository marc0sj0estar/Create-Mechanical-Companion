package net.myr.createmechanicalcompanion.item;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.myr.createmechanicalcompanion.CreateMechanicalCompanion;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, CreateMechanicalCompanion.MOD_ID);

    public static final RegistryObject<Item> ICON_ITEM = ITEMS.register("tab_icon", () -> new Item(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> MECHANICAL_WOLF_LINK = ITEMS.register("mechanical_wolf_link",
            () -> new MechanicalWolfLink(new Item.Properties().stacksTo(1)));

    //Defensive modules
    public static final RegistryObject<Item> REINFORCED_PLATES = ITEMS.register("reinforced_plates", () -> new Item(new Item.Properties().stacksTo(1)){
        @Override
        public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
            pTooltipComponents.add(Component.translatable("item.createmechanicalcompanion.defensive_module"));
            pTooltipComponents.add(Component.translatable("item.createmechanicalcompanion.reinforced_plates.tooltip"));
            super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
        }
    });

    public static final RegistryObject<Item> NETHERITE_PLATES = ITEMS.register("netherite_plates", () -> new Item(new Item.Properties().stacksTo(1)){
    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.translatable("item.createmechanicalcompanion.defensive_module"));
        pTooltipComponents.add(Component.translatable("item.createmechanicalcompanion.netherite_plates.tooltip"));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
});

    //Offensive modules
    public static final RegistryObject<Item> MOUNTED_CROSSBOW = ITEMS.register("mounted_crossbow", () -> new Item(new Item.Properties().stacksTo(1)){
        @Override
        public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
            pTooltipComponents.add(Component.translatable("item.createmechanicalcompanion.offensive_module"));
            pTooltipComponents.add(Component.translatable("item.createmechanicalcompanion.mounted_crossbow.tooltip"));
            super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
        }
    });
    public static final RegistryObject<Item> TESLA_TAIL = ITEMS.register("tesla_tail", () -> new Item(new Item.Properties().stacksTo(1)){
        @Override
        public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
            pTooltipComponents.add(Component.translatable("item.createmechanicalcompanion.offensive_module"));
            pTooltipComponents.add(Component.translatable("item.createmechanicalcompanion.tesla_tail.tooltip"));
            super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
        }
    });
    public static final RegistryObject<Item> SMELTING_FANGS = ITEMS.register("smelting_fangs", () -> new Item(new Item.Properties().stacksTo(1)){
        @Override
        public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
            pTooltipComponents.add(Component.translatable("item.createmechanicalcompanion.offensive_module"));
            pTooltipComponents.add(Component.translatable("item.createmechanicalcompanion.smelting_fangs.tooltip"));
            super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
        }
    });

    //Movement modules
    public static final RegistryObject<Item> BOOSTER_ROCKET = ITEMS.register("booster_rocket", () -> new Item(new Item.Properties().stacksTo(1)){
        @Override
        public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
            pTooltipComponents.add(Component.translatable("item.createmechanicalcompanion.movement_module"));
            pTooltipComponents.add(Component.translatable("item.createmechanicalcompanion.booster_rocket.tooltip"));
            super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
        }
    });
    public static final RegistryObject<Item> QUANTUM_DRIVE = ITEMS.register("quantum_drive", () -> new Item(new Item.Properties().stacksTo(1)){
        @Override
        public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
            pTooltipComponents.add(Component.translatable("item.createmechanicalcompanion.movement_module"));
            pTooltipComponents.add(Component.translatable("item.createmechanicalcompanion.quantum_drive.tooltip"));
            super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
        }
    });

    //Utility modules
    public static final RegistryObject<Item> REGENERATIVE_CASING = ITEMS.register("regenerative_casing", () -> new Item(new Item.Properties().stacksTo(1)){
        @Override
        public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
            pTooltipComponents.add(Component.translatable("item.createmechanicalcompanion.utility_module"));
            pTooltipComponents.add(Component.translatable("item.createmechanicalcompanion.regenerative_casing.tooltip"));
            super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
        }
    });
    public static final RegistryObject<Item> MOUNTED_LIGHT= ITEMS.register("mounted_light", () -> new Item(new Item.Properties().stacksTo(1)){
        @Override
        public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
            pTooltipComponents.add(Component.translatable("item.createmechanicalcompanion.utility_module"));
            pTooltipComponents.add(Component.translatable("item.createmechanicalcompanion.mounted_light.tooltip"));
            super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
        }
    });
    public static final RegistryObject<Item> MOB_RADAR = ITEMS.register("mob_radar", () -> new Item(new Item.Properties().stacksTo(1)){
        @Override
        public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
            pTooltipComponents.add(Component.translatable("item.createmechanicalcompanion.utility_module"));
            pTooltipComponents.add(Component.translatable("item.createmechanicalcompanion.mob_radar.tooltip"));
            super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
        }
    });

    public static void register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }

}