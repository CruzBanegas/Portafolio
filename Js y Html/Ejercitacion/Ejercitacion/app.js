// Variable global
let mensajeGlobal = "¡Bienvenido a tu lista de tareas!";

function inicializarLista() {
    const mensajeInicial = mensajeGlobal;

    mostrarMensaje(mensajeInicial);
    mostrarTareasIniciales();
    configurarBotonAgregarTarea();
}

function mostrarMensaje(texto) {
    const elementoMensaje = document.getElementById('mensaje');
    elementoMensaje.textContent = texto;
}

function mostrarTareasIniciales() {
    const tareas = ["Comprar víveres", "Estudiar JavaScript", "Hacer ejercicio", "Llamar a un amigo"];
    const listaTareasElemento = document.getElementById('lista-tareas');

    tareas.forEach(tarea => {
        const elementoLista = document.createElement('li');
        elementoLista.textContent = tarea;
        listaTareasElemento.appendChild(elementoLista);
    });
}

function configurarBotonAgregarTarea() {
    const botonAgregar = document.getElementById('boton-agregar-tarea');

    botonAgregar.addEventListener('click', function() {
        const nuevaTarea = prompt("Escribe la nueva tarea:");

        if (nuevaTarea && nuevaTarea.trim() !== "") {
            const listaTareasElemento = document.getElementById('lista-tareas');
            const elementoLista = document.createElement('li');
            elementoLista.textContent = nuevaTarea;
            listaTareasElemento.appendChild(elementoLista);

            mostrarMensaje("¡Tarea guardada con éxito!");
        } else {
            mostrarMensaje("No has escrito ninguna tarea.");
        }
    });
}

// Inicializar lista al cargar el script
inicializarLista();

// Ver la variable global en consola
console.log("Variable global mensajeGlobal:", mensajeGlobal);
