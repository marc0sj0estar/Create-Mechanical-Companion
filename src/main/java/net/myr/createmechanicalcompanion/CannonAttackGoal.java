package net.myr.createmechanicalcompanion;

import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.RangedCrossbowAttackGoal;
import net.minecraft.world.entity.monster.CrossbowAttackMob;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;

public class CannonAttackGoal extends Goal {

    @Override
    public boolean canUse() {
        return false;
    }
}
