
package logicaConsulta;

import java.util.Objects;

/**
 *
 * @author 
 * Muñoz Campos, Agustín (62846) 
 * Ramírez, Nicolás (63318)
 */
public class DocumentoConsulta implements Comparable{
    private String link;
    private float ponderacion;
    
   
    public DocumentoConsulta(String link, int frecuenciadeterminoPorDocumento, int docstotales, int docsdiferentesportermino)
    {
        this.link = link;
        float divaux = (float)docstotales/docsdiferentesportermino;
        this.ponderacion= (float) (frecuenciadeterminoPorDocumento * Math.log10(divaux));
    }
    /**
     * Suma la ponderacion de un documento recibido como parametro. 
     * @param d  DocumentoConsulta del cual se toma la ponderacion a sumar.
     */
    public void addPonderacion (DocumentoConsulta d)
    {
        this.ponderacion += d.getPonderacion();
    }
    
    

    public float getPonderacion() {
        return ponderacion;
    }

    public String getLink() {
        return link;
    }
    
/**
 * Compara la ponderacion instancia actual con la ponderacion de otro objeto DocumentoConsulta,
 * devuelve -1 en caso de que la ponderacion del parametro sea mayor.
 * devuelve +1 en caso  de que la ponderacion de la instancia actual sea mayor.
 * devuelve 0 en caso de que las ponderaciones tanto de la instancia actual como la del parametro sean iguales.
 * @param o Objeto con el cual comparar
 * @return 
 */
    @Override
    public int compareTo(Object o) {        
        DocumentoConsulta x = (DocumentoConsulta) o;
        float aux =this.getPonderacion()- x.getPonderacion();
        if ( aux < 0 ) return -1;
        else if ( aux > 0) return 1;
        else return 0;
    } 

/**
 * Compara los links de la instancia actual con la de un DocumentoCOnsulta enviada como parametro.
 * @param obj Objeto con el cual comparar.
 * @return true si el link de la instancia actual es igual al link del objeto a comparar.
 * false si los links son distintos
 */

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DocumentoConsulta other = (DocumentoConsulta) obj;
        if (!Objects.equals(this.link, other.link)) {
            return false;
        }
        return true;
    }
    
    
}
