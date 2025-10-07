Feature: US08 - Contactar via correo

  Scenario: Visualización de la sección "Contáctanos"
    Given que el usuario accede a la landing page
    When el usuario navega por la página
    Then debe visualizar claramente un botón de enviar en la sección Contactanos

  Scenario: Acceso a enviar algún mensaje
    Given que el usuario desea contactar a la plataforma
    When el usuario visualiza la sección
    Then debe llenar el formulario para poder mandar algún mensaje que requiera
