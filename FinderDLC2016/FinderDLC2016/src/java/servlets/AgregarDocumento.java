package servlets;

import controladoresWeb.LecturaDirectorio;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import logicaIndexado.Coordinador;

/**
 *
 * @author agust
 */
public class AgregarDocumento extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            LecturaDirectorio ld = new LecturaDirectorio();
            File[] nuevosDocumentos = ld.leerDirectorio(getServletContext().getRealPath("/") + "..\\..\\..\\NuevosDocumentos");
            if (nuevosDocumentos.length <= 0) {
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>No hay documentos</title>");
                out.println("</head>");
                out.println("<body>");
                out.println("<h1>La carpeta 'NuevosDocumentos' esta vacía.</h1>");
                out.println("<h3>Coloque los nuevos documentos que desea agregar en la carpeta FinderDLC2016\\NuevosDocumentos</h3>");
                out.println("</br><a href=index.html>Volver</a>");
                out.println("</body>");
                out.println("</html>");
            } else {
                index();

                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Documentos agregados</title>");
                out.println("</head>");
                out.println("<body>");
                out.println("<h1>Los documentos se agregaron correctamente.</h1>");
                out.println("</br><a href=index.html>Volver</a>");
                out.println("</body>");
                out.println("</html>");

            }
        }

    }

    public void index() throws IOException {
        LecturaDirectorio ld = new LecturaDirectorio();

        File[] directorios = ld.leerDirectorio(getServletContext().getRealPath("/") + "..\\..\\..\\NuevosDocumentos");
        Coordinador c = new Coordinador(getServletContext().getRealPath("/"));
        for (File documento : directorios) {
            String path = getServletContext().getRealPath("/") + "..\\..\\..\\NuevosDocumentos\\" + documento.getName();

            c.loadDocs(path);

        }

        c.processdocs();
        //De esta forma se estaria guardando en bd la ruta de NuevosDocumentos, cuando deberia tener DocumentoTPtest para poder abrirlo despues
        for (File documento : directorios) {           

            Path origen = Paths.get(getServletContext().getRealPath("/") + "..\\..\\..\\NuevosDocumentos\\" + documento.getName());
            Path destino = Paths.get(getServletContext().getRealPath("/") + "..\\..\\..\\DocumentosTPtest");
            try{
            Files.move(origen, destino.resolve(origen.getFileName()));
            }
            catch (IOException ex) {
                System.out.println(ex);
            }
            }
        }
    

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
