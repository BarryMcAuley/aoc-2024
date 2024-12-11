package util;

import util.types.Grid;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


public class FileUtil {
    public static <T> Grid<T> readGrid(String filename, Parser<T> parser, String delimiter) throws IOException {
        var lines = Files.readAllLines(Paths.get(filename));
        return new Grid<T>(lines, parser, delimiter);
    }
}
