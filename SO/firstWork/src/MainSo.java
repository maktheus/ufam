package src;

import java.util.ArrayList;

public class MainSo {
    public static void main(String[] args) {
        FileRead fileRead = new FileRead();
        //get from args
        if(args.length == 0){
            System.out.println("Please, enter the file name");
            return;
        }
        if(args.length > 1){
            System.out.println("Please, enter only the file name");
            return;
        }

        if(!args[0].contains(".txt")){
            System.out.println("Please, enter a valid file name only .txt files");
            return;
        }

        ArrayList<Process> processes = fileRead.read( args[0] );
        Menu menu = new Menu(processes, args[0]);
        menu.runMenu();
    }
}