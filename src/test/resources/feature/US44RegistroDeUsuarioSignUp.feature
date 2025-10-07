Feature: Registro de usuario

  Scenario: Registro exitoso
    Given que estoy en la pantalla de registro
    When ingreso un correo único y contraseña válida y hago clic en “Registrarse”
    Then recibo confirmación de registro y acceso al sistema
