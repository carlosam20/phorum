
function cargar_plantillas_del_servidor(){

    $.get("plantillas_mustache/listado_foros.html",function(data){
        plantillaListarForos = data;
    });
	$.get("plantillas_mustache/listado_posts.html",function(data){
        plantillaListarPosts = data;
    });
	$.get("plantillas_mustache/listado_comentarios.html",function(data){
        plantillaListarComentarios = data;
    });
	$.get("plantillas_mustache/post.html",function(data){
        plantillaPost = data;
    });
    $.get("plantillas_mustache/registrar_usuario.html",function(data){
        plantillaRegistrarUsuario = data;
    });
$.get("plantillas_mustache/editar_usuario.html",function(data){
        plantillaEditarUsuario = data;
    });
    $.get("plantillas_mustache/login.html",function(data){
        plantillaLogin = data;
    });	
	$.get("plantillas_mustache/perfil.html",function(data){
		plantillaPerfil = data;
	});
		
}
