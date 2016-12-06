package mcjty.nice.particle;

public interface IParticleSystem {

    // Create the initial calculated particle system.
    ICalculatedParticleSystem createCalculatedParticleSystem();

    // Update it based on the time.
    void update(ICalculatedParticleSystem calculated, long time);
}
