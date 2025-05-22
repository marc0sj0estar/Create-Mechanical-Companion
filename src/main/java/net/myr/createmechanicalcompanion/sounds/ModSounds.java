package net.myr.createmechanicalcompanion.sounds;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.myr.createmechanicalcompanion.CreateMechanicalCompanion;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, CreateMechanicalCompanion.MOD_ID);

    public static final RegistryObject<SoundEvent> EQUIP_MODULE =
            SOUND_EVENTS.register("equip_module", () ->
                    SoundEvent.createVariableRangeEvent(new ResourceLocation(CreateMechanicalCompanion.MOD_ID, "equip_module")));

    public static final RegistryObject<SoundEvent> MOUNTED_CROSSBOW_SOUND =
            SOUND_EVENTS.register("mounted_crossbow_sound", () ->
                    SoundEvent.createVariableRangeEvent(new ResourceLocation(CreateMechanicalCompanion.MOD_ID, "mounted_crossbow_sound")));

    public static final RegistryObject<SoundEvent> BITE_SOUND =
            SOUND_EVENTS.register("bite_sound", () ->
                    SoundEvent.createVariableRangeEvent(new ResourceLocation(CreateMechanicalCompanion.MOD_ID, "bite_sound")));
}
