// Variables de la página de post
// Funciones comentarios
const abrirComents = () => {
  document.getElementById("comentarios").style.width = "100%";
  document.getElementById("post").style.marginLeft = "0";
  document.getElementById("fechaCreacionPost").style.display = "none";
};

const cerrarComents = () => {
  document.getElementById("comentarios").style.width = "0%";
  document.getElementById("post").style.marginLeft = "0";
  document.getElementById("fechaCreacionPost").style.display = "initial";
};

// if (typeof likeDOM === "undefined" && typeof dislikeDOM === "undefined") {
const likeDOM = document.getElementById("like-icon");
const dislikeDOM = document.getElementById("dislike-icon");

// Clicks de likes y dislikes

dislikeDOM.addEventListener("click", function () {
  if (dislikeDOM.classList.contains("fa-regular")) {
    darDislike();
    const disLikeActivado = true;
    quitarLike(disLikeActivado);
  } else {
    quitarDislike();
    disLikeActivado = false;
  }
});

likeDOM.addEventListener("click", function () {
  if (likeDOM.classList.contains("fa-regular")) {
    darLike();
    const likeActivado = true;
    quitarDislike(likeActivado);
  } else {
    quitarLike();
    likeActivado = false;
  }
});

// funciones likes y dislikes
const darLike = () => {
  likeDOM.classList.add("fa-bounce");
  setTimeout(() => {
    likeDOM.classList.remove("fa-bounce");
    likeDOM.classList.remove("fa-regular");
    likeDOM.classList.add("fa-solid");
  }, 800);
};

const quitarLike = (disLikeActivado) => {
  if (dislikeDOM.classList.contains("fa-solid") && disLikeActivado === false) {
    likeDOM.classList.add("fa-bounce");
  }
  setTimeout(() => {
    likeDOM.classList.remove("fa-bounce");
    likeDOM.classList.remove("fa-solid");
    likeDOM.classList.add("fa-regular");
  }, 800);
};

const darDislike = () => {
  dislikeDOM.classList.add("fa-bounce");

  setTimeout(() => {
    dislikeDOM.classList.remove("fa-bounce");
    dislikeDOM.classList.remove("fa-regular");
    dislikeDOM.classList.add("fa-solid");
  }, 800);
};
const quitarDislike = (likeActivado) => {
  if (likeDOM.classList.contains("fa-solid") && likeActivado === false) {
    dislikeDOM.classList.add("fa-bounce");
  }
  setTimeout(() => {
    dislikeDOM.classList.remove("fa-bounce");
    dislikeDOM.classList.remove("fa-solid");
    dislikeDOM.classList.add("fa-regular");
  }, 800);
};
// }
