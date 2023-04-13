//Funciones vercomentarios

document.querySelector(".boton_post_top3").addEventListener("click", () => {
  abrirPostsTop3();
});

document.querySelector(".closebtn").addEventListener("click", () => {
  cerrarPostsTop3();
});

let abrirPostsTop3 = () => {
  document.getElementById("postsTop3").style.width = "50%";
  document.getElementById("recomendacionesPostsPopulares").style.marginLeft =
    "50%";
};

let cerrarPostsTop3 = () => {
  document.getElementById("postsTop3").style.width = "0%";
  document.getElementById("recomendacionesPostsPopulares").style.marginLeft =
    "0%";
};
