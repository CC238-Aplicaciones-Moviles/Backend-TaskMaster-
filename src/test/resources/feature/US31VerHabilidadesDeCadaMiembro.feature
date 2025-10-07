Feature: Ver habilidades de miembros

  Scenario: Ver habilidades registradas
    Given que el usuario está en la lista de miembros del proyecto
    When busca un usuario y presiona “Show skills”
    Then se muestra la lista de habilidades del miembro

  Scenario: Miembro sin habilidades
    Given que el usuario está en la lista de miembros del proyecto
    When busca un usuario sin habilidades y presiona “Show skills”
    Then aparece un mensaje indicando que no hay habilidades disponibles
