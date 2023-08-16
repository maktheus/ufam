import java.util.Scanner;

class Main {
    public static void res(int numero1, int numero2) {
        // Chamando a função de soma e armazenando o resultado em uma variável
        int resultado = somarNumeros(numero1, numero2);
        
        // Exibindo o resultado da soma
        System.out.println("A soma de " + numero1 + " e " + numero2 + " é igual a: " + resultado);
    }

    public static int[] readDataFromKeyboard() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Digite um número: ");
        int numero1 = scanner.nextInt();
        System.out.println("Digite outro número: ");
        int numero2 = scanner.nextInt();
        scanner.close();

        return  new int[]{numero1, numero2};
    }
    public static void main(String[] args) {
        int[] nums = readDataFromKeyboard();
       res(nums[0], nums[1]);
    }
    
    // Função que realiza a soma de dois números inteiros e retorna o resultado
    public static int somarNumeros(int a, int b) {
        return a + b;
    }
}
// Crie uma classe em Java chamada HelloUfam que imprima a mensagem "Universidade Federal do Amazonas".

// Path: src/main.java
