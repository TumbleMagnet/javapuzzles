package be.rouget.puzzles.adventofcode.year2020.day17;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;

import static be.rouget.puzzles.adventofcode.year2020.day17.ActiveStatus.ACTIVE;
import static be.rouget.puzzles.adventofcode.year2020.day17.ActiveStatus.INACTIVE;

public class HyperCubeMap {

    private final int width;
    private final int height;
    private final int depth;
    private final int duration;
    private final Map<HyperCoordinates, ActiveStatus> elements = Maps.newHashMap();

    public HyperCubeMap(int width, int height, int depth, int duration) {
        this.width = width;
        this.height = height;
        this.depth = depth;
        this.duration = duration;
    }

    public ActiveStatus getStatusAt(HyperCoordinates coordinates) {
        if (!isInMap(coordinates)) {
            throw new IllegalArgumentException("HyperCoordinates " + coordinates + " are not in map!");
        }
        return elements.get(coordinates);
    }

    public void setStatusAt(HyperCoordinates coordinates, ActiveStatus status) {
        elements.put(coordinates, status);
    }

    public HyperCubeMap cycle() {
        HyperCubeMap extendedMap = this.extend();
        HyperCubeMap newMap = new HyperCubeMap(extendedMap.getWidth(), extendedMap.getHeight(), extendedMap.getDepth(), extendedMap.getDuration());
        for (HyperCoordinates coordinates : enumerateCoordinates(newMap)) {
            newMap.setStatusAt(coordinates, extendedMap.computeNewStatus(coordinates));
        }
        return newMap;
    }

    public ActiveStatus computeNewStatus(HyperCoordinates coordinates) {
        int activeNeighborCount = countActiveNeighbours(coordinates);
        ActiveStatus currentStatus = getStatusAt(coordinates);
        if (currentStatus == ACTIVE) {
            return (activeNeighborCount == 2 || activeNeighborCount == 3) ? ACTIVE : INACTIVE;
        } else if (currentStatus == INACTIVE) {
            return activeNeighborCount == 3 ? ACTIVE : INACTIVE;
        } else {
            throw new IllegalStateException("Unsupported status " + currentStatus);
        }
    }

    private HyperCubeMap extend() {
        HyperCubeMap extendedMap = new HyperCubeMap(getWidth()+2, getHeight()+2, getDepth()+2, getDuration()+2);
        for (HyperCoordinates coordinates : enumerateCoordinates(extendedMap)) {
            if (!extendedMap.isOnEdge(coordinates)) {
                HyperCoordinates oldHyperCoordinates = new HyperCoordinates(coordinates.getX() - 1, coordinates.getY() - 1, coordinates.getZ() - 1, coordinates.getW()-1);
                extendedMap.setStatusAt(coordinates, getStatusAt(oldHyperCoordinates));
            } else {
                extendedMap.setStatusAt(coordinates, INACTIVE);
            }
        }
        return extendedMap;
    }

    public int countActiveNeighbours(HyperCoordinates coordinates) {
        int activeCount = 0;
        for (HyperCoordinates neighbour : enumerateCoordinates(
                new Range(coordinates.getX()-1, coordinates.getX()+1),
                new Range(coordinates.getY()-1, coordinates.getY()+1),
                new Range(coordinates.getZ()-1, coordinates.getZ()+1),
                new Range(coordinates.getW()-1, coordinates.getW()+1))) {
            if (isInMap(neighbour) && !neighbour.equals(coordinates) && getStatusAt(neighbour) == ACTIVE) {
                activeCount++;
            }
        }
        return activeCount;
    }

    public int countActiveCubes() {
        int activeCount = 0;
        for (HyperCoordinates c : enumerateCoordinates(this)) {
            if (getStatusAt(c) == ACTIVE) {
                activeCount++;
            }
        }
        return activeCount;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getDepth() {
        return depth;
    }

    public int getDuration() {
        return duration;
    }

    public boolean isInMap(HyperCoordinates coordinates) {
        return coordinates.getX() >= 0 && coordinates.getX() < getWidth()
                && coordinates.getY() >= 0 && coordinates.getY() < getHeight()
                && coordinates.getZ() >= 0 && coordinates.getZ() < getDepth()
                && coordinates.getW() >= 0 && coordinates.getW() < getDuration();
    }

    public boolean isOnEdge(HyperCoordinates coordinates) {
        return coordinates.getX() == 0 || coordinates.getX() == getWidth()-1
                || coordinates.getY() == 0 || coordinates.getY() == getHeight() -1
                || coordinates.getZ() == 0 || coordinates.getZ() == getDepth() -1
                || coordinates.getW() == 0 || coordinates.getW() == getDuration() -1;
    }

    private List<HyperCoordinates> enumerateCoordinates(Range xRange, Range yRange, Range zRange, Range wRange) {
        List<HyperCoordinates> coordinates = Lists.newArrayList();
        for (int x = xRange.getFrom(); x <= xRange.getTo(); x++) {
            for (int y = yRange.getFrom(); y <= yRange.getTo(); y++) {
                for (int z = zRange.getFrom(); z <= zRange.getTo(); z++) {
                    for (int w = wRange.getFrom(); w <= wRange.getTo(); w++) {
                        coordinates.add(new HyperCoordinates(x, y, z, w));
                    }
                }
            }
        }
        return coordinates;
    }

    private List<HyperCoordinates> enumerateCoordinates(HyperCubeMap map) {
        return enumerateCoordinates(
                new Range(0, map.getWidth()-1),
                new Range(0, map.getHeight()-1),
                new Range(0, map.getDepth()-1),
                new Range(0, map.getDuration()-1)
        );
    }
}
