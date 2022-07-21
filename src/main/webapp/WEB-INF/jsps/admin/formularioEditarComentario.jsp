<%@ taglib uri="http://www.springframework.org/tags/form" prefix="springform" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>formularioRegistroOrdenador</title>
</head>
<body>
<jsp:include page="cabecera.jsp"></jsp:include>

<springform:form modelAttribute="nuevoComentario" action="guardarNuevoComentario" enctype="multipart/form-data">

Usuario id: <springform:input path="usuario"/><br>
Comentario: <springform:input path="textoComentario"/><br>
Post id: <springform:input path="post"/><br>
<springform:hidden path="id"/>


<input type="submit" value="Registrar Comentario">



</springform:form>
</body>
</html>







