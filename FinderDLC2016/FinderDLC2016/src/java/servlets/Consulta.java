/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import controladoresWeb.*;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import logicaConsulta.*;
import logicaIndexado.Coordinador;

/**
 *
 * @author agust
 */
public class Consulta extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            
            String con = request.getParameter("lblConsulta");           
           
          consulta(con);
         // index();

            response.sendRedirect("ResultadoBusqueda.jsp?consulta=" + con);
        }
    }
    
    public void consulta(String con) throws IOException
    {
        ListaResultados.getInstance().nuevaLista();

            Buscador b = new Buscador(getServletContext().getRealPath("/"));
            int cantidadDocumentosAMostrar = 10;
                      
            Iterator listaurls = b.buscarConsulta(con, cantidadDocumentosAMostrar);
            Resultado r;
           
            while (listaurls.hasNext() && cantidadDocumentosAMostrar != 0)                
            {
                r = new Resultado();
                DocumentoConsulta dc = (DocumentoConsulta)listaurls.next();
                r.obtenerDatos(getServletContext().getRealPath("/")+dc.getLink(), dc.getPonderacion()); 
            
                ListaResultados.getInstance().getLista().add(r);
                cantidadDocumentosAMostrar--;
            }
    }
    
    public void index()
    {
         LecturaDirectorio ld = new LecturaDirectorio();           

           File[] directorios = ld.leerDirectorio(getServletContext().getRealPath("/")+ "..\\..\\..\\DocumentosTPtest");
            Coordinador c = new Coordinador(getServletContext().getRealPath("/"));
            for (File documento : directorios) {
                String path = getServletContext().getRealPath("/")+ "..\\..\\..\\DocumentosTPtest\\" + documento.getName();

                 c.loadDocs(path);
            }
                     
           c.processdocs();
    }
    
    @Override
    public void init() throws ServletException {
        super.init(); //To change body of generated methods, choose Tools | Templates.
        ArrayList lista = ListaResultados.getInstance().getLista();
        getServletContext().setAttribute("listado", lista);
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
