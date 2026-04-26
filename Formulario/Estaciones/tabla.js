// Definimos un array de objetos con personajes de Star Wars
const personajesStarWars = [
    {id:1, nombre:'Darth Vader', direccion: 'Estrella de la muerte', barrio:'espacio', activo:true}, 
    {id:2, nombre:'Luke Skywalker', direccion: 'Tatooine', barrio: 'Halcon Milenario', activo:false}, 
    {id:3, nombre:'Princesa Leila', direccion: 'Polis Massa', barrio:'Halcon Milenario', activo:true}
]

// Función que agrega UNA fila a la tabla
function agregarFila(personaje, tabla) {
    // HTML que representa una fila de la tabla
    const fila = `<tr class="fila">
        <td>${personaje.id}</td>
        <td>${personaje.nombre}</td>
        <td>${personaje.direccion}</td>
        <td>${personaje.barrio}</td>
        <td>${personaje.activo}</td>
    </tr>`

    // insertRow agrega una nueva fila al final de la tabla (tabla.rows.length = última posición)
    const htmlFila = tabla.insertRow(tabla.rows.length)

    // innerHTML coloca dentro de esa fila el contenido que armamos en la variable fila
    htmlFila.innerHTML = fila 
}

// Función que agrega TODAS las filas de un array
function agregarFilas(array, tabla) {
    // Recorremos con un for...of cada personaje del array
    for (let personaje of array) {
        agregarFila(personaje, tabla) // Llamamos a la función agregarFila por cada objeto
    }
}

// Función para filtrar por nombre
function filtrar(aBuscar, tabla) {
    // filter recorre el array y devuelve solo los objetos que cumplan la condición
    const filtrados = personajesStarWars.filter((personaje) => {
        // includes devuelve true si el nombre contiene el texto buscado
        return personaje.nombre.includes(aBuscar)  
        // Nota: si usáramos === sería coincidencia exacta, no parcial
    })

    // Agregamos las filas filtradas a la tabla
    agregarFilas(filtrados, tabla)
}

// Manejo de eventos (callback)
document.addEventListener('DOMContentLoaded', (event) => {
    // console.log(event) // Podría mostrar info del evento cuando carga el DOM

    // 1. Referencia a la tabla
    const tabla = document.getElementById('tablaStarWars')

    // 2. Al cargar la página, mostramos todos los personajes
    agregarFilas(personajesStarWars, tabla)

    // 3. Referencia al botón de filtrado
    const btnFiltrar = document.getElementById('btnFiltrar')

    // 4. Cuando hago click en el botón, se ejecuta la función flecha
    btnFiltrar.addEventListener('click', (event) => {
        // Tomamos el valor del input de texto, quitando espacios con trim()
        const value = document.getElementById('texto').value.trim()

        // Mostramos por consola lo que se escribió (debug)
        console.log('click value:', value)

        // Filtramos la tabla con ese valor
        filtrar(value, tabla)
    })
})

