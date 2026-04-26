// ─────────────────────────────────────────────────────
// PARTE A: MODELO DE DATOS
// ─────────────────────────────────────────────────────

class Persona {
  constructor(nombre, correo, profesion, fechaNacimiento) {
    this.nombre = nombre;
    this.correo = correo;
    this.profesion = profesion;
    this.fechaNacimiento = fechaNacimiento;
  }

  edad() {
    if (!(this.fechaNacimiento instanceof Date)) return 0;

    const hoy = new Date();
    let edad = hoy.getFullYear() - this.fechaNacimiento.getFullYear();

    const mes = hoy.getMonth() - this.fechaNacimiento.getMonth();

    if (mes < 0 || (mes === 0 && hoy.getDate() < this.fechaNacimiento.getDate())) {
      edad--;
    }

    return edad;
  }

  esMayor() {
    return this.edad() >= 18;
  }

  toString() {
    return `${this.nombre} -  ${this.correo} - ${this.profesion} - ${this.edad()} años`;
  }
}


// ─────────────────────────────────────────────────────
// DATOS DE PRUEBA
// ─────────────────────────────────────────────────────

const personas = [
  new Persona("Ana García",    "ana@mail.com",   "Médica",    new Date(1985, 3, 10)),
  new Persona("Luis Herrera",  "luis@mail.com",  "Ingeniero", new Date(2007, 10, 20)),
  new Persona("Marta Ruiz",    "marta@mail.com", "Médica",    new Date(1972, 6, 1)),
  new Persona("Pedro López",   "pedro@mail.com", "Docente",   new Date(1990, 1, 28)),
  new Persona("Carla Soto",    "carla@mail.com", "Médica",    new Date(1998, 8, 15)),
  new Persona("Jorge Díaz",    null,             "Abogado",   new Date(2008, 0, 5)),
  new Persona("Valeria Torres","val@mail.com",   "Docente",   new Date(1965, 5, 22)),
];


// ─────────────────────────────────────────────────────
// PARTE B: CONSULTAS FUNCIONALES
// ─────────────────────────────────────────────────────

function mayoresDeEdad(listaPersonas) {
  if (!Array.isArray(listaPersonas)) {
    console.warn("Lista inválida");
    return [];
  }

  return listaPersonas.filter(p => {
    if (!p || typeof p.edad !== "function") {
      console.warn("Elemento inválido:", p);
      return false;
    }
    return p.edad() >= 18;
  });
}


function personasPorProfesion(listaPersonas, profesion) {
  if (!Array.isArray(listaPersonas)) {
    console.warn("Lista inválida");
    return [];
  }

  if (typeof profesion !== "string" || profesion.trim() === "") {
    console.warn("Profesión inválida");
    return [];
  }

  return listaPersonas.filter(p => {
    if (!p || typeof p.profesion !== "string") {
      console.warn("Elemento inválido:", p);
      return false;
    }
    return p.profesion.toLowerCase() === profesion.toLowerCase();
  });
}


function obtenerPersonaMasGrande(listaPersonas) {
  if (!Array.isArray(listaPersonas)) {
    console.warn("Lista inválida");
    return null;
  }

  if (listaPersonas.length === 0) {
    console.warn("Lista vacía");
    return null;
  }

  return listaPersonas.reduce((mayor, actual) => {
    if (!actual || typeof actual.edad !== "function") {
      console.warn("Elemento inválido:", actual);
      return mayor;
    }

    if (!mayor) return actual;

    return actual.edad() > mayor.edad() ? actual : mayor;
  }, null);
}


function obtenerProfesiones(listaPersonas) {
  if (!Array.isArray(listaPersonas)) {
    console.warn("Lista inválida");
    return [];
  }

  const resultado = [];

  listaPersonas.forEach(p => {
    if (!p || typeof p.profesion !== "string") {
      console.warn("Elemento inválido:", p);
      return;
    }

    const existe = resultado.some(
      prof => prof.toLowerCase() === p.profesion.toLowerCase()
    );

    if (!existe) {
      resultado.push(p.profesion);
    }
  });

  return resultado;
}


// ─────────────────────────────────────────────────────
// BONUS
// ─────────────────────────────────────────────────────

const correosMedicas = personas
  .filter(p => p.esMayor()) // paso 1: filtra mayores de edad
  .filter(p => p.profesion.toLowerCase() === "médica") // paso 2: filtra médicas
  .map(p => p.correo) // paso 3: obtiene correos
  .filter(c => c) // elimina null
  .sort(); // paso 4: ordena alfabéticamente

console.log("Correos médicas mayores:", correosMedicas);


// ─────────────────────────────────────────────────────
// PRUEBAS
// ─────────────────────────────────────────────────────

console.log("\n═══════════════════════════════════");
console.log("  PRUEBAS DE FUNCIONALIDAD");
console.log("═══════════════════════════════════");

personas.forEach(p => console.log(p.toString()));

const mayores = mayoresDeEdad(personas);
console.log(`\nMayores de edad (${mayores.length}):`,
  mayores.map(p => p.nombre).join(', '));

const medicas = personasPorProfesion(personas, "médica");
console.log(`Médicas (${medicas.length}):`,
  medicas.map(p => p.nombre).join(', '));

const grande = obtenerPersonaMasGrande(personas);
console.log(`Persona más grande: ${grande?.nombre} (${grande?.edad()} años)`);

const profs = obtenerProfesiones(personas);
console.log(`Profesiones únicas: ${profs.join(', ')}`);

console.log("\n═══════════════════════════════════");
console.log("  CASOS BORDE — No debe explotar");
console.log("═══════════════════════════════════");

mayoresDeEdad(null);
mayoresDeEdad(undefined);
mayoresDeEdad([]);
mayoresDeEdad("cadena");
personasPorProfesion(personas, null);
personasPorProfesion(personas, "");
obtenerPersonaMasGrande([]);