//Variables de la página de post
let likeDOM = document.getElementById("like-icon");
let dislikeDOM = document.getElementById("dislike-icon");



//Funciones comentarios
function abrirComents() {
	document.getElementById("comentarios").style.width = "50%";
	document.getElementById("post").style.marginLeft = "50%";
	document.getElementById("fechaCreacionPost").style.display = "none";
	
}

function cerrarComents() {
	document.getElementById("comentarios").style.width = "0%";
	document.getElementById("post").style.marginLeft = "0%";
	document.getElementById("fechaCreacionPost").style.display = "inheret";
	
}

// Clicks de likes y dislikes
likeDOM.addEventListener("click", function () {
	if (likeDOM.classList.contains("fa-regular")) {
		darLike();
		let likeActivado = true;
		quitarDislike(likeActivado);
		
	} else {
		quitarLike();
		likeActivado = false;
	}
});
dislikeDOM.addEventListener("click", function () {
	if (dislikeDOM.classList.contains("fa-regular")) {
		darDislike();
		let disLikeActivado = true;
		quitarLike(disLikeActivado);
		
	} else {
		quitarDislike();
		disLikeActivado = false;
	}
});

//funciones likes y dislikes
function darLike() {
	likeDOM.classList.add("fa-bounce");
	setTimeout(() => {
		likeDOM.classList.remove("fa-bounce");
		likeDOM.classList.remove("fa-regular");
		likeDOM.classList.add("fa-solid");
		

	}, 800);
}

function quitarLike(disLikeActivado) {
	if (dislikeDOM.classList.contains("fa-solid") && disLikeActivado === false) {
		likeDOM.classList.add("fa-bounce");
		
	}
	setTimeout(() => {
		likeDOM.classList.remove("fa-bounce");
		likeDOM.classList.remove("fa-solid");
		likeDOM.classList.add("fa-regular");
		
	}, 800);
}

function darDislike() {
	dislikeDOM.classList.add("fa-bounce");
	
	setTimeout(() => {
		dislikeDOM.classList.remove("fa-bounce");
		dislikeDOM.classList.remove("fa-regular");
		dislikeDOM.classList.add("fa-solid");
		
	}, 800);
}
function quitarDislike(likeActivado) {
	if (likeDOM.classList.contains("fa-solid") && likeActivado === false) {
		dislikeDOM.classList.add("fa-bounce");

	}
	setTimeout(() => {
		dislikeDOM.classList.remove("fa-bounce");
		dislikeDOM.classList.remove("fa-solid");
		dislikeDOM.classList.add("fa-regular");
		
	}, 800);
}




