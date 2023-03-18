<%@ taglib uri="http://www.springframework.org/tags/form" prefix="springform" %>
	<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
		<!DOCTYPE html>
		<html>

		<head>
			<meta charset="UTF-8">
			<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
			<link rel="stylesheet" href="../css/style.css">
			<link rel="stylesheet" href="../css/formsAdmin.css">
			<title>formularioRegistroPost</title>

		</head>

		<body>

			<div class="row justify-content-center m-1">
				<h2 class="text-center">Registra tu comentario aqui</h2>
			</div>
			<div class="registroComentario mx-auto my-auto">
				<div class="row m-1 text-center d-flex justify-content-center align-items-center">
					<div class="col-lg-10 col-md-10 col-sm-6">
						<springform:form modelAttribute="nuevoComentario" action="guardarNuevoComentario"
							enctype="multipart/form-data" class="d-grid gap-3">

							<div class="row d-flex justify-content-center">
								<div class="col-sm-2 col-md-2 col-lg-2">
									<i class="fa-regular fa-comment"></i>
								</div>
								<div class="col-sm-10 col-md-10 col-lg-10">
									<div class="row">
										<springform:textarea path="textoComentario" placeholder="Texto del comentario"
											rows="5" cols="40" pattern="^.{1,60}$" maxlenght="60" class="text-start" />
										<p>
											<springform:errors path="textoComentario" />
										</p>
									</div>
								</div>
							</div>

							<div class="row d-flex justify-content-center">
								<div class="col-sm-2 col-md-2 col-lg-2">
									<i class="fa-regular fa-calendar-days fa-2xl"></i>
								</div>
								<div class="col-sm-10 col-md-10 col-lg-10">
									<springform:input type="date" pattern="yyyy/MM/dd" path="fechaCreacion" class="text-start" />
									<p>
										<springform:errors path="fechaCreacion" />
									</p>
								</div>
							</div>

							<div class="row d-flex justify-content-center">
								<div class="col-sm-2 col-md-2 col-lg-2">
									<p>Post:</p>
								</div>
								<div class="col-sm-10 col-md-10 col-lg-10">
									<div class="custom-select">
										<springform:select path="idPostComentario" class="text-start">
											<springform:options items="${posts}" />
										</springform:select>
									</div>
								</div>
							</div>

							<div class="row d-flex justify-content-center">
								<div class="col-sm-2 col-md-2 col-lg-2">
									<p>Usuario:</p>
								</div>
								<div class="col-sm-10 col-md-10 col-lg-10">
									<div class="custom-select">
										<springform:select path="idUsuario" class="text-start">
											<springform:options items="${usuarios}" />
										</springform:select>
									</div>
								</div>
							</div>

							<springform:hidden path="id" />
							<div class="text-center m-3">
								<input class="btn btn-primary" type="submit" value="Registrar Comentario">
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

		</html>