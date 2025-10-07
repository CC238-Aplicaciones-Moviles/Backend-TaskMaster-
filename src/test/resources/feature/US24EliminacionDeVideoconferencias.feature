Feature: US24 - Eliminar videoconferencia existente

  Scenario: Eliminar videoconferencia
    Given que el Scrum Master está en la sección de reuniones
    When selecciona una videoconferencia para eliminarla
    Then el sistema solicita confirmación
    And al confirmar, la videoconferencia es eliminada de la lista
