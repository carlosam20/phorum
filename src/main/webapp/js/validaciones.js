

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

	if (regexp_nombre.test(nombre)) {
		return true;

	} else {

		return false;
	}
}

function validarNombrePost(nombrePost) {

	if (regexp_nombre_post.test(nombre)) {
		return true;

	} else {

		return false;
	}
}
function validarTextoComentario(textoComentario) {
	if (regexp_comentario_texto.test(textoComentario)) {
		return true;
	} else {

		return false;
	}
}

function validarEmail(email) {

	if (regexp_email.test(email)) {
		return true;
	} else {

		return false;
	}

}

function validarPass(pass) {

	if (regexp_pass.test(pass)) {
		return true;
	} else {

		return false;
	}

}

function validarDescripcion(descripcion) {

	if (regexp_descripcion.test(descripcion)) {
		return true;
	} else {

		return false;
	}

}

function validarComentario(comentario) {
	if (regexp_comentario_texto.text(comentario)) {
		return true;
	} else {
		return false;
	}
}