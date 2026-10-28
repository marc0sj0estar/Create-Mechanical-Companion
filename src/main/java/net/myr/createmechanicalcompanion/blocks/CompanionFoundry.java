package net.myr.createmechanicalcompanion.blocks;

import com.simibubi.create.content.kinetics.base.*;
import com.simibubi.create.content.kinetics.simpleRelays.ICogWheel;
import com.simibubi.create.foundation.block.IBE;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class CompanionFoundry extends HorizontalKineticBlock implements IBE<CompanionFoundryEntity>, IRotate {

    public CompanionFoundry(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    public Class<CompanionFoundryEntity> getBlockEntityClass() {
        return CompanionFoundryEntity.class;
    }

    @Override
    public BlockEntityType<? extends CompanionFoundryEntity> getBlockEntityType() {
        return ModBlockEntities.COMPANION_FOUNDRY_ENTITY.get();
    }

    @Override
    public boolean hasShaftTowards(LevelReader world, BlockPos pos, BlockState state, Direction face) {
        return face == state.getValue(HORIZONTAL_FACING).getOpposite();
    }

    @Override
    public Direction.Axis getRotationAxis(BlockState blockState) {
        return blockState.getValue(HORIZONTAL_FACING).getAxis();
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        useWithoutItem(state, level, pos, player, hitResult);
        return ItemInteractionResult.SUCCESS;
    }


    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (!level.isClientSide()) {
            BlockEntity entity = level.getBlockEntity(pos);
            if(entity instanceof CompanionFoundryEntity companionFoundryEntity) {
                ((ServerPlayer) player).openMenu(new SimpleMenuProvider(companionFoundryEntity, Component.literal("Test Block")), pos);
            } else {
                throw new IllegalStateException("ERROR");
            }
        }

        return InteractionResult.SUCCESS;
    }
}
