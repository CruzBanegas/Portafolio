// Pedir los dos números al usuario
let numero1 = Number(prompt("Ingrese el primer número:"));
let numero2 = Number(prompt("Ingrese el segundo número:"));

// Sumar los números
let suma = numero1 + numero2;

// Mostrar la suma en la consola
console.log("La suma es: " + suma);

// Verificar si la suma es mayor a 10
if (suma > 10) {
    console.log("La suma es mayor a 10.");
} else {
    console.log("La suma no es mayor a 10.");
}