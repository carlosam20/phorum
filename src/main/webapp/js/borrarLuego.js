function follow(){
    $(".follow").click(function (e) {
        e.preventDefault();
        comprobarExisteFollow(idForo)
            .then((followExiste) => {
                if (followExiste === false) {
                    //Dar follow si no hay 
                    darFollow(idForo)
                } else {
                    //Si ya le dio a seguir, le quitamos el follow
                    quitarFollow(idForo);
                }
            })
            .catch((error) => {
                console.error(error);
            });
    });
}//-end follow-


function comprobarExisteFollow(idForo) {
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

function darFollow(idForo){
    //Se comprueba si hay follow previamente y se añade si no lo hay
    let formData = new FormData();
    formData.append("idPost", idPost);
    $.ajax("identificado/servicioWebFollow/registrarFollow", {
        type: "POST",
        data: formData,
        cache: false,
        contentType: false,
        processData: false,
        success: function (res) {
            console.log("Follow registrado");
        },
    }).fail(function (){
        swal("Ha fallado la funcion de dar follow", {
            icon: "error",
        });
    });
}


function eliminarFollow(idForo) {
        //Se comprueba si hay follow previamente y se elimina si lo hay
		$.ajax("identificado/servicioWebValoracion/eliminarFollow?idForo=" + idPost, {
			success: function (data) {
				if (data.includes("ok")) {
				}
			}//---end success---
		}).fail(function () {
			swal("Ha fallado la funcion de quitar follow", {
				icon: "error",
			});
		})//--end ajax--


} //-end eliminar valoracion true-