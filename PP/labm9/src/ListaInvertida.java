import java.util.Hashtable;
import java.util.LinkedList;


public class ListaInvertida{
    private Hashtable<String, LinkedList<String>> tabela;

    public ListaInvertida(){
        this.tabela = new Hashtable<String, LinkedList<String>>();
    }

    public boolean insere(String palavra, String documento){
        if(tabela.containsKey(palavra)){
            LinkedList<String> lista = tabela.get(palavra);
            if(!lista.contains(documento)){
                lista.add(documento);
                tabela.put(palavra, lista);
                return true;
            }
            return false;
        }
        else{
            LinkedList<String> lista = new LinkedList<String>();
            lista.add(documento);
            tabela.put(palavra, lista);
            return true;
        }
    }


    public LinkedList<String> busca(String palavra){
        if(tabela.containsKey(palavra)){
            return tabela.get(palavra);
        }
        else{
            return null;
        }
    }



    public String  toString(){
        return tabela.toString();
    }

}