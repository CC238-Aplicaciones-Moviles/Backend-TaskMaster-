Feature: US11 - Eliminación de user stories

  Scenario: Eliminación de User Story en el Backlog
    Given que el Product Owner está en la sección "Backlog items"
    When selecciona una user story para eliminarla
    Then el sistema solicita confirmación
    And al confirmar, la user story es eliminada del backlog y la vista user stories, mostrándose un mensaje de éxito
