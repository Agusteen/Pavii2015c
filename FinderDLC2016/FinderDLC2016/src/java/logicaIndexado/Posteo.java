
package logicaIndexado;

/**
 *
 * @author nicor_000
 */
public class Posteo implements Comparable{
    private String link;
    private int frecuenciadeterminoPorDocumento;
    
 
    
    public Posteo()
    {
        
    }
    
   

    public Posteo(String link, int frecuenciadeterminoPorDocumento) {
        this.link = link;
        this.frecuenciadeterminoPorDocumento = frecuenciadeterminoPorDocumento;
     
    }

    public String getLink() {
        return link;
    }

    public int getFrecuenciadeterminoPorDocumento() {
        return frecuenciadeterminoPorDocumento;
    }

    @Override
    public int compareTo(Object o) {
        Posteo x = (Posteo) o;
        float aux =this.getFrecuenciadeterminoPorDocumento()- x.getFrecuenciadeterminoPorDocumento();
        if ( aux < 0 ) return -1;
        else if ( aux > 0) return 1;
        else return 0; }



    




}
