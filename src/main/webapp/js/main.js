
//Variables
let email = "";
let pass = "";
let nombre_login = "";

//Carga de plantillas en variables
let plantillaHome = "";
let plantillaListarForos = "";
let plantillaListarPostYComentarios = "";
let plantillaListarForosBusqueda = "";
let plantillaForo = "";
let plantillaLogin = "";
let plantillaCategoria = "";
let plantillaRegistrarUsuario = "";
let plantillaIdentificarUsuario = "";
let plantillaEditarUsuario = "";
let plantillaPerfil = "";


//URL
const URL = window.location.href;




//Metodos OnInit
cargar_plantillas_del_servidor();
listadoInicio();

let numberOfEntries = window.history.length;


function listadoInicio() {

	$.ajax("servicioWebForos/obtenerForosYPosts", {
		success: function (data) {
			$('body').removeClass('cargando');
			console.log(numberOfEntries);
			let forosYPost = JSON.parse(data);
			let texto_html = "";
			texto_html = Mustache.render(plantillaHome,
				forosYPost);
			$("#contenedor").html(texto_html);


		}//---end success ---
	}); //---end ajax ---
}


function busquedaForos() {
	//Buscador de Foros
	$(".boton_buscar").click(function (e) {
		let nombreForo = $("#inputBuscador").val();
		$.ajax("servicioWebForos/obtenerForosDeNombreIntroducido?nombreForo=" + nombreForo, {
			success: function (res) {
				let forosEncontrados = JSON.parse(res);
				let texto_html = "";
				texto_html = Mustache.render(plantillaListarForosBusqueda, forosEncontrados);
				$("#contenedor").html(texto_html);
				busquedaForos();
			}//---end success---

		});//--end ajax obtenerForosBuscados--

	});//--end click boton_buscar--
}

function obtener_listado_foros() {

	$.ajax("servicioWebForos/obtenerForos", {
		success: function (data) {
			console.log(numberOfEntries);
			alert("recibido: " + data);
			let foros = JSON.parse(data);
			let texto_html = "";
			texto_html = Mustache.render(plantillaListarForos,
				foros);
			$("#contenedor").html(texto_html);

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


			//Boton Ver Posts de Foro	
			$(".boton_post_foro").click(function (e) {
				let id = $(this).attr("id");
				console.log(numberOfEntries);

				$.ajax("servicioWebPosts/obtenerPostPorForoId?id=" + id, {
					success: function (data) {
						let posts = JSON.parse(data);
						let texto_html = Mustache.render(plantillaListarPosts, posts);
						$("#contenedor").html(texto_html);


						//Registrar Post
						$("#form_registro_post").submit(function (e) {
							let nombre = $("#nombre").val();
							let descripcion = $("#descripcion").val();

							let idUsuario = "";
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
												$('#crearPostModal').modal('hide');
												location.reload();
												// obtener_listado_posts();
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

						//Boton Ver Post y Comentarios 
						$(".boton_ver_post").click(function (e) {
							let idPost = $(this).attr("id");
							

							$.ajax("identificado/servicioWebPosts/obtenerPostYComentariosPorId?idPost=" + idPost, {
								success: function (data) {
									alert("recibido: " + data);
									let postYComentarios = JSON.parse(data);
									let texto_html = "";
									texto_html = Mustache.render(plantillaListarPostYComentarios, postYComentarios);
									$("#contenedor").html(texto_html);

								}//---end success---
							});//--end ajax--

						});
					}//---end success---
				});//--end ajax--

				e.preventDefault();

			});//--end obtener_listado-comentarios_post--

			// Buscar foros
			busquedaForos();



		}//---end success---
	});//--end ajax--





}//-end obtener_listado-




function obtener_listado_posts() {

	$.ajax("servicioWebPosts/obtenerPosts", {
		success: function (data) {
			alert("recibido: " + data);
			let posts = JSON.parse(data);
			let texto_html = "";
			texto_html = Mustache.render(plantillaListarPostsPopulares, posts);
			$("#contenedor").html(texto_html);

			//Ver post y comentarios
			$(".boton_ver_post").click(function (e) {
				let idPost = $(this).attr("id");
				alert("pedir al servidor post id:" + idPost);

				$.ajax("identificado/servicioWebPosts/obtenerPostYComentariosPorId?idPost=" + idPost, {
					success: function (data) {
						alert("recibido: " + data);
						let postYComentarios = JSON.parse(data);
						let texto_html = "";
						texto_html = Mustache.render(plantillaListarPostYComentarios, postYComentarios);
						$("#contenedor").html(texto_html);

					}//---end success---
				});//--end ajax--

			});



		}//---end success---
	});//--end ajax--
}//-end obtener_listado-


function mostrarRegistroComentario() {

	$("#contenedor").html(plantillaRegistrarPost);
	$("#form_registro_comentarios").submit(function (e) {
		let comentario = $("#textoComentario").val();

		if (validarNombre(nombre)) {
			//vamos a usar FormData para mandar el form al servicio web
			let formulario = document.forms[0];
			let formData = new FormData(formulario);
			$.ajax("identificado/servicioWebComentarios/registroComentario", {
				type: "POST",
				data: formData,
				cache: false,
				contentType: false,
				processData: false,
				success: function (res) {
					if (res == "ok") {
						obtenerPostsForo();
					} else {
						alert(res);
						swal("Error en el registro de comentario", "Comentario no valido", "error");
					}
				}
			});

		}//end if validaciones
		e.preventDefault();
	});
}


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
								setTimeOut(mostrarIdentificacionUsuario(), 4000);


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
}


function mostrarIdentificacionUsuario() {

	$("#contenedor").html(plantillaLogin);

	if (typeof (Cookies.get("email")) != "undefined") {
		$("#email").val(Cookies.get("email"));
	}
	if (typeof (Cookies.get("pass")) != "undefined") {
		$("#pass").val(Cookies.get("pass"));
	}


	$("#form_login").submit(function () {

		email = $("#email").val();
		pass = $("#pass").val();

		if (validarEmail(email)) {

			if (validarPass(pass)) {

				$.ajax("servicioWebUsuarios/loginUsuario", {
					data: "email=" + email + "&pass=" + pass,
					success: function (res) {
						if (res.split(",")[0] == "ok") {
							nombre_login = res.split(",")[1];
							id_login = res.split(",")[2];


							if ($("#recordar_datos").prop('checked')) {
								alert("guardar datos en cookie");
								Cookies.set('email', email, { expires: 100 });
								Cookies.set('pass', pass, { expires: 100 });
							}
							$("#mensaje_login").html(nombre_login);


						} else {
							alert(res);
						}
					}
				});//end.ajax

			} else {
				swal("El formato de la contraseña no es valido", "Fallo", "error");
			}
			//endValidarPass
		} else {
			swal("El formato del email no es valido", "Fallo", "error");
		}
		//endValidarEmail
		res.preventDefault();
	});
}

function logout() {

	$.ajax("servicioWebUsuarios/logout", {
		success: function (res) {
			if (res == "ok") {
				swal("", "Sesi&oacuten cerrada", "info");
				location.reload();
			}
		}
	});
}

function perfil() {


	$.ajax("identificado/servicioWebUsuarios/obtenerUsuarioPorId", {
		success: function (data) {
			alert("recibido: " + data);
			let info = JSON.parse(data);
			let texto_html = "";

			texto_html = Mustache.render(plantillaPerfil, info);
			$("#contenedor").html(texto_html);

			//Boton Editar
			$(".boton_editar_usuario").click(function (e) {

				$.ajax("identificado/servicioWebUsuarios/obtenerUsuarioPorId", {
					url: URL + "/perfil/",
					success: function (data) {
						alert("recibido: " + data);
						let info = JSON.parse(data);
						let texto_html = "";
						texto_html = Mustache.render(plantillaEditarUsuario, info);
						$("#contenedor").html(texto_html);

						//Form
						$("#form_editar_usuario").submit(function (e) {

							//letiables form
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
								swal("El nombre es incorrecto", "Error", "error");
							}//end Descripcion

						}); //end Submit Form
						e.preventDefault();


					}//end Success (Carga plantilla)

				});	//end ajax 
				e.preventDefault();
			});//-end enlace editar


			//Boton Borrar usuario 
			$(".boton_borrar_usuario").click(function (e) {
				$.ajax("identificado/servicioWebUsuarios/borrarUsuarioPorId", {
					success: function (data) {
						alert("recibido: " + data);
						if (data.contains("ok")) {
							logout();
							// listadoInicio();
						}
					}//---end success---
				});//--end ajax--

			});//end Borrar Usuario


		}//end success Obtener id

	});	//end ajax

}//end perfil


$(document).ready(function () {
	$('body').addClass('cargando');
});


$("#enlace_home").click(listadoInicio);
$("#enlace_listado_foros").click(obtener_listado_foros);
$("#enlace_listado_posts").click(obtener_listado_posts);
$("#enlace_editar_usuario").click(mostrarRegistroUsuario);
$("#enlace_registrarme").click(mostrarRegistroUsuario);
$("#enlace_identificarme").click(mostrarIdentificacionUsuario);
$("#enlace_logout").click(logout);
$("#enlace_perfil").click(perfil);


//Si estoy identificado
function comprobarIdentificacion() {
	$.ajax("servicioWebUsuarios/comprobarLogin", {
		success: function (res) {
			if (res.includes('ok')) {
				return true;
			} else {
				return false;
			}
		}
	});
}

function obtenerUsuario() {
	$.ajax("servicioWebUsuarios/obtenerUsuario", {
		success: function (res) {
			if (res.split(",")[0] == "ok") {
				nombre_login = res.split(",")[1];
				$("#mensaje_login").html(" Perfil: " + nombre_login + " ");
			} else {
				$("#mensaje_login").html("( no estas identificado )");
			}
		}
	});
}


