Feature: US16 - Modificación de tasks

  Scenario: Edición de Tasks en el Backlog
    Given que el desarrollador está visualizando una task en la sección “Backlog Items”
    When selecciona la opción "Editar Task"
    Then puede modificar el título, descripción de la task, encargado
    And al guardar, los cambios se reflejan dentro de la vista Tasks y en el product backlog con un mensaje de éxito
