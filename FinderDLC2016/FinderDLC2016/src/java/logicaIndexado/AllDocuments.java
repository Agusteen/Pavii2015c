/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logicaIndexado;

import java.util.Iterator;
import java.util.LinkedList;
import persistencia.BDManager;

/**
 *
 * @author Ariza, Gonzalo (62069)
 * Muñoz Campos, Agustín (62846)
 * Ramírez, Nicolás (63318)
 */
public class AllDocuments {

    private LinkedList<Documento> lista;
    private BDManager bdmanager = BDManager.getBDManager();
    private Coordinador coordinador;
    private LinkedList<Documento> docsBD;

    public AllDocuments(Coordinador c) {
        lista = new LinkedList<>();
        coordinador = c;
        this.recuperarDocs();

    }

    /**
     * Devuelve el tamaño de la lista de documentos.
     * @return tamaño de la lista de documentos.
     */
    public int count() {
        return lista.size();
    }
    
/**
 * Agrega documentos en la lista de documentos solo si los documentos 
 * no se encuentran la base de datos o en la lista de documentos de la propia clase
 * @param link La ruta del documento que desea agregarse a la lista de documentos
 * @return true si el documento pudo guardarse en la lista, false si el documento
 * no pudo guardarse en la lista por cualquier motivo.
 */
    public boolean addDocument(String link) {

        if (link == null) {
            return false;
        }
       
        Documento d = new Documento(link);
        
        
        Iterator it= docsBD.iterator();
           while (it.hasNext())
           {
               Documento dbd = (Documento) it.next();
               
               //DEBO COMPARAR NOMBRES DE DCUMENTOS O RUTAS RELATIVAS CREO PREFERIBLEMENTE
               if (dbd.equalsRelativa(d))return false;
               
           }
            
          

      
        
        if (!this.contieneDoc(d)) {

            lista.add(d);
            return true;
        }
        
        return true;

    }

   
    /**
     * Valida si un documento se encuentra en la lista de documentos de la clase.
     * @param doc documento que se desea buscar en la lista de documentos de la clase.
     * @return true si el documento se encuentra en la lista de documentos de la clase,
     * false si el documento no se encuentra en la lista de documentos de la clase.
     */
    private boolean contieneDoc(Documento doc) {
        if (lista.isEmpty()) {
            return false;
        }
        //verifica si el documento existe en toda la lista.
        for (Documento d : lista) {
            if (d.equals(doc)) {
                return true;
            }
        }
        return false;
    }

    public Iterator getIteratorDocuments() {
        return lista.iterator();
    }

    public void persistdocumentos() {
        bdmanager.persistDoc(lista.iterator());
    }

    /**
     * Limpia los atributos de la clase.
     */
    public void clear() {
        lista = null;
        lista = new LinkedList<>();
        docsBD = null;
        docsBD = new LinkedList<>();
    }
/**
 *  Recupera de la base de datos una lista de documentos y lo almacena en la variable docsDB
 */
    public void recuperarDocs() {
        docsBD = bdmanager.materializeDocs();
       
    }
/**
 * Persiste un documento mandado como parametro en la base de datos.
 * @param doc Documento que se desea persistir en la base de datos.
 */
    void persistdocumento(Documento doc) {
        bdmanager.persistDoc(doc);    }
}
