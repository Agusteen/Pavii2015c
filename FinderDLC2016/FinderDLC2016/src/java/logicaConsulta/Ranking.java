
package logicaConsulta;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;

/**
 *
 * @author 
 * Muñoz Campos, Agustín (62846) 
 * Ramírez, Nicolás (63318)
 */
public class Ranking {
    LinkedList<DocumentoConsulta> ranking;
    
    
    public Ranking() {
    ranking = new LinkedList<DocumentoConsulta>();
    
}
    
    /**
     * Agrega un DocumentoConsulta a la lista de ranking en el caso de que el que no se encuentre ya en la lista.
     * Si se encontrara en la lista suma las ponderaciones de esos DcocumentoConsulta.
     * @param doc El DocumentoConsulta que se desea agregar a la lista.
     */
    public void addDoc( DocumentoConsulta doc)
    {
        Iterator i = ranking.iterator();
        while (i.hasNext())
        {
            DocumentoConsulta dociterador = (DocumentoConsulta)i.next();
            if (dociterador.equals(doc)){
                dociterador.addPonderacion(doc);
            return;}
        }
        
        ranking.add(doc);
        
    }
    
    /**
     * Método que devuelve el iterador descendente del ranking luego de haberlo ordenado de menor a mayor.
     * @return El iterador descendente del ranking para poder leer los documentos en funcion de lo especificado
     * en el dominio (desde el mas importante
     * al menos importante).
     */
    public Iterator getIterator()
    {
        Collections.sort(ranking);
        Iterator desciterator = ranking.descendingIterator();
        return desciterator;
    }
}
