Feature: Ver estimación de esfuerzo

  Scenario: Ver estimación en el timeline
    Given que hay User Stories en el timeline
    When el Desarrollador visualiza una historia
    Then ve la estimación de esfuerzo asociada

  Scenario: Mostrar estimación con color de la historia
    Given que hay varias User Stories
    When accede a la tabla de historias
    Then ve la estimación con el mismo color de la historia correspondiente
