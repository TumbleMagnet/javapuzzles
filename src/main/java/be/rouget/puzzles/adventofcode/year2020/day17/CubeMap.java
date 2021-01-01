package be.rouget.puzzles.adventofcode.year2020.day17;

import com.google.common.collect.Maps;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

import static be.rouget.puzzles.adventofcode.year2020.day17.ActiveStatus.ACTIVE;
import static be.rouget.puzzles.adventofcode.year2020.day17.ActiveStatus.INACTIVE;

public class CubeMap {

    private static final Logger LOG = LogManager.getLogger(CubeMap.class);

    private final int width;
    private final int height;
    private final int depth;
    private final Map<Coordinates, ActiveStatus> elements = Maps.newHashMap();

    public CubeMap(int width, int height, int depth) {
        this.width = width;
        this.height = height;
        this.depth = depth;
    }

    public ActiveStatus getStatusAt(Coordinates coordinates) {
        if (!isInMap(coordinates)) {
            throw new IllegalArgumentException("Coordinates " + coordinates + " are not in map!");
        }
        return elements.get(coordinates);
    }

    public void setStatusAt(Coordinates coordinates, ActiveStatus status) {
        elements.put(coordinates, status);
    }

    public CubeMap cycle() {
        CubeMap extendedMap = this.extend();
        // LOG.info("Extended map: \n{}",extendedMap);

        CubeMap newMap = new CubeMap(extendedMap.getWidth(), extendedMap.getHeight(), extendedMap.getDepth());
        for (int x = 0; x < newMap.getWidth(); x++) {
            for (int y = 0; y < newMap.getWidth(); y++) {
                for (int z = 0; z < newMap.getDepth(); z++) {
                    Coordinates coordinates = new Coordinates(x, y, z);
                    newMap.setStatusAt(coordinates, extendedMap.computeNewStatus(coordinates));
                }
            }
        }
        return newMap;
    }

    public ActiveStatus computeNewStatus(Coordinates coordinates) {
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

    private CubeMap extend() {
        CubeMap extendedMap = new CubeMap(getWidth()+2, getHeight()+2, getDepth()+2);
        for (int x = 0; x < extendedMap.getWidth(); x++) {
            for (int y = 0; y < extendedMap.getHeight(); y++) {
                for (int z = 0; z < extendedMap.getDepth(); z++) {
                    Coordinates coordinates = new Coordinates(x, y, z);
                    if (!extendedMap.isOnEdge(coordinates)) {
                        Coordinates oldCoordinates = new Coordinates(coordinates.getX() - 1, coordinates.getY() - 1, coordinates.getZ() - 1);
                        extendedMap.setStatusAt(coordinates, getStatusAt(oldCoordinates));
                    } else {
                        extendedMap.setStatusAt(coordinates, INACTIVE);
                    }
                }
            }
        }
        return extendedMap;
    }

    public int countActiveNeighbours(Coordinates coordinates) {
        int activeCount = 0;
        for (int x = coordinates.getX()-1; x <= coordinates.getX()+1; x++) {
            for (int y = coordinates.getY()-1; y <= coordinates.getY()+1; y++) {
                for (int z = coordinates.getZ()-1; z <= coordinates.getZ()+1; z++) {
                    Coordinates neighbour = new Coordinates(x, y, z);
                    if (isInMap(neighbour) && !neighbour.equals(coordinates) && getStatusAt(neighbour) == ACTIVE) {
                        activeCount++;
                    }
                }
            }
        }
        return activeCount;
    }

    public int countActiveCubes() {
        int activeCount = 0;
        for (int x = 0; x < getWidth(); x++) {
            for (int y = 0; y < getWidth(); y++) {
                for (int z = 0; z < getDepth(); z++) {
                    Coordinates coordinates = new Coordinates(x, y, z);
                    if (getStatusAt(coordinates) == ACTIVE) {
                        activeCount++;
                    }
                }
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

    public boolean isInMap(Coordinates coordinates) {
        return coordinates.getX() >= 0 && coordinates.getX() < getWidth()
                && coordinates.getY() >= 0 && coordinates.getY() < getHeight()
                && coordinates.getZ() >= 0 && coordinates.getZ() < getDepth();
    }

    public boolean isOnEdge(Coordinates coordinates) {
        return coordinates.getX() == 0 || coordinates.getX() == getWidth()-1
                || coordinates.getY() == 0 || coordinates.getY() == getHeight() -1
                || coordinates.getZ() == 0 || coordinates.getZ() == getDepth() -1;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int z = 0; z < getDepth(); z++) {
            for (int y = 0; y < getHeight(); y++) {
                for (int x = 0; x < getWidth(); x++) {
                    sb.append(getStatusAt(new Coordinates(x, y, z)).getMapChar());
                }
                sb.append("\n");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
