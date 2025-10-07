Feature: US15 - Creación de tasks

  Scenario: Creación de Nueva Task en el Backlog
    Given que el desarrollador está en la sección “Backlog Items”
    When selecciona la opción "Crear Task"
    Then puede ingresar el título, descripción de la task, user story relacionada y encargados
    And al guardar, la task aparece listada en la vista Tasks y en el product backlog con un mensaje de éxito
