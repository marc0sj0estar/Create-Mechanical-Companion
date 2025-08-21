package net.myr.createmechanicalcompanion;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.decoration.PaintingVariant;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.RegistryObject;
import net.myr.createmechanicalcompanion.entity.ModEntity;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BlueprintPaintingItem extends Item {

    public BlueprintPaintingItem(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.translatable("item.createmechanicalcompanion.blueprint_painting_tooltip"));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        Level level = pContext.getLevel();
        BlockPos pos = pContext.getClickedPos();
        Direction face = pContext.getClickedFace();

        if (face == Direction.DOWN || face == Direction.UP) {
            return InteractionResult.FAIL;
        }

        if (!level.isClientSide) {
            BlockPos placePos = pos.relative(face);
            List<Holder<PaintingVariant>> variants = BlueprintPaintingVariants.getAllVariants();

            for (Holder<PaintingVariant> variant : variants) {
                BlueprintPaintingEntity painting = ModEntity.BLUEPRINT_ENTITY.get().create(level);
                painting.setVariant(variant);
                painting.setDirectionPublic(face);
                painting.moveTo(
                        placePos.getX() + 0.5,
                        placePos.getY() + 0.5,
                        placePos.getZ() + 0.5,
                        face.toYRot(),
                        0.0F
                );
                if (painting.survives()) {
                    level.addFreshEntity(painting);
                    if (!pContext.getPlayer().getAbilities().instabuild) {
                        pContext.getItemInHand().shrink(1);
                    }
                    return InteractionResult.SUCCESS;
                }
            }
            return InteractionResult.FAIL;
        }
        return InteractionResult.CONSUME;

    }


}
