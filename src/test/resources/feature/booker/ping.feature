#language: es

Característica: Prueba servicio
  yo como administrador
  necesito validar que el servicio este activo
  para poder realizar reservas


  Escenario: servicio activo
    Dado dado que el admininistrador se encuentra validando el servicio ping
    Cuando  realiza la peticion get
    Entonces recibe un codigo exitoso y el mensaje Created
