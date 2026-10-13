package net.myr.createmechanicalcompanion;

import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.util.List;

import static net.myr.createmechanicalcompanion.CreateMechanicalCompanion.LOGGER;

public class ServerConfig {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    //General
    public static final ModConfigSpec.BooleanValue autoUpdateConfigValues = BUILDER
            .comment("IMPORTANT: If true the config values will be reset after the mod version changes")
            .define("autoUpdateConfigValues", true);

    public static final ModConfigSpec.ConfigValue<String> modVersion = BUILDER
            .comment("The current version of the mod")
            .define("modVersion", "0");

    //Wolf stats
    public static final ModConfigSpec.ConfigValue<List<? extends String>> wolfBlacklist = BUILDER
            .comment("List of entities the wolf won't attack")
            .defineListAllowEmpty(
                    List.of("wolfBlacklist"),
                    () -> List.of("minecraft:creeper", "minecraft:ghast", "mekanism:robit"),
                    o -> o instanceof String
            );

    public static final ModConfigSpec.DoubleValue wrenchHealAmount = BUILDER
            .comment("Amount of health restored by using the Wrench on the Mechanical Companion (Default value = 2.0)")
            .defineInRange("wrenchHealAmount", 2.0, 0.0, Double.MAX_VALUE);

    //Module stats

    public static final ModConfigSpec.IntValue mechanicalWolfRespawnCooldown = BUILDER
            .comment("Cooldown time (in ticks) for the Mechanical Wolf (Default value = 1200)")
            .defineInRange("mechanicalWolfRespawnCooldown", 1200, 0, Integer.MAX_VALUE);

    public static final ModConfigSpec.IntValue reinforcedPlatesArmorValue = BUILDER
            .comment("Armor value provided by Reinforced Plates [0-20] (Default value = 4)")
            .defineInRange("reinforcedPlatesArmorValue", 4, 0, 20);

    public static final ModConfigSpec.IntValue reinforcedPlatesHealthIncrease = BUILDER
            .comment("Health increase provided by Reinforced Plates (Default value = 0)")
            .defineInRange("reinforcedPlatesHealthIncrease", 0, 0, Integer.MAX_VALUE);

    public static final ModConfigSpec.DoubleValue reinforcedPlatesKnockBackResistance = BUILDER
            .comment("Knockback resistance provided by Reinforced Plates [0.0 - 1.0] (Default value = 0.1)")
            .defineInRange("reinforcedPlatesKnockBackResistance", 0.1, 0.0, 1.0);

    public static final ModConfigSpec.IntValue netheritePlatesArmorValue = BUILDER
            .comment("Armor value provided by Netherite Plates [0-20] (Default value = 12)")
            .defineInRange("netheritePlatesArmorValue", 12, 0, 20);

    public static final ModConfigSpec.IntValue netheritePlatesHealthIncrease = BUILDER
            .comment("Health increase provided by Netherite Plates (Default value = 20)")
            .defineInRange("netheritePlatesHealthIncrease", 20, 0, Integer.MAX_VALUE);

    public static final ModConfigSpec.DoubleValue netheritePlatesKnockBackResistance = BUILDER
            .comment("Knockback resistance provided by Netherite Plates [0.0 - 1.0] (Default value = 0.4)")
            .defineInRange("netheritePlatesKnockBackResistance", 0.4, 0.0, 1.0);

    public static final ModConfigSpec.IntValue mountedCrossbowCooldown = BUILDER
            .comment("Cooldown time of the Mounted Crossbow (Default value = 60)")
            .defineInRange("mountedCrossbowCooldown", 60, 0, Integer.MAX_VALUE);

    public static final ModConfigSpec.DoubleValue teslaTailDamage = BUILDER
            .comment("Damage dealt by the Tesla Tail (Default value = 2.0)")
            .defineInRange("teslaTailDamage", 2.0, 0.0, Double.MAX_VALUE);

    public static final ModConfigSpec.IntValue smeltingFangsFireDuration = BUILDER
            .comment("Duration that the target is set on fire by the Smelting Fangs (Default value = 5)")
            .defineInRange("smeltingFangsFireDuration", 5, 0, Integer.MAX_VALUE);

    public static final ModConfigSpec.DoubleValue boosterRocketSpeedIncrease = BUILDER
            .comment("Speed boost provided by the Booster Rocket (Default value = 0.3)")
            .defineInRange("boosterRocketSpeedIncrease", 0.3, 0.0, Double.MAX_VALUE);

    public static final ModConfigSpec.IntValue boosterRocketDuration = BUILDER
            .comment("Duration of the speed boost from the Booster Rocket (Default value = 40)")
            .defineInRange("boosterRocketDuration", 40, 0, Integer.MAX_VALUE);

    public static final ModConfigSpec.IntValue boosterRocketCooldown = BUILDER
            .comment("Cooldown time (in ticks) for the Booster Rocket (Default value = 180)")
            .defineInRange("boosterRocketCooldown", 180, 0, Integer.MAX_VALUE);

    public static final ModConfigSpec.IntValue quantumDriveCooldown = BUILDER
            .comment("Cooldown time (in ticks) for the Quantum Drive (Default value = 200)")
            .defineInRange("quantumDriveCooldown", 200, 0, Integer.MAX_VALUE);

    public static final ModConfigSpec.DoubleValue regenerativeCasingHealAmount = BUILDER
            .comment("Amount of health restored by the Regenerative Casing (Default value = 0.5)")
            .defineInRange("regenerativeCasingHealAmount", 0.5, 0.0, Double.MAX_VALUE);

    public static final ModConfigSpec.IntValue regenerativeCasingHealDelay = BUILDER
            .comment("Delay (in ticks) between each heal from the Regenerative Casing (Default value = 60)")
            .defineInRange("regenerativeCasingHealDelay", 60, 0, Integer.MAX_VALUE);

    public static final ModConfigSpec.IntValue mobRadarRange = BUILDER
            .comment("Detection range of the Mob Radar (Default value = 32)")
            .defineInRange("mobRadarRange", 32, 0, Integer.MAX_VALUE);

    public static final ModConfigSpec.IntValue mobRadarCooldown = BUILDER
            .comment("Cooldown time (in ticks) for the Mob Radar (Default value = 200)")
            .defineInRange("mobRadarCooldown", 200, 0, Integer.MAX_VALUE);

    public static void onConfigLoad(String modVersion) {
        if(ServerConfig.autoUpdateConfigValues.get()) {
            String configVersion = ServerConfig.modVersion.get();
            if(!configVersion.equals(modVersion) || modVersion.equals("0.0NONE")) {
                ServerConfig.modVersion.set(modVersion);
                ServerConfig.reset();
            }
        }
    }

    public static void reset(){
        wolfBlacklist.set(wolfBlacklist.getDefault());
        wrenchHealAmount.set(wrenchHealAmount.getDefault());
        smeltingFangsFireDuration.set(smeltingFangsFireDuration.getDefault());
        boosterRocketSpeedIncrease.set(boosterRocketSpeedIncrease.getDefault());
        boosterRocketDuration.set(boosterRocketDuration.getDefault());
        boosterRocketCooldown.set(boosterRocketCooldown.getDefault());
        quantumDriveCooldown.set(quantumDriveCooldown.getDefault());
        regenerativeCasingHealAmount.set(regenerativeCasingHealAmount.getDefault());
        regenerativeCasingHealDelay.set(regenerativeCasingHealDelay.getDefault());
        mobRadarRange.set(mobRadarRange.getDefault());
        mobRadarCooldown.set(mobRadarCooldown.getDefault());
        SERVER_SPEC.save();
    }

    public static final ModConfigSpec SERVER_SPEC = BUILDER.build();
}
