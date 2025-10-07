Feature: Eliminar incidencias

  Scenario: Confirmar eliminación de incidencia
    Given que estoy viendo una incidencia
    When hago clic en “Eliminar”
    Then la incidencia se elimina
