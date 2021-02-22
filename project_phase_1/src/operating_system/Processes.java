package operating_system;

public class Processes implements Runnable {

    @Override
    public void run() {

        System.out.println("Current process: " + Main.currentProcess);
        for(Integer i = Main.serviceTime; i >= 0; i--){
            System.out.println("Remaining Time for " + Main.currentProcess + ": " + String.valueOf(i));
        }
    }
}
