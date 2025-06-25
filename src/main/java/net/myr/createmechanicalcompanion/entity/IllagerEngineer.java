package net.myr.createmechanicalcompanion.entity;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllItems;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.MoveTowardsRestrictionGoal;
import net.minecraft.world.entity.monster.Vindicator;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class IllagerEngineer extends Vindicator {

    public IllagerEngineer(EntityType<? extends Vindicator> type, Level level) {
        super(type, level);
    }

    @Override
    protected void populateDefaultEquipmentSlots(RandomSource pRandom, DifficultyInstance pDifficulty) {
        super.populateDefaultEquipmentSlots(pRandom, pDifficulty);
        this.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(AllItems.WRENCH));
    }

    public static AttributeSupplier.@NotNull Builder createAttributes() {
        return Vindicator.createAttributes()
                .add(Attributes.ATTACK_DAMAGE, 13.0D);
    }

    @Override
    protected void dropCustomDeathLoot(net.minecraft.world.damagesource.DamageSource source, int looting, boolean recentlyHit) {
        super.dropCustomDeathLoot(source, looting, recentlyHit);
        this.spawnAtLocation(AllItems.CRUSHED_COPPER.get(), 1 + this.random.nextInt(2 + looting));
        this.spawnAtLocation(AllItems.CRUSHED_IRON.get(), 1 + this.random.nextInt(2 + looting));
        this.spawnAtLocation(AllItems.CRUSHED_ZINC.get(), 1 + this.random.nextInt(2 + looting));
        this.spawnAtLocation(AllBlocks.COGWHEEL, 1 + this.random.nextInt(2 + looting));
    }

    @Override
    public boolean canBeLeader() {
        return false;
    }
}
