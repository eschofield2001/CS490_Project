package operating_system.utils;

import java.io.File;
import java.io.FileNotFoundException;

import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;

public class Utils {

    public static Map<Integer, String> readFile(String path) throws FileNotFoundException {

        File file = new File(path);
        Scanner sc = new Scanner(file);

        Map<Integer, String> allLines = new HashMap<Integer, String>();

        int count = 0;

        while (sc.hasNextLine()) {
            allLines.put(count,sc.nextLine());
            count ++;

        }
        return allLines;
    }
}
