package mcjty.nice.particle.systems;

import mcjty.nice.particle.ICalculatedParticleSystem;
import mcjty.nice.particle.IParticleSystem;

public class FishSystem implements IParticleSystem {

    @Override
    public ICalculatedParticleSystem createCalculatedParticleSystem() {
        return new FishCalculatedSystem();
    }

    @Override
    public void update(ICalculatedParticleSystem calculated, long time) {
        ((FishCalculatedSystem) calculated).update(time);
    }
}
