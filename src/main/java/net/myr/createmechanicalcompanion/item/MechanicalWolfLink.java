package net.myr.createmechanicalcompanion.item;

import java.util.List;
import java.util.UUID;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;
import net.myr.createmechanicalcompanion.ModConfig;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.myr.createmechanicalcompanion.entity.CustomWolf;
import net.myr.createmechanicalcompanion.entity.ModEntity;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;

import static net.myr.createmechanicalcompanion.CreateMechanicalCompanion.MOD_ID;

public class MechanicalWolfLink extends Item implements ICurioItem {

    public MechanicalWolfLink(Properties properties) {
        super(properties);
    }

    private CompoundTag previousModuleTag;

    // Helper method to get custom data from item (1.21.1 way)
    private CompoundTag getCustomTag(ItemStack stack) {
        CustomData customData = stack.get(DataComponents.CUSTOM_DATA);
        if (customData != null) {
            return customData.copyTag();
        }
        return new CompoundTag();
    }

    // Helper method to set custom data on item (1.21.1 way)
    private void setCustomTag(ItemStack stack, CompoundTag tag) {
        stack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
    }

    @Override
    public boolean canEquip(SlotContext slotContext, ItemStack stack) {
        LivingEntity playerEntity = slotContext.entity();

        if (!(playerEntity instanceof Player player)) {
            return false;
        }

        ICuriosItemHandler curiosInventory = CuriosApi.getCuriosInventory(playerEntity).orElse(null);

        if (curiosInventory == null) {
            return true;
        }

        ICurioStacksHandler headSlotHandler = curiosInventory.getCurios().get("head");

        if (headSlotHandler == null) {
            return true;
        }

        for (int i = 0; i < headSlotHandler.getSlots(); i++) {
            ItemStack itemStack = headSlotHandler.getStacks().getStackInSlot(i);
            if (itemStack.getItem() instanceof MechanicalWolfLink) {
                player.displayClientMessage(Component.translatable("item.createmechanicalcompanion.mechanical_wolf_link.duplicate_warning"), true);
                return false;
            }
        }
        return true;
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        Entity entity = slotContext.entity();
        Level level = entity.level();
        CompoundTag tag = getCustomTag(stack);
        if (!level.isClientSide && tag.contains("WolfUUID") && newStack.getItem().equals(Items.AIR)) {
            dismissWolf(level, tag);
            setCustomTag(stack, tag);
        }
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        Level level = slotContext.entity().level();
        if (level.isClientSide || !(slotContext.entity() instanceof Player player)) {
            return;
        }

        CompoundTag tag = getCustomTag(stack);

        if (tag.contains("WolfUUID")) {
            UUID wolfUUID = tag.getUUID("WolfUUID");
            Entity entity = ((ServerLevel) level).getEntity(wolfUUID);
            if (entity == null || !(entity instanceof CustomWolf)) {
                summonWolf(level, player, tag);
                setCustomTag(stack, tag);
                return;
            }

            CustomWolf wolf = (CustomWolf) entity;

            if (wolf.getHealth() <= 0) {
                tag.putInt("SpawnCooldown", ModConfig.COMMON.mechanicalWolfRespawnCooldown.get());
                setCustomTag(stack, tag);
            }

            if (wolf.getCustomName() != null) {
                String wolfName = wolf.getCustomName().getString();
                tag.putString("Nametag", wolfName);
                setCustomTag(stack, tag);
            }

            saveWolfModulesToTrinket(stack, wolf.getItemHandler());
        } else {
            summonWolf(level, player, tag);
            setCustomTag(stack, tag);
        }
    }

    private void summonWolf(Level level, Player player, CompoundTag tag) {
        if (tag.contains("SpawnCooldown")) {
            int spawnCooldown = tag.getInt("SpawnCooldown");
            spawnCooldown--;
            if (spawnCooldown == 0) {
                tag.remove("SpawnCooldown");
            } else {
                tag.putInt("SpawnCooldown", spawnCooldown);
            }
            return;
        }

        CustomWolf newWolf = new CustomWolf(ModEntity.CUSTOM_WOLF.get(), level);
        newWolf.tameToPlayer(player);
        newWolf.setPos(player.getX(), player.getY(), player.getZ());
        level.addFreshEntity(newWolf);
        if (tag.contains("Nametag")) {
            newWolf.setCustomName(Component.literal(tag.getString("Nametag")));
        }

        ItemStackHandler wolfInventory = loadWolfModulesFromTrinket(tag, newWolf.getItemHandler().getSlots());
        newWolf.setItemHandler(wolfInventory);
        tag.putUUID("WolfUUID", newWolf.getUUID());
    }

    private void dismissWolf(Level level, CompoundTag tag) {
        if (tag != null && tag.hasUUID("WolfUUID")) {
            UUID wolfUUID = tag.getUUID("WolfUUID");
            Entity entity = ((ServerLevel) level).getEntity(wolfUUID);

            if (entity instanceof CustomWolf wolf) {
                saveWolfModulesToTrinketTag(tag, wolf.getItemHandler());
                wolf.discard();
            }
            tag.remove("WolfUUID");
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        CompoundTag tag = getCustomTag(stack);

        if (tag.contains("WolfModules")) {
            CompoundTag modulesTag = tag.getCompound("WolfModules");
            net.minecraft.nbt.ListTag items = modulesTag.getList("Items", 10);
            if (!items.isEmpty()) {
                tooltipComponents.add(Component.empty());
                tooltipComponents.add(Component.literal("§e§nEquipped Modules§r"));
                for (int i = 0; i < items.size(); i++) {
                    String itemId = items.getCompound(i).getString("id");
                    net.minecraft.resources.ResourceLocation resourceLocation = net.minecraft.resources.ResourceLocation.tryParse(itemId);
                    Item item = net.minecraft.core.registries.BuiltInRegistries.ITEM.get(resourceLocation);
                    tooltipComponents.add(
                            Component.literal("§6> §r").append(item.getDescription())
                    );
                }
                tooltipComponents.add(Component.empty());
            }
        }

        if (Screen.hasShiftDown()) {
            tooltipComponents.add(Component.translatable("item.createmechanicalcompanion.shift2"));
            tooltipComponents.add(Component.translatable("item.createmechanicalcompanion.mechanical_wolf_link.tooltip"));
        } else {
            tooltipComponents.add(Component.translatable("item.createmechanicalcompanion.shift"));
        }
        if (tag.contains("SpawnCooldown")) {
            int cooldownTicks = tag.getInt("SpawnCooldown");
            int seconds = cooldownTicks / 20;
            tooltipComponents.add(Component.literal("§7Spawn Cooldown: §c" + seconds + "s"));
        }

        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }

    public void saveWolfModulesToTrinket(ItemStack stack, ItemStackHandler handler) {
        if (handler == null) {
            return;
        }

        CompoundTag tag = getCustomTag(stack);
        CompoundTag serialized = serializeHandler(handler);

        if (serialized.equals(previousModuleTag)) {
            return;
        }

        tag.put("WolfModules", serialized);
        previousModuleTag = serialized;
        setCustomTag(stack, tag);
    }

    private void saveWolfModulesToTrinketTag(CompoundTag tag, ItemStackHandler handler) {
        if (tag == null || handler == null) {
            return;
        }
        tag.put("WolfModules", serializeHandler(handler));
    }

    private CompoundTag serializeHandler(ItemStackHandler handler) {
        CompoundTag tag = new CompoundTag();
        tag.putInt("Size", handler.getSlots());
        net.minecraft.nbt.ListTag items = new net.minecraft.nbt.ListTag();
        for (int i = 0; i < handler.getSlots(); i++) {
            ItemStack slotStack = handler.getStackInSlot(i);
            if (!slotStack.isEmpty()) {
                CompoundTag itemTag = new CompoundTag();
                itemTag.putInt("Slot", i);
                itemTag.putString("id", net.minecraft.core.registries.BuiltInRegistries.ITEM.getKey(slotStack.getItem()).toString());
                itemTag.putInt("count", slotStack.getCount());
                items.add(itemTag);
            }
        }
        tag.put("Items", items);
        return tag;
    }

    public ItemStackHandler loadWolfModulesFromTrinket(CompoundTag tag, int size) {
        ItemStackHandler handler = new ItemStackHandler(size);

        if (tag != null && tag.contains("WolfModules")) {
            CompoundTag modulesTag = tag.getCompound("WolfModules");
            if (modulesTag.contains("Items")) {
                net.minecraft.nbt.ListTag items = modulesTag.getList("Items", 10);
                for (int i = 0; i < items.size(); i++) {
                    CompoundTag itemTag = items.getCompound(i);
                    int slot = itemTag.getInt("Slot");
                    if (slot >= 0 && slot < size) {
                        net.minecraft.resources.ResourceLocation itemId = net.minecraft.resources.ResourceLocation.tryParse(itemTag.getString("id"));
                        if (itemId != null) {
                            Item item = net.minecraft.core.registries.BuiltInRegistries.ITEM.get(itemId);
                            if (item != Items.AIR) {
                                handler.setStackInSlot(slot, new ItemStack(item, itemTag.getInt("count")));
                            }
                        }
                    }
                }
            }
        }

        return handler;
    }
}
