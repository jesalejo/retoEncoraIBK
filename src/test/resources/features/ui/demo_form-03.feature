#language: es
@web
Característica: Demo Form (#3)
  Orange HRM es un formulario WEB UI ejemplo para fines de entrenamiento
  Por favor ver (https://opensource-demo.orangehrmlive.com/web/index.php/auth/login)

  -- DESCRIPCION --

  Para este ejercicio, es necesario ingresar a la página 'https://opensource-demo.orangehrmlive.com/web/index.php/auth/login'
  para conocer el comportamiento de lo que se está solicitando en el escenario, sientete libre de navegar para entender el caso de prueba.

  - Comportamiento: Se encuentra descrito como comentarios en el cuerpo del Escenario.

  1) Es necesario completar y conocer todo el comportamiento para conocer el flujo end to end
  2) Escribir el comportamiento del caso de manera declarativa (de preferencia)
  3) Considerar el uso de DataTables
  4) Considerar el uso del patron de diseño Page Object Model (POM), puedes agregar clases, metodos y código para cumplir el patrón.
  5) Sientete libre de programar y agregar código si lo consideras necesario.
  6) Generar reportes Cucumber (html/json) y/o SerenityReport

  @caso2
  Escenario: Agregar y validar la creación de un nuevo empleado
    # Ingresar y validar el ingreso con las credenciales correctas
    # En el menú, ir a la opción PIM > 'Add Employee'
    # Agregar un nuevo empleado, ingresando los datos requeridos
      # (Salvar el ID del nuevo empleado agregado)
    # Completar y guardar los datos personales adicionales:
        # NickName
        # Driver's License Number
        # License Expiry Date
        # Nationality
        # Marital Status
        # Date of Birth
        # Gender
    # Ir a la opción 'Employee List', buscar al nuevo usuario creado por el ID y validar que existe
    # Cerrar sesión
