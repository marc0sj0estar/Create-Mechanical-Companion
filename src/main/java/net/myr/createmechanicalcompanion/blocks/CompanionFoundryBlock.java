package net.myr.createmechanicalcompanion.blocks;

import com.simibubi.create.content.kinetics.base.*;
import com.simibubi.create.foundation.block.IBE;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class CompanionFoundryBlock extends HorizontalKineticBlock implements IBE<CompanionFoundryEntity>, IRotate {

    public enum Part implements StringRepresentable {
        BOTTOM_LEFT("bottom_left"),
        BOTTOM_CENTER("bottom_center"),
        BOTTOM_RIGHT("bottom_right"),
        TOP_LEFT("top_left"),
        TOP_CENTER("top_center"),
        TOP_RIGHT("top_right");

        private final String name;

        Part(String name) {
            this.name = name;
        }

        @Override
        public String getSerializedName() {
            return name;
        }
    }

    public static final EnumProperty<Part> PART =
            EnumProperty.create("part", Part.class);

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        if (state.getValue(PART) != Part.BOTTOM_CENTER) {
            return null;
        }
        return getBlockEntityType().create(pos, state);
    }

    public CompanionFoundryBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState(defaultBlockState().setValue(PART, Part.BOTTOM_CENTER));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder.add(PART));
    }


    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState state = super.getStateForPlacement(context);

        if (state == null)
            return null;

        BlockPos pos = context.getClickedPos();
        Direction facing = state.getValue(HORIZONTAL_FACING);
        Direction widthDirection = facing.getClockWise();

        for (int y = 0; y < 2; y++) {
            for (int x = -1; x <= 1; x++) {

                BlockPos target = pos.relative(widthDirection, x).above(y);

                if (!context.getLevel().getBlockState(target)
                        .canBeReplaced(context)) {
                    return null;
                }
            }
        }

        return state.setValue(PART, Part.BOTTOM_CENTER);
    }

    @Override
    public void setPlacedBy(Level worldIn, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        super.setPlacedBy(worldIn, pos, state, placer, stack);

        if (worldIn.isClientSide)
            return;

        Direction facing = state.getValue(HORIZONTAL_FACING);
        Direction widthDirection = facing.getClockWise();

        for (int y = 0; y < 2; y++) {
            for (int x = -1; x <= 1; x++) {

                if (x == 0 && y == 0)
                    continue;

                BlockPos target = pos.relative(widthDirection, x).above(y);

                Part part;

                if (y == 0) {
                    part = x == -1 ? Part.BOTTOM_LEFT : Part.BOTTOM_RIGHT;
                } else {
                    part = switch (x) {
                        case -1 -> Part.TOP_LEFT;
                        case 0 -> Part.TOP_CENTER;
                        default -> Part.TOP_RIGHT;
                    };
                }

                worldIn.setBlock(target,
                        state.setValue(PART, part),
                        Block.UPDATE_ALL);
            }
        }
    }

    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pIsMoving) {
        if (pState.is(this) && !pNewState.is(this) && !pLevel.isClientSide) {
            Direction widthDirection =
                    pState.getValue(HORIZONTAL_FACING).getClockWise();

            BlockPos centerPos = getBottomCenter(pPos, pState);

            for (int y = 0; y < 2; y++) {
                for (int x = -1; x <= 1; x++) {
                    BlockPos target = centerPos
                            .relative(widthDirection, x)
                            .above(y);

                    if (!target.equals(pPos)
                            && pLevel.getBlockState(target).is(this)) {
                        pLevel.setBlock(
                                target,
                                Blocks.AIR.defaultBlockState(),
                                Block.UPDATE_ALL
                        );
                    }
                }
            }
        }

        super.onRemove(pState, pLevel, pPos, pNewState, pIsMoving);
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
        if (!level.isClientSide() && player instanceof ServerPlayer serverPlayer) {
            BlockPos centerPos = getBottomCenter(pos, state);
            BlockEntity entity = level.getBlockEntity(centerPos);

            if (entity instanceof CompanionFoundryEntity foundry) {
                serverPlayer.openMenu(
                        new SimpleMenuProvider(foundry, Component.translatable("createmechanicalcompanion.ponder.companion_foundry.header")),
                        centerPos
                );
            }
        }

        return InteractionResult.SUCCESS;
    }

    @Override
    protected VoxelShape getShape(
            BlockState state,
            BlockGetter level,
            BlockPos pos,
            CollisionContext context) {

        return getCompanionFoundryVoxelShape(state, pos);
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return Shapes.block();
    }

    private VoxelShape getCompanionFoundryVoxelShape(BlockState state, BlockPos pos) {
        VoxelShape shape;
        BlockPos centerPosition = getBottomCenter(pos, state);
        if(state.getValue(HORIZONTAL_FACING).getAxis() == Direction.Axis.X){
            shape = box(0, 0, -16, 16, 32, 32);
        } else {
            shape = box(-16, 0, 0, 32, 32, 16);
        }
        return shape.move(
                centerPosition.getX() - pos.getX(),
                centerPosition.getY() - pos.getY(),
                centerPosition.getZ() - pos.getZ()
        );
    }

    public BlockPos getBottomCenter(BlockPos pos, BlockState state) {
        Direction widthDirection = state.getValue(HORIZONTAL_FACING).getClockWise();

        return switch (state.getValue(PART)) {
            case BOTTOM_LEFT -> pos.relative(widthDirection, 1);
            case BOTTOM_CENTER -> pos;
            case BOTTOM_RIGHT -> pos.relative(widthDirection, -1);
            case TOP_LEFT -> pos.relative(widthDirection, 1).below();
            case TOP_CENTER -> pos.below();
            case TOP_RIGHT -> pos.relative(widthDirection, -1).below();
        };
    }

}
