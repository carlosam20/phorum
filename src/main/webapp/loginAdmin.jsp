<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
	<!DOCTYPE html>
	<html>

	<head>
		<meta charset="UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
  		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
		<link rel="stylesheet" href="css/style.css">
		<link rel="stylesheet" href="css/admin.css">
		<link rel="stylesheet" href="css/loginAdmin.css">
		<title>Login Admin</title>
	</head>

	<body>

		<div class="d-flex align-items-center justify-content-center m-1">

				<div class="row justify-content-center">

					<form class="" id="form-admin" method="post"
						action="admin/">

						<h1 class="text-center m-2">Identificate para acceder a la administraci&oacuten</h1>

						<div class="col-lg-6 col-md-6 col-sm-6 justify-content-center">
							<div class="">
								<img src="images/logo-phorum.svg" id="logo-phorum" alt="phorum">
							</div>
	
							<div class="">
								<input type="password" class="form-control" value="${sessionScope.campo_pass}" name="pass"
								id="pass" placeholder="Contraseña" title="La contraseña tiene que tener entre 3 y 10 caracteteres"/>
							</div>

							<div class=" form-check form-check-inline">
								<input class="form-check-input m-1" type="checkbox" checked="checked" name="recordar_pass" />
								<label class="form-check-label m-1" for="Contraseña">Recordar contrase&ntildea</label>
							</div>
							<div class="">
								<input class="m-1 btn btn-primary" type="submit" />
							</div>
						</div>
					</form>
				</div>

			</div>
		</div>

	</body>

	</html>