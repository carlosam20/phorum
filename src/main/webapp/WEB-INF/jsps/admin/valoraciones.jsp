<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Valoraciones</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
</head>
<body>

<jsp:include page="cabecera.jsp"></jsp:include>
<a class="btn btn-outline-secondary m-3" href="registrarValoracion">Nuevo Valoracion</a><br>


<form action="listarValoraciones">

<div class="input-group mb-3">
  <span class="input-group-text" id="basic-addon1">id:</span>
  <input type="number" name="id" value="${id}" class="form-control" placeholder="Id" aria-label="Id" aria-describedby="basic-addon1" />
  <input type="submit" value="BUSCAR"  class="btn btn-primary" />
</div>

	
</form>

<div class="mx-auto">
	paginacion: <br>
	total de valoracions: ${total} <br>
	
	<c:if test="${ anterior >= 0 }">
		<a href="listarValoraciones?comienzo=${anterior}&id=${id}" class="btn btn-outline-primary">anterior</a>
	</c:if>
	
		
	<c:if test="${siguiente < total}">
		<a href="listarValoraciones?comienzo=${siguiente}&id=${id}" class="btn btn-outline-primary">siguiente</a>
	</c:if>
	
</div>

<c:forEach var="valoracion" items="${info}" >

<div class="card mx-auto " style="width: 32rem;" >
	
	<ul class="list-group list-group-flush">
    <li class="list-group-item">Id: ${valoracion.id} </li>
    <li class="list-group-item">Id usuario: ${valoracion.usuario.id} </li>
    <li class="list-group-item">Nombre de usuario: ${valoracion.usuario.nombre} </li>
    <li class="list-group-item">Id post: ${valoracion.post.id} </li>
    <li class="list-group-item">Nombre post: ${valoracion.post.nombre} </li>
	<li class="list-group-item">Valor ${valoracion.valor} </li>
    </ul>
    
    <div class="card-body">
	<a href="editarValoracion?id=${valoracion.id}" class="card-link" >Editar</a>
	</div>
	 <div class="card-body">
	<a href="borrarValoracion?id=${valoracion.id}" class="card-link" >Borrar</a>
	</div>	
</div>
</c:forEach>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
</body>
</html>