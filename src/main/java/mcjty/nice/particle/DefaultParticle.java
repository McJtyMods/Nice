package mcjty.nice.particle;

import net.minecraft.util.math.Vec3d;

public class DefaultParticle implements IParticle {

    private Vec3d offset;
    private double scale;

    public DefaultParticle() {
    }

    public DefaultParticle(Vec3d offset, double scale) {
        this.offset = offset;
        this.scale = scale;
    }

    public void setOffset(Vec3d offset) {
        this.offset = offset;
    }

    public void setScale(double scale) {
        this.scale = scale;
    }

    @Override
    public Vec3d getOffset() {
        return offset;
    }

    @Override
    public double getScale() {
        return scale;
    }
}
