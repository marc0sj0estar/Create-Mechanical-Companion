package net.myr.createmechanicalcompanion.entity;

import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.myr.createmechanicalcompanion.CreateMechanicalCompanion;
import net.myr.createmechanicalcompanion.screen.WolfMenu;
import org.jetbrains.annotations.Nullable;

public class StrollUnlessMenuOpenGoal extends WaterAvoidingRandomStrollGoal {

    private static float MINIMUM_DISTANCE_TO_OWNER;

    public StrollUnlessMenuOpenGoal(PathfinderMob mob, double speedModifier, float minDistanceToOwner) {
        super(mob, speedModifier);
        MINIMUM_DISTANCE_TO_OWNER = minDistanceToOwner;
    }

    @Override
    public boolean canUse() {
        if(this.mob instanceof CustomWolf wolf && wolf.getOwner() instanceof Player player
                && player.containerMenu instanceof WolfMenu) {
            return false;
        }
        return super.canUse();
    }

    @Override
    public boolean canContinueToUse() {
        if(this.mob instanceof CustomWolf wolf && wolf.getOwner() instanceof Player player
                && player.containerMenu instanceof WolfMenu) {
            return false;
        }
        return super.canContinueToUse();
    }

    @Override
    protected @Nullable Vec3 getPosition() {
        Vec3 position = super.getPosition();
        if(position != null && this.mob instanceof CustomWolf wolf && wolf.getOwner() instanceof Player player) {
            if (position.distanceTo(player.position()) < MINIMUM_DISTANCE_TO_OWNER) {
                return null;
            }
        }
        return position;
    }
}
