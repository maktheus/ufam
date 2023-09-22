package urnaeletronica.BackEnd.Controllers;

public class LoginController {

    public static boolean login(String password) {
        // Corrija a comparação de strings usando o método equals()
        if ("1234".equals(password)) {

            return true;
        } else {
            System.out.println("Senha incorreta");
            return false;
        }
    }
}
