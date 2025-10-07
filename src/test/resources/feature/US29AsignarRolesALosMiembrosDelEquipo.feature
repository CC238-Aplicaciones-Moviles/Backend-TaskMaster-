Feature: Asignación de roles SCRUM

  Scenario: Agregar rol a un miembro
    Given que el usuario está en “Members/Users” con un miembro sin rol
    When edita y agrega un rol desde el cuadro emergente
    Then el rol se guarda y se otorgan los permisos correspondientes

  Scenario: Cambiar rol de un miembro
    Given que el usuario está en “Members/Users” con un miembro con rol
    When edita, cambia los roles y presiona “Apply”
    Then los nuevos roles se guardan y se actualizan sus permisos
