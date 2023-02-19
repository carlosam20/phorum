$(".dislike").click(function (e) {

	e.preventDefault();

	comprobarExisteValoracion(idPost).then(
		(valoracionExiste) =>{
			if(!valoracionExiste){
				let idPost = $(this).attr("id");
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
					}
				});
				
			} else {
				eliminarValoracionFalse(idPost);
			}
			
		}).catch((error) => {
			console.error(error);
		});
	});
	
