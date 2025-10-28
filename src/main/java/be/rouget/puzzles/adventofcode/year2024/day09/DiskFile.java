package be.rouget.puzzles.adventofcode.year2024.day09;

public record DiskFile(int fileId, int startIndex, int length) {

    public DiskFile moveTo(int newStartIndex) {
        return new DiskFile(fileId, newStartIndex, length);
    }

    public long checksum() {
        long checksum = 0L;
        for (int i = startIndex; i < startIndex+length; i++) {
            checksum += ((long) i) * ((long) fileId);
        }
        return checksum;
    }
}
