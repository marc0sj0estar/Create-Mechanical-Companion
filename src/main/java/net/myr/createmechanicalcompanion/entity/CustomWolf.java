package net.myr.createmechanicalcompanion.entity;

import com.simibubi.create.AllItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
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
import net.minecraft.world.item.Items;
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
import net.myr.createmechanicalcompanion.item.MechanicalWolfLink;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.myr.createmechanicalcompanion.ServerConfig;
import net.myr.createmechanicalcompanion.sounds.ModSounds;
import net.myr.createmechanicalcompanion.item.ModItems;
import net.myr.createmechanicalcompanion.screen.WolfMenu;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;

import java.util.List;

public class CustomWolf extends Wolf implements MenuProvider {

    private static final double movementSpeed = 0.35D;
    private static final double defaultHealthValue = 30.0D;

    // Config stats

    private List<? extends String> wolfBlacklist;
    private double wrenchHealAmount;
    private int reinforcedPlatesArmorValue;
    private int reinforcedPlatesHealthIncrease;
    private double reinforcedPlatesKnockBackResistance;
    private int netheritePlatesArmorValue;
    private int netheritePlatesHealthIncrease;
    private double netheritePlatesKnockBackResistance;
    private int mountedCrossbowCooldown;
    private double teslaTailDamage;
    private int teslaTailCooldown;
    private int smeltingFangsFireDuration;
    private double boosterRocketSpeedIncrease;
    private int boosterRocketDuration;
    private int boosterRocketCooldown;
    private int quantumDriveCooldown;
    private double regenerativeCasingHealAmount;
    private int regenerativeCasingHealDelay;
    private int mobRadarRange;
    private int mobRadarCooldown;


    private BlockPos previousLightPos = null;
    private float currentTorchTick = 0;
    private float mountedCrossbowTick = 0;
    private float regenerativeCasingHealTick = 0;
    private float teslaTailTick = 0;

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
        getValuesFromConfig();

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

    private void getValuesFromConfig(){
        wolfBlacklist = ServerConfig.wolfBlacklist.get();
        wrenchHealAmount = ServerConfig.wrenchHealAmount.get();
        reinforcedPlatesArmorValue = ServerConfig.reinforcedPlatesArmorValue.get();
        reinforcedPlatesHealthIncrease = ServerConfig.reinforcedPlatesHealthIncrease.get();
        reinforcedPlatesKnockBackResistance = ServerConfig.reinforcedPlatesKnockBackResistance.get();
        netheritePlatesArmorValue = ServerConfig.netheritePlatesArmorValue.get();
        netheritePlatesHealthIncrease = ServerConfig.netheritePlatesHealthIncrease.get();
        netheritePlatesKnockBackResistance = ServerConfig.netheritePlatesKnockBackResistance.get();
        mountedCrossbowCooldown = ServerConfig.mountedCrossbowCooldown.get();
        teslaTailDamage = ServerConfig.teslaTailDamage.get();
        teslaTailCooldown = ServerConfig.teslaTailCooldown.get();
        smeltingFangsFireDuration = ServerConfig.smeltingFangsFireDuration.get();
        boosterRocketSpeedIncrease = ServerConfig.boosterRocketSpeedIncrease.get();
        boosterRocketDuration = ServerConfig.boosterRocketDuration.get();
        boosterRocketCooldown = ServerConfig.boosterRocketCooldown.get();
        quantumDriveCooldown = ServerConfig.quantumDriveCooldown.get();
        regenerativeCasingHealAmount = ServerConfig.regenerativeCasingHealAmount.get();
        regenerativeCasingHealDelay = ServerConfig.regenerativeCasingHealDelay.get();
        mobRadarRange = ServerConfig.mobRadarRange.get();
        mobRadarCooldown = ServerConfig.mobRadarCooldown.get();

    }

    public void tameToPlayer(Player player) {
        this.setTame(true, true); // Set tame with sound
        this.setOwnerUUID(player.getUUID());
        this.setTarget(null);
        this.navigation.stop();
        this.setOrderedToSit(false);
        this.setPersistenceRequired();
    }

    public static AttributeSupplier.@NotNull Builder createAttributes() {
        return Wolf.createAttributes()
                .add(Attributes.MAX_HEALTH, defaultHealthValue)
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
        return wolfBlacklist.contains(id.toString());
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
                this.heal((float) wrenchHealAmount);
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
        this.goalSelector.addGoal(6, new FollowOwnerGoal(this, 1.0D, 10.0F, 4.0F));
        this.goalSelector.addGoal(8, new StrollUnlessMenuOpenGoal(this, 0.6D, 4.0F));
        this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(10, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(this, Monster.class, 5, false, false, this::isValidAttackTarget));
        this.targetSelector.addGoal(8, new ResetUniversalAngerTargetGoal<>(this, true));
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide) {
            if(getOwner() == null){
                discard();
            }

            checkForDuplicate();

            if(isModuleEquipped(ModItems.REINFORCED_PLATES.get()))
            {
                applyModuleArmor(reinforcedPlatesHealthIncrease, reinforcedPlatesKnockBackResistance, reinforcedPlatesArmorValue);
            }
            else if(isModuleEquipped(ModItems.NETHERITE_PLATES.get()))
            {
                applyModuleArmor(netheritePlatesHealthIncrease, netheritePlatesKnockBackResistance, netheritePlatesArmorValue);
            }else{
                applyModuleArmor(0.0D, 0.0D, 0.0D);
            }

            if (isModuleEquipped(ModItems.REGENERATIVE_CASING.get()) && this.getHealth() < this.getMaxHealth()) {
                regenerativeCasingHealTick++;
                if (regenerativeCasingHealTick >= regenerativeCasingHealDelay) {
                    this.heal((float) regenerativeCasingHealAmount);
                    regenerativeCasingHealTick = 0;
                }
            }

            if (isModuleEquipped(ModItems.MOUNTED_LIGHT.get())) {
                if(this.getOffhandItem().isEmpty()){this.setItemSlot(EquipmentSlot.OFFHAND, new ItemStack(Items.LANTERN));}
            }else if(this.getOffhandItem().is(Items.LANTERN)){
                this.setItemSlot(EquipmentSlot.OFFHAND, ItemStack.EMPTY);
            }

            if (isModuleEquipped(ModItems.TESLA_TAIL.get()) && teslaTailTick < teslaTailCooldown) {
                teslaTailTick++;
            }

            if(isModuleEquipped(ModItems.MOUNTED_CROSSBOW.get()) && this.getTarget() != null && mountedCrossbowTick >= mountedCrossbowCooldown){
                mountedCrossbowTick = 0;
                TargetedArrowEntity arrow = new TargetedArrowEntity(this.level(), this, this.getTarget());
                arrow.setBaseDamage(4);

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

            if(isModuleEquipped(ModItems.BOOSTER_ROCKET.get()) && this.getTarget() != null && boosterTimer >= boosterRocketCooldown){
                if(movementAttribute.getBaseValue() == movementSpeed){
                    movementAttribute.setBaseValue(movementSpeed + boosterRocketSpeedIncrease);
                    boosterTimer = 0;
                }
            }else{
                if(boosterTimer >= boosterRocketDuration && movementAttribute.getBaseValue() != movementSpeed){
                    movementAttribute.setBaseValue(movementSpeed);
                }
            }

            if(boosterTimer < boosterRocketCooldown){
                boosterTimer++;
            }

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

            if(this.getAttribute(Attributes.MOVEMENT_SPEED).getBaseValue() == movementSpeed + boosterRocketSpeedIncrease){
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

    private boolean isValidAttackTarget(LivingEntity target) {
        LivingEntity owner = getOwner();
        if(owner == null || target == null || target == owner || isBlacklisted(target)) {return false;}

        if(target instanceof Player player && owner instanceof Player ownerPlayer) {
            return player.canHarmPlayer(ownerPlayer);
        }

        if(target instanceof TamableAnimal tamableTarget && tamableTarget.isOwnedBy(owner)){
            return false;
        }

        if(target instanceof Mob mob){
            return mob.getTarget() == owner;
        }

        return false;
    }


    private void applyModuleArmor(double healthIncrease, double knockBackResistance, double armorValue) {
        double currentMaxHealth = this.getAttribute(Attributes.MAX_HEALTH).getBaseValue();
        double newMaxHealth = defaultHealthValue + healthIncrease;
        if(currentMaxHealth != newMaxHealth){
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(newMaxHealth);
            this.getAttribute(Attributes.KNOCKBACK_RESISTANCE).setBaseValue(knockBackResistance);
            this.getAttribute(Attributes.ARMOR).setBaseValue(armorValue);
        }
    }

    @Override
    public boolean canDrownInFluidType(FluidType type) {
        return false;
    }

    @Override
    protected boolean canRide(Entity vehicle) {
        return false;
    }

    @Override
    protected void pushEntities() {}

    private void applyGlowingToHostileMobs() {
        List<Entity> nearbyEntities = level().getEntities(this, this.getBoundingBox().inflate(mobRadarRange), entity -> entity instanceof Mob mob && mob.getType().getCategory() == MobCategory.MONSTER);

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
        if(this.tickCount < 2){
            return;
        }
        if(this.getOwner() instanceof Player player){
            ItemStack link = MechanicalWolfLink.getLinkFromPlayer(player);
            if(link == ItemStack.EMPTY){
                discard();
                return;
            }
            net.minecraft.world.item.component.CustomData customData = link.get(net.minecraft.core.component.DataComponents.CUSTOM_DATA);
            if(customData == null){
                discard();
                return;
            }
            CompoundTag compoundTag = customData.copyTag();
            if(!compoundTag.contains("WolfUUID")){
                discard();
                return;
            }
            if(!this.getUUID().equals(compoundTag.getUUID("WolfUUID"))){
                discard();
            }
        }else{
            discard();
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
            pEntity.setRemainingFireTicks(smeltingFangsFireDuration * 20);
        }
        float pitch = 0.8F + this.random.nextFloat() * 0.4F;
        this.level().playSound(this, this.blockPosition(), ModSounds.BITE_SOUND.get(), SoundSource.NEUTRAL, 0.6f, pitch);
        return pEntity.hurt(this.damageSources().mobAttack(this), (float)((int)this.getAttributeValue(Attributes.ATTACK_DAMAGE)));
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if(level().isClientSide()){
           return super.hurt(source, amount);
        }

        if(isModuleEquipped(ModItems.TESLA_TAIL.get()))
        {
            if(teslaTailTick >= teslaTailCooldown && source.getEntity() != null && source.getEntity() != this)
            {
                source.getEntity().hurt(source.getEntity().damageSources().mobAttack(this), (float) teslaTailDamage);
                source.getEntity().invulnerableTime = 0;
                teslaTailTick = 0;
                spawnTeslaTailParticles();
            }
        }
        return super.hurt(source, amount);
    }

    private void spawnTeslaTailParticles(){
        if(!(level() instanceof ServerLevel serverLevel)){return;}

        for (int i = 0; i < 10; i++) {
            serverLevel.sendParticles(ParticleTypes.ELECTRIC_SPARK,
                    getX(), getY() + 0.5D, getZ(), 8,
                    0.5D, 0.5D, 0.5D, 0.2D);
        }
    }

    @Override
    public boolean fireImmune() {
        return isModuleEquipped(ModItems.NETHERITE_PLATES.get()) || isModuleEquipped(ModItems.SMELTING_FANGS.get());
    }

    @Override
    public int getArmorValue() {
        if (isModuleEquipped(ModItems.REINFORCED_PLATES.get())) {
            return reinforcedPlatesArmorValue;
        }
        if(isModuleEquipped(ModItems.NETHERITE_PLATES.get())) {
            return netheritePlatesArmorValue;
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
        super.die(pCause);
    }
}
