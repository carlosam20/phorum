<%@ taglib uri="http://www.springframework.org/tags/form" prefix="springform" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>formularioEditarUsuario</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="../css/style.css">
</head>
<body>
<jsp:include page="cabecera.jsp"></jsp:include>

<springform:form modelAttribute="usuario" action="guardarCambiosUsuario" enctype="multipart/form-data">
<div>
	<div class="mb-3">
	nombre: <springform:input path="nombre" class="form-label" />
	</div>
	<div class="mb-3"> 
	pass: <springform:input type="password" path="pass" class="form-label" />
	</div>
	<div class="mb-3">
	email: <springform:input path="email" class="form-label" />
	</div>
	<div class="mb-3">
	descripcion: <springform:textarea path="descripcion" class="form-label" />
	</div>
	<div class="col-sm-10 col-md-10 col-lg-10">
		<springform:input id="fecha" type="date"
			pattern=""
			path="fechaCreacion" class="form form-lg form-control" value="${usuario.fechaCreacion}" />
	</div>
	<div class="mb-3">
	imagen : <springform:input path="imagen" type="file" pattern="/^.+\.(png|jpg)$/" /><br>
	</div>
	<springform:hidden path="id"/>
</div>	
	<div class="mb-3">
		<input class="btn btn-primary"type="submit" value="Guardar Cambios">
</div>
	
</springform:form>

</body>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
            crossorigin="anonymous"></script>   
<script src="../js/reloadCache.js"></script>
<script src="../js/setFecha.js"></script>
</html>







