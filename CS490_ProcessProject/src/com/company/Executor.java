package com.company;

import javax.swing.*;

public class Executor implements Runnable{
    CPUPanel cpu;

    public Executor(CPUPanel cpu){
        this.cpu = cpu;
    }
    public void run(){
        int time = 0;
        while (Main.isPaused == false) {

            try{
                while(Main.processList.isEmpty() == false ){
                    //should maybe set up a lock here?
                    cpu.setProcess(Main.processList.get(0).getProcessID());
                    cpu.setTimeRem(Main.processList.get(0).getServiceTime());
                    time = Main.processList.get(0).getServiceTime();
                    Main.model.removeRow(0);
                    Main.processList.remove(0);
                    //Move index 0 to finished list
                    for (int j = time; j >=0; j--){
                        if(Main.isPaused == true){
                            //do nothing
                            Thread.sleep((long)Main.timeUnit);
                            j++;
                        }
                        else{
                            Thread.sleep((long)Main.timeUnit);
                            cpu.setTimeRem(j);
                        }

                    }
                }
            }catch (InterruptedException ex){
                //idk yet
            }
        }
    }
}
