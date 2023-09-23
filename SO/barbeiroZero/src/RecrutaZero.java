import java.util.concurrent.*;

import java.util.*;


public class RecrutaZero implements Runnable {
    private Semaphore cortandoCabelo;
    private int firstCurrentTime=0;
    private int tipoOficial=-1;
    private Random random = new Random();
    public RecrutaZero(Semaphore cortandoCabelo){
        this.cortandoCabelo = cortandoCabelo;
    }

    @Override
    public void run(){
        try {

                //Random number between 1 and 3
                if(Main.getCurrentTime() > firstCurrentTime + random.nextInt(2)+1 && firstCurrentTime != 0 && tipoOficial == 0){
                    cortandoCabelo.release();
                }

                if(Main.getCurrentTime() > firstCurrentTime + random.nextInt(3)+2 && firstCurrentTime != 0 && tipoOficial == 1){
                    cortandoCabelo.release();
                }
                if(Main.getCurrentTime() > firstCurrentTime + random.nextInt(5)+4 && firstCurrentTime != 0 && tipoOficial == 2){
                    cortandoCabelo.release();
                }


                cortandoCabelo.acquire();
                firstCurrentTime = Main.getCurrentTime();
                System.out.println("Recruta Zero está cortando o cabelo: "+ Main.getCurrentTime());    
                tipoOficial = Barbearia.removeMaiorPrioridade();
                //release after current time is over 3 plus the first current time
                      
            
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void runRecrutaZero(Semaphore cortandoCabelo){
        Thread recrutaZero = new Thread(new RecrutaZero(cortandoCabelo));
        recrutaZero.start();
    }

}
