package net.myr.createmechanicalcompanion;

import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import net.myr.createmechanicalcompanion.entity.CustomWolf;
import net.myr.createmechanicalcompanion.screen.WolfMenu;

import javax.annotation.Nullable;

public class FollowUnlessMenuOpenGoal extends WaterAvoidingRandomStrollGoal {
    public FollowUnlessMenuOpenGoal(PathfinderMob pMob, double pSpeedModifier) {
        super(pMob, pSpeedModifier);
    }

    public FollowUnlessMenuOpenGoal(PathfinderMob pMob, double pSpeedModifier, float pProbability) {
        super(pMob, pSpeedModifier, pProbability);
    }

    @Override
    public boolean canUse() {
        if(this.mob instanceof CustomWolf wolf && wolf.getOwner() instanceof Player player && player.containerMenu instanceof WolfMenu) {
            return false;
        }
        return super.canUse();
    }
}
