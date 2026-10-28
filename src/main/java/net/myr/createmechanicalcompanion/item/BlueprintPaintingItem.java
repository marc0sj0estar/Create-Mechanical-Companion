package net.myr.createmechanicalcompanion.item;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.decoration.PaintingVariant;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.myr.createmechanicalcompanion.entity.BlueprintPaintingEntity;
import net.myr.createmechanicalcompanion.entity.ModEntities;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BlueprintPaintingItem extends Item {

    public BlueprintPaintingItem(Properties properties) {
        super(properties);
    }

    @Override
    public void appendHoverText(ItemStack pStack, TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.translatable("item.createmechanicalcompanion.blueprint_painting_tooltip"));
        super.appendHoverText(pStack, pContext, pTooltipComponents, pIsAdvanced);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        Direction direction = context.getClickedFace();
        Player player = context.getPlayer();

        if (!direction.getAxis().isHorizontal()) {
            return InteractionResult.FAIL;
        }

        if(level.isClientSide){
            return InteractionResult.SUCCESS;
        }

        BlockPos anchor = context.getClickedPos().relative(context.getClickedFace());

        if (player != null && !player.mayUseItemAt(anchor, direction, context.getItemInHand())) {
            return InteractionResult.FAIL;
        }

        Registry<PaintingVariant> registry = level.registryAccess().registryOrThrow(Registries.PAINTING_VARIANT);

        List<ResourceKey<PaintingVariant>> keys = BlueprintPaintingVariants.VARIANTS;

        if (keys.isEmpty()) {
            return InteractionResult.FAIL;
        }

        List<BlueprintPaintingEntity> placeableBlueprints = new ArrayList<>();

        for (ResourceKey<PaintingVariant> key : keys) {
            Optional<Holder.Reference<PaintingVariant>> variant = registry.getHolder(key);

            if (variant.isEmpty()) {continue;}

            BlueprintPaintingEntity blueprintEntity = new BlueprintPaintingEntity(ModEntities.BLUEPRINT_ENTITY.get(), level);

            blueprintEntity.setFacing(direction);
            blueprintEntity.setVariant(variant.get());
            blueprintEntity.setPos(anchor.getX() + 0.5, anchor.getY() + 0.5, anchor.getZ() + 0.5);

            if (blueprintEntity.survives()) {
                placeableBlueprints.add(blueprintEntity);
            }
        }

        if (placeableBlueprints.isEmpty()) {
            return InteractionResult.FAIL;
        }

        BlueprintPaintingEntity picture = placeableBlueprints.get(level.getRandom().nextInt(placeableBlueprints.size()));

        if (!level.addFreshEntity(picture)) {
            return InteractionResult.FAIL;
        }

        picture.playPlacementSound();

        if (player == null || !player.isCreative()) {
            context.getItemInHand().shrink(1);
        }

        return InteractionResult.CONSUME;
    }

}


