/**
 * 
 */



//Fuerza un refresco entero de la página, para que se puedan ver los nuevos cambios

var button = document.querySelector("#").value;



if(button === "Guardar Cambios"){
    button.addEventListener("click", function onclick(event) {
        Viewer.toggleThumbnails();
        event.preventDefault();
      });
}




  
