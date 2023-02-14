<%@ taglib uri="http://www.springframework.org/tags/form" prefix="springform" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>formulario Editar Valoracion</title>

</head>
<body>


<springform:form modelAttribute="valoracion" action="guardarCambiosValoracion" enctype="multipart/form-data">

Post: <springform:select path="idPost">
		<springform:options items="${posts}"/>
</springform:select><br>

Usuario: <springform:select path="idUsuario">
		<springform:options items="${usuarios}"/>
</springform:select><br>

Valoracion: <springform:select path="valor">
		<springform:options items="${valoraciones}"/>
</springform:select><br>



<springform:hidden path="id"/>
<!-- Imagen que introducimos -->
<input class="btn btn-primary" type="submit" value="Guardar Cambios">
</springform:form>
</body>
</html>







