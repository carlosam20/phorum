<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Comentarios</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
</head>
<body>

<jsp:include page="cabecera.jsp"></jsp:include>
<a class="btn btn-outline-secondary m-3" href="registrarPost">Nuevo post</a><br>


<form action="listarComentarios">

<div class="input-group mb-3">
  <span class="input-group-text" id="basic-addon1">Texto:</span>
  <!--  <input type="text"  placeholder="Username" aria-label="Username" aria-describedby="basic-addon1">-->
  <input type="text" name="nombre" value="${comentarioTexto}" class="form-control" placeholder="Nombre Post" aria-label="Post" aria-describedby="basic-addon1" />
  <input type="submit" value="BUSCAR"  class="btn btn-primary" />
</div>

</form>



<div class="mx-auto">
	paginacion: <br>
	total de post: ${total} <br>
	<c:if test="${ anterior >= 0 }">
		<a href="listarComentarios?comienzo=${anterior}&comentarioTexto=${comentario.comentarioTexto}" class="btn btn-outline-primary">anterior</a>
	</c:if>
	
	
	<c:if test="${siguiente < total}">
		<a href="listarComentarios?comienzo=${siguiente}&comentarioTexto=${comentario.comentarioTexto}" class="btn btn-outline-primary">siguiente</a>
	</c:if>
	
</div>



	
<c:forEach var="comentario" items="${info}" >

<div class="card mx-auto " style="width: 32rem;" >

  <div class="card-body">
    <h5 class="card-title">${comentario.textoComentario}  ${comentario.fechaCreacion }</h5>
    <p class="card-text">Descripcion</p>
  </div>
  
  <ul class="list-group list-group-flush">
  <li class="list-group-item">usuario: ${comentario.usuario.nombre} </li>
    <li class="list-group-item">usuario id: ${comentario.usuario.id} </li>
    <li class="list-group-item">nombre post: ${comentario.postComentario.nombre}</li>
    <li class="list-group-item">post id: ${comentario.postComentario.id}</li>
  </ul>
  
  <div class="card-body">
  <a href="editarComentario?id=${comentario.id}" class="card-link" >Editar</a>
	<a href="borrarComentario?id=${comentario.id}" onclick="return confirm(Estas seguro?)">Borrar</a> 
  </div>
</div>
</c:forEach>
	
	
	

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
</body>
</html>