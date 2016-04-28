<%
    String con = request.getParameter("consulta");
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<form method="get" action="Consulta">
    
    <div class='text-left col-md-3'><img style="width: 75%; height: 75%; margin-left: 25%; margin-top: 1.5%" src="bootstrap/css/logo1.png" alt=""/></div>
    <div class="col-lg-8" style="margin-top: 1.5%">
        <div class="row">
            
            <div class="input-group input-group-lg">
                <input type="text" class="form-control" value="<%=con%>" name="lblConsulta">
                <span class="input-group-btn">
                    <button class="btn btn-primary" type="submit">
                        <span class="glyphicon glyphicon-search"></span>
                    </button>
                </span>                    
            </div>  
        </div>
    </div>

</form>