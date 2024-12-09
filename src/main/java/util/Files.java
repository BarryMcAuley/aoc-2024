package util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Files {
    public static List<List<Character>> readGrid(String filename) {
        List<List<Character>> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                List<Character> chars = new ArrayList<>();
                for (char c : line.toCharArray()) {
                    chars.add(c);
                }
                lines.add(chars);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return lines;
    }
}
