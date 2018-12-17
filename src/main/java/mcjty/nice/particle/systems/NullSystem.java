package mcjty.nice.particle.systems;

import mcjty.nice.particle.DefaultCalculatedParticleSystem;
import mcjty.nice.particle.ICalculatedParticleSystem;
import mcjty.nice.particle.IParticleSystem;

public class NullSystem implements IParticleSystem {

    @Override
    public ICalculatedParticleSystem createCalculatedParticleSystem(float scale) {
        DefaultCalculatedParticleSystem calculated = new DefaultCalculatedParticleSystem(0, scale);
        return calculated;
    }

    @Override
    public void update(ICalculatedParticleSystem calculated, long time) {
    }
}
