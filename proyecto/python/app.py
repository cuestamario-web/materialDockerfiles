# Proyecto Python
# Lee variables de entorno (ENV) en tiempo de EJECUCIÓN y muestra una ficha.
import os
import platform

from colorama import Fore, Style

# Valores leídos del entorno (con un valor por defecto por si no existen)
proyecto = os.getenv("PROYECTO", "(no definido)")
nombre = os.getenv("APP_NOMBRE", "Invitado")
entorno = os.getenv("APP_ENTORNO", "desarrollo")
color = os.getenv("APP_COLOR", "verde")

# Traducción de nombres de color en español a colores de colorama
colores = {
    "verde": Fore.GREEN,
    "azul": Fore.BLUE,
    "rojo": Fore.RED,
    "amarillo": Fore.YELLOW,
    "magenta": Fore.MAGENTA,
}
c = colores.get(color, Fore.WHITE)
fin = Style.RESET_ALL

# Mensaje distinto según el entorno
mensajes = {
    "desarrollo": "Modo DESARROLLO: logs detallados activados",
    "pruebas": "Modo PRUEBAS: usando base de datos de pruebas",
    "produccion": "Modo PRODUCCION: logs mínimos, máximo rendimiento",
}
mensaje = mensajes.get(entorno, f"Entorno desconocido: {entorno}")

print(c + "==============================================" + fin)
print(c + f"   Hola desde Python {platform.python_version()}" + fin)
print(c + "==============================================" + fin)
print(f"  Proyecto (ARG -> ENV) : {proyecto}")
print(f"  Nombre   (APP_NOMBRE) : {nombre}")
print(f"  Entorno  (APP_ENTORNO): {entorno}")
print(f"  Color    (APP_COLOR)  : {color}")
print(c + f"  >> {mensaje}" + fin)
print(c + "==============================================" + fin)
