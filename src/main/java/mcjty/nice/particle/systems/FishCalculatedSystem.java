package mcjty.nice.particle.systems;

import mcjty.nice.particle.DefaultCalculatedParticleSystem;
import mcjty.nice.particle.DefaultParticle;
import net.minecraft.util.math.Vec3d;

import java.util.Random;

public class FishCalculatedSystem extends DefaultCalculatedParticleSystem {

    private static Random random = new Random();

    private Vec3d[] position;
    private Vec3d[] movement;


    public FishCalculatedSystem() {
        super(random.nextInt(3)+1);

        int amount = particles.size();
        position = new Vec3d[amount];
        movement = new Vec3d[amount];
        for (int i = 0 ; i < amount ; i++) {
            position[i] = new Vec3d(random.nextDouble()*.8-.4, random.nextDouble()*.8-.4, random.nextDouble()*.8-.4);
            randomMovement(i);
            DefaultParticle particle = (DefaultParticle) particles.get(i);
            int type = random.nextInt(3);
            particle.setUV(((double)type+1D)/8, 0, ((double)type+2D)/8, 1D/8);
            particle.setColor(255, 255, 255, 255);
            particle.setScale(.2);
            particle.setOffset(position[i]);
        }
    }

    private void randomMovement(int i) {
        movement[i] = new Vec3d(random.nextDouble()-.5, random.nextDouble()-.5, random.nextDouble()-.5).normalize().scale(.01);
    }

    public void update(long time) {
        for (int i = 0; i < position.length; i++) {
            Vec3d o = position[i];
            DefaultParticle particle = (DefaultParticle) particles.get(i);
            float offset = (time % 2000) / 2000.0f;
            Vec3d newo = o.add(movement[i].scale(offset));
            if (newo.xCoord <= -.4 || newo.yCoord <= -.4 || newo.zCoord <= -.4 || newo.xCoord >= .4 || newo.yCoord >= .4 || newo.zCoord >= .4) {
                randomMovement(i);
            } else {
                position[i] = newo;
                particle.setOffset(newo);
            }

        }
    }

}
