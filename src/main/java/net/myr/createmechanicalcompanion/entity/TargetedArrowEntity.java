package net.myr.createmechanicalcompanion.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.Arrow;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.phys.EntityHitResult;
import java.util.UUID;

public class TargetedArrowEntity extends Arrow {
    private UUID targetUUID;

    public TargetedArrowEntity(EntityType<? extends TargetedArrowEntity> type, Level level) {
        super(type, level);
    }

    public TargetedArrowEntity(Level level, LivingEntity shooter, LivingEntity target) {
        super(level, shooter);
        this.targetUUID = target.getUUID();
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        if (result.getEntity().getUUID().equals(this.targetUUID)) {
            super.onHitEntity(result);
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        if (this.targetUUID != null) {
            tag.putUUID("TargetUUID", this.targetUUID);
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        if (tag.hasUUID("TargetUUID")) {
            this.targetUUID = tag.getUUID("TargetUUID");
        }
    }
}

