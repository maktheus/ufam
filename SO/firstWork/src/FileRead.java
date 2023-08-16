package src;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class FileRead {
    public FileRead() {
    }

    public ArrayList<Process> read(String fileName) {
        ArrayList<Process> processes = new ArrayList<Process>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String line = br.readLine();
            while (line != null) {
                String[] process = line.split(" ");
                processes.add(new Process(process[0], Integer.parseInt(process[1]), Integer.parseInt(process[2]), Integer.parseInt(process[3]), Integer.parseInt(process[4])));
                line = br.readLine();
            }
            br.close();
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return processes;
    }
}
