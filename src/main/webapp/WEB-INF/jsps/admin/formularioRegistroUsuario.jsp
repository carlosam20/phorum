<%@ taglib uri="http://www.springframework.org/tags/form" prefix="springform" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>formularioRegistroUsuario</title>
</head>
<body>


<springform:form modelAttribute="nuevoUsuario" action="guardarNuevoUsuario" enctype="multipart/form-data">


nombre: <springform:input path="nombre"/>
pass: <springform:input path="pass"/>
email: <springform:input path="email"/>
imagen : <springform:input path="imagen" type="file"/><br>
<springform:hidden path="id"/>
<!-- Imagen que introducimos -->

<input type="submit" value="Registrar Usuario">



</springform:form>
</body>
</html>







