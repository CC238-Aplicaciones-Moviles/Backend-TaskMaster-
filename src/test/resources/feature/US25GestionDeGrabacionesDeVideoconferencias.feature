Feature: Gestión de grabaciones de videoconferencias

  Scenario: Filtrar grabaciones por búsqueda o fecha
    Given que el Product Owner está en la sección de Grabaciones
    When utiliza el campo de búsqueda o filtros de rango de fechas
    Then podrá filtrar las grabaciones

  Scenario: Editar nombre o enlace de una grabación
    Given que el Product Owner selecciona una grabación
    When edite el nombre o el enlace
    Then podrá guardar los cambios
