  
let iconosFollow = document.querySelectorAll('i');

iconosFollow.forEach(
    (icono) =>{
        if(!icono.id ===""){
            icono.classList.remove("fa-regular fa-circle-plus");
            icono.classList.add("fa-solid fa-circle-plus");
        }
        icono.addEventListener("click", function () {
            if(icono.classList.contains("fa-regular")){
                darFollow(icono);
            }else{
                quitarFollow(icono);
            }
        });
    }
);



function darFollow(icono) {
	icono.classList.add("fa-flip");
	setTimeout(() => {
		icono.classList.remove("fa-flip");
		icono.classList.remove("fa-regular");
		icono.classList.add("fa-solid");	
	}, 800);
}

function quitarFollow(icono) {
	icono.classList.add("fa-flip");
	setTimeout(() => {
		icono.classList.remove("fa-flip");
		icono.classList.remove("fa-regular");
		icono.classList.add("fa-solid");	
	}, 800);
}
