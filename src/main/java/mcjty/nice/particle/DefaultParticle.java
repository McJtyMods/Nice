package mcjty.nice.particle;

import net.minecraft.util.math.vector.Vector3d;

public class DefaultParticle implements IParticle {

    private Vector3d offset;
    private double scale;
    private double u1;
    private double u2;
    private double v1;
    private double v2;
    private int r;
    private int g;
    private int b;
    private int a;

    public DefaultParticle() {
    }

    public DefaultParticle(Vector3d offset, double scale, double u1, double u2, double v1, double v2) {
        this.offset = offset;
        this.scale = scale;
        this.u1 = u1;
        this.u2 = u2;
        this.v1 = v1;
        this.v2 = v2;
    }

    public void setOffset(Vector3d offset) {
        this.offset = offset;
    }

    public void setScale(double scale) {
        this.scale = scale;
    }

    public void setColor(int r, int g, int b, int a) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.a = a;
    }

    public void setUV(double u1, double v1, double u2, double v2) {
        this.u1 = u1;
        this.u2 = u2;
        this.v1 = v1;
        this.v2 = v2;
    }

    @Override
    public Vector3d getOffset() {
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

    @Override
    public int getR() {
        return r;
    }

    @Override
    public int getG() {
        return g;
    }

    @Override
    public int getB() {
        return b;
    }

    @Override
    public int getA() {
        return a;
    }
}
