Feature: Filtrar incidencias

  Scenario: Filtrar por Sprint
    Given que estoy en la sección de Issues
    When selecciono un Sprint
    Then se muestran solo las incidencias asociadas a ese Sprint

  Scenario: Filtrar por Prioridad
    Given que estoy en la sección de Issues
    When selecciono un nivel de Prioridad
    Then se muestran solo las incidencias con esa prioridad

  Scenario: Filtrar por Sprint y Prioridad
    Given que estoy en la sección de Issues
    When selecciono ambos filtros
    Then se muestran solo las incidencias que cumplan ambos criterios
