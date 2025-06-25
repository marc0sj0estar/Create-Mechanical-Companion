package net.myr.createmechanicalcompanion;

import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.Painting;
import net.minecraft.world.entity.decoration.PaintingVariant;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.myr.createmechanicalcompanion.item.ModItems;
import org.jetbrains.annotations.Nullable;

public class BlueprintPaintingEntity extends Painting {
    public BlueprintPaintingEntity(EntityType<? extends Painting> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    public void setVariant(Holder<PaintingVariant> pVariant) {
        super.setVariant(pVariant);
    }

    public void setDirectionPublic(Direction direction) {
        this.setDirection(direction);
    }


    @Override
    public ItemStack getPickResult() {
        return new ItemStack(ModItems.BLUEPRINT_PAINTING_ITEM.get());
    }

    @Override
    public void dropItem(@Nullable Entity pBrokenEntity) {
        if (this.level().getGameRules().getBoolean(GameRules.RULE_DOENTITYDROPS)) {
            this.playSound(SoundEvents.PAINTING_BREAK, 1.0F, 1.0F);
            if (pBrokenEntity instanceof Player) {
                Player player = (Player)pBrokenEntity;
                if (player.getAbilities().instabuild) {
                    return;
                }
            }

            this.spawnAtLocation(ModItems.BLUEPRINT_PAINTING_ITEM.get());
        }
    }


}
