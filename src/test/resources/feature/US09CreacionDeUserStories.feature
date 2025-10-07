Feature: US09 - Creación de user stories

  Scenario: Creación de Nueva User Story en el Backlog
    Given que el Product Owner está en la sección "Backlog items"
    When selecciona la opción "Crear User Story"
    Then puede ingresar el título, descripción, prioridad y criterios de aceptación de la historia
    And al guardar, la nueva user story aparece en la vista user stories y backlog
