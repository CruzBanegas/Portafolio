// script.js
function agregarTarea() {
  const tareaInput = document.getElementById('tarea');
  const tareaTexto = tareaInput.value;

  if (tareaTexto.trim() === '') return; // no agregar tareas vacías

  const li = document.createElement('li'); // crear elemento <li>
  li.innerText = tareaTexto;               // asignar el texto
  document.getElementById('lista').appendChild(li); // agregar a la lista

  tareaInput.value = ''; // limpiar input
}
