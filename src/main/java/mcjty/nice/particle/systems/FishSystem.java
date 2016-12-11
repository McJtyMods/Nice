package mcjty.nice.particle.systems;

import mcjty.nice.particle.*;
import net.minecraft.util.math.Vec3d;

import java.util.List;
import java.util.Random;

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
