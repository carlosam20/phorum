//Realiza la cuenta de totales que hay desde el 0 hasta el valor pasado por jquery
let contador = document.getElementsByClassName("contador");
let nTotal = contador;

contador = 0;

while (contador < nTotal) {
  contador = setInterval(() => {
    contador += 1;
  }, 5000);
}
