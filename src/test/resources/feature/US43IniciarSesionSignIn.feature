Feature: Iniciar sesión

  Scenario: Inicio de sesión exitoso
    Given que estoy en la pantalla de inicio de sesión
    When ingreso credenciales válidas y hago clic en “Iniciar sesión”
    Then soy redirigido a la página principal

  Scenario: Credenciales inválidas
    Given que estoy en la pantalla de inicio de sesión
    When ingreso credenciales incorrectas y hago clic en “Iniciar sesión”
    Then recibo un mensaje de error
