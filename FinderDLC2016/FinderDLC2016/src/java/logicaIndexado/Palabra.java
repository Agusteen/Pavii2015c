
/**
 *
 * @author 
 * Muñoz Campos, Agustín (62846)
 * Ramírez, Nicolás (63318)
 */
package logicaIndexado;

//import java.util.Iterator;

import java.util.Objects;

//import java.util.LinkedList;

public class Palabra implements Comparable{

    private String cadena;
    /*Variable Booleana Aparicion, tiene como objetivo poder identificar cuando una palabra de la hashmap de palabras
    es nueva o es repetida pero es del documento actual que se esta leyendo y diferenciarla (false) de las palabras
    que existen en memoria pero no estan en el documento actual que se esta leyendo. Al finalizar la lectura de un
    documento (persistirlo preferentemente) se debe setear las flags en falso. */
    private boolean aparicion;
   // private LinkedList<Documento> docs;
    private Documento doc;
    /* Cuantas veces se repite por documento, tf*/
    private int repeticionpordocumento;
    /* Cuantas veces se repite como maximo en el documento ( documento en el que mas aparece) mas tf*/
    private int repeticionmaxima;
    /*En cuantos documentos distintos esta la palabra nr */
    private int documentosdiferentes;
    private int cantidaddocs;    

    private Palabra() {

    }

    private Palabra(String p) {
        cadena = p;
        repeticionpordocumento = 1;
        repeticionmaxima = -1;
        documentosdiferentes = 0;
        aparicion = true;
       // docs = new LinkedList<>();
        
    }

    /*
    Crea un objeto palabra, identeifica si la cadena que ingresa es alfanumerica
    en el caso de la cadena tener guiones (-) al inicio o al final los elimina
    */
    public static Palabra generarPalabra(String p) {
        
        //Para palabras alfanumericas
        String aux = p;
        String aux2 = aux;//.replaceAll("\\d", "");
        

       //Para quitar guiones de conversasion.
        boolean deletePrefix = true, deleteSufix = true;
        if (aux.length()==1){
            Palabra word = new Palabra(aux.toLowerCase());
            return word;
        }
        if (aux.compareTo(aux2) == 0 && !(aux.isEmpty()) && (aux.length() > 1)) {
           
            while (deletePrefix) {
                if (aux.length() > 0 && aux.charAt(0) == '-') {
                    if (aux.length() > 1) {
                        aux = aux.substring(1, aux.length());
                    } else {
                        aux = "";
                    }
                } else {
                    deletePrefix = false;
                }
            }
            while (deleteSufix) {
                if (aux.length() > 0 && aux.charAt(aux.length() - 1) == '-') {
                    aux = aux.substring(0, aux.length() - 1);
                } else {
                    deleteSufix = false;
                }
            }
            if (aux.length() < 2) {
                return null;
            } 

            Palabra word = new Palabra(aux.toLowerCase());
            return word;
        } else {
            return null;
        }

    }

    public int getRepeticionpordocumento() {
        return repeticionpordocumento;
    }


    public void setContador(int con) {
        repeticionpordocumento = con;
    }
/*
    Agrega un documento, si ya existe no lo agrega
    */
    public void addDocument(Documento d) {
        if (d == null) {
            return;
        }
        doc = d; 
//        if (!this.contieneDoc(d)) {
//            docs.add(d);
//            cantidaddocs++;
//        }
    }

   public void trash()
   {
//      public Iterator getIteratorDocs() {
//        return docs.iterator();
//    } 
       
//      public Documento ultimoDoc() {
//        return docs.getLast();
//    } 
       
//       public void setCantidadDocumentos(int c)
//    {
//       
//       cantidaddocs = c;
//    }
       
//         public int getCantidadDocumentos()
//    {
//                return cantidaddocs;
//    }
       
       /*
    Busca un documento en la lista de documentos retorna true si existe ya 
    el documento, false si no existe
    */
//    private boolean contieneDoc(Documento doc) {
//        if( docs.isEmpty()) return false;
//        else {
//        //Toma el ultimo elemento de la lista donde es mas probable encontrar el mismo documento
//        if (docs.getLast().equals(doc)) {
//            return true;
//            }
//        }
//        //verifica si el documento existe en toda la lista.
//        for (Documento d : docs) {
//            if (d.equals(doc)) {
//                return true;
//            }
//        }
//        return false;
//    }
   }

    public Documento getDoc() {
        return doc;
    }
   
   
    
   public void verificarRepeticionMaxima()
   {
       if (this.repeticionmaxima < this.repeticionpordocumento)
       {
           this.repeticionmaxima = this.repeticionpordocumento;
       }
       
   }

    public void setRepeticionMaxima(int repeticionmaxima) {
        this.repeticionmaxima = repeticionmaxima;
    }

    public void setDocumentosDiferentes(int documentosdiferentes) {
        this.documentosdiferentes = documentosdiferentes;
    }

    public int getRepeticionMaxima() {
        return repeticionmaxima;
    }

    public int getDocumentosDiferentes() {
        return documentosdiferentes;
    }
   
   
   public void contarNuevoDocumento()
   {
       this.documentosdiferentes++;
   }
   
   public boolean getaparicion()
   {
       return aparicion;
   }
   
   public void registraraparicion()
   {
       this.aparicion= true;
   }
   
   public void reiniciaraparicion()
   {
       this.aparicion = false;
   }
    

    public void contarrepeticion() {
        repeticionpordocumento++;
    }

    public String getCadena() {
        return cadena;
    }

    public void setCadena(String cadena) {
        this.cadena = cadena;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Palabra: ").append(cadena).append(" - Repeticiones max: ").append(repeticionmaxima).append(" - Repeticiones en doc: ").append(repeticionpordocumento).append(" - Cantidadiad de documentos: ").append(documentosdiferentes).append("\n ");
       // Iterator i = docs.iterator();
//        while (i.hasNext()) {
//            sb.append("Documentos: ").append(i.next().toString()).append("\n ");
//        }
        return sb.toString();
    }

    void reiniciarrepeticion() {
       repeticionpordocumento = 0;
    }


    @Override
    public int compareTo(Object o) {
            Palabra p = (Palabra) o;
            int i = this.getDocumentosDiferentes() - p.getDocumentosDiferentes();
      if (i<0) return -1;
      else if ( i > 0) return 1;
      else return 0;
    
      
            
    
    }

    

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Palabra other = (Palabra) obj;
        if (!Objects.equals(this.cadena, other.cadena)) {
            return false;
        }
        return true;
    }

   
    
    
  


}
