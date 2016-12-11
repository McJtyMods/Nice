package mcjty.nice.particle.systems;

import mcjty.nice.particle.*;
import net.minecraft.util.math.Vec3d;

import java.util.List;
import java.util.Random;

public class FishSystem implements IParticleSystem {

    private static Random random = new Random();

    private Vec3d[] position;
    private Vec3d[] movement;

    @Override
    public ICalculatedParticleSystem createCalculatedParticleSystem() {
        int amount = random.nextInt(3)+1;
        position = new Vec3d[amount];
        movement = new Vec3d[amount];

        DefaultCalculatedParticleSystem calculated = new DefaultCalculatedParticleSystem(amount);
        List<IParticle> particles = calculated.getParticles();
        for (int i = 0 ; i < amount ; i++) {
            position[i] = new Vec3d(random.nextDouble(), random.nextDouble(), random.nextDouble());
            movement[i] = new Vec3d(random.nextDouble(), random.nextDouble(), random.nextDouble()).normalize().scale(.05);
            DefaultParticle particle = (DefaultParticle) particles.get(i);
            particle.setUV(1D/8, 0, 2D/8, 1D/8);
            particle.setColor(255, 255, 255, 255);
            particle.setScale(.2);
            particle.setOffset(position[i]);
        }

        return calculated;
    }

    @Override
    public void update(ICalculatedParticleSystem calculated, long time) {
        List<IParticle> particles = calculated.getParticles();
        for (int i = 0 ; i < position.length ; i++) {
            Vec3d o = position[i];
            DefaultParticle particle = (DefaultParticle) particles.get(i);
            float offset = (time % 2000) / 2000.0f;
            Vec3d newo = o.add(movement[i].scale(offset * .1f));
            if (newo.xCoord <= 0 || newo.yCoord <= 0 || newo.zCoord <= 0 || newo.xCoord >= 1 || newo.yCoord >= 1 || newo.zCoord >= 1) {
                movement[i] = new Vec3d(random.nextDouble(), random.nextDouble(), random.nextDouble()).normalize().scale(.05);
            } else {
                position[i] = newo;
                particle.setOffset(newo);
            }

        }

    }
}
