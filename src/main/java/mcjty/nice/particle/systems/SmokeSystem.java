package mcjty.nice.particle.systems;

import mcjty.nice.particle.*;
import net.minecraft.util.math.Vec3d;

import java.util.List;

public class SmokeSystem implements IParticleSystem {

    private static Vec3d[] offsets = new Vec3d[6];

    static {
        offsets[0] = new Vec3d(0, .43f, .2f);
        offsets[1] = new Vec3d(-.3f, .3f, 0f);
        offsets[2] = new Vec3d(.1f, 0, -.3f);
        offsets[3] = new Vec3d(0, .16f, .27f);
        offsets[4] = new Vec3d(.2f, -.4f, -.21f);
        offsets[5] = new Vec3d(-.17f, -.25f, -.2f);
    }

    @Override
    public ICalculatedParticleSystem createCalculatedParticleSystem() {
        DefaultCalculatedParticleSystem calculated = new DefaultCalculatedParticleSystem(offsets.length);
        List<IParticle> particles = calculated.getParticles();
        for (int i = 0 ; i < offsets.length ; i++) {
            DefaultParticle particle = (DefaultParticle) particles.get(i);
            particle.setScale(.4);
            particle.setUV(0, 0, 1.0D / 8, 1.0D / 8);
            particle.setColor(255, 255, 255, 128);
        }
        return calculated;
    }

    @Override
    public void update(ICalculatedParticleSystem calculated, long time) {
        List<IParticle> particles = calculated.getParticles();
        for (int i = 0 ; i < offsets.length ; i++) {
            Vec3d o = offsets[i];
            float offset = (time % 2000) / 2000.0f;
            double ox = o.xCoord;
            double oy = (o.yCoord + offset +.5f) % 1f-.5f;
            double oz = o.zCoord;
            DefaultParticle particle = (DefaultParticle) particles.get(i);
            particle.setOffset(new Vec3d(ox, oy, oz));
        }

    }
}
