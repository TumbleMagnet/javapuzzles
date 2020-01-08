package be.rouget.puzzles.adventofcode.year2019;

import be.rouget.puzzles.adventofcode.year2019.AoC2019Day12.Moon;
import be.rouget.puzzles.adventofcode.year2019.AoC2019Day12.Position;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import static org.junit.Assert.*;

public class AoC2019Day12Test {

    private static Logger LOG = LogManager.getLogger(AoC2019Day12Test.class);
    @Test
    public void simulateStep() {
        Moon[] moons = new Moon[] {
                new Moon(new Position(-8, -10, 0)),
                new Moon(new Position(5, 5, 10)),
                new Moon(new Position(2, -7, 3)),
                new Moon(new Position(9, -8, -3)),
        };
        System.out.println("After 0 step:");
        AoC2019Day12.printStatus(moons);

        for (int i = 0; i < 100; i++) {
            AoC2019Day12.simulateStep(moons);
        }
        System.out.println("After 100 steps:");
        AoC2019Day12.printStatus(moons);
        System.out.println("Energy: " + AoC2019Day12.computeTotalEnergy(moons));
    }
}