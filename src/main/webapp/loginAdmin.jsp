<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
	<!DOCTYPE html>
	<html>

	<head>
		<meta charset="UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<link href="https://unpkg.com/@primer/css@^20.2.4/dist/primer.css" rel="stylesheet" />
		<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
		<link rel="stylesheet" href="css/style.css">
		<link rel="stylesheet" href="css/loginAdmin.css">
		<title>Login Admin</title>
	</head>

	<body>
		<div class="loginAdmin">
			<div class="d-grid gap-1">

				<div class="row d-flex justify-content-center">
					<h1 class="text-center m-1">Identificate para acceder a la administraci&oacuten</h1>
				</div>

				<div class="col-lg-12 col-md-12 col-sm-12 d-flex justify-content-center">
					<div class="row contenedorFormulario">
						<form id="form-admin" method="post" action="admin/" class="d-grid gap-2">

							<div class="row d-flex justify-content-center">
								<div class="col-lg-6 col-md-6 col-sm-6">
									<img class="" src="images/logo-phorum.svg" id="logo-phorum"
										alt="phorum">
								</div>
							</div>

							<div class="row d-flex justify-content-center">
								<div class="col-lg-12 col-md-12 col-sm-12 d-flex justify-content-center">
									<input type="password" class="form-control" value="${sessionScope.campo_pass}"
										name="pass" id="pass" placeholder="Contraseña"
										title="La contraseña tiene que tener entre 3 y 10 caracteteres"
										maxlength="10" />
								</div>
							</div>

							<div class="row d-flex justify-content-center">
								<div class="col-lg-12 col-md-12 col-sm-12">
									<div class="form-check form-check-inline">
										<input class="form-check-input m-1" type="checkbox" checked="checked"
											name="recordar_pass" />
										<label class="form-check-label m-1" for="Contraseña">Recordar
											contrase&ntildea</label>
									</div>
								</div>
							</div>

							<div class="d-flex justify-content-center">
								<input class="btn btn-primary" type="submit" />
							</div>
					</div>
					</form>
				</div>
			</div>

		</div>


	</body>

	</div>

	</html>