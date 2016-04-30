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
 * @author Muñoz Campos, Agustín (62846) Ramírez, Nicolás (63318)
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

    /**
     * Carga los documentos que van a ser procesados
     *
     * @param link Ruta del documento que se va a agregar al conjunto de
     * documentos.
     * @return true si el documento fue guardado en el conjunto de documentos,
     * false si el documento no pudo ser guardado por cualquier motivo.
     */
    public boolean loadDocs(String link) {
        if (!link.isEmpty()) {
            if (conjuntodocumentos.addDocument(link)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Busca la codificacion del archivo para poder ser procesado.
     *
     * @param archivo Archivo que se quiere leer para procesar.
     * @return Cadena con la codificacion del archivo que se quiere leer.
     */
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

    /**
     * Procesa todos los documentos del conjunto de documentos palabra por
     * palabra segun un patron preestablecido, por cada documento se verifica la
     * maxima repeticion de cada palabra y se registra la lectura de un nuevo
     * documento, persiste el documento seguido de las palabras( sus listas de
     * posteo tambien) que aparecen en cada documento reinicia la aparicion de
     * cada una de las palabras que se encuentra en memoria antes de comenzar la
     * lectura de otro documento
     */
    public void processdocs() {

        Iterator<Documento> k = conjuntodocumentos.getIteratorDocuments();
        /* Para saber si el indexado esta haciendo algo */
        int docsleidos = 0;
     //   int docstotal = conjuntodocumentos.count();

       // long time_start, time_end;
        while (k.hasNext()) {
            docsleidos++;
            //    time_start= System.currentTimeMillis();

            Documento doc = new Documento(k.next().getLink());// crea documento obtenido del iterador de docs

            File archivo = new File(doc.getLink());

            try (Scanner sc = new Scanner(archivo, this.getCodificacion(archivo))) {
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

   //     time_end = System.currentTimeMillis();
            //        System.out.println("Tiempo de procesamiento del doc:"+ docsleidos+ ","+ (time_end-time_start)/1000 +"seg");  
            if (docsleidos % 50 == 0) {
                System.out.println(docsleidos);
            }

        }

//        conjuntodocumentos.persistdocumentos();
//        mapapalabras.persistpalabras();
//        mapapalabras.clear();
//        conjuntodocumentos.clear();
    }

}
