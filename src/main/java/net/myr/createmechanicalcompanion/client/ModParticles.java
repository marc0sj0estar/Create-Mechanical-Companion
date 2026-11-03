package net.myr.createmechanicalcompanion.client;

import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.myr.createmechanicalcompanion.CreateMechanicalCompanion;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModParticles {
        public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES =
                DeferredRegister.create(BuiltInRegistries.PARTICLE_TYPE, CreateMechanicalCompanion.MOD_ID);

        public static final Supplier<SimpleParticleType> COG_PARTICLE =
                PARTICLE_TYPES.register(
                        "cog_particle",
                        () -> new SimpleParticleType(false)
                );

        public static void registerParticleProviders(RegisterParticleProvidersEvent event) {
                event.registerSpriteSet(
                        COG_PARTICLE.get(),
                        CogParticle.Provider::new
                );
        }

        public static void register(IEventBus eventBus) {
            PARTICLE_TYPES.register(eventBus);
        }
}
