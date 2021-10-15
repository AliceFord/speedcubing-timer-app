package io.github.techiehelper.speedcubingtimer;

import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class AlgorithmFileParser {
    public static List<List<String>> parse(InputStream stream) {
        List<List<String>> returnValue = new LinkedList<>();
        Scanner s = new Scanner(stream);
        for (int i = 0; s.hasNextLine(); i++) {
            returnValue.add(new LinkedList<>());
            for (String current : s.nextLine().split(",")) {
                returnValue.get(i).add(current);
            }
        }

        return returnValue;
    }
}
