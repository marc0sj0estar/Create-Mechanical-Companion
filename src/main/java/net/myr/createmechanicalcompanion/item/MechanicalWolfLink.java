package net.myr.createmechanicalcompanion.item;

import java.util.List;
import java.util.UUID;

import net.minecraft.client.gui.screens.Screen;
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
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.items.ItemStackHandler;
import net.myr.createmechanicalcompanion.entity.CustomWolf;
import net.myr.createmechanicalcompanion.entity.ModEntity;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

public class MechanicalWolfLink extends Item implements ICurioItem {


    public MechanicalWolfLink(Properties properties) {
        super(properties);
    }

    private CompoundTag previousModuleTag;

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        Entity entity = slotContext.entity();
        Level level = entity.level();
        CompoundTag tag = stack.getOrCreateTag();
        if (!level.isClientSide && tag.contains("WolfUUID") && newStack.getItem().equals(Items.AIR)) {
            dismissWolf(level, tag);
        }
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        Level level = slotContext.entity().level();
        if (level.isClientSide || !((slotContext.entity() instanceof Player player))) {
            return;
        }

        CompoundTag tag = stack.getOrCreateTag();

        if(tag.contains("WolfUUID")){
            UUID wolfUUID = tag.getUUID("WolfUUID");
            CustomWolf entity =(CustomWolf) ((ServerLevel) level).getEntity(wolfUUID);
            if(entity == null) {
                summonWolf(level, player, tag);
                return;
            }

            if(entity.getHealth() <= 0){
                tag.putInt("SpawnCooldown", 200);
            }

            if(entity.getCustomName() != null){
                String wolfName = entity.getCustomName().getString();
                tag.putString("Nametag", wolfName);
            }

            saveWolfModulesToTrinket(tag, ((CustomWolf) entity).getItemHandler());
        }else{
            summonWolf(level, player, tag);
        }
    }

    private void summonWolf(Level level, Player player, CompoundTag tag) {
        if(tag.contains("SpawnCooldown"))
        {
            int spawnCooldown = tag.getInt("SpawnCooldown");
            spawnCooldown--;
            if(spawnCooldown == 0){
                tag.remove("SpawnCooldown");
            }else{
                tag.putInt("SpawnCooldown", spawnCooldown);
            }
            return;

        }

        CustomWolf newWolf = new CustomWolf(ModEntity.CUSTOM_WOLF.get(), level);
        newWolf.tame(player);
        newWolf.setPos(player.getX(), player.getY(), player.getZ());
        level.addFreshEntity(newWolf);
        if(tag.contains("Nametag")){
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

                saveWolfModulesToTrinket(tag, wolf.getItemHandler());

                wolf.discard();
            }
            tag.remove("WolfUUID");
        }
    }

    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, CompoundTag nbt) {
        return super.initCapabilities(stack, nbt);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        CompoundTag tag = pStack.getTag();
        if(Screen.hasShiftDown()){
            pTooltipComponents.add(Component.translatable("item.createmechanicalcompanion.shift2"));
            pTooltipComponents.add(Component.translatable("item.createmechanicalcompanion.mechanical_wolf_link.tooltip"));
        }else{
            pTooltipComponents.add(Component.translatable("item.createmechanicalcompanion.shift"));
        }
        if (tag != null && tag.contains("SpawnCooldown")) {
            int cooldownTicks = tag.getInt("SpawnCooldown");
            int seconds = cooldownTicks / 20;
            pTooltipComponents.add(Component.literal("§7Spawn Cooldown: §c" + seconds + "s"));
        }
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }

    public void saveWolfModulesToTrinket(CompoundTag tag, ItemStackHandler handler) {
        if(tag == null || handler == null){
            return;
        }

        CompoundTag serialized = handler.serializeNBT();

        if(serialized.equals(previousModuleTag)){
            return;
        }

        tag.put("WolfModules", serialized);
        previousModuleTag = serialized;

    }

    public ItemStackHandler loadWolfModulesFromTrinket(CompoundTag tag, int size) {
        ItemStackHandler handler = new ItemStackHandler(size);

        if (tag != null && tag.contains("WolfModules")) {
            CompoundTag serialized = tag.getCompound("WolfModules");
            handler.deserializeNBT(serialized);
        }

        return handler;
    }
}