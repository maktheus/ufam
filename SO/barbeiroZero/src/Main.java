import java.io.*;
import java.util.*;
import java.util.concurrent.Semaphore;

public class Main {
    public static int currentTime = 0;
    public static  Semaphore cortandoCabelo = new Semaphore(1);
   


    public static void main(String[] args) throws IOException {
        // get time off waiting from args
        
        int timeOffWaiting = Integer.parseInt(args[0]);

        // get file from args
        String file = args[1];

        // run ler arquivo
        List<List<Integer>> fila = lerArquivo(file);

        //criar threads
        Thread recrutaZero = new Thread(new RecrutaZero(cortandoCabelo));
        Thread sargentoTainha = new Thread(new SargentoTainha(cortandoCabelo,fila, timeOffWaiting));
        // Thread tenenteEscovinha = new Thread(new TenenteEscovinha(fila, timeOffWaiting));

        // run threads
        
        recrutaZero.start();
        sargentoTainha.start();
        // tenenteEscovinha.start();

        try {
            recrutaZero.join();
            sargentoTainha.join();
            // tenenteEscovinha.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public static void addCurrentTime(int time) {
        currentTime += time;
    }

    private static List<List<Integer>> lerArquivo(String arquivo) throws IOException {
        List<List<Integer>> entrada = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] partes = linha.split(" ");
                List<Integer> numeros = new ArrayList<>();
                for (String parte : partes) {
                    try {
                        numeros.add(Integer.parseInt(parte));
                    } catch (NumberFormatException e) {
                        System.err.println("Não foi possível converter a string para inteiro: " + parte);
                    }
                }
                entrada.add(numeros);
            }
        }
        return entrada;
    }
}
