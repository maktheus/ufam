package urnaeletronica.BackEnd.Controllers;

public class LoginController {

    public static boolean login(String password) {
        // Corrija a comparação de strings usando o método equals()
        if ("1234".equals(password)) {
            System.out.println("Senha correta");
            return true;
        } else {
            System.out.println("Senha incorreta");
            return false;
        }
    }
}
