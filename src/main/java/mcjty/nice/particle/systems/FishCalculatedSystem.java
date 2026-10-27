package mcjty.nice.particle.systems;

import mcjty.nice.particle.DefaultCalculatedParticleSystem;
import mcjty.nice.particle.DefaultParticle;
import net.minecraft.world.phys.Vec3;

import java.util.Random;

public class FishCalculatedSystem extends DefaultCalculatedParticleSystem {

    private static final double HORIZONTAL_MARGIN = 1.0 / 16.0;
    private static Random random = new Random();

    private final double horizontalLimit;
    private Vec3[] position;
    private Vec3[] movement;


    public FishCalculatedSystem(float scale) {
        super(random.nextInt(3)+1, scale);

        horizontalLimit = (scale < .5f ? scale / 2.0 : .4) - HORIZONTAL_MARGIN;
        int amount = particles.size();
        position = new Vec3[amount];
        movement = new Vec3[amount];
        for (int i = 0 ; i < amount ; i++) {
            // Use the same inset for spawning and swimming, including small cylinders.
            double horizontalScale = horizontalLimit * 2.0 / scale;
            position[i] = scale < .5f ? randomCylindricalOffset(random).multiply(horizontalScale, 1, horizontalScale)
                    : new Vec3((random.nextDouble()*2-1)*horizontalLimit, random.nextDouble()*.8-.8/2.0,
                    (random.nextDouble()*2-1)*horizontalLimit);
            randomMovement(i);
            DefaultParticle particle = (DefaultParticle) particles.get(i);
            int type = random.nextInt(3);
            particle.setTexture("fish" + (type + 1));
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
            boolean outside = getScale() < .5f
                    ? newo.x * newo.x + newo.z * newo.z >= horizontalLimit * horizontalLimit || newo.y <= -.4 || newo.y >= .4
                    : newo.x <= -horizontalLimit || newo.y <= -.4 || newo.z <= -horizontalLimit
                    || newo.x >= horizontalLimit || newo.y >= .4 || newo.z >= horizontalLimit;
            if (outside) {
                randomMovement(i);
            } else {
                position[i] = newo;
                particle.setOffset(newo);
            }

        }
    }

}
