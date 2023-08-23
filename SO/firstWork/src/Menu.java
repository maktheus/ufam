package src;

import java.util.ArrayList;

public class Menu {
    // simple menu
    public int option = 0;
    public int option2 = 0;
    public int quantum;
    public int typeMenuPremp;
    public String FolderName;
    Scheduller scheduller = new Scheduller();
    ArrayList<Process> processes;

    public Menu(ArrayList<Process> processes, String FolderName) {
        this.processes = processes;
        this.quantum = 1;
        this.FolderName = FolderName;
        typeMenuPremp = this.processes.get(0).type;
    }

    public void runMenu() {

        if (typeMenuPremp == 1) {
            while (option != 5) {
                menuTypeTwoPreemptivo();
                option = readOption();
                switch (option) {
                    case 1:

                        scheduller.roundRobin(processes, quantum);
                        refreshProcess();
                        break;
                    case 2:

                        scheduller.shortestRemainingTimeFirst(processes);
                        refreshProcess();

                        break;
                    case 3:

                        scheduller.prioP(processes);
                        refreshProcess();

                        break;
                    case 4:

                        System.out.println("Enter the new quantum");
                        quantum = readOption();

                        break;
                    case 5:

                        System.out.println("Bye");
                        break;
                    default:
                        System.out.println("Invalid option");
                        break;
                }
            }
        }

        if (typeMenuPremp == 2) {
            while (option != 3) {
                menuTypeOneNPreempetivo();
                option = readOption();
                switch (option) {
                    case 1:

                        scheduller.fcfs(processes);
                        refreshProcess();

                        break;
                    case 2:

                        scheduller.shortestJobFirst(processes);
                        refreshProcess();

                        break;
                    case 3:

                        scheduller.prioC(processes);
                        refreshProcess();

                        break;
                    default:
                        System.out.println("Invalid option");
                        break;
                }
            }
        }

        if (typeMenuPremp == 3) {
            while (option != 8) {
                menu();
                option = readOption();
                switch (option) {
                    case 1:

                        scheduller.fcfs(processes);
                        refreshProcess();

                        break;
                    case 2:

                        scheduller.roundRobin(processes, quantum);
                        refreshProcess();

                        break;
                    case 3:

                        scheduller.shortestJobFirst(processes);
                        refreshProcess();

                        break;
                    case 4:

                        scheduller.shortestRemainingTimeFirst(processes);
                        refreshProcess();

                        break;
                    case 5:

                        scheduller.prioC(processes);
                        refreshProcess();

                        break;
                    case 6:

                        scheduller.prioP(processes);
                        refreshProcess();

                        break;
                    case 7:

                        System.out.println("Enter the new quantum");
                        quantum = readOption();
                        break;
                    case 8:

                        System.out.println("Bye");
                        break;
                    default:
                        System.out.println("Invalid option");
                        break;
                }
            }

        }
    }

    public void menuTypeOneNPreempetivo() {

        System.out.println("\n");
        System.out.println("Select an option");
        System.out.println("1. First Come First Served");
        // sjf
        System.out.println("2. Shortest Job First");
        // prioC
        System.out.println("3. PrioC");

    }

    public void menuTypeTwoPreemptivo() {

        System.out.println("\n");
        System.out.println("Select an option");
        // rr srtf
        System.out.println("1. Round Robin");
        System.out.println("2. Shortest Remaining Time First");
        // prioP
        System.out.println("3. PrioP");
        // change quantum option
        System.out.println(" 4. Change Quantum");
        // exit
        System.out.println("5. Exit");
    }

    public void menu() {

        System.out.println("Select an option");
        System.out.println("1. First Come First Served");
        System.out.println("2. Round Robin");
        System.out.println("3. Shortest Job First");
        // SRTF
        System.out.println("4. Shortest Remaining Time First");
        System.out.println("5. PrioC");
        System.out.println("6. PrioP");
        // cahnge quantum
        System.out.println("7. Change Quantum");
        System.out.println("8. Exit");
    }

    public void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public void menuFCFS() {
        System.out.println("1. First Come First Served");
        System.out.println("2. Back");
    }

    public void menuRR() {
        System.out.println("1. Round Robin");
        System.out.println("2. Back");
    }

    public void menuSJF() {
        System.out.println("1. Shortest Job First");
        System.out.println("2. Back");
    }

    public void menuSRTF() {
        System.out.println("1. Shortest Remaining Time First");
        System.out.println("2. Back");
    }

    public void menuPrioC() {
        System.out.println("1. PrioC");
        System.out.println("2. Back");
    }

    public void menuPrioP() {
        System.out.println("1. PrioP");
        System.out.println("2. Back");
    }

    public int readOption() {
        int option = 0;
        try {
            option = Integer.parseInt(System.console().readLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid option");
        }
        return option;
    }

    public void refreshProcess() {
        // clean arraylist
        FileRead fileRead = new FileRead();
        processes.clear();
        this.processes = fileRead.read("src/Example1.txt");
    }
}
