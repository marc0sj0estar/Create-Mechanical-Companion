package net.myr.createmechanicalcompanion.item;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

public class CompanionFoundryItem extends BlockItem {
    public CompanionFoundryItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public InteractionResult place(BlockPlaceContext context) {
        Level level = context.getLevel();
        BlockPos center = context.getClickedPos();
        Direction widthDirection = context.getHorizontalDirection().getClockWise();

        for (int width = -1; width <= 1; width++) {
            BlockPos bottom = center.relative(widthDirection, width);
            BlockPos top = bottom.above();

            if (!level.getWorldBorder().isWithinBounds(bottom) || !level.getWorldBorder().isWithinBounds(top)
                    || !level.getBlockState(bottom).canBeReplaced(context) || !level.getBlockState(top).canBeReplaced(context)) {
                return InteractionResult.FAIL;
            }
        }

        return super.place(context);
    }




}
