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
    private BlueprintPaintingVariants() {}

    public static final DeferredRegister<PaintingVariant> PAINTING_VARIANTS =
            DeferredRegister.create(Registries.PAINTING_VARIANT, CreateMechanicalCompanion.MOD_ID);

    public static final DeferredHolder<PaintingVariant, PaintingVariant> BLUEPRINT_ART_=
            PAINTING_VARIANTS.register("blueprint_art_0", () -> new PaintingVariant(64, 48,
                    ResourceLocation.fromNamespaceAndPath("createmechanicalcompanion", "blueprint_art_0")));


    public static final List<ResourceKey<PaintingVariant>> VARIANTS =
            List.of(
                    key("blueprint_art_0"),
                    key("blueprint_art_1"),
                    key("blueprint_art_2"),
                    key("blueprint_art_3"),
                    key("blueprint_art_4")
            );

    private static ResourceKey<PaintingVariant> key(String name) {
        return ResourceKey.create(
                Registries.PAINTING_VARIANT,
                ResourceLocation.fromNamespaceAndPath("createmechanicalcompanion", name)
        );
    }
}
