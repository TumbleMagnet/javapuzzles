package be.rouget.puzzles.adventofcode.year2024.day09;

import com.google.common.collect.Lists;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

import static be.rouget.puzzles.adventofcode.year2024.day09.ElementType.EMPTY_SPACE;
import static be.rouget.puzzles.adventofcode.year2024.day09.ElementType.FILE;

public class FileSystemPart1 {

    private static final Logger LOG = LogManager.getLogger(FileSystemPart1.class);

    private final List<DiskBlock> blocks;

    public FileSystemPart1(List<DiskElement> diskElements) {

        // Fill list of blocks from input elements
        blocks = Lists.newArrayList();
        int fileId = 0;
        for (DiskElement element : diskElements) {
            if (element.elementType() == FILE) {
                for (int i = 0; i < element.length(); i++) {
                    blocks.add(new FileBlock(fileId));
                }
                fileId++;
            } else if (element.elementType() == EMPTY_SPACE) {
                for (int i = 0; i < element.length(); i++) {
                    blocks.add(new EmptyBlock());
                }
            }
        }
        LOG.info("File system has {} blocks...", blocks.size());
    }

    void defragment() {
        // Defragment the disk by filling early empty block with file blocks from the end
        for (int i = 0; i < blocks.size(); i++) {
            DiskBlock currentBlock = blocks.get(i);
            if (currentBlock instanceof EmptyBlock) {
                // Find first non-empty block from endIndex, going backward
                int endBlockIndex = findLastNonEmptyBlock().orElse(0);
                if (endBlockIndex > i) {
                    // Switch the two blocks
                    DiskBlock targetBlock = blocks.get(endBlockIndex);
                    blocks.set(i, targetBlock);
                    blocks.set(endBlockIndex, currentBlock);
                }
            }
        }
    }

    private Optional<Integer> findLastNonEmptyBlock() {
        for (int i = blocks.size()-1; i >=0; i--) {
            if (blocks.get(i) instanceof FileBlock) {
                return Optional.of(i);
            }
        }
        return Optional.empty();
    }


    public long checksum() {
        long checksum = 0L;
        for (int i = 0; i < blocks.size() - 1; i++) {
            if (blocks.get(i) instanceof FileBlock(long fileId)) {
                checksum += i * fileId;
            }
        }
        return checksum;
    }
}
