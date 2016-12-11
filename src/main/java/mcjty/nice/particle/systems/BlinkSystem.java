package mcjty.nice.particle.systems;

import mcjty.nice.particle.*;
import net.minecraft.util.math.Vec3d;

import java.util.List;

public class BlinkSystem implements IParticleSystem {

    private static Vec3d[] offsets = new Vec3d[6];

    static {
        offsets[0] = new Vec3d(0, .3f, .2f);
        offsets[1] = new Vec3d(-.3f, 2f, 0f);
        offsets[2] = new Vec3d(.1f, 0, -.3f);
        offsets[3] = new Vec3d(0, .4f, .5f);
        offsets[4] = new Vec3d(.2f, .6f, -.3f);
        offsets[5] = new Vec3d(-.4f, .8f, -.2f);
    }

    @Override
    public ICalculatedParticleSystem createCalculatedParticleSystem() {
        DefaultCalculatedParticleSystem calculated = new DefaultCalculatedParticleSystem(offsets.length);
        List<IParticle> particles = calculated.getParticles();
        for (int i = 0 ; i < offsets.length ; i++) {
            DefaultParticle particle = (DefaultParticle) particles.get(i);
            particle.setUV(1D/8, 0, 2D/8, 1D/8);
            particle.setColor(255, 255, 255, 255);
        }

        return calculated;
    }

    @Override
    public void update(ICalculatedParticleSystem calculated, long time) {
        List<IParticle> particles = calculated.getParticles();
        for (int i = 0 ; i < offsets.length ; i++) {
            Vec3d o = offsets[i];
            DefaultParticle particle = (DefaultParticle) particles.get(i);
            particle.setOffset(o);
            float offset = (time % 2000) / 2000.0f;
            particle.setScale(offset * .4f);
        }

    }
}
