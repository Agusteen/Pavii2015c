
package controladoresWeb;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author 
 * Muñoz Campos, Agustín (62846) 
 * Ramírez, Nicolás (63318)
 */
public class DocumentoParaMostrar {

    String titulo = "";
    String cuerpo = "";
    String ruta = "";

    public DocumentoParaMostrar() {
    }

    public String getTitulo() {
        return titulo;
    }

    public String getCuerpo() {
        return cuerpo;
    }
    
    public String getRuta() {
        return ruta;
    }

    /**
     * Crea el documento que se mostrara luego de hacer click en el resultado de una consulta
     * @param ruta La ruta de ubicacion del documento.
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public void armarDocumento(String ruta) throws FileNotFoundException, IOException {
        String line;
        this.ruta = ruta;
        FileReader f = new FileReader(ruta);
        int count = 0;
        try (BufferedReader b = new BufferedReader(f)) {
            while ((line = b.readLine()) != null && count < 500) {
                if (!line.isEmpty()) {
                    if (titulo == "") {
                        titulo = line;
                    } else {
                        cuerpo = cuerpo + "\n" + line;
                    }                    
                }
                count++;
            }

        }
    }

}
