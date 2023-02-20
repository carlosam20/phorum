$(".boton_post_foro").click(function (e) {
	let id = $(this).attr("id");

	//ObtenerPostPorForoDeID
	$.ajax("servicioWebPosts/obtenerPostPorForoId?id=" + id, {
		success: function (data) {
			let posts = JSON.parse(data);
			let texto_html = Mustache.render(plantillaListarPosts, posts);
			$("#contenedor").html(texto_html);


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

						//Cambiar iconos según la valoración del usuario
						let valor = postYComentarios.valoracion_usuario_sesion.valorUsuarioSesion;
						alert("El valor: " + valor);

						if (valor === "true") {
							$("#like-icon").removeClass("fa-regular fa-thumbs-up fa-xl").addClass("fa-solid fa-thumbs-up fa-xl");
						} else if (valor === "false") {
							$("#dislike-icon").removeClass("fa-regular fa-thumbs-down fa-xl").addClass("fa-solid fa-thumbs-down fa-xl");
						}

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
							e.preventDefault()
						});




						//Ver Perfil del usuario que comento
						$(".boton_ver_perfil").click(function (e) {

							let idUsuarioComentario = $(this).attr("id");

							$.ajax("identificado/servicioWebUsuarios/obtenerUsuarioComentarioPorId?idUsuarioComentario=" + idUsuarioComentario, {
								type: "GET",
								data: formData,
								cache: false,
								contentType: false,
								processData: false,
								success: function (res) {
									alert("recibido: " + data);
									let info = JSON.parse(data);
									let texto_html = "";

									texto_html = Mustache.render(plantillaPerfil, info);
									$("#contenedor").html(texto_html);
								}
							});

						});


						//Crear valoración y poner like

						$(".like").click(function (e) {
							e.preventDefault();

							comprobarExisteValoracion(idPost)
								.then((valoracionExiste) => {
									if (!valoracionExiste) {
										/*Eliminar valoracion del dislike para crear un like*/
										//Aqui se comprueba si existe una valoracion previa con valoracion = false
										//En el caso de que haya será eliminada previamente
										eliminarValoracionFalse(idPost);
										let formData = new FormData();
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
											},
										});
									} else {
										eliminarValoracionTrue(idPost);
									}
								})
								.catch((error) => {
									console.error(error);
								});
						});


						//Crear valoración y poner dislike
						$(".dislike").click(function (e) {

							e.preventDefault();

							comprobarExisteValoracion(idPost).then(
								(valoracionExiste) => {
									if (!valoracionExiste) {
										eliminarValoracionTrue(idPost);
										let idPost = $(this).attr("id");
										let formData = new FormData();
										formData.append("idPost", idPost);
										formData.append("valor", false);
										$.ajax("identificado/servicioWebValoracion/registrarValoracion", {
											type: "POST",
											data: formData,
											cache: false,
											contentType: false,
											processData: false,
											success: function (res) {
											}
										});

									} else {
										eliminarValoracionFalse(idPost);
									}

								}).catch((error) => {
									console.error(error);
								});
						});

						//Funciones de eliminacion de valoraciones



					}//---end success---


				});//--end ajax--


			});

		}//---end success---
	});//--end ajax--

	e.preventDefault();

});//--end obtener_listado-comentarios_post--