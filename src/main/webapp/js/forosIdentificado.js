  
function init(){
    let iconosFollow = document.querySelectorAll('i');

    iconosFollow.forEach(
        (icono) =>{
            //Comprueba si contiene un número
            if(/\d+/.test(icono.id)){
                icono.classList.remove("fa-regular", "fa-square-plus", "fa-2xl");
                icono.classList.add("fa-solid", "fa-square-plus", "fa-2xl");            
            }
            icono.addEventListener("click", function () {
                if(icono.classList.contains("fa-regular")){
                    darFollowIcono(icono);
                }else{
                    quitarFollowIcono(icono);
                }
            });
        }
    );
}




function darFollowIcono(icono) {
	icono.classList.add("fa-flip");
	setTimeout(() => {
		icono.classList.remove("fa-flip");
		icono.classList.remove("fa-regular");
		icono.classList.add("fa-solid");	
	}, 800);
}

function quitarFollowIcono(icono) {
	icono.classList.add("fa-flip");
	setTimeout(() => {
		icono.classList.remove("fa-flip");
		icono.classList.remove("fa-solid");
		icono.classList.add("fa-regular");	
	}, 800);
}
