import java.util.Scanner;

public class Naja {
    public static void main(String[] args) {
        // Cria um objeto Scanner para ler a entrada do usuário
        Scanner scanner = new Scanner(System.in);

      
        int n = scanner.nextInt();

        // Fecha o objeto Scanner após a leitura
        scanner.close();

        // print a square
        for (int i = 0; i < n; i++) {
            for(int j =0 ; j < n; j++){
                // System.out.print("*");
                if( == 0){

                    System.out.println("*");
                }
                else{
                    System.out.println(" ");
                }
            }
            System.out.println();
        }
    }
}
