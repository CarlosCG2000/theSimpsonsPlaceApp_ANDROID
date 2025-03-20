

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

## INICIO PROYECTO
Creo la aplicación `TheSimpsonPlace` con una vista `Empty Activity` de `Jetpack Compose`.

1. Creación de una `paleta de colores propia de la aplicación`, de modo que visualmente se pueda identificar la app a un estilo visual reconocible. Ficheros `Color.kt` y `Theme.kt`. ✅

2. Los `textos, dimensiones, colores y estilos` deben residir en su `fichero de recursos` correspondiente. Usar al menos un estilo en los `layouts XML`. ❌

3. `Esqueleto` del proyecto.
- Carpeta `data`: service, datasource, mapper....
- Carpeta `domain`: repository, usecases...
- Carpeta `presentation`: viewModels, views...

Añadida las subcarpetas para la sección de personajes. Explicación `1. DUDA.`

4. Instalación de `dependencias` (de momento).
* 1. Serialización: `kotlinx.serialization` para manejar `JSON`. (`@Serializable`). Utilizado por ejemplo a la hora de transformar la entidad `CharacterDto` recibida del json por otro nombres.
* 2. ConstraintLayout: `androidx.constraintlayout` y elegimos `constraintlayout-compose`. Para realizar en Compose (vistas).
* 3. ViewModel: `androidx.lifecycle` llamada `lifecycle-viewmodel-compose`. Descartamos versiones `alpha` coger la última que no sea de este tipo. Para los ViewModels.
* 4. Navigation:  navigation compose. `androidx.navigation`.
* 5. Coil: `io.coil-kt` y aplicamos la de `coil-compose`. Carga de imágenes de web.
* 6. Extensión de iconos: buscar `material-icons` obtener `material-icons-extended`.

5. Añadir en el esqueleto (`punto 3.`) a aparte la que esta ya de personajes, las `secciones` de `episodio`, `citas` y `juego`.
En `1. DUDA`, explicado el esquema que he seguido para app (muy similar en personajes con las demás secciones que ahora implemento).

Ejemplo con la `sección episodios`:



6. ...


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
El archivo Logger.kt define una `interfaz de logging` que proporciona métodos estandarizados para registrar mensajes en diferentes niveles de severidad (Verbose, Debug, Info, Warning, Error, Assert). Su propósito es `centralizar y simplificar el proceso de depuración`, permitiendo que cualquier clase que la implemente pueda registrar logs sin repetir código. Además, asigna automáticamente `el nombre de la clase como etiqueta (tag)`, facilitando la identificación del origen de cada mensaje en Logcat.

### 4. Fichero ...
ME QUEDAN IMPLEMENTAR PARA LOS OTROS SECCIONES, REPASAR LAS CLASES DE ROBERTO, PASAR A REALIZAR LAS PANTALLAS BONITAS.

### X. MIS DUDAS

#### 1. DUDA ✅
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

### 2. DUDA ✅
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

### 3. DUDA ✅
@@@@ DUDA @@@@
Ahora voy a tener que tener la `entidad episodio` pero hay `dos variables nuevas` (ambas para la `base de datos` son booleanos que dicen si es visto y si es favorito el episodio) entonces no se que es mejor **crear `otra entidad nueva EpisodeDb` (y donde ponerle en este caso) o `modificar la entidad Episode` y que contenga esos `dos valores nuevos` a false por defecto** y asi utilizar la misma entidad tanto en con los datos normales como cuando se trabaja con la base de datos.

@@@@ RESPUESTA GHAT GPT @@@@
¿Debe haber una `nueva entidad EpisodeDb` o `modificar Episode`?
Tienes dos opciones:
1.	Usar la misma entidad Episode y agregar las variables esVisto y esFavorito con un valor por defecto (false).
2.	Crear EpisodeDb como una entidad diferente exclusiva para la base de datos.

¿Cuál es la mejor opción?
✅ Si la app siempre necesita manejar esVisto y esFavorito, lo mejor es mantener una única entidad Episode y establecer esos valores por defecto en false.
✅ Si esVisto y esFavorito solo son relevantes en la base de datos, es mejor crear EpisodeDb y usar un mapper para convertir Episode <-> EpisodeDb.

@@@@ DUDA @@@@
Respuesta a: si `esVisto` y `esFavorito` son importantes en toda la app, agrégalos a `Episode`.
Respecto a eso, mi idea es que en una `vista` tenga el `total de episodios` (obtenidos del json) con sus datos y `un icono` en un estado diferente dependiendo de si estan en `favoritos` y `visibles` o no (obtenido de la base de datos) y que se puede desde esa misma pantalla añadir a favoritos o no y dicha pantalla se actualice, lo idea es que **no tuviera que renderizarse todo el listado de nuevo solo el elemento de episodio que cambie de estado al ser de favortio o visto (dependiendo del icono pulsado)**.

@@@@ RESPUESTA GHAT GPT @@@@
El objetivo es mostrar todos los episodios obtenidos del JSON, pero que los episodios marcados como esFavorito y esVisto se actualicen desde la Base de Datos (sin recargar toda la lista). Además, si el usuario cambia uno de estos estados desde la interfaz, solo el episodio afectado debe actualizarse.

En el repositorio, al obtener los episodios desde el JSON, hay que cruzar los datos con la BD para verificar cuáles están en favoritos y vistos: ⚠️⛔️
```kotlin
override fun getAllEpisodes(): List<Episode> {
    val episodesFromJson = episodeDaoJson.getAllEpisodes().map { it.toEpisode() }
    val favoriteEpisodes = episodeDaoDb.getFavoriteEpisodes() // Solo IDs marcados como favoritos
    val watchedEpisodes = episodeDaoDb.getWatchedEpisodes()   // Solo IDs marcados como vistos

    return episodesFromJson.map { episode ->
        episode.copy(
            esFavorito = favoriteEpisodes.any { it.id == episode.id },
            esVisto = watchedEpisodes.any { it.id == episode.id }
        )
    }
}
```

En el View Model:
```kotlin
class EpisodesViewModel(private val repository: EpisodesRepository) : ViewModel() {
    private val _episodes = MutableStateFlow<List<Episode>>(emptyList())
    val episodes: StateFlow<List<Episode>> = _episodes

    init {
        loadEpisodes()
    }

    private fun loadEpisodes() {
        viewModelScope.launch {
            _episodes.value = repository.getAllEpisodes()
        }
    }

    fun toggleFavorite(id: String) {
        viewModelScope.launch {
            repository.updateFavoriteStatus(id)
            _episodes.value = _episodes.value.map { ep ->
                if (ep.id == id) ep.copy(esFavorito = !ep.esFavorito) else ep
            }
        }
    }

    fun toggleWatched(id: String) {
        viewModelScope.launch {
            repository.updateWatchedStatus(id)
            _episodes.value = _episodes.value.map { ep ->
                if (ep.id == id) ep.copy(esVisto = !ep.esVisto) else ep
            }
        }
    }
}
```

En la UI con LazyColumn y actualización eficiente
```kotlin
@Composable
fun EpisodesScreen(viewModel: EpisodesViewModel) {
    val episodes by viewModel.episodes.collectAsState()

    LazyColumn {
        items(episodes) { episode ->
            EpisodeItem(episode, viewModel::toggleFavorite, viewModel::toggleWatched)
        }
    }
}

@Composable
fun EpisodeItem(episode: Episode, onFavClick: (String) -> Unit, onWatchedClick: (String) -> Unit) {
    Row(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
        Text(text = episode.titulo, modifier = Modifier.weight(1f))

        IconButton(onClick = { onFavClick(episode.id) }) {
            Icon(
                imageVector = if (episode.esFavorito) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                contentDescription = "Favorito"
            )
        }

        IconButton(onClick = { onWatchedClick(episode.id) }) {
            Icon(
                imageVector = if (episode.esVisto) Icons.Filled.Check else Icons.Outlined.CheckBoxOutlineBlank,
                contentDescription = "Visto"
            )
        }
    }
}
```

### 4. DUDA
@@@@ DUDA @@@@
Respecto a la `implementación (en kotlin) del repositorio de episodios` dada en la `Duda 3`.
Seria algo extraño estoy teniendo que hacer cada vez que se obtiene o filtra unos episodios del json comprobar a su vez si están en la BD para darles los valores `esVisto` y `esFav` a true o false, esto **¿no creo que `merma mucho el rendimiento` o no es asi? ¿Hay alguna lógica mejor para hacer esto?**

@@@@ RESPUESTA GHAT GPT @@@@
Sí, tu implementación funciona correctamente, pero hay `un problema de rendimiento` al estar consultando toda la base de datos cada vez que obtienes episodios del JSON.

✅ Problema actual
• Cada vez que obtienes episodios del JSON, descargas todos los episodios de la BD (fetchAllEpisodes()).
• Luego, filtras en memoria cuáles son esFavorito y esVisto con `.any { it.id == episode.id }`, lo que puede ser ineficiente si hay muchos episodios.

🔥 Mejor solución: Consultas optimizadas a la BD

💡 Optimización clave:
👉 En lugar de traer toda la BD, consulta solo los IDs necesarios.

En una BD añadir la funcionalidad de obtener episodios por array de ids:
```kotlin
@Dao
interface EpisodeDatabaseDao {
    @Query("SELECT * FROM episodes WHERE id IN (:ids)")
    fun fetchEpisodesByIds(ids: List<String>): List<Episode>
}
```

🛠️ 1. Optimizar el acceso a la BD en getAllEpisodes()
```kotlin
override fun getAllEpisodes(): List<Episode> {
    val allEpisodesDto: List<EpisodeDto> = dao.getAllEpisodes()
    val allEpisodes: List<Episode> = allEpisodesDto.map { it.toEpisode() }
    val ids = allEpisodes.map { it.id } // Solo obtener los IDs de los episodios del JSON

    val episodesFromDb: Map<String, Episode> = daoDb.fetchEpisodesByIds(ids).associateBy { it.id }

    return allEpisodes.map { episode ->
        val episodeDb = episodesFromDb[episode.id] // Buscar solo el episodio en la BD por el id
        episode.copy(
            esFavorito = episodeDb?.esFavorito ?: false,
            esVisto = episodeDb?.esVisto ?: false
        )
    }
}
```

🛠️ 2. Optimizar getEpisodeById(id: String)
No hace falta traer toda la BD, consulta solo el episodio:
```kotlin
override fun getEpisodeById(id: String): Episode? {
    val episodeDto: EpisodeDto? = dao.getEpisodeById(id)
    val episode = episodeDto?.toEpisode()
    val episodeDb = daoDb.fetchEpisodeById(id)

    return episode?.copy(
        esFavorito = episodeDb?.esFavorito ?: false,
        esVisto = episodeDb?.esVisto ?: false
    )
}
```

🛠️ 3. Optimizar getEpisodesByTitle(title: String)
Misma lógica de getAllEpisodes(), pero solo para los episodios con el título buscado.

📌 Resumen final 🚀
🔹 ANTES: Descargabas toda la BD y filtrabas en memoria.
🔹 AHORA: Solo consultas los episodios necesarios directamente en la BD.
🔹 BENEFICIO: Código más eficiente, evita procesos innecesarios y mejora el rendimiento en listas grandes.

### 5. DUDA ✅
@@@@ DUDA @@@@
Como verias en vez de crear **dos dao `EpisodeDao.kt` y `EpisodeDatabaseDao.kt`, juntar los dos en una única interfaz con todas las funciones** y luego que la implementación ya contenga todas las acciones.

@@@@ RESPUESTA GHAT GPT @@@@
¿Es buena idea unir `EpisodeDao` y `EpisodeDatabaseDao` en una sola interfaz?

❌ `No` es recomendable unirlos en una sola interfaz, porque estás mezclando `dos fuentes de datos diferentes`:
• `getAllEpisodes()` → Obtiene episodios del `JSON o API`.
• `getAllEpisodesDb()` → Obtiene episodios de la `BD (Room, Firebase, etc.)`.

Esto rompe el `Principio de Responsabilidad Única (SRP)` y hace que el `EpisodeDaoJsonRoom` tenga dos responsabilidades distintas en la misma clase.

🔥 Mejor alternativa: Separar en EpisodeRemoteDataSource y EpisodeLocalDataSource

💡 1️⃣ Crear dos interfaces separadas
Mantén una para la `API/JSON` y otra para la `BD`, así no mezclas responsabilidades.

💡 2️⃣ Implementar las interfaces separadas
📌 Implementación de la API/JSON
📌 Implementación de la BD (Room)

📌 Conclusión
🚀 Separar `EpisodeDao` en `EpisodeRemoteDataSource` y `EpisodeLocalDataSource` mejora:
✅ Claridad: Cada fuente de datos tiene su propia lógica.
✅ Escalabilidad: Si en el futuro cambias `Room` por `Firebase`, solo modificas `EpisodeLocalDataSourceImpl`.
✅ Mantenimiento: Más fácil de modificar sin afectar otras partes del código.

### 6. DUDA
@@@@ DUDA @@@@
Y si en vez de combinar en el repositorio `los episodios de db` con los de `json` se realiza por `separado` y cada vez que se vea un elemento de episodio lo que haga sea llamar a la función de ver si esta en la BD si esta ver su `estado de fav y visto` y según ese mostrar de una forma u otra. Eso no seria más correcto o mejor en rendimiento?

@@@@ RESPUESTA GHAT GPT @@@@
**¿Es mejor unir los datos de la `BD y JSON en el repositorio` o `consultarlos al mostrarlos`?**
Tu pregunta tiene dos enfoques:
1️⃣ Opción actual (mezclar JSON y BD en el `repositorio`)
2️⃣ Opción alternativa (consultar la BD al mostrar cada episodio en la `UI`)

1️⃣ Opción actual: Unir los datos en el Repository
✅ Ventajas
✔ Consulta única a la BD → No hay llamadas extra cada vez que se muestra un episodio.
✔ Menos impacto en la UI → La vista recibe los datos ya procesados.
✔ Mejor rendimiento en listas grandes → Evita múltiples accesos a la BD mientras el usuario navega.

❌ Desventajas
✖ Carga inicial más lenta → Se obtiene y fusiona todo de golpe.
✖ No es reactivo → Si se actualiza la BD mientras ves la lista, la UI no se actualiza.

2️⃣ Opción alternativa: Consultar la BD al mostrar el episodio.
✅ Ventajas
✔ Carga inicial más rápida → No se hace la consulta a la BD hasta que la UI la necesita.
✔ Más reactivo → Si la BD cambia, la UI se actualiza dinámicamente.
✔ Menos memoria usada → No carga toda la información de la BD si el usuario no la necesita.

❌ Desventajas
✖ Más consultas a la BD → Puede afectar el rendimiento en listas grandes.
✖ Más trabajo en la UI → Cada elemento hace una consulta a la BD, aumentando el uso de recursos.

🚀 Conclusión: ¿Qué opción es mejor?

🔹 Si tu lista de episodios es grande (más de 50 elementos) → Mejor unir los datos en el Repository.
🔹 Si necesitas que la UI reaccione en tiempo real a cambios en la BD → Mejor consultar en la UI.
🔹 Si usas `Jetpack Compose con Flow o LiveData` en Room → Puedes hacer que la UI se actualice automáticamente sin necesidad de recargar la lista completa.

🔸 Recomendación híbrida: Cargar `los datos en el Repository` pero usar `Flow para actualizar la UI` si la BD cambia. 🚀
La idea de esta solución es combinar lo mejor de ambas estrategias:
• Cargar los datos iniciales desde el Repository (para evitar consultas innecesarias en la UI).
• Usar `Flow de Room` para que la `UI` se actualice automáticamente cuando la BD cambie.

💡 Conceptos clave

🔹 1. Flow y reactividad en Room
En lugar de hacer consultas manuales, `Room` permite exponer datos como `Flow`, lo que significa que cualquier cambio en la BD notificará automáticamente a la UI.
```kotlin
@Dao
interface EpisodeDao {
    @Query("SELECT * FROM episodes")
    fun getAllEpisodesFlow(): Flow<List<EpisodeEntity>> // 🚀 Se actualizará automáticamente si cambia la BD
}
```

🔹 2. Cargar datos combinados en el Repository
El Repository se encargará de cargar los datos iniciales del JSON y luego escuchar cambios en la BD con Flow.
```kotlin
class EpisodeRepository(
    private val episodeDao: EpisodeDao, // BD
    private val apiService: EpisodeApi //JSON / API
) {

    fun getEpisodes(): Flow<List<Episode>> {
        return flow {
            // 🚀 1️⃣ Cargar datos del JSON/API
            val apiEpisodes = apiService.getEpisodes().map { it.toEpisode() }
            emit(apiEpisodes) // Emitir episodios iniciales

            // 🚀 2️⃣ Escuchar cambios en la BD en segundo plano
            episodeDao.getAllEpisodesFlow().collect { dbEpisodes ->
                val updatedEpisodes = apiEpisodes.map { apiEpisode ->
                    val episodeDb = dbEpisodes.find { it.id == apiEpisode.id }
                    apiEpisode.copy(
                        esVisto = episodeDb?.esVisto ?: false,
                        esFavorito = episodeDb?.esFavorito ?: false
                    )
                }
                emit(updatedEpisodes) // Emitir episodios con estados actualizados
            }
        }
    }
}
```

🔹 3. Mostrar los datos en la UI usando collectAsState en Compose
En la UI, simplemente escuchamos el Flow y la lista se actualiza automáticamente si cambia la BD.
```kotlin
@Composable
fun EpisodeListScreen(viewModel: EpisodeViewModel) {
    val episodes by viewModel.episodes.collectAsState(initial = emptyList()) // 🚀 Se actualiza solo

    LazyColumn {
        items(episodes) { episode ->
            Text(episode.titulo)
        }
    }
}
```

🚀 Ventajas de esta solución híbrida
✅ Carga inicial rápida → Los episodios se muestran antes de consultar la BD.
✅ Reactivo y eficiente → La UI solo se actualiza si la BD cambia, sin consultas innecesarias.
✅ Buen rendimiento → Flow gestiona los cambios sin necesidad de recargar la lista completa.

📌 Resumen
💡 El Repository obtiene los datos del JSON/API y emite la lista de episodios.
💡 Luego, usa Flow de Room para escuchar cambios en la BD y actualizar la UI sin necesidad de consultas extra.

### 7. DUDA
@@@@ DUDA @@@@
**Conceptos básicos, 3 dudas**

@@@@ RESPUESTA GHAT GPT @@@@
1️⃣ `¿Qué hace copy en Kotlin?`
El método `copy()` se usa en las `data class` para crear una nueva instancia con los mismos valores de la original, pero permitiendo modificar solo algunos campos.

📌 ¿Por qué se usa en tu código?
Porque cuando combinas datos del JSON con la BD, la información de esFavorito y esVisto proviene de la BD, así que en lugar de modificar el objeto original, se genera una nueva copia con los valores actualizados.

```kotlin
data class Character(
    val id: Int,
    val name: String,
    val esFavorito: Boolean
)

fun main() {
    val character1 = Character(1, "Homer", false)
    val character2 = character1.copy(esFavorito = true) // Solo cambia "esFavorito"

    println(character1) // Character(id=1, name=Homer, esFavorito=false)
    println(character2) // Character(id=1, name=Homer, esFavorito=true)
}
```

2️⃣ `Optimizar la lógica de combinación de datos`
• Actualmente en getAllCharacters() comparas la lista obtenida del JSON con la de la BD pero accediendo a la BD para cada elemento (allCharactersDB[character.id]).
• Esto puede ser costoso en rendimiento, ya que estás haciendo N búsquedas en la BD (donde N es el número de personajes) en vez de una sola operación.
• Solución: Convertir allCharactersDB en un Set o Map para evitar búsquedas repetitivas.

Ambas formas que con `Set` o `Map` son similares, pero la segunda opción con Map<String, Episode> es más eficiente. Y nunca por comparación normal.
📌 ¿Por qué es mejor?
• Búsqueda rápida: associateBy crea un Map<String, Episode>, lo que permite acceder por id en O(1) en lugar de hacer búsquedas repetitivas en una lista (O(n)).
•	Estructura clara: La BD solo se consulta una vez y no en cada iteración.

Para mejorar la eficiencia de la función, en lugar de usar un `Set` para almacenar los id de los personajes favoritos, podemos utilizar un `Map<Int, Character>` para acceder rápidamente a los personajes favoritos con sus datos completos. Esto permite extender fácilmente el proceso si en el futuro se agregan más atributos desde la BD.

```kotlin
override fun getAllCharacters(): List<Character> {
    // 🚀 1️⃣ Cargar datos del JSON/API
    val allCharactersDto = dao.getAllCharacters()
    val allCharacters = allCharactersDto.map { it.toCharacter() }

    // 🚀 2️⃣ Obtener los personajes favoritos de la BD y convertirlos en un Set para búsqueda rápida
    val favoriteCharacterIds = databaseDao.getAllCharactersDb().map { it.id }.toSet()

    // 🚀 3️⃣ Retornar lista fusionada sin hacer múltiples búsquedas
    return allCharacters.map { character ->
        character.copy(esFavorito = favoriteCharacterIds.contains(character.id))
    }
}
```

Pasamos a:
```kotlin
override fun getAllCharacters(): List<Character> {
    // 🚀 1️⃣ Obtener todos los personajes del JSON/API y mapearlos a la entidad `Character`
    val allCharactersDto = dao.getAllCharacters()
    val allCharacters = allCharactersDto.map { it.toCharacter() }

    // 🚀 2️⃣ Obtener los personajes favoritos de la BD y convertirlos en un Map para acceso rápido
    val favoriteCharactersMap = databaseDao.getAllCharactersDb().associateBy { it.id }

    // 🚀 3️⃣ Fusionar datos del JSON con la BD (si el personaje está en la BD, tomar `esFavorito` de ahí)
    return allCharacters.map { character ->
        val characterDb = favoriteCharactersMap[character.id] // Buscar personaje en la BD
        character.copy(
            esFavorito = characterDb?.esFavorito ?: false // Si está en la BD, usar su estado real
        )
    }
}
```

📌 ¿Por qué esta versión es mejor?
✔ Acceso rápido a los datos de la BD: `associateBy { it.id }` convierte la lista de la BD en un Map<Int, Character>, lo que permite búsquedas O(1) en lugar de O(n).
✔ Facilidad de ampliación: Si en el futuro se necesitan más datos de la BD, solo hay que modificar `copy()`.
✔ Menos iteraciones innecesarias: No se necesita recorrer la lista de la BD varias veces para verificar `esFavorito`, ya que el Map permite un acceso directo.

3️⃣ `Usar suspend en las operaciones de la BD`
• Las operaciones de BD deberían ejecutarse en un coroutineScope, ya que son operaciones de I/O y pueden bloquear el hilo principal si no son suspendidas.
• insertCharacterDb() y deleteCharacterDb() deben ser suspend.

```kotlin
interface CharaterRepository {
    fun getAllCharacters(): List<Character>
    fun getCharactersByName(name: String): List<Character>

    fun getAllCharactersDb(): Flow<List<Character>> // 🚀 Flow para cambios en tiempo real
    suspend fun insertCharacterDb(character: Character)
    suspend fun deleteCharacterDb(id: Int)
}
```

```kotlin
override suspend fun insertCharacterDb(character: Character) {
    databaseDao.insertCharacterDb(character)
}

override suspend fun deleteCharacterDb(id: Int) {
    databaseDao.deleteCharacterDb(id)
}
```