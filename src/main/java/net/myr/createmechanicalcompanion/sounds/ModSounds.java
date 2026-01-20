package net.myr.createmechanicalcompanion.sounds;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.myr.createmechanicalcompanion.CreateMechanicalCompanion;

import java.util.function.Supplier;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, CreateMechanicalCompanion.MOD_ID);

    public static final Supplier<SoundEvent> EQUIP_MODULE =
            SOUND_EVENTS.register("equip_module", () ->
                    SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(CreateMechanicalCompanion.MOD_ID, "equip_module")));

    public static final Supplier<SoundEvent> MOUNTED_CROSSBOW_SOUND =
            SOUND_EVENTS.register("mounted_crossbow_sound", () ->
                    SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(CreateMechanicalCompanion.MOD_ID, "mounted_crossbow_sound")));

    public static final Supplier<SoundEvent> BITE_SOUND =
            SOUND_EVENTS.register("bite_sound", () ->
                    SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath(CreateMechanicalCompanion.MOD_ID, "bite_sound")));

    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}
