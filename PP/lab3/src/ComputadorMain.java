public class ComputadorMain {
    
    public static void main(String[] args) {
        Processador processador = new Processador("AMD", "Ryzen 5", 3.2, 6);
        Memoria memoria = new Memoria("Corsair", "DDR4", 8, 3.2, 2);
        Disco disco = new Disco("Seagate", "HDD", 4000, 7200);
        Computador computador = new Computador("Dell", processador, memoria, disco);

        System.out.println(computador.getDescricao());
    }

}
