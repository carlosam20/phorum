<%@ taglib uri="http://www.springframework.org/tags/form" prefix="springform" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>formulario Registro Valoracion</title>
<link rel="stylesheet" href="css/formulario_admin.css">
</head>
<body>


<springform:form modelAttribute="nuevoValoracion" action="guardarNuevoValoracion" enctype="multipart/form-data">

Post: <springform:select path="idPost">
		<springform:options items="${posts}"/>
</springform:select><br>
Usuario: <springform:select path="idUsuario">
		<springform:options items="${usuarios}"/>
</springform:select><br>

Valoracion:  <springform:select path="valor">
		<springform:options items="${valoraciones}"/>
</springform:select><br>



<springform:hidden path="id"/>
<!-- Imagen que introducimos -->
<input type="submit" value="Registrar Valoracion">
</springform:form>

</body>
<script src="../js/reloadCache.js"></script>
</html>







