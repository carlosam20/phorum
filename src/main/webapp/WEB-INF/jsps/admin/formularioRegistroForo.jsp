<%@ taglib uri="http://www.springframework.org/tags/form" prefix="springform" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>formularioRegistroForo</title>
</head>
<body>


<springform:form modelAttribute="nuevoForo" action="guardarNuevoForo" enctype="multipart/form-data">

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

<springform:hidden path="id"/>
<!-- Imagen que introducimos -->
<input type="submit" value="Registrar Foro">
</springform:form>

</body>
<script src="../js/admin.js"></script>
</html>







