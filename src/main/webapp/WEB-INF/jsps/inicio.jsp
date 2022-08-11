<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Home</title>

<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
<link rel="stylesheet" href="https://unpkg.com/@stackoverflow/stacks/dist/css/stacks.min.css">
<link rel="stylesheet" href="css/bootstrap.css">
<link rel="stylesheet" href="css/style.css">
<link rel="stylesheet" href="css/registrar.css">
<link rel="stylesheet" href="https://fonts.sandbox.google.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@20..48,100..700,0..1,-50..200" />
<link rel="icon" type="image/svg" href="images/logo-phorum.svg">
</head>
<body>


<!--Empieza Navbar -->
<div class="barraNavegacion">
    <nav class="navbar navbar-expand-lg navbar-dark">
        <div class="container-fluid">
            <a class="navbar-brand" href="#">
            	<img src=<c:url value="images/logo-phorum.svg"></c:url> alt="Foto" width="60" height="60" class="d-inline-block align-text-top"/>
                
            </a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarTogglerDemo02" aria-controls="navbarTogglerDemo02" aria-expanded="false" aria-label="Toggle navigation">
                <span class="material-symbols-outlined">menu</span>
            </button>

            <div class="collapse navbar-collapse" id="navbarTogglerDemo02">
                <ul class="navbar-nav me-auto mb-3 mb-lg-0">
                    <li class="nav-item">
                        <a class="nav-link m-3" id="home" aria-current="page" href="#">Home</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link m-3" id="enlace_perfil" aria-current="page" href="#">Perfil</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link m-3" id="enlace_listado_foros" aria-current="page" href="#">Foros</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link m-3" id="enlace_listado_posts" aria-current="page" href="#">Posts</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link m-3" id="enlace_logout" aria-current="page" href="#">Cerrar Sesión</a>
                    </li>
                </ul>
            </div>


             <ul class="navbar-nav me-auto mb-3 mb-lg-0">
                 <li class="nav-item">
                     <a class="s-btn nav-link active m-3" id="enlace_identificarme" aria-current="page" href="#">Iniciar Sesión</a>
                 </li>
                 <li class="nav-item">
                     <a class="s-btn nav-link active m-3" id="enlace_registrarme" aria-current="page" href="#">Registrar</a>
                 </li>
             </ul>

        </div>
    </nav>
</div>


<!--Modal -->




<div id="contenedor"></div>


   
      
<!-- JavaScript -->      

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
<script type="text/javascript" src="librerias_javascript/jquery.js"></script>
<script type="text/javascript" src="librerias_javascript/mustache.js"></script>
<script type="text/javascript" src="librerias_javascript/js.cookie.min.js"></script>
<script type="text/javascript" src="js/carga_plantilla.js"></script>
<script src="https://unpkg.com/@stackoverflow/stacks/dist/js/stacks.min.js"></script>
<script type="text/javascript" src="js/validaciones.js"></script>
<script type="text/javascript" src="js/main.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/animejs/3.2.1/anime.min.js"></script>
<script type="text/javascript" src="js/index.js"></script>


<script type="text/javascript">



var login="";
var email = "";
var pass = "";	

//parte de carga de las plantillas en variables:
var plantillaListarForos = "";
var plantillaListarCategorias = "";
var plantillaForo = "";
var plantillaLogin = "";
var plantillaCategoria = "";
var plantillaRegistrarUsuario = "";
var plantillaIdentificarUsuario = "";
var plantillaEditarUsuario = "";
var plantillaPerfil = "";



		
cargar_plantillas_del_servidor();
//funciones ajax

	function obtener_listado_foros() {
		
		$.ajax("servicioWebForos/obtenerForos", {
			success : function(data) {
				alert("recibido: "+data);
				var foros = JSON.parse(data);
				var texto_html = "";
				texto_html = Mustache.render(plantillaListarForos,
						foros);
				$("#contenedor").html(texto_html);
				
				$(".enlace").click(function(){
					var id = $(this).attr("id");
					$.post("identificado/servicioWebForo/registroForo",
							{
								idForo : id+""
							}).done(function(res){
								if( res!= "ok" ){
									alert(res);
								}else{
									//Llamará al obtenerPostConComentarios Recogiendo el id
									obtenerComentariosPost();	
								}
							});
				});
				
				$("#enlace_crear_foro").submit(function(e){
					var id = $(this).attr("id");
					var nombre = $("#nombre").val();
					var descripcion = $("#descripcion").val();
					if( validarNombre(nombre) && validarEmail(email) && 
						validarPass(pass)){
						
						alert("todo ok, mandando informacion al servicio web...");
						
						//vamos a usar FormData para mandar el form al servicio web
						var formulario = document.forms[0];
						var formData = new FormData(formulario);
						$.ajax("servicioWebUsuarios/registrarUsuario",{
							type: "POST",
							data: formData,
							cache: false,
							contentType: false,
							processData: false,
							success: function(res){
								if(res == "ok"){
									alert("registrado correctamente, ya puedes identificarte");
									mostrarIdentificacionUsuario();
								}else{
									alert(res);
									alert("Usuario no valido");
								}
							}
						});
							
					}//end if validaciones
					e.preventDefault();
				});
				
			}//---end success---
		});//--end ajax--

	}//-end obtener_listado-
	
	
function obtener_listado_posts() {
		$.ajax("servicioWebCategorias/obtenerCategorias", {
			success : function(data) {
				alert("recibido: "+data);
				var categorias = JSON.parse(data);
				var texto_html = "";
				texto_html = Mustache.render(plantillaListarCategorias,
						categorias);
				$("#contenedor").html(texto_html);
				
			}//---end success---
		});//--end ajax--

	}//-end obtener_listado
	
	
	
	
	function obtener_listado_comentarios_post(id){
		var id = $('#boton-post').val();
		$.ajax("servicioWebComentarios/obtenerComentarios?id="+id, {
			success : function(data) {
				alert("recibido: "+data);
				var categorias = JSON.parse(data);
				var texto_html = "";
				texto_html = Mustache.render(plantillaListarComentarios,
						categorias);
				$("#contenedor").html(texto_html);
				
			}//---end success---
		});//--end ajax--
	}//-end obtener_listado-comentarios_post
		
		
	function obtener_listado_posts_foro(id){
		var id = $('#boton-post').val();
		$.ajax("servicioWebPosts/obtenerPosts?id="+id, {
			success : function(data) {
				alert("recibido: "+data);
				var categorias = JSON.parse(data);
				var texto_html = "";
				texto_html = Mustache.render(plantillaListarPosts,
						categorias);
				$("#contenedor").html(texto_html);
				
			}//---end success---
		});//--end ajax--
	}	
	
	
	function obtenerPostsForo() {
		$.ajax("identificado/servicioWebForo/obtenerPostsForo",{
			success:function(res){
				if(res == "te has colado."){
					alert("tienes que identificarte");
				}else{
					var posts_foro = JSON.parse(res);
					var html = Mustache.render(plantillaForo,posts_foro);
					$("#contenedor").html(html);
					
					$(".enlace_entrar_post").click(function(){
						var id = $(this).attr("id-foro");
						$.post("identificado/servicioWebForo/listarPosts",
								{
									idProducto : id+""
								}).done(function(res){
									if( res!= "ok" ){
										alert(res);
									}else{
										//Llamará al obtenerPostConComentarios Recogiendo el id
										obtenerComentariosPost();	
									}
								});
					});
					
					$(".enlace_crear_post").click(function(id){
						var id = $(this).attr("id-foro");
						$.post("identificado/servicioWebForos/crearPostForo?id="+id,
								{
									idProducto : id+""
								}).done(function(res){
									if( res!= "ok" ){
										alert(res);
									}else{
										//Llamará al obtenerPostConComentarios Recogiendo el id
										mostrarRegistroPost();	
									}
								});
					});

				}	
			}
		});
	}
	

	function mostrarRegistroComentario(){
		$("#contenedor").html(plantillaRegistrarPost);
		$("#form_registro_comentarios").submit(function(e){
			var comentario = $("#textoComentario").val();
			if(validarNombre(nombre)){
				
				alert("todo ok, mandando informacion al servicio web...");
				
				//vamos a usar FormData para mandar el form al servicio web
				var formulario = document.forms[0];
				var formData = new FormData(formulario);
				$.ajax("identificado/servicioWebComentarios/registroComentario",{
					type: "POST",
					data: formData,
					cache: false,
					contentType: false,
					processData: false,
					success: function(res){
						if(res == "ok"){
							obtenerPostsForo();
						}else{
							alert(res);
							alert("No ha cargado correctamente");
						}
					}
				});
					
			}//end if validaciones
			e.preventDefault();
		});
	}
	
	function mostrarRegistroPost(){
		$("#contenedor").html(plantillaRegistrarPost);
		$("#form_registro_post").submit(function(e){
			var nombre = $("#nombre").val();
			var descripcion = $("#descripcion").val();
			if( validarNombre(nombre)){
				
				alert("todo ok, mandando informacion al servicio web...");
				
				//vamos a usar FormData para mandar el form al servicio web
				var formulario = document.forms[0];
				var formData = new FormData(formulario);
				$.ajax("identificado/servicioWebPosts/registroPost",{
					type: "POST",
					data: formData,
					cache: false,
					contentType: false,
					processData: false,
					success: function(res){
						if(res == "ok"){
							obtenerPostsForo();
						}else{
							alert(res);
							alert("No ha cargado correctamente");
						}
					}
				});
					
			}//end if validaciones
			e.preventDefault();
		});
	}
	

	function mostrarRegistroUsuario(){
		$("#contenedor").html(plantillaRegistrarUsuario);
		$("#form_registro_usuario").submit(function(e){
			var nombre = $("#nombre").val();
			var email = $("#email").val();
			var pass = $("#pass").val();
			if( validarNombre(nombre) && validarEmail(email) && 
				validarPass(pass)){
				
				alert("todo ok, mandando informacion al servicio web...");
				
				//vamos a usar FormData para mandar el form al servicio web
				var formulario = document.forms[0];
				var formData = new FormData(formulario);
				$.ajax("servicioWebUsuarios/registrarUsuario",{
					type: "POST",
					data: formData,
					cache: false,
					contentType: false,
					processData: false,
					success: function(res){
						if(res == "ok"){
							alert("registrado correctamente, ya puedes identificarte");
							mostrarIdentificacionUsuario();
						}else{
							alert(res);
							alert("Usuario no valido");
						}
					}
				});
					
			}//end if validaciones
			e.preventDefault();
		});
	}

	function mostrarEditarUsuario(){
		$("#contenedor").html(plantillaEditarUsuario);
		$("#form_editar_usuario").submit(function(e){
			var nombre = $("#nombre").val();
			var email = $("#email").val();
			var pass = $("#pass").val();
			if( validarNombre(nombre) && validarEmail(email) && 
				validarPass(pass)){
	
				var formulario = document.forms[0];
				var formData = new FormData(formulario);
				$.ajax("servicioWebUsuarios/editarUsuario",{
					type: "POST",
					data: formData,
					cache: false,
					contentType: false,
					processData: false,
					success: function(res){
						if(res == "ok"){
							alert("registrado correctamente, ya puedes identificarte");
							mostrarIdentificacionUsuario();
						}else{
							alert(res);
							alert("Usuario no valido");
						}
					}
				});
					
			}//end if validaciones
			e.preventDefault();
		});
	}
	
	

function mostrarIdentificacionUsuario(){
	$("#contenedor").html(plantillaLogin);
	if( typeof(Cookies.get("email")) != "undefined" ){
		$("#email").val(Cookies.get("email"));
	}
	if( typeof(Cookies.get("pass")) != "undefined" ){
		$("#pass").val(Cookies.get("pass"));
	}
	$("#form_login").submit(function(e){
		email = $("#email").val();
		pass = $("#pass").val();
		$.ajax("servicioWebUsuarios/loginUsuario",{
			data: "email="+email+"&pass="+pass,
			success: function(res){
				if (res.split(",")[0] == "ok"){
					nombre_login = res.split(",")[1];

					if( $("#recordar_datos").prop('checked') ){
						alert("guardar datos en cookie");
						Cookies.set('email', email, { expires: 100 });
						Cookies.set('pass', pass, { expires: 100 });	
					}
					
					$("#mensaje_login").html("( identificado como: " + nombre_login + " )");
					$("#contenedor").html("login ok");
				}else{
					alert(res);
				}
			}			
		});//end.ajax
		e.preventDefault();				
	});
}

	function logout(){
		$.ajax("servicioWebUsuarios/logout",{
			success:function(res){
				if(res == "ok"){
					$("#contenedor").html("hasta pronto " + nombre_login);
					$("#mensaje_login").html("( no estas identificado )");
				}
			}
		});	
	}
	
	function perfil(){
		$.ajax("identificado/servicioWebUsuarios/obtenerUsuarioPorId",{
			success:function(data){
				alert("recibido: "+data);
				var info = JSON.parse(data);	
				var texto_html = "";
				texto_html = Mustache.render(plantillaPerfil,info);
				$("#contenedor").html(texto_html);
			}//end success
		});	//end ajax
		
		
		
		$(".enlace_editar_usuario").click(function(){
			var id = $(this).attr("id-foro");
			$.post("identificado/servicioWebUsuarios/editarUsuarioPorId",
					{
						idProducto : id+""
					}).done(function(res){
						if( res!= "ok" ){
							alert(res);
						}else{
							//Llamará al obtenerPostConComentarios Recogiendo el id
							obtenerComentariosPost();	
						}
					});
			
		});
		
		$(".enlace_borrar_usuario").click(function(){
		
			$.post("identificado/servicioWebUsuarios/borrarUsuarioid",
					{
						
					}).done(function(res){
						if( res!= "ok" ){
							alert(res);
						}else{
							//Llamará al obtenerPostConComentarios Recogiendo el id
							mostrarRegistroPost();	
						}
					});
		});
	}//end mis_datos
	

	
	
	
	
		
	$("#enlace_listado_foros").click(obtener_listado_foros);
	$("#enlace_registrar_foro").click();
	
	
	$("#enlace_registrarme").click(mostrarRegistroUsuario);
	$("#enlace_identificarme").click(mostrarIdentificacionUsuario);
	$("#enlace_logout").click(logout);
	$("#enlace_perfil").click(perfil);

	
	//comprobar si el usuario actual sigue idenficado
	$.ajax("servicioWebUsuarios/comprobarLogin",{
		success:function(res){
			if(res.split(",")[0]=="ok"){
				nombre_login = res.split(",")[1];
				$("#mensaje_login").html(" Perfil: " + nombre_login + " ");
			}else{
				$("#mensaje_login").html("( no estas identificado )");
			}
		}
	});
	


</script>








</body>
</html>