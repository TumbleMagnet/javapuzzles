package be.rouget.puzzles.adventofcode.year2019;

import be.rouget.puzzles.adventofcode.util.ResourceUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class AoC2019Day01 {

    private static Logger LOG = LogManager.getLogger(AoC2019Day01.class);

    public static void main(String[] args) {
        List<String> input = ResourceUtils.readLines("aoc_2019_day01_input.txt");
        long fuelRequired = AoC2019Day01.measureRequiredFuel(input);
        LOG.info("Total fuel required: " + fuelRequired);
    }

    private static long measureRequiredFuel(List<String> input) {

        long totalFuel = 0;
        for (String massString : input) {
            long mass = Long.valueOf(massString);
            totalFuel += fuelForModule(mass);
        }

        return totalFuel;
    }

    public static long fuelForModule(long moduleMass) {

        long fuel = fuelForMass(moduleMass);

        long moreFuel = fuelForMass(fuel);
        while (moreFuel >0 ) {
            fuel += moreFuel;
            moreFuel = fuelForMass(moreFuel);
        }
        return fuel;
    }

    private static long fuelForMass(long mass) {
        long result = (mass / 3L) - 2L;
        return result > 0L ? result : 0l;
    }
}