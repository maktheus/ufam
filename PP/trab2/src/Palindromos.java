import java.util.Scanner;

public class Palindromos {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String text = sc.nextLine();
        String formattedText = text.replace(" ", "").toUpperCase();

        String reversedText = new StringBuilder(formattedText).reverse().toString();

        System.out.print(formattedText + " ");
        if (formattedText.equals(reversedText)) {
            System.out.println(1);
        } else {
            System.out.println(0);
        }

        sc.close();
    }
}
