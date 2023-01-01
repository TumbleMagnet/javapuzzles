package be.rouget.puzzles.adventofcode.year2022.day07;

import be.rouget.puzzles.adventofcode.util.SolverUtils;
import com.google.common.annotations.VisibleForTesting;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;


public class NoSpaceLeftOnDevice {

    private static final Logger LOG = LogManager.getLogger(NoSpaceLeftOnDevice.class);
    public static final long FS_TOTAL_SIZE = 70000000L;
    public static final long FS_REQUIRED_SIZE = 30000000L;
    
    
    private final DiskDirectory rootDirectory;

    @SuppressWarnings("java:S2629")
    public static void main(String[] args) {
        List<String> input = SolverUtils.readInput(NoSpaceLeftOnDevice.class);
        NoSpaceLeftOnDevice aoc = new NoSpaceLeftOnDevice(input);
        LOG.info("Result for part 1 is: {}", aoc.computeResultForPart1());
        LOG.info("Result for part 2 is: {}", aoc.computeResultForPart2());
    }

    public NoSpaceLeftOnDevice(List<String> input) {
        LOG.info("Input has {} lines...", input.size());
        rootDirectory = buildFileSystem(input);
    }

    @VisibleForTesting
    protected static DiskDirectory buildFileSystem(List<String> input) {
        return  new FileSystemParser(input).parse();
    }

    public long computeResultForPart1() {
        return rootDirectory.listAllDirectories().stream()
                .filter(directory -> directory.size() <= 100000L)
                .mapToLong(DiskDirectory::size)
                .sum();
    }

    public long computeResultForPart2() {
        
        // Compute how much space we need to free
        long currentlyFree = FS_TOTAL_SIZE - rootDirectory.size();
        if (currentlyFree >= FS_REQUIRED_SIZE) {
            throw new IllegalStateException("No need to delete any directory: free space is " + currentlyFree);
        }
        long minSizeToDelete = FS_REQUIRED_SIZE - currentlyFree;

        return rootDirectory.listAllDirectories().stream()
                .map(DiskDirectory::size)
                .filter(size -> size >= minSizeToDelete)
                .sorted()
                .findFirst()
                .orElseThrow();
    }
}