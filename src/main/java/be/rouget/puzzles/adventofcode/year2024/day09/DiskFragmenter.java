package be.rouget.puzzles.adventofcode.year2024.day09;

import be.rouget.puzzles.adventofcode.util.AocStringUtils;
import be.rouget.puzzles.adventofcode.util.SolverUtils;
import com.google.common.collect.Lists;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;


public class DiskFragmenter {

    private static final Logger LOG = LogManager.getLogger(DiskFragmenter.class);
    private final List<DiskElement> diskElements;

    @SuppressWarnings("java:S2629")
    public static void main(String[] args) {
        List<String> input = SolverUtils.readInput(DiskFragmenter.class);
        DiskFragmenter aoc = new DiskFragmenter(input);
        LOG.info("Result for part 1 is: {}", aoc.computeResultForPart1());
        LOG.info("Result for part 2 is: {}", aoc.computeResultForPart2());
    }

    public DiskFragmenter(List<String> input) {
        LOG.info("Input has {} lines...", input.size());
        String diskMap = input.getFirst();

        // Parse disk map into integer values
        List<Integer> values = AocStringUtils.extractCharacterList(diskMap).stream()
                .map(Integer::parseInt)
                .toList();
        LOG.info("Found {} values...", values.size());

        // Convert integer values in either file or empty space
        ElementType nextType = ElementType.FILE; // First element is a file
        diskElements = Lists.newArrayList();
        for (int value : values) {
            diskElements.add(new DiskElement(nextType, value));
            nextType = nextType == ElementType.FILE ? ElementType.EMPTY_SPACE : ElementType.FILE;
        }
    }

    public long computeResultForPart1() {
        FileSystemPart1 fileSystem = new FileSystemPart1(diskElements);
        fileSystem.defragment();
        return fileSystem.checksum();
    }

    public long computeResultForPart2() {
        FileSystemPart2 fileSystem = new FileSystemPart2(diskElements);
        fileSystem.defragment();
        return fileSystem.checksum();
    }
}