package net.myr.createmechanicalcompanion.entity;

import com.simibubi.create.AllItems;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.entity.monster.Pillager;
import net.minecraft.world.entity.monster.Vindicator;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.npc.InventoryCarrier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.level.Level;
import net.myr.createmechanicalcompanion.PotatoCannonAttackGoal;


public class PotatoCannonIllager extends Pillager {



    private static final EntityDataAccessor<Boolean> ATTACKING = SynchedEntityData.defineId(PotatoCannonIllager.class, EntityDataSerializers.BOOLEAN);

    private final SimpleContainer inventory = new SimpleContainer(5);

    public PotatoCannonIllager(EntityType<? extends Pillager> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public boolean isAttacking() {
        return this.entityData.get(ATTACKING);
    }

    public void setAttacking(boolean attacking) {
        this.entityData.set(ATTACKING, attacking);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ATTACKING, false);
    }

    @Override
    protected void populateDefaultEquipmentSlots(RandomSource pRandom, DifficultyInstance pDifficulty) {
        super.populateDefaultEquipmentSlots(pRandom, pDifficulty);
        this.setItemInHand(InteractionHand.MAIN_HAND, new ItemStack(AllItems.POTATO_CANNON));
        this.inventory.setItem(0, new ItemStack(Items.POTATO, 64));
    }


    @Override
    public IllagerArmPose getArmPose() {
        if(isAttacking()){
            return IllagerArmPose.CROSSBOW_HOLD;
        }
        return IllagerArmPose.CROSSBOW_CHARGE;
    }

    @Override
    public ItemStack getProjectile(ItemStack pShootable) {
        return new ItemStack(Items.POTATO);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(2, new Raider.HoldGroundAttackGoal(this, 10.0F));
        this.goalSelector.addGoal(8, new RandomStrollGoal(this, 0.6D));
        this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 15.0F, 1.0F));
        this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Mob.class, 15.0F));
        this.targetSelector.addGoal(1, (new HurtByTargetGoal(this, Raider.class)).setAlertOthers());
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, false));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
        this.goalSelector.addGoal(3, new PotatoCannonAttackGoal(this));
    }

    @Override
    public void tick() {
        super.tick();
        if(!(level().isClientSide())){
            this.setAttacking(this.getTarget() != null && this.getTarget().isAlive());

        }

    }

    @Override
    public boolean canBeLeader() {
        return false;
    }

}
