package mcjty.nice.particle;

import net.minecraft.util.math.vector.Vector3d;

public interface IParticle {

    Vector3d getOffset();

    double getScale();

    double getU1();
    double getU2();

    double getV1();
    double getV2();

    int getR();
    int getG();
    int getB();
    int getA();

}
