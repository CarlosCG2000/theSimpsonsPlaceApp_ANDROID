

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

3. `Esqueleto` del proyecto.
- Carpeta `data`: service, datasource, mapper....
- Carpeta `domain`: repository, usecases...
- Carpeta `presentation`: viewModels, views...

4. Intalación de `dependencias` (de momento).
* 1. Serialización: `kotlinx.serialization` para manejar `JSON`. (`@Serializable`). Utilizado por ejemplo a la hora de transformar la entidad `CharacterDto` recibida del json por otro nombres.
* 2. ConstraintLayout: `androidx.constraintlayout` y elegimos `constraintlayout-compose`. Para realizar en Compose (vistas).
* 3. ViewModel: `androidx.lifecycle` llamada `lifecycle-viewmodel-compose`. Descartamos versiones `alpha` coger la última que no sea de este tipo. Para los ViewModels.
* 4. Navigation:  navigation compose. `androidx.navigation`.
* 5. Coil: `io.coil-kt` y aplicamos la de `coil-compose`. Carga de imágenes de web.
* 6. Extensión de iconos: buscar `material-icons` obtener `material-icons-extended`.


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



### 3. Fichero `Logger.kt`


ME QUEDAN EL LOGGER, IMPLEMENTAR PARA LOS OTROS SECCIONES, PASAR A REALIZAR LAS PANTALLAS BONITAS.


### X. DUDAS

#### 1. DUDA
@@@@ DUDA @@@@
Tengo mis dudas al intentar formar mi aplicación (`3.`):
En cuanto al esqueleto de mi aplicación: tengo 3 carpetas principales `data`, `domain` y `presentation`.

* Carpeta `Data`: de momento contiene:

- La subcarpeta `entities` a `CharacterDao.kt` (datos de com recibo una entidad del json de characters), `ImagenDto.kt` (datos de como recibo una entidad del json de imagenes), `Gender.kt` (enumerado con función para pasar de string a ese enumerado).

- La subcarpeta `mapper` donde tengo `CharacterMapper.kt` con una extensión para pasar de `CharacterDto` a `Character` que es como trabajare en el domain.

- La subcarpeta `sources` la cual no se si esto es adecuado pero he hecho es dividir en dos carpetas:

+ Una subcarpeta `local` donde mi idea es obtener `los personajes del json` donde tengo un `CharacterDao.kt` con la `interface` y tiene `una función` de obtener todos personajes (**voy a necesitar `otra función` para filtrar por nombres de personajes pero no se si es adecuado incluirla aqui o mas adelante**).
`CharacterDaoJsonProd.kt` y `CharacterDaoJsonTest.kt` son dos de las implemanciones de la interface para obtener la funcion de los personajes.

+ Una subcarpeta `db` con lo mismo pero con la interface `CharacterDBDao.kt` y las funciones que tiene para los `characters` pero de en la `BD` y su correspondiente implementación. **¿Esta bien separarlos en dos `daos` diferentes? Uno con la lógica deo obbtención de datos y el otro con la de la BD o hay una mejor estructura?**

* La carpeta `domain` que de momento contiene:

- La subcarpeta `entities` con `Character.kt` que es el la entidad de personaje final con la que quiero trabajar tanto para la base de datos como la extraccion principal de los datos.

- La subcarpeta `repository` donde solo tengo una `interface` con el `CharacterRepository` con todas las funciones tanto del `dao` del `json` como para la del `dao` de la `base de datos` y he añadido `la nueva función para filtrar por nombre` los personajes que se obtienen del json (no se la puse en el dao del json) **¿Esta bien ponerla aqui o mejor era antes en el dao o después mas adelante ya en el view model?** Luego tengo la implentación de ese dao `CharacterRepositoryImpl.kt` donde paso por paremetro las interfaces de los dos daos anteriores e implemento el `CharacterRepository`, aqui me surge otra vez **la duda al implementarse la función de `filterCharacters` de filtrar los personajes tendria que realizar toda la función de obtener todos los personajes y luego filtrarlo algo que no se si es buen en rendimiento por ello no se si debería estar.**

- La subcarpeta `usescases`: tengo todos los casos de uso de la interface `CharaterRepository.kt`, cada uno en una interfaz diferente y una carpeta `impl` con la implementación de cada caso de uso pasandoloe por parámetro el `CharaterRepository.kt`.

* La carpeta `presentation`: de momento solo esta creada dicha carpeta.
- La subcarpeta `ui` contiene a su vez
+ La subcarpeta `components` con vistas reutilizables en varias vistas principales como `BottomBar`, `TopBar` o `Icono dinamico` dependiendo de una variable por parámetro.
+ La subcarpeta `screens` con otras carpetas donde divido la carpeta por secciones chracter, episode, menu, profile y quote. Tambien tiene el fichero de la actividad principal y unica de la aplicación y un fichero Navegation el cual es una vista con toda la navegacion de la aplicación (es la vista que se pasa  a la actividad).
+ La subcarpeta `themes` con los ficheros de configuración de color, tema, tipografia...
+ La subcarpeta `viewmodel` donde de momento no hay anda pero se encontraran los ficheros de estaod d ela interfaz junto los view models de cada seccion de vistas.

* La carpete `utils` seperada de las tres capas de la aplicación de momento solo tengo una inteerfaz `Logger.kt` con los posibles logger de la aplicación. **¿Que te parece la distribución de los casos de uso en la capa del `domain`, y el esquema de capa de `presentacion`? Que me recomiendas cambiar o esta bien como esta? Es mi primera app y quiero tener una arquitectura lo mas clara, escalable y manenible posible.**

@@@@ SOLUCIÓN GHAT GPT @@@@
Tu estructura `Data - Domain - Presentation` está bien planteada y sigue las mejores prácticas de `Clean Architecture` en `Kotlin` con `Jetpack Compose`.

📌 1. Estructura en la capa `data`

Actualmente tienes:
• entities/ → CharacterDto, ImageDto, Gender ✅ Bien.
• mappers/ → CharacterMappers ✅ Bien.
• sources/local/ → CharacterDao, CharacterDaoImpl 🛑
• sources/db/ → CharacterDBDao, CharacterDBDaoImpl 🛑

🔹 ¿Está bien separar `DAO` del `JSON` y `DAO` de la `BD`?
✅ Sí, está bien porque:
1.	El JSON no es una BD real, solo es una fuente de datos estática.
2.	`Separar responsabilidades` evita que `ambas fuentes` se mezclen en una misma clase.
3.	Si en el futuro usas Retrofit en vez de JSON local, el Dao del JSON ya no sería necesario.

🚀 Mejora: **Puedes renombrar `CharacterDao.kt` → `CharacterJsonSource.kt` y `CharacterDBDao.kt` → `CharacterDatabaseSource.kt` para que quede más claro.**

📌 2. Estructura en la capa `domain`

Actualmente tienes:
• entities/ → Character ✅ Bien.
• repository/
    • CharacterRepository (interfaz) 🛑
    • impl/ → CharacterRepositoryImpl (implementación) 🛑
• usecases/ → Cada caso de uso en una interfaz distinta
	• impl/ → Implementación de cada caso de uso pasando CharacterRepository

🔹 ¿Dónde debe ir `filterCharacters(nombre)`?
🔸 Si es un `simple filtro` sobre la lista obtenida, puede estar en `RepositoryImpl.kt`.
🔸 Si en `el futuro la API` permite filtrar, debería ir en `CharacterJsonSource.kt` para no cargar la app de datos innecesarios.

🚀 Mejora recomendada: **Si el `JSON nunca cambiará` → déjalo en `RepositoryImpl.kt`. Pero si el `JSON es una API` o puede llegar a serlo en el futuro puede `permitir filtrar` → Ponlo en `CharacterJsonSource.kt`.**

❗ Rendimiento
Si la lista de personajes es grande, filtrar después de cargar no es óptimo. En ese caso, es mejor:
1.	Cargar solo los que coincidan → Implementa `filterCharacters(name: String)` en `CharacterJsonSource.kt`.
2.	Guardar personajes en `BD local` y aplicar `filtros allí`.

📌 Casos de uso
✅ Puntos positivos
✔️ Separación clara de la lógica de negocio.
✔️ Cumple el `principio de responsabilidad única` (cada caso de uso hace una sola cosa).
✔️ Permite `testear` cada caso de uso de `forma independiente`.

📌 3. Estructura en `presentation`

Actualmente tienes:
• 📂 components/ → Vistas reutilizables ✅
• 📂 screens/ → División por secciones ✅
• 📂 themes/ → Colores, tipografía, etc. ✅
• 📂 viewmodel/ → (Aún vacío, pero pensado para ViewModels) ✅

✅ Puntos positivos
✔️ Buena separación de responsabilidades.
✔️ División clara de screens por secciones.
✔️ Uso de components para evitar código duplicado.
✔️ Carpeta themes bien ubicada.

⚠️ Posibles mejoras
📌 1. Mover Navegation a una carpeta navigation/ dentro de presentation/ui
• La navegación no es exactamente una screen, sino una estructura que maneja las pantallas.
• Crear 📂 ui/navigation/ → Mover Navegation.kt allí.

📌 2. La actividad principal en una carpeta root/ dentro de presentation/ui
• 📂 ui/root/ → MainActivity.kt
• 📌 Esto deja screens/ solo con pantallas reales y mejora la organización.

📌 3. ViewModels bien organizados en viewmodel/
• 📂 viewmodel/character/CharacterViewModel.kt
• 📂 viewmodel/episode/EpisodeViewModel.kt
• 📂 viewmodel/profile/ProfileViewModel.kt
• 📌 Esto ayuda a encontrar rápidamente el ViewModel de cada sección.

📌 4. Estructura en `utils`

Actualmente tienes:
	•	📂 utils/ → Logger.kt

✅ Puntos positivos
✔️ Centraliza funciones reutilizables.

⚠️ Posibles mejoras
📌 Si Logger.kt solo se usa en data, tal vez debería ir en data/utils/.
📌 Si utils/ tendrá más funciones reutilizables (como extensiones de Strings, Dates, etc.), entonces está bien en root/utils/.

🎯 Conclusión
✅ Tu arquitectura está bien, solo hay que ajustar algunas responsabilidades.
✅ Sí, es correcto separar el DAO del JSON y el de la BD.
✅ Filtrar personajes en el RepositoryImpl está bien, pero si el JSON puede filtrar, mejor en CharacterJsonSource.
✅ Si tienes una BD, filtrar allí es mejor por rendimiento.

🚀 Recomendaciones finales:
🔹 Renombrar CharacterDao → CharacterJsonSource y CharacterDBDao → CharacterDatabaseSource.
🔹 Si el JSON es estático, filtrar en RepositoryImpl.
🔹 Si es API, filtrar en CharacterJsonSource.
🔹 Si usas BD local, filtrar en la base de datos.
✔️ En domain: Usar clases de un solo método en vez de interfaces para los casos de uso.
✔️ En presentation: Mover Navegation.kt a navigation/ y MainActivity.kt a root/.
✔️ En viewmodel/: Organizar los ViewModels en subcarpetas por secciones.
✔️ En utils/: Mantenerlo solo si tendrá más funcionalidades reutilizables.

### 2. DUDA
@@@@ DUDA @@@@
Debe solo tener una implementación de `CharacterDao` para ficheros json, mediante una variable que se pase el json por parámetro para saber si es el `json de test` o de `producción`? Ese parametro deve de ser `val` o no?

@@@@ SOLUCIÓN GHAT GPT @@@@
📌 1. Sobre el uso de `val json: String`

En este caso, sí es correcto usar `val json: String` en el constructor, porque:
✅ Es un `dato inmutable` → Una vez que pasas el json, no cambiará.
✅ Hace `la clase más reutilizable` → Puedes usar un JSON diferente para `testing` y `producción`.

📌 Cuándo usar `val` en `parámetros del constructor`
1. ✅ SI USAR `val` → Si el parámetro se usará `dentro de la clase` como una `propiedad`.

```kotlin
    class CharacterDaoJson(val json: String) // Se usa dentro de la clase
```

2. ❌ NO USAR val → Si solo se pasa como argumento y no necesitas guardarlo en la clase.
```kotlin
    class CharacterDaoJson(json: String) { // json NO se guarda en la clase
        fun getAllCharacters(): List<CharacterDto> {
            val data = json // Aquí lo usas solo dentro de la función
            return listOf() // Lógica de parsing
        }
    }
```

En tu caso, como `json es una propiedad que usarás en múltiples funciones`, debe ser `val`.

### 3. DUDA
...