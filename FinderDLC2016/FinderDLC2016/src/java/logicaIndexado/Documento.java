/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package logicaIndexado;



/**
 *
 * @author Muñoz Campos, Agustín (62846)
 * Ramírez, Nicolás (63318)
 */
public class Documento {
    
    private String link;
    
    
    public Documento()
    {        
    }
    
    public Documento (String l)
    {
        
      if(!l.isEmpty())link= l;
    }
    
  
    
    public String getLink()
    {
        return link;
    }
    
    @Override
       public String toString() {
        return link;
    }
       /**
        * Devuelve la ruta relativa de un documento para poder persistir en base de datos independientemente de la 
        * maquina donde se esta corriendo el programa.
        * @return cadena con la ruta relativa del documento.
        */
       public String getPathRelativa()
       {
           
           String[] cadenaaux = link.split("..\\..\\..");
           String relativa ="..\\.."+  cadenaaux[1];

           return relativa;
       }
       
       
    /**
     * Compara el link de la instancia con el link del documento mandado como parametro.
     * @param d Documento con el cual se quiere comparar.
     * @return true si los links son iguales, false si los links son diferentes.
     */
    public boolean equals (Documento d) 
   {
       return (this.getLink().equals(d.getLink()));
   }
    
    /**
     * Compara el link (ruta relativa actual) de un objeto documento (recuperado de una base de datos)
     * con la ruta relativa de un documento que se encuentra en memoria y todavia no ha sido persistido.
     * @param d Documento con el cual se quiere comparar la ruta relativa de la base de datos.
     * @return true si las rutas (relativas) de los documetos es la misma, false si la ruta relativa es distinta.
     */
    public boolean equalsRelativa(Documento d)
    {
        String linkviejo = this.getLink();
        String docnuevo = d.getPathRelativa();
        return (this.getLink().equals(d.getPathRelativa()));
    }
    
    
}
