package be.rouget.puzzles.adventofcode.year2015.day13;

import be.rouget.puzzles.adventofcode.util.PermutationGenerator;
import be.rouget.puzzles.adventofcode.util.ResourceUtils;
import com.google.common.collect.Lists;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class KnightsOfTheDinnerTable {

    private static final String YEAR = "2015";
    private static final String DAY = "13";

    private static final Logger LOG = LogManager.getLogger(KnightsOfTheDinnerTable.class);

    private Map<GuestPair, SeatingHappiness> happinessMap;
    private Set<String> guests;

    public KnightsOfTheDinnerTable(List<String> input) {
        LOG.info("Input has {} lines...", input.size());

        List<SeatingHappiness> seatingHappinessList = input.stream()
                .map(SeatingHappiness::fromInput)
                .collect(Collectors.toList());

        guests = seatingHappinessList.stream()
                .map(sh -> sh.getGuestPair().getSeater())
                .collect(Collectors.toSet());
        LOG.info("There are {} guests...", guests.size());

        happinessMap = seatingHappinessList.stream().collect(Collectors.toMap(SeatingHappiness::getGuestPair, Function.identity()));
    }

    public static void main(String[] args) {
        List<String> input = ResourceUtils.readLines(YEAR + "/aoc_" + YEAR + "_day" + DAY + "_input.txt");
        KnightsOfTheDinnerTable aoc = new KnightsOfTheDinnerTable(input);
        LOG.info("Result for part 1 is: " + aoc.computeResultForPart1());
        LOG.info("Result for part 2 is: " + aoc.computeResultForPart2());
    }

    public long computeResultForPart1() {
        return computeHappinessOfBestSeating();
    }

    private int computeHappinessOfBestSeating() {
        return enumerateSeatings().stream()
                .map(this::computeTotalHappiness)
                .mapToInt(Integer::intValue)
                .max()
                .orElseThrow(() -> new IllegalStateException("No seatings!"));
    }

    private List<List<String>> enumerateSeatings() {
        return new PermutationGenerator<String>(Lists.newArrayList(guests)).generatePermutations();
    }

    private int computeTotalHappiness(List<String> seatings) {
        int totalHappiness = 0;
        for (int i = 0; i < seatings.size(); i++) {
            String first = seatings.get(i);
            String second = seatings.get(i+1 >= seatings.size() ? 0 : i+1);
            totalHappiness += computeHappiness(first, second);
        }
        return totalHappiness;
    }


    private int computeHappiness(String guest1, String guest2) {
        int happiness1 = happinessMap.get(new GuestPair(guest1, guest2)).getHappinessChange();
        int happiness2 = happinessMap.get(new GuestPair(guest2, guest1)).getHappinessChange();
        return happiness1 + happiness2;
    }

    public long computeResultForPart2() {

        // Add myself
        String myself = "myself";
        for (String guest : guests) {
            SeatingHappiness seating1 = new SeatingHappiness(new GuestPair(myself, guest), 0);
            happinessMap.put(seating1.getGuestPair(), seating1);
            SeatingHappiness seating2 = new SeatingHappiness(new GuestPair(guest, myself), 0);
            happinessMap.put(seating2.getGuestPair(), seating2);
        }
        guests.add(myself);

        return computeHappinessOfBestSeating();
    }
}