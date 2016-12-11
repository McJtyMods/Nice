package mcjty.nice.particle.systems;

import mcjty.nice.particle.DefaultCalculatedParticleSystem;
import mcjty.nice.particle.DefaultParticle;
import net.minecraft.util.math.Vec3d;

import java.util.Random;

public class BlinkCalculatedSystem extends DefaultCalculatedParticleSystem {

    private static Random random = new Random();

    private long offsets[] = new long[] { 200, 343, 708, 1700, 3, 1493 };

    public BlinkCalculatedSystem() {
        super(6);

        int amount = particles.size();
        for (int i = 0 ; i < amount ; i++) {
            DefaultParticle particle = (DefaultParticle) particles.get(i);
            Vec3d o = new Vec3d(random.nextDouble()*.8-.4, random.nextDouble()*.8-.4, random.nextDouble()*.8-.4);
            particle.setOffset(o);
            particle.setUV(4D/8, 0, 5D/8, 1D/8);
            particle.setColor(255, 255, 255, 128);
            particle.setScale(.2);
        }
    }

    public void update(long time) {
        for (int i = 0; i < particles.size(); i++) {
            DefaultParticle particle = (DefaultParticle) particles.get(i);
            float offset = ((time + offsets[i]) % 2000) / 2000.0f;
            if (offset > .5) {
                offset = 1.0f-offset;
            }
            particle.setScale(offset * .3f);
            if (offset < .001) {
                Vec3d o = new Vec3d(random.nextDouble()*.8-.4, random.nextDouble()*.8-.4, random.nextDouble()*.8-.4);
                particle.setOffset(o);
            }
        }
    }

}
