
package logicaConsulta;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import logicaIndexado.*;
import persistencia.BDManager;



/**
 *
 * @author nicor_000
 * 
 */
public class Buscador {
    /**cantidadDocumentos cantidad de documentos a buscar en la consulta (busqueda)**/
    int cantidadDocumentosAMostrar;
    /** cantidad de documentos N para la formula coseno**/
    int cantidaddocumentostotal;
    /**consulta **/
    String consulta;
    String[] cadenas;
    LinkedList<Palabra> listapalabras;
    private BDManager bdmanager;
    private Ranking ranking;
   
    
    
    
    
    
    public Buscador (String connectionString)
    {
        consulta = "";
        this.bdmanager =  BDManager.getBDManager();
        bdmanager.setConnectionString(connectionString);
        bdmanager.connect();
        listapalabras = new LinkedList<>();
        ranking = new Ranking();
    }

   
    
    
    public Iterator buscarConsulta( String consulta, int cantidadDocumentosAMostrar)
    {
        if (cantidadDocumentosAMostrar <= 0) return null;
        this.cantidadDocumentosAMostrar = cantidadDocumentosAMostrar;
        if ("".equals(consulta)) return null;
        this.consulta = consulta;
        this.cantidaddocumentostotal = bdmanager.buscarCantidadDocumentostotal();
        String [] cadenasdeconsulta = consulta.split(" ");
        for (String cadena : cadenasdeconsulta) {
            Palabra p= bdmanager.buscarPalabra(cadena);
            if (p != null)listapalabras.add(p);
        }
        
        
        Collections.sort(listapalabras);
        
//Recorrer lista de palabras de menora a mayor nr;
        Iterator i = listapalabras.iterator();
        
        while (i.hasNext())
        {
            Palabra p =(Palabra) i.next();
            Iterator iteradorposteos = bdmanager.buscarPosteos(p.getCadena(), cantidadDocumentosAMostrar);//traer ordenado
               // select (info) from POSTEO where palabra = "parametro" order by termfrecuency;
               // guardar ordenado en una lista y devolver el iterador:
            for (int documentostraidos = 0;documentostraidos < cantidadDocumentosAMostrar ; documentostraidos ++ )
            {
               if(iteradorposteos.hasNext() )
                {
                    Posteo posteo = (Posteo) iteradorposteos.next();
                    String link= posteo.getLink();
                    int frecuenciadeterminoPorDocumento = posteo.getFrecuenciadeterminoPorDocumento();
                            int docstotales = cantidaddocumentostotal;
                            int docsdiferentesportermino = p.getDocumentosDiferentes();
                    DocumentoConsulta documentoconsulta= new DocumentoConsulta(link,frecuenciadeterminoPorDocumento,docstotales,docsdiferentesportermino);
                    
                    ranking.addDoc(documentoconsulta);
                }
            }
        
        

            
        }
        
        return ranking.getIterator();
     
        
    }
    
    

    
    
   
    
    
    
    /*
      Nulo: Identificamos cuantos documentos vamos a mostrar.
     ¨Primero: Debemos buscar por cada palabra de la Consulta su idf, en practica nr ( buscar terminos con menor nr)
      Segundo: Traer los Primeros R documentos. SI no se llegan a lor R, se busca el proximo con menor nr.
      Tercer: 
      
    */

    
}
