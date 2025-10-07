Feature: US19 - Visualización del product backlog

  Scenario: Visualización del Product Backlog y Estado de Ítems
    Given que el usuario está en la sección principal del sistema
    When selecciona la opción "Backlog" en el sidebar
    Then puede observar el product backlog con los diferentes backlog items existentes
    And cada item visualiza el estado de cada item
