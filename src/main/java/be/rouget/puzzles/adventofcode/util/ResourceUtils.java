package be.rouget.puzzles.adventofcode.util;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

public class ResourceUtils {
    private static String RESOURCE_PATH = "C:\\programming\\projects\\javapuzzles\\src\\main\\resources\\";

    public static String readIntoString(String resourceName) {
        try {
            return FileUtils.readFileToString(new File(RESOURCE_PATH + resourceName), Charset.defaultCharset());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<String> readLines(String resourceName) {
        try {
            return FileUtils.readLines(new File(RESOURCE_PATH + resourceName), Charset.defaultCharset());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
