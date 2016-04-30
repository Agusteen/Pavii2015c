
/**
 *
 * @author Muñoz Campos, Agustín (62846)
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

    
    /**
     * Implementacion del patron Factory.
     * Crea un objeto palabra en el caso de la cadena tener guiones (-) al inicio o al final los elimina.
     * Si la cadena es alfanumerica o solo númerica la almacena como palabra.
     * @param p cadena a partir del cual se quiere crear una palabra.
     * @return una palabra si la creacion no tuvo problemas, null en caso de no poder crease la palabra.
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

   /**
    * Agrega un documento a la palabra
    * @param d Documento que se desea agregar a la palabra.
    */
    public void addDocument(Documento d) {
        if (d == null) {
            return;
        }
        doc = d; 

    }

   

    public Documento getDoc() {
        return doc;
    }
   
   
    /**
     * Verifica la repeticion máxima, si la repeticion actual de la palabra por documento es menor 
     * la repeticion máxima permanece igual, si la repeticion actual de la palabra por documento es mayor se actualiza
     * el valor de repetición máxima.
     */
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
    
/**
 * Cuenta la repeticion de una palabra.
 */
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

/**
 * Compara la instancia con otro objeto recibido como parametro.
 * 
 * @param o Objeto que se desea comparar.
 * @return devuelve -1 si la instancia aparece en menos documentos diferentes que el parametro.
 * +1 si la instancia aparece en mas documentos diferentes que el parametro.
 * 0 si la instancia y el parametro aparece en iguales documentos diferentes.
 */
    @Override
    public int compareTo(Object o) {
            Palabra p = (Palabra) o;
            int i = this.getDocumentosDiferentes() - p.getDocumentosDiferentes();
      if (i<0) return -1;
      else if ( i > 0) return 1;
      else return 0;
    
      
            
    
    }

    
/**
 * Compara la cadena de una instancia con la cadena del objeto mandado como parametro.
 * @param obj
 * @return true si las cadenas de la instancia y del parametro son iguales.
 * false si las cadenas de la instancia y del parametro son distintas.
 */
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
