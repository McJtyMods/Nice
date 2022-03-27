package mcjty.nice.particle.systems;

import mcjty.nice.particle.*;
import net.minecraft.util.math.vector.Vector3d;

import java.util.List;

public class BubbleSystem implements IParticleSystem {

    private static Vector3d[] offsets = new Vector3d[6];

    static {
        offsets[0] = new Vector3d(0, .43f, .2f);
        offsets[1] = new Vector3d(-.3f, .3f, 0f);
        offsets[2] = new Vector3d(.1f, 0, -.3f);
        offsets[3] = new Vector3d(0, .16f, .27f);
        offsets[4] = new Vector3d(.2f, -.4f, -.21f);
        offsets[5] = new Vector3d(-.17f, -.25f, -.2f);
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
            Vector3d o = offsets[i];
            float offset = (time % 2000) / 2000.0f;
            double ox = o.x * calculated.getScale() / 0.8f;
            double oy = (o.y + offset +.5f) % 1f-.5f;
            double oz = o.z * calculated.getScale() / 0.8f;
            DefaultParticle particle = (DefaultParticle) particles.get(i);
            particle.setOffset(new Vector3d(ox, oy, oz));
        }

    }
}
