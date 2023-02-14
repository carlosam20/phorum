function abrirComents() {
    document.getElementById("comentarios").style.width = "800px";
    document.getElementById("post").style.marginLeft = "800px";
}

function cerrarComents() {
    document.getElementById("comentarios").style.width = "0";
    document.getElementById("post").style.marginLeft = "0";
}


//Variables de la página de post

let likeButton = document.getElementById("like").className;
let dislikeButton = document.getElementById("dislike").className;

let likeDOM = document.getElementById("like-icon").className;
let dislikeDOM = document.getElementById("dislike-icon").className;

let contadorLikes = document.getElementById("like-contador").className;
let contadorDislikes = document.getElementById("dislike-contador").className;



//Poner condición para ver si ha sido checkeado o no (fa-solid o fa-regular)

likeDOM.addEventListener("click", function () {
	if(likeDOM.classList.contains("fa-regular fa-thumbs-up fa-xl")){
        likeDOM.classList.add("fa-beat");
	setTimeout(() => {
		likeDOM.classList.remove("fa-beat");
		likeDOM.classList.remove("fa-regular");
		likeDOM.classList.add("fa-solid");
		contadorLikes++;
	}, 999);
}else{
	likeDOM.classList.add("fa-beat");
	setTimeout(() => {
		likeDOM.classList.remove("fa-beat");
		likeDOM.classList.remove("fa-solid");
		likeDOM.classList.add("fa-regular");
		--contadorLikes;
	}, 999);
}
});

dislikeDOM.addEventListener("click", function () {
if(dislikeDOM.classList.contains("fa-regular fa-thumbs-down fa-xl")){
	dislikeDOM.classList.add("fa-beat");
	setTimeout(() => {
		dislikeDOM.classList.remove("fa-beat");
		dislikeDOM.classList.remove("fa-regular");
		dislikeDOM.classList.add("fa-solid");
		contadorDislikes++;
	}, 999);
}else{
	dislikeDOM.classList.add("fa-beat");
	setTimeout(() => {
		dislikeDOM.classList.remove("fa-beat");
		dislikeDOM.classList.remove("fa-regular");
		dislikeDOM.classList.add("fa-solid");
		--contadorDislikes;
	}, 999);
}
});


