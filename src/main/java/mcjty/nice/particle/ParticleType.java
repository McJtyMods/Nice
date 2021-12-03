package mcjty.nice.particle;

import mcjty.nice.particle.systems.*;

import java.util.HashMap;
import java.util.Map;

public enum ParticleType {
    SMOKE("smoke", new SmokeSystem()),
    BLINK("blink", new BlinkSystem()),
    FISH("fish", new FishSystem()),
    BUBBLE("bubble", new BubbleSystem()),
    NONE("none", new NullSystem());

    private final String name;
    private final IParticleSystem particleSystem;

    private static Map<String, ParticleType> BY_NAME = new HashMap<>();

    static {
        for (ParticleType value : ParticleType.values()) {
            BY_NAME.put(value.getName(), value);
        }
    }

    ParticleType(String name, IParticleSystem particleSystem) {
        this.name = name;
        this.particleSystem = particleSystem;
    }

    public String getName() {
        return name;
    }

    public IParticleSystem getParticleSystem() {
        return particleSystem;
    }

    public static ParticleType getByName(String name) {
        return BY_NAME.get(name);
    }
}
