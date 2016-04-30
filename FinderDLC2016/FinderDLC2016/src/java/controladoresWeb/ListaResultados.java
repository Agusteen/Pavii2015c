package controladoresWeb;

import java.util.ArrayList;
/**
 *
 * @author 
 * Muñoz Campos, Agustín (62846) 
 * Ramírez, Nicolás (63318)
 */
public class ListaResultados {
    ArrayList<Resultado> listaResultados;
     private static ListaResultados instancia;

    public ListaResultados() {
        listaResultados = new ArrayList<>();
    }
    
    public ArrayList<Resultado> getLista() {        
        return listaResultados;
    }
    /**
     * Agrega un resultado a la lista de resultados de la clase
     * @param r Recibe un resultado.
     */
    public void addResultado(Resultado r)
    {
        listaResultados.add(r);        
    }
    /**
     * Implementación de singleton, retorna la instancia de la clase si existe, en caso de que no existiera crea la instancia
     * y la devuelve. 
     * @return la unica instancia de la clase ListaResultados
     */
    public static ListaResultados getInstance() {
        if (instancia == null) instancia = new ListaResultados();        
        return instancia;
    }
    
    /**
     * Vacía la lista de resultados;
     */
    public void nuevaLista()
    {
        instancia.getLista().clear();
    }

}
