import java.util.Scanner;
public class CaixaEletronico {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int valorDaCompra = scanner.nextInt();
        if(valorDaCompra %2 !=0 || valorDaCompra < 0){
            System.out.println("Valor Invalido");
            return;
        }
        int notasDe50 =0, notasDe10 =0, notasDe2=0;
        while(valorDaCompra >=50){
            valorDaCompra -= 50;
            notasDe50 ++;
        }

        while( valorDaCompra >= 10){
            notasDe10 ++;
            valorDaCompra -= 10;
        }
        
        while( valorDaCompra >= 2){
            notasDe2 ++;
            valorDaCompra -=2;
        }
        //7 notas de R$50, 2 notas de R$10 e 4 notas de R$2
        System.out.println(notasDe50 + " notas de R$50, " + notasDe10 + " notas de R$10 e " + notasDe2 + " notas de R$2");
        scanner.close();
    }
}
