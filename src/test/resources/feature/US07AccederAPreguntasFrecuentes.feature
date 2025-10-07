Feature: US07 - Acceder a Preguntas Frecuentes

  Scenario: Visualización de la sección
    Given que el usuario accede a la landing page
    When el usuario busca la sección de "Preguntas Frecuentes"
    Then la sección debe estar visible en el menú o al final de la página

  Scenario: Mostrar preguntas frecuentes
    Given que el usuario está en la sección de "Preguntas Frecuentes"
    When el usuario hace clic en una pregunta
    Then debe desplegarse la respuesta asociada de forma dinámica (sin recargar la página)

  Scenario: Ocultar respuesta
    Given que el usuario ha desplegado una respuesta de la sección de "Preguntas Frecuentes"
    When el usuario hace clic nuevamente en la misma pregunta
    Then la respuesta debe colapsarse, ocultándose de nuevo
