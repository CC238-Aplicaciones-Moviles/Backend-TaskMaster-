Feature: US04 - Iniciar Sesión Rápido y Visible

  Scenario: Un usuario de la plataforma busca iniciar sesión
    Given que el usuario accede a la página principal
    When localiza el enlace de inicio de sesión en la esquina superior derecha
    Then puede hacer clic en el enlace e iniciar sesión fácilmente
