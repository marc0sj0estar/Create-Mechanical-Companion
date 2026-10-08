package net.myr.createmechanicalcompanion.blocks;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.myr.createmechanicalcompanion.item.ModItems;
import net.myr.createmechanicalcompanion.screen.TestBlockMenu;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.util.Set;

public class TestBlockEntity extends KineticBlockEntity implements MenuProvider {

    protected final ContainerData data;
    private int progress = 0;
    private int buildingStep = 0;
    private static final Set<Fluid> ALLOWED_FLUIDS = Set.of(Fluids.WATER, Fluids.LAVA);
    private static final int MINIMUM_SPEED = 32;
    private static final int PROCESSING_TIME = 200;

    public final FluidTank fluidTank = new FluidTank(1000, fluidStack -> ALLOWED_FLUIDS.contains(fluidStack.getFluid())) {
        @Override
        protected void onContentsChanged() {
            setChanged();
            if(level != null && !level.isClientSide()) {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }
    };

    public final ItemStackHandler itemHandler = new ItemStackHandler(2) {

        @Override
        public boolean isItemValid(int slot, ItemStack stack) {
            if(slot == 0){
                return switch (buildingStep) {
                    case 0 -> stack.is(ModItems.MECHANICAL_WOLF_CHASSIS.get());
                    case 1 -> stack.is(ModItems.MECHANICAL_WOLF_PROCESSOR.get());
                    case 2 -> stack.is(AllBlocks.REDSTONE_LINK.asItem());
                    default -> false;
                };
            }
            if(slot == 1){return false;}
            return super.isItemValid(slot, stack);
        }

        @Override
        protected int getStackLimit(int slot, ItemStack stack) {
            return 1;
        }

        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if(level != null && !level.isClientSide()) {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }

    };

    @Override
    public boolean isSpeedRequirementFulfilled() {
        return Math.abs(getSpeed()) >= MINIMUM_SPEED;
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new TestBlockMenu(i, inventory, this, this.data);
    }

    public TestBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        data = new ContainerData() {
            @Override
            public int get(int i) {
                return switch (i) {
                    case 0 -> TestBlockEntity.this.progress;
                    case 1 -> TestBlockEntity.this.buildingStep;
                    case 2 -> TestBlockEntity.this.fluidTank.getFluidAmount();
                    case 3 -> TestBlockEntity.this.fluidTank.getCapacity();
                    default -> 0;
                };
            }

            @Override
            public void set(int i, int value) {
                switch (i) {
                    case 0 -> TestBlockEntity.this.progress = value;
                    case 1 -> TestBlockEntity.this.buildingStep = value;
                    case 2, 3 -> {}
                }
            }

            @Override
            public int getCount() {
                return 4;
            }
        };
    }


    @Override
    public @NotNull Component getDisplayName() {
        return Component.literal("Test");
    }

    @Override
    protected void write(CompoundTag compound, HolderLookup.Provider registries, boolean clientPacket) {
        super.write(compound, registries, clientPacket);
        compound.putInt("progress", progress);
        compound.putInt("buildingStep", buildingStep);
        compound.put("inventory", itemHandler.serializeNBT(registries));
        CompoundTag fluidTag = new CompoundTag();
        fluidTank.writeToNBT(registries, fluidTag);
        compound.put("fluid", fluidTag);
    }

    @Override
    protected void read(CompoundTag compound, HolderLookup.Provider registries, boolean clientPacket) {
        super.read(compound, registries, clientPacket);
        progress = compound.getInt("progress");
        buildingStep = compound.getInt("buildingStep");
        if (compound.contains("inventory")) {
            itemHandler.deserializeNBT(registries, compound.getCompound("inventory"));
        }
        if (compound.contains("fluid")) {
            fluidTank.readFromNBT(registries, compound.getCompound("fluid"));
        }
    }

    private void advanceProgress(){
        progress += Math.round(getSpeed()/MINIMUM_SPEED);
    }

    @Override
    public void tick() {
        super.tick();
        if(level == null || level.isClientSide() || !isSpeedRequirementFulfilled() || !itemHandler.getStackInSlot(1).isEmpty()) {return;}

        switch (buildingStep) {
            case 0:
                if(itemHandler.getStackInSlot(0).is(ModItems.MECHANICAL_WOLF_CHASSIS.get())) {advanceProgress();}
                break;
            case 1:
                if(itemHandler.getStackInSlot(0).is(ModItems.MECHANICAL_WOLF_PROCESSOR.get())) {advanceProgress();}
                break;
            case 2:
                if(itemHandler.getStackInSlot(0).is(AllBlocks.REDSTONE_LINK.asItem())) {advanceProgress();}
                break;
            case 3:
                if(fluidTank.isEmpty()) {return;}
                if(fluidTank.getFluid().is(Fluids.LAVA) && fluidTank.getFluidAmount() >= 1000) {advanceProgress();}
                break;
            case 4:
                itemHandler.setStackInSlot(1, new ItemStack(ModItems.MECHANICAL_WOLF_LINK.get()));
                progress = 0;
                buildingStep = 0;
                break;
            default:
                buildingStep = 0;
                break;
        }

        if(progress >= PROCESSING_TIME){
            progress = 0;
            itemHandler.extractItem(0, 1, false);
            if(buildingStep == 3) {
                fluidTank.drain(fluidTank.getFluidAmount(), IFluidHandler.FluidAction.EXECUTE);
            }
            buildingStep++;
        }

        setChanged();
    }
}
