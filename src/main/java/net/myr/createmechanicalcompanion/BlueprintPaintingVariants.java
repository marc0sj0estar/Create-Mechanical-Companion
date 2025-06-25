package net.myr.createmechanicalcompanion;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.decoration.PaintingVariant;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

public class BlueprintPaintingVariants {
    public static final DeferredRegister<PaintingVariant> BLUEPRINT_PAINTINGS = DeferredRegister.create(Registries.PAINTING_VARIANT, CreateMechanicalCompanion.MOD_ID);

    public static final RegistryObject<PaintingVariant> BLUEPRINT_ART_0 = BLUEPRINT_PAINTINGS.register("blueprint_art_0", () -> new PaintingVariant(64, 48));
    public static final RegistryObject<PaintingVariant> BLUEPRINT_ART_1 = BLUEPRINT_PAINTINGS.register("blueprint_art_1", () -> new PaintingVariant(32, 32));
    public static final RegistryObject<PaintingVariant> BLUEPRINT_ART_2 = BLUEPRINT_PAINTINGS.register("blueprint_art_2", () -> new PaintingVariant(32, 16));
    public static final RegistryObject<PaintingVariant> BLUEPRINT_ART_3 = BLUEPRINT_PAINTINGS.register("blueprint_art_3", () -> new PaintingVariant(16, 32));
    public static final RegistryObject<PaintingVariant> BLUEPRINT_ART_4 = BLUEPRINT_PAINTINGS.register("blueprint_art_4", () -> new PaintingVariant(16, 16));

    public static List<Holder<PaintingVariant>> getAllVariants() {
        return List.of(
                BLUEPRINT_ART_0.getHolder().get(),
                BLUEPRINT_ART_1.getHolder().get(),
                BLUEPRINT_ART_2.getHolder().get(),
                BLUEPRINT_ART_3.getHolder().get(),
                BLUEPRINT_ART_4.getHolder().get()

        );
    }
}
