package mcjty.nice.particle;

import net.minecraft.util.math.Vec3d;

public interface IParticle {

    Vec3d getOffset();

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
