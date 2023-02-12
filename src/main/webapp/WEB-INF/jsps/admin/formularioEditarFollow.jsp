<%@ taglib uri="http://www.springframework.org/tags/form" prefix="springform" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>formulario Editar Follow</title>

</head>
<body>


<springform:form modelAttribute="follow" action="guardarCambiosFollow" enctype="multipart/form-data">

foro: <springform:select path="idForo">
		<springform:options items="${foros}"/>
</springform:select><br>
usuario: <springform:select path="idUsuario">
		<springform:options items="${usuarios}"/>
</springform:select><br>

<springform:hidden path="id"/>
<!-- Imagen que introducimos -->
<input class="btn btn-primary" type="submit" value="Guardar Cambios">
</springform:form>
</body>
</html>







