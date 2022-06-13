#language: es

Característica: teristica: yo como administrador  deseo poder loguearme en la app que se
genere un token para poder actualizar o eliminar reservas
rest

  Escenario: login
    Dado  el usuario esta en la pagina de inicio de sesion con el de usuario "admin" y contraseña "password123"
    Cuando  el usuario ingrese la peticion que desea
    Entonces se mostrara un codiogo y una respuesta  exitosa y un token