/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package logicaIndexado;



/**
 *
 * @author Ariza, Gonzalo (62069)
 * Muñoz Campos, Agustín (62846)
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
       
       public String getPathRelativa()
       {
           
           String[] cadenaaux = link.split("..\\..\\..");
           String relativa ="..\\.."+  cadenaaux[1];

           return relativa;
       }
       
       
    
    public boolean equals (Documento d) 
   {
       return (this.getLink().equals(d.getLink()));
   }
    
    public boolean equalsRelativa(Documento d)
            
    {
        String linkviejo = this.getLink();
        String docnuevo = d.getPathRelativa();
        return (this.getLink().equals(d.getPathRelativa()));
    }
    
    
}
