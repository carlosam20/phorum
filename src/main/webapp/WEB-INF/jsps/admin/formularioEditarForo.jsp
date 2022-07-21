<%@ taglib uri="http://www.springframework.org/tags/form" prefix="springform" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>formularioEditarForo</title>
</head>
<body>
<jsp:include page="cabecera.jsp"></jsp:include>

<springform:form modelAttribute="foro" action="guardarCambiosForo" enctype="multipart/form-data">
<div>
	<div class="mb-3">
	nombre: <springform:input path="nombre" class="form-label"/>
	</div>
	<div class="mb-3">
	pass: <springform:input path="descripcion" class="form-label"/>
	</div>
	<div class="mb-3">
	pass: <springform:input path="fechaCreacion" class="form-label"/>
	</div>
	<div class="mb-3">
	imagen : <springform:input path="imagen" type="file"/><br>
	</div>
	<springform:hidden path="id"/>
</div>	

	<div class="mb-3">
		<input type="submit" value="Guardar Cambios">
	</div>
</springform:form>

</body>
</html>







