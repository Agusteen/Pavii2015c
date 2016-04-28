
package logicaConsulta;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;

/**
 *
 * @author nicor_000
 */
public class Ranking {
    LinkedList<DocumentoConsulta> ranking;
    
    
    public Ranking() {
    ranking = new LinkedList<DocumentoConsulta>();
    
}
    
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
     * Método que devuelve el iterador del ranking luego de haberlo ordenado de menor a mayor.
     * @return El iterador descendente del ranking para poder leer los documentos desde el mas importante
     * al menos importante.
     */
    public Iterator getIterator()
    {
        Collections.sort(ranking);
        Iterator desciterator = ranking.descendingIterator();
        return desciterator;
    }
}
