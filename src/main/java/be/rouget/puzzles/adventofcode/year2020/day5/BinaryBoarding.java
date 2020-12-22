package be.rouget.puzzles.adventofcode.year2020.day5;

import be.rouget.puzzles.adventofcode.util.ResourceUtils;
import com.google.common.collect.Lists;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class BinaryBoarding {

    private static final String YEAR = "2020";
    private static final String DAY = "05";

    private static Logger LOG = LogManager.getLogger(BinaryBoarding.class);

    private List<String> input;

    public BinaryBoarding(List<String> input) {
        this.input = input;
        LOG.info("Input has {} lines...", input.size());
    }

    public static void main(String[] args) {
        List<String> input = ResourceUtils.readLines(YEAR + "/aoc_" + YEAR + "_day" + DAY + "_input.txt");
        BinaryBoarding aoc = new BinaryBoarding(input);
        LOG.info("Result for part 1 is: " + aoc.computeResultForPart1());
        LOG.info("Result for part 2 is: " + aoc.computeResultForPart2());
    }

    public long computeResultForPart1() {
        return input.stream()
                .map(BoardingPass::new)
                .map(BoardingPass::getSeatId)
                .max(Comparator.naturalOrder())
                .orElseThrow(IllegalStateException::new);
    }

    public long computeResultForPart2() {

        List<BoardingPass> possibleSeats = Lists.newArrayList();
        for (int row = 1; row < 127; row++) {
            for (int column = 0; column < 8; column++) {
                possibleSeats.add(new BoardingPass(row, column));
            }
        }

        List<Integer> takenSeatIds = input.stream()
                .map(BoardingPass::new)
//                .sorted(Comparator.comparing(BoardingPass::getRow).thenComparing(BoardingPass::getColumn))
//                .peek(bp -> LOG.info("Adding pass for seat {}", bp))
                .map(BoardingPass::getSeatId)
                .collect(Collectors.toList());

        for (BoardingPass seat : possibleSeats) {
            LOG.info("Checking seat {}", seat);
            BoardingPass beforeSeat = new BoardingPass(seat.getRow()-1, seat.getColumn());
            BoardingPass afterSeat = new BoardingPass(seat.getRow()+1, seat.getColumn());
            if (takenSeatIds.contains(beforeSeat.getSeatId())
                && takenSeatIds.contains(afterSeat.getSeatId())
                && !takenSeatIds.contains(seat.getSeatId())) {
                return seat.getSeatId();
            }
        }

        throw new IllegalStateException("Did not find a fee seat!");
    }
}