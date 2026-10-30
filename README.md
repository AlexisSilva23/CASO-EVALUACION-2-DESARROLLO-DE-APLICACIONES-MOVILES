# SafeMuni - Aplicación de Seguridad Ciudadana Municipal

**SafeMuni** es una solución móvil nativa para el sistema operativo Android diseñada para la gestión y registro de incidencias ciudadanas a nivel municipal. El sistema permite a los usuarios registrar eventos en tiempo real adjuntando coordenadas geográficas y evidencia fotográfica, realizar seguimiento del estado de los casos e interactuar con una interfaz modular basada en componentes reactivos.

## Funcionalidades del Sistema

**Módulo de Autenticación:** Sistema de registro e inicio de sesión con verificación de credenciales y persistencia de datos local mediante Room Database.

**Módulo de Reportes:** Formulario para el levantamiento de incidencias que integra lectura de posición mediante GPS, visualización en mapa y captura/adjunto de imágenes.

**Módulo de Monitoreo ("Mis Casos"):** Interfaz de consulta de historial de reportes asociados al usuario, con capacidad de filtrado según el estado de la solicitud y vista detallada por folio.

**Módulo de Perfil:** Panel de usuario que consolida la información de la cuenta, estadísticas directas de reportes creados/resueltos y opciones de gestión de sesión.

**Navegación Declarativa:** Flujo de pantallas gestionado por estados de Compose evitando el acoplamiento de fragmentos tradicionales.

## Pila Tecnológica

**Lenguaje de Programación:** Kotlin

**Framework de UI:** Jetpack Compose con especificación Material Design 3

**Patrón de Arquitectura:** MVVM (Model - View - ViewModel)

**Persistencia de Datos:** Room Database

**Procesamiento de Anotaciones:** KSP (Kotlin Symbol Processing) configurado para la generación explícita de código nativo en Kotlin (room.generateKotlin = true)

**Gestión de Imágenes:** Coil Compose para carga asíncrona y renderizado

**Servicios de Ubicación:** Google Maps Compose SDK y Google Play Services Location

**Manejo de Concurrencia:** Kotlin Coroutines y StateFlow / MutableState para la reactividad de la interfaz

## Entorno de Desarrollo y Requisitos

**IDE:** Android Studio Ladybug (o superior compatible con Gradle 8.x)

**Versión de JDK:** Java 11 o Java 17

**Compile SDK:** 37

**Min SDK:** 24 (Android 7.0 Nougat)

**Target SDK:** 36

## Instrucciones de Configuración y Despliegue

**Clonar el repositorio remoto:** git clone https://github.com/AlexisSilva23/CASO-EVALUACION-2-DESARROLLO-DE-APLICACIONES-MOVILES.git
cd CASO-EVALUACION-2-DESARROLLO-DE-APLICACIONES-MOVILES


**Cambiar a la rama de integración:** git checkout develop


Abrir el proyecto en Android Studio y sincronizar las dependencias del proyecto (Sync Project with Gradle Files).

Ejecutar el proyecto en un emulador o dispositivo físico configurado en modo depuración (API 24+).

## Estructura del Proyecto

El código fuente de la aplicación está organizado bajo el paquete principal `com.duoc.SafeMuni` siguiendo la arquitectura recomendada por Android (MVVM):

```text
com.duoc.SafeMuni/
├── model/            # Entidades de Room (Usuario, Reporte) y configuración de AppDatabase.
├── repository/       # Clases repositorio y abstracciones DAO (UsuarioDao, ReporteDao).
├── viewmodel/        # Lógica de negocio y manejo de estado de UI (AuthViewModel, NuevoReporteViewModel).
├── ui/               # Componentes de interfaz de usuario con Jetpack Compose.
│   └── theme/        # Estilos globales, paleta de colores y tipografía de la aplicación.
└── MainActivity.kt   # Punto de entrada de la aplicación y orquestación de las vistas.
```



## Estrategia de Ramificación (Gitflow)

El repositorio utiliza una estructura de ramas distribuida para separar el código en producción, la integración continua y el trabajo individual de cada integrante del equipo:

**main:** Rama de producción que almacena las versiones estables y consolidadas del proyecto.

**develop:** Rama principal de integración donde se unifican las funcionalidades desarrolladas antes de pasar a producción.

**alexis-silva:** Rama de trabajo individual para el desarrollo y pruebas de módulos asignados a Alexis Silva.

**eliascarcamo:** Rama de trabajo individual para el desarrollo y pruebas de módulos asignados a Elías Cárcamo.

**javier-fernandez:** Rama de trabajo individual para el desarrollo y pruebas de módulos asignados a Javier Fernández.
