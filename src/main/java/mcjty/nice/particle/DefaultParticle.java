package mcjty.nice.particle;

import net.minecraft.util.math.Vec3d;

public class DefaultParticle implements IParticle {

    private Vec3d offset;
    private double scale;
    private double u1;
    private double u2;
    private double v1;
    private double v2;

    public DefaultParticle() {
    }

    public DefaultParticle(Vec3d offset, double scale, double u1, double u2, double v1, double v2) {
        this.offset = offset;
        this.scale = scale;
        this.u1 = u1;
        this.u2 = u2;
        this.v1 = v1;
        this.v2 = v2;
    }

    public void setOffset(Vec3d offset) {
        this.offset = offset;
    }

    public void setScale(double scale) {
        this.scale = scale;
    }

    public void setUV(double u1, double v1, double u2, double v2) {
        this.u1 = u1;
        this.u2 = u2;
        this.v1 = v1;
        this.v2 = v2;
    }

    @Override
    public Vec3d getOffset() {
        return offset;
    }

    @Override
    public double getScale() {
        return scale;
    }

    @Override
    public double getU1() {
        return u1;
    }

    @Override
    public double getU2() {
        return u2;
    }

    @Override
    public double getV1() {
        return v1;
    }

    @Override
    public double getV2() {
        return v2;
    }
}
