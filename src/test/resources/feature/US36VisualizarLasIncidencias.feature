Feature: Ver incidencias

  Scenario: Mostrar lista de incidencias
    Given que estoy en la sección de Issues
    When accedo a la lista
    Then se muestra una tabla con título, estado, prioridad, asignado y fecha

  Scenario: Filtrar incidencias
    Given que estoy viendo la lista de incidencias
    When aplico un filtro
    Then se muestran solo las que cumplen el criterio
