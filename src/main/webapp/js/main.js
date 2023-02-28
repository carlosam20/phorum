
//Variables globales de usuario
let email = "";
let pass = "";
let nombre_login = "";
let idUsuario = "";

//Carga de plantillas en variables
let plantillaHome = "";
let plantillaListarForos = "";
let plantillaListarForosIdentificado = "";
let plantillaListarPostYComentarios = "";
let plantillaListarForosBusqueda = "";
let plantillaForo = "";
let plantillaLogin = "";
let plantillaCategoria = "";
let plantillaRegistrarUsuario = "";
let plantillaIdentificarUsuario = "";
let plantillaEditarUsuario = "";
let plantillaPerfil = "";
let plantillaPerfilUsuarioComentario = "";

//Metodos OnInit
cargar_plantillas_del_servidor();
listadoInicio();


function registrarPost() {
	$("#form_registro_post").submit(function (e) {

		let nombre = $("#nombre").val();
		let descripcion = $("#descripcion").val();

		idUsuario = "";
		let idForo = id;

		$.ajax("identificado/servicioWebUsuarios/obtenerUsuarioPorId", {
			success: function (data) {
				//alert("recibido: "+data);
				let info = JSON.parse(data);
				idUsuario = info.id;

				let formulario = document.forms[0];
				let formData = new FormData(formulario);
				formData.append('idForo', idForo);
				formData.append('idUsuario', idUsuario);


				$.ajax("identificado/servicioWebPosts/registrarPosts", {
					type: "POST",
					data: formData,
					cache: false,
					contentType: false,
					processData: false,
					success: function (res) {
						if (res == "ok") {
							$('#crearPostModal').modal("hide");
							obtener_listado_posts();
						} else {
							swal("Error de sintaxis en algun form del post", "Post no valido", "error");
							alert(res);
						}
					} //end Success Registrar Post
				}); //end Registrar Post

			}//end Success (Recogemos El id del usuario)
		});	//end ajax ObtenerUsuario

		e.preventDefault();

	});
}//-end registrar post-

function registrarComentarioPost() {
	//Realizar comentario en post										
	$("#form_registro_comentario").submit(function (e) {


		let textoComentario = $("#textoComentario").val();

		let formulario = document.forms[0];
		let formData = new FormData(formulario);

		$.ajax("identificado/servicioWebComentarios/registrarComentario?idPost=" + idPost, {
			type: "POST",
			data: formData,
			cache: false,
			contentType: false,
			processData: false,
			success: function (res) {
				if (res == "ok") {
					alert(res);
					//TODO recargar PostYcomentarios aqui
				}
				else {
					alert(res);
				}
			}
			//end Success Registrar Comentario
		}); //end Registrar Comentarios
		e.preventDefault();

	});
}//-end registrar comentario post-

function verPerfilDeComentario() {
	//Ver Perfil del usuario que comento
	$(".boton_ver_perfil").click(function (e) {

		let idUsuarioComentario = $(this).attr("id");

		$.ajax("identificado/servicioWebUsuarios/obtenerUsuarioComentarioPorId?idUsuarioComentario=" + idUsuarioComentario, {
			type: "GET",
			success: function (res) {
				alert("recibido: " + res);
				let info = JSON.parse(res);
				let texto_html = "";

				texto_html = Mustache.render(plantillaPerfil, info);
				$("#contenedor").html(texto_html);
			}
		});

	});
}//-end ver perfil de comentario-

function editarValoracion(idPost, valor) {
	let formData = new FormData();
	// editamosLaValoración				
	formData.append("idPost", idPost);
	formData.append("valor", valor);
	$.ajax("identificado/servicioWebValoracion/editarValoracion", {
		type: "POST",
		data: formData,
		cache: false,
		contentType: false,
		processData: false,
		success: function (res) {
			if (res.includes("ok")) {
				console.log("Valoracion editada con valor: " + valor);
			}

		},
	});

}//-end editarValoracion-

function valoracionLike(idPost) {

	let contadorLikes = document.getElementById("like-contador");
	let contadorDislikes = document.getElementById("dislike-contador");

	$(".like").click(function (e) {
		e.preventDefault();
		comprobarExisteValoracion(idPost)
			.then((valoracionExiste) => {
				let formData = new FormData();

				if (valoracionExiste[0] === false) {
					// User has not yet rated the post				
					formData.append("idPost", idPost);
					formData.append("valor", true);
					$.ajax("identificado/servicioWebValoracion/registrarValoracion", {
						type: "POST",
						data: formData,
						cache: false,
						contentType: false,
						processData: false,
						success: function (res) {
							console.log("Like registrado");
							contadorLikes.textContent = Number(contadorLikes.textContent) + 1;
						},
					});

				} else if (valoracionExiste[0] && valoracionExiste[1]) {
					console.log("Quitando like de usuario'.");
					contadorLikes.textContent = Number(contadorLikes.textContent) - 1;
					eliminarValoracionTrue(idPost);
				} else if (valoracionExiste[0] && !valoracionExiste[1]) {
					// User has already rated the post negatively
					console.log("Quitando dislike de usuario");
					contadorDislikes.textContent = Number(contadorDislikes.textContent) - 1;
					contadorLikes.textContent = Number(contadorLikes.textContent) + 1;
					editarValoracion(idPost, true);
					// eliminarValoracionFalse(idPost);
					//Editar Valoración
				}
			})
			.catch((error) => {
				console.error(error);
			});
	});


}//-end valoracion like-

function valoracionDislike(idPost) {

	let contadorLikes = document.getElementById("like-contador");
	let contadorDislikes = document.getElementById("dislike-contador");

	$(".dislike").click(function (e) {
		e.preventDefault();
		comprobarExisteValoracion(idPost)
			.then((valoracionExiste) => {
				let formData = new FormData();
				if (!valoracionExiste[0]) {
					formData.append("idPost", idPost);
					formData.append("valor", false);
					// User has not yet rated the post				
					$.ajax("identificado/servicioWebValoracion/registrarValoracion", {
						type: "POST",
						data: formData,
						cache: false,
						contentType: false,
						processData: false,
						success: function (res) {
							console.log("Dislike registrado");
							contadorDislikes.textContent = Number(contadorDislikes.textContent) + 1;
						},
					});
				} else if (valoracionExiste[0] && !valoracionExiste[1]) {
					console.log("Quitando dislike de usuario'.");
					eliminarValoracionFalse(idPost);
					contadorDislikes.textContent = Number(contadorDislikes.textContent) - 1;
				} else if (valoracionExiste[0] && valoracionExiste[1]) {
					// User has already rated the post negatively
					console.log("Eliminando like y dando dislike.");
					editarValoracion(idPost, false);
					contadorLikes.textContent = Number(contadorLikes.textContent) - 1;
					contadorDislikes.textContent = Number(contadorDislikes.textContent) + 1;
				}
			})
			.catch((error) => {
				console.error(error);
			});
	});
}//-end valoracion dislike-

function verPostYComentarios() {

	$(".boton_ver_post").click(function (e) {

		let idPost = $(this).attr("id");
		$.ajax("identificado/servicioWebPosts/obtenerPostYComentariosPorId?idPost=" + idPost, {
			success: function (data) {

				alert("recibido: " + data);
				let postYComentarios = JSON.parse(data);
				let texto_html = "";
				texto_html = Mustache.render(plantillaListarPostYComentarios, postYComentarios);
				$("#contenedor").html(texto_html);

				//Cambiar iconos según la valoración del usuario
				let valor = postYComentarios.valoracion_usuario_sesion.valorUsuarioSesion;
				alert("El valor: " + valor);

				if (valor === "true") {
					$("#like-icon").removeClass("fa-regular fa-thumbs-up fa-xl").addClass("fa-solid fa-thumbs-up fa-xl");
				} else if (valor === "false") {
					$("#dislike-icon").removeClass("fa-regular fa-thumbs-down fa-xl").addClass("fa-solid fa-thumbs-down fa-xl");
				}

				//Comentario en post
				registrarComentarioPost();


				//Ver perfil de comentario
				verPerfilDeComentario();


				//Crear valoración y poner like
				valoracionLike(idPost);


				//Crear valoración y poner dislike
				valoracionDislike(idPost);



			}//---end success---


		});//--end ajax--


	});
}//-end ver Post y comentarios-

function busquedaForos() {

	//Buscador de Foros
	$(".boton_buscar").click(function (e) {
		let nombreForo = $("#inputBuscador").val();
		$.ajax("servicioWebForos/obtenerForosDeNombreIntroducido?nombreForo=" + nombreForo, {
			success: function (res) {
				let forosEncontrados = JSON.parse(res);
				let texto_html = "";
				texto_html = Mustache.render(plantillaListarForos, forosEncontrados);
				$("#contenedor").html(texto_html);

				//Ver post Foros
				verPostsDeForo();

				//Registrar foros
				registrarForo();

				//Buscar foros
				busquedaForos();

			}//---end success---


		});//--end ajax obtenerForosBuscados--

	});//--end click boton_buscar--
}//-end busqueda foros-

function busquedaFollowsPerfil() {

	//Buscador de Foros
	$(".boton_buscar").click(function (e) {
		let nombreForo = $("#inputBuscador").val();
		$.ajax("identificado/servicioWebForos/obtenerForosDeNombreIntroducidoPerfil?nombreForo=" + nombreForo, {
			success: function (res) {
				let forosEncontrados = JSON.parse(res);
				let texto_html = "";
				texto_html = Mustache.render(plantillaListarForosIdentificado, forosEncontrados);
				$("#contenedor").html(texto_html);

				//Ver post Foros
				verPostsDeForo();

				//Registrar foros
				registrarForo();

				//Buscar foros
				busquedaForos();

			}//---end success---


		});//--end ajax obtenerForosBuscados--

	});//--end click boton_buscar--
}//-end busqueda foros-

function listadoInicio() {

	$.ajax("servicioWebForos/obtenerForosYPosts", {
		success: function (data) {
			$("body").removeClass("cargando");
			let forosYPost = JSON.parse(data);
			let texto_html = "";
			texto_html = Mustache.render(plantillaHome,
				forosYPost);
			$("#contenedor").html(texto_html);

			//Ver post de foro
			verPostsDeForo();

			//Ver post y comentarios
			verPostYComentarios();

			//Registrar header
			$("#enlace_registrarme_header").click(mostrarRegistroUsuario);


		},
		//---end success ---	
	}); //---end ajax listado inicio ---



}//-end listado inicio-

function registrarForo() {
	//Registro		 
	$("#form_registro_foro").submit(function (e) {


		let nombre = $("#nombre").val();
		let descripcion = $("#descripcion").val();

		if (validarNombreForo(nombre) && validarDescripcionForo(descripcion)) {

			alert("todo ok, mandando informacion al servicio web...");

			//vamos a usar FormData para mandar el form al servicio web
			let formulario = document.forms[0];
			let formData = new FormData(formulario);

			$.ajax("identificado/servicioWebForos/registroForo", {
				type: "POST",
				data: formData,
				cache: false,
				contentType: false,
				processData: false,
				success: function (res) {
					if (res == "ok") {
						alert("registrado correctamente");

						$('#crearForoModal').modal("hide");
						obtener_listado_foros();
					} else {
						swal(res, "Foro no valido", "error");
						alert("Foro no valido");
					}
				}
			});
		}//end if validaciones
		e.preventDefault();

	});

}//-end registrar foro-

function registrarPost() {
	//Registrar Post
	$("#form_registro_post").submit(function (e) {

		let nombre = $("#nombre").val();
		let descripcion = $("#descripcion").val();

		idUsuario = "";
		let idForo = id;

		$.ajax("identificado/servicioWebUsuarios/obtenerUsuarioPorId", {
			success: function (data) {
				//alert("recibido: "+data);
				let info = JSON.parse(data);
				idUsuario = info.id;

				let formulario = document.forms[0];
				let formData = new FormData(formulario);
				formData.append('idForo', idForo);
				formData.append('idUsuario', idUsuario);


				$.ajax("identificado/servicioWebPosts/registrarPosts", {
					type: "POST",
					data: formData,
					cache: false,
					contentType: false,
					processData: false,
					success: function (res) {
						if (res == "ok") {
							$('#crearPostModal').modal("hide");
							obtener_listado_posts();
						} else {
							swal("Error de sintaxis en algun form del post", "Post no valido", "error");
							alert(res);
						}
					} //end Success Registrar Post
				}); //end Registrar Post

			}//end Success (Recogemos El id del usuario)
		});	//end ajax ObtenerUsuario

		e.preventDefault();

	});
}//-end registrar post-

function verPostsDeForo() {
	//Boton Ver Posts de Foro
	$(".boton_post_foro").click(function (e) {
		let id = $(this).attr("id");
		//ObtenerPostPorForoDeID
		$.ajax("servicioWebPosts/obtenerPostPorForoId?id=" + id, {
			success: function (data) {
				let posts = JSON.parse(data);
				let texto_html = Mustache.render(plantillaListarPosts, posts);
				$("#contenedor").html(texto_html);

				//Registrar post
				registrarPost();
				//Ver post y comentarios
				verPostYComentarios();

			}//---end success---
		});//--end ajax--

		e.preventDefault();

	});//--end obtener_listado-comentarios_post--
}//-end posts de foro-

function obtener_listado_foros() {
	$.ajax("servicioWebForos/obtenerForos", {
		success: function (data) {

			alert("recibido: " + data);
			let foros = JSON.parse(data);
			let texto_html = "";
			texto_html = Mustache.render(plantillaListarForos,
				foros);
			$("#contenedor").html(texto_html);

			//Ver Posts de Foro
			verPostsDeForo();

			// Buscar foros
			busquedaForos();

			//Registro de foros
			registrarForo();

		}//---end success---
	});//--end ajax--
}//-end obtener_listado-

function obtener_listado_foros_identificado() {
	$.ajax("identificado/servicioWebForos/obtenerForos", {
		success: function (data) {
			alert("recibido: " + data);
			let foros = JSON.parse(data);
			let texto_html = "";
			texto_html = Mustache.render(plantillaListarForosIdentificado,
				foros);
			$("#contenedor").html(texto_html);

			//Dar follow por el usuario
			follow();

			//Ver Posts de Foro
			verPostsDeForo();

			// Buscar foros
			busquedaForos();

			//Registro de foros
			registrarForo();


		}//---end success---
	});//--end ajax--
}//-end obtener_listado-

function listadoFollowsPerfil(){
	$(".boton_ver_follows").click(function () {
		$.ajax("identificado/servicioWebForos/obtenerForosPerfil", {
			success: function (data) {
				alert("recibido: " + data);
				let foros = JSON.parse(data);
				let texto_html = "";
				texto_html = Mustache.render(plantillaListarForosIdentificado,
					foros);
				$("#contenedor").html(texto_html);
	
				//Dar follow por el usuario
				follow();
	
				//Ver Posts de Foro
				verPostsDeForo();
	
				// Buscar foros
				busquedaForos();
	
				// Registro de foros
				registrarForo();
	
	
			}//---end success---
		});//--end ajax--
	});
}//-end listado follows perfil

function follow() {
	$(".follow").click(function () {
		let idForo = $(this).attr("id");
		comprobarExisteFollow(idForo)
			.then((followExiste) => {
				if (!followExiste) {
					//Dar follow si no hay
					darFollow(idForo);
				} else {
					//Si ya le dio a seguir, le quitamos el follow
					eliminarFollow(idForo);
				}
			});
	});
}//-end follow-

function comprobarExisteFollow(idForo) {
	console.log("Comprobar: "+idForo);
	return new Promise(function (resolve, reject) {
		$.ajax("identificado/servicioWebFollow/comprobarFollow?idForo=" + idForo, {
			success: function (data) {
				if (data.includes("ok, true")) {
					resolve(true);
				} else if (data.includes("ok, false")) {
					resolve(false);
				}
				console.log(data);
			},
			error: function () {
				reject(new Error("No se pudo obtener la follow"));
			}
		});
	});
}//-end comprobarExisteFollow

function darFollow(idForo) {
	//Se comprueba si hay follow previamente y se añade si no lo hay
	let formData = new FormData();
	formData.append("idForo", idForo);
	

	$.ajax("identificado/servicioWebFollow/registrarFollow", {
		type: "POST",
		data: formData,
		cache: false,
		contentType: false,
		processData: false,
		success: function (res) {
			console.log("Follow registrado");
		},
	}).fail(function () {
		swal("Ha fallado la funcion de dar follow", {
			icon: "error",
		});
	});
}//-end dar follow

function eliminarFollow(idForo) {
	//Se comprueba si hay follow previamente y se elimina si lo hay
	
	$.ajax("identificado/servicioWebFollow/eliminarFollow?idForo=" + idForo, {
		success: function (data) {
			if (data.includes("ok")) {
			}
		}//---end success---
	}).fail(function () {
		swal("Ha fallado la funcion de quitar follow", {
			icon: "error",
		});
	})//--end ajax--


} //-end eliminar follow-

function obtener_listado_posts() {

	$.ajax("servicioWebPosts/obtenerPosts", {
		success: function (data) {
			alert("recibido: " + data);
			let posts = JSON.parse(data);
			let texto_html = "";
			texto_html = Mustache.render(plantillaListarPostsPopulares, posts);
			$("#contenedor").html(texto_html);

			//Ver post y comentarios
			verPostYComentarios();

			//RegistrarPost
			registrarPost();



		}//---end success---
	});//--end ajax--
}//-end obtener_listado_posts-

function mostrarRegistroUsuario() {

	$("#contenedor").html(plantillaRegistrarUsuario);
	$("#form_registro_usuario").submit(function (e) {
		let nombre = $("#nombre").val();
		let email = $("#email").val();
		let pass = $("#pass").val();

		if (validarNombre(nombre)) {

			if (validarEmail(email)) {

				if (validarPass(pass)) {

					let formulario = document.forms[0];
					let formData = new FormData(formulario);
					$.ajax("servicioWebUsuarios/registrarUsuario", {
						type: "POST",
						data: formData,
						cache: false,
						contentType: false,
						processData: false,
						success: function (res) {
							if (res == "ok") {
								swal("El registro se ha realizado de forma correcta", "Realizado", "success");

							} else {
								swal("El registro se ha realizado de forma incorrecta", "Error", "error");
							}
						}
					});

				} else {
					swal("La cotrase&ntildea introducida no es correcto", "Error", "error");
				}//endIfPass

			} else {
				swal("El email introducido no es correcto", "Error", "error");
			}

		} else {
			swal("El nombre introducido no es correcto", "Error", "error");
		}


		e.preventDefault();
	});
}//-end registro usuario-

function mostrarIdentificacionUsuario() {

	$("#contenedor").html(plantillaLogin);


	//Comprobamos si hay cookies guardadas
	if (typeof (Cookies.get("email")) != "undefined") {
		$("#email").val(Cookies.get("email"));
	}
	if (typeof (Cookies.get("pass")) != "undefined") {
		$("#pass").val(Cookies.get("pass"));
	}

	$("#form_login").submit(function () {
		//Comprobamos hay una cuenta logeada previamente
		if (comprobarIdentificacion) {
			logout();
		}
		email = $("#email").val();
		pass = $("#pass").val();

		if (validarEmail(email)) {
			if (validarPass(pass)) {

				//Llamamos a la función ajax de login
				loginUsuario(email, pass);

			} else {
				swal("El formato de la contraseña no es valido", "Fallo", "error");
			}
			//endValidarPass
		} else {
			swal("El formato del email no es valido", "Fallo", "error");
		}
		//endValidarEmail
	});



}//-end identificacion usuario-

function loginUsuario(email, pass) {
	$.ajax("servicioWebUsuarios/loginUsuario", {
		data: "email=" + email + "&pass=" + pass,
		success: function (res) {
			if (res.includes("ok")) {
				nombre_login = res.split(",")[1];
				id_login = res.split(",")[2];

				$("#mensaje_login").text(res.split(",")[1]);
				swal("", "Sesión iniciada", "success");

				if ($("#recordar_datos").prop('checked')) {
					swal("Cookies sesion", "Datos guardados", "success");
					Cookies.set('email', email, { expires: 100 });
					Cookies.set('pass', pass, { expires: 100 });
				}

			} else {
				swal("", "Error al iniciar sesión", "error");
			}

		}
	});//end.ajax
}//-end loginUsuario-

function logout() {
	$.ajax("servicioWebUsuarios/logout", {
		success: function (res) {
			if (res == "ok") {
				swal("Sesi&aocuten cerrada", "Operaci&aocuten completada", "success");
				$("#mensaje_login").html("");
				setTimeout(() => { location.reload(); }, 2000);
			}
		}
	});
}//-end logout-

function editarUsuario() {
	//Boton Editar
	$(".boton_editar_usuario").click(function (e) {
		if (comprobarIdentificacion()) {
			$.ajax("identificado/servicioWebUsuarios/obtenerUsuarioPorId", {
				success: function (data) {
					alert("recibido: " + data);
					let info = JSON.parse(data);
					let texto_html = "";
					texto_html = Mustache.render(plantillaEditarUsuario, info);
					$("#contenedor").html(texto_html);

					//Form
					$("#form_editar_usuario").submit(function (e) {


						let nombre = $("#nombre").val();
						let email = $("#email").val();
						let descripcion = $("#descripcion").val();
						let pass = $("#pass").val();

						if (validarNombre(nombre)) {
							if (validarEmail(email)) {
								if (validarDescripcion(descripcion)) {
									if (validarPass(pass)) {

										let formulario = document.forms[0];
										let formData = new FormData(formulario);

										$.ajax("identificado/servicioWebUsuarios/editarUsuarioPorId", {
											type: "POST",
											data: formData,
											cache: false,
											contentType: false,
											processData: false,
											success: function (res) {

												caches.open("v1").then((cache) => {
													cache.delete("/images/" + idUsuario + ".jpg").then((response) => {
														someUIUpdateFunction();
														window.location.reload();
													});
												});

												caches.open("v1").then((cache) => {
													cache.delete("/images/" + idUsuario + ".png").then((response) => {
														someUIUpdateFunction();
														window.location.reload();
													});
												});
											}
										});

									} else {
										swal("La contraseña  es incorrecta", "Error", "error");
									}//end Pass

								} else {
									swal("La descripción es incorrecta", "Error", "error");
								}//end Descripcion

							} else {
								swal("El email es incorrecto", "Error", "error");
							}//end Descripcion

						} else {

						}//end Descripcion
					}); //end Submit Form

				}//end Success (Carga plantilla)

			});	//end ajax 
			e.preventDefault();
		}

	});//-end enlace editar
}//-end editar usuario-

function borrarUsuario() {
	//Boton Borrar usuario 
	$(".boton_borrar_usuario").click(function (e) {

		swal({
			title: "Est&aacutes seguro de que quieres eliminarlo?",
			text: "No se podr&aacute recuperar el usuario",
			icon: "warning",
			buttons: true,
			dangerMode: true,
		})
			.then((eliminarUsuario) => {
				if (eliminarUsuario) {
					$.ajax("identificado/servicioWebUsuarios/borrarUsuarioPorId", {
						success: function (data) {
							if (data.includes("ok")) {
								//Aviso de operacion
								swal("El usuario se ha eliminado", {
									icon: "success",
								})
								setTimeout(() => { mostrarIdentificacionUsuario(); }, 2000);
							}
						}//---end success---
					});//--end ajax--

				} else {
					perfil();
				}
			});


	});//end Borrar Usuario
} //-end borrar usuario-

function perfil() {
	$.ajax("identificado/servicioWebUsuarios/obtenerUsuarioPorId", {
		success: function (data) {
			alert("recibido: " + data);
			let info = JSON.parse(data);
			let texto_html = "";

			texto_html = Mustache.render(plantillaPerfil, info);
			$("#contenedor").html(texto_html);

			// Editar usuario
			editarUsuario();
			// Borrar usuario
			borrarUsuario();

			//Ver listado follows
			listadoFollowsPerfil();

		}//end success Obtener id

	});	//end ajax
}//-end perfil-

function eliminarValoracionFalse(idPost) {
	//Eliminar valoración
	//Se le llama y se le pasa la valoración previa realizada
	$(".like").click(

		$.ajax("identificado/servicioWebValoracion/eliminarValoracion?idPost=" + idPost, {
			success: function (data) {
				if (data.includes("ok")) {

				}
			}//---end success---
		}).fail(function () {
			swal("Ha fallado la funcion de quitar dislike", {
				icon: "error",
			});
		})//--end ajax--
	);
} //-end eliminar valoracion false-

function eliminarValoracionTrue(idPost) {
	//Eliminar valoración
	//Se le llama y se le pasa la valoración previa realizada
	$(".dislike").click(
		$.ajax("identificado/servicioWebValoracion/eliminarValoracion?idPost=" + idPost, {
			success: function (data) {
				if (data.includes("ok")) {
				}
			}//---end success---
		}).fail(function () {
			swal("Ha fallado la funcion de quitar like", {
				icon: "error",
			});
		})//--end ajax--
	);
} //-end eliminar valoracion true-

function comprobarExisteValoracion(idPost) {
	return new Promise(function (resolve, reject) {
		$.ajax("identificado/servicioWebValoracion/comprobarValoracion?idPost=" + idPost, {
			success: function (data) {
				if (data.includes("ok, true, true")) {
					resolve([true, true]);
				} else if (data.includes("ok, true, false")) {
					resolve([true, false]);
				} else if (data.includes("ok, false")) {
					resolve([false]);
				}
				console.log(data);
			},
			error: function () {
				reject(new Error("No se pudo obtener la valoración"));
			}
		});
	});
}//-end comprobar existe valoracion

function comprobarIdentificacion() {
	return new Promise(function (resolve, reject) {
		$.ajax("servicioWebUsuarios/comprobarLogin", {
			success: function (res) {
				if (res.includes("ok")) {
					resolve(true);
				} else {
					resolve(false)
				}
			}, error: function () {
				reject(new Error("No se pudo obtener la identificacion"));
			}

		});
	});
} // -end comprobar identificacion-

//Enlaces del navbar
$("#enlace_home").click(listadoInicio);

//El listado de foros cambia dependiendo de si está identificado o no 
$("#enlace_listado_foros").click(function () {
	comprobarIdentificacion().then((usuarioIdentificado) => {
		if (usuarioIdentificado === false) {
			//Dar follow si no hay 
			obtener_listado_foros();
		} else {
			//Si ya le dio a seguir, le quitamos el follow
			obtener_listado_foros_identificado();
		}
	}

	)
	.catch((error) => {
		swal("se ha producido un error en identificación", "Error","error");
	});
});//-end enlace listado foros

$("#enlace_listado_foros").click(obtener_listado_foros);
$("#enlace_listado_posts").click(obtener_listado_posts);
$("#enlace_editar_usuario").click(mostrarRegistroUsuario);
$("#enlace_registrarme").click(mostrarRegistroUsuario);
$("#enlace_identificarme").click(mostrarIdentificacionUsuario);
$("#enlace_logout").click(logout);
$("#enlace_perfil").click(perfil);





