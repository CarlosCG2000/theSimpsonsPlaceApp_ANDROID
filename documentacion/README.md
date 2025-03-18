

### MI APLICACIÓN

## REQUISITOS
Requisitos `OBLIGATORIOS` de la aplicación:
1. Uso de `minSdk 24` ✅

2. Uso de `AndroidX` ✅

3. Aplicación desarrollada en `Kotlin` ✅

4. Uso de `ViewModel` ❌

5. Uso de `Flow` ❓

6. Uso de `Coroutines` ❓

7. Utilización `DrawerLayout/ButtomNavigationView` para crear una estructura de navegación de app  ❌
8. Crear un mínimo de `3 CustomView` propia ❓

9. En caso de utilizarse `diálogos`, se tendrá que utilizar `DialogFragments` ❓

10. Disponer de una `pantalla de Settings` que permita elegir y cambiar algún `parámetro funcional` de la app❓

11. Tener al menos una acción en la `Toolbar` además de la de `Settings` ❓

12. Uso de `variantes con gradle`. Se requerirá al menos tener dos variantes diferentes, ya sea empleando `Flavors` (free, paid, staging, production, etc...) o mediante `Build Types` (debug, beta, release...). En ambos casos se requerirá que las dos variantes contengan parte del código completamente aislado uno de otro. Pueden ser dos features completamente diferentes o dos entornos de backend que obtengan los datos de manera distinta, en el caso de los `Flavors`, o en el caso de `Build Types` diferentes herramientas que puedan ayudar al desarrollador en diferentes escenarios, como podría ser la posibilidad de cambiar la `URL del servidor` a la que apunta la aplicación, añadir una `capa de logging` o funcionalidad adicional que ayude a `detectar errores` en debug. En cualquier caso, esto son solo ejemplos para los que se pueden usar los flavors; pero cualquier uso creativo de las variantes será bienvenido.

13. Los `textos, dimensiones, colores y estilos` deben residir en su `fichero de recursos` correspondiente. Usar al menos un estilo en los `layouts XML`. ❓

14. Uso de permisos con `Android 6.0` ✅

15. Creación de `una paleta de colores propia` de la aplicación, de modo que visualmente se pueda identificar la app a un estilo visual reconocible. ✅

16. Uso de un `modo de persistencia local` además de las `Preferences DataStore` (también obligatorio). Entre las opciones de persistencia local, se permitirán tanto almacenamiento en base de datos exclusivamente (`Room`) como frameworks que aporten esa solución (`como Firebase por ejemplo`) ❓

17. Uso de `ConstraintLayout`, `ViewPager` y `SwipeRefreshLayout`. ❓

18. Realización de `peticiones web`: ya sea servicios `REST` con `Volley` o `Retrofit` o con `APIs` que se encarguen de `encapsular peticiones` como podría ser con `Amazon Web Services` por ejemplo. ❓

19. Subir la aplicación a `Google Play` (aunque sea en fase alpha o beta e invitar a los profesores) ❓

## INICIO
Creo la aplicación `TheSimpsonPlace` con una vista `Empty Activity` de `Jetpack Compose`.

1. Creación de una `paleta de colores propia de la aplicación`, de modo que visualmente se pueda identificar la app a un estilo visual reconocible. Ficheros `Color.kt` y `Theme.kt`. ✅

2. Los `textos, dimensiones, colores y estilos` deben residir en su `fichero de recursos` correspondiente. Usar al menos un estilo en los `layouts XML`. ❌

3. 


### 1. Fichero `Color.kt`

Defino la paleta de colores para el estilo visual de mi app. Tanto en modo claro como modo oscuro.

```kotlin
// 🎨 Modo Oscuro
val BackgroundColor = Color(0xFF09184D)
val TextColor = Color(0xFFFFC107)
val BackgroundComponentColor = Color(0xFF4E5D9C)
val TextComponentColor = Color(0xFFFFFFFF)

// 🎨 Modo Claro
val BackgroundColorLight = Color(0xFF3B4D8B) // Azul más claro que 0xFF09184D
val TextColorLight = Color(0xFFFFD54F) // Amarillo más suave
val BackgroundComponentColorLight = Color(0xFF6F7ECF) // Azul más claro que 0xFF4E5D9C
val TextComponentColorLight = Color(0xFF000000) // Negro (para contraste en claro)
```

### 2. Fichero `Theme.kt`

Añado los colores en los temas.
```kotlin
private val DarkColorScheme = darkColorScheme(
    primary = BackgroundColor, // Color principal
    secondary = BackgroundComponentColor, // Color para elementos secundarios
    background = BackgroundColor, // Color de fondo de la app
    surface = BackgroundComponentColor, // Color de fondo de los componentes
    onPrimary = TextColor, // Color del texto sobre el color primario
    onSecondary = TextComponentColor, // Color del texto sobre el color secundario
    onBackground = TextComponentColor, // Color del texto sobre el fondo
    onSurface = TextComponentColor // Color del texto sobre los componentes
)

private val LightColorScheme = lightColorScheme(
    primary = BackgroundColorLight, // Color principal más claro
    secondary = BackgroundComponentColorLight, // Color para elementos secundarios más claro
    background = BackgroundColorLight, // Color de fondo de la app más claro
    surface = BackgroundComponentColorLight, // Color de fondo de los componentes más claro
    onPrimary = TextColorLight, // Color del texto sobre el color primario más claro
    onSecondary = TextComponentColorLight, // Color del texto sobre el color secundario más claro
    onBackground = TextComponentColorLight, // Color del texto sobre el fondo más claro
    onSurface = TextComponentColorLight // Color del texto sobre los componentes más claro
)

```

Desactivo los colores dinámicos, para usar exclusivamente los colores de `DarkColorScheme` y `LightColorScheme`

```kotlin
@Composable
fun TheSimpsonPlaceTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean =  false, // ❌ Desactiva los colores dinámicos, usará exclusivamente los colores de DarkColorScheme y LightColorScheme.
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
```

Ahora mi app tiene un `modo claro` con `tonos más suaves`, diferenciándose del `modo oscuro` sin perder `identidad visual`.


