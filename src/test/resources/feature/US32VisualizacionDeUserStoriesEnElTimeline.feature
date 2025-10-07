Feature: Visualización de User Stories en el timeline

  Scenario: Mostrar User Stories en columnas por sprint
    Given que el Scrum Master está en la vista del timeline
    When visualiza el timeline
    Then ve columnas con sprints y User Stories correspondientes

  Scenario: Mostrar detalles de User Stories debajo del timeline
    Given que el Scrum Master está revisando el timeline
    When visualiza la tabla inferior
    Then aparece un cuadro con el título y estimación de cada User Story
