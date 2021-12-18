package be.rouget.puzzles.adventofcode.year2021.day12;

import be.rouget.puzzles.adventofcode.util.graph.Vertex;
import org.apache.commons.lang3.StringUtils;

public class Cave extends Vertex {

    public Cave(String name) {
        super(name);
    }

    public boolean isLarge() {
        return StringUtils.isAllUpperCase(getName());
    }
}
