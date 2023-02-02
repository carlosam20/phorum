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


<springform:form modelAttribute="nuevoPost" action="guardarNuevoPost" enctype="multipart/form-data">

<div class="mb-3">
nombre: <springform:input path="nombre"/>
</div>
<div class="mb-3">
descripcion: <springform:input path="descripcion"/>
</div>
<div class="mb-3">
fechaCreacion: <springform:input type="date" pattern="dd/MM/yyyy" path="fechaCreacion"/>
</div>
<div class="mb-3">
imagen : <springform:input path="imagen" type="file"/><br>
</div>
<div class="mb-3">
likes: <springform:input type="number" path="likes"/>
</div>

foro: <springform:select path="idForo">
		<springform:options items="${foros}"/>
</springform:select><br>

usuario: <springform:select path="idUsuario">
		<springform:options items="${usuarios}"/>
</springform:select><br>

<springform:hidden path="id"/>
<!-- Imagen que introducimos -->
<input type="submit" value="Registrar Post">
</springform:form>

</body>
<script src="../js/admin.js"></script>
</html>







