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

texto del comentario: <springform:input path="nombreCompleto"/><br><br>
direccion: <springform:input path="direccion"/><br>
provincia: <springform:input path="provincia"/><br>
campo provincia: <springform:input path="campoProvincia"/><br>
codigo postal: <springform:input path="codigoPostal"/><br>
tipo de tarjeta <springform:input path="tipoTarjeta"/><br>
titular de la tarjeta <springform:input path="titularTarjeta"/><br>
pais: <springform:input path="pais"/><br>
<springform:hidden path="usuario_id"/>
<springform:hidden path="id"/>


<input type="submit" value="Registrar Pedido">



</springform:form>
</body>
</html>







