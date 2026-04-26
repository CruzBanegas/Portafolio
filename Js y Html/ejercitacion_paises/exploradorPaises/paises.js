// Recibe un arreglo de países y los muestra en la tabla
function renderTabla(paises) {
  const tbody = document.getElementById("tablaBody");
  tbody.innerHTML = ""; // limpiar contenido anterior

  paises.forEach(pais => {
    const fila = `
      <tr>
        <td><img src="${pais.bandera}" width="40" alt="${pais.nombre}"></td>
        <td>${pais.nombre}</td>
        <td>${pais.capital}</td>
        <td>${pais.poblacion.toLocaleString("es-AR")}</td>
        <td>${pais.area.toLocaleString("es-AR")}</td>
      </tr>
    `;
    tbody.innerHTML += fila;
  });
}

// Cargar países desde API
async function cargarPaises() {
  const mensaje = document.getElementById("mensaje");
  mensaje.textContent = "Cargando países...";

  try {
    //  TODO 1
    const res = await fetch("https://restcountries.com/v3.1/region/europe");

    // Verificar que la respuesta sea exitosa

    if (!res.ok) {
      throw new Error(`Error HTTP: ${res.status}`);
    }

    //  TODO 2
    const datos = await res.json();

    // Transformación
    const paises = datos.map(p => ({
      nombre: p.name.common,
      capital: p.capital ? p.capital[0] : "Sin capital",

      //  TODO 3
      poblacion: p.population,
      area: p.area,

      bandera: p.flags.png
    }));

    //  TODO 4
    renderTabla(paises);

    mensaje.textContent = `Se cargaron ${paises.length} países.`;

  } catch (err) {
    mensaje.textContent = `Error: ${err.message}`;
    mensaje.classList.add("text-danger");
  }
}

// Evento botón
document.getElementById("btnCargar")
  .addEventListener("click", cargarPaises);