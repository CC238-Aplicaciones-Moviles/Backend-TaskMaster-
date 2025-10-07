Feature: US22 - Gestión de nuevas videoconferencias desde la sección de Reuniones

  Scenario: Agregar una nueva videoconferencia
    Given que el Scrum Master está en la sección de Reuniones
    When hace clic en el botón "+"
    Then se abrirá un formulario para agregar la videoconferencia

  Scenario: Guardar una videoconferencia
    Given que el Scrum Master está completando el formulario
    When ingresa el título, fecha, hora y enlace de la videoconferencia
    Then podrá guardarla
