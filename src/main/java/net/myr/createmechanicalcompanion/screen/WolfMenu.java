package net.myr.createmechanicalcompanion.screen;

import net.minecraft.client.Minecraft;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.SlotItemHandler;
import net.myr.createmechanicalcompanion.entity.CustomWolf;
import net.myr.createmechanicalcompanion.sounds.ModSounds;
import net.myr.createmechanicalcompanion.item.ModItems;

import org.jetbrains.annotations.NotNull;

public class WolfMenu extends AbstractContainerMenu {

    public final CustomWolf wolf;

    public static final int slotAmount = 9;
    private int firstColumnXPosition = 84;
    private int secondColumnXPosition = 108;
    private int thirdColumnXPosition = 132;
    private int firstRowYPosition = 10;
    private int secondRowYPosition = 32;
    private int thirdRowYPosition = 54;

    public WolfMenu(int pContainerId, Inventory inv, RegistryFriendlyByteBuf extraData) {
        this(pContainerId, inv, getWolfEntity(extraData, inv.player.level()), new SimpleContainerData(slotAmount));
    }

    private static CustomWolf getWolfEntity(RegistryFriendlyByteBuf buf, Level level) {
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
        this.wolf = wolf;
        
        addWolfInventorySlots();

        addPlayerInventory(playerInventory);
        addPlayerHotbar(playerInventory);
    }

    private void addWolfInventorySlots() {
        IItemHandler itemHandler = wolf.getItemHandler();
        if (itemHandler == null) {
            System.err.println("ItemHandler is null!");
            return;
        }

        // Defense slot (0, 0)
        this.addSlot(new SlotItemHandler(itemHandler, 0, firstColumnXPosition, firstRowYPosition) {
            @Override
            public boolean mayPlace(@NotNull ItemStack stack) {
                return (stack.getItem() == ModItems.REINFORCED_PLATES.get() ||
                        stack.getItem() == ModItems.NETHERITE_PLATES.get());
            }

            @Override
            public void setByPlayer(ItemStack pStack, ItemStack pOldStack) {
                playInsertSound();
                super.setByPlayer(pStack, pOldStack);
            }
        });
        

        // Attack slots Row 2

        this.addSlot(new SlotItemHandler(itemHandler, 1, firstColumnXPosition, secondRowYPosition) {
            @Override
            public boolean mayPlace(@NotNull ItemStack stack) {
                return (stack.getItem() == ModItems.MOUNTED_CROSSBOW.get() ||
                        stack.getItem() == ModItems.SMELTING_FANGS.get() ||
                        stack.getItem() == ModItems.TESLA_TAIL.get());
            }

            @Override
            public void setByPlayer(ItemStack pStack, ItemStack pOldStack) {
                playInsertSound();
                super.setByPlayer(pStack, pOldStack);
            }
        });

        this.addSlot(new SlotItemHandler(itemHandler, 2, secondColumnXPosition, secondRowYPosition) {
            @Override
            public boolean mayPlace(@NotNull ItemStack stack) {
                return (stack.getItem() == ModItems.MOUNTED_CROSSBOW.get() ||
                        stack.getItem() == ModItems.SMELTING_FANGS.get() ||
                        stack.getItem() == ModItems.TESLA_TAIL.get());
            }

            @Override
            public void setByPlayer(ItemStack pStack, ItemStack pOldStack) {
                playInsertSound();
                super.setByPlayer(pStack, pOldStack);
            }
        });
        
        this.addSlot(new SlotItemHandler(itemHandler, 3, thirdColumnXPosition, secondRowYPosition) {
            @Override
            public boolean mayPlace(@NotNull ItemStack stack) {
                return (stack.getItem() == ModItems.MOUNTED_CROSSBOW.get() ||
                        stack.getItem() == ModItems.SMELTING_FANGS.get() ||
                        stack.getItem() == ModItems.TESLA_TAIL.get());
            }

            @Override
            public void setByPlayer(ItemStack pStack, ItemStack pOldStack) {
                playInsertSound();
                super.setByPlayer(pStack, pOldStack);
            }
        });
        
        // Movement slots (0, 1), (0, 2)
        this.addSlot(new SlotItemHandler(itemHandler, 4, secondColumnXPosition, firstRowYPosition) {
            @Override
            public boolean mayPlace(@NotNull ItemStack stack) {
                return (stack.getItem() == ModItems.BOOSTER_ROCKET.get() ||
                        stack.getItem() == ModItems.QUANTUM_DRIVE.get());
            }

            @Override
            public void setByPlayer(ItemStack pStack, ItemStack pOldStack) {
                playInsertSound();
                super.setByPlayer(pStack, pOldStack);
            }
        });
        
        this.addSlot(new SlotItemHandler(itemHandler, 5, thirdColumnXPosition, firstRowYPosition) {
            @Override
            public boolean mayPlace(@NotNull ItemStack stack) {
                return (stack.getItem() == ModItems.BOOSTER_ROCKET.get() ||
                        stack.getItem() == ModItems.QUANTUM_DRIVE.get());
            }

            @Override
            public void setByPlayer(ItemStack pStack, ItemStack pOldStack) {
                playInsertSound();
                super.setByPlayer(pStack, pOldStack);
            }
        });
        
        // Utility slots Third Row
        this.addSlot(new SlotItemHandler(itemHandler, 6, firstColumnXPosition, thirdRowYPosition) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return (stack.getItem() == ModItems.REGENERATIVE_CASING.get() ||
                        stack.getItem() == ModItems.MOB_RADAR.get() ||
                        stack.getItem() == ModItems.MOUNTED_LIGHT.get());
            }

            @Override
            public void setByPlayer(ItemStack pStack, ItemStack pOldStack) {
                playInsertSound();
                super.setByPlayer(pStack, pOldStack);
            }
        });
        
        this.addSlot(new SlotItemHandler(itemHandler, 7, secondColumnXPosition, thirdRowYPosition) {
            @Override
            public boolean mayPlace(@NotNull ItemStack stack) {
                return (stack.getItem() == ModItems.REGENERATIVE_CASING.get() ||
                        stack.getItem() == ModItems.MOB_RADAR.get() ||
                        stack.getItem() == ModItems.MOUNTED_LIGHT.get());
            }

            @Override
            public void setByPlayer(ItemStack pStack, ItemStack pOldStack) {
                playInsertSound();
                super.setByPlayer(pStack, pOldStack);
            }
        });
        
        this.addSlot(new SlotItemHandler(itemHandler, 8, thirdColumnXPosition, thirdRowYPosition) {
            @Override
            public boolean mayPlace(@NotNull ItemStack stack) {
                return (stack.getItem() == ModItems.REGENERATIVE_CASING.get() ||
                        stack.getItem() == ModItems.MOB_RADAR.get() ||
                        stack.getItem() == ModItems.MOUNTED_LIGHT.get());
            }

            @Override
            public void setByPlayer(ItemStack pStack, ItemStack pOldStack) {
                playInsertSound();
                super.setByPlayer(pStack, pOldStack);
            }
        });

    }

    private void playInsertSound() {
        if (wolf.level().isClientSide) {
            float pitch = 0.95F + wolf.getRandom().nextFloat() * 0.1F;
            Minecraft.getInstance().level.playLocalSound(wolf.blockPosition(), ModSounds.EQUIP_MODULE.get(), SoundSource.PLAYERS, 0.8F, pitch, false);
        }
    }

    @Override
    public ItemStack quickMoveStack(Player playerIn, int pIndex) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(pIndex);

        if (slot != null && slot.hasItem()) {
            ItemStack originalStack = slot.getItem();
            newStack = originalStack.copy();

            if (pIndex < slotAmount) {
                if (!this.moveItemStackTo(originalStack, slotAmount, slotAmount + 36, true)) {
                    return ItemStack.EMPTY;
                }
            } else {
                if (!this.moveItemStackTo(originalStack, 0, slotAmount, false)) {
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
