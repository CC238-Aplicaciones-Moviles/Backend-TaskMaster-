Feature: Ver historial de una incidencia

  Scenario: Mostrar historial completo de eventos
    Given que tengo acceso a un issue
    When visualizo su detalle
    Then veo el historial con usuario, fecha y descripción
