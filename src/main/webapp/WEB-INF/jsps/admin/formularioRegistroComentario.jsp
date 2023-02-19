<%@ taglib uri="http://www.springframework.org/tags/form" prefix="springform" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>formularioRegistroPost</title>

</head>
<body>


<springform:form modelAttribute="nuevoComentario" action="guardarNuevoComentario" enctype="multipart/form-data">

<div class="mb-3">
Texto del comentario: <springform:input path="textoComentario"/>
</div>
<div class="mb-3">
fechaCreacion: <springform:input type="date" pattern="dd/MM/yyyy"  path="fechaCreacion"/>
</div>

post: <springform:select path="idPostComentario">
		<springform:options items="${posts}"/>
</springform:select><br>

usuario: <springform:select path="idUsuario">
		<springform:options items="${usuarios}"/>
</springform:select><br>

<springform:hidden path="id"/>



<input type="submit" value="Registrar Comentario">
</springform:form>

</body>
</html>







