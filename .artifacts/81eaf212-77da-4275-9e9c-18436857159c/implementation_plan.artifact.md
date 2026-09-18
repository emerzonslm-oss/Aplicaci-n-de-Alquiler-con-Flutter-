# Implementación de Autenticación con Firebase y Firestore

Este plan detalla la implementación del flujo de Registro, Login, Gestión de Sesión y Cierre de Sesión utilizando Firebase Authentication y Cloud Firestore.

## User Review Required

> [!IMPORTANT]
> Se detectó un archivo llamado `google-services (1).json` en la carpeta `app/`. Para que Firebase funcione correctamente, el archivo debe llamarse exactamente `google-services.json`. Procederé a renombrarlo.

> [!NOTE]
> La implementación utilizará Jetpack Compose y Navigation Compose, manteniendo la arquitectura actual del proyecto.

## Proposed Changes

### Dependencias y Configuración

#### [MODIFY] [libs.versions.toml](file:///C:/Users/emerz/AndroidStudioProjects/AlquiGo/gradle/libs.versions.toml)
Añadir versiones y librerías para:
- `navigation-compose`
- `lifecycle-viewmodel-compose`

#### [MODIFY] [build.gradle.kts](file:///C:/Users/emerz/AndroidStudioProjects/AlquiGo/app/build.gradle.kts)
Añadir dependencias de Firebase Auth, Firestore y las nuevas librerías de Compose.

---

### Capa de Datos

#### [NEW] [User.kt](file:///C:/Users/emerz/AndroidStudioProjects/AlquiGo/app/src/main/java/com/example/alquigo/data/model/User.kt)
Data class para representar al usuario en Firestore.

#### [NEW] [AuthRepository.kt](file:///C:/Users/emerz/AndroidStudioProjects/AlquiGo/app/src/main/java/com/example/alquigo/data/repository/AuthRepository.kt)
Clase encargada de interactuar con Firebase Auth y Firestore.

---

### Capa de Presentación (UI & ViewModel)

#### [NEW] [AuthViewModel.kt](file:///C:/Users/emerz/AndroidStudioProjects/AlquiGo/app/src/main/java/com/example/alquigo/ui/auth/AuthViewModel.kt)
Gestiona el estado de autenticación y la lógica de negocio de las pantallas.

#### [NEW] [LoginScreen.kt](file:///C:/Users/emerz/AndroidStudioProjects/AlquiGo/app/src/main/java/com/example/alquigo/ui/auth/LoginScreen.kt)
Pantalla de inicio de sesión.

#### [NEW] [RegisterScreen.kt](file:///C:/Users/emerz/AndroidStudioProjects/AlquiGo/app/src/main/java/com/example/alquigo/ui/auth/RegisterScreen.kt)
Pantalla de registro con validaciones.

#### [NEW] [HomeScreen.kt](file:///C:/Users/emerz/AndroidStudioProjects/AlquiGo/app/src/main/java/com/example/alquigo/ui/home/HomeScreen.kt)
Pantalla principal (placeholder) con opción de cerrar sesión.

#### [NEW] [NavGraph.kt](file:///C:/Users/emerz/AndroidStudioProjects/AlquiGo/app/src/main/java/com/example/alquigo/navigation/NavGraph.kt)
Configuración de la navegación entre pantallas.

#### [MODIFY] [MainActivity.kt](file:///C:/Users/emerz/AndroidStudioProjects/AlquiGo/app/src/main/java/com/example/alquigo/MainActivity.kt)
Integrar el NavGraph y manejar el estado inicial de la sesión.

## Verification Plan

### Automated Tests
- No se implementarán tests unitarios en esta fase, pero se verificará que el build sea exitoso tras cada cambio.

### Manual Verification
1. **Registro**: Probar crear un usuario con datos válidos y verificar errores en datos inválidos. Confirmar en Firebase Console (Auth y Firestore).
2. **Login**: Probar entrar con las credenciales creadas.
3. **Persistencia**: Cerrar la app y volver a abrirla para verificar que no pide login.
4. **Logout**: Cerrar sesión y verificar que redirige a Login y bloquea el acceso a Home.

## Configuración en Firebase Console
1. **Authentication**: Habilitar el método "Correo electrónico/contraseña".
2. **Firestore**: Crear la base de datos en modo producción y aplicar las reglas de seguridad proporcionadas en el requerimiento.
