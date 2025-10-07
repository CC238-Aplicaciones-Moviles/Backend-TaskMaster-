Feature: Ver información de usuario

  Scenario: Mostrar información profesional de un miembro
    Given que el usuario está en “Members/Users” con miembros aceptados
    When busca un usuario y presiona “Show details”
    Then aparece un cuadro con su perfil profesional, formación y habilidades
