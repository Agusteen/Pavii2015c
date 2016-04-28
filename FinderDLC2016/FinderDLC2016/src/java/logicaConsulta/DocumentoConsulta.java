
package logicaConsulta;

import java.util.Objects;

/**
 *
 * @author nicor_000
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
    
    public void addPonderacion (DocumentoConsulta d)
    {
        this.ponderacion += d.getPonderacion();
    }
    
    /* No creo usar este metodo*/
    public void addPonderacion (int frecuenciadetermino, int docstotales, int docsdiferentesportermino)
    {
        
        float divaux = (float)docstotales/docsdiferentesportermino;
        this.ponderacion += (float) (frecuenciadetermino * Math.log10(divaux));
    }

    public float getPonderacion() {
        return ponderacion;
    }

    public String getLink() {
        return link;
    }
    

    @Override
    public int compareTo(Object o) {        
        DocumentoConsulta x = (DocumentoConsulta) o;
        float aux =this.getPonderacion()- x.getPonderacion();
        if ( aux < 0 ) return -1;
        else if ( aux > 0) return 1;
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
        final DocumentoConsulta other = (DocumentoConsulta) obj;
        if (!Objects.equals(this.link, other.link)) {
            return false;
        }
        return true;
    }
    
    
}
