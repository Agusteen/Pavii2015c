
package logicaIndexado;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import persistencia.BDManager;

/**
 *
 * @author Ariza, Gonzalo (62069)
 * Muñoz Campos, Agustín (62846)
 * Ramírez, Nicolás (63318)
 */
public class AllWords {

    //Tamaño aproximado del lenguaje castellano    
    private HashMap<String, Palabra> map;
    private BDManager bdmanager = BDManager.getBDManager();
    private Coordinador coordinador;

    public AllWords(Coordinador c) {
        map = new HashMap<>(300000);

        coordinador = c;
    }

    public Collection Values() {
        return map.values();
    }

    public String toString() {
        Collection coleccion = map.values();
        Iterator i = coleccion.iterator();
        StringBuilder sb = new StringBuilder();
        while (i.hasNext()) {

            sb.append(i.next().toString()).append("\n");
        }
        return sb.toString();
    }

    

    /*
     Agrega una palabra al mapa de palabras
     */
    public void agregar(String cadena, Documento doc) {
        Palabra aux = Palabra.generarPalabra(cadena);
        if (aux == null) return;
        
        Palabra palabra = map.get(aux.getCadena());
        //El mapa devuelve null si no encuentra la palabra
        if (palabra == null) {
            palabra = aux;
            
            
                //Agrega una nueva palabra y el Documento donde apareció
                palabra.registraraparicion();
                map.put(palabra.getCadena(), palabra);
                palabra.addDocument(doc);

            

        } else {
            //La palabra existe en el mapa, se cuenta una repeticion y se agrega el documento;
            palabra.registraraparicion();
            palabra.contarrepeticion();
            palabra.addDocument(doc);
        }

    }

    public int getContador() {
        return this.Values().size();
    }

    public void clear() {
        map = null;
        map = new HashMap<>(300000);

    }
    
    public void reiniciaraparicionyaparicion()
    {
        Collection coleccion = map.values();
        Iterator i = coleccion.iterator();
         while (i.hasNext()) {
             Palabra p= (Palabra)i.next();
             p.reiniciarrepeticion();
             p.reiniciaraparicion();
        }
    }

    public void persistpalabras() {
     bdmanager.persistpalabras(map.values().iterator());
    }

    public void verificarMaximaRepeticionynuevoDocumento() {
       Collection coleccion = map.values();
        Iterator i = coleccion.iterator();
         while (i.hasNext()) {
             Palabra p= (Palabra)i.next();
            
             if (!p.getaparicion())continue;
             
             p.verificarRepeticionMaxima();
            p.contarNuevoDocumento();
        }
       
        
    }

   

    
}
