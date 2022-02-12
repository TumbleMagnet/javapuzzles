package be.rouget.puzzles.adventofcode.year2021.day22;

import com.google.common.collect.Lists;
import lombok.Value;

import java.util.List;

@Value
public class Cuboid {
    Range xRange;
    Range yRange;
    Range zRange;

    public List<Cuboid> minus(Cuboid other) {

        Cuboid intersection = this.intersection(other);
        if (intersection == null) {
            return List.of(this);
        }

        List<Cuboid> result = Lists.newArrayList();

        // Divide the cuboid on the X axis
        for (Range remainingXRange : this.getXRange().minus(intersection.getXRange())) {
            result.add(new Cuboid(remainingXRange, this.getYRange(), this.getZRange()));
        }

        // Divide the cuboid on the Y axis
        for (Range remainingYRange : this.getYRange().minus(intersection.getYRange())) {
            result.add(new Cuboid(intersection.getXRange(), remainingYRange, this.getZRange()));
        }

        // Divide the cuboid on the Z axis
        for (Range remainingZRange : this.getZRange().minus(intersection.getZRange())) {
            result.add(new Cuboid(intersection.getXRange(), intersection.getYRange(), remainingZRange));
        }

        return result;
    }

    private boolean intersects(Cuboid other) {
        return xRange.intersects(other.getXRange())
                && yRange.intersects(other.getYRange())
                && zRange.intersects(other.getZRange());
    }

    public Cuboid intersection(Cuboid other) {
        if (!this.intersects(other)) {
            return null;
        }
        return new Cuboid(
                this.getXRange().intersection(other.getXRange()),
                this.getYRange().intersection(other.getYRange()),
                this.getZRange().intersection(other.getZRange())
        );
    }

    public long getVolume() {
        return xRange.getLength() * yRange.getLength() * zRange.getLength();
    }
}
