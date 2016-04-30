
package logicaIndexado;

/**
 *
 * @author Muñoz Campos, Agustín (62846)
 * Ramírez, Nicolás (63318)
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
/**
 * Compara la instancia con un parametro en funcion de la frecuencia de terminos por documento.
 * @param o objeto que se desea comparar
 * @return un valor numérico -1 si la instancia tiene una menor frecuencia de termino por documento que la del parametro.
 * +1 si la instancia tiene una mayor frecuencia de termino por documento que la del parametro.
 * 0 si la instancia tiene igual frecuencia de termino por documento que la del parametro.
 */
    @Override
    public int compareTo(Object o) {
        Posteo x = (Posteo) o;
        float aux =this.getFrecuenciadeterminoPorDocumento()- x.getFrecuenciadeterminoPorDocumento();
        if ( aux < 0 ) return -1;
        else if ( aux > 0) return 1;
        else return 0; }



    




}
