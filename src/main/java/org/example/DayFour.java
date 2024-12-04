package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Set;
import java.util.stream.Stream;

public class DayFour {

    public Character[][] loadDataFromFile() throws IOException {
        Stream<String> data = Files.lines(Paths.get("src/main/resources/dayfour.txt"));

        String[] lines = data.toArray(String[]::new);
        Character[][] characters = new Character[lines.length][lines[0].length()];
        for (int i = 0; i < lines.length; i++) {
            for (int j = 0; j < lines[i].length(); j++) {
                characters[i][j] = lines[i].charAt(j);
            }
        }

        return characters;
    }

    public void partOne() throws IOException {
        Character[][] chars = loadDataFromFile();
        int count = 0;

        for (int row = 0; row < chars.length; row++) {
            for (int col = 0; col < chars[row].length; col++) {
                if (col + 3 < chars[row].length) {
                    StringBuilder horizontal = new StringBuilder();
                    for (int i = 0; i < 4; i++) {
                        horizontal.append(chars[row][col + i]);
                    }
                    if (horizontal.toString().equals("XMAS") || horizontal.reverse().toString().equals("XMAS")) count++;
                }

                if (row + 3 < chars.length) {
                    StringBuilder vertical = new StringBuilder();
                    for (int i = 0; i < 4; i++) {
                        vertical.append(chars[row + i][col]);
                    }
                    if (vertical.toString().equals("XMAS") || vertical.reverse().toString().equals("XMAS")) count++;
                }

                if (row + 3 < chars.length && col + 3 < chars[row].length) {
                    StringBuilder diagonal = new StringBuilder();
                    for (int i = 0; i < 4; i++) {
                        diagonal.append(chars[row + i][col + i]);
                    }
                    if (diagonal.toString().equals("XMAS") || diagonal.reverse().toString().equals("XMAS")) count++;
                }

                if (row + 3 < chars.length && col - 3 >= 0) {
                    StringBuilder antiDiagonal = new StringBuilder();
                    for (int i = 0; i < 4; i++) {
                        antiDiagonal.append(chars[row + i][col - i]);
                    }
                    if (antiDiagonal.toString().equals("XMAS") || antiDiagonal.reverse().toString().equals("XMAS")) count++;
                }
            }
        }
        System.out.println(count);
    }

    public void partTwo() throws IOException {
        Set<String> validOptions = Set.of("MS", "SM");
        Character[][] chars = loadDataFromFile();
        int count = 0;

        for (int row = 1; row < chars.length - 1; row++) {
            for (int col = 1; col < chars[row].length - 1; col++) {
                if (chars[row][col] == 'A') {
                    var tlbr = new StringBuilder().append(chars[row - 1][col - 1]).append(chars[row + 1][col + 1]).toString();
                    var trbl = new StringBuilder().append(chars[row - 1][col + 1]).append(chars[row + 1][col - 1]).toString();
                    if (validOptions.contains(tlbr) && validOptions.contains(trbl)) {
                        count++;
                    }
                }
            }
        }
        System.out.println(count);
    }

    public static void main(String[] args) {
        DayFour dayFour = new DayFour();

        try {
            dayFour.partOne();
            dayFour.partTwo();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}