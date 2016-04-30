package controladoresWeb;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author 
 * Muñoz Campos, Agustín (62846) 
 * Ramírez, Nicolás (63318)
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
    
    /**
     * Se setean los atributos de la clase a partir de una Ruta a un documento
     * Se leen las primeras 600 palabras del documento para tener una previsualizacion del resultado de la busqueda.
     * Se setea la ponderacion tambien para poder mostrarlos en los resultados de la busqueda.
     * @param ruta Donde se encuentra el documento que es un resultado de la consulta.
     * @param ponderacion Valor de ponderacion que tiene el documento frente a una consulta.
     * @throws FileNotFoundException
     * @throws IOException 
     */
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
    }
    
}
