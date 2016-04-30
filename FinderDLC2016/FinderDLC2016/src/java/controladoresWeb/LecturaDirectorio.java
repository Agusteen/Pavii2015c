package controladoresWeb;

import java.io.File;

/**
 *
 * @author 
 * Muñoz Campos, Agustín (62846) 
 * Ramírez, Nicolás (63318)
 */
public class LecturaDirectorio {

    File[] documentos;


    public LecturaDirectorio() {
        
    }

    public File[] getDocumentos() {
        return documentos;
    }    

    /**
     * Recibe una ruta sobre la cual se van a leer los documentos y crea un arreglo
     * con los archivos que se encuentran dentro de ella.
     * @param ruta La direccion donde se deben leer los documentos.
     * @return 
     */
    public File[] leerDirectorio(String ruta) {
        File f = new File(ruta);        
        
        if (f.exists()) {
            documentos = f.listFiles();

            

        } else {
            System.out.println("Error");            
        }
        
        return documentos;
    }

}
