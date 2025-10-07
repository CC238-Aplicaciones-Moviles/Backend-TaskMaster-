Feature: US17 - Eliminación de tasks

  Scenario: Eliminación de Tasks en el Backlog
    Given que el desarrollador está visualizando una task en la sección “Backlog Items”
    When selecciona la opción "Eliminar Task"
    Then el sistema solicita confirmación
    And al confirmar, la task es eliminada y se muestra un mensaje de éxito
