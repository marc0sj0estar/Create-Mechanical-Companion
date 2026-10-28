package net.myr.createmechanicalcompanion.entity;

import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.Painting;
import net.minecraft.world.entity.decoration.PaintingVariant;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.myr.createmechanicalcompanion.item.ModItems;
import org.jetbrains.annotations.Nullable;

public class BlueprintPaintingEntity extends Painting {
    public BlueprintPaintingEntity(
            EntityType<? extends BlueprintPaintingEntity> type,
            Level level
    ) {
        super(type, level);
    }

    @Override
    public void dropItem(Entity breaker) {
        if(level().isClientSide || (breaker instanceof Player player && player.isCreative())) {
            return;
        }
        spawnAtLocation(new ItemStack(ModItems.BLUEPRINT_PAINTING_ITEM.get()));
    }

    public void setFacing(Direction direction){
        setDirection(direction);
    }
}
