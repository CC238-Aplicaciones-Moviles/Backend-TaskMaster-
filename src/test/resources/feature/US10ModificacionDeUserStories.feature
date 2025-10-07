Feature: US10 - Modificación de user stories

  Scenario: Edición de User Story en el Backlog
    Given que el Product Owner está en la sección "Backlog items"
    When selecciona una user story para editarla
    Then puede modificar su título, descripción, prioridad y criterios de aceptación
    And al guardar, los cambios se reflejan en la vista user stories y el backlog con un mensaje de éxito
