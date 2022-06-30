package cech12.extendedmushrooms.client.particle;

import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;

@OnlyIn(Dist.CLIENT)
public class FairyRingParticle extends TextureSheetParticle {

    protected FairyRingParticle(ClientLevel world, double posX, double posY, double posZ, double motionX, double motionY, double motionZ, double maxAge, SpriteSet sprite) {
        super(world, posX, posY, posZ);
        this.pickSprite(sprite);
        this.xd = motionX;
        this.yd = motionY;
        this.zd = motionZ;
        this.lifetime = (int)(10.0D / (Math.random() * 0.8D + 0.2D));
        this.quadSize *= 0.4F;
    }

    @Nonnull
    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }


    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        if (this.age++ >= this.lifetime) {
            this.remove();
        } else {
            this.yd += 0.004D;
            this.move(this.xd, this.yd, this.zd);
            if (this.y == this.yo) {
                this.xd *= 1.1D;
                this.zd *= 1.1D;
            }

            this.quadSize *= 0.96F;
            this.xd *= 0.96D;
            this.yd *= 0.96D;
            this.zd *= 0.96D;
            if (this.onGround) {
                this.xd *= 0.7D;
                this.zd *= 0.7D;
            }

        }
    }

    @OnlyIn(Dist.CLIENT)
    public static class Factory implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;

        public Factory(SpriteSet p_i51045_1_) {
            this.spriteSet = p_i51045_1_;
        }

        public Particle createParticle(@Nonnull SimpleParticleType typeIn, @Nonnull ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            return new FairyRingParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, 1.0D, this.spriteSet);
        }
    }
}
