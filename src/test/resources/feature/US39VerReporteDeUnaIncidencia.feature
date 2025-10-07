Feature: Ver reporte de una incidencia

  Scenario: Ver información detallada de incidencia
    Given que el usuario selecciona una incidencia
    When accede al reporte
    Then ve una descripción, historial, estado actual y asignaciones
