
//Crear valoración y poner like
//Like pasa a ser filled
$("#like").click(
	function (e) {
		let idPost = $(this).attr("id");
		//Tenemos que recoger el usuario con el metodo
		$.post("identificado/servicioWebComentarios/registroValoracion", {
			data: jQuery.param({ idPost: idPost, valoracion: true }),
			cache: false,
			contentType: false,
			processData: false,
		},
			function (res) {
			}).fail(function () {
				swal("Ha fallado la funcion de dar dislike", {
					icon: "error",
				});
			});
		e.preventDefault();
	}
);


//Crear valoración y poner dislike
//Dislike pasa a ser filled
$("#dislike").click(
	function (e) {
		let idPost = $(this).attr("id");
		$.post("identificado/servicioWebValoracion/registroValoracion", {
			data: jQuery.param({ idPost: idPost, valoracion: false }),
			cache: false,
			contentType: false,
			processData: false,
		},
			function (res) {
				//Incrementar el valor de dislike temporalmente

			}).fail(function () {
				swal("Ha fallado la funcion de dar dislike", {
					icon: "error",
				});
			});
		e.preventDefault();
	}
);

//Eliminar valoración
//Hay llamar a esta funcion de ajax cuando el like o el dislike deje de estar pulsado
//Se comprobara el id y el estado del botón o del checkbox
$("#dislike").click(
	
	$.ajax("identificado/servicioWebValoracion/eliminarValoracion", {
		success: function (data) {
			if (data.includes("ok")) {
			}
			
		}//---end success---
	}).fail(function () {
		swal("Ha fallado la funcion de quitar dislike o like", {
			icon: "error",
		});
	})//--end ajax--
);






