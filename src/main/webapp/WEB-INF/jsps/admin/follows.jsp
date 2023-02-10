<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Follows</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
</head>
<body>

<jsp:include page="cabecera.jsp"></jsp:include>
<a class="btn btn-outline-secondary m-3" href="registrarFollow">Nuevo Follow</a><br>


<form action="listarFollows">

<div class="input-group mb-3">
  <span class="input-group-text" id="basic-addon1">id:</span>
  <input type="number" name="id" value="${id}" class="form-control" placeholder="Id" aria-label="Id" aria-describedby="basic-addon1" />
  <input type="submit" value="BUSCAR"  class="btn btn-primary" />
</div>

	
</form>

<div class="mx-auto">
	paginacion: <br>
	total de follows: ${total} <br>
	
	<c:if test="${ anterior >= 0 }">
		<a href="listarFollows?comienzo=${anterior}&id=${id}" class="btn btn-outline-primary">anterior</a>
	</c:if>
	
		
	<c:if test="${siguiente < total}">
		<a href="listarFollows?comienzo=${siguiente}&id=${nombre}" class="btn btn-outline-primary">siguiente</a>
	</c:if>
	
</div>

<c:forEach var="follow" items="${info}" >

<div class="card mx-auto " style="width: 32rem;" >
	
	<ul class="list-group list-group-flush">
    <li class="list-group-item">Id: ${follow.id} </li>
    <li class="list-group-item">Id usuario: ${follow.usuario.id} </li>
    <li class="list-group-item">Nombre de usuario: ${follow.usuario.nombre} </li>
    <li class="list-group-item">Id foro: ${follow.foro.id} </li>
    <li class="list-group-item">Nombre foro: ${follow.foro.nombre} </li>
    </ul>
    
    <div class="card-body">
	<a href="editarFollow?id=${follow.id}" class="card-link" >Editar</a>
	</div>
	 <div class="card-body">
	<a href="borrarFollow?id=${follow.id}" class="card-link" >Borrar</a>
	</div>	
</div>
</c:forEach>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
</body>
</html>