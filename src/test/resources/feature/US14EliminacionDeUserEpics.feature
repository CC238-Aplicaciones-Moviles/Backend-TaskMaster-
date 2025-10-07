Feature: US14 - Eliminación de user epics

  Scenario: Eliminación de Épica en el Backlog
    Given que el Product Owner está en la sección "Backlog Items"
    When selecciona una epic para eliminarla
    Then el sistema solicita confirmación
    And al confirmar, la epic es eliminada y se muestra un mensaje de éxito
