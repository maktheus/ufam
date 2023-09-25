import java.util.*;

public class TenenteEscovinha implements Runnable {
   @Override
    public void run() {
        try {
           System.out.println("Tenente Escovinha está ocioso");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
