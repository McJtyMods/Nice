package mcjty.nice.particle.systems;

import mcjty.nice.particle.ICalculatedParticleSystem;
import mcjty.nice.particle.IParticleSystem;

public class FishSystem implements IParticleSystem {

    @Override
    public ICalculatedParticleSystem createCalculatedParticleSystem(float scale) {
        return new FishCalculatedSystem(scale);
    }

    @Override
    public void update(ICalculatedParticleSystem calculated, long time) {
        ((FishCalculatedSystem) calculated).update(time);
    }
}
