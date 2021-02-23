package com.company;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Class that mimics CPU execution of a list of processes in FIFO order.
 */
public class Executor implements Runnable{
    CPUPanel cpu;
    Lock threadLock;

    /**
     * Creates the executor and initializes the threadLock as well as sets the CPUPanel to be updated during process execution
     */
    public Executor(CPUPanel cpu){
        this.cpu = cpu;
        threadLock = new ReentrantLock();
    }

    /**
     * Function to simulate a CPU executing processes one at a time using the processes in Main.processList. After the process is pulled, it is removed from processList
     */
    public void run(){
        int time;
        while (!Main.isPaused) {
            try{
                while(!Main.processList.isEmpty()){
                    //Lock processList while getting necessary information on next process to execute
                    threadLock.lock();
                    try{
                        cpu.setProcess(Main.processList.get(0).getProcessID());
                        cpu.setTimeRem(Main.processList.get(0).getServiceTime());
                        time = Main.processList.get(0).getServiceTime();
                        Main.model.removeRow(0);
                        Main.processList.remove(0);
                        //Move index 0 to finished list somewhere in here
                    }finally{
                        threadLock.unlock();
                    }

                    //Execute the process one second at a time, checking each second if the system is paused and pausing execution if it is
                    for (int j = time; j >=0; j--){
                        if(Main.isPaused){
                            //Do nothing if paused
                            Thread.sleep(Main.timeUnit);
                            j++;
                        }
                        else{
                            //Sleep for a second and update timer
                            Thread.sleep(Main.timeUnit);
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
