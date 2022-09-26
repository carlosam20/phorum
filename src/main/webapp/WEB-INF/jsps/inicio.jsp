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

 <link rel="stylesheet" href="//code.jquery.com/ui/1.13.2/themes/base/jquery-ui.css">
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
                        <a class="nav-link m-3" id="enlace_home" aria-current="page" href="#">Home</a>
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
                        <a class="nav-link m-3" id="enlace_logout" aria-current="page" href="#">Cerrar Sesi&oacuten</a>
                    </li>
                </ul>
            </div>


             <ul class="navbar-nav mb-auto mb-lg-0">
                 <li class="nav-item">
                     <a class="s-btn nav-link active m-3" id="enlace_identificarme" aria-current="page" href="#">Iniciar Sesi&oacuten</a>
                 </li>
                 
                 <li class="nav-item">
                     <a class="s-btn nav-link active m-3" id="enlace_registrarme" aria-current="page" href="#">Registrar</a>
                 </li>
             </ul>

        </div>
    </nav>
</div>



<div id="contenedor"></div>


   
      
<!-- JavaScript -->      

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
<script type="text/javascript" src="librerias_javascript/jquery.js"></script>
<script type="text/javascript" src="librerias_javascript/mustache.js"></script>
<script type="text/javascript" src="librerias_javascript/js.cookie.min.js"></script>
<script type="text/javascript" src="js/carga_plantilla.js"></script>
<script src="https://unpkg.com/@stackoverflow/stacks/dist/js/stacks.min.js"></script>
<script src="https://unpkg.com/sweetalert@2.1.2/dist/sweetalert.min.js"></script>
<script type="text/javascript" src="js/validaciones.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/animejs/3.2.1/anime.min.js"></script>
<script type="text/javascript" src="js/index.js"></script>



<script type="text/javascript">



var login="";
var email = "";
var pass = "";	

//parte de carga de las plantillas en variables:
var plantillaHome = "";
var plantillaListarForos = "";
var plantillaListarPostYComentarios = "";
var plantillaForo = "";
var plantillaLogin = "";
var plantillaCategoria = "";
var plantillaRegistrarUsuario = "";
var plantillaIdentificarUsuario = "";
var plantillaEditarUsuario = "";
var plantillaPerfil = "";


		
cargar_plantillas_del_servidor();
//Llamar aquí a la función de inicio
listadoInicio();

	function listadoInicio() {
	
		$.ajax("servicioWebForos/obtenerForosYPosts", {
			success : function(data) {
				alert("recibido: "+data);
				var forosYPost = JSON.parse(data);
				var texto_html = "";
				texto_html = Mustache.render(plantillaHome,
						forosYPost);
				$("#contenedor").html(texto_html);
				
			}//---end success ---
		}); //---end ajax ---
	}

	function obtener_listado_foros() {
		
		$.ajax("servicioWebForos/obtenerForos", {
			success : function(data) {
				alert("recibido: "+data);
				var foros = JSON.parse(data);
				var texto_html = "";
				texto_html = Mustache.render(plantillaListarForos,
						foros);
				$("#contenedor").html(texto_html);
				
				
				<!--Registro -->
				 
			    $("#form_registro_foro").submit(function(e){
			        var nombre = $("#nombre").val();
			        var descripcion = $("#descripcion").val();
			        
			        if(validarNombre(nombre)){
			            
			            alert("todo ok, mandando informacion al servicio web...");
			            
			            //vamos a usar FormData para mandar el form al servicio web
			            var formulario = document.forms[0];
			            var formData = new FormData(formulario);
			            
			            $.ajax("identificado/servicioWebForos/registroForo",{
			                type: "POST",
			                data: formData,
			                cache: false,
			                contentType: false,
			                processData: false,
			                success: function(res){
			                    if(res == "ok"){
			                        alert("registrado correctamente");
			                        $('#crearForoModal').modal('hide');
			                        obtener_listado_foros();
			                    }else{
			                        alert(res);
			                        alert("Foro no valido");
			                    }
			                }
			            });
			            
	            
			                
			        }//end if validaciones
			        e.preventDefault();
			    });
				
			    
			    <!--Boton Ver Post -->	
			    $(".boton_post_foro").click(function(e){
					var id = $(this).attr("id");
					
					alert("pedir al servidor id:" +id);
					
					$.ajax("servicioWebPosts/obtenerPostPorForoId?id="+id, {
						success : function(data) {
							alert("recibido: "+data);
							var posts = JSON.parse(data);
							var texto_html = Mustache.render(plantillaListarPosts ,posts);
							$("#contenedor").html(texto_html);
							
											
							<!--Registrar Post -->
							
							  $("#form_registro_post").submit(function(e){
							        var nombre = $("#nombre").val();
							        var descripcion = $("#descripcion").val();
							        var idForo = 1;
							        var idUsuario = 1;
							        
							            alert("todo ok, mandando informacion al servicio web...");
							          
							            var formulario = document.forms[0];
							            var formData = new FormData(formulario);
							            
							            $.ajax("identificado/servicioWebPosts/registrarPosts",{
							                type: "POST",
							                data: formData,
							                cache: false,
							                contentType: false,
							                processData: false,
							                success: function(res){
							                    if(res == "ok"){
							                    	
							                    	$('#crearPostModal').modal('hide');
							                    	
							                    
							                        obtener_listado_posts();
							                        
							                    }else{
							                        alert(res);
							                        alert("Post no valido");
							                    }
							                }
							            });
							            
							       
							        e.preventDefault();
							    });
							  						  
							  <!--Boton Ver Comentarios -->
							  $(".boton_ver_post").click(function(e){
								  var id = $(this).attr("id");
								  obtener_post_y_comentarios(id);
								});						
						}//---end success---
					});//--end ajax--
					
					e.preventDefault();
					
				});//-end obtener_listado-comentarios_post	
				
			}//---end success---
		});//--end ajax--

	}//-end obtener_listado-
	
	
function obtener_post_y_comentarios(id){
	var id = $('#boton_comentarios').val();
	$.ajax("identificado/servicioWebComentarios/obtenerComentarios?id="+id, {
		success : function(data) {
			alert("recibido: "+data);
			var categorias = JSON.parse(data);
			var texto_html = "";
			texto_html = Mustache.render(plantillaListarPostYComentarios,
					categorias);
			$("#contenedor").html(texto_html);
			
		}//---end success---
	});//--end ajax--
}//-end obtener_listado-comentarios_post
	
	
function obtener_listado_posts() {
	
	$.ajax("servicioWebPosts/obtenerPosts", {
		success : function(data) {
			alert("recibido: "+data);
			var posts = JSON.parse(data);
			var texto_html = "";
			texto_html = Mustache.render(plantillaListarPosts, posts);
			$("#contenedor").html(texto_html);				
		}//---end success---
	});//--end ajax--
}//-end obtener_listado-
	


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
	
	
	

function mostrarRegistroUsuario(){
	
	$("#contenedor").html(plantillaRegistrarUsuario);
	$("#form_registro_usuario").submit(function(e){
		var nombre = $("#nombre").val();
		var email = $("#email").val();
		var pass = $("#pass").val();
		
		if(validarNombre(nombre)){
			
	
			if(validarEmail(email)){
							
				if(validarPass(pass)){
					
					
						
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
						swal("El registro se ha realizado de forma correcta", "Realizado", "success");
						setTimeOut(mostrarIdentificacionUsuario(),4000);
						
						
					}else{
						swal("El registro se ha realizado de forma incorrecta", "Fallo", "error");
					}
				}
			});
			
				}else{
					swal("La cotrase&ntildea introducida no es correcto", "Error", "error");
				}//endIfPass
			
			}else{
				swal("El email introducido no es correcto", "Error", "error");
			}
				
		}else{
			swal("El nombre introducido no es correcto", "Error", "error");
		}
		
		
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
		
		if(validarEmail(email)){
			
			if(validarPass(pass)){
				
		
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
					
				}else{
					alert(res);
				}
			}			
		});//end.ajax
		
			}else{
				swal("El formato de la contraseña no es valido", "Fallo", "error");
			}
			//endValidarPass
		}else{
			swal("El formato del email no es valido", "Fallo", "error");
		}
		//endValidarEmail
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
					
					<!--Boton Editar -->
					 $(".enlace_editar_usuario").click(function(e){
						 
						 $.ajax("identificado/servicioWebUsuarios/obtenerUsuarioPorId",{
								success:function(data){
									alert("recibido: "+data);
									var info = JSON.parse(data);	
									var texto_html = "";
									texto_html = Mustache.render(plantillaEditarUsuario, info);
									$("#contenedor").html(texto_html);
												
									<!--Form-->
									$("#form_editar_usuario").submit(function(e){
										
										<!--Variables form -->
										var nombre = $("#nombre").val();
										var email = $("#email").val();
										var descripcion = $("#descripcion").val();
										var pass = $("#pass").val();
										
										if(validarNombre(nombre)){
											if(validarEmail(email)){
												if(validarDescripcion(descripcion)){
													if(validarPass(pass)){
										
										 
										var formulario = document.forms[0];
										var formData = new FormData(formulario);
										
																		
										$.ajax("identificado/servicioWebUsuarios/editarUsuarioPorId",{
											type: "POST",
											data: formData,
											cache: false,
											contentType: false,
											processData: false,
											success: function(res){
																				
												if(res == "ok"){
													alert("editado correctamente");
													perfil();
												}else{
													alert(res);
													alert("Usuario no valido");
												}
											}
										});
										
												}//endPass
											}//endDescripcion
								 
								 		}//endEmail
									}//endNombre
										
									}); // end submit form							

								}//end success plantillaCargarForm Editar
												
						});	//end ajax
						
				});//-end enlace editar
												
			}//end success
			
		});	//end ajax
			
}//end perfil


	$("#enlace_home").click(listadoInicio);
	$("#enlace_listado_foros").click(obtener_listado_foros);
	$("#enlace_listado_posts").click(obtener_listado_posts);
	$("#enlace_editar_usuario").click(mostrarRegistroUsuario);
	$("#enlace_registrarme").click(mostrarRegistroUsuario);
	$("#enlace_identificarme").click(mostrarIdentificacionUsuario);
	$("#enlace_logout").click(logout);
	$("#enlace_perfil").click(perfil);

	
	//comprobar si el usuario actual sigue idenficado
	$.ajax("servicioWebUsuarios/comprobarLogin",{
		success:function(res){
			if(res.split(",")[0]=="ok"){
				nombre_login = res.split(",")[1];			
			}
		}
	});
	


</script>


</body>
</html>