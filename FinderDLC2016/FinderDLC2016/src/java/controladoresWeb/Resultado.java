package controladoresWeb;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author agust
 */
public class Resultado {
    
    String titulo = "";
    String detalle = "";
    String nombreArchivo;
    float ponderacion;
    
    public Resultado()
    {
        
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDetalle() {
        return detalle + "...";
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }    
        public float getPonderacion() {
        return ponderacion;
    }    
    public void obtenerDatos(String ruta, float ponderacion) throws FileNotFoundException, IOException
    {
            File f = new File(ruta);
            BufferedReader br = new BufferedReader(new FileReader(f));
            nombreArchivo = f.getName();
            this.ponderacion= ponderacion;
            
            String line = "";
            
            while(detalle.length() <= 600)
            {  
                line = br.readLine();
                if(line == null)return;
                if(!line.isEmpty())
                {                    
                    if(titulo == ""){
                        titulo = line;
                    }
                    else if(detalle == "")
                    {
                        detalle = line;
                    }
                    else if(detalle != "")
                    {
                        detalle = detalle + line;
                    }                   
                }
                else if(!"".equals(detalle))
                {
                    detalle = detalle + ". ";
                }
            }
//                System.out.println(nombreArchivo);
//                System.out.println(titulo);
//                System.out.println(detalle+"...");            
    }
    
}
