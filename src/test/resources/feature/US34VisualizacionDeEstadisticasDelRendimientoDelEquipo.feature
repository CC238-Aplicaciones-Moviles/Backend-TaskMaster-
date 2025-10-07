Feature: Ver estadísticas de rendimiento

  Scenario: Mostrar panel con estadísticas del equipo
    Given que el Product Owner está en la sección de estadísticas
    When selecciona la opción para ver estadísticas
    Then visualiza un panel con historias completadas y pendientes

  Scenario: Mostrar estados con colores
    Given que hay User Stories en estadísticas
    When las visualiza
    Then se representan en verde (completadas) y rojo (en progreso)

  Scenario: Mostrar detalles al pasar el cursor
    Given que está en el panel de estadísticas
    When pasa el cursor sobre una historia
    Then se muestra un cuadro con detalles relevantes
