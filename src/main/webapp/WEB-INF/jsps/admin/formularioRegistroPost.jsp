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


<springform:form modelAttribute="nuevoPost" action="guardarNuevoPost" enctype="multipart/form-data">

<div class="mb-3">
nombre: <springform:input path="nombre"/>
</div>
<div class="mb-3">
descripcion: <springform:input path="descripcion"/>
</div>
<div class="mb-3">
fechaCreacion: <springform:input path="fechaCreacion"/>
</div>
<div class="mb-3">
imagen : <springform:input path="imagen" type="file"/><br>
</div>
foro: <springform:select path="foro">
		<springform:options items="${foros}"/>
</springform:select><br>

usuario: <springform:select path="usuario">
		<springform:options items="${usuarios}"/>
</springform:select><br>

<springform:hidden path="id"/>
<!-- Imagen que introducimos -->
<input type="submit" value="Registrar Post">
</springform:form>

</body>
</html>






