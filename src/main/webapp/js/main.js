
// Variables globales de usuario
let email = "";
let pass = "";
const idUsuario = "";
let nombreLogin = "";

// URLS
const urlForos = "foros";
const urlPerfil = "perfil";
const urlListadoFollows = "listadoFollows";
const urlForosIdentificado = "forosIdentificados";
const urlPosts = "listadoPostsPopulares";
const urlPostsForo = "listadoPosts";
const urlPostYComentarios = "postYcomentarios";
const urlEditarPerfil = "editarPerfil";
const urlLogin = "login";
const urlRegistrar = "registrar";
const baseURL = "http://localhost:8080/phorum/";

// Se inician al principio
// Call the function using the global variable

let isHandlingStateChange = false;
let popstateFlag = false;
const handleStateChange = (state) => {
  if (popstateFlag) return;
  popstateFlag = true;
  if (isHandlingStateChange) {
    return;
  }
  isHandlingStateChange = true;
  if (state && state.url && state.textoHtml) {
    if (state.url === baseURL + urlPerfil) {
      $("#contenedor").html(state.textoHtml);
    } else if (state.url === baseURL) {
      console.log("Handle Inicio");
      $("#contenedor").html(state.textoHtml);
    } else if (state.url === baseURL + urlPosts) {
      console.log("Handle Posts");
      $("#contenedor").html(state.textoHtml);
    } else if (state.url === baseURL + urlLogin) {
      $("#contenedor").html(state.textoHtml);
    } else if (state.url === baseURL + urlRegistrar) {
      $("#contenedor").html(state.textoHtml);
    } else if (state.url === baseURL + urlForos) {
      console.log("Handle Foros");
      $("#contenedor").html(state.textoHtml);
    } else if (state.url === baseURL + urlPostYComentarios) {
      $("#contenedor").html(state.textoHtml);
    } else if (state.url === baseURL + urlPostsForo) {
      $("#contenedor").html(state.textoHtml);
    } else if (state.url === baseURL + urlListadoFollows) {
      $("#contenedor").html(state.textoHtml);
    }
    setTimeout(() => {
      popstateFlag = false;
    }, 100);
    isHandlingStateChange = false;
  }
};

let nWindows = 0;
window.addEventListener("popstate", function (e) {
  if (e.state !== null) {
    nWindows = nWindows + 1;
    console.log("State no es null:" + nWindows);
    handleStateChange(e.state);
  }
});
// Cargamos el home
const listadoInicio = () => {
  $.ajax("servicioWebForos/obtenerForosYPosts", {
    success: (data) => {
      const forosYPost = JSON.parse(data);
      let textoHtml = "";
      textoHtml = Mustache.render(plantillaHome, forosYPost);
      $("#contenedor").html(textoHtml);

      const stateObj = {
        url: baseURL,
        textoHtml: Mustache.render(plantillaHome, forosYPost, () => {
          // Ver post de foro
          verPostsDeForo();

          // Ver post y comentarios
          verPostYComentarios();
        })
      };
      window.history.replaceState(stateObj, baseURL, baseURL);
    },
    error: () => {
      swal("", "Error en el listado de inicio", "Error");
    },
    complete: () => {
      // Ver post de foro
      verPostsDeForo();

      // Ver post y comentarios
      verPostYComentarios();
    }
  });
};
cargarPlantillasDelServidor();
listadoInicio();

const registrarComentarioPost = () => {
  // Realizar comentario en post
  $(".form_registro_comentario").submit(function (e) {
    const idPost = $(this).attr("id");
    const formulario = document.forms[0];
    const formData = new FormData(formulario);
    console.log(idPost);
    $.ajax(
      "identificado/servicioWebComentarios/registrarComentario?idPost=" +
      idPost,
      {
        type: "POST",
        data: formData,
        cache: false,
        contentType: false,
        processData: false,
        success: function (res) {
          if (res === "ok") {
            window.location.reload();
          }
        }, // end Success Registrar Comentario
        error: (res) => {
          swal("", "Error al registrar comentario", "error");
        }// end Error
      }
    ); // end Registrar Comentarios
    e.preventDefault();
  });
}; // -end registrar comentario post-

const verPerfilDeComentario = () => {
  // Ver Perfil del usuario que comento
  $(".boton_ver_perfil").click((e) => {
    const idUsuarioComentario = $(e.currentTarget).attr("id");
    $.ajax(
      "identificado/servicioWebUsuarios/obtenerUsuarioComentarioPorId?idUsuarioComentario=" +
      idUsuarioComentario,
      {
        type: "GET",
        success: function (res) {
          alert("recibido: " + res);
          const info = JSON.parse(res);
          let textoHtml = "";
          textoHtml = Mustache.render(plantillaPerfilUsuarioComentario, info);
          $("#contenedor").html(textoHtml);
        },
        error: (res) => {
          swal(res, "Error en ver perfil comentario", "error");
        }
      }
    );
    e.preventDefault();
  });
}; // -end ver perfil de comentario-

const editarValoracion = (idPost, valor) => {
  const formData = new FormData();
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
    }
  });
}; // -end editarValoracion-

const valoracionLike = (idPost) => {
  const contadorLikes = document.getElementById("like-contador");
  const contadorDislikes = document.getElementById("dislike-contador");

  $(".like").click(function (e) {
    e.preventDefault();
    comprobarExisteValoracion(idPost)
      .then((valoracionExiste) => {
        const formData = new FormData();

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
            success: (e) => {
              console.log("Like registrado");
              contadorLikes.textContent = Number(contadorLikes.textContent) + 1;
              e.preventDefault();
            }
          });
        } else if (valoracionExiste[0] && valoracionExiste[1]) {
          console.log("Quitando like de usuario'.");
          contadorLikes.textContent = Number(contadorLikes.textContent) - 1;
          eliminarValoracionTrue(idPost);
        } else if (valoracionExiste[0] && !valoracionExiste[1]) {
          // User has already rated the post negatively
          console.log("Quitando dislike de usuario");
          contadorDislikes.textContent =
            Number(contadorDislikes.textContent) - 1;
          contadorLikes.textContent = Number(contadorLikes.textContent) + 1;
          editarValoracion(idPost, true);
          // eliminarValoracionFalse(idPost);
          // Editar Valoración
        }
      })
      .catch((error) => {
        console.error(error);
      });
  });
}; // -end valoracion like-

const valoracionDislike = (idPost) => {
  const contadorLikes = document.getElementById("like-contador");
  const contadorDislikes = document.getElementById("dislike-contador");

  $(".dislike").click(function (e) {
    e.preventDefault();
    comprobarExisteValoracion(idPost)
      .then((valoracionExiste) => {
        const formData = new FormData();
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
            success: function () {
              contadorDislikes.textContent =
                Number(contadorDislikes.textContent) + 1;
            }
          });
        } else if (valoracionExiste[0] && !valoracionExiste[1]) {
          eliminarValoracionFalse(idPost);
          contadorDislikes.textContent =
            Number(contadorDislikes.textContent) - 1;
        } else if (valoracionExiste[0] && valoracionExiste[1]) {
          // User has already rated the post negatively

          editarValoracion(idPost, false);
          contadorLikes.textContent = Number(contadorLikes.textContent) - 1;
          contadorDislikes.textContent =
            Number(contadorDislikes.textContent) + 1;
        }
      })
      .catch((error) => {
        console.error(error);
      });
  });
}; // -end valoracion dislike-

const verPostYComentarios = () => {
  $(".boton_ver_post").click(function (e) {
    const idPost = $(this).attr("id");
    $.ajax("identificado/servicioWebPosts/obtenerPostYComentariosPorId?idPost=" + idPost, {
      success: (data) => {
        alert("recibido: " + data);
        const postYComentarios = JSON.parse(data);
        let textoHtml = "";
        textoHtml = Mustache.render(
          plantillaListarPostYComentarios,
          postYComentarios
        );
        $("#contenedor").html(textoHtml);

        // Cambiar iconos según la valoración del usuario
        const valor = postYComentarios.valoracion_usuario_sesion.valorUsuarioSesion;

        if (valor === "true") {
          $("#like-icon")
            .removeClass("fa-regular fa-thumbs-up fa-xl")
            .addClass("fa-solid fa-thumbs-up fa-xl");
        } else if (valor === "false") {
          $("#dislike-icon")
            .removeClass("fa-regular fa-thumbs-down fa-xl")
            .addClass("fa-solid fa-thumbs-down fa-xl");
        }
        const stateObj = {
          url: baseURL + urlPostYComentarios,
          textoHtml:
            Mustache.render(
              plantillaListarPostYComentarios,
              postYComentarios
            )
        };
        window.history.pushState(stateObj, urlPostYComentarios, baseURL + urlPostYComentarios);
      },
      complete: () => {
        // Comentario en post
        registrarComentarioPost();

        // Ver perfil de comentario
        verPerfilDeComentario();

        // Crear valoración y poner like
        valoracionLike(idPost);

        // Crear valoración y poner dislike
        valoracionDislike(idPost);
      },
      error: () => {
        swal("", "Error cargando post y comentarios", "error");
      }
    }
    ); // --end ajax--
    e.preventDefault();
  });
};// -end ver Post y comentarios-

const busquedaForos = () => {
  // Buscador de Foros
  $(".boton_buscar").click((e) => {
    const nombreForo = $("#inputBuscador").val();
    $.ajax(
      "servicioWebForos/obtenerForosDeNombreIntroducido?nombreForo=" +
      nombreForo,
      {
        success: function (res) {
          const forosEncontrados = JSON.parse(res);
          let textoHtml = "";
          textoHtml = Mustache.render(plantillaListarForos, forosEncontrados);
          $("#contenedor").html(textoHtml);
        },
        complete: () => {
          // Ver post Foros
          verPostsDeForo();

          // Registrar foros
          registrarForo();

          // Buscar foros
          busquedaForos();
        },
        error: () => {
          swal("", "Error en busqueda", "error");
        } // ---end success---
      }
    ); // --end ajax obtenerForosBuscados--
    e.preventDefault();
  }); // --end click boton_buscar--
}; // -end busqueda foros-

const busquedaFollowsPerfil = () => {
  // Buscador de Foros
  $(".boton_buscar").click((e) => {
    const nombreForo = $("#inputBuscador").val();
    $.ajax(
      "identificado/servicioWebForos/obtenerForosDeNombreIntroducidoPerfil?nombreForo=" +
      nombreForo,
      {
        success: function (res) {
          const forosEncontrados = JSON.parse(res);
          let textoHtml = "";
          textoHtml = Mustache.render(
            plantillaListarForosIdentificado,
            forosEncontrados
          );
          $("#contenedor").html(textoHtml);
        },
        complete: () => {
          // Ver post Foros
          verPostsDeForo();

          // Registrar foros
          registrarForo();

          // Buscar foros
          busquedaForos();
        },
        error: () => {
          swal("", "Error en busqueda de follows de perfil", "error");
        } // ---end success---
      }
    ); // --end ajax obtenerForosBuscados--
    e.preventDefault();
  }); // --end click boton_buscar--
}; // -end busqueda foros-

const registrarForo = () => {
  // Registro
  $("#form_registro_foro").submit(function (e) {
    const nombre = $("#nombre").val();
    const descripcion = $("#descripcion").val();

    if (validarNombreForo(nombre) && validarForoDescripcion(descripcion)) {
      // vamos a usar FormData para mandar el form al servicio web
      const formulario = document.forms[0];
      const formData = new FormData(formulario);

      $.ajax("identificado/servicioWebForos/registroForo", {
        type: "POST",
        data: formData,
        cache: false,
        contentType: false,
        processData: false,
        success: function (res) {
          if (res === "ok") {
            swal("", "Se ha creado correctamente", "success");
            $("#crearForoModal").modal("hide");
          }
        },
        error: (res) => {
          swal(res, "Error al registrar", "error");
        },
        complete: (res) => {
          if (res === "ok") {
            obtenerListadoForosIdentificado();
          }
        }
      });
    } // end if validaciones
    e.preventDefault();
  });
}; // -end registrar foro-

const verPostsDeForo = () => {
  // Boton Ver Posts de Foro
  $(".boton_post_foro").click(function (e) {
    const id = $(this).attr("id");
    // ObtenerPostPorForoDeID
    $.ajax("servicioWebPosts/obtenerPostPorForoId?id=" + id, {
      success: function (data) {
        const posts = JSON.parse(data);
        const textoHtml = Mustache.render(plantillaListarPosts, posts);
        $("#contenedor").html(textoHtml);
        const stateObj = {
          url: baseURL + urlPostsForo,
          textoHtml:
            Mustache.render(plantillaListarPosts, posts)
        };
        window.history.pushState(stateObj, urlPostsForo, baseURL + urlPostsForo);
        // Registrar post
        registrarPost();
        // Ver post y comentarios
        verPostYComentarios(id);
      } // ---end success---
    }); // --end ajax--

    e.preventDefault();
  }); // --end obtener_listado-comentarios_post--
}; // -end posts de foro-

const obtenerListadoForos = () => {
  $.ajax("servicioWebForos/obtenerForos", {
    url: baseURL + urlForos,
    success: function (data) {
      const foros = JSON.parse(data);
      let textoHtml = "";
      textoHtml = Mustache.render(plantillaListarForos, foros);
      $("#contenedor").html(textoHtml);

      const stateObj = {
        url: baseURL + urlForos,
        textoHtml: textoHtml = Mustache.render(plantillaListarForos, foros)
      };
      window.history.pushState(stateObj, urlForos, baseURL + urlForos);

      // Ver Posts de Foro
      verPostsDeForo(urlForos);

      // Buscar foros
      busquedaForos(urlForos);

      // Registro de foros
      registrarForo(urlForos);
    } // ---end success---
  }); // --end ajax--
}; // -end obtener_listado-

const obtenerListadoForosIdentificado = () => {
  $.ajax("identificado/servicioWebForos/obtenerForos", {
    success: function (data) {
      const foros = JSON.parse(data);
      let textoHtml = "";
      textoHtml = Mustache.render(plantillaListarForosIdentificado, foros);
      $("#contenedor").html(textoHtml);
      const stateObj = {
        url: baseURL + urlForosIdentificado,
        textoHtml: Mustache.render(plantillaListarForosIdentificado, foros)
      };
      window.history.pushState(stateObj, urlForosIdentificado, baseURL + urlForosIdentificado);
      // Dar follow por el usuario
      follow();

      // Ver Posts de Foro
      verPostsDeForo();

      // Buscar foros
      busquedaForos();

      // Registro de foros
      registrarForo();
    } // ---end success---
  }); // --end ajax--
}; // -end obtener_listado-

const listadoFollowsPerfil = () => {
  $(".boton_ver_follows").click(function () {
    $.ajax("identificado/servicioWebForos/obtenerForosPerfil", {
      success: function (data) {
        alert("recibido: " + data);
        const foros = JSON.parse(data);
        let textoHtml = "";
        textoHtml = Mustache.render(plantillaListarForosIdentificado, foros);
        $("#contenedor").html(textoHtml);
        const stateObj = { url: baseURL + urlListadoFollows };
        window.history.pushState(stateObj, urlListadoFollows, baseURL + urlListadoFollows);
        // Dar follow por el usuario
        follow();

        // Ver Posts de Foro
        verPostsDeForo();

        // Buscar foros
        busquedaFollowsPerfil();

        // Registro de foros
        registrarForo();
      },
      error: (data) => {
        swal(data, "Error en listado follows de perfil", "error");
      } // ---end success---
    }); // --end ajax--
  });
}; // -end listado follows perfil

const follow = () => {
  $(".follow").click(function () {
    const idForo = $(this).attr("id");
    comprobarExisteFollow(idForo).then((followExiste) => {
      if (!followExiste) {
        // Dar follow si no hay
        darFollow(idForo);
      } else {
        // Si ya le dio a seguir, le quitamos el follow
        eliminarFollow(idForo);
      }
    });
  });
}; // -end follow-

const comprobarExisteFollow = (idForo) => {
  console.log("Comprobar: " + idForo);
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
}; // -end comprobarExisteFollow

const darFollow = (idForo) => {
  // Se comprueba si hay follow previamente y se añade si no lo hay
  const formData = new FormData();
  formData.append("idForo", idForo);

  $.ajax("identificado/servicioWebFollow/registrarFollow", {
    type: "POST",
    data: formData,
    cache: false,
    contentType: false,
    processData: false
  })
    .done(() => {
      console.log("Follow registrado");
    })
    .fail(function () {
      swal("Ha fallado la funcion de dar follow", {
        icon: "error"
      });
    });
}; // -end dar follow

const eliminarFollow = (idForo) => {
  // Se comprueba si hay follow previamente y se elimina si lo hay
  $.ajax("identificado/servicioWebFollow/eliminarFollow?idForo=" + idForo).fail(
    swal("Ha fallado la funcion de quitar follow", "Error", "error")
  ); // --end ajax--
}; // -end eliminar follow-

const obtenerListadoPostsPopulares = () => {
  $.ajax("servicioWebPosts/obtenerPosts", {
    success: (data) => {
      const posts = JSON.parse(data);
      let textoHtml = "";
      textoHtml = Mustache.render(plantillaListarPostsPopulares, posts);
      $("#contenedor").html(textoHtml);

      const stateObj = {
        url: baseURL + urlPosts,
        textoHtml: Mustache.render(plantillaListarPostsPopulares, posts)
      };

      window.history.pushState(stateObj, urlPosts, baseURL + urlPosts);

      verPostYComentarios();
      registrarPost();
    },
    error: () => {
      swal("No ha cargado listado posts", "Error", "error");
    }
  });
}; // -end obtener listado posts-

const registrarPost = () => {
  $("#form_registro_post").submit(function (e) {
    const nombre = $("#nombre").val();
    const descripcion = $("#descripcion").val();
    const idForo = $(".enlacePost").attr("id");

    // Testeamos los campos antes de pasarlos

    console.log(idForo + " " + nombre + " " + descripcion + " ");
    const formulario = document.forms[0];
    const formData = new FormData(formulario);
    let respuestaError;
    formData.append("idForo", idForo);

    $.ajax("identificado/servicioWebPosts/registrarPosts", {
      type: "POST",
      data: formData,
      cache: false,
      contentType: false,
      processData: false,
      success: function (res) {
        if (res === "ok") {
          alert("se ha metido en el registro");
          respuestaError = res;
        }
      } // end Success Registrar Post
    })
      .fail(swal(respuestaError, "Error", "error"))
      .then($("#crearPostModal").modal("hide"))
      .then(obtenerListadoPostsPopulares());
    // end Registrar Post
    e.preventDefault();
  });
}; // -end registrar post-

const mostrarRegistroUsuario = () => {
  $("#contenedor").html(plantillaRegistrarUsuario);
  const stateObj = {
    url: baseURL + urlRegistrar,
    textoHtml: Mustache.render(plantillaRegistrarUsuario)
  };
  window.history.pushState(stateObj, urlRegistrar, baseURL + urlRegistrar);
  $("#form_registro_usuario").submit(function (e) {
    const nombre = $("#nombre").val();
    const email = $("#email").val();
    const pass = $("#pass").val();

    if (validarNombre(nombre)) {
      if (validarEmail(email)) {
        if (validarPass(pass)) {
          const formulario = document.forms[0];
          const formData = new FormData(formulario);
          $.ajax("servicioWebUsuarios/registrarUsuario", {
            type: "POST",
            data: formData,
            cache: false,
            contentType: false,
            processData: false,
            success: function (res) {
              if (res === "ok") {
                swal(
                  "El registro se ha realizado de forma correcta",
                  "Realizado",
                  "success"
                );
              } else {
                swal(
                  "El registro se ha realizado de forma incorrecta",
                  "Error",
                  "error"
                );
              }
            }
          });
        } else {
          swal(
            "La cotrase&ntildea introducida no es correcto",
            "Error",
            "error"
          );
        } // endIfPass
      } else {
        swal("El email introducido no es correcto", "Error", "error");
      }
    } else {
      swal("El nombre introducido no es correcto", "Error", "error");
    }

    e.preventDefault();
  });
}; // -end registro usuario-

const mostrarIdentificacionUsuario = () => {
  $("#contenedor").html(plantillaLogin);

  const stateObj = {
    url: baseURL + urlLogin,
    textoHtml: Mustache.render(plantillaLogin)
  };
  window.history.pushState(stateObj, urlLogin, baseURL + urlLogin);

  // Comprobamos si hay cookies guardadas
  if (typeof Cookies.get("email") !== "undefined") {
    $("#email").val(Cookies.get("email"));
  }
  if (typeof Cookies.get("pass") !== "undefined") {
    $("#pass").val(Cookies.get("pass"));
  }

  $("#form_login").submit(() => {
    window.history.replaceState(stateObj, "", baseURL);
    email = $("#email").val();
    pass = $("#pass").val();
    if (validarEmail(email) && validarPass(pass)) {
      // Llamamos a la función ajax de login
      loginUsuario(email, pass);
    } else if (!validarEmail(email)) {
      swal("El formato del email no es valido", "Fallo", "error");
    } else if (!validarPass(pass)) {
      swal("El formato de la contraseña no es valido", "Fallo", "error");
    } // endValidaciones
  });
}; // -end identificacion usuario-

const loginUsuario = (email, pass) => {
  $.ajax("servicioWebUsuarios/loginUsuario", {
    // type: "POST",
    url: baseURL,
    data: "email=" + email + "&pass=" + pass,
    success: function (res) {
      if (res.includes("ok")) {
        nombreLogin = res.split(",")[1];
        swal("", "Inicio de sesión correcto", "success");
        if ($("#recordar_datos").prop("checked")) {
          swal("Cookies de Sesión", "Datos guardados", "success");
          Cookies.set("email", email, { expires: 100 });
          Cookies.set("pass", pass, { expires: 100 });
        }
      }
      $("#mensaje_login").text(nombreLogin);
      const stateObj = {
        url: baseURL + urlLogin,
        textoHtml: Mustache.render(plantillaLogin)
      };
      window.history.pushState(stateObj, urlLogin, baseURL + urlLogin);
    },
    error: () => {
      swal("", "Error al iniciar sesión", "error");
    }
  }); // end.ajax
}; // -end loginUsuario-

const logout = () => {
  $.ajax("servicioWebUsuarios/logout", {
    success: function (res) {
      if (res === "ok") {
        swal("Sesión cerrada", "Operación completada", "success");
        $("#mensaje_login").html("");
        setTimeout(() => {
          location.reload();
        }, 2000);
      }
    }
  });
}; // -end logout-

const editarUsuario = () => {
  // Boton Editar
  $(".boton_editar_usuario").click(function (e) {
    if (comprobarIdentificacion()) {
      $.ajax("identificado/servicioWebUsuarios/obtenerUsuarioPorId", {
        success: function (data) {
          alert("recibido: " + data);
          const info = JSON.parse(data);
          let textoHtml = "";
          textoHtml = Mustache.render(plantillaEditarUsuario, info);
          $("#contenedor").html(textoHtml);
          const stateObj = {
            url: baseURL + urlEditarPerfil,
            textoHtml: Mustache.render(plantillaEditarUsuario, info)
          };
          window.history.pushState(stateObj, urlEditarPerfil, baseURL + urlEditarPerfil);
          // Form
          $("#form_editar_usuario").submit(function () {
            const stateObj = {
              url: baseURL,
              textoHtml: Mustache.render(plantillaLogin)
            };
            window.history.pushState(stateObj, "", baseURL);
            const nombre = $("#nombre").val();
            const email = $("#email").val();
            const descripcion = $("#descripcion").val();
            const pass = $("#pass").val();

            if (validarNombre(nombre)) {
              if (validarEmail(email)) {
                if (validarDescripcion(descripcion)) {
                  if (validarPass(pass)) {
                    const formulario = document.forms[0];
                    const formData = new FormData(formulario);

                    $.ajax(
                      "identificado/servicioWebUsuarios/editarUsuarioPorId",
                      {
                        type: "POST",
                        data: formData,
                        cache: false,
                        contentType: false,
                        processData: false
                      }
                    ).then(
                      caches.open("v1").then((cache) => {
                        cache
                          .delete("/images/" + idUsuario + ".jpg")
                          .then(() => {
                            perfil();
                            window.location.reload();
                          });
                      })
                    );
                  } else {
                    swal("La contraseña  es incorrecta", "Error", "error");
                  } // end Pass
                } else {
                  swal("La descripción es incorrecta", "Error", "error");
                } // end Descripcion
              } else {
                swal("El email es incorrecto", "Error", "error");
              } // end Descripcion
            } else {
              swal("El nombre es incorrecto", "Error", "error");
            } // end Descripcion
          }); // end Submit Form
        } // end Success (Carga plantilla)
      }); // end ajax
      e.preventDefault();
    }
  }); // -end enlace editar
}; // -end editar usuario-

const borrarUsuario = () => {
  // Boton Borrar usuario
  $(".boton_borrar_usuario").click(function (e) {
    swal({
      title: "Est&aacutes seguro de que quieres eliminarlo?",
      text: "No se podr&aacute recuperar el usuario",
      icon: "warning",
      buttons: true,
      dangerMode: true
    }).then((eliminarUsuario) => {
      if (eliminarUsuario) {
        $.ajax("identificado/servicioWebUsuarios/borrarUsuarioPorId", {
          success: function (data) {
            if (data.includes("ok")) {
              // Aviso de operacion
              swal("El usuario se ha eliminado", {
                icon: "success"
              });
              setTimeout(() => {
                mostrarIdentificacionUsuario();
              }, 2000);
            }
          } // ---end success---
        }); // --end ajax--
      } else {
        perfil();
      }
    });
    e.preventDefault();
  }); // end Borrar Usuario
}; // -end borrar usuario-

const perfil = () => {
  $.ajax("identificado/servicioWebUsuarios/obtenerUsuarioPorId", {
    success: function (data) {
      alert("recibido: " + data);
      const info = JSON.parse(data);
      let textoHtml = "";

      textoHtml = Mustache.render(plantillaPerfil, info);
      $("#contenedor").html(textoHtml);
      const stateObj = {
        url: baseURL + urlPerfil,
        textoHtml: Mustache.render(plantillaPerfil, info)
      };
      window.history.pushState(stateObj, urlPerfil, baseURL + urlPerfil);

      // Editar usuario
      editarUsuario();
      // Borrar usuario
      borrarUsuario();

      // Ver listado follows
      listadoFollowsPerfil();
    } // end success Obtener id
  }); // end ajax
}; // -end perfil-

const eliminarValoracionFalse = (idPost) => {
  // Eliminar valoración
  // Se le llama y se le pasa la valoración previa realizada
  $(".like").click(
    $.ajax(
      "identificado/servicioWebValoracion/eliminarValoracion?idPost=" + idPost
    ).fail(function () {
      swal("Ha fallado la funcion de quitar dislike", {
        icon: "error"
      });
    }) // --end ajax--
  );
}; // -end eliminar valoracion false-

const eliminarValoracionTrue = (idPost) => {
  // Eliminar valoración
  // Se le llama y se le pasa la valoración previa realizada
  $(".dislike").click(
    $.ajax(
      "identificado/servicioWebValoracion/eliminarValoracion?idPost=" + idPost
    ).fail(function () {
      swal("Ha fallado la funcion de quitar like", "Error", "error");
    }) // --end ajax--
  );
}; // -end eliminar valoracion true-

const comprobarExisteValoracion = (idPost) => {
  return new Promise(function (resolve, reject) {
    $.ajax(
      "identificado/servicioWebValoracion/comprobarValoracion?idPost=" + idPost,
      {
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
      }
    );
  });
}; // -end comprobar existe valoracion

const comprobarIdentificacion = () => {
  return new Promise(function (resolve, reject) {
    $.ajax("servicioWebUsuarios/comprobarLogin", {
      success: function (res) {
        if (res.includes("ok")) {
          resolve(true);
        } else {
          resolve(false);
        }
      },
      error: function () {
        reject(new Error("No se pudo obtener la identificacion"));
      }
    });
  });
}; // -end comprobar identificacion-

// El listado de foros cambia dependiendo de si está identificado o no
$("#enlace_listado_foros").click(() => {
  comprobarIdentificacion()
    .then((usuarioIdentificado) => {
      if (usuarioIdentificado === false) {
        obtenerListadoForos();
      } else if (usuarioIdentificado === true) {
        obtenerListadoForosIdentificado();
      }
    })
    .catch(() => {
      swal("se ha producido un error en identificación", "Error", "error");
    });
}); // -end enlace listado foros

// Enlaces del navbar
$("#enlace_home").click(listadoInicio);
// $("#enlace_listado_foros").click(obtenerListadoForos);
$("#enlace_listado_posts").click(obtenerListadoPostsPopulares);
$("#enlace_editar_usuario").click(mostrarRegistroUsuario);
$("#enlace_registrarme").click(mostrarRegistroUsuario);
$("#enlace_identificarme").click(mostrarIdentificacionUsuario);
$("#enlace_logout").click(logout);
$("#enlace_perfil").click(perfil);
