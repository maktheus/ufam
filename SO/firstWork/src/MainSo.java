package src;

import java.util.ArrayList;

public class MainSo {
    public static void main(String[] args) {
        FileRead fileRead = new FileRead();
        ArrayList<Process> processes = fileRead.read("src/Example1.txt");

        Menu menu = new Menu(processes);

        menu.runMenu();
    }
}