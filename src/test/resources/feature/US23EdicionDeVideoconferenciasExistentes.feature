Feature: US23 - Edición de videoconferencias existentes

  Scenario: Selección de videoconferencia para edición
    Given que el Product Owner está en la lista de videoconferencias
    When selecciona una videoconferencia para editarla
    Then puede modificar el título, fecha, hora y enlace de la videoconferencia
    And al guardar, se reflejan los cambios
