package controladoresWeb;

import java.io.File;

/**
 *
 * @author agust
 */
public class LecturaDirectorio {

    File[] documentos;


    public LecturaDirectorio() {
        
    }

    public File[] getDocumentos() {
        return documentos;
    }    

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
