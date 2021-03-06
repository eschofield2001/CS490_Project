package com.company;

import java.util.concurrent.locks.Lock;

/**
 * Class that mimics CPU execution of a list of processes in FIFO order.
 */
public class Executor implements Runnable{
    private CPUPanel cpu;
    private Lock threadLock;

    /**
     * Creates the executor and initializes the threadLock as well as sets the CPUPanel to be updated during process execution
     */
    public Executor(CPUPanel cpu, Lock threadLock){
        this.cpu = cpu;
        this.threadLock = threadLock;
    }

    /**
     * Function to simulate a CPU executing processes one at a time using the processes in Main.processList. After the process is pulled, it is removed from processList
     */
    public void run(){
        int time;
        while (!Main.getIsPaused()) {
            try{
                while(!Main.getProcessList().isEmpty()){
                    //Lock processList while getting necessary information on next process to execute
                    threadLock.lock();
                    try{
                        cpu.setProcess(Main.getProcessList().get(0).getProcessID());
                        cpu.setTimeRem(Main.getProcessList().get(0).getServiceTime());
                        time = Main.getProcessList().get(0).getServiceTime();
                        Main.getModel().removeRow(0);
                        Main.getProcessList().remove(0);
                        //Move index 0 to finished list somewhere in here
                    }finally{
                        threadLock.unlock();
                    }

                    //Execute the process one second at a time, checking each second if the system is paused and pausing execution if it is
                    for (int j = time; j >=0; j--){
                        if(Main.getIsPaused()){
                            //Do nothing if paused
                            Thread.sleep(Main.getTimeUnit());
                            j++;
                        }
                        else{
                            //Sleep for a second and update timer
                            Thread.sleep(Main.getTimeUnit());
                            cpu.setTimeRem(j);
                        }

                    }
                }
            }catch (InterruptedException ex){
                //I don't know what to put here
            }
        }
    }
}
