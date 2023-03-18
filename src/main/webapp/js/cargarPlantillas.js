const cargarPlantillasDelServidor = () => {
    $.get("plantillas_mustache/post.html", (data) => {
      plantillaListarPostYComentarios = data;
    });
    $.get("plantillas_mustache/home.html", (data) => {
      plantillaHome = data;
    });
    $.get("plantillas_mustache/listado_foros.html", (data) => {
      plantillaListarForos = data;
    });
    $.get("plantillas_mustache/listado_foros_busqueda.html", (data) => {
      plantillaListarForosBusqueda = data;
    });
    $.get("plantillas_mustache/listado_foros_identificado.html", (data) => {
      plantillaListarForosIdentificado = data;
    });
    $.get("plantillas_mustache/listado_posts.html", (data) => {
      plantillaListarPosts = data;
    });
    $.get("plantillas_mustache/listado_posts_populares.html", (data) => {
      plantillaListarPostsPopulares = data;
    });
    $.get("plantillas_mustache/registrar_usuario.html", (data) => {
      plantillaRegistrarUsuario = data;
    });
    $.get("plantillas_mustache/editar_usuario.html", (data) => {
      plantillaEditarUsuario = data;
    });
    $.get("plantillas_mustache/login.html", (data) => {
      plantillaLogin = data;
    });
    $.get("plantillas_mustache/perfil.html", (data) => {
      plantillaPerfil = data;
    });
    $.get("plantillas_mustache/perfil.html", (data) => {
      plantillaPerfilUsuarioComentario = data;
    });
  };