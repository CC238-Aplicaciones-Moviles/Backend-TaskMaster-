Feature: Editar incidencias

  Scenario: Editar información de una incidencia
    Given que estoy viendo una incidencia
    When hago clic en “Editar”
    Then aparece un formulario precargado para modificarla

  Scenario: Cambiar estado de una incidencia
    Given que edité una incidencia
    When guardo los cambios
    Then el estado se actualiza en la lista
