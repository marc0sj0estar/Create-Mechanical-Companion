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

        public Common(ForgeConfigSpec.Builder builder) {
            wolfBlacklist = builder
                    .comment("List of entities the wolf won't attack")
                    .defineListAllowEmpty(
                            List.of("wolfBlacklist"),
                            List.of("minecraft:creeper", "minecraft:ghast", "mekanism:robit"),
                            o -> o instanceof String
                    );
        }
    }
}