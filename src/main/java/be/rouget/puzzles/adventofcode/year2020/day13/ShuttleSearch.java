package be.rouget.puzzles.adventofcode.year2020.day13;

import be.rouget.puzzles.adventofcode.util.ResourceUtils;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.stream.Collectors;

public class ShuttleSearch {

    private static final String YEAR = "2020";
    private static final String DAY = "13";

    private static final Logger LOG = LogManager.getLogger(ShuttleSearch.class);

    private final List<String> input;
    private int startingTimestamp;
    private List<Integer> busIDs;

    public ShuttleSearch(List<String> input) {
        this.input = input;
        LOG.info("Input has {} lines...", input.size());
        this.startingTimestamp = Integer.parseInt(input.get(0));
        LOG.info("Starting timestamp is {}", startingTimestamp);
        this.busIDs = parseBusIds(input.get(1));
        LOG.info("Bus IDs: {}", busIDs);
    }

    private List<Integer> parseBusIds(String line) {
        return Arrays.stream(line.split(","))
                .filter(StringUtils::isNumeric)
                .map(s -> Integer.valueOf(s))
                .collect(Collectors.toList());
    }

    public static void main(String[] args) {
        List<String> input = ResourceUtils.readLines(YEAR + "/aoc_" + YEAR + "_day" + DAY + "_input.txt");
        ShuttleSearch aoc = new ShuttleSearch(input);
        LOG.info("Result for part 1 is: " + aoc.computeResultForPart1());
        LOG.info("Result for part 2 is: " + aoc.computeResultForPart2());
    }

    public long computeResultForPart1() {

        Integer bestBusId = null;
        Integer bestDeparture = null;
        for (Integer busId : busIDs) {
            int departure = firstPossibleDeparture(startingTimestamp, busId);
            if ((bestDeparture == null) || (departure < bestDeparture)) {
                bestDeparture = departure;
                bestBusId = busId;
            }
        }
        return (bestDeparture-startingTimestamp)*bestBusId;
    }

    public long computeResultForPart2() {

        Map<Integer, Integer> offsets = parseOffsets(input.get(1));
        offsets.entrySet().stream()
                .sorted(Comparator.comparing(Map.Entry::getValue))
                .forEach(e -> LOG.info("Bus ID {} at offset {}", e.getKey(), e.getValue()));

        List<Map.Entry<Integer, Integer>> sortedOffsets = offsets.entrySet().stream()
                .sorted(Comparator.comparing(Map.Entry::getValue))
                .collect(Collectors.toList());

        Long timestamp = 0L;
        Long delta = 1L;
        for (Map.Entry<Integer, Integer> offset : sortedOffsets) {
            while ((timestamp + offset.getValue()) % offset.getKey() != 0) {
                timestamp += delta;
            }
            delta = delta * offset.getKey();
        }

        return timestamp;
    }

    private Map<Integer, Integer> parseOffsets(String line) {
        Map<Integer, Integer> offsets = Maps.newHashMap();
        String[] tokens = line.split(",");
        for (int i = 0; i < tokens.length; i++) {
            String token = tokens[i];
            if (StringUtils.isNumeric(token)) {
                offsets.put(Integer.valueOf(token), i);
            }
        }
        return offsets;
    }

    public int firstPossibleDeparture(int startingTimestamp, int busId) {
        int timestamp = 0;
        while (timestamp < startingTimestamp) {
            timestamp += busId;
        }
        return timestamp;
    }
}