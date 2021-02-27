package be.rouget.puzzles.adventofcode.year2015.day9;

import be.rouget.puzzles.adventofcode.util.ResourceUtils;
import com.google.common.collect.Lists;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class AllInASingleNight {

    private static final String YEAR = "2015";
    private static final String DAY = "09";

    private static final Logger LOG = LogManager.getLogger(AllInASingleNight.class);

    private final List<String> input;
    private final Map<String, List<CityDistance>> distancesByCity;
    private final Set<String> cities;

    private Integer shortestPath = null;
    private Integer longestPath = 0;

    public AllInASingleNight(List<String> input) {

        this.input = input;
        LOG.info("Input has {} lines...", input.size());

        List<CityDistance> forwardDistances = input.stream()
                .map(CityDistance::fromInput)
                .collect(Collectors.toList());
        List<CityDistance> reverseDistances = forwardDistances.stream()
                .map(CityDistance::reverse)
                .collect(Collectors.toList());
        List<CityDistance> distances = Lists.newArrayList();
        distances.addAll(forwardDistances);
        distances.addAll(reverseDistances);
        distancesByCity = distances.stream().collect(Collectors.groupingBy(CityDistance::getStartCity));
        cities = distancesByCity.keySet();
    }

    public static void main(String[] args) {
        List<String> input = ResourceUtils.readLines(YEAR + "/aoc_" + YEAR + "_day" + DAY + "_input.txt");
        AllInASingleNight aoc = new AllInASingleNight(input);
        LOG.info("Result for part 1 is: " + aoc.computeResultForPart1());
        LOG.info("Result for part 2 is: " + aoc.computeResultForPart2());
    }

    public long computeResultForPart1() {
        for (String city : cities) {
            enumeratePathsStartingFrom(city);
        }
        return shortestPath;
    }

    public void enumeratePathsStartingFrom(String city) {
        for (CityDistance possibleCityDistance : distancesByCity.get(city)) {
            List<CityDistance> newPath = Lists.newArrayList(possibleCityDistance);
            List<String> remainingCities = copyExcept(cities, possibleCityDistance.getStartCity(), possibleCityDistance.getEndCity());
            enumeratePaths(newPath, remainingCities);
        }
    }

    public void enumeratePaths(List<CityDistance> currentPath, List<String> remainingCities) {
        String currentCity = currentPath.get(currentPath.size()-1).getEndCity();
        for (CityDistance possibleCityDistance : distancesByCity.get(currentCity)) {
            if (remainingCities.contains(possibleCityDistance.getEndCity())) {
                List<CityDistance> newPath = Lists.newArrayList(currentPath);
                newPath.add(possibleCityDistance);
                List<String> newRemaining = copyExcept(remainingCities, possibleCityDistance.getEndCity());
                if (newRemaining.isEmpty()) {
                    // new complete path
                    processCompletePath(newPath);

                } else {
                    enumeratePaths(newPath, newRemaining);
                }
            }
        }
    }

    private List<String> copyExcept(Collection<String> original, String... elementsToSkip) {
        List<String> copy = Lists.newArrayList(original);
        for (String toSkip : elementsToSkip) {
            copy.remove(toSkip);
        }
        return copy;
    }

    private void processCompletePath(List<CityDistance> newPath) {

        int distance = newPath.stream()
                .mapToInt(CityDistance::getDistance)
                .sum();

//        // Print path
//        String startingCity = newPath.get(0).getStartCity();
//        String pathString = newPath.stream()
//                .map(CityDistance::getEndCity)
//                .collect(Collectors.joining(" -> "));
//        String completePath = startingCity + " -> " + pathString;
//        LOG.info(" Path: ({}) - {}", distance, completePath);

        // Check if shortest
        if (this.shortestPath == null) {
            this.shortestPath = distance;
        }
        this.shortestPath = Math.min(this.shortestPath, distance);

        // Check if longest
        this.longestPath = Math.max(this.longestPath, distance);
    }

    public long computeResultForPart2() {
        return this.longestPath;
    }
}