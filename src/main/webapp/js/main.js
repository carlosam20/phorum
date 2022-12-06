
var email = "";
var pass = "";	



const login = "";


var nombre_login ="";

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
				
				
				//Registro		 
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
				
			    
			    //Boton Ver Posts de Foro	
			    $(".boton_post_foro").click(function(e, login){
					var id = $(this).attr("id");
					
					alert("pedir al servidor id:" +id);
					
					$.ajax("servicioWebPosts/obtenerPostPorForoId?id="+id, {
						success : function(data) {
							alert("recibido: "+data);
							var posts = JSON.parse(data);
							var texto_html = Mustache.render(plantillaListarPosts ,posts);
							$("#contenedor").html(texto_html);
							
											
							//Registrar Post
							
							  $("#form_registro_post").submit(function(e){
							        var nombre = $("#nombre").val();
							        var descripcion = $("#descripcion").val();
							        
							        var idUsuario = "";
							        var idForo = id;
							        
							        $.ajax("identificado/servicioWebUsuarios/obtenerUsuarioPorId",{
							            success:function(data){
							                //alert("recibido: "+data);
							                var info = JSON.parse(data);
							                idUsuario = info.id;
							                
							                 
							        	
							        
							            var formulario = document.forms[0];
							            var formData = new FormData(formulario);
							            formData.append('idForo', idForo);
							            formData.append('idUsuario', idUsuario);
							            
							            $.ajax("identificado/servicioWebPosts/registrarPosts",{
							                type: "POST",
							                data: formData,
							                cache: false,
							                contentType: false,
							                processData: false,
							                success: function(res){
							                    if(res == "ok"){		                    	
							                    	$('#crearPostModal').modal('hide');
							                    	location.reload();
							                       // obtener_listado_posts();
							                    }else{
							                        alert(res);
							                        alert("Post no valido");
							                    }
							                } //end Success Registrar Post
							            }); //end Registrar Post
							            
							            }//end Success (Recogemos El id del usuario)
							        });	//end ajax ObtenerUsuario
							        
							        e.preventDefault();
							    });
							  	  
							  //Boton Ver Post y Comentarios 
							  $(".boton_ver_post").click(function(e){
									var idPost = $(this).attr("id");
									
									alert("pedir al servidor post id:"+idPost);
									
									$.ajax("identificado/servicioWebComentarios/obtenerPostYComentariosPorId?idPost="+idPost, {
										success : function(data) {
											alert("recibido: "+data);
											var postYComentarios = JSON.parse(data);
											var texto_html = "";
											texto_html = Mustache.render(plantillaListarPostYComentarios, postYComentarios);
											$("#contenedor").html(texto_html);
											
										}//---end success---
									});//--end ajax--
								  
							  });						
						}//---end success---
					});//--end ajax--
					
					e.preventDefault();
					
				});//-end obtener_listado-comentarios_post	
				
			}//---end success---
		});//--end ajax--

	}//-end obtener_listado-

	
function obtener_listado_posts() {
	
	$.ajax("servicioWebPosts/obtenerPosts", {
		success : function(data) {
			alert("recibido: "+data);
			var posts = JSON.parse(data);
			var texto_html = "";
			texto_html = Mustache.render(plantillaListarPostsPopulares, posts);
			$("#contenedor").html(texto_html);
			
			//Aqui va Ir a Foro en el que muestra los post
			//También Va Ir al 
			
			
							
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
						swal("El registro se ha realizado de forma incorrecta", "Error", "error");
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
	

function mostrarIdentificacionUsuario(login){
	
	$("#contenedor").html(plantillaLogin);
	
	if( typeof(Cookies.get("email")) != "undefined" ){
		$("#email").val(Cookies.get("email"));
	}
	if( typeof(Cookies.get("pass")) != "undefined" ){
		$("#pass").val(Cookies.get("pass"));
	}
	
	$("#form_login").submit(function(){
		
		email = $("#email").val();
		pass = $("#pass").val();
		
		if(validarEmail(email)){
			
			if(validarPass(pass)){
				
	
		$.ajax("servicioWebUsuarios/loginUsuario",{
			data: "email="+email+"&pass="+pass,
			success: function(res){
				if (res.split(",")[0] == "ok"){
					nombre_login = res.split(",")[1];
					id_login = res.split(",")[2];
					
					
					if( $("#recordar_datos").prop('checked') ){
						alert("guardar datos en cookie");
						Cookies.set('email', email, { expires: 100 });
						Cookies.set('pass', pass, { expires: 100 });	
					}
					$("#mensaje_login").html(nombre_login);
					
					
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
				swal("", "Sesi&oacuten cerrada", "info");
				location.reload();
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
					
					//Boton Editar
					 $(".enlace_editar_usuario").click(function(e){
						 
						 $.ajax("identificado/servicioWebUsuarios/obtenerUsuarioPorId",{
								success:function(data){
									alert("recibido: "+data);
									var info = JSON.parse(data);	
									var texto_html = "";
									texto_html = Mustache.render(plantillaEditarUsuario, info);
									$("#contenedor").html(texto_html);
												
									//Form
									$("#form_editar_usuario").submit(function(e){
										
										//Variables form
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
										
													}//end Pass
												}//end Descripcion
									 
									 		}//end Email
									 		
										}//end Nombre
										
									}); //end Submit Form							

								}//end Success (Carga plantilla)
												
						});	//end ajax 
						
				});//-end enlace editar
												
			}//end success Obtener id
			
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


	
	$.ajax("servicioWebUsuarios/obtenerUsuario",{
		success:function(res){
			if(res.split(",")[0]=="ok"){
				nombre_login = res.split(",")[1];
				$("#mensaje_login").html(" Perfil: " + nombre_login + " ");
			}else{
				$("#mensaje_login").html("( no estas identificado )");
			}
		}		
	});
		
	