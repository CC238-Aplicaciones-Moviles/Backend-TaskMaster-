Feature: US18 - Vista centrada en backlog items

  Scenario: Gestión de Ítems en el Backlog
    Given que el usuario está en la sección "Backlog"
    When selecciona la opción "Backlog Items"
    Then puede ver todas las épicas, user stories y tasks agrupadas
    And realizar acciones como crear, editar o eliminar directamente desde la vista
    And al realizar cualquier acción, se muestra un mensaje de éxito
