package mcjty.nice.particle.systems;

import mcjty.nice.particle.DefaultCalculatedParticleSystem;
import mcjty.nice.particle.DefaultParticle;
import net.minecraft.world.phys.Vec3;

import java.util.Random;

public class BlinkCalculatedSystem extends DefaultCalculatedParticleSystem {

    private static Random random = new Random();

    private static long offsets[] = new long[] { 1000, 500, 200, 343, 708, 1700, 3, 1200, 1493, 1200 };

    public BlinkCalculatedSystem(float scale) {
        super(offsets.length, scale);

        int amount = particles.size();
        for (int i = 0 ; i < amount ; i++) {
            DefaultParticle particle = (DefaultParticle) particles.get(i);
            Vec3 o = new Vec3(random.nextDouble()*scale-scale/2.0f, random.nextDouble()*.8-.8/2.0f, random.nextDouble()*scale-scale/2.0f);
            particle.setOffset(o);
            particle.setUV(4D/8, 0, 5D/8, 1D);
            particle.setColor(255, 255, 255, 128);
            particle.setScale(.2);
        }
    }

    public void update(long time) {
        for (int i = 0; i < particles.size(); i++) {
            DefaultParticle particle = (DefaultParticle) particles.get(i);
            float offset = ((time + offsets[i]) % 1000) / 1000.0f;
            if (offset > .5) {
                offset = 1.0f-offset;
            }
            particle.setScale(offset * .3f);
            if (offset < .001) {
                Vec3 o = new Vec3(random.nextDouble()*.8-.4, random.nextDouble()*.8-.4, random.nextDouble()*.8-.4);
                particle.setOffset(o);
            }
        }
    }

}
