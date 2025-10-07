Feature: US05 - Resumen de la Empresa, Misión y Visión

  Scenario: El visitante quiere conocer más sobre la empresa antes de registrarse
    Given que el visitante navega por la sección "Acerca de" en la página principal
    When lee el contenido de la misión y visión
    Then puede obtener un resumen claro y conciso sobre la empresa y sus valores
