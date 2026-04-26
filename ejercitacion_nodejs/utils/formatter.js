// utils/formatter.js

// TODO 1: exportar función formatearPrecio(numero)
export function formatearPrecio(numero) {
  return numero.toLocaleString('es-AR', {
    style: 'currency',
    currency: 'ARS'
  });
}

// TODO 2: exportar función calcularTotal(precio, cantidad)
export function calcularTotal(precio, cantidad) {
  return precio * cantidad;
}