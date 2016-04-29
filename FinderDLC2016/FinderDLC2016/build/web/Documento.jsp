<%-- 
    Document   : Documento
    Created on : 26-abr-2016, 23:58:22
    Author     : agust
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="controladoresWeb.DocumentoParaMostrar"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Documento</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">

        <!-- Bootstrap -->
        <link href="bootstrap/css/bootstrap.min.css" rel="stylesheet">        
        <link rel="stylesheet" href="bootstrap/css/bootstrap.css" type="text/css">
        <script src="bootstrap/js/bootstrap.js" type="text/javascript"></script>        
        <script src="http://code.jquery.com/jquery-2.0.3.min.js" type="text/javascript"></script>
        <script src="http://netdna.bootstrapcdn.com/bootstrap/3.0.0/js/bootstrap.min.js" type="text/javascript"></script>
    </head>
    <body>
        <script type="text/javascript">
            $(document).ready(function () {
                $('#tooltip1').tooltip();
            });
        </script>

        <div id="wrap">

            <div class='text-left col-md-3'><img style="width: 75%; height: 75%; margin-left: 25%; margin-top: 1.5%" src="bootstrap/css/logo1.png" alt=""/></div>
                <% DocumentoParaMostrar p = (DocumentoParaMostrar) request.getAttribute("documento");%>
            <div id="wrap">
                <div id="content">
                    <div class="col-md-8 col-md-offset-2" style="margin-top: 1.5%">    
                        <div class="panel panel-default">

                            <div class="panel panel-title">
                                <div class="text-right" style="margin-top: 1%; margin-right: 1%">
                                    <a href="file:///<%=p.getRuta()%>" target="_blank"><button  class="btn btn-primary" id="tooltip1" data-placement="bottom" data-toggle="tooltip" title="Solo para Internet Explorer"><span class="glyphicon glyphicon-download-alt"></span></button></a>                                    
                                </div>
                                <h2 class="text-center">                                    
                                    <%= p.getTitulo()%>

                                </h2>
                            </div>
                            <div class="panel-body">
                                <p> 
                                    <%= p.getCuerpo()%>
                                </p>
                            </div>
                        </div>


                    </div>
                </div>

            </div>
        </div>
        <!-- Comienza pie de pagina -->
        <div id="footer">
            <div class="piedepagina">
                <h5>Muñoz Campos, Agustín (62846) - Ramirez, Nicolás (63318) - DLC - UTN FRC 2016</h5>
            </div>
        </div>

    </body>
</html>
