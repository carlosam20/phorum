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

post: <springform:select path="idPost">
		<springform:options items="${posts}"/>
</springform:select><br>
usuario: <springform:select path="idUsuario">
		<springform:options items="${usuarios}"/>
</springform:select><br>
Valor del post:<springform:checkbox path="valoracion" />


<springform:hidden path="id"/>
<!-- Imagen que introducimos -->
<input class="btn btn-primary" type="submit" value="Guardar Cambios">
</springform:form>
</body>
</html>







