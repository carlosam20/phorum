<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Categorias</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
</head>
<body>

<jsp:include page="cabecera.jsp"></jsp:include>
<a href="registrarCategoria">Nueva categoria</a><br>

<p class="">Listado categorias de la tienda:</p>
<c:forEach var="categoria" items="${info}">
<div class="card mx-auto " style="width: 32rem;" >
	<div>
		nombre: ${categoria.nombre}<br>
		descripcion: ${categoria.descripcion}<br>
		alta: ${categoria.alta}<br>
	</div>
	<div class="card-body">
  <c:if test="${ categoria.alta == true}">
		 	<a  href="darBajaCategoria?id=${categoria.id}"class="card-link">dar de baja</a>
		 </c:if>
		 <c:if test="${ categoria.alta == false}">
		 	<a href="darAltaCategoria?id=${categoria.id}" class="card-link">dar de alta</a>
	</c:if>
	<a href="editarCategoria?id=${categoria.id}" class="card-link">Editar</a>
 	</div>
 </div>
</c:forEach>
</body>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
</html>