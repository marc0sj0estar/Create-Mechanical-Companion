package net.myr.createmechanicalcompanion.entity;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Vindicator;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class IllagerEngineer extends Vindicator {

    public IllagerEngineer(EntityType<? extends Vindicator> type, Level level) {
        super(type, level);
        // Set wrench immediately in constructor
        equipWrench();
    }

    private void equipWrench() {
        ItemStack wrench = new ItemStack(AllItems.WRENCH.get());
        this.setItemInHand(InteractionHand.MAIN_HAND, wrench);
        // Prevent dropping the weapon
        this.setDropChance(EquipmentSlot.MAINHAND, 0.0F);
    }

    @Override
    protected void populateDefaultEquipmentSlots(RandomSource pRandom, DifficultyInstance pDifficulty) {
        super.populateDefaultEquipmentSlots(pRandom, pDifficulty);
        equipWrench();
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData spawnData) {
        SpawnGroupData data = super.finalizeSpawn(level, difficulty, reason, spawnData);
        // Ensure wrench is equipped after spawn finalization
        equipWrench();
        return data;
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        // Re-equip wrench when loading from save
        equipWrench();
    }

    @Override
    public void tick() {
        super.tick();
        // Ensure the illager always has the wrench
        if (this.getMainHandItem().isEmpty() || !this.getMainHandItem().is(AllItems.WRENCH.get())) {
            equipWrench();
        }
    }

    public static AttributeSupplier.@NotNull Builder createAttributes() {
        return Vindicator.createAttributes()
                .add(Attributes.ATTACK_DAMAGE, 13.0D);
    }

    @Override
    protected void dropCustomDeathLoot(ServerLevel level, DamageSource source, boolean recentlyHit) {
        super.dropCustomDeathLoot(level, source, recentlyHit);
        this.spawnAtLocation(AllItems.CRUSHED_COPPER.get(), 1 + this.random.nextInt(3));
        this.spawnAtLocation(AllItems.CRUSHED_IRON.get(), 1 + this.random.nextInt(3));
        this.spawnAtLocation(AllItems.CRUSHED_ZINC.get(), 1 + this.random.nextInt(3));
        this.spawnAtLocation(AllBlocks.COGWHEEL.get(), 1 + this.random.nextInt(3));
    }

    @Override
    public boolean canBeLeader() {
        return false;
    }

    @Override
    public boolean removeWhenFarAway(double pDistanceToClosestPlayer) {
        return false;
    }
}
