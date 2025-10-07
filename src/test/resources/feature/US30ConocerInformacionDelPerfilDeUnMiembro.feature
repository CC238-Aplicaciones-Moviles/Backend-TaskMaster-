Feature: Ver perfil de un miembro

  Scenario: Acceder al perfil de un miembro
    Given que el usuario está en la lista de miembros del proyecto
    When busca un miembro y presiona “Show details”
    Then se muestra su perfil con foto, ubicación e intereses

  Scenario: Miembro sin información disponible
    Given que el usuario está en la lista de miembros del proyecto
    When busca un miembro sin perfil y presiona “Show details”
    Then aparece un mensaje indicando que no hay información disponible

  Scenario: Cerrar perfil y volver a la lista
    Given que está en el perfil de un miembro
    When presiona el botón “X”
    Then vuelve a la lista de miembros
