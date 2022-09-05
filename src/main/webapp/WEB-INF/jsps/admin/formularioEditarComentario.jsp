<%@ taglib uri="http://www.springframework.org/tags/form" prefix="springform" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>formularioEditarPost</title>

</head>
<body>


<springform:form modelAttribute="comentario" action="guardarCambiosComentario" enctype="multipart/form-data">

<div class="mb-3">
Texto del comentario: <springform:input path="textoComentario"/>
</div>
<div class="mb-3">
fechaCreacion: <springform:input type="date" pattern="dd/MM/yyyy" path="fechaCreacion"/>
</div>

<div class="mb-3">
post: <springform:select path="idPostComentario">
		<springform:options items="${posts}"/>
</springform:select><br>
</div>

<div class="mb-3">
usuario: <springform:select path="idUsuario">
		<springform:options items="${usuarios}"/>
</springform:select><br>
</div>

<springform:hidden path="id"/>


<div class="mb-3">
<input type="submit" value="Guardar Cambios">
</div>

</springform:form>

</body>
</html>







