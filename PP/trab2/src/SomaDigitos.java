import java.util.Scanner;

public class SomaDigitos {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Lê o número do teclado
        int num = sc.nextInt();
        
        // Variável para guardar a soma dos dígitos
        int soma = 0;
        
        while (num != 0) {
            // Adiciona o último dígito do número à soma
            soma += num % 10;
            
            // Remove o último dígito do número
            num /= 10;
        }
        
        // Imprime a soma dos dígitos
        System.out.println(soma);
        
        sc.close();
    }
}
