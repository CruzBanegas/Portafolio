// script.js
let valor = 0; // variable que guarda el valor del contador

function actualizarContador() {
  document.getElementById('contador').innerText = valor;
}

function incrementar() {
  valor++;
  actualizarContador();
}

function disminuir() {
  valor--;
  actualizarContador();
}
