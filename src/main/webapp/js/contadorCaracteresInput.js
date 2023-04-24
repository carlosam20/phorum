const contadorNombre = document.getElementById("contadorNombre");
// const contadorEmail = document.getElementById("contadorEmail");
const inputNombre = document.getElementById("inputNombre");
// const inputEmail = document.getElementById("inputEmail");
// const inputDescripcion = document.getElementById("inputDescripcion");

inputNombre.addEventListener("keyup", (event) => {
  contadorNombre.value = contadorNombre.value++;
});

inputNombre.addEventListener("keydown", (event) => {
  if (event.key === "Backspace") {
    contadorNombre.value = --contadorNombre.value;
    contadorNombre.textContent(contadorNombre);
  } else if (event.key === "Delete") {
    contadorNombre.value = --contadorNombre.value;
  }
});
