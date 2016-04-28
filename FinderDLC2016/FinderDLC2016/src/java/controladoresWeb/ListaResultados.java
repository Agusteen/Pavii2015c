package controladoresWeb;


import java.util.ArrayList;

public class ListaResultados {
    ArrayList<Resultado> listaResultados;
     private static ListaResultados instancia;

    public ListaResultados() {
        listaResultados = new ArrayList<>();
    }
    
    public ArrayList<Resultado> getLista() {        
        return listaResultados;
    }
    
    public void addResultado(Resultado r)
    {
        listaResultados.add(r);        
    }
    
    public static ListaResultados getInstance() {
        if (instancia == null) instancia = new ListaResultados();        
        return instancia;
    }
    
    public void nuevaLista()
    {
        instancia.getLista().clear();
    }

}
