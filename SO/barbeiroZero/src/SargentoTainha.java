import java.util.*;
import java.util.concurrent.Semaphore;

public class SargentoTainha implements Runnable {
    private Semaphore cortandoCabelo;
    private List<List<Integer>> filaOutside = new ArrayList<>();
    private int tempoDeDescanso = 0;
    private int ocioso = 0;


    
    public SargentoTainha( Semaphore cortandoCabelo,List<List<Integer>> fila, int tempoDeDescanso) {
        this.cortandoCabelo = cortandoCabelo;
        this.filaOutside = fila;
        this.tempoDeDescanso = tempoDeDescanso;
    }

    @Override
    public void run() {
        try {
            for (List<Integer> oficial : filaOutside) {  // Especifique o tipo de dados aqui
                // cortandoCabelo.acquire();
                // System.out.println(oficial.get(0) + " " + oficial.get(1));
                if(oficial.get(0) == 0){
                    Barbearia.addToFila(new Cliente(0, 0));
                    ocioso++;
                    if(ocioso == 3){
                        System.out.println("Não há mais clientes para serem atendidos pelo Sargento Tainha");
                        Barbearia.filaOutside = false;
                        break;
                    }
                    System.out.println("Sargento Tainha está ocioso");
                    Thread.sleep(tempoDeDescanso);
                    continue;
                }

                Cliente cliente = new Cliente(oficial.get(0), oficial.get(1));
                if(Barbearia.getSizeOfAllFilas() < 20){
                    ocioso = 0;
                    Barbearia.addToFila(cliente);   
                    System.out.println("Sargento Tainha adicionou um cliente a fila" + cliente);
                }else{
                    System.out.println("Sargento Tainha não pode adicionar um cliente a fila");
                }
                // cortandoCabelo.release();
                Thread.sleep(tempoDeDescanso);
            }
            System.out.println("Não há mais clientes para serem atendidos pelo Sargento Tainha todos foram adicionados a fila");
            Barbearia.filaOutside = false;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } 
    }
}
