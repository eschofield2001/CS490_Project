package operating_system;

import operating_system.utils.Utils;

import java.io.FileNotFoundException;
import java.util.Map;

import operating_system.Processes;


public class Main {

    public static String currentProcess = "";
    public static Integer serviceTime = 0;

    public static void main(String[] args) throws FileNotFoundException {
        Map<Integer, String> inputProcesses = Utils.readFile("src/operating_system/resources/input_file.csv");

        Processes p = new Processes();

        for (int i = 0; i < inputProcesses.size(); i++) {
            String[] input = inputProcesses.get(i).split(",");
            currentProcess = input[1];
            serviceTime = Integer.parseInt(input[2].trim());

            Thread t = new Thread(p, currentProcess);
            t.run();
        }
    }
}
