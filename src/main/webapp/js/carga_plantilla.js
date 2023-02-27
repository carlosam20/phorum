
function cargar_plantillas_del_servidor(){

	$.get("plantillas_mustache/post.html",function(data){
        plantillaListarPostYComentarios = data;
    });
	$.get("plantillas_mustache/home.html",function(data){
        plantillaHome = data;
    });

    $.get("plantillas_mustache/listado_foros.html",function(data){
        plantillaListarForos = data;
    });
    $.get("plantillas_mustache/listado_foros_busqueda.html",function(data){
        plantillaListarForosBusqueda = data;
    });
    $.get("plantillas_mustache/listado_foros_identificado.html",function(data){
        plantillaListarForosIdentificado = data;
    });
	$.get("plantillas_mustache/listado_posts.html",function(data){
        plantillaListarPosts = data;
    });
	$.get("plantillas_mustache/listado_posts_populares.html",function(data){
        plantillaListarPostsPopulares = data;
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
    //TODO hacer una nueva plantilla sin los botones de editar ni borrar solo para que el usuario lo vea
    $.get("plantillas_mustache/perfil.html",function(data){
		plantillaPerfilUsuarioComentario = data;
	});
		
}
