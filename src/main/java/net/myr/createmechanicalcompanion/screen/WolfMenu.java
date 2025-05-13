package net.myr.createmechanicalcompanion.screen;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.SlotItemHandler;
import net.myr.createmechanicalcompanion.entity.CustomWolf;
import net.myr.createmechanicalcompanion.sounds.ModSounds;
import net.myr.createmechanicalcompanion.item.ModItems;

import org.jetbrains.annotations.NotNull;

public class WolfMenu extends AbstractContainerMenu {

    public final CustomWolf wolf;

    public static final int slotAmount = 5;


    public WolfMenu(int pContainerId, Inventory inv, FriendlyByteBuf extraData) {
        this(pContainerId, inv, getWolfEntity(extraData, inv.player.level()), new SimpleContainerData(slotAmount));
        addWolfInventorySlots();
    }


    private static CustomWolf getWolfEntity(FriendlyByteBuf buf, Level level) {
        int entityId = buf.readVarInt();
        Entity entity = level.getEntity(entityId);
        if (entity instanceof CustomWolf) {
            return (CustomWolf) entity;
        } else {
            throw new IllegalStateException("Entity with ID " + entityId + " is not a CustomWolfEntity!");
        }
    }

    public WolfMenu(int pContainerId, Inventory playerInventory, CustomWolf wolf, ContainerData data) {
        super(ModMenuTypes.WOLF_MENU.get(), pContainerId);
        System.out.println("WolfMenu constructor");
        this.wolf = wolf;
        
        addWolfInventorySlots();

        addPlayerInventory(playerInventory);
        addPlayerHotbar(playerInventory);
    }

    private void addWolfInventorySlots() {
        wolf.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(itemHandler -> {
            System.out.println("ItemHandler capability retrieved successfully.");
        });

        wolf.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(itemHandler -> {

            this.addSlot(new SlotItemHandler(itemHandler, 0, 96, 10)
            {
                @Override
                public boolean mayPlace(@NotNull ItemStack stack) {
                    return (stack.getItem() == ModItems.REINFORCED_PLATES.get() ||
                            stack.getItem() == ModItems.NETHERITE_PLATES.get());
                }


                @Override
                public void setByPlayer(ItemStack pStack) {
                    if(!pStack.isEmpty()){
                        playInsertSound();
                    }
                    super.setByPlayer(pStack);
                }
            });
            this.addSlot(new SlotItemHandler(itemHandler, 1, 120, 10)
            {
                @Override
                public boolean mayPlace(@NotNull ItemStack stack) {
                    return (stack.getItem() == ModItems.MOUNTED_CROSSBOW.get() ||
                            stack.getItem() == ModItems.SMELTING_FANGS.get() ||
                            stack.getItem() == ModItems.TESLA_TAIL.get());
                }

                @Override
                public void setByPlayer(ItemStack pStack) {
                    if(!pStack.isEmpty()){
                        playInsertSound();
                    }
                    super.setByPlayer(pStack);
                }
            });
            this.addSlot(new SlotItemHandler(itemHandler, 2, 108, 32){
                @Override
                public boolean mayPlace(@NotNull ItemStack stack) {
                    return (stack.getItem() == ModItems.BOOSTER_ROCKET.get() ||
                            stack.getItem() == ModItems.QUANTUM_DRIVE.get());
                }

                @Override
                public void setByPlayer(ItemStack pStack) {
                    if(!pStack.isEmpty()){
                        playInsertSound();
                    }
                    super.setByPlayer(pStack);
                }
            });
            this.addSlot(new SlotItemHandler(itemHandler, 3, 96, 54)
            {
                @Override
                public boolean mayPlace(ItemStack stack) {
                    if(itemHandler.getStackInSlot(4).getItem() == stack.getItem()){
                        return false;
                    }
                    return (stack.getItem() == ModItems.REGENERATIVE_CASING.get() ||
                            stack.getItem() == ModItems.MOB_RADAR.get() ||
                            stack.getItem() == ModItems.MOUNTED_LIGHT.get());
                }

                @Override
                public void setByPlayer(ItemStack pStack) {
                    if(!pStack.isEmpty()){
                        playInsertSound();
                    }
                    super.setByPlayer(pStack);
                }
            });
            this.addSlot(new SlotItemHandler(itemHandler, 4, 120, 54)
            {
                @Override
                public boolean mayPlace(@NotNull ItemStack stack) {
                    if(itemHandler.getStackInSlot(3).getItem() == stack.getItem()){
                        return false;
                    }
                   return (stack.getItem() == ModItems.REGENERATIVE_CASING.get() ||
                            stack.getItem() == ModItems.MOB_RADAR.get() ||
                            stack.getItem() == ModItems.MOUNTED_LIGHT.get());
                }

                @Override
                public void setByPlayer(ItemStack pStack) {
                    if(!pStack.isEmpty()){
                        playInsertSound();
                    }
                    super.setByPlayer(pStack);
                }
            });
        });
    }

    private void playInsertSound(){
        if (wolf.level().isClientSide) {
            Minecraft.getInstance().level.playLocalSound(wolf.blockPosition(), ModSounds.EQUIP_MODULE.get(), SoundSource.PLAYERS, 1.0F, 1.0F, false);
        }
    }

    @Override
    public ItemStack quickMoveStack(Player playerIn, int pIndex) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(pIndex);

        if (slot != null && slot.hasItem()) {
            ItemStack originalStack = slot.getItem();
            newStack = originalStack.copy();

            if (pIndex < 5) {
                if (!this.moveItemStackTo(originalStack, 5, 41, true)) {
                    return ItemStack.EMPTY;
                }
            } else {
                if (!this.moveItemStackTo(originalStack, 0, 5, false)) {
                    return ItemStack.EMPTY;
                }
            }

            if (originalStack.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }
        return newStack;
    }

    private void addPlayerInventory(Inventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l * 18, 90 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(Inventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 148));
        }
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return this.wolf.isAlive() && this.wolf.distanceTo(pPlayer) < 8.0F;
    }
}
