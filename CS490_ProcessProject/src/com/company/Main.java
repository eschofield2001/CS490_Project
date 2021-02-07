package com.company;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Main controller of the project. Displays the GUI and controls the flow of execution of the processes
 */
public class Main {

    private static final int FRAME_WIDTH = 1000;
    private static final int FRAME_HEIGHT = 1000;
    private static ArrayList<Process> processList = new ArrayList<>();

    public static void main(String[] args) {

        /*
        Create main GUI of the project -------------------------------------------------------------------------------------
         */
        JFrame mainFrame = new JFrame("Process Simulation", null);
        Dimension d = new Dimension();
        d.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        mainFrame.setSize(d);
        mainFrame.setLayout(new BorderLayout());

        //Create table that displays the current loaded processes
        JPanel tableDisplay = new JPanel(new BorderLayout());
        Object columns[] = {"Process Name", "Service Time"};
        DefaultTableModel model = new DefaultTableModel();
        model.setColumnIdentifiers(columns);
        JTable processTable = new JTable(model);
        JScrollPane jsp = new JScrollPane(processTable, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        JLabel tableTitle = new JLabel("Waiting Process Queue");

        tableDisplay.add(jsp, BorderLayout.CENTER);
        tableDisplay.add(tableTitle, BorderLayout.NORTH);

        //Create top section of GUI that allows user to start or pause the CPU
        JLabel cpuState = new JLabel("System Uninitialized");

        JButton startButton = new JButton("Start System");
        startButton.addActionListener(e -> {
            //Need to press start button to initialize process table
            if(cpuState.getText().equals("System Uninitialized")){
                Object[] row;
                //Initialize table
                for(int i = 0; i < processList.size(); i++){
                    row = new Object[2];
                    row[0] = processList.get(i).getProcessID();
                    row[1] = processList.get(i).getServiceTime();
                    model.addRow(row);
                }
            }
            cpuState.setText("System Running");
            //enable CPU
        });

        JButton pauseButton = new JButton("Pause System");
        pauseButton.addActionListener(e -> {
            cpuState.setText("System Paused");
            //disable CPU
        });

        JPanel topSection = new JPanel(new FlowLayout());
        topSection.add(startButton);
        topSection.add(pauseButton);
        topSection.add(cpuState);

        //Add sections to GUI and initialize
        mainFrame.add(topSection, BorderLayout.NORTH);
        mainFrame.add(tableDisplay, BorderLayout.WEST);

        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.pack();
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(false);

        //Display start menu. When that is exited, the main GUI will be set to visible
        startMenu(mainFrame);

    }

    /**
     * Displays a start menu to the user asking for a name for the file containing the processes. Updates processList and sets the visibility of frame to true
     * @param frame The main display of the project, will be set to visible when processList is initialized
     */
    public static void startMenu(JFrame frame){
        JFrame start = new JFrame("Start", null);
        Dimension d = new Dimension();
        d.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        start.setSize(d);
        start.setLayout(new BorderLayout());

        final int FIELD_WIDTH = 10;
        JTextField inputText = new JTextField(FIELD_WIDTH);
        inputText.setText("File name");

        JButton enterButton = new JButton("Enter");
        enterButton.addActionListener(e -> {
            processReader(inputText.getText());
            frame.setVisible(true);
            start.dispose();
        });

        JLabel instructions = new JLabel("Enter the directory of the file containing the processes.");
        JPanel flowLayout = new JPanel();
        flowLayout.add(inputText);
        flowLayout.add(enterButton);

        start.add(flowLayout, BorderLayout.SOUTH);
        start.add(instructions, BorderLayout.NORTH);
        start.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        start.pack();
        start.setLocationRelativeTo(null);
        start.setVisible(true);
    }

    /**
     * Reads in the processes from the file indicated by fileName and updates processList with its contents
     * @param fileName The name of the file the user would like to open that contains a list of processes
     */
    public static void processReader(String fileName){
        FileReader infile = null;
        ArrayList<Process> processes = new ArrayList<>();

        try{
            assert fileName != null;
            infile = new FileReader(fileName);
        }
        catch(FileNotFoundException ex){
            System.err.println("File could not be located.");
            System.exit(1);
        }

        Scanner fileIn = new Scanner(infile);

        String processLine;
        String[] processInfo;
        Process process = new Process();

        while(fileIn.hasNextLine()){
            processLine = fileIn.nextLine();
            processInfo = processLine.split(", ");
            for (int i = 0; i < 4; i++){
                if (i == 0){
                    process.setArrivalT(Integer.parseInt(processInfo[0]));
                }
                else if (i == 1){
                    process.setProcessID(processInfo[1]);
                }
                else if (i == 2){
                    process.setServiceT(Integer.parseInt(processInfo[2]));
                }
                else{
                    process.setPriority(Integer.parseInt(processInfo[3]));
                }
            }
            processes.add(process);
            process = new Process();
        }

        processList = new ArrayList<>(processes);
    }
}
