package mcjty.nice.particle.systems;

import mcjty.nice.particle.*;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class BubbleSystem implements IParticleSystem {

    private static Vec3[] offsets = new Vec3[6];

    static {
        offsets[0] = new Vec3(0, .43f, .2f);
        offsets[1] = new Vec3(-.3f, .3f, 0f);
        offsets[2] = new Vec3(.1f, 0, -.3f);
        offsets[3] = new Vec3(0, .16f, .27f);
        offsets[4] = new Vec3(.2f, -.4f, -.21f);
        offsets[5] = new Vec3(-.17f, -.25f, -.2f);
    }

    @Override
    public ICalculatedParticleSystem createCalculatedParticleSystem(float scale) {
        DefaultCalculatedParticleSystem calculated = new DefaultCalculatedParticleSystem(offsets.length, scale);
        List<IParticle> particles = calculated.getParticles();
        for (int i = 0 ; i < offsets.length ; i++) {
            DefaultParticle particle = (DefaultParticle) particles.get(i);
            particle.setScale(.13);
            particle.setUV(5.0D/8, 0, 6.0D / 8, 1.0D);
            particle.setColor(255, 255, 255, 128);
        }
        return calculated;
    }

    @Override
    public void update(ICalculatedParticleSystem calculated, long time) {
        List<IParticle> particles = calculated.getParticles();
        for (int i = 0 ; i < offsets.length ; i++) {
            Vec3 o = offsets[i];
            float offset = (time % 2000) / 2000.0f;
            double ox = o.x * calculated.getScale() / 0.8f;
            double oy = (o.y + offset +.5f) % 1f-.5f;
            double oz = o.z * calculated.getScale() / 0.8f;
            DefaultParticle particle = (DefaultParticle) particles.get(i);
            particle.setOffset(new Vec3(ox, oy, oz));
        }

    }
}
