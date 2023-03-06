// Comprueba que las contraseñas coinciden y devuelve un boolean



const inputPassConf = document.getElementById("pass");


inputPassConf.addEventListener("focus", () => {
const valorPass = document.getElementById("pass").value;
const valorPassConf = document.getElementById("pass-validacion").value;
const danger="#003F30";
const primary="#780B0B";
  if (valorPass === valorPassConf) {
    
    inputPassConf.style.borderColor = danger;
  } else {
    inputPassConf.style.borderColor = primary;
  }
});

