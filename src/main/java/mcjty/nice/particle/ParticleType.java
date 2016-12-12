package mcjty.nice.particle;

import mcjty.nice.particle.systems.BlinkSystem;
import mcjty.nice.particle.systems.BubbleSystem;
import mcjty.nice.particle.systems.FishSystem;
import mcjty.nice.particle.systems.SmokeSystem;

public enum ParticleType {
    SMOKE(new SmokeSystem()),
    BLINK(new BlinkSystem()),
    FISH(new FishSystem()),
    BUBBLE(new BubbleSystem());

    private final IParticleSystem particleSystem;

    ParticleType(IParticleSystem particleSystem) {
        this.particleSystem = particleSystem;
    }

    public IParticleSystem getParticleSystem() {
        return particleSystem;
    }
}
