package mcjty.nice.particle;

import net.minecraft.world.phys.Vec3;

public interface IParticle {

    Vec3 getOffset();

    double getScale();

    double getU1();
    double getU2();

    double getV1();
    double getV2();

    default String getTextureType() { return null; }

    default int getTextureSeed() { return 0; }

    default long getTextureStartTime() { return 0; }

    int getR();
    int getG();
    int getB();
    int getA();

}
