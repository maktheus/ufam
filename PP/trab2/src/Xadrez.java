import java.util.Scanner;

public class Xadrez {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int numero = scanner.nextInt();
        for(int i = 0; i < numero; i++){
            if(i%2 ==0){

                for(int j = 0; j < numero; j++){
                   System.out.print("* ");
                }
            }else{
                System.out.print(" ");
                for(int j = 0; j < numero; j++){
                   System.out.print("* ");
                }
            }

            System.out.println();
        }
        //close the scanner
        scanner.close();
        
    }    
}
