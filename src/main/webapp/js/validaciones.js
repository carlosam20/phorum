

//Usuario
const REGEXP_NOMBRE = /^[a-z ]{2,10}$/i;
const REGEXP_EMAIL = /^([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})$/i;
const REGEXP_PASS = /^[a-z0-9áéíóú]{3,10}$/i;
const REGEXP_DESCRIPCION = /^[a-z0-9áéíóú ]{3,500}$/i;

//Post
const REGEXP_NOMBRE_POST = /^[a-z0-9áéíóú_\.-]{2,200}$/i;
const REGEXP_DESCRIPCION_POST = /^[a-z0-9áéíóú ]{3,500}$/i;

//Comentarios
const REGEXP_COMENTARIO_TEXTO = /^[a-z0-9áéíóú_\.-]{2,200}$/i;



function validarNombre(nombre) {

	if (REGEXP_NOMBRE.test(nombre)) {
		return true;

	} else {

		return false;
	}
}

function validarNombrePost(nombrePost) {

	if (REGEXP_NOMBRE_POST.test(nombre)) {
		return true;

	} else {

		return false;
	}
}
function validarTextoComentario(textoComentario) {
	if (REGEXP_COMENTARIO_TEXTO.test(textoComentario)) {
		return true;
	} else {

		return false;
	}
}

function validarEmail(email) {

	if (REGEXP_EMAIL.test(email)) {
		return true;
	} else {

		return false;
	}

}

function validarPass(pass) {

	if (REGEXP_PASS.test(pass)) {
		return true;
	} else {

		return false;
	}

}

function validarDescripcion(descripcion) {

	if (REGEXP_DESCRIPCION.test(descripcion)) {
		return true;
	} else {

		return false;
	}

}

function validarComentario(comentario) {
	if (REGEXP_COMENTARIO_TEXTO.text(comentario)) {
		return true;
	} else {
		return false;
	}
}