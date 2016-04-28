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
    private LinkedList<Documento> docsbd;

    public AllDocuments(Coordinador c) {
        lista = new LinkedList<>();
        coordinador = c;

    }

    public int count() {
        return lista.size();
    }

    /*
     Agrega documentos en la lista de documentos solo si los documentos 
     no se encuentran la base de datos o en la lista de documentos de la propia clase
     Envia mensajes de la situacion de cada documento en cada caso
      
     */
    public void addDocument(String link) {

        if (link == null) {
            return;
        }
       
        Documento d = new Documento(link);

        
        if (!this.contieneDoc(d)) {

            lista.add(d);
        }

    }

    /*
     Valida si un documento se encuentra en la lista de documentos de la clase
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

    public void clear() {
        lista = null;
        lista = new LinkedList<>();
        docsbd = null;
        docsbd = new LinkedList<>();
    }

    public void recuperarDocs() {
        docsbd = bdmanager.materializeDocs();
    }

    void persistdocumento(Documento doc) {
        bdmanager.persistDoc(doc);    }
}
