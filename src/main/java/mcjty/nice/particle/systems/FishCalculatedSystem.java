package mcjty.nice.particle.systems;

import mcjty.nice.particle.DefaultCalculatedParticleSystem;
import mcjty.nice.particle.DefaultParticle;
import net.minecraft.world.phys.Vec3;

import java.util.Random;

public class FishCalculatedSystem extends DefaultCalculatedParticleSystem {

    private static Random random = new Random();

    private Vec3[] position;
    private Vec3[] movement;


    public FishCalculatedSystem(float scale) {
        super(random.nextInt(3)+1, scale);

        int amount = particles.size();
        position = new Vec3[amount];
        movement = new Vec3[amount];
        for (int i = 0 ; i < amount ; i++) {
            position[i] = new Vec3(random.nextDouble()*scale-scale/2.0, random.nextDouble()*.8-.8/2.0, random.nextDouble()*scale-scale/2.0);
            randomMovement(i);
            DefaultParticle particle = (DefaultParticle) particles.get(i);
            int type = random.nextInt(3);
            particle.setUV(((double)type+1D)/8, 0, ((double)type+2D)/8, 1D);
            particle.setColor(255, 255, 255, 255);
            particle.setScale(.2);
            particle.setOffset(position[i]);
        }
    }

    private void randomMovement(int i) {
        movement[i] = new Vec3(random.nextDouble()-.5, random.nextDouble()-.5, random.nextDouble()-.5).normalize().scale(.01);
    }

    public void update(long time) {
        for (int i = 0; i < position.length; i++) {
            Vec3 o = position[i];
            DefaultParticle particle = (DefaultParticle) particles.get(i);
            float offset = (time % 2000) / 2000.0f;
            Vec3 newo = o.add(movement[i].scale(offset));
            if (newo.x <= -.4 || newo.y <= -.4 || newo.z <= -.4 || newo.x >= .4 || newo.y >= .4 || newo.z >= .4) {
                randomMovement(i);
            } else {
                position[i] = newo;
                particle.setOffset(newo);
            }

        }
    }

}
