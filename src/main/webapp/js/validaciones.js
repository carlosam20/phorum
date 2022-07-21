var regexp_nombre = /^[a-z ]{2,10}$/i;
var regexp_email = /^([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})$/i;
var regexp_pass = /^[a-z0-9áéíóú]{3,10}$/i;

function validarNombre(nombre){
	if ( regexp_nombre.test(nombre)){
		return true;
	}else{
		alert("nombre incorrecto");
		return false;
	}
}
function validarEmail(email){
	if( regexp_email.test(email)){
		return true;
	}else{
		alert("email incorrecto");
		return false;
	}
}
function validarPass(pass){
	if( regexp_pass.test(pass)){
		return true
	}else{
		alert("pass no valido, solo letras y numeros")
		return false;
	}	
}