
package logicaConsulta;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import logicaIndexado.*;
import persistencia.BDManager;


/**
 *
 * @author 
 * Muñoz Campos, Agustín (62846) 
 * Ramírez, Nicolás (63318)
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

   
    
    /**
     * Recibe una consulta, la separa por palabras y crea una lista de cadenas de consulta,
     * esta cadena esta conformada por las palabras de la consulta que se encuentran en la base de datos
     * y de las cuales se van a poder traer resultados posibles, esta lista no tiene palabras (cadenas) repetidas.
     * La lista de palabras se ordenan en funcion del valor de la palabra para el dominio, luego esta lista es 
     * recorrida junto con los las listas de posteo correspondientes para cada palabra que es recuperada de base de datos.
     * Se recorren los posteos y por cada posteo encontrado se crean DocumentosConsulta que poseerá la informacion
     * de los resultados de la consulta. La lista de DocumentosConsulta que se crea tendra un tamaño fijado como parametro o menor 
     * en caso de que los posteos encontrados de base de datos sea menor.
     * Se devuelve luego un iterador de la la clase Ranking que es la clase que maneja los DocumentosConsulta.
     * @param consulta Una cadena de palabras correspondiente a la consulta a realizarse.
     * @param cantidadDocumentosAMostrar La canidad de documentos a mostrar como resultado de la consulta.
     * @return 
     */
    public Iterator buscarConsulta( String consulta, int cantidadDocumentosAMostrar)
    {
        if (cantidadDocumentosAMostrar <= 0) return null;
        this.cantidadDocumentosAMostrar = cantidadDocumentosAMostrar;
        if ("".equals(consulta)) return null;
        this.consulta = consulta;
        this.cantidaddocumentostotal = bdmanager.buscarCantidadDocumentostotal();
        String [] cadenasdeconsulta = consulta.split(" ");
        boolean yaenlista;
        for (String cadena : cadenasdeconsulta) {
            yaenlista = false;
            Palabra p= bdmanager.buscarPalabra(cadena);
            Iterator i= listapalabras.iterator();
            while (i.hasNext())
            {
                Palabra auxp=(Palabra) i.next();
                if (auxp.equals(p))
                {yaenlista = true;
                 break;
                }
            }
            if (p != null && yaenlista == false)listapalabras.add(p);
        }
        
        
        Collections.sort(listapalabras);
        
//Recorrer lista de palabras de menora a mayor nr;
        Iterator i = listapalabras.iterator();
        
        while (i.hasNext())
        {
            Palabra p =(Palabra) i.next();
            Iterator iteradorposteos = bdmanager.buscarPosteos(p.getCadena(), cantidadDocumentosAMostrar);//traer ordenado
            
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
    
    

    
    
   
    
    
    
   

    
}
