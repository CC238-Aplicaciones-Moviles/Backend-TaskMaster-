Feature: US13 - Modificación de user epics

  Scenario: Edición de Épica en el Backlog
    Given que el Product Owner está en la sección "Backlog Items"
    When selecciona una epic para editarla
    Then puede modificar el título, descripción y criterios de éxito
    And al guardar, los cambios se reflejan en la vista epics con un mensaje de éxito
