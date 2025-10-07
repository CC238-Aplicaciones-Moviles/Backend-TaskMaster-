Feature: US21 - Gestión de sprints

  Scenario: Creación de Sprint
    Given que el Scrum Master está en la sección de "Backlog"
    When selecciona la opción "Crear Sprint"
    Then puede ingresar los detalles del sprint (nombre, duración, fechas de inicio y fin)
    And agregar los items correspondientes
    And al guardar, el sistema muestra un mensaje de éxito y el nuevo sprint pasa a ser el actual

  Scenario: Edición de Sprint
    Given que el Scrum Master está visualizando un sprint en progreso
    When selecciona la opción "Editar Sprint"
    Then puede modificar los detalles del sprint (nombre, fechas, duración)
    And al guardar, los cambios se reflejan en el sistema y se muestra un mensaje de éxito

  Scenario: Finalización de Sprint
    Given que el Scrum Master está visualizando un sprint activo
    When selecciona la opción "Finalizar Sprint"
    Then el sistema muestra una confirmación solicitando si se desea finalizar el sprint
    And al confirmar, el sprint se marca como completado y los backlog items no terminados se liberan para reasignación
    And se muestra un mensaje de éxito
