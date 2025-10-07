Feature: Crear incidencias

  Scenario: Crear una nueva incidencia
    Given que estoy en la sección de Issues
    When completo el formulario y lo envío
    Then la incidencia se crea y aparece en la lista

  Scenario: Asignar incidencia a un miembro
    Given que he creado una incidencia
    When asigno un miembro
    Then se asigna automáticamente a ese miembro
