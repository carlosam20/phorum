<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Usuarios</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
</head>
<body>

<jsp:include page="cabecera.jsp"></jsp:include>
<a class="btn btn-outline-secondary m-3" href="registrarUsuario">Nuevo usuario</a><br>


<form action="listarUsuarios">

<div class="input-group mb-3">
  <span class="input-group-text" id="basic-addon1">Nombre:</span>
  <!--  <input type="text"  placeholder="Username" aria-label="Username" aria-describedby="basic-addon1">-->
  <input type="text" name="nombre" value="${nombre}" class="form-control" placeholder="Nombre" aria-label="Nombre" aria-describedby="basic-addon1" />
  <input type="submit" value="BUSCAR"  class="btn btn-primary" />
</div>

	
</form>

<div class="mx-auto">
	paginacion: <br>
	total de usuarios: ${total} <br>
	
	<c:if test="${ anterior >= 0 }">
		<a href="listarUsarios?comienzo=${anterior}&nombre=${nombre}" class="btn btn-outline-primary">anterior</a>
	</c:if>
	
		
	<c:if test="${siguiente < total}">
		<a href="listarUsuarios?comienzo=${siguiente}&marca=${nombre}" class="btn btn-outline-primary">siguiente</a>
	</c:if>
	
</div>

<c:forEach var="usuario" items="${info}" >

<div class="card mx-auto " style="width: 32rem;" >
	
	<img style="height: 500px; margin: 3px"class="card-img-top" src="../subidasUsuario/${usuario.id}.jpg"/>
	<ul class="list-group list-group-flush">
    <li class="list-group-item">id: ${usuario.id} </li>
    <li class="list-group-item">nombre: ${usuario.nombre} </li>
    <li class="list-group-item">email: ${usuario.email} </li>
    <li class="list-group-item">descripcion: ${usuario.descripcion} </li>
    </ul>
    
    <div class="card-body">
	<a href="editarUsuario?id=${usuario.id}" class="card-link" >Editar</a>
	</div>
	 <div class="card-body">
	<a href="borrarUsuario?id=${usuario.id}" class="card-link" >Borrar</a>
	</div>	
</div>
</c:forEach>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
</body>
</html>