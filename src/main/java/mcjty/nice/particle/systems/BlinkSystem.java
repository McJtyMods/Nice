package mcjty.nice.particle.systems;

import mcjty.nice.particle.ICalculatedParticleSystem;
import mcjty.nice.particle.IParticleSystem;

public class BlinkSystem implements IParticleSystem {

    @Override
    public ICalculatedParticleSystem createCalculatedParticleSystem() {
        return new BlinkCalculatedSystem();
    }

    @Override
    public void update(ICalculatedParticleSystem calculated, long time) {
        ((BlinkCalculatedSystem) calculated).update(time);
    }
}
