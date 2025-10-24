package net.myr.createmechanicalcompanion;

import net.minecraftforge.common.ForgeConfigSpec;

import java.util.List;

public class ModConfig {
    public static final ForgeConfigSpec COMMON_SPEC;
    public static final Common COMMON;

    static {
        final ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        COMMON = new Common(builder);
        COMMON_SPEC = builder.build();
    }

    public static class Common {
        public final ForgeConfigSpec.ConfigValue<List<? extends String>> wolfBlacklist;

        public final ForgeConfigSpec.DoubleValue wrenchHealAmount;

        //Module stats
        public final ForgeConfigSpec.IntValue reinforcedPlatesArmorValue;
        public final ForgeConfigSpec.IntValue reinforcedPlatesHealthIncrease;
        public final ForgeConfigSpec.DoubleValue reinforcedPlatesKnockBackResistance;

        public final ForgeConfigSpec.IntValue netheritePlatesArmorValue;
        public final ForgeConfigSpec.IntValue netheritePlatesHealthIncrease;
        public final ForgeConfigSpec.DoubleValue netheritePlatesKnockBackResistance;

        public final ForgeConfigSpec.IntValue mountedCrossbowCooldown;
        public final ForgeConfigSpec.DoubleValue teslaTailDamage;
        public final ForgeConfigSpec.IntValue smeltingFangsFireDuration;

        public final ForgeConfigSpec.DoubleValue boosterRocketSpeedIncrease;
        public final ForgeConfigSpec.IntValue boosterRocketDuration;
        public final ForgeConfigSpec.IntValue boosterRocketCooldown;
        public final ForgeConfigSpec.IntValue quantumDriveCooldown;

        public final ForgeConfigSpec.DoubleValue regenerativeCasingHealAmount;
        public final ForgeConfigSpec.IntValue mobRadarRange;
        public final ForgeConfigSpec.IntValue mobRadarCooldown;

        public Common(ForgeConfigSpec.Builder builder) {
            wolfBlacklist = builder
                    .comment("List of entities the wolf won't attack")
                    .defineListAllowEmpty(
                            List.of("wolfBlacklist"),
                            List.of("minecraft:creeper", "minecraft:ghast", "mekanism:robit"),
                            o -> o instanceof String
                    );

            wrenchHealAmount = builder
                    .comment("Amount of health restored by using the Wrench on the Mechanical Companion (Default value = 2.0)")
                    .defineInRange("wrenchHealAmount", 2.0, 0.0, Double.MAX_VALUE);

            reinforcedPlatesArmorValue = builder
                    .comment("Armor value provided by Reinforced Plates [0-20] (Default value = 4)")
                    .defineInRange("reinforcedPlatesArmorValue", 4, 0, 20);

            reinforcedPlatesHealthIncrease = builder
                    .comment("Health increase provided by Reinforced Plates (Default value = 0)")
                    .defineInRange("reinforcedPlatesHealthIncrease", 0, 0, Integer.MAX_VALUE);

            reinforcedPlatesKnockBackResistance = builder
                    .comment("Knockback resistance provided by Reinforced Plates [0.0 - 1.0] (Default value = 0.1)")
                    .defineInRange("reinforcedPlatesKnockBackResistance", 0.1, 0.0, 1.0);

            netheritePlatesArmorValue = builder
                    .comment("Armor value provided by Netherite Plates [0-20] (Default value = 6)")
                    .defineInRange("netheritePlatesArmorValue", 6, 0, 20);

            netheritePlatesHealthIncrease = builder
                    .comment("Health increase provided by Netherite Plates (Default value = 10)")
                    .defineInRange("netheritePlatesHealthIncrease", 10, 0, Integer.MAX_VALUE);

            netheritePlatesKnockBackResistance = builder
                    .comment("Knockback resistance provided by Netherite Plates [0.0 - 1.0] (Default value = 0.2)")
                    .defineInRange("netheritePlatesKnockBackResistance", 0.2, 0.0, 1.0);

            mountedCrossbowCooldown = builder
                    .comment("Cooldown time of the Mounted Crossbow (Default value = 60)")
                    .defineInRange("mountedCrossbowCooldown", 60, 0, Integer.MAX_VALUE);

            teslaTailDamage = builder
                    .comment("Damage dealt by the Tesla Tail (Default value = 2.0)")
                    .defineInRange("teslaTailDamage", 2.0, 0.0, Double.MAX_VALUE);

            smeltingFangsFireDuration = builder
                    .comment("Duration that the target is set on fire by the Smelting Fangs (Default value = 5)")
                    .defineInRange("smeltingFangsFireDuration", 5, 0, Integer.MAX_VALUE);

            boosterRocketSpeedIncrease = builder
                    .comment("Speed boost provided by the Booster Rocket (Default value = 0.3)")
                    .defineInRange("boosterRocketSpeedIncrease", 0.3, 0.0, Double.MAX_VALUE);

            boosterRocketDuration = builder
                    .comment("Duration of the speed boost from the Booster Rocket (Default value = 40)")
                    .defineInRange("boosterRocketDuration", 40, 0, Integer.MAX_VALUE);

            boosterRocketCooldown = builder
                    .comment("Cooldown time for the Booster Rocket (Default value = 180)")
                    .defineInRange("boosterRocketCooldown", 180, 0, Integer.MAX_VALUE);

            quantumDriveCooldown = builder
                    .comment("Cooldown time (in seconds) for the Quantum Drive (Default value = 40)")
                    .defineInRange("quantumDriveCooldown", 40, 0, Integer.MAX_VALUE);

            regenerativeCasingHealAmount = builder
                    .comment("Amount of health restored by the Regenerative Casing (Default value = 0.05)")
                    .defineInRange("regenerativeCasingHealAmount", 0.05, 0.0, Double.MAX_VALUE);

            mobRadarRange = builder
                    .comment("Detection range of the Mob Radar (Default value = 32)")
                    .defineInRange("mobRadarRange", 32, 0, Integer.MAX_VALUE);

            mobRadarCooldown = builder
                    .comment("Cooldown time for the Mob Radar (Default value = 200)")
                    .defineInRange("mobRadarCooldown", 200, 0, Integer.MAX_VALUE);


        }


    }
}