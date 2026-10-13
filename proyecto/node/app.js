// Proyecto Node.js
// Lee variables de entorno (ENV) en tiempo de EJECUCIÓN y muestra una ficha.
const chalk = require("chalk");

// Valores leídos del entorno (con un valor por defecto por si no existen)
const proyecto = process.env.PROYECTO || "(no definido)";
const nombre = process.env.APP_NOMBRE || "Invitado";
const entorno = process.env.APP_ENTORNO || "desarrollo";
const color = process.env.APP_COLOR || "verde";

// Traducción de nombres de color en español a funciones de chalk
const colores = {
  verde: chalk.green,
  azul: chalk.blue,
  rojo: chalk.red,
  amarillo: chalk.yellow,
  magenta: chalk.magenta,
};
const pintar = colores[color] || chalk.white;

// Mensaje distinto según el entorno
const mensajes = {
  desarrollo: "Modo DESARROLLO: logs detallados activados",
  pruebas: "Modo PRUEBAS: usando base de datos de pruebas",
  produccion: "Modo PRODUCCION: logs mínimos, máximo rendimiento",
};
const mensaje = mensajes[entorno] || `Entorno desconocido: ${entorno}`;

console.log(pintar("=============================================="));
console.log(pintar("   Hola desde Node.js " + process.version));
console.log(pintar("=============================================="));
console.log(`  Proyecto (ARG -> ENV) : ${proyecto}`);
console.log(`  Nombre   (APP_NOMBRE) : ${nombre}`);
console.log(`  Entorno  (APP_ENTORNO): ${entorno}`);
console.log(`  Color    (APP_COLOR)  : ${color}`);
console.log(pintar(`  >> ${mensaje}`));
console.log(pintar("=============================================="));
