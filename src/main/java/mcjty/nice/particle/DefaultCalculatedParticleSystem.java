package mcjty.nice.particle;

import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DefaultCalculatedParticleSystem implements ICalculatedParticleSystem {

    protected List<IParticle> particles;

    private final float scale;

    public DefaultCalculatedParticleSystem(int size, float scale) {
        particles = new ArrayList<>(size);
        for (int i = 0 ; i < size ; i++) {
            particles.add(new DefaultParticle());
        }
        this.scale = scale;
    }

    protected Vec3 randomCylindricalOffset(Random random) {
        // Sample the circular cross-section evenly, keeping the full cylinder height.
        double radius = Math.sqrt(random.nextDouble()) * scale / 2.0;
        double angle = random.nextDouble() * Math.PI * 2;
        return new Vec3(Math.cos(angle) * radius, random.nextDouble() * .8 - .4, Math.sin(angle) * radius);
    }

    @Override
    public List<IParticle> getParticles() {
        return particles;
    }

    @Override
    public float getScale() {
        return scale;
    }
}
