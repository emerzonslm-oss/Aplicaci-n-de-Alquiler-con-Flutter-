# Plan de Implementación: Registrar Propiedad (Simulado)

Este plan detalla la implementación del requerimiento "Registrar propiedad" para la aplicación AlquiGo. La funcionalidad incluirá un formulario para ingresar detalles de la propiedad y una selección de imagen simulada desde la galería local, sin persistencia en servidor ni uso de ViewModels externos para esta pantalla.

## Cambios Propuestos

### Modelos de Datos

#### [NEW] [Property.kt](file:///C:/Users/Andy2/AndroidStudioProjects/Aplicaci-n-de-Alquiler-con-Flutter-/app/src/main/java/com/example/alquigo/data/model/Property.kt)
- Definición de la data class `Property` con campos: `titulo`, `descripcion`, `precio`, `direccion`, `tipo` e `imagenUri` (simulada).

### Interfaz de Usuario (UI)

#### [NEW] [AddPropertyScreen.kt](file:///C:/Users/Andy2/AndroidStudioProjects/Aplicaci-n-de-Alquiler-con-Flutter-/app/src/main/java/com/example/alquigo/ui/property/AddPropertyScreen.kt)
- Pantalla con un formulario `Column` y `TextFields` para cada dato.
- Botón para seleccionar imagen usando `rememberLauncherForActivityResult(GetContent())` para simular la selección de galería.
- Botón "Registrar" que validará los campos y mostrará un mensaje de éxito (Toast).
- Gestión de estado local con `mutableStateOf` (cumpliendo el requisito de "sin ViewModels").

#### [MODIFY] [HomeScreen.kt](file:///C:/Users/Andy2/AndroidStudioProjects/Aplicaci-n-de-Alquiler-con-Flutter-/app/src/main/java/com/example/alquigo/ui/home/HomeScreen.kt)
- Adición de un `FloatingActionButton` o un botón en el contenido para navegar a la pantalla de registro de propiedad.

### Navegación

#### [MODIFY] [NavGraph.kt](file:///C:/Users/Andy2/AndroidStudioProjects/Aplicaci-n-de-Alquiler-con-Flutter-/app/src/main/java/com/example/alquigo/navigation/NavGraph.kt)
- Agregar la ruta `add_property` a la clase sellada `Screen`.
- Agregar el composable `AddPropertyScreen` al `NavHost`.

## Plan de Verificación

### Verificación Manual
- Navegar desde la pantalla de Inicio a "Registrar Propiedad".
- Llenar el formulario con datos de ejemplo.
- Probar el selector de imagen (galería del simulador/dispositivo).
- Pulsar "Registrar" y verificar que se muestra el mensaje de confirmación y permite regresar.
