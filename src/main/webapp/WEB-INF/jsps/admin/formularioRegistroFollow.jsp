<%@ taglib uri="http://www.springframework.org/tags/form" prefix="springform" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>formularioRegistroPost</title>
<link rel="stylesheet" href="css/formulario_admin.css">
</head>
<body>


<springform:form modelAttribute="nuevoFollow" action="guardarNuevoFollow" enctype="multipart/form-data">

<div class="mb-3">
id: <springform:input path="id"/>
</div>
foro: <springform:select path="idForo">
		<springform:options items="${foros}"/>
</springform:select><br>
usuario: <springform:select path="idUsuario">
		<springform:options items="${usuarios}"/>
</springform:select><br>

<springform:hidden path="id"/>
<!-- Imagen que introducimos -->
<input type="submit" value="Registrar Follow">
</springform:form>

</body>
<script src="../js/reloadCache.js"></script>
</html>







