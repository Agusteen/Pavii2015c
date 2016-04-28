
package logicaIndexado;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.mozilla.universalchardet.UniversalDetector;

/**
 * Clase para detectar la codificacion de un archivo de texto.
 * @author 
 * Muñoz Campos, Agustín (62846)
 * Ramírez, Nicolás (63318)
 */
public class Codificacion {
   
    
    public static String getFileEncoding(File f) throws FileNotFoundException, IOException
  {
      byte[] buf = new byte[4096];
    //String fileName = "user_uploads/22975-8.txt";
    java.io.FileInputStream fis = new java.io.FileInputStream(f);

    // (1)
    UniversalDetector detector = new UniversalDetector(null);

    // (2)
    int nread;
    while ((nread = fis.read(buf)) > 0 && !detector.isDone()) {
      detector.handleData(buf, 0, nread);
    }
    // (3)
    detector.dataEnd();

    // (4)
    String encoding = detector.getDetectedCharset();
    detector.reset();
    if (encoding != null) {
    
      return encoding;
    } else {
    
      return "ISO-8859-1";
    }

    // (5)
    
  }
 
}

