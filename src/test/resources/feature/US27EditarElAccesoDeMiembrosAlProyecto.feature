Feature: Gestión de acceso de miembros al proyecto

  Scenario: Enviar invitación para unirse al proyecto
    Given que el usuario ya creó su proyecto y está en la sección “Members/Users”
    When presiona “Invite Users”, coloca un correo en el campo y presiona “Send”
    Then se envía una invitación y se guarda en la lista visible en el cuadro emergente

  Scenario: Cancelar una invitación enviada
    Given que el usuario está en “Members/Users” y abre la lista de invitaciones
    When presiona el botón “X” de una invitación
    Then la invitación se cancela y se borra de la lista

  Scenario: Eliminar un miembro del proyecto
    Given que hay miembros en la sección “Members/Users”
    When busca al usuario, presiona “Remove” y confirma con “OK”
    Then el miembro es eliminado y pierde acceso al proyecto
