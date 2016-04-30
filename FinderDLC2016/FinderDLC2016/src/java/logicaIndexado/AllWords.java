
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

    /**
     * Devuelve una coleccion a partir de la HashMap de la clase que se usa para almacenar las palabras.
     * @return La coleccion que se obtiene del HashMap de palabras de la clase.
     */
    public Collection Values() {
        return map.values();
    }
/**
 *  Retorna una cadena de los elementos del HashMap de la clase.
 * @return Cadena correspondiente a todos las palabras del Hashmap de la clase.
 */
    @Override
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
    /**
     * Agrega una palabra con su documento al mapa de palabras si la palabra no se encuentra en el HashMap de la clase
     * la crea, si se encuentra, se registra una nueva aparicion, y se agrega el documento recibido como parametro
     * a la palabra creada.
     * @param cadena Cadena que se desea buscar en el HashMap correspondiente a una palabra que se desea agregar.
     * @param doc Documento poseedor de la palabra que se desea agregar al hashMap.
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

    /**
     * Devuelve el tamaño del HashMap.
     * @return entero con el tamaño del HashMap de la clase.
     */
    public int getContador() {
        return this.Values().size();
    }
    /**
     * Limpia los atributos de la clase.
     */
    public void clear() {
        map = null;
        map = new HashMap<>(300000);

    }
    /**
     * Cada palabra de la collecion posee un atributo booleano para identificarla en caso de una nueva aparicion
     * en un documento, de forma que al momento de realizar la persistencia no se persistan palabras que no aparecen en los
     * nuevos documentos. Este metodo recorre el HashMap de palabras y reinicia el atributo booleando al momento de terminar
     * de persistir un documento y sus palabras, para que todas las palabras se encuentren en un estado inicial de no aparicion
     * al momento de leer un nuevo documento.
     * Tambien se reinician las apariciones de las palabras en el documento, para tener un estado inicial correcto al momento de
     * leer otro documento.
     */
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

    /**
     * Invoca a un metodo de BDManager para realizar la persistencias de las palabras que se encuentra en el
     * HashMap.
     */
    public void persistpalabras() {
     bdmanager.persistpalabras(map.values().iterator());
    }

    /**
     * Recorre el HashMap de palabras para verificar la maximaRepeticion de palabras por documento 
     * y cuenta la aparicion de un nuevo documento en cada una de las palabras.
     */
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
