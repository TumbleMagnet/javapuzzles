package be.rouget.puzzles.adventofcode.year2024.day09;

import com.google.common.collect.Lists;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.stream.Collectors;

public class FileSystemPart2 {

    private static final Logger LOG = LogManager.getLogger(FileSystemPart2.class);

    // Files sorted by start index
    private List<DiskFile> files;

    public FileSystemPart2(List<DiskElement> diskElements) {

        // Convert values into files
        files = Lists.newArrayList();
        int fileId = 0;
        int blockIndex = 0;
        for (DiskElement element : diskElements) {
            if (element.elementType() == ElementType.FILE) {
                DiskFile file = new DiskFile(fileId, blockIndex, element.length());
                files.add(file);
                fileId++;
            }
            blockIndex += element.length();
        }
        LOG.info("Found {} files...", files.size());
    }

    void defragment() {
        // Extract file ids in reverse order
        List<Integer> fileIds = files.stream()
                .map(DiskFile::fileId)
                .sorted(Collections.reverseOrder())
                .toList();

        // Move each corresponding file to the first available space that is big enough
        for (int fileId : fileIds) {
            DiskFile fileToMove = getFileById(fileId);
            Optional<Integer> possibleStartIndex = findFirstAvailableSpace(fileToMove);
            possibleStartIndex.ifPresent(integer -> moveFile(fileToMove, integer));
        }
    }

    private void moveFile(DiskFile fileToMove, int newStartIndex) {
        if (fileToMove.startIndex() < newStartIndex) {
            throw new IllegalArgumentException("Files should only be moved to the left");
        }
        DiskFile movedFile = fileToMove.moveTo(newStartIndex);

        // Remove old file
        List<DiskFile> tempFiles = files.stream()
                .filter(file -> file != fileToMove)
                .collect(Collectors.toCollection(ArrayList::new));

        // Add moved file and sort by start index
        tempFiles.add(movedFile);
        files = tempFiles.stream()
                .sorted(Comparator.comparing(DiskFile::startIndex))
                .toList();
    }

    private Optional<Integer> findFirstAvailableSpace(DiskFile fileToMove) {
        // List of files before (and including) the file to move
        List<DiskFile> filesBefore = files.stream()
                .filter(file -> file.startIndex() <= fileToMove.startIndex())
                .toList();
        
        // Check spaces between files
        for (int i = 0; i < filesBefore.size()-1; i++) {
            DiskFile firstFile = filesBefore.get(i);
            DiskFile secondFile = filesBefore.get(i+1);
            int startIndexOfEmptySpace = firstFile.startIndex() + firstFile.length();
            int sizeOfEmptySpace = secondFile.startIndex() - startIndexOfEmptySpace;
            if (sizeOfEmptySpace >= fileToMove.length()) {
                // Current space is large enough for file
                return Optional.of(startIndexOfEmptySpace);
            }
        }
        return Optional.empty();
    }

    private DiskFile getFileById(int fileId) {
        return  files.stream()
                .filter(file -> file.fileId() == fileId)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No file found with id " + fileId));
    }

    public long checksum() {
        return files.stream()
                .mapToLong(DiskFile::checksum)
                .sum();
    }
}
