<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
		<!DOCTYPE html>
		<html>

		<head>
			<meta charset="UTF-8">
			<title>Valoraciones</title>
			<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet"
				integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3"
				crossorigin="anonymous">
			<link rel="stylesheet" href="../css/style.css">
			<link rel="stylesheet" href="../css/listadosAdmin.css">
		</head>

		<body>

			<jsp:include page="cabecera.jsp"></jsp:include>
			<div class="row text-left">
				<div class="col-4">
					<a class="btn btn-outline-secondary m-1" href="registrarValoracion">Nuevo Valoracion</a><br>
				</div>
			</div>

			<div class="row">
				<div class="col-8 mx-auto">
					<form action="listarValoraciones">
						<div class="input-group mb-3">
							<span class="input-group-text" id="basic-addon1">id:</span>
							<input type="number" name="id" value="${id}" class="form-control" placeholder="Id"
								aria-label="Id" aria-describedby="basic-addon1" />
							<input type="submit" value="BUSCAR" class="btn btn-primary" />
						</div>
					</form>
				</div>
			</div>

			<div class="row d-flex justify-content-start m-1 d-flex d-grid gap-2" id="paginacion">
				<div class="col-2">
				  <c:if test="${ anterior >= 0 }">
					<a href="listarValoraciones?comienzo=${anterior}&id=${id}"
					  class="btn btn-outline-primary btn-sm">anterior</a>
				  </c:if>
				  <c:if test="${ anterior < 0 }">
					<a href="listarValoraciones?comienzo=${anterior}&id=${id}"
					  class="btn btn-outline-primary disabled">anterior</a>
				  </c:if>
				</div>
				<div class="col-2">
				  <c:if test="${siguiente < total}">
					<a href="listarValoraciones?comienzo=${siguiente}&id=${id}"
					  class="btn btn-outline-primary btn-sm">siguiente</a>
				  </c:if>
				  <c:if test="${ siguiente > total}">
					<a href="listarForos?comienzo=${siguiente}&id=${id}"
					  class="btn btn-outline-primary disabled">siguiente</a>
				  </c:if>
				</div>
		
			  </div>
			  </div>
			  <div class="row d-flex justify-content-start m-1 ">
				<div class="col-4 d-flex justify-content-start align-items-center">
				  <h4>Total de foros: </h4><span class="badge rounded-pill bg-primary">${total}</span>
				</div>
		
			  </div>

			<c:forEach var="valoracion" items="${info}">

				<div class="row d-flex justify-content-center m-3">
					<div class="col-lg-6 co-md-6 col-sm-6 ">
						<div class="card"">
							<div class=" card-body">
							<h3 class="card-title">Usuario:${valoracion.usuario.nombre}</h3>
							<h3 class="card-title">Id post: ${valoracion.post.id}</h3>
						</div>
						<ul class=" list-group list-group-flush m-2">
							<li class="list-group-item">Id: ${valoracion.id} </li>
							<li class="list-group-item">Id usuario: ${valoracion.usuario.id} </li>
							<li class="list-group-item">Nombre de usuario: ${valoracion.usuario.nombre} </li>
							<li class="list-group-item">Id post: ${valoracion.post.id} </li>
							<li class="list-group-item">Nombre post: ${valoracion.post.nombre} </li>
							<li class="list-group-item">Valor ${valoracion.valor} </li>
						</ul>

						<div class="card-body">
							<a href="editarValoracion?id=${valoracion.id}" class="btn btn-outline-primary">Editar</a>
							<a href="borrarValoracion?id=${valoracion.id}" class="btn btn-outline-danger">Borrar</a>
						</div>
					</div>
				</div>
				</div>
			</c:forEach>

			<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
				integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
				crossorigin="anonymous"></script>
		</body>

		</html>