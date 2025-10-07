Feature: Creación automática de eventos en issues

  Scenario: Evento automático al crear issue
    Given que soy un miembro del equipo
    When creo un issue
    Then se genera un evento de creación con fecha, creador y descripción

  Scenario: Evento automático al asignar issue
    Given que soy un miembro del equipo
    When asigno un issue
    Then se genera un evento de asignación con fecha, miembro y descripción
