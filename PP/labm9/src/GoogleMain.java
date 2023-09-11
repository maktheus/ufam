public class GoogleMain {
    //test ListaInvertida

    public static void main(String[] args) {
        ListaInvertida lista = new ListaInvertida();
        lista.insere("casa", "doc1");
        lista.insere("casa", "doc2");
        lista.insere("casa", "doc3");
        lista.insere("casa", "doc4");
        lista.insere("casa", "doc5");
        lista.insere("casa", "doc6");
        lista.insere("casa", "doc7");
        lista.insere("casa", "doc8");
        lista.insere("casa", "doc9");
        lista.insere("casa", "doc10");
        lista.insere("casa", "doc11");
        lista.insere("casa", "doc12");
        lista.insere("casa", "doc13");
        lista.insere("casa", "doc14");
        lista.insere("casa", "doc15");
        lista.insere("casa", "doc16");
        lista.insere("casa", "doc17");
        lista.insere("casa", "doc18");
        lista.insere("casa", "doc19");
        lista.insere("casa", "doc20");
        lista.insere("casa", "doc21");
        lista.insere("casa", "doc22");
        lista.insere("casa", "doc23");
        lista.insere("casa", "doc24");
        lista.insere("casa", "doc25");
        lista.insere("casa", "doc26");
        lista.insere("casa", "doc27");
        lista.insere("casa", "doc28");
        lista.insere("casa", "doc29");
        lista.insere("casa", "doc30");
        lista.insere("casa", "doc31");
        lista.insere("casa", "doc32");
        lista.insere("casa", "doc33");
        lista.insere("casa", "doc34");
        lista.insere("casa", "doc35");
        lista.insere("casa", "doc36");
        lista.insere("casa", "doc37");
        lista.insere("casa", "doc38");
        lista.insere("casa", "doc39");



        lista.insere("casa", "doc40");



        //busca por palavra que existe

        System.out.println(lista.busca("casa"));

        //busca por palavra que não existe

        System.out.println(lista.busca("casa1"));

        // String

        System.out.println(lista.toString());



    }
}


