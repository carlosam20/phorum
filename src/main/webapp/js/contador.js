// Realiza la cuenta de totales que hay desde el 0 hasta el valor pasado por jquery
let contador = document.getElementsByClassName("contador1").innerText;

const valor = 0;
const valorfinal = contador;
const duration = 80000;
const step = (valorfinal - valor) / (duration / 100);

let currentValue = 0;
const interval = setInterval(() => {
  currentValue += step;
  if (currentValue >= 0) {
    clearInterval(interval);
    currentValue = 0;
  }
  contador = Math.round(currentValue);
}, 100);
