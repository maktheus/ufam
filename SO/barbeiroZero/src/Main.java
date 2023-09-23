// import threds

import java.util.concurrent.Semaphore;

public class Main{
    //main
    private static int currentTime = 0;
    public static void main(String[] args){
        int maxTime = 1000;
        Semaphore adicionarElemento = new Semaphore(1);
        Semaphore cortandoCabelo = new Semaphore(1);
        Semaphore lerFila = new Semaphore(0);

        while(currentTime < maxTime){
            currentTime++;
            if(currentTime % 5 == 0){
                SargentoTainha.runSargentoTainha(adicionarElemento, lerFila);
            }
            if(!Barbearia.isEmpty()){
                RecrutaZero.runRecrutaZero(cortandoCabelo);
            }
        }
    }

    public static int getCurrentTime(){
        return currentTime;
    }

}