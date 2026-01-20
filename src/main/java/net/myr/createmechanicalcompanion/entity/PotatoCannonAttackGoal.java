package net.myr.createmechanicalcompanion.entity;


import com.simibubi.create.AllEntityTypes;
import com.simibubi.create.content.equipment.potatoCannon.PotatoProjectileEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;


public class PotatoCannonAttackGoal extends Goal {
    private final Mob mob;
    private int cooldown = 0;
    private final double speed = 1.0D;
    private final double attackRange = 10D;
    private ItemStack[] projectileList;

    public PotatoCannonAttackGoal(Mob mob) {
        this.projectileList = new ItemStack[]{
                new ItemStack(Items.POTATO),
                new ItemStack(Items.CARROT),
                new ItemStack(Items.COD),
                new ItemStack(Items.MELON_SLICE),
                new ItemStack(Items.POISONOUS_POTATO),
                new ItemStack(Items.BAKED_POTATO),
                new ItemStack(Items.SWEET_BERRIES),
                new ItemStack(Items.MELON),
                new ItemStack(Items.PUMPKIN),
                new ItemStack(Items.BEETROOT),
                new ItemStack(Items.APPLE)

        };
        this.mob = mob;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        return mob.getTarget() != null && mob.getTarget().isAlive();
    }

    @Override
    public void tick() {
        LivingEntity target = mob.getTarget();
        if (target == null) {
            return;
        }

        double distance = mob.distanceTo(target);
        mob.getLookControl().setLookAt(target, 30, 30);

        if (distance > attackRange || !mob.getSensing().hasLineOfSight(target)) {
            mob.getNavigation().moveTo(target, speed);
            return;
        } else {
            mob.getNavigation().stop();
        }

        if (cooldown > 0) {
            cooldown--;
            return;
        }

        mob.getLookControl().setLookAt(target, 30, 30);

        cooldown = 20 + mob.getRandom().nextInt(20);

        Vec3 from = mob.position().add(0, mob.getEyeHeight(), 0);
        Vec3 to = target.position().add(0, target.getEyeHeight() + mob.distanceTo(target) * 0.1, 0);
        Vec3 dir = to.subtract(from).normalize().scale(1.5);

        PotatoProjectileEntity proj = AllEntityTypes.POTATO_PROJECTILE.create(mob.level());
        if (proj != null) {
            proj.setItem(getRandomProjectile());
            proj.setPos(from.x, from.y, from.z);
            proj.setDeltaMovement(dir);
            mob.level().addFreshEntity(proj);
        }
    }

    private ItemStack getRandomProjectile() {
        int rand = mob.getRandom().nextInt(projectileList.length);
        return projectileList[rand];
    }
}
