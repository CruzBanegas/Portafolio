// index.js

import fs from "node:fs/promises";
import path from "node:path";
import { fileURLToPath } from "node:url";

// TODO 3: importar formatearPrecio y calcularTotal
import { formatearPrecio, calcularTotal } from "./utils/formatter.js";

const __filename = fileURLToPath(import.meta.url);
const __dirname = path.dirname(__filename);

// ── Leer el JSON local ──────────────────────────────────────────
async function leerProductos() {
  const ruta = path.join(__dirname, 'data', 'productos.json');
  const raw = await fs.readFile(ruta, 'utf-8');
  return JSON.parse(raw);
}

// ── Obtener posts de JSONPlaceholder (simulan comentarios/reseñas) ─
async function obtenerResenas() {
  const res = await fetch('https://jsonplaceholder.typicode.com/posts?_limit=5');

  if (!res.ok) throw new Error(`HTTP ${res.status}`);

  return res.json();
}

// ── Función principal ────────────────────────────────────────────
async function main() {
  const productos = await leerProductos();
  const resenas = await obtenerResenas();

  // TODO 4: usar .map()
  const reporte = productos.map(p => ({
    id: p.id,
    nombre: p.nombre,
    precio: formatearPrecio(p.precio),
    valorTotal: calcularTotal(p.precio, p.stock),
    stock: p.stock,
    resena: resenas[p.id - 1]?.title ?? 'Sin reseña'
  }));

  console.log('\n📦 Reporte de productos:\n');
  console.table(reporte);
}

main().catch(console.error);