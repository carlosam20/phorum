<%@ taglib uri="http://www.springframework.org/tags/form" prefix="springform" %>
	<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
		<!DOCTYPE html>
		<html>

		<head>
			<meta charset="UTF-8">
			<title>formularioEditarComentario</title>
			<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
			<link rel="stylesheet" href="../css/style.css">
			<link rel="stylesheet" href="../css/formsAdmin.css">

		</head>

		<body>
			<div class="row text-center align-items-start">
				<div class="col-lg-12 col-md-12 col-sm-12">
					<h1>EditarComentario</h1>
				</div>
			</div>

			<div class="editarComentario my-3">
				<div class="row my-3 mx-2 text-center d-flex justify-content-center align-items-center">
					<div class="col-lg-8 col-md-8 col-sm-8 my-3 mx-1">

						<springform:form modelAttribute="comentario" action="guardarCambiosComentario"
							enctype="multipart/form-data" class="d-grid gap-4" id="formularioComentario" accept-charset="UTF-8">

							<div class="row">
								<div class="col-sm-1 col-md-1 col-lg-1 d-flex align-items-center">
									<p>Post</p>
								</div>
								<div class="col-sm-10 col-md-10 col-lg-10 d-flex align-items-center">
									<springform:select path="idPostComentario">
										<springform:options items="${posts}" />
									</springform:select>
								</div>
							</div>

							<div class="row">
								<div class="col-sm-1 col-md-1 col-lg-1 d-flex align-items-center">
									<p>Usuario</p>
								</div>
								<div class="col-sm-11 col-md-11 col-lg-11 d-flex align-items-center">
									<springform:select path="idUsuario">
										<springform:options items="${usuarios}" />
									</springform:select><br>
								</div>
							</div>
							<div class="row d-flex justify-content-center">
								<div class="col-1 d-flex align-items-center">
									<i class="fa-regular fa-calendar-days fa-lg"></i>
								</div>
								<div class="col-sm-11 col-md-11 col-lg-11 d-flex align-items-center">
									<springform:input type="date" pattern="yyyy/MM/dd" path="fechaCreacion"
										class="form form-lg form-control text-start" />
									<p>
										<springform:errors path="fechaCreacion" />
									</p>
								</div>
							</div>
							<div class="row">
								<div class="col-2 d-flex align-items-center">
									<p>Texto comentario</p>
								</div>
								<div class="col-10 d-flex align-items-center">
									<springform:textarea path="textoComentario" placeholder="Texto del comentario"
										rows="5" cols="40" pattern="^.{1,60}$" maxlenght="60" class="text-start" />
								</div>
							</div>

							<springform:hidden path="id" />

							<div class="row mt-2 " id="botonForm">
								<div class="col-sm-12 col-md-12 col-lg-12 text-center mx-auto">
									<input id="boton_submit" class="btn btn-primary btn-lg" type="submit"
										value="Registrar Comentario">
								</div>
							</div>
						</springform:form>
					</div>
				</div>
			</div>
		</body>

		<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
			integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
			crossorigin="anonymous"></script>
		<script src="https://kit.fontawesome.com/485aa9f350.js" crossorigin="anonymous"></script>
		<script src="../js/spanAdmin.js"></script>
		<script src="../js/reloadCache.js"></script>

		</html>