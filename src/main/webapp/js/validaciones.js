

//Usuario
const REGEXP_NOMBRE = /^[a-z ]{2,10}$/i;
const REGEXP_EMAIL = /^([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})$/i;
const REGEXP_PASS = /^[a-z0-9áéíóú]{3,10}$/i;
const REGEXP_DESCRIPCION = /^[a-z0-9áéíóú ]{3,500}$/i;

// Foro
const REGEXP_FORO_NOMBRE = /^[a-z0-9áéíóú_\.-]{2,200}$/i;
const REGEXP_FORO_DESCRIPCION = /^[a-z0-9áéíóú .,:¿?!¡]{3,500}$/i; 

// Post
const REGEXP_POST_NOMBRE = /^[a-z0-9áéíóú_\.-]{2,200}$/i;
const REGEXP_POST_DESCRIPCION_ = /^[a-z0-9áéíóú .,:¿?!¡]{3,500}$/i;

//Comentarios
const REGEXP_COMENTARIO_TEXTO = /^[a-z0-9áéíóú_\.-]{2,200}$/i;



function validarNombre(nombre) {

	if (REGEXP_NOMBRE.test(nombre)) {
		return true;

	} else {

		return false;
	}
}


function validarComentarioTexto(textoComentario) {
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


function validarNombreForo(nombreForo) {

	if (REGEXP_FORO_NOMBRE.test(nombreForo)) {
		return true;

	} else {

		return false;
	}
}

function validarForoDescripcion(descripcionForo){
	if (REGEXP_FORO_DESCRIPCION.test(descripcionForo)) {
		return true;

	} else {

		return false;
	}
}


function validarNombrePost(nombrePost) {

	if (REGEXP_POST_NOMBRE.test(nombrePost)) {
		return true;

	} else {

		return false;
	}
}