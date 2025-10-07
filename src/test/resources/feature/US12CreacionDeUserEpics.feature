Feature: US12 - Creación de user epics

  Scenario: Creación de Nueva Épica en el Backlog
    Given que el Product Owner está en la sección "Backlog Items"
    When selecciona la opción "Crear Epic"
    Then puede ingresar el título, descripción y criterios de éxito de la epic
    And al guardar, la nueva epic aparece listada en la vista epics con un mensaje de éxito
