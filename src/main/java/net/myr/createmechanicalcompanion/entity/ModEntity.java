package net.myr.createmechanicalcompanion.entity;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.myr.createmechanicalcompanion.CreateMechanicalCompanion;

import java.util.function.Supplier;

public class ModEntity {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, CreateMechanicalCompanion.MOD_ID);

    public static final Supplier<EntityType<CustomWolf>> CUSTOM_WOLF = ENTITIES.register("custom_wolf",
            () -> EntityType.Builder.of(CustomWolf::new, MobCategory.CREATURE)
                    .sized(0.6F, 0.85F)
                    .build("custom_wolf"));

    public static final Supplier<EntityType<IllagerEngineer>> ILLAGER_ENGINEER = ENTITIES.register("illager_engineer",
            () -> EntityType.Builder.of(IllagerEngineer::new, MobCategory.MONSTER)
                    .sized(0.6F, 1.95F)
                    .build("illager_engineer"));

    public static final Supplier<EntityType<PotatoCannonIllager>> POTATO_CANNON_ILLAGER = ENTITIES.register("potato_cannon_illager",
            () -> EntityType.Builder.of(PotatoCannonIllager::new, MobCategory.MONSTER)
                    .sized(0.6F, 1.95F)
                    .build("potato_cannon_illager"));

    public static final Supplier<EntityType<TargetedArrowEntity>> TARGETED_ARROW = ENTITIES.register("targeted_arrow",
            () -> EntityType.Builder.<TargetedArrowEntity>of(TargetedArrowEntity::new, MobCategory.MISC)
                    .sized(0.5f, 0.5f)
                    .clientTrackingRange(4)
                    .updateInterval(20)
                    .build(ResourceLocation.fromNamespaceAndPath(CreateMechanicalCompanion.MOD_ID, "targeted_arrow").toString())
            );

    public static final Supplier<EntityType<BlueprintPaintingEntity>> BLUEPRINT_ENTITY =
            ENTITIES.register("blueprint_painting",
                    () -> EntityType.Builder.<BlueprintPaintingEntity>of(BlueprintPaintingEntity::new, MobCategory.MISC)
                            .sized(0.5F, 0.5F)
                            .clientTrackingRange(10)
                            .build("blueprint_painting"));

    public static void register(IEventBus eventBus) {
        ENTITIES.register(eventBus);
    }
}
