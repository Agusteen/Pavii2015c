<%-- 
    Document   : ResultadoBusqueda
    Created on : 06-abr-2016, 12:37:42
    Author     : agust
--%>

<%@page import="java.util.ArrayList"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Resultado busqueda</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">

        <!-- Bootstrap -->
        <link href="bootstrap/css/bootstrap.min.css" rel="stylesheet">        
        <link rel="stylesheet" href="bootstrap/css/bootstrap.css" type="text/css">
    </head>
    <body>


        <div id="wrap">

            <div id="header">
                <jsp:include page="header.jsp"/>
            </div>

            <!-- Comienza panel de resultados -->
            <div id="content">
                <div class="col-md-8 col-md-offset-2" style="margin-top: 1.5%">          

                    <!--<div class="panel panel-default">
                        <div class="panel-body">-->                   
                    <c:if test="${listado.isEmpty()}"><h1 class="text-center"><b style="font-size: 100px">:(</b></br>No se encontraron resultados</h1></c:if>
                        <c:forEach items="${listado}" var="r" >                           
                            <a href="LecturaDocumento?doc=<c:out value="${r.getNombreArchivo()}"></c:out>" target="_blank" class="list-group-item"><span class="badge">Ponderación: <c:out value="${r.getPonderacion()}"> - </c:out> </span>
                                <span class="badge"> <c:out value="${r.getNombreArchivo()}"> - </c:out> </span>
                                <h2 class="list-group-item-heading"> <c:out value="${r.titulo}">No especificado</c:out></h2>  
                                <p class="list-group-item-text"> <c:out value="${r.detalle}"></c:out></p></a>
                        </c:forEach>  
                        
                    </div>

                    <!--</div>
                </div>-->
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
