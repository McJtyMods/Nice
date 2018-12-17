package mcjty.nice.particle;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public List<IParticle> getParticles() {
        return particles;
    }

    @Override
    public float getScale() {
        return scale;
    }
}
