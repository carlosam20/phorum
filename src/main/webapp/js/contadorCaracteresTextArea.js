let contador = document.getElementById("contador");
const textarea = document.getElementById("textarea");

textarea.addEventListener("keyup", (event) => {
  contador = contador++;
});

textarea.addEventListener("keydown", (event) => {
  if (event.key === "Backspace") {
    contador = --contador;
  } else if (event.key === "Delete") {
    contador = --contador;
  }
});
