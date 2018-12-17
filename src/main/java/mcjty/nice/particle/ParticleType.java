package mcjty.nice.particle;

import mcjty.nice.particle.systems.*;

public enum ParticleType {
    SMOKE(new SmokeSystem()),
    BLINK(new BlinkSystem()),
    FISH(new FishSystem()),
    BUBBLE(new BubbleSystem()),
    NONE(new NullSystem());

    private final IParticleSystem particleSystem;

    ParticleType(IParticleSystem particleSystem) {
        this.particleSystem = particleSystem;
    }

    public IParticleSystem getParticleSystem() {
        return particleSystem;
    }
}
