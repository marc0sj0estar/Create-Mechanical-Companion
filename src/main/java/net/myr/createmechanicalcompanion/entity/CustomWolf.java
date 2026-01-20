package net.myr.createmechanicalcompanion.entity;

import com.simibubi.create.AllItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.*;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LightBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.myr.createmechanicalcompanion.ModConfig;
import net.myr.createmechanicalcompanion.sounds.ModSounds;
import net.myr.createmechanicalcompanion.item.ModItems;
import net.myr.createmechanicalcompanion.screen.WolfMenu;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;

import java.util.List;
import java.util.Optional;

public class CustomWolf extends Wolf implements MenuProvider {

    private static final int DEFENSIVE_SLOT = 0;
    private static final int ATTACK_SLOT = 1;
    private static final int ATTACK_SLOT2 = 2;
    private static final int MOVEMENT_SLOT = 3;
    private static final int MOVEMENT_SLOT2 = 4;
    private static final int UTILITY_SLOT = 5;
    private static final int UTILITY_SLOT2 = 6;
    private static final int UTILITY_SLOT3 = 7;
    private static final int UTILITY_SLOT4 = 8;

    private static final double movementSpeed = 0.35D;

    private BlockPos previousLightPos = null;
    private float currentTorchTick = 0;

    private float mountedCrossbowTick = 0;

    private float boosterTimer = 0;
    private float boosterAnimationTick = 0;
    private float quantumDriveTimer = 0;
    private float quantumDriveParticleTimer = 0;
    private float mobRadarTimer = 0;
    private final int slotAmount = 9;

    private ItemStackHandler itemHandler = new ItemStackHandler(slotAmount);

    protected final ContainerData data;

    private static final EntityDataAccessor<CompoundTag> ITEM_HANDLER_DATA = SynchedEntityData.defineId(CustomWolf.class, EntityDataSerializers.COMPOUND_TAG);

    public CustomWolf(EntityType<? extends Wolf> entityType, Level level) {
        super(entityType, level);

        data = new ContainerData() {
            private final int[] values = new int[slotAmount];

            @Override
            public int get(int pIndex) {
                if (pIndex >= 0 && pIndex < values.length) {
                    return values[pIndex];
                }
                throw new IndexOutOfBoundsException("Invalid index for ContainerData: " + pIndex);
            }

            @Override
            public void set(int pIndex, int pValue) {
                if (pIndex >= 0 && pIndex < values.length) {
                    values[pIndex] = pValue;
                } else {
                    throw new IndexOutOfBoundsException("Invalid index for ContainerData: " + pIndex);
                }
            }

            @Override
            public int getCount() {
                return values.length;
            }
        };
    }

    /**
     * Custom taming method to ensure the wolf is properly set up as a tamed companion.
     * This handles 1.21.1 taming requirements including owner UUID and tame flag.
     */
    public void tameToPlayer(Player player) {
        this.setTame(true, true); // Set tame with sound
        this.setOwnerUUID(player.getUUID());
        this.setTarget(null);
        this.navigation.stop();
        // Ensure the wolf is in a valid state for combat
        this.setOrderedToSit(false);
        this.setPersistenceRequired();
    }

    @Override
    public boolean wantsToAttack(LivingEntity pTarget, LivingEntity pOwner) {
        if (!isBlacklisted(pTarget)) {
            if (pTarget instanceof Wolf) {
                Wolf wolf = (Wolf)pTarget;
                return !wolf.isTame() || wolf.getOwner() != pOwner;
            } else if (pTarget instanceof Player && pOwner instanceof Player && !((Player)pOwner).canHarmPlayer((Player)pTarget)) {
                return false;
            } else if (pTarget instanceof AbstractHorse && ((AbstractHorse)pTarget).isTamed()) {
                return false;
            } else {
                return !(pTarget instanceof TamableAnimal) || !((TamableAnimal)pTarget).isTame();
            }
        } else {
            return false;
        }
    }

    public static AttributeSupplier.@NotNull Builder createAttributes() {
        return Wolf.createAttributes()
                .add(Attributes.MAX_HEALTH, 30.0D)
                .add(Attributes.MOVEMENT_SPEED, movementSpeed)
                .add(Attributes.ATTACK_DAMAGE, 6.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0)
                .add(Attributes.FOLLOW_RANGE, 16.0D)
                .add(Attributes.ARMOR, 0.0D);
    }

    @Override
    protected double getDefaultGravity() {
        return 0.08D;
    }

    @Override
    public void load(CompoundTag pCompound) {
        super.load(pCompound);
        if(pCompound.contains("previousLightPosition"))
        {
            int[] coordinates = pCompound.getIntArray("previousLightPosition");
            previousLightPos = new BlockPos(coordinates[0], coordinates[1], coordinates[2]);
        }
        if (pCompound.contains("inventory")) {
            itemHandler.deserializeNBT(this.registryAccess(), pCompound.getCompound("inventory"));
        }
        removeLightBlocksAround(this, 3);
    }

    public boolean isBlacklisted(Entity entity) {
        ResourceLocation id = BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType());
        if (id == null) return false;
        return ModConfig.COMMON.wolfBlacklist.get().contains(id.toString());
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        pCompound.put("inventory", itemHandler.serializeNBT(this.registryAccess()));
        if(previousLightPos != null)
        {
            int[] previousLightPositionCoordinates = {previousLightPos.getX(), previousLightPos.getY(), previousLightPos.getZ()};
            pCompound.putIntArray("previousLightPosition", previousLightPositionCoordinates);
        }
        super.addAdditionalSaveData(pCompound);
    }

    public ItemStackHandler getItemHandler() {
        return this.itemHandler;
    }

    public void setItemHandler(ItemStackHandler handler) {
        if (handler != null) {
            this.itemHandler = handler;
            syncItemHandler();
        }
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new WolfMenu(pContainerId, pPlayerInventory, this, this.data);
    }

    @Override
    public @NotNull InteractionResult mobInteract(Player player, InteractionHand hand) {
        if (!this.level().isClientSide && player instanceof ServerPlayer serverPlayer) {
            if(player.getItemInHand(hand).getItem() == AllItems.WRENCH.get()){
                this.heal(ModConfig.COMMON.wrenchHealAmount.get().floatValue());
                this.level().playSound(this, this.blockPosition(), SoundEvents.IRON_GOLEM_REPAIR, SoundSource.NEUTRAL, 1, 1);
                return InteractionResult.SUCCESS;
            }

            if(this.getOwner() != null && player.getUUID().equals(this.getOwner().getUUID()))
            {
                serverPlayer.openMenu(this, buf -> buf.writeVarInt(this.getId()));
            }else{
                serverPlayer.displayClientMessage(Component.translatable("entity.createmechanicalcompanion.ownership_warning"), true);
            }
        }
        return InteractionResult.SUCCESS;
    }

    public boolean isModuleEquipped(Item item){
        if(itemHandler.getSlots() != slotAmount){
            return false;
        }

        for (int i = 0; i < slotAmount; i++) {
            if (itemHandler.getStackInSlot(i).getItem() == item) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(4, new CustomLeapAtTargetGoal(this, 0.3F));
        this.goalSelector.addGoal(5, new MeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.addGoal(6, new FollowOwnerGoal(this, 1.0D, 10.0F, 2.0F));
        this.goalSelector.addGoal(8, new StrollUnlessMenuOpenGoal(this, 0.6D));
        this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(10, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
        this.targetSelector.addGoal(3, (new HurtByTargetGoal(this)).setAlertOthers());
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, true, this::isAngryAt));
        // Add proactive hostile mob targeting - wolf will attack nearby hostile mobs
        this.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(this, Monster.class, 5, false, false, 
            (target) -> target instanceof LivingEntity && this.getOwner() != null && this.wantsToAttack((LivingEntity) target, this.getOwner())));
        this.targetSelector.addGoal(8, new ResetUniversalAngerTargetGoal<>(this, true));
    }

    @Override
    public void tick() {
        super.tick();
        double boosterSpeedIncrease = ModConfig.COMMON.boosterRocketSpeedIncrease.get();
        if (!this.level().isClientSide) {
            if(getOwner() == null){
                discard();
            }

            checkForDuplicate();

            double defaultHealthValue = 30;
            double defaultArmorValue = 0;
            
            if(isModuleEquipped(ModItems.REINFORCED_PLATES.get()))
            {
                double reinforcedPlatesHealthIncrease = ModConfig.COMMON.reinforcedPlatesHealthIncrease.get();
                int reinforcedArmorValue = ModConfig.COMMON.reinforcedPlatesArmorValue.get();
                if(this.getAttribute(Attributes.MAX_HEALTH).getBaseValue() != (defaultHealthValue + reinforcedPlatesHealthIncrease)) {
                    this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(defaultHealthValue + reinforcedPlatesHealthIncrease);
                    this.getAttribute(Attributes.KNOCKBACK_RESISTANCE).setBaseValue(ModConfig.COMMON.reinforcedPlatesKnockBackResistance.get());
                }
                // Set armor attribute for 1.21.1
                if(this.getAttribute(Attributes.ARMOR).getBaseValue() != reinforcedArmorValue) {
                    this.getAttribute(Attributes.ARMOR).setBaseValue(reinforcedArmorValue);
                }
            }
            else if(isModuleEquipped(ModItems.NETHERITE_PLATES.get()))
            {
                double netheritePlatesHealthIncrease = ModConfig.COMMON.netheritePlatesHealthIncrease.get();
                int netheriteArmorValue = ModConfig.COMMON.netheritePlatesArmorValue.get();
                if(this.getAttribute(Attributes.MAX_HEALTH).getBaseValue() != (defaultHealthValue + netheritePlatesHealthIncrease)) {
                   this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(defaultHealthValue + netheritePlatesHealthIncrease);
                    this.getAttribute(Attributes.KNOCKBACK_RESISTANCE).setBaseValue(ModConfig.COMMON.netheritePlatesKnockBackResistance.get());
               }
                // Set armor attribute for 1.21.1
                if(this.getAttribute(Attributes.ARMOR).getBaseValue() != netheriteArmorValue) {
                    this.getAttribute(Attributes.ARMOR).setBaseValue(netheriteArmorValue);
                }
            }else{
                if(this.getAttribute(Attributes.MAX_HEALTH).getBaseValue() != defaultHealthValue) {
                    this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(defaultHealthValue);
                    this.getAttribute(Attributes.KNOCKBACK_RESISTANCE).setBaseValue(0);
                    if(this.getHealth() > this.getMaxHealth())
                    {
                        this.setHealth(this.getMaxHealth());
                    }
                }
                // Reset armor when no plates equipped
                if(this.getAttribute(Attributes.ARMOR).getBaseValue() != defaultArmorValue) {
                    this.getAttribute(Attributes.ARMOR).setBaseValue(defaultArmorValue);
                }
            }

            if (isModuleEquipped(ModItems.REGENERATIVE_CASING.get()) && this.getHealth() < this.getMaxHealth()) {
                this.heal(ModConfig.COMMON.regenerativeCasingHealAmount.get().floatValue());
            }

            if (isModuleEquipped(ModItems.MOUNTED_LIGHT.get())) {
                BlockPos torchPosition = new BlockPos(this.getBlockX(), this.getBlockY() + 1, this.getBlockZ());
                if(torchPosition != previousLightPos)
                {
                    removePreviousLightBlock();
                    if(this.level().getBlockState(torchPosition).isAir() && this.isAlive())
                    {
                        this.level().setBlockAndUpdate(torchPosition, Blocks.LIGHT.defaultBlockState().setValue(LightBlock.LEVEL, 15));
                        previousLightPos = torchPosition;
                    }
                }
            }else{
                removePreviousLightBlock();
            }

            float mountedCrossbowCooldown = ModConfig.COMMON.mountedCrossbowCooldown.get();
            if(isModuleEquipped(ModItems.MOUNTED_CROSSBOW.get()) && this.getTarget() != null && mountedCrossbowTick >= mountedCrossbowCooldown){
                mountedCrossbowTick = 0;
                TargetedArrowEntity arrow = new TargetedArrowEntity(this.level(), this, this.getTarget());
                arrow.setBaseDamage(4);
                // Knockback handled differently in 1.21.1

                Vec3 direction = this.getTarget().getEyePosition().subtract(this.getEyePosition());
                direction = direction.normalize();

                arrow.setPos(this.getX(), this.getEyeY() + 0.5, this.getZ());
                arrow.shoot(direction.x, direction.y, direction.z, 1.6F, 0.0F);
                float pitch = 0.8F + this.random.nextFloat() * 0.4F;
                this.level().playSound(this, this.blockPosition(), ModSounds.MOUNTED_CROSSBOW_SOUND.get(), SoundSource.HOSTILE, 0.15f, pitch);

                this.level().addFreshEntity(arrow);
            }
            else if(mountedCrossbowTick < mountedCrossbowCooldown){
                mountedCrossbowTick++;
            }

            AttributeInstance movementAttribute = this.getAttribute(Attributes.MOVEMENT_SPEED);

            float boosterCooldown = ModConfig.COMMON.boosterRocketCooldown.get();
            if(isModuleEquipped(ModItems.BOOSTER_ROCKET.get()) && this.getTarget() != null && boosterTimer >= boosterCooldown){
                if(movementAttribute.getBaseValue() == movementSpeed){
                    movementAttribute.setBaseValue(movementSpeed + boosterSpeedIncrease);
                    boosterTimer = 0;
                }
            }else{
                float boosterDuration = ModConfig.COMMON.boosterRocketDuration.get();
                if(boosterTimer >= boosterDuration && movementAttribute.getBaseValue() != movementSpeed){
                    movementAttribute.setBaseValue(movementSpeed);
                }
            }

            if(boosterTimer < boosterCooldown){
                boosterTimer++;
            }

            float quantumDriveCooldown = ModConfig.COMMON.quantumDriveCooldown.get();
            if(quantumDriveTimer >= quantumDriveCooldown && this.getTarget() != null && isModuleEquipped(ModItems.QUANTUM_DRIVE.get())) {
                Entity target = this.getTarget();
                Vec3 targetPosition = target.position();
                int teleportX = (int) (targetPosition.x + (random.nextDouble() - 0.5) * 2);
                int teleportY = (int) targetPosition.y;
                int teleportZ = (int) (targetPosition.z + (random.nextDouble() - 0.5) * 2);

                if (this.level().isEmptyBlock(new BlockPos(teleportX, teleportY, teleportZ))) {
                    this.teleportTo(teleportX, teleportY, teleportZ);
                    this.level().playSound(null, this.blockPosition(), SoundEvents.ENDERMAN_TELEPORT, SoundSource.HOSTILE, 1.0F, 1.0F);
                }
                quantumDriveTimer = 0;
            }
            else if(quantumDriveTimer < quantumDriveCooldown){
                quantumDriveTimer++;
            }

            float mobRadarCooldown = ModConfig.COMMON.mobRadarCooldown.get();
            if(mobRadarTimer >= mobRadarCooldown && isModuleEquipped(ModItems.MOB_RADAR.get()))
            {
                applyGlowingToHostileMobs();
                mobRadarTimer = 0;
            }else if(mobRadarTimer < mobRadarCooldown){
                mobRadarTimer++;
            }

                syncItemHandler();
        }else{

            if(isModuleEquipped(ModItems.MOUNTED_LIGHT.get())) {
                spawnMountedTorchParticles();
            }

            if(this.getAttribute(Attributes.MOVEMENT_SPEED).getBaseValue() == movementSpeed + boosterSpeedIncrease){
                spawnRocketBoostParticles();
            }
            float quantumDriveParticleCooldown = 30;
            if(isModuleEquipped(ModItems.QUANTUM_DRIVE.get()) && (quantumDriveParticleTimer >= quantumDriveParticleCooldown))
            {
                spawnQuantumDriveParticles();
                quantumDriveParticleTimer = 0;
            }else{
                quantumDriveParticleTimer++;
            }
        }
    }


    private void applyGlowingToHostileMobs() {
        float radarRadius = ModConfig.COMMON.mobRadarRange.get();
        List<Entity> nearbyEntities = level().getEntities(this, this.getBoundingBox().inflate(radarRadius), entity -> entity instanceof Mob mob && mob.getType().getCategory() == MobCategory.MONSTER);

        for (Entity entity : nearbyEntities) {
            if(entity instanceof LivingEntity livingEntity)
            {
                int mobRadarPingDuration = 60;
                livingEntity.addEffect(new MobEffectInstance(MobEffects.GLOWING, mobRadarPingDuration, 0, false, false));
            }
        }
    }

    @Override
    public boolean canChangeDimensions(Level level, Level level1) {
        return false;
    }

    private void checkForDuplicate(){
        if(this.getOwner() instanceof Player player){
            ICuriosItemHandler curiosInventory = CuriosApi.getCuriosInventory(player).orElse(null);
            if(curiosInventory == null){
                discard();
                return;
            }
            Optional<SlotResult> item = curiosInventory.findCurio("head", 0);
            if(item.isEmpty()) {
                discard();
                return;
            }
            // Use DataComponents for 1.21.1 instead of getTag()
            ItemStack stack = item.get().stack();
            net.minecraft.world.item.component.CustomData customData = stack.get(net.minecraft.core.component.DataComponents.CUSTOM_DATA);
            if(customData != null) {
                CompoundTag compoundTag = customData.copyTag();
                if(compoundTag.contains("WolfUUID"))
                {
                    if(!this.getUUID().equals(compoundTag.getUUID("WolfUUID"))){
                        discard();
                    }
                }
            }
        }
    }

    private void spawnQuantumDriveParticles() {
            double offsetX = (random.nextDouble() - 0.5) * 0.6;
            double offsetY = random.nextDouble() * 0.4 + 0.8;
            double offsetZ = (random.nextDouble() - 0.5) * 0.6;

            double x = getX() + offsetX;
            double y = getY() + offsetY;
            double z = getZ() + offsetZ;

            level().addParticle(ParticleTypes.REVERSE_PORTAL, x, y, z, 0.0, 0.01, 0.0);
    }

    private void removePreviousLightBlock(){
        if(previousLightPos == null){
            return;
        }

        this.level().setBlock(previousLightPos, Blocks.AIR.defaultBlockState(), 3);
        previousLightPos = null;
    }

    public static void removeLightBlocksAround(Entity entity, int radius) {
        Level level = entity.level();
        BlockPos center = entity.blockPosition();

        for (int dx = -radius; dx <= radius; dx++) {
            for (int dy = -radius; dy <= radius; dy++) {
                for (int dz = -radius; dz <= radius; dz++) {
                    BlockPos pos = center.offset(dx, dy, dz);
                    BlockState state = level.getBlockState(pos);
                    if (state.is(Blocks.LIGHT)) {
                        level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);
                    }
                }
            }
        }
    }

    private void spawnRocketBoostParticles(){
        float boosterAnimationCooldown = 3;
        if(boosterAnimationTick >= boosterAnimationCooldown)
        {
            level().addParticle(ParticleTypes.SMOKE, this.getRandomX(0.5), this.getRandomY(), this.getRandomZ(0.5), 0.0, 0.05, 0.0);
            level().addParticle(ParticleTypes.SMOKE, this.getRandomX(0.5), this.getRandomY(), this.getRandomZ(0.5), 0.0, 0.05, 0.0);
            level().addParticle(ParticleTypes.FLAME, this.getRandomX(0.5), this.getRandomY(), this.getRandomZ(0.5), 0.0, 0.05, 0.0);

        }else{
            boosterAnimationTick++;
        }
    }

    private void spawnMountedTorchParticles(){
        float mountedTorchParticleDelayInTicks = 30;
        if (currentTorchTick >= mountedTorchParticleDelayInTicks && level().isClientSide) {
                double offsetX = (random.nextDouble() - 0.5) * 0.6;
                double offsetY = random.nextDouble() * 0.4 + 1;
                double offsetZ = (random.nextDouble() - 0.5) * 0.6;

                double x = getX() + offsetX;
                double y = getY() + offsetY;
                double z = getZ() + offsetZ;

                level().addParticle(ParticleTypes.SMOKE, x, y, z, 0.0, 0.05, 0.0);
            level().addParticle(ParticleTypes.FLAME, x, y, z, 0.0, 0.05, 0.0);
            currentTorchTick = 0;
        }
        else if(currentTorchTick < mountedTorchParticleDelayInTicks){
            currentTorchTick++;
        }
    }

    @Override
    public boolean doHurtTarget(Entity pEntity) {
        if(isModuleEquipped(ModItems.SMELTING_FANGS.get())) {
            pEntity.setRemainingFireTicks(ModConfig.COMMON.smeltingFangsFireDuration.get() * 20);
        }
        float pitch = 0.8F + this.random.nextFloat() * 0.4F;
        this.level().playSound(this, this.blockPosition(), ModSounds.BITE_SOUND.get(), SoundSource.NEUTRAL, 0.6f, pitch);
        return pEntity.hurt(this.damageSources().mobAttack(this), (float)((int)this.getAttributeValue(Attributes.ATTACK_DAMAGE)));
    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        if(isModuleEquipped(ModItems.TESLA_TAIL.get()))
        {
            if(pSource.getEntity() != null && pSource.getEntity() != this)
            {
                pSource.getEntity().hurt(pSource.getEntity().damageSources().mobAttack(this), ModConfig.COMMON.teslaTailDamage.get().floatValue());
                pSource.getEntity().invulnerableTime = 0;
            }
            spawnTeslaTailParticles();
        }
        return super.hurt(pSource, pAmount);
    }

    private void spawnTeslaTailParticles(){
        if(!level().isClientSide)
        {
            return;
        }

        for (int i = 0; i < 10; i++) {
            this.level().addParticle(ParticleTypes.ELECTRIC_SPARK,
                    this.getRandomX(1.5),
                    this.getRandomY(),
                    this.getRandomZ(1.5),
                    0.0, 0.2, 0.0);
        }

    }

    @Override
    public boolean fireImmune() {
        return isModuleEquipped(ModItems.NETHERITE_PLATES.get()) || isModuleEquipped(ModItems.SMELTING_FANGS.get());
    }

    @Override
    public int getArmorValue() {
        if (isModuleEquipped(ModItems.REINFORCED_PLATES.get())) {
            return ModConfig.COMMON.reinforcedPlatesArmorValue.get();
        }
        if(isModuleEquipped(ModItems.NETHERITE_PLATES.get())) {
            return ModConfig.COMMON.netheritePlatesArmorValue.get();
        }
        return super.getArmorValue();
    }

    @Override
    public @NotNull Component getDisplayName() {
        return super.getDisplayName();
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(ITEM_HANDLER_DATA, new CompoundTag());
    }

    private void syncItemHandler() {
        if (!this.level().isClientSide) {
            CompoundTag tag = itemHandler.serializeNBT(this.registryAccess());
            this.entityData.set(ITEM_HANDLER_DATA, tag);
        }
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
        super.onSyncedDataUpdated(key);
        if (key.equals(ITEM_HANDLER_DATA)) {
            CompoundTag tag = this.entityData.get(ITEM_HANDLER_DATA);
            itemHandler.deserializeNBT(this.registryAccess(), tag);
        }
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return null;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.IRON_GOLEM_DAMAGE;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return SoundEvents.IRON_GOLEM_DAMAGE;
    }


    @Override
    public void die(DamageSource pCause) {
        removeLightBlocksAround(this, 5);
        return;
    }
}
