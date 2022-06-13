#language: es

Característica: actualizar publicaciones
  como cliente deseo poder actualizar la informacion de las
  publicaciones con el fin de poder corregir posibles errores


  Escenario: actualizacion publicaciones
    Dado el usuario tiene toda la informacion del post a actualizar
    Cuando el usuario realiza la peticion
  Entonces  recibe el codigo 200 y el contenido actualizado