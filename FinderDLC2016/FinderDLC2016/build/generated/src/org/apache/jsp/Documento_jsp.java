package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import controladoresWeb.DocumentoParaMostrar;

public final class Documento_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List<String> _jspx_dependants;

  private org.glassfish.jsp.api.ResourceInjector _jspx_resourceInjector;

  public java.util.List<String> getDependants() {
    return _jspx_dependants;
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;

    try {
      response.setContentType("text/html;charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;
      _jspx_resourceInjector = (org.glassfish.jsp.api.ResourceInjector) application.getAttribute("com.sun.appserv.jsp.resource.injector");

      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("<!DOCTYPE html>\n");
      out.write("<html>\n");
      out.write("    <head>\n");
      out.write("        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n");
      out.write("        <title>Documento</title>\n");
      out.write("        <meta charset=\"UTF-8\">\n");
      out.write("        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n");
      out.write("\n");
      out.write("        <!-- Bootstrap -->\n");
      out.write("        <link href=\"bootstrap/css/bootstrap.min.css\" rel=\"stylesheet\">        \n");
      out.write("        <link rel=\"stylesheet\" href=\"bootstrap/css/bootstrap.css\" type=\"text/css\">\n");
      out.write("    </head>\n");
      out.write("    <body>\n");
      out.write("\n");
      out.write("        <div id=\"wrap\">\n");
      out.write("\n");
      out.write("            <div class='text-left col-md-3'><img style=\"width: 75%; height: 75%; margin-left: 25%; margin-top: 1.5%\" src=\"bootstrap/css/logo1.png\" alt=\"\"/></div>\n");
      out.write("                ");
 DocumentoParaMostrar p = (DocumentoParaMostrar) request.getAttribute("documento");
      out.write("\n");
      out.write("            <div id=\"wrap\">\n");
      out.write("                <div id=\"content\">\n");
      out.write("                    <div class=\"col-md-8 col-md-offset-2\" style=\"margin-top: 1.5%\">    \n");
      out.write("                        <div class=\"panel panel-default\">\n");
      out.write("\n");
      out.write("                            <div class=\"panel panel-title\">\n");
      out.write("                                <div class=\"text-right\" style=\"margin-top: 1%\">\n");
      out.write("                                    <button  class=\"btn btn-primary\"><span class=\"glyphicon glyphicon-plus\"></span></button>\n");
      out.write("                                </div>\n");
      out.write("                                <h2 class=\"text-center\">                                    \n");
      out.write("                                    ");
      out.print( p.getTitulo());
      out.write("\n");
      out.write("\n");
      out.write("                                </h2>\n");
      out.write("                            </div>\n");
      out.write("                            <div class=\"panel-body\">\n");
      out.write("                                <p> \n");
      out.write("                                    ");
      out.print( p.getCuerpo());
      out.write("\n");
      out.write("                                </p>\n");
      out.write("                            </div>\n");
      out.write("                        </div>\n");
      out.write("\n");
      out.write("\n");
      out.write("                    </div>\n");
      out.write("                </div>\n");
      out.write("\n");
      out.write("            </div>\n");
      out.write("        </div>\n");
      out.write("        <!-- Comienza pie de pagina -->\n");
      out.write("        <div id=\"footer\">\n");
      out.write("            <div class=\"piedepagina\">\n");
      out.write("                <h5>Muñoz Campos, Agustín (62846) - Ramirez, Nicolás (63318) - DLC - UTN FRC 2016</h5>\n");
      out.write("            </div>\n");
      out.write("        </div>\n");
      out.write("\n");
      out.write("    </body>\n");
      out.write("</html>\n");
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          out.clearBuffer();
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
