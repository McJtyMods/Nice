package mcjty.nice.particle.systems;

import mcjty.nice.particle.DefaultCalculatedParticleSystem;
import mcjty.nice.particle.DefaultParticle;
import net.minecraft.util.math.vector.Vector3d;

import java.util.Random;

public class FishCalculatedSystem extends DefaultCalculatedParticleSystem {

    private static Random random = new Random();

    private Vector3d[] position;
    private Vector3d[] movement;


    public FishCalculatedSystem(float scale) {
        super(random.nextInt(3)+1, scale);

        int amount = particles.size();
        position = new Vector3d[amount];
        movement = new Vector3d[amount];
        for (int i = 0 ; i < amount ; i++) {
            position[i] = new Vector3d(random.nextDouble()*scale-scale/2.0, random.nextDouble()*.8-.8/2.0, random.nextDouble()*scale-scale/2.0);
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
        movement[i] = new Vector3d(random.nextDouble()-.5, random.nextDouble()-.5, random.nextDouble()-.5).normalize().scale(.01);
    }

    public void update(long time) {
        for (int i = 0; i < position.length; i++) {
            Vector3d o = position[i];
            DefaultParticle particle = (DefaultParticle) particles.get(i);
            float offset = (time % 2000) / 2000.0f;
            Vector3d newo = o.add(movement[i].scale(offset));
            if (newo.x <= -.4 || newo.y <= -.4 || newo.z <= -.4 || newo.x >= .4 || newo.y >= .4 || newo.z >= .4) {
                randomMovement(i);
            } else {
                position[i] = newo;
                particle.setOffset(newo);
            }

        }
    }

}
