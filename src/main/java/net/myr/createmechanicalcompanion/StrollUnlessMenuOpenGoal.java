package net.myr.createmechanicalcompanion;

import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.player.Player;
import net.myr.createmechanicalcompanion.entity.CustomWolf;
import net.myr.createmechanicalcompanion.screen.WolfMenu;

public class StrollUnlessMenuOpenGoal extends WaterAvoidingRandomStrollGoal {
    public StrollUnlessMenuOpenGoal(PathfinderMob pMob, double pSpeedModifier) {
        super(pMob, pSpeedModifier);
    }

    public StrollUnlessMenuOpenGoal(PathfinderMob pMob, double pSpeedModifier, float pProbability) {
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
