// script.js
function mostrarNombre() {
  const nombre = document.getElementById('nombre').value; // tomar el valor del input
  document.getElementById('saludo').innerText = 'Hola, ' + nombre; // mostrar saludo
}
