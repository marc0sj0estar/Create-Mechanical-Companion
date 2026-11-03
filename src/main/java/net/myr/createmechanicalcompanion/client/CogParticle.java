package net.myr.createmechanicalcompanion.client;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;

public class CogParticle extends TextureSheetParticle {
    public CogParticle(ClientLevel level, double x, double y, double z, SpriteSet sprites)
    {
        super(level, x, y, z);

        this.pickSprite(sprites);
        this.gravity = 0.0F;
        this.friction = 0.95F;
        this.lifetime = 10;
        this.quadSize = 0.15F;
        this.xd = 0.0D;
        this.yd = 0.0D;
        this.zd = 0.0D;
    }

    public CogParticle(
            ClientLevel level,
            double x,
            double y,
            double z,
            double xSpeed,
            double ySpeed,
            double zSpeed,
            SpriteSet sprites
    ) {
        super(level, x, y, z);

        this.pickSprite(sprites);
        this.gravity = 0.0F;
        this.friction = 0.95F;
        this.lifetime = 10;
        this.quadSize = 0.15F;
        this.xd = xSpeed;
        this.yd = ySpeed;
        this.zd = zSpeed;
    }

    @Override
    public void tick() {
        super.tick();

        float progress = (float) this.age / (float) this.lifetime;
        this.quadSize = 0.2f * (float) Math.sin(progress * Math.PI);
    }

    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet sprites;

        public Provider(SpriteSet sprites) {
            this.sprites = sprites;
        }

        @Override
        public Particle createParticle(
                SimpleParticleType type,
                ClientLevel level,
                double x,
                double y,
                double z,
                double xSpeed,
                double ySpeed,
                double zSpeed
        ) {
            return new CogParticle(level, x, y, z, xSpeed, ySpeed, zSpeed, this.sprites);
        }

    }
}
