/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logicaIndexado;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.regex.Pattern;
import javax.swing.table.DefaultTableModel;
import persistencia.BDManager;


/**
 *
 * @author Ariza, Gonzalo (62069)
 * Muñoz Campos, Agustín (62846)
 * Ramírez, Nicolás (63318)
 */
public class Coordinador {

    private BDManager bdmanager;// = BDManager.getBDManager();
    
    private AllWords mapapalabras;
    private AllDocuments conjuntodocumentos;
   

    public Coordinador(String connectionString) {
        bdmanager = BDManager.getBDManager();
        bdmanager.setConnectionString(connectionString);
        bdmanager.connect();
        bdmanager.setConnectionString(connectionString);
        bdmanager.connect();
        mapapalabras = new AllWords(this);
        conjuntodocumentos = new AllDocuments(this);
        
      
    }

    /*Quien cargue los documentos podria llamar a este metodo dandole los links*/
    public void loadDocs(String link) {
        if (!link.isEmpty()) {
            conjuntodocumentos.addDocument(link);
        }

    }

    public String getCodificacion(File archivo) {
        String codificacion = "";
        try {
            codificacion = Codificacion.getFileEncoding(archivo);

        } catch (FileNotFoundException e) {
            System.out.println("No existe el archivo de entrada...");
        } catch (IOException e) {
            System.out.println("Error IO Exception...");

        }
        return codificacion;
    }

    public void processdocs() {

        Iterator<Documento> k = conjuntodocumentos.getIteratorDocuments();
        /* Para saber si el indexado esta haciendo algo */
        int docsleidos = 0;
        int docstotal = conjuntodocumentos.count();

      
        while (k.hasNext()) {

            docsleidos++;
            System.out.println(docsleidos);

            Documento doc = new Documento(k.next().getLink());// crea documento obtenido del iterador de docs
           
                
                File archivo = new File(doc.getLink());
            
            //System.out.println(doc.getLink());
            //System.out.println(archivo.getPath());

            try (Scanner sc = new Scanner(archivo, this.getCodificacion(archivo));) {
                /*
                El patron permite separar las cadenas por cualquier caracter que no este comprendido entre
                a-z;0-9;A-Z; ni sea á;é;í;ó;ú;Á;É;Í;Ó;Ú;ñ;Ñ;ü;Ü; Tampoco guiones (-) para reconocer palabras
                en ingles como re-use.
                Cabe aclarar que con este patron la cadena www.google.com; es separada y almacenada como 
                3 palabras distintas www ; gooogle ; com
                */
                /*
                03/04/2016 agregado simbolo " ' " para no tomarlo como un delimitador de palabras
                */
             
                Pattern patron = Pattern.compile("-{2,}|[^a-z0-9A-ZáéíóúÁÉÍÓÚñÑüÜ'-]");
                sc.useDelimiter(patron);

                while (sc.hasNext()) {
                    mapapalabras.agregar(sc.next(), doc);

                }

            } catch (FileNotFoundException ex) {
                System.out.println("No existe el archivo de entrada...");
                System.out.println("Error " + ex.getMessage());
            }
mapapalabras.verificarMaximaRepeticionynuevoDocumento();
      
       /*Se guarda el documento en tabla DOCUMENTO */
        conjuntodocumentos.persistdocumento(doc);
        mapapalabras.persistpalabras();//Se guardan las palabras y su POSTEO CORRESPONDIENTE;
      
        mapapalabras.reiniciaraparicionyaparicion();
        
        
          
        }
       
//        conjuntodocumentos.persistdocumentos();
//        mapapalabras.persistpalabras();
//        mapapalabras.clear();
//        conjuntodocumentos.clear();

        
    }

    public void registrarInicioPersist() {
       
    }

    public void registrarAvancePersist() {
        
    }

    void registrarFinPersist() {
        
    }

  

//    public String buscarPalabra(String cadena) {
//        return bdmanager.materializeAparicion(cadena).toString();
//    }
    /*
     Calcula el progreso de lectura de documentos
     */
   

    

    public void recuperarDocs() {

        conjuntodocumentos.recuperarDocs();
    }
    
    

}
