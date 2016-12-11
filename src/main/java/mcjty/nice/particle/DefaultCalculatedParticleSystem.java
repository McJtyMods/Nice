package mcjty.nice.particle;

import java.util.ArrayList;
import java.util.List;

public class DefaultCalculatedParticleSystem implements ICalculatedParticleSystem {

    protected List<IParticle> particles;

    public DefaultCalculatedParticleSystem(int size) {
        particles = new ArrayList<>(size);
        for (int i = 0 ; i < size ; i++) {
            particles.add(new DefaultParticle());
        }
    }

    @Override
    public List<IParticle> getParticles() {
        return particles;
    }
}
