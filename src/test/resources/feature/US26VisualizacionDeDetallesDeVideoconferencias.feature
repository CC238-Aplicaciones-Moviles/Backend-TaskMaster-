Feature: Visualización de detalles de videoconferencias

  Scenario: Mostrar detalles al hacer clic en el título
    Given que el Scrum Master está en la lista de videoconferencias
    When haga clic en el título de una videoconferencia
    Then se mostrarán los detalles en un cuadro modal

  Scenario: Cerrar el modal de detalles
    Given que el modal de detalles está abierto
    When el Scrum Master haga clic en el botón de cerrar
    Then el modal se cerrará y regresará a la lista
