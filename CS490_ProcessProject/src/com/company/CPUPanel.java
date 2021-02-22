package com.company;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class CPUPanel extends JPanel {
    JLabel cpuNameLabel;
    JLabel pExec;
    JLabel timeRem;


    //Creates the panel and initializes the time and process name to blank values
    public CPUPanel(String cpuName){
        setLayout(new GridLayout(3,1));
        Border lineBorder = BorderFactory.createLineBorder(Color.black);
        setBorder(lineBorder);

        cpuNameLabel = new JLabel(cpuName);
        add(cpuNameLabel);

        JPanel cpuExec = new JPanel(new FlowLayout());
        JLabel execText = new JLabel("Executing: ");
        pExec = new JLabel("Idle");
        cpuExec.add(execText);
        cpuExec.add(pExec);
        add(cpuExec);

        JPanel timeRemPanel = new JPanel(new FlowLayout());
        JLabel timeRemText = new JLabel("Time Remaining: ");
        timeRem = new JLabel("N/A");
        timeRemPanel.add(timeRemText);
        timeRemPanel.add(timeRem);
        add(timeRemPanel);
    }

    public void setProcess(String process){
        pExec.setText(process);
    }

    public void setTimeRem(int time){
        timeRem.setText(String.valueOf(time));
    }
}
