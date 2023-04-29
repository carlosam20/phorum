// Funciones vercomentarios

if (window.innerWidth > 600) {
  document.querySelector(".boton_post_top3").addEventListener("click", () => {
    document.getElementById("postsTop3").style.width = "50%";
    document.getElementById("recomendacionesPostsPopulares").style.marginLeft =
      "50%";
  });

  document.querySelector(".closebtn").addEventListener("click", () => {
    document.getElementById("postsTop3").style.width = "0%";
    document.getElementById("recomendacionesPostsPopulares").style.marginLeft =
      "0%";
  });
}
if (window.innerWidth <= 600) {
  document.querySelector(".boton_post_top3").addEventListener("click", () => {
    document.getElementById("postsTop3").style.width = "100%";
    document.getElementById("recomendacionesPostsPopulares").style.marginLeft =
      "0%";
  });

  document.querySelector(".closebtn").addEventListener("click", () => {
    document.getElementById("postsTop3").style.width = "0%";
    document.getElementById("recomendacionesPostsPopulares").style.marginLeft =
      "0%";
  });
}
