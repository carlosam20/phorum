


//Usuario
var regexp_nombre = /^[a-z ]{2,10}$/i;
var regexp_email = /^([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})$/i;
var regexp_pass = /^[a-z0-9áéíóú]{3,10}$/i;
var regexp_descripcion = /^[a-z0-9áéíóú ]{3,500}$/i;

//Post
var regexp_nombre_post = /^[a-z0-9áéíóú_\.-]{2,200}$/i;
var regexp_descripcion_post = /^[a-z0-9áéíóú ]{3,500}$/i;

//Comentarios
var regexp_comentario_texto = /^[a-z0-9áéíóú_\.-]{2,200}$/i;



function validarNombre(nombre){
	
	if ( regexp_nombre.test(nombre)){
		return true;
		
		}else{
		
			return false;
		}
}
	
function validarNombrePost(nombrePost){

	if( regexp_nombre_post .test(nombre)){
		return true;
		
		}else{
			
			return false;
		}
}
function validarTextoComentario(textoComentario){
	if( regexp_comentario_texto.test(textoComentario)){
		return true;
	}else{
			
		return false;
	}
}
		
function validarEmail(email){
	
	if( regexp_email.test(email)){
		return true;
	}else{
		
		return false;
	}
	
}

function validarPass(pass){
	
		if( regexp_pass.test(pass)){
			return true
		}else{
			
			return false;
		}
	
}

function validarDescripcion (descripcion){
		
	if(regexp_descripcion.test(descripcion)){
		return true
		}else{
			
			return false;
		}

}