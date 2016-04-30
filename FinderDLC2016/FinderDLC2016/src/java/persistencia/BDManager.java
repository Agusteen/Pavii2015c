
package persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import logicaIndexado.*;

/**
 *
 * @author 
 * Muñoz Campos, Agustín (62846) 
 * Ramírez, Nicolás (63318)
 */
public class BDManager {

    private static BDManager singleIntance;
    private Connection connection = null;
    private Statement statement = null;
    private String query = "";
    private ResultSet rs = null;
    private PreparedStatement ps = null;
    private PreparedStatement pi = null;
    private PreparedStatement pu = null;
    private PreparedStatement psaux = null;
    private PreparedStatement piaux = null;
    private PreparedStatement psaux2 = null;
    private PreparedStatement psaux3 = null;
    private PreparedStatement pioi = null;
    private PreparedStatement pior = null;
    private String connectionString;
    
    private BDManager() {
       
    }
    /**
     * Recibe el contexto de la aplicacion y tranforma el contexto en la cadena que usara la clase para conectarse a la BD
     * @param connectionString contexto de la aplicacion.
     */
    public void setConnectionString(String connectionString) {
        this.connectionString = "jdbc:sqlite:"+connectionString+"..\\..\\..\\DB\\BuscadorVectorial";
    }

    /**
     * Realiza la conexión de la base de datos
     * @return Una cadena con el error en caso de que lo hubiera
     */
    public String connect() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(connectionString);

        } catch (SQLException ex) {
            return "Error " + ex.getMessage();
        } catch (ClassNotFoundException ex) {
            return "Class Error " + ex.getMessage();
        }
        return "";
    }

    /**
     * Implementación de singleton, retorna la instancia de la clase si existe, en caso de que no existiera crea la instancia
     * y la devuelve.
     * @return la unica instancia de la clase BDManager
     */
    public static BDManager getBDManager() {
        if (singleIntance != null) {
            return singleIntance;
        } else {
            singleIntance = new BDManager();
            return singleIntance;
        }
    }

    /**
     * Almacena en la BD un documento.
     *
     * @param d documento que se desea persistir.
     */
    public void persistDoc(Documento d) {

        if (d == null) {
            return;
        }

        try {
            connection.setAutoCommit(false);
            pioi = connection.prepareStatement("Insert or ignore into DOCUMENTO (ruta) values (?)");

            String link = d.getPathRelativa();
            pioi.setString(1, link);
            pioi.executeUpdate();

            connection.commit();
            connection.setAutoCommit(true);

        } catch (SQLException ex) {

            System.out.println("Error " + ex.getMessage());
            try {
                connection.rollback();
            } catch (SQLException e) {

                System.out.println("Error " + e.getMessage());

            }

        }

    }

    /**
     * Almacena en la base de datos todas las palabras que estan en memoria, correspondientes al documento que se esta leyendo
     * @param i Recibe un iterador de la colección de palabras que se encuentra en memoria.
     */
    public void persistpalabras(Iterator<Palabra> i) {

        Palabra p = null;
        Documento d = null;

        if (i == null) {
            return;
        }

        try {
            connection.setAutoCommit(false);

            String ior = "Insert or replace into VOCABULARIO (palabra, documentos, maxRepeticion)";
            ior += " values ( ?, ?, ?)";

//            String ior = "Insert or replace into VOCABULARIO  (cadena, repeticiones)";
//            ior += " values ( ?,coalesce";
//            ior += "( (SELECT repeticiones FROM palabra WHERE cadena LIKE ?) + ?,?))";
            pior = connection.prepareStatement(ior);

            String insert = "Insert into POSTEO (palabra, idDocumento, repeticion) values (?,(select id from DOCUMENTO where ruta like ?),?);";
//            String ioi = "Insert or ignore into PalabraXDocumento (idDoc, cadena) ";
//            ioi += "values ((select idDoc from Documento where link like ?), ?)";
//             pioi = connection.prepareStatement(ioi);
            pi = connection.prepareStatement(insert);

            while (i.hasNext()) {

                p = i.next();

                if (p.getaparicion() == false) {
                    continue;
                }

                pior.setString(1, p.getCadena());
                pior.setInt(2, p.getDocumentosDiferentes());
                pior.setInt(3, p.getRepeticionMaxima());

                pior.executeUpdate();

                String ruta = p.getDoc().getPathRelativa();
//                Documento d = p.getDoc();
                d = p.getDoc();
                pi.setString(1, p.getCadena());
                pi.setString(2, ruta);
                pi.setInt(3, p.getRepeticionpordocumento());
                pi.executeUpdate();

            }

            connection.commit();

   //         mapapalabras.registrarFinPersist();
            //        mapapalabras.registrarFinPersitinFiles("Listo");
            connection.setAutoCommit(true);

        } catch (SQLException ex) {
            System.out.println("Error " + ex.getMessage());
            try {
                connection.rollback();
                //         mapapalabras.registrarFinPersitinFiles("Error al guardar.");
            } catch (SQLException e) {

                System.out.println("Error " + e.getMessage());
            }
        }

    }

    /**
     * Retorna una lista de todas las palabras que recupera de la BD
     *
     * @return retorna la lista
     */
    public LinkedList materializeWords() {
        statement = null;
        rs = null;
        Palabra p;
        LinkedList<Palabra> list = new LinkedList<>();

        query = "select p.cadena as 'Palabra', p.repeticiones as 'Repeticiones', count(pxd.idDoc) as 'Aparicion' ";
        query += "from Palabra as p join PalabraXDocumento as pxd on (p.cadena= pxd.cadena) ";
        query += "group by p.cadena, p.repeticiones";

        try {
            statement = connection.createStatement();
            rs = statement.executeQuery(query);
            while (rs.next()) {
                p = Palabra.generarPalabra(rs.getString("Palabra"));
                p.setContador(rs.getInt("Repeticiones"));

             //   p.setCantidadDocumentos(rs.getInt("Aparicion"));
                list.add(p);

            }

        } catch (SQLException ex) {

            System.out.println("Error " + ex.getMessage());
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {
                    System.out.println("Error " + ex.getMessage());
                }
            }

        }

        return list;

    }

    /**
     * Busca una cadena en la BD, crea una Palabra junto con cada aparición en
     * documentos distintos y la retorna
     *
     * @param cadena a buscar en BD
     * @return la palabra que recupera de la BD
     */
    public Palabra materializeAparicion(String cadena) {
        statement = null;
        Palabra p = null;
        Documento d;
        query = "select d.link as 'link', pxd.cadena as 'cadena', p.repeticiones as 'repeticiones' ";
        query += "from PalabraXDocumento as pxd join Documento as d on (pxd.idDoc = d.idDoc) ";
        query += "join Palabra as p on (pxd.cadena = p.cadena) ";
        query += "where pxd.cadena like '" + cadena + "'";
        try {
            statement = connection.createStatement();
            rs = statement.executeQuery(query);

            while (rs.next()) {
                if (null == p) {
                    p = Palabra.generarPalabra(rs.getString("cadena"));
                    p.setContador(rs.getInt("repeticiones"));
                }
                d = new Documento(rs.getString("link"));

                p.addDocument(d);
            }
        } catch (SQLException ex) {
            System.out.println("Error " + ex.getMessage());
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {

                    System.out.println("Error " + ex.getMessage());
                }
            }

        }

        return p;

    }

    /**
     * Borra el contenido de la BD.
     */
    public void deleteall() {

        statement = null;

        try {
            query = "Delete from Palabra";
            statement = connection.createStatement();
            statement.executeUpdate(query);

            query = "Delete from Documento";
            statement = connection.createStatement();
            statement.executeUpdate(query);

            query = "Delete from PalabraXDocumento";
            statement = connection.createStatement();
            statement.executeUpdate(query);

        } catch (SQLException ex) {

            System.out.println("Error " + ex.getMessage());
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {

                    System.out.println("Error " + ex.getMessage());
                }
            }
        }

    }

    /**
     * Almacena en la BD todos los documentos recorriendo el iterador.
     *
     * @param i iterador de una lista o coleccion de documentos
     */
    public void persistDoc(Iterator<Documento> i) {

        if (i == null) {
            return;
        }

        try {
            connection.setAutoCommit(false);
            pioi = connection.prepareStatement("Insert or ignore into Documento (link) values (?)");
            while (i.hasNext()) {
                String link = i.next().getLink();
                pioi.setString(1, link);
                pioi.executeUpdate();

            }
            connection.commit();
            connection.setAutoCommit(true);

        } catch (SQLException ex) {

            System.out.println("Error " + ex.getMessage());
            try {
                connection.rollback();
            } catch (SQLException e) {

                System.out.println("Error " + e.getMessage());

            }

        }

    }

    
    /**
     *
     * @return retorna una lista con los documentos que se recuperan de la BD.
     */
    public LinkedList<Documento> materializeDocs() {

        statement = null;
        Documento d;
        LinkedList<Documento> list = new LinkedList<>();

        try {
            statement = connection.createStatement();
            rs = statement.executeQuery("select ruta from DOCUMENTO");
            while (rs.next()) {
                d = new Documento(rs.getString("ruta"));
                list.add(d);
            }

        } catch (SQLException ex) {

            System.out.println("Error " + ex.getMessage());
        }
return list;


    }

    /**
     * Recupera de la base de datos la cantidad de documentos distintos que se encuentran cargados
     * @return 
     */
    public int buscarCantidadDocumentostotal() {
        statement = null;

        int cantidad = 0;
        Documento d;
        query = "select count(*) as 'Cantidad' from DOCUMENTO";
        try {
            statement = connection.createStatement();
            rs = statement.executeQuery(query);

            while (rs.next()) {
                cantidad = rs.getInt("Cantidad");
            }
        } catch (SQLException ex) {
            System.out.println("Error " + ex.getMessage());
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {

                    System.out.println("Error " + ex.getMessage());
                }
            }

        }

        return cantidad;
    }

    /**
     * Busca la existencia de una palabra en al base de datos.
     * @param cadena Es la cadena que se ba a buscar en la base de datos para verificar su existencia. 
     * @return Retorna la palabra que encuentra en la base de datos o null en caso de no encontrarla.
     */
    public Palabra buscarPalabra(String cadena) {
        ps = null;
        Palabra palabra = null;

        query = "select palabra as 'cadena', documentos as 'documentos', maxRepeticion as 'maxrepeticion' from VOCABULARIO where palabra like ?";

        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, cadena);

            rs = ps.executeQuery();

            while (rs.next()) {

                palabra = Palabra.generarPalabra(rs.getString("cadena"));
                palabra.setDocumentosDiferentes(rs.getInt("documentos"));
                palabra.setRepeticionMaxima(rs.getInt("maxrepeticion"));

            }
        } catch (SQLException ex) {
            System.out.println("Error " + ex.getMessage());
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {

                    System.out.println("Error " + ex.getMessage());
                }
            }
        }
        return palabra;
    }

    /**
     * Recuperar una cantidad específica de posteos de base de datos correspondientes 
     * a la cadena mandada como parámetro, el ordenamiento de la consulta es en orden 
     * descendente sin embargo la lista generada se ordena posteriormente de menor a mayor
     * y se retorna un iterador descendente de manera de recorrer los posteos de mayor 
     * a menor debido a la naturaleza del dominio.
     * @param cadena La cadena correspondientes con la que se buscaran los posteos
     * @param cantidadDocumentosAMostrar La cantidad de posteos a traer
     * @return Iterador descendente que recorra los posteos de mayor a menor en funcion de la frecuencia de los mismos.
     */
    public Iterator buscarPosteos(String cadena, int cantidadDocumentosAMostrar) {
        LinkedList<Posteo> listaposteos = new LinkedList<>();;
        ps = null;
        rs = null;
        Posteo posteo;
        query = "select d.ruta as 'ruta', p.repeticion as 'repeticion' from POSTEO as p ";
        query += " JOIN DOCUMENTO as d on (p.idDocumento = d.id) where p.palabra like ? order by repeticion desc limit ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, cadena);
            ps.setInt(2, cantidadDocumentosAMostrar);
            rs = ps.executeQuery();
            while (rs.next()) {

                posteo = new Posteo(rs.getString("ruta"), rs.getInt("repeticion"));
                listaposteos.add(posteo);
            }

        } catch (SQLException ex) {

            System.out.println("Error " + ex.getMessage());
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {
                    System.out.println("Error " + ex.getMessage());
                }
            }

        }
        //La lista ya viene ordenada desde la BD y por como se lee los elementos se van agreando de
        //mayor a menor, pudiendo devolver simplemente el iterador listaposteos.Iterator();
        //Sin embargo si de BD no viniera acotada ni ordenada, con los metodos de abajo se ordena de menor
        // a mayor y se devuelve el Iterador Descendente.
        // Por la naturaleza del dominio debemos leer los posteos de mayor a menor.
        Collections.sort(listaposteos);

        return listaposteos.descendingIterator();

    }

}
