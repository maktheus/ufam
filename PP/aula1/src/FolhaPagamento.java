import java.util.Scanner;

public class FolhaPagamento {
    public static void main(String[] args) {
        // Cria um objeto Scanner para ler a entrada do usuário
        Scanner scanner = new Scanner(System.in);

        // Lê o valor da hora e a quantidade de horas trabalhadas de Fernando
        //System.out.print("Digite o valor da hora trabalhada: ");
        double valorHora = scanner.nextDouble();

        //System.out.print("Digite a quantidade de horas trabalhadas: ");
        int quantidadeHoras = scanner.nextInt();

        // Fecha o objeto Scanner após a leitura
        scanner.close();

        // Cálculo do salário bruto
        double salarioBruto = valorHora * quantidadeHoras;

        // Cálculo do Imposto de Renda (11%)
        double impostoRenda = salarioBruto * 0.11;

        // Cálculo do INSS (8%)
        double inss = salarioBruto * 0.08;

        // Cálculo do total de descontos
        double totalDescontos = impostoRenda + inss;

        // Cálculo do salário líquido
        double salarioLiquido = salarioBruto - totalDescontos;

        // Imprime os resultados formatados
        System.out.printf("Salario bruto: R$%.2f\n", salarioBruto);
        System.out.printf("IR: R$%.2f\n", impostoRenda);
        System.out.printf("INSS: R$%.2f\n", inss);
        System.out.printf("Total de descontos: R$%.2f\n", totalDescontos);
        System.out.printf("Salario liquido: R$%.2f\n", salarioLiquido);
    }
}
