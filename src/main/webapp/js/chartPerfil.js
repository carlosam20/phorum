
//Crear valoración y poner like
$("#like").click(
	function (e) {
		let idUsuario = idUsuario;
		let idPost = $(this).attr("id");
		$.post("identificado/servicioWebComentarios/registroValoracion", {
			data: jQuery.param({ idUsuario: idUsuario, idPost: idPost }),
			cache: false,
			contentType: false,
			processData: false,
		},
			function (res) {

			}).fail(function () {
				console.log("error");
			});
		e.preventDefault();
	});


