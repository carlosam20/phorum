let likeButton = document.getElementById("like");
let dislikeButton = document.getElementById("dislike");

let likeDOM = document.getElementById("like-icon").className;
let dislikeDOM = document.getElementById("dislike-icon").className;

likeButton.addEventListener("click", function () {
	like.classList.add("fa-beat");
	setTimeout(() => {
		like.classList.remove("fa-beat");
	}, 1000);
});

dislikeButton.addEventListener("click", function () {

	dislike.classList.add("fa-beat");
	setTimeout(() => {
		dislike.classList.remove("fa-beat");
	}, 1000);
});


