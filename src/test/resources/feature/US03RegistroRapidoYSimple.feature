Feature: US03 - Registro Rápido y Simple

  Scenario: Un nuevo usuario desea registrarse
    Given que el usuario nuevo accede a la página de registro
    When completa el formulario con los datos básicos
    Then puede finalizar el registro sin complicaciones ni pasos adicionales innecesarios
