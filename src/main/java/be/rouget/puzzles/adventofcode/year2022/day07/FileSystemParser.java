package be.rouget.puzzles.adventofcode.year2022.day07;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileSystemParser {

    private static final Logger LOG = LogManager.getLogger(FileSystemParser.class);

    private final List<String> input;
    private int inputIndex;

    public FileSystemParser(List<String> input) {
        this.input = input;
    }

    public DiskDirectory parse() {
        inputIndex = 0;
        return parseDirectory();
    }

    private DiskDirectory parseDirectory() {
        
        // Enter directory
        String changeDirectoryCommand = readNextLine();
        if (!isEnterDirectoryCommand(changeDirectoryCommand)) {
            throw new IllegalStateException("Expected enter directory command, got " + changeDirectoryCommand);
        }
        String directoryName = extractTargetDirectory(changeDirectoryCommand);
        LOG.info("Current directory is {}", directoryName);

        // List contents of directory
        List<String> listOutput = parseOutputOfListCommand();
        LOG.info("Directory {} contains:", directoryName);
        listOutput.forEach(line -> LOG.info(" - {}", line));
        
        // Enter children repositories
        Map<String, DiskDirectory> childrenDirectoriesByName = parseChildrenDirectories();

        // Build list of children for current directory
        List<DiskItem> children = listOutput.stream()
                .map(childLine -> toDiskItem(childLine, childrenDirectoriesByName))
                .toList();
        
        // Return directory
        return new DiskDirectory(directoryName, children);
    }

    private Map<String, DiskDirectory> parseChildrenDirectories() {
        Map<String, DiskDirectory> childrenDirectoriesByName = Maps.newHashMap();
        if (!hasMoreInputLines()) {
            return childrenDirectoriesByName;
        }
        String nextCommand = readNextLine();
        while (isEnterDirectoryCommand(nextCommand)) {
            putBackLastReadLine();
            DiskDirectory childDirectory = parseDirectory();
            childrenDirectoriesByName.put(childDirectory.name(), childDirectory);
            if (!hasMoreInputLines()) {
                return childrenDirectoriesByName;
            }
            nextCommand = readNextLine();
        }
        if (!isExitDirectoryCommand(nextCommand)) {
            throw new IllegalStateException("Expected exit directory command, got " + nextCommand);
        }
        return childrenDirectoriesByName;
    }

    private DiskItem toDiskItem(String inputLine, Map<String, DiskDirectory> childrenDirectoriesByName) {
        if (isDirectoryListLine(inputLine)) {
            String directoryName = extractListedDirectory(inputLine);
            DiskDirectory directory = childrenDirectoriesByName.get(directoryName);
            if (directory == null) {
                throw new IllegalStateException("Could not find directory with name " + directoryName);
            }
            return directory;
        } else {
            return parseFileListOutput(inputLine);
        }
    }

    private DiskFile parseFileListOutput(String line) {
        Pattern pattern = Pattern.compile("(\\d+) (.+)");
        Matcher matcher = pattern.matcher(line);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Cannot parse file line: " + line);
        }
        long size = Long.parseLong(matcher.group(1));
        String name = matcher.group(2);
        return new DiskFile(name, size);
    }

    private String extractListedDirectory(String input) {
        Pattern pattern = Pattern.compile("dir (.+)");
        Matcher matcher = pattern.matcher(input);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Cannot parse directory line: " + input);
        }
        return matcher.group(1);
    }

    private boolean isDirectoryListLine(String line) {
        return line.startsWith("dir ");
    }

    private boolean isEnterDirectoryCommand(String line) {
        return line.startsWith("$ cd ") && !isExitDirectoryCommand(line);
    }

    private boolean isExitDirectoryCommand(String line) {
        return "$ cd ..".equals(line);
    }

    private List<String> parseOutputOfListCommand() {
        String listCommand = readNextLine();
        if (!isListCommand(listCommand)) {
            throw new IllegalStateException("Expected list command, got " + listCommand);
        }
        List<String> listOutput = Lists.newArrayList();
        if (!hasMoreInputLines()) {
            return listOutput;
        }
        String nextLine = readNextLine();
        while (isListOutput(nextLine)) {
            listOutput.add(nextLine);
            if (hasMoreInputLines()) {
                nextLine = readNextLine();
            } else {
                return listOutput;
            }
        }
        putBackLastReadLine();
        return listOutput;
    }

    private void putBackLastReadLine() {
        LOG.info("Rewinding last line...");
        inputIndex--;
    }

    private boolean isListOutput(String line) {
        return !line.startsWith("$");
    }

    private boolean isListCommand(String line) {
        return "$ ls".equals(line);
    }

    private String extractTargetDirectory(String changeDirectoryCommand) {
        Pattern pattern = Pattern.compile("\\$ cd (.+)");
        Matcher matcher = pattern.matcher(changeDirectoryCommand);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Cannot parse input: " + changeDirectoryCommand);
        }
        return matcher.group(1);
    }

    private String readNextLine() {
        if (!hasMoreInputLines()) {
            throw new IllegalStateException("No more lines in input!");
        }
        String line = input.get(inputIndex);
        inputIndex++;
        LOG.info("Read line: {}", line);
        return line;
    }

    private boolean hasMoreInputLines() {
        return inputIndex < input.size();
    }
}
