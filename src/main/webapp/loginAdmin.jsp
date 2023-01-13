<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" href="css/bootstrap.css">
<link rel="stylesheet" href="css/style.css">
<link rel="stylesheet" href="css/admin.css">
<title>Login Admin</title>
</head>
<body>







<div class="col-md justify-content-center m-3 w-50  " id="contenedor-form">

	<form  class="col-md justify-content-center p-2" id="form-admin"method="post" action="admin/">
	
	<h1 class="text-center m-3">Identificate para acceder a la administraci&oacuten</h1>
	
	<div class="row m-3 justify-content-center">
		<img src="images/logo-phorum.svg" alt="phorum">
	</div>
	
	<div class="row m-3 justify-content-center">
		<input class="form-check-input" type="password" value="${sessionScope.campo_pass}" name="pass" id="pass" placeholder="Inserte la contrase&ntildea" />
	</div>
	
	<div class="row m-3 justify-content-center">
			<label class="align-self-start m-2"for="Contraseña">Recordar contrase&ntildea</label>
			<input class="align-self-start m-2"type="checkbox" checked="checked" name="recordar_pass"/> 
	</div>	
	
	<div class="row m-3	justify-content-center">
		<input class="m-2 btn btn-lg" type="submit"/>
	</div>
	
	</form>

</div>
</body>
</html>