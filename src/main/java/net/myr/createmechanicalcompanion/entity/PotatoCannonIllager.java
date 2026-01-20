package net.myr.createmechanicalcompanion.entity;

import com.simibubi.create.AllItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PotatoCannonIllager extends AbstractIllager {

    // Synced data for attack animation
    private static final EntityDataAccessor<Boolean> ATTACKING = SynchedEntityData.defineId(PotatoCannonIllager.class, EntityDataSerializers.BOOLEAN);

    public PotatoCannonIllager(EntityType<? extends AbstractIllager> type, Level level) {
        super(type, level);
        // Set potato cannon immediately in constructor
        equipPotatoCannon();
    }

    public boolean isAttacking() {
        return this.entityData.get(ATTACKING);
    }

    public void setAttacking(boolean attacking) {
        this.entityData.set(ATTACKING, attacking);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(ATTACKING, false);
    }

    private void equipPotatoCannon() {
        ItemStack potatoCannon = new ItemStack(AllItems.POTATO_CANNON.get());
        this.setItemInHand(InteractionHand.MAIN_HAND, potatoCannon);
        // Prevent dropping the weapon
        this.setDropChance(EquipmentSlot.MAINHAND, 0.0F);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(2, new AbstractIllager.RaiderOpenDoorGoal(this));
        this.goalSelector.addGoal(3, new Raider.HoldGroundAttackGoal(this, 10.0F));
        this.goalSelector.addGoal(4, new PotatoCannonAttackGoal(this));
        this.goalSelector.addGoal(8, new RandomStrollGoal(this, 0.6D));
        this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 15.0F, 1.0F));
        this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Mob.class, 15.0F));
        this.targetSelector.addGoal(1, (new HurtByTargetGoal(this, Raider.class)).setAlertOthers());
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, false));
    }

    @Override
    protected void populateDefaultEquipmentSlots(RandomSource pRandom, DifficultyInstance pDifficulty) {
        super.populateDefaultEquipmentSlots(pRandom, pDifficulty);
        equipPotatoCannon();
    }

    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData spawnData) {
        SpawnGroupData data = super.finalizeSpawn(level, difficulty, reason, spawnData);
        // Ensure potato cannon is equipped after spawn finalization
        equipPotatoCannon();
        return data;
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        // Re-equip potato cannon when loading from save
        equipPotatoCannon();
    }

    @Override
    public void tick() {
        super.tick();
        // Ensure the illager always has the potato cannon
        if (this.getMainHandItem().isEmpty() || !this.getMainHandItem().is(AllItems.POTATO_CANNON.get())) {
            equipPotatoCannon();
        }
        // Update attacking state for animation
        if (!this.level().isClientSide()) {
            this.setAttacking(this.getTarget() != null && this.getTarget().isAlive());
        }
    }

    @Override
    public AbstractIllager.@NotNull IllagerArmPose getArmPose() {
        if (isAttacking()) {
            return IllagerArmPose.CROSSBOW_HOLD;
        }
        return IllagerArmPose.CROSSBOW_CHARGE;
    }

    public static AttributeSupplier.@NotNull Builder createAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.35D)
                .add(Attributes.MAX_HEALTH, 24.0D)
                .add(Attributes.ATTACK_DAMAGE, 5.0D)
                .add(Attributes.FOLLOW_RANGE, 32.0D);
    }

    @Override
    public void applyRaidBuffs(ServerLevel level, int wave, boolean unusedFalse) {
    }

    @Override
    public boolean canBeLeader() {
        return false;
    }

    @Override
    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return false;
    }

    @Override
    public SoundEvent getCelebrateSound() {
        return SoundEvents.PILLAGER_CELEBRATE;
    }

    @Override
    protected void dropCustomDeathLoot(ServerLevel level, DamageSource source, boolean recentlyHit) {
        super.dropCustomDeathLoot(level, source, recentlyHit);
        this.spawnAtLocation(Items.POTATO, 3 + this.random.nextInt(5));
        this.spawnAtLocation(Items.BAKED_POTATO, 1 + this.random.nextInt(3));
    }
}
