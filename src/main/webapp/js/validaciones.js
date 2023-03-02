

//Usuario
const REGEXP_NOMBRE = /^.{1,60}$/i;
const REGEXP_EMAIL = /^[a-zA-Z]+@[a-zA-Z]+\.[a-zA-Z]{3,254}$/;
const REGEXP_PASS = /^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*_=+-]).{8,60}$/;
const REGEXP_DESCRIPCION = /^.{1,300}$/;

// Foro
const REGEXP_FORO_NOMBRE = /^.{1,60}$/i
const REGEXP_FORO_DESCRIPCION = /^.{1,300}$/; 

// Post
const REGEXP_POST_NOMBRE = /^.{1,100}$/i;
const REGEXP_POST_DESCRIPCION_ = /^.{1,300}$/;

//Comentarios
const REGEXP_COMENTARIO_TEXTO = /^.{1,60}$/i;;



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