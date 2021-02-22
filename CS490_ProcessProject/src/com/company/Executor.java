package com.company;

public class Executor implements Runnable{
    CPUPanel cpu;

    public Executor(CPUPanel cpu){
        this.cpu = cpu;
    }
    public void run(){
        while (Main.isPaused == false) {

            try{
                while(Main.processList.isEmpty() == false ){
                    cpu.setProcess(Main.processList.get(0).getProcessID());
                    cpu.setTimeRem(Main.processList.get(0).getServiceTime());
                    for (int j = Main.processList.get(0).getServiceTime(); j >=0; j--){
                        if(Main.isPaused == true){
                            //do nothing
                            Thread.sleep((long)1000);
                            j++;
                        }
                        else{
                            Thread.sleep((long)1000);
                            cpu.setTimeRem(j);
                        }

                    }
                    Main.processList.remove(0);
                    //Move index 0 to finished list
                }
            }catch (InterruptedException ex){
                //idk yet
            }
        }
    }
}
