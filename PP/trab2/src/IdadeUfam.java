import java.util.Scanner;
public class IdadeUfam {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int idade = scanner.nextInt();
        idade =  idade - 1909;
        System.out.println("A UFAM tem " + idade + " anos de fundacao");

        //close the scanner
        scanner.close();
        
    }
}
