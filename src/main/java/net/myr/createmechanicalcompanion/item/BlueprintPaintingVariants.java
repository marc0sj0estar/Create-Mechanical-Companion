package net.myr.createmechanicalcompanion.item;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.decoration.PaintingVariant;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.myr.createmechanicalcompanion.CreateMechanicalCompanion;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BlueprintPaintingVariants {
    public static final DeferredRegister<PaintingVariant> BLUEPRINT_PAINTINGS = DeferredRegister.create(Registries.PAINTING_VARIANT, CreateMechanicalCompanion.MOD_ID);

    public static final DeferredHolder<PaintingVariant, PaintingVariant> BLUEPRINT_ART_0 = BLUEPRINT_PAINTINGS.register("blueprint_art_0",
            () -> new PaintingVariant(4, 3, CreateMechanicalCompanion.genRL("blueprint_art_0")));
    public static final DeferredHolder<PaintingVariant, PaintingVariant> BLUEPRINT_ART_1 = BLUEPRINT_PAINTINGS.register("blueprint_art_1",
            () -> new PaintingVariant(2, 2, CreateMechanicalCompanion.genRL("blueprint_art_1")));
    public static final DeferredHolder<PaintingVariant, PaintingVariant> BLUEPRINT_ART_2 = BLUEPRINT_PAINTINGS.register("blueprint_art_2",
            () -> new PaintingVariant(2, 1, CreateMechanicalCompanion.genRL("blueprint_art_2")));
    public static final DeferredHolder<PaintingVariant, PaintingVariant> BLUEPRINT_ART_3 = BLUEPRINT_PAINTINGS.register("blueprint_art_3",
            () -> new PaintingVariant(1, 2, CreateMechanicalCompanion.genRL("blueprint_art_3")));
    public static final DeferredHolder<PaintingVariant, PaintingVariant> BLUEPRINT_ART_4 = BLUEPRINT_PAINTINGS.register("blueprint_art_4",
            () -> new PaintingVariant(1, 1, CreateMechanicalCompanion.genRL("blueprint_art_4")));

    public static final ResourceLocation RL_ART_0 = CreateMechanicalCompanion.genRL("blueprint_art_0");
    public static final ResourceLocation RL_ART_1 = CreateMechanicalCompanion.genRL("blueprint_art_1");
    public static final ResourceLocation RL_ART_2 = CreateMechanicalCompanion.genRL("blueprint_art_2");
    public static final ResourceLocation RL_ART_3 = CreateMechanicalCompanion.genRL("blueprint_art_3");
    public static final ResourceLocation RL_ART_4 = CreateMechanicalCompanion.genRL("blueprint_art_4");

    public static List<Holder<PaintingVariant>> getAllVariants(Level level) {
        Optional<Registry<PaintingVariant>> maybeRegistry = level.registryAccess().registry(Registries.PAINTING_VARIANT);

        Object[][] defs = new Object[][]{
                {RL_ART_0, 4, 3},
                {RL_ART_1, 2, 2},
                {RL_ART_2, 2, 1},
                {RL_ART_3, 1, 2},
                {RL_ART_4, 1, 1}
        };

        List<Holder<PaintingVariant>> result = new ArrayList<>(defs.length);

        if (maybeRegistry.isPresent()) {
            Registry<PaintingVariant> registry = maybeRegistry.get();
            for (Object[] d : defs) {
                ResourceLocation rl = (ResourceLocation) d[0];
                int w = (int) d[1];
                int h = (int) d[2];
                ResourceKey<PaintingVariant> key = ResourceKey.create(Registries.PAINTING_VARIANT, rl);
                registry.getHolder(key).ifPresentOrElse(
                        result::add,
                        () -> result.add(Holder.direct(new PaintingVariant(w, h, rl)))
                );
            }
        } else {
            for (Object[] d : defs) {
                ResourceLocation rl = (ResourceLocation) d[0];
                int w = (int) d[1];
                int h = (int) d[2];
                result.add(Holder.direct(new PaintingVariant(w, h, rl)));
            }
        }

        return result;
    }

    public static void register(IEventBus eventBus) {
        BLUEPRINT_PAINTINGS.register(eventBus);
    }
}
