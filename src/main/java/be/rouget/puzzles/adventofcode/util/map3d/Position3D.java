package be.rouget.puzzles.adventofcode.util.map3d;

import java.util.List;

public record Position3D(int x, int y, int z) {
    
    public List<Position3D> getFacetNeighbours() {
        return List.of(
                new Position3D(x-1, y, z), new Position3D(x+1, y, z),
                new Position3D(x, y-1, z), new Position3D(x, y+1, z),
                new Position3D(x, y, z-1), new Position3D(x, y, z+1)
        );
    }
}
