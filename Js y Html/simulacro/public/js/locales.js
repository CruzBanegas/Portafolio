
async function cargarLocales() {
  try {
    const response = await fetch("/api/locales");
    const locales = await response.json();

    const tbody = document.getElementById("bodyTablaLocales");
    tbody.innerHTML = ""; // Limpiar contenido previo

    locales.forEach((local) => {
      const fila = document.createElement("tr");
      fila.innerHTML = `
        <td>${local.storeName}</td>
        <td>${local.streetAddress}</td>
        <td>${local.city}</td>
        <td>${local.country}</td>
        <td>${local.semiemisferio}</td>
      `;
      tbody.appendChild(fila);
    });

  } catch (error) {
    console.error("Error al cargar locales:", error);
  }
}

async function filtrarLocales(evt) {
  if (evt)
    evt.preventDefault;

  const texto = document.getElementById("texto").value;
  const hemisferio = document.getElementById("hemisferio").value;

  try {
    const response = await fetch(`/api/locales?texto=${encodeURIComponent(texto)}&hemisferio=${encodeURIComponent(hemisferio)}`);
    const locales = await response.json();

    const tbody = document.getElementById("bodyTablaLocales");
    tbody.innerHTML = "";

    locales.forEach((local) => {
      const fila = document.createElement("tr");
      fila.innerHTML = `
        <td>${local.storeName}</td>
        <td>${local.streetAddress}</td>
        <td>${local.city}</td>
        <td>${local.country}</td>
        <td>${local.semiemisferio}</td>
      `;
      tbody.appendChild(fila);
    });

  } catch (error) {
    console.error("Error al aplicar filtros:", error);
  }
}