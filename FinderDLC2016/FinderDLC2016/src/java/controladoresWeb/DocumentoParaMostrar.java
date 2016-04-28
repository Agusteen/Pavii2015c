
package controladoresWeb;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author agust
 */
public class DocumentoParaMostrar {

    String titulo = "";
    String cuerpo = "";

    public DocumentoParaMostrar() {
    }

    public String getTitulo() {
        return titulo;
    }

    public String getCuerpo() {
        return cuerpo;
    }

    public void armarDocumento(String ruta) throws FileNotFoundException, IOException {
        String line;
        FileReader f = new FileReader(ruta);
        try (BufferedReader b = new BufferedReader(f)) {
            while ((line = b.readLine()) != null) {
                if (!line.isEmpty()) {
                    if (titulo == "") {
                        titulo = line;
                    } else {
                        cuerpo = cuerpo + "\n" + line;
                    }
                }
            }

        }
    }

}
