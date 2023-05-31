/* eslint-disable no-undef */

// Variables globales de usuario
const idUsuario = "";

// URLS
const urlForos = "foros";
const urlPerfil = "perfil";
const urlListadoFollows = "listadoFollows";
const urlPosts = "listadoPostsPopulares";
const urlPostsForo = "listadoPosts";
const urlPostYComentarios = "postYcomentarios";
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
      // Editar usuario
      editarUsuario();
      // Borrar usuario
      borrarUsuario();

      // Ver listado follows
      listadoFollowsPerfil();
      $("#contenedor").html(state.textoHtml);
    } else if (state.url === baseURL) {
      console.log("Handle Inicio");
      $("#contenedor").html(state.textoHtml);
      // Ver post de foro
      verPostsDeForo();
      // Ver post y comentarios
      verPostYComentarios();
    } else if (state.url === baseURL + urlPosts) {
      verPostYComentarios();
      registrarPost();
      console.log("Handle Posts");
      $("#contenedor").html(state.textoHtml);
    } else if (state.url === baseURL + urlRegistrar) {
      $("#contenedor").html(state.textoHtml);
    } else if (state.url === baseURL + urlForos) {
      $.ajax("servicioWebForos/obtenerForos", {
        url: baseURL + urlForos,
        success: function (data) {
          const foros = JSON.parse(data);
          let textoHtml = "";
          textoHtml = Mustache.render(plantillaListarForos, foros);
          $("#contenedor").html(textoHtml);

          const stateObj = {
            url: baseURL + urlForos,
            textoHtml: textoHtml = Mustache.render(plantillaListarForos, foros, () => {
              // Ver Posts de Foro
              verPostsDeForo();

              // Buscar foros
              busquedaForos();
            })
          };
          window.history.pushState(stateObj, urlForos, baseURL + urlForos);

          // Ver Posts de Foro
          verPostsDeForo();

          // Buscar foros
          busquedaForos();
        } // ---end success---

      }); // --end ajax--
      console.log("Handle Foros");
      $("#contenedor").html(state.textoHtml);
    } else if (state.url === baseURL + urlPostYComentarios) {
      $.ajax("identificado/servicioWebPosts/obtenerPostYComentariosPorId?idPost=" + state.id, {
        success: (data, idPost) => {
          const postYComentarios = JSON.parse(data);
          let textoHtml = "";
          textoHtml = Mustache.render(
            plantillaListarPostYComentarios,
            postYComentarios
          );
          $("#contenedor").html(textoHtml);

          // Cambiar iconos según la valoración del usuario
          const valor = postYComentarios.valoracion_usuario_sesion.valorUsuarioSesion;
          console.log("Valor" + valor);

          if (valor === "true") {
            $("#like-icon")
              .removeClass("fa-regular fa-thumbs-up fa-xl")
              .addClass("fa-solid fa-thumbs-up fa-xl");
          } else if (valor === "false") {
            $("#dislike-icon")
              .removeClass("fa-regular fa-thumbs-down fa-xl")
              .addClass("fa-solid fa-thumbs-down fa-xl");
          }

          // Comentario en post
          registrarComentarioPost();

          // Ver perfil de comentario
          verPerfilDeComentario();

          // Crear valoración y poner like
          valoracionLike(state.id);

          // Crear valoración y poner dislike
          valoracionDislike(state.id);

          const stateObj = {
            url: baseURL + urlPostYComentarios,
            textoHtml:
              Mustache.render(
                plantillaListarPostYComentarios,
                postYComentarios),
            id: state.id
          };
          window.history.pushState(stateObj, urlPostYComentarios, baseURL + urlPostYComentarios);
        }
      });
    } else if (state.url === baseURL + urlPostsForo) {
      // Registrar post
      registrarPost();
      // Ver post y comentarios
      verPostYComentarios(state.id);
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
    handleStateChange(e.state);
  }
});

$(document).ready(function () {
  // get the current URL
  // eslint-disable-next-line prefer-const
  let currentURL = window.location.href;

  // detect the URL and call the appropriate function
  handleURL(currentURL);
});

// -- Fin  recarga de URL

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
        textoHtml: Mustache.render(plantillaHome, forosYPost)
      };
      window.history.replaceState(stateObj, baseURL, baseURL);
      // Ver post de foro
      verPostsDeForo();

      // Ver post y comentarios
      verPostYComentarios();
    },
    error: () => {
      swal("", "Error en el listado de inicio", "Error");
    }
  });
};
cargarPlantillasDelServidor();
listadoInicio();

const registrarComentarioPost = () => {
  // Realizar comentario en post
  if (comprobarIdentificacion().then((usuarioIdentificado) => {
    if (usuarioIdentificado === false) {
      throw swal("Error no identificado", "Te debes identificar para acceder", "info");
    } else {
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
                location.reload();
              }
            }, // end Success Registrar Comentario
            error: (res) => {
              swal("Error al registrar comentario", res.responseText, "error");
            }// end Error
          }
        ); // end Registrar Comentarios
        e.preventDefault();
      });
    }
  }));
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
    if (comprobarIdentificacion().then((usuarioIdentificado) => {
      if (usuarioIdentificado === false) {
        throw swal("Error no identificado", "Te debes identificar para acceder", "info");
      }
    })) {
      const idPostClick = $(this).attr("id");
      $.ajax("identificado/servicioWebPosts/obtenerPostYComentariosPorId?idPost=" + idPostClick, {
        success: (data) => {
          const postYComentarios = JSON.parse(data);
          let textoHtml = "";
          textoHtml = Mustache.render(
            plantillaListarPostYComentarios,
            postYComentarios
          );
          $("#contenedor").html(textoHtml);

          // Cambiar iconos según la valoración del usuario
          const valor = postYComentarios.valoracion_usuario_sesion.valorUsuarioSesion;
          console.log("Valor" + valor);

          if (valor === "true") {
            $("#like-icon")
              .removeClass("fa-regular fa-thumbs-up fa-xl")
              .addClass("fa-solid fa-thumbs-up fa-xl");
          } else if (valor === "false") {
            $("#dislike-icon")
              .removeClass("fa-regular fa-thumbs-down fa-xl")
              .addClass("fa-solid fa-thumbs-down fa-xl");
          }

          // Comentario en post
          registrarComentarioPost();

          // Ver perfil de comentario
          verPerfilDeComentario();

          // Crear valoración y poner like
          valoracionLike(idPostClick);

          // Crear valoración y poner dislike
          valoracionDislike(idPostClick);

          const stateObj = {
            url: baseURL,
            textoHtml: Mustache.render(plantillaListarPostYComentarios, postYComentarios),
            id: idPostClick
          };

          window.history.pushState(stateObj, baseURL, baseURL);
        }
      }); // --end ajax--
      e.preventDefault();
    }
  });
}; // -end ver Post y comentarios-

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
          // Ver post Foros
          verPostsDeForo();

          // Buscar foros
          busquedaForos();
          const stateObj = {
            url: baseURL + urlForos,
            textoHtml:
              Mustache.render(
                plantillaListarForos,
                forosEncontrados)
          };
          window.history.replaceState(stateObj, urlForos, baseURL + urlForos);
        },
        error: () => {
          swal("No se ha encontrado ning\u00FAn elemento", "info");
        } // ---end success---
      }
    ); // --end ajax obtenerForosBuscados--
    e.preventDefault();
  }); // --end click boton_buscar--
}; // -end busqueda foros-

const busquedaForosIdentificado = () => {
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
          textoHtml = Mustache.render(plantillaListarForosIdentificado, forosEncontrados);
          $("#contenedor").html(textoHtml);
          // Ver post Foros
          verPostsDeForo();

          // Registrar foros
          registrarForo();

          // Buscar foros
          busquedaForosIdentificado();
          const stateObj = {
            url: baseURL,
            textoHtml:
              Mustache.render(
                plantillaListarForosIdentificado,
                forosEncontrados)
          };
          window.history.replaceState(stateObj, baseURL, baseURL);
        },
        error: () => {
          swal("No se ha encontrado ning\u00FAn elemento", "info");
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
            plantillaListarFollows,
            forosEncontrados
          );
          $("#contenedor").html(textoHtml);
        },
        complete: () => {
          // Ver post Foros
          verPostsDeForo();

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
    // vamos a usar FormData para mandar el form al servicio web
    const formulario = document.forms[0];
    const formData = new FormData(formulario);

    if (comprobarIdentificacion().then((usuarioIdentificado) => {
      if (usuarioIdentificado === false) {
        throw swal("Error no identificado", "Te debes identificar para acceder", "info");
      } else {
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
              obtenerListadoForosIdentificado();
            }
          },
          error: (res) => {
            swal("Error al registrar", res.responseText, "error");
          }
        });
        e.preventDefault();
      }
    }));
  });
}; // -end registrar foro-

const verPostsDeForo = () => {
  // Boton Ver Posts de Foro
  $(".boton_post_foro").click(function (e) {
    const idForo = $(this).attr("id");
    // ObtenerPostPorForoDeID
    $.ajax("servicioWebPosts/obtenerPostPorForoId?id=" + idForo, {
      success: function (data) {
        const posts = JSON.parse(data);
        let textoHtml = Mustache.render(plantillaListarPosts, posts);
        $("#contenedor").html(textoHtml);
        const stateObj = {
          url: baseURL,
          textoHtml: textoHtml = Mustache.render(plantillaListarPosts, posts),
          id: idForo
        };
        window.history.pushState(stateObj, baseURL, baseURL);
        // Registrar post
        registrarPost();
        // Ver post y comentarios
        verPostYComentarios(idForo);
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
        textoHtml: textoHtml = Mustache.render(plantillaListarForos, foros, () => {
          // Ver Posts de Foro
          verPostsDeForo();

          // Buscar foros
          busquedaForos();
        })
      };
      window.history.pushState(stateObj, urlForos, baseURL + urlForos);

      // Ver Posts de Foro
      verPostsDeForo();

      // Buscar foros
      busquedaForos();
    } // ---end success---

  }); // --end ajax--
};

const obtenerListadoForosIdentificado = () => {
  $.ajax("identificado/servicioWebForos/obtenerForos", {
    success: function (data) {
      const foros = JSON.parse(data);
      let textoHtml = "";
      textoHtml = Mustache.render(plantillaListarForosIdentificado, foros);
      $("#contenedor").html(textoHtml);
      const stateObj = {
        url: baseURL,
        textoHtml: textoHtml = Mustache.render(plantillaListarForosIdentificado, foros, () => {
          // Dar follow por el usuario
          follow();

          // Ver Posts de Foro
          verPostsDeForo();

          // Buscar foros
          busquedaForosIdentificado();

          // Registro de foros
          registrarForo();
        })
      };
      window.history.pushState(stateObj, baseURL, baseURL);
      // Dar follow por el usuario
      follow();

      // Ver Posts de Foro
      verPostsDeForo();

      // Buscar foros
      busquedaForosIdentificado();

      // Registro de foros
      registrarForo();
    } // ---end success---
  }); // --end ajax--
}; // -end obtener_listado-

const listadoFollowsPerfil = () => {
  $(".boton_ver_follows").click(function () {
    if (comprobarIdentificacion().then((usuarioIdentificado) => {
      if (usuarioIdentificado === false) {
        throw swal("Error no identificado", "Te debes identificar para acceder", "info");
      }
    })) {
      $.ajax("identificado/servicioWebForos/obtenerForosPerfil", {
        success: function (data) {
          const foros = JSON.parse(data);
          let textoHtml = "";
          textoHtml = Mustache.render(plantillaListarFollows, foros, () => {
            // Ver Posts de Foro
            verPostsDeForo();

            // Buscar foros
            busquedaFollowsPerfil();
          });
          $("#contenedor").html(textoHtml);
          const stateObj = { url: baseURL + urlListadoFollows };
          window.history.pushState(stateObj, urlListadoFollows, baseURL + urlListadoFollows);
          // Dar follow por el usuario
          follow();

          // Ver Posts de Foro
          verPostsDeForo();

          // Buscar foros
          busquedaFollowsPerfil();
        },
        error: (data) => {
          swal(data, "Error en listado follows de perfil", "error");
        } // ---end success---
      }); // --end ajax--
    }
  });
}; // -end listado follows perfil

const follow = () => {
  $(".follow").click(function () {
    if (comprobarIdentificacion().then((usuarioIdentificado) => {
      if (usuarioIdentificado === false) {
        throw swal("Error no identificado", "Te debes identificar para acceder", "info");
      }
    })) {
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
    }
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
  if (comprobarIdentificacion().then((usuarioIdentificado) => {
    if (usuarioIdentificado === false) {
      throw swal("Error no identificado", "Te debes identificar para acceder", "info");
    }
  })) {
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
        swal("Ha fallado la funci\u00F3n de dar follow", {
          icon: "error"
        });
      });
  }
}; // -end dar follow

const eliminarFollow = (idForo) => {
  if (comprobarIdentificacion().then((usuarioIdentificado) => {
    if (usuarioIdentificado === false) {
      throw swal("Error no identificado", "Te debes identificar para acceder", "info");
    }
  })) {
    // Se comprueba si hay follow previamente y se elimina si lo hay
    $.ajax("identificado/servicioWebFollow/eliminarFollow?idForo=" + idForo, {
      error: (res) => {
        swal("Error en eliminar follow", res, "error");
      }
    }); // --end ajax--
  }
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
    if (comprobarIdentificacion().then((usuarioIdentificado) => {
      if (usuarioIdentificado === false) {
        throw swal("Error no identificado", "Te debes identificar para acceder", "info");
      } else {
        const idForo = $(".enlacePost").attr("id");

        const formulario = document.forms[0];
        const formData = new FormData(formulario);
        formData.append("idForo", idForo);

        $.ajax("identificado/servicioWebPosts/registrarPosts", {
          type: "POST",
          data: formData,
          cache: false,
          contentType: false,
          processData: false,
          success: function (res) {
            if (res === "ok") {
              swal("Registro correcto", "", "success");
              $("#crearPostModal").modal("hide");
              obtenerListadoPostsPopulares();
            }
          },
          error: (res) => {
            swal("Error en el registro", res.responseText, "error");
          } // end Success Registrar Post
        });
        // end Registrar Post
        e.preventDefault();
      }
    }));
  });
}; // -end registrar post-

const mostrarRegistroUsuario = () => {
  $("#contenedor").html(plantillaRegistrarUsuario);

  $("#form_registro_usuario").submit(function (e) {
    const formulario = document.forms[0];
    const formData = new FormData(formulario);
    $.ajax("servicioWebUsuarios/registrarUsuario", {
      type: "POST",
      data: formData,
      cache: false,
      contentType: false,
      processData: false,
      success: () => {
        swal(
          "El registro se ha realizado de forma correcta",
          "Realizado",
          "success"
        );
      },
      error: (res) => {
        swal("Error al registrar", res.responseText, "error");
      }
    });
    e.preventDefault();
  });
}; // -end registro usuario-

const mostrarIdentificacionUsuario = () => {
  $("#contenedor").html(plantillaLogin);

  const stateObj = {
    url: baseURL,
    textoHtml: Mustache.render(plantillaLogin)
  };
  window.history.pushState(stateObj, baseURL, baseURL);

  // Comprobamos si hay cookies guardadas
  if (typeof Cookies.get("email") !== "undefined") {
    $("#email").val(Cookies.get("email"));
  }
  if (typeof Cookies.get("pass") !== "undefined") {
    $("#pass").val(Cookies.get("pass"));
  }

  $("#form_login").submit((e) => {
    e.preventDefault();
    window.history.replaceState(stateObj, "", baseURL);
    const email = $("#email").val();
    const pass = $("#pass").val();
    if (validarEmail(email) && validarPass(pass)) {
      // Llamamos a la función ajax de login
      loginUsuario(email, pass);
    } else if (!validarEmail(email)) {
      swal("El formato del email no es v\u00E1lido", "Fallo", "error");
    } else if (!validarPass(pass)) {
      swal("El formato de la contraseña no es v\u00E1lido", "Fallo", "error");
    } // endValidaciones
  });
}; // -end identificacion usuario-

const loginUsuario = (email, pass) => {
  $.ajax("servicioWebUsuarios/loginUsuario", {
    type: "POST",
    url: baseURL,
    data: "email=" + email + "&pass=" + pass,
    success: function (res) {
      if (res.includes("ok")) {
        const nombreLogin = res.split(",")[1];
        $("#mensaje_login").text(nombreLogin);
        swal("Realizado", "Inicio de sesi\u00F3n correcto", "success");
        if ($("#recordar_datos").prop("checked")) {
          Cookies.set("email", email, { expires: 100 });
          Cookies.set("pass", pass, { expires: 100 });
        } else {
          Cookies.remove("email");
          Cookies.remove("pass");
        }
      } else {
        swal("Error", "Inicio de sesi\u00F3n incorrecto", "error");
      }
      const stateObj = {
        url: baseURL,
        textoHtml: Mustache.render(plantillaLogin)
      };
      window.history.pushState(stateObj, baseURL, baseURL);
    },
    error: () => {
      swal("Error", "Error al iniciar sesi\u00F3n", "error");
    }
  }); // end.ajax
}; // -end loginUsuario-

const logout = () => {
  $.ajax("servicioWebUsuarios/logout", {
    success: function (res) {
      if (res === "ok") {
        swal("Sesi&acuteon cerrada", "Operaci\u00F3n completada", "success");
        $("#mensaje_login").html("");
        setTimeout(() => {
          listadoInicio();
        }, 2000);
      }
    }
  });
}; // -end logout-

const editarUsuario = () => {
  // Boton Editar

  $(".boton_editar_usuario").click(function () {
    if (comprobarIdentificacion()) {
      $.ajax("identificado/servicioWebUsuarios/obtenerUsuarioPorId", {
        success: function (data) {
          const info = JSON.parse(data);
          let textoHtml = "";
          textoHtml = Mustache.render(plantillaEditarUsuario, info);
          $("#contenedor").html(textoHtml);
          // Form
          $("#form_editar_usuario").submit(function (e) {
            const formulario = document.forms[0];
            const formData = new FormData(formulario);
            $.ajax(
              "identificado/servicioWebUsuarios/editarUsuarioPorId",
              {
                type: "POST",
                data: formData,
                contentType: false,
                cache: false,
                processData: false,
                success: (res) => {
                  if (res === "ok") {
                    alert(res);
                    swal("Usuario editado", "El usuario se ha editado correctamente", "success", {
                      buttons: {
                        catch: {
                          text: "OK",
                          value: "ok"
                        }
                      }
                    })
                      .then((value) => {
                        switch (value) {
                          case "ok":
                            caches.open("v1").then((cache) => {
                              cache
                                .delete("/images/" + idUsuario + ".jpg")
                                .then(() => {
                                  const stateObj = {
                                    url: baseURL,
                                    textoHtml: Mustache.render(plantillaEditarUsuario)
                                  };
                                  window.history.pushState(stateObj, baseURL, baseURL);
                                  perfil();
                                });
                            });
                            break;
                        }
                      });
                  }
                },
                error: (res) => {
                  alert(res);
                  swal("Error al editar", res.responseText, "error");
                }
              }
            );
            e.preventDefault();
          }); // end Submit Form
        } // end Success (Carga plantilla)
      }); // end ajax
    }
    e.preventDefault();
  }); // -end enlace editar
}; // -end editar usuario-

const borrarUsuario = () => {
  // Boton Borrar usuario
  $(".boton_borrar_usuario").click(function (e) {
    swal({
      title: "Est\u00E1s seguro de que quieres eliminarlo?",
      text: "No se podr\u00E1 recuperar el usuario",
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
                logout();
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
  if (comprobarIdentificacion().then((usuarioIdentificado) => {
    if (usuarioIdentificado === false) {
      throw swal("Error no identificado", "Te debes identificar para acceder", "info");
    }
  })) {
    $.ajax("identificado/servicioWebUsuarios/obtenerUsuarioPorId", {
      success: function (data) {
        const info = JSON.parse(data);
        let textoHtml = "";

        textoHtml = Mustache.render(plantillaPerfil, info);
        $("#contenedor").html(textoHtml);
        const stateObj = {
          url: baseURL + urlPerfil,
          textoHtml: Mustache.render(plantillaPerfil, info, () => {

          })
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
  }
}; // -end perfil-

const eliminarValoracionFalse = (idPost) => {
  // Eliminar valoración
  // Se le llama y se le pasa la valoración previa realizada
  if (comprobarIdentificacion().then((usuarioIdentificado) => {
    if (usuarioIdentificado === false) {
      throw swal("Error no identificado", "Te debes identificar para acceder", "info");
    }
  })) {
    $(".like").click(
      $.ajax(
        "identificado/servicioWebValoracion/eliminarValoracion?idPost=" + idPost
      ).fail(function () {
        swal("Ha fallado la funci\u00F3n de quitar dislike", {
          icon: "error"
        });
      }) // --end ajax--
    );
  }
}; // -end eliminar valoracion false-

const eliminarValoracionTrue = (idPost) => {
  // Eliminar valoración
  // Se le llama y se le pasa la valoración previa realizada
  if (comprobarIdentificacion().then((usuarioIdentificado) => {
    if (usuarioIdentificado === false) {
      throw swal("Error no identificado", "Te debes identificar para acceder", "info");
    }
  })) {
    $(".dislike").click(
      $.ajax(
        "identificado/servicioWebValoracion/eliminarValoracion?idPost=" + idPost
      ).fail(function () {
        swal("Ha fallado la funci\u00F3n de quitar like", "Error", "error");
      }) // --end ajax--
    );
  }
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
      swal("se ha producido un error en identificaci\u00F3n", "Error", "error");
    });
}); // -end enlace listado foros

$("#enlace_listado_foros_footer").click(() => {
  comprobarIdentificacion()
    .then((usuarioIdentificado) => {
      if (usuarioIdentificado === false) {
        obtenerListadoForos();
      } else if (usuarioIdentificado === true) {
        obtenerListadoForosIdentificado();
      }
    })
    .catch(() => {
      swal("se ha producido un error en identificaci\u00F3n", "Error", "error");
    });
}); // -end enlace listado foros

// Enlaces del navbar
$("#enlace_home").click(listadoInicio);
$("#enlace_listado_posts").click(obtenerListadoPostsPopulares);
$("#enlace_registrarme").click(mostrarRegistroUsuario);
$("#enlace_identificarme").click(mostrarIdentificacionUsuario);
$("#enlace_logout").click(logout);
$("#enlace_perfil").click(perfil);

$("#enlace_home_footer").click(listadoInicio);
$("#enlace_listado_posts_footer").click(obtenerListadoPostsPopulares);
$("#enlace_registrarme_footer").click(mostrarRegistroUsuario);
$("#enlace_identificarme_footer").click(mostrarIdentificacionUsuario);
$("#enlace_logout_footer").click(logout);
$("#enlace_perfil_footer").click(perfil);
