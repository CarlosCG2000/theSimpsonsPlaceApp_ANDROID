
# MI APLICACIÓN

## __________ ESQUEMA MI APP __________

## ____________ (`app`) ____________
### Carpeta `manifest`
- Fichero `AndroidManifest.xml`

### Carpeta `kotlin+java`

### `es.upsa.mimo.thesimpsonplace (main)`

- `MainActivity.kt`
Este es el punto de entrada de tu aplicación en Jetpack Compose (a nivel de Android, porque es la Activity principal que se lanza cuando se inicia la aplicación).
📌 Puntos clave:
• @AndroidEntryPoint permite la inyección de dependencias con Hilt en esta actividad.
• enableEdgeToEdge() configura la UI para usar toda la pantalla.
• setContent { MyApp() } inicia la interfaz de usuario con Compose, renderizando MyApp().

- `TheSimpsonPlaceApp.kt`
Este es el Application de la app, donde configuras Hilt y la base de datos Room.
📌 Puntos clave:
• @HiltAndroidApp habilita Hilt para la inyección de dependencias en toda la app.
• database es una instancia de TheSimpsonsDatabaseRoom, accesible desde Context.database.
• migrationVersion1toVersion2 define una migración de Room, agregando las tablas episodes y quotes.
• initDatabase() inicializa la BD usando Room.databaseBuilder() y registra la migración.

+ Carpeta `utils`
    - `DateExtensions.kt`
    • Extiende String con toDate(), convirtiendo un texto en formato "yyyy-MM-dd" a Date.
	• Extiende Date con toFormattedString(), devolviendo una fecha en "dd/MM/yyyy".
	• Facilita el manejo de fechas en la app.

    - `Logger.kt`
    • Logger es una interfaz con métodos para registrar logs en diferentes niveles (verbose, debug, info, etc.).
	• LoggerClass la implementa, permitiendo su uso en cualquier parte de la app.
	• Útil para depuración y seguimiento del flujo de ejecución.

    - `LocaleHelper.kt`
    • Se encarga del cambio de idioma de forma ordenada y eficiente.
    • Android 13+ (API 33):
	    • Se usa LocaleManager para cambiar el idioma de forma segura y recomendada.
	    • applicationLocales = LocaleList.forLanguageTags(...) es la forma correcta en Android 13+.
    • Android 12 y anteriores:
	    • Se usa la forma antigua (setLocale(locale)) con updateConfiguration, pero con suppress deprecation para evitar errores de compilación.

+ Carpeta `data`

    + Carpeta `daos`
        + Carpeta`local`
            + Carpeta `datastore`
                - `GameDao.kt`
                - `UserDao.kt`

                + Carpeta `impl`
                    - `GameDaoImpl.kt`
                    - `UserDaoImpl.kt`

                * UserDao y GameDao (Interfaces DAO para DataStore)
                • Definen las operaciones para leer/escribir datos de preferencias del usuario (UserDao) y estadísticas de juego (GameDao).
                • Usan Flow<> para obtener cambios en tiempo real.
                • updateUser() y updateStats() para actualizar datos en DataStore.

                * GameDaoImpl (Implementación de GameDao)
                • Usa DataStore<Preferences> para guardar estadísticas de juego (aciertos y preguntas respondidas).
                • gameStatsFlow: Flujo que devuelve un Pair<Int, Int> con aciertos y preguntas.
                • updateStats(): Suma nuevos valores a los existentes.
                • resetStats(): Elimina los datos almacenados.

                * UserDaoImpl (Implementación de UserDao)
                • Usa DataStore<Preferences> para guardar datos del usuario (nombre, tema oscuro, idioma).
                • userPreferencesFlow: Flujo que devuelve un UserPreference.
                • updateUser(): Guarda los nuevos valores en DataStore.

                ⚡ Puntos clave
                ✔ Uso de Flow para obtener datos en tiempo real.
                ✔ DataStore en lugar de SharedPreferences → ✅ Más seguro y eficiente.
                ✔ Implementación con Hilt (@Inject) para inyección de dependencias.
                ✔ Uso de edit {} en DataStore para modificar datos atómicamente.

            + Carpeta `room`
                - `CharacterDatabaseDao.kt`
                - `EpisodeDatabaseDao.kt`
                - `QuoteDatabaseDao.kt`
                - `TheSimpsonsConverters.kt`
                - `TheSimpsonsDatabase.kt`

                * CharacterDatabaseDao (y similares para Episode (EpisodeDatabaseDao) y Quote (QuoteDatabaseDao))
                • Define las operaciones CRUD en Room para CharacterEntity.
                • Usa @Query para obtener todos los personajes o buscar por ID.
                • Usa Flow<List<CharacterEntity>> para obtener actualizaciones en tiempo real.
                • @Insert(onConflict = OnConflictStrategy.REPLACE): inserta o actualiza si ya existe.
                • @Delete: elimina un personaje de la BD.

                * TheSimpsonsConverters (Conversores de tipos para Room)
                • Convierte tipos complejos (que Room no maneja nativamente) en tipos primitivos:
                • Date ↔ Long
                • List<String> ↔ String (JSON)
                • URL ↔ String

                * TheSimpsonsDatabaseRoom (Base de datos de Room)
                • Usa @Database para definir las entidades (CharacterEntity, EpisodeEntity, QuoteEntity).
                • Versión 2 de la BD → indica que ha habido un cambio en la estructura (se añadieron Episodes y Quotes).
                • @TypeConverters(TheSimpsonsConverters::class): Habilita los conversores para manejar tipos complejos.
                • abstract fun characterDbDao(): expone los DAOs para interactuar con la BD.

                ⚡ Puntos clave
                ✔ Uso de Flow en consultas para reactividad.
                ✔ Conversores (@TypeConverters) permiten manejar tipos complejos.
                ✔ Actualización de la BD con version = 2 y migraciones.
                ✔ Buen uso de OnConflictStrategy.REPLACE para evitar conflictos en inserts.

        + Carpeta `remote`
            - `CharacterDao.kt`
            - `EpisodeDao.kt`
            - `QuoteDao.kt`

            + Carpeta `impl`
                - `CharacterDaoImpl.kt`
                - `EpisodeDaoImpl.kt`

            * Interfaces DAO (CharacterDao, EpisodeDao, QuoteDao)
            Estas interfaces definen las funciones para obtener datos remotos (desde una API o un archivo JSON en assets).
            🟡 CharacterDao
                • getAllCharacters(): Retorna todos los personajes disponibles.
                • getCharactersByName(name: String): Filtra personajes por nombre.

            🟠 EpisodeDao
                • getAllEpisodes(): Obtiene todos los episodios.
                • getEpisodeById(id: String): Busca un episodio por ID.
                • getEpisodesByTitle(title: String): Filtra episodios por título.
                • getEpisodesByDate(minDate: Date?, maxDate: Date?): Filtra episodios por fecha.
                • getEpisodesBySeason(season: Int): Filtra por temporada.
                • getEpisodesByChapter(chapter: Int): Filtra por capítulo.

            🔴 QuoteDao
                • Usa Retrofit para obtener frases de la API externa.
                • getQuotes(numElementos, textPersonaje): Hace una petición HTTP a https://thesimpsonsquoteapi.glitch.me/quotes.
                • @GET("/quotes") → Define el endpoint.
                • @Query("count") → Número de frases a recuperar.
                • @Query("character") → Opcionalmente, filtra por personaje.

            * CharacterDaoImpl y EpisodeDaoImpl usa JSON en assets en lugar de API
            • En lugar de llamar a una API, lee archivos JSON locales en assets.
            • Dependencias: Recibe Context y los nombres de los archivos JSON (dataJson, imageJson).
            • Usa Kotlinx Serialization (Json { ignoreUnknownKeys = true }) para parsear el JSON.

            ⚡ Puntos clave
            ✔ DAO desacoplado de Retrofit → Puede obtener datos de API o JSON local.
            ✔ Uso de Kotlinx Serialization en vez de Gson para mayor eficiencia.
            ✔ Uso de assets.open() para leer JSON en assets como alternativa a una API.
            ✔ Flow en QuoteDao para reactividad en las peticiones HTTP.

    + Carpeta `di`
        - `DatabaseModule.kt`
        - ❌ `NetworkModule.kt` (borrado y pasado para usar en `es.upsa.mimo.thesimpsonplace (remote)` o `es.upsa.mimo.thesimpsonplace (mock)`, gracias a `Flavors` definido en `Gradle Scripts`)
        - `DataModule.kt`
        - `DomainModule.kt`

        * DatabaseModule.kt (Provee DAOs de Room)
        Este módulo se encarga de proporcionar los DAOs de Room, permitiendo la interacción con la base de datos. Y la inyeccón de Room y la DataStore.
        ✔ Funciones @Provides en DAOs
            • provideCharacterDatabaseDao() → Retorna CharacterDatabaseDao.
            • provideEpisodeDatabaseDao() → Retorna EpisodeDatabaseDao.
            • provideQuoteDatabaseDao() → Retorna QuoteDatabaseDao.
        ✔ TheSimpsonsDatabaseRoom para Room.
            • provideDatabaseRoom() → Función @Provides de Room Database asegura que Room solo tenga una instancia de la base de datos.
        ✔ DataStore para GameDataStore y UserDataStore. Con Qualifiers:
            • Se usa @Qualifier para diferenciar los DataStore<Preferences> de “game” y “user”.
            • Permite inyectar dependencias sin que haya conflicto entre ambas instancias.

        * NetworkModule.kt (Retrofit y API remota)
        Este módulo gestiona la configuración de Retrofit para interactuar con la API externa.
        ✔ Funciones @Provides
            • provideRetrofit() → Instancia única de Retrofit.
            • provideQuoteDao() → Crea QuoteDao con retrofit.create(QuoteDao::class.java).
         No es necesario crear un QuoteDaoImpl, ya que Retrofit implementa la interfaz automáticamente.

        * DataModule.kt (Fuentes de Datos y DataStore)
        Este módulo proporciona:
        ✔ CharacterDao y EpisodeDao, permitiendo elegir entre modo producción o test con archivos JSON.
        Ventaja: Permite cambiar entre archivos de producción y prueba sin modificar el código en otros lugares.

        * DomainModule.kt (Casos de Uso y Repositorios)
        Este módulo proporciona los repositorios y casos de uso mediante @Binds.
        ✔ @Binds se usa en lugar de @Provides cuando ya existe una implementación de una interfaz. Separa el acceso a datos del dominio, mejorando la escalabilidad y mantenibilidad.
        ✔ @Singleton se usa en los repositorios (repository) para mantener una única instancia de CharacterRepositoryImpl, etc.

    + Carpeta `entities`
        + Carpeta `character`
            - `CharacterDTO.kt`
            - `CharacterEntity.kt`
            - `imageDTO.kt`
            - `Gender.kt`

        + Carpeta `episode`
            - `EpisodesDTO.kt`
            - `EpisodeDTO.kt`
            - `EpisodeEntity.kt`

        + Carpeta `quote`
            - `QuoteDTO.kt`
            - `QuoteEntity.kt`

        + Carpeta `user`
            - `Language.kt`
            - `UserPreference.kt`

        Partimos de solo un ejemplo el de Character (ya que los demás son logicamente muy parecidos)
        * CharacterDTO (Data Transfer Object)
        • Representa la estructura de los datos que llegan desde la API.
        • Usa @Serializable para poder deserializar JSON.
        • @SerialName("campo_api") permite mapear nombres de la API a nombres más manejables en el código.
        • Contiene una función getIdAsInt() para asegurar que id se convierta a Int.

        * CharacterEntity (Entidad para Room)
        • Es el modelo que se almacena en la base de datos local.
        • Usa @Entity para definir la tabla en Room.
        • Define TABLE_NAME en un companion object para centralizar el nombre.
        • Usa @PrimaryKey(autoGenerate = false) para definir la clave primaria.

        * Gender (Enumerado para género)
	    • Define los valores posibles de género (Male, Female, Undefined).
    	• Tiene una función fromAbbreviation() para mapear los valores de la API ("m" y "f") al enum.

        * ImageDTO
	    • Representa imágenes asociadas a un personaje en la API.
	    • Usa @Serializable y @SerialName para definir el mapeo JSON.

        ⚡ Puntos importantes a considerar
        1.	Separación clara entre DTOs y Entities → Ayuda a diferenciar datos de API y base de datos.
        2.	Uso de @Serializable en DTOs → Permite una conversión eficiente de JSON.
        3.	Uso de enums (Gender) → Facilita la gestión de valores en lugar de usar String directamente.
        4.	companion object en CharacterEntity → Mantiene constantes como el nombre de la tabla centralizado.

    + Carpeta `mappers`
        - `CharacterDtoMapper.kt`
        - `EpisodeDtoMapper.kt`
        - `QuoteDtoMapper.kt`

        * Estos archivos (CharacterDtoMapper, EpisodeDtoMapper, QuoteDtoMapper) contienen funciones de extensión para convertir entre diferentes modelos de datos:
        • DTO → Modelo de dominio (datos de la API/JSON a la app).
        • ROOM → Modelo de dominio (datos almacenados localmente a la app).

+ Carpeta `domain`

    + Carpeta `mappers`
        - `CharacterMapper.kt`
        - `EpisodeMapper.kt`
        - `QuestionMapper.kt`
        - `QuoteMapper.kt`

        * Estos mapeadores (mappers) convierten datos entre diferentes modelos de la aplicación.
        Son utilizables en diferentes capas del proyecto (BD, lógica de negocio y UI).  Como:
            • toCharacterDb() y toQuoteDb() sirven para persistencia en Room.
            • toQuestion() convierte una cita en una pregunta con opciones para el juego.
            • getRandomOptions() genera respuestas incorrectas aleatorias.
            • ... demás funciones de mapeo.

    + Carpeta `models`
        - `Character.kt`
        - `Episode.kt`
        - `EpisodeFilter.kt`
        - `Question.kt`
        - `Quote.kt`

        * Las clases de dominio, que representan los datos tal y como los usa la lógica de negocio de la aplicación.
        Estas clases son independientes de la base de datos (Entity) o de la API/JSON (DTO).

    + Carpeta `repository`
        - `CharacterRepository.kt`
        - `EpisodeRepository.kt`
        - `QuestionRepository.kt`
        - `QuoteRepository.kt`
        - `GameRepository.kt`
        - `UserRepository.kt`

        + Carpeta `impl`
            - `CharacterRepositoryImpl.kt`
            - `EpisodeRepositoryImpl.kt`
            - `QuestionRepositoryImpl.kt`
            - `QuoteRepositoryImpl.kt`
            - `GameRepositoryImpl.kt`
            - `UserRepositoryImpl.kt`

        El patrón Repository para separar la lógica de acceso a datos y proporcionar una abstracción sobre las fuentes de datos.

        * CharacterRepository.kt (Interfaz), etc
        Define los métodos que la capa de dominio puede usar para acceder a los datos. No sabe de dónde vienen los datos, solo define las operaciones disponibles.

        * CharacterRepositoryImpl.kt (Implementación), etc
        Implementa la interfaz usando CharacterDao (fuente remota) y CharacterDatabaseDao (base de datos local).
        Se ejecuta en Dispatchers.IO para evitar bloquear el hilo principal. Usa Flow para emitir cambios en la BD en tiempo real.

        * 📌 Conclusión
        • Desacoplamiento: La UI no sabe si los datos vienen de la API o de la BD, solo usa el Repository.
        • Uso de mappers: Convierte CharacterDTO (API) ↔ Character (Dominio) ↔ CharacterEntity (BD).
        • Corrutinas y Flow:
            • Dispatchers.IO para llamadas de red.
            • Flow para datos en tiempo real desde Room.

    + Carpeta `usecases`

        + Carpeta `character`
            - `GetAllCharactersUseCase.kt`
            - `GetAllCharactersDbUseCase.kt`
            - `GetCharacterDbByIdUseCase.kt`
            - `GetFilterNameCharactersUseCase.kt`
            - `InsertCharacterDbUseCase.kt`
            - `DeleteCharacterDbUseCase.kt`

        + Carpeta `episode`
            - `GetAllEpisodesUseCase.kt`
            - `GetEpisodeByIdUseCase.kt`
            - `GetEpisodesByTitleUseCase.kt`
            - `GetEpisodesByDateUseCase.kt`
            - `GetEpisodesBySeasonUseCase.kt`
            - `GetEpisodesByChapterUseCase.kt`
            - `GetEpisodesByViewUseCase.kt`
            - `GetEpisodesOrderUseCase.kt`
            - `GetAllEpisodesDbUseCase.kt`
            - `GetEpisodeDbByIdUseCase.kt`
            - `GetWatchedEpisodesUseCase.kt`
            - `IsEpisodeDbWatchedUseCase.kt`
            - `IsEpisodeDbFavoriteUseCase.kt`
            - `InsertEpisodeDbUseCase.kt`
            - `UpdateEpisodeDbStatusUseCase.kt`

        + Carpeta `quote`
            - `GetQuotesUseCase.kt`
            - `GetAllQuotesDbUseCase.kt`
            - `GetQuoteDbByCitaUseCase.kt`
            - `InsertQuoteDbUseCase.kt`
            - `DeleteQuoteDbUseCase.kt`

        + Carpeta `game`
            - `GetGameStatsUseCase.kt`
            - `UpdateStatsUseCase.kt`
            - `ResetStatsUseCase.kt`

        + Carpeta `user`
            - `GetUserPreferencesUseCase.kt`
            - `UpdateUserUseCase.kt`

        + Carpeta `impl`

            + Carpeta `character`
                - `GetAllCharactersUseCaseImpl.kt`
                - `GetAllCharactersDbUseCaseImpl.kt`
                - `GetCharacterDbByIdUseCaseImpl.kt`
                - `GetFilterNameCharactersUseCaseImpl.kt`
                - `InsertCharacterDbUseCaseImpl.kt`
                - `DeleteCharacterDbUseCaseImpl.kt`

            + Carpeta `episode`
                - `GetAllEpisodesUseCaseImpl.kt`
                - `GetEpisodeByIdUseCaseImpl.kt`
                - `GetEpisodesByTitleUseCaseImpl.kt`
                - `GetEpisodesByDateUseCaseImpl.kt`
                - `GetEpisodesBySeasonUseCaseImpl.kt`
                - `GetEpisodesByChapterUseCaseImpl.kt`
                - `GetEpisodesByViewUseCaseImpl.kt`
                - `GetEpisodesOrderUseCaseImpl.kt`
                - `GetAllEpisodesDbUseCaseImpl.kt`
                - `GetEpisodeDbByIdUseCaseImpl.kt`
                - `GetWatchedEpisodesUseCaseImpl.kt`
                - `IsEpisodeDbWatchedUseCaseImpl.kt`
                - `IsEpisodeDbFavoriteUseCaseImpl.kt`
                - `InsertEpisodeDbUseCaseImpl.kt`
                - `UpdateEpisodeDbStatusUseCaseImpl.kt`

            + Carpeta `quote`
                - `GetQuotesUseCaseImpl.kt`
                - `GetAllQuotesDbUseCaseImpl.kt`
                - `GetQuoteDbByCitaUseCaseImpl.kt`
                - `InsertQuoteDbUseCaseImpl.kt`
                - `DeleteQuoteDbUseCaseImpl.kt`

            + Carpeta `game`
                - `GetGameStatsUseCaseImpl.kt`
                - `UpdateStatsUseCaseImpl.kt`
                - `ResetStatsUseCaseImpl.kt`

            + Carpeta `user`
                - `GetUserPreferencesUseCaseImpl.kt`
                - `UpdateUserUseCaseImpl.kt`

        * Estoy aplicando el principio de separación de responsabilidades (SRP) dentro de la capa de dominio al introducir casos de uso (Use Cases). Estos encapsulan reglas de negocio y evitan que la UI acceda directamente a los repositorios.

        *  Ejemplo con Character con GetAllCharactersDbUseCase y GetAllCharactersDbUseCaseImpl:
        Define un contrato que dice que el caso de uso debe devolver un Flow<List<Character>. La UI solo usa esta interfaz, sin saber qué repositorio usa internamente.
        La implementación (Impl) simplemente delega la llamada al repositorio. Usa inyección de dependencias (@Inject constructor) para recibir el CharacterRepository.

        * Beneficios del enfoque
        Mayor separación de responsabilidades:
            • La capa de dominio se encarga solo de la lógica de negocio.
            • La capa de datos (repositorios) gestiona el acceso a la API y BD.
        Facilidad para hacer pruebas unitarias:
            • Los UseCase se pueden testear individualmente con mocks de Repository.
        Evita dependencias directas en la UI:
            • Si la UI necesita personajes de la BD, usa GetAllCharactersDbUseCase, sin tocar directamente el repositorio.

        * Conclusión
        Has implementado correctamente el patrón de “casos de uso” (Use Cases) en MVVM + Clean Architecture.
        El código es modular, testable y escalable.

+ Carpeta `presentation`

    + Carpeta `ui`

        + Carpeta `components`
            - `BottomBarComponent.kt`
            - `TopBarComponent.kt`
            - `NoContentComponent.kt`
            - `MySearchTextField.kt`
            - `ModifierContainer.kt`

            + Carpeta `character`
                - `CharacterList.kt`
                - `CharacterItem.kt`

            + Carpeta `episode`
                - `ListEpisodes.kt`
                - `EpisodeItem.kt`

            + Carpeta `quote`
                - `BottomBarQuoteComponent.kt`
                - `ListQuotes.kt`
                - `QuoteItem.kt`

            + Carpeta `game`
                - `HistoryGameStatistics.kt`
                - `PieChartData.kt`

        + Carpeta `navigation`
            - `NavegacionApp.kt`

            * Este archivo gestiona la navegación de la app utilizando NavHost y NavController.
            Se estructura en:
            • 📌 Clase Screen: Define las rutas de la app con sealed class Screen, permitiendo pasar parámetros en las rutas (ej. EpisodeDetail(id)).
            • 📌 NavHost con NavController: Define 16 pantallas en total y sus transiciones.

            * Resumen
            • NavegacionApp.kt organiza la navegación con NavHost.
		    • Screen define las rutas de manera estructurada y clara.
		    • navigateTo() simplifica la navegación en toda la app.

        + Carpeta `root`
            - `MyApp.kt`

            * Es el punto de entrada de la aplicación (a nivel de la UI en Jetpack Compose, el primer Composable que se ejecuta cuando la aplicación se carga.)
            • Obtiene las preferencias del usuario (userState) desde ProfileViewModel.
            • Aplica el idioma seleccionado con LocaleHelper.updateLocale().
            • Configura el tema oscuro/claro con TheSimpsonPlaceTheme.
            • Inicia la navegación con NavegacionApp().

        + Carpeta `screen`

            + Carpeta `characterSection`
                - `CharactersScreen.kt`
                - `CharacterFilterScreen.kt`
                - `CharactersFavScreen.kt`

            + Carpeta `episodeSection`
                - `EpisodesScreen.kt`
                - `EpisodesFilterScreen.kt`
                - `EpisodesFavScreen.kt`
                - `EpisodeDetailScreen.kt`

            + Carpeta `quoteSection`
                - `QuotesScreen.kt`
                - `QuotesFilterScreen.kt`
                - `QuotesFavScreen.kt`

                + Carpeta `gameQuotes`
                    - `QuotesGameScreen.kt`
                    - `QuotesQuestionScreen.kt`
                    - `QuotesResultScreen.kt`

            + Carpeta `menuSection`
                - `MenuScreen.kt`
                - `ItemMenuComponent.kt`
                - `TopBarMenuComponent.kt`

            + Carpeta `profileSection`
                - `ProfileScreen.kt`
                - `TopBarProfileComponent.kt`

                + Carpeta `profileEdit`
                    - `ProfileEditScreen.kt`

        + Carpeta `theme`
            - `Color.kt`
            - `Theme.kt`
            - `Type.kt`

    + Carpeta `viewmodel`

        + Carpeta `character`

            + Carpeta `charactersList`
                - `ListCharactersViewModel.kt`
                - `ListCharactersStateUI.kt`

            + Carpeta `charactersFilterName`
                - `ListCharactersFilterViewModel.kt`
                - `ListCharactersFilterStateUI.kt`

            + Carpeta `charactersListFav`
                - `ListCharactersDBViewModel.kt`
                - `ListCharactersDbStateUI.kt`


        + Carpeta `episode`

            + Carpeta `episodesList`
                - `ListEpisodesViewModel.kt`
                - `ListEpisodesStateUI.kt`

            + Carpeta `episodesFilterName`
                - `ListEpisodesFilterViewModel.kt`
                - `ListEpisodesFilterStateUI.kt`

            + Carpeta `episodesListFav`
                - `ListEpisodesDBViewModel.kt`
                - `ListEpisodesDbStateUI.kt`

            + Carpeta `episodeDetails`
                - `DetailsEpisodeViewModel.kt`
                - `DetailsEpisodeStateUI.kt`

        + Carpeta `profile`
            - `ProfileStateUI.kt`
            - `ProfileViewModel.kt`

        + Carpeta `quote`

            + Carpeta `quotesListFav`
                - `ListQuotesDBViewModel.kt`
                - `ListQuotesDbStateUI.kt`

            + Carpeta `quotesList`
                - `ListQuotesViewModel.kt`
                - `ListQuotesStateUI.kt`

            + Carpeta `quotesGame`

                + Carpeta `questionGame`
                    - `QuotesGameViewModel.kt`
                    - `QuotesGameUI.kt`

                + Carpeta `resultGame`
                    - `ResultGameViewModel.kt`
                    - `ResultGameUI.kt`

        * Esta parte de la arquitectura se encarga de manejar la lógica de presentación para la pantalla de lista de personajes, episodios, citas, juego de citas y perfil de usuario.
        Define el estado de la UI.
        🛠 Resumen y buenas prácticas
            • Usar StateFlow y update {} en lugar de modificar valores directamente.
            • Separar estado y lógica → StateUI solo almacena datos, ViewModel los gestiona.
            • Ejecutar llamadas a la API o base de datos dentro de viewModelScope.launch para evitar bloqueos.

### `es.upsa.mimo.thesimpsonplace (remote)`

+ Carpeta `data`
    + Carpeta `di`
        - `NetworkModule.kt`

### `es.upsa.mimo.thesimpsonplace (mock)`

+ Carpeta `data`
    + Carpeta `di`
        - `MockModule.kt`

### `es.upsa.mimo.thesimpsonplace (androidTest)`
    (vacío: serían los test de integración y de UI)

### `es.upsa.mimo.thesimpsonplace (test)`
+ Carpeta `data.daos.remote.impl`
    - Fichero `CharacterDaoImplTest.kt`

+ Carpeta `domain.usescases.impl.character`
    - Fichero `DeleteCharacterDbUseCaseImplTest.kt`
    - Fichero `GetAllCharactersUseCaseImplTest.kt`

+ Carpeta `presentation.viewmodel.character`
    + Carpeta `charactersFilterName`
        - Fichero `ListCharactersFilterViewModelTest.kt`
    + Carpeta `charactersListFav`
        - Fichero `ListCharactersDBViewModelTest.kt`

+ Carpeta `useCasesMockFake`
    - Fichero `DeleteCharacterDbUseCaseMock.kt`
    - Fichero `GetAllCharactersDbUseCaseMock.kt`
    - Fichero `GetCharacterDbByIdUseCaseMock.kt`
    - Fichero `InsertCharacterDbUseCaseMock.kt`

- Fichero `CharactersFake.kt`

### `es.upsa.mimo.thesimpsonplace.presentation.ui (screenshotTest)`
+ Carpeta `component.character`
    - `CharacterItemScreenshotPreview.kt`

+ Carpeta `screen.characterSection`
    - `CharactersListScreenshotPreview.kt`

### Carpeta `assets`
- Ficheros `personajes_data.json` y `personajes_test.json`: jsons de personajes (prod y test)
- Ficheros `imagenes_data.json` y `imagenes_test.json`: jsons de imagenes (prod y test)
- Ficheros `episodios_data.json` y `episodios_test.json`: jsons de episodios (prod y test)
- Fichero `citas_test.json`: json de citas (test). En producción la fuente es una ApiRest (https://thesimpsonsquoteapi.glitch.me/)

### Carpeta `res`
+ Carpeta `drawable`
    - Imágenes webp de personajes de los Simpsons.

+ Carpeta `values`
    + Carpeta `strings`
        - Fichero `strings.xml`
        - Fichero `strings.xml (en)`
        - Fichero `strings.xml (es)`
        - Fichero `strings.xml (fr)`

## ____________ (`Gradle Scripts`) ____________
- Fichero `build.gradle.kts` (project)
- Fichero `build.grade.kts` (module :app)
- Fichero `libs.versions.toml`
- Fichero `settings.gradle.kts`

## REQUISITOS
Requisitos `OBLIGATORIOS` de la aplicación:
1. Uso de `minSdk 24` ✅
2. Uso de `AndroidX` ✅
3. Aplicación desarrollada en `Kotlin` ✅
4. Uso de `ViewModel` ✅
5. Uso de `Flow` ✅
6. Uso de `Coroutines` ✅
7. Utilización `DrawerLayout/ButtomNavigationView` para crear una estructura de navegación de app  ✅
8. Crear un mínimo de `3 CustomView` propia ✅
9. En caso de utilizarse `diálogos`, se tendrá que utilizar `DialogFragments` ✅
10. Disponer de una `pantalla de Settings` que permita elegir y cambiar algún `parámetro funcional` de la app ✅
11. Tener al menos una acción en la `Toolbar` además de la de `Settings` ✅
12. Uso de `variantes con gradle`. Se requerirá al menos tener dos variantes diferentes, ya sea empleando `Flavors` (free, paid, staging, production, etc...) o mediante `Build Types` (debug, beta, release...). En ambos casos se requerirá que las dos variantes contengan parte del código completamente aislado uno de otro. Pueden ser dos features completamente diferentes o dos entornos de backend que obtengan los datos de manera distinta, en el caso de los `Flavors`, o en el caso de `Build Types` diferentes herramientas que puedan ayudar al desarrollador en diferentes escenarios, como podría ser la posibilidad de cambiar la `URL del servidor` a la que apunta la aplicación, añadir una `capa de logging` o funcionalidad adicional que ayude a `detectar errores` en debug. En cualquier caso, esto son solo ejemplos para los que se pueden usar los flavors; pero cualquier uso creativo de las variantes será bienvenido. ✅
13. Los `textos, dimensiones, colores y estilos` deben residir en su `fichero de recursos` correspondiente. Usar al menos un estilo en los `layouts XML`. ✅
14. Uso de permisos con `Android 6.0` ✅
15. Creación de `una paleta de colores propia` de la aplicación, de modo que visualmente se pueda identificar la app a un estilo visual reconocible. ✅
16. Uso de un `modo de persistencia local` además de las `Preferences DataStore` (también obligatorio). Entre las opciones de persistencia local, se permitirán tanto almacenamiento en base de datos exclusivamente (`Room`) como frameworks que aporten esa solución (`como Firebase por ejemplo`) ✅
17. Uso de `ConstraintLayout`, `ViewPager` y `SwipeRefreshLayout`. ✅
18. Realización de `peticiones web`: ya sea servicios `REST` con `Volley` o `Retrofit` o con `APIs` que se encarguen de `encapsular peticiones` como podría ser con `Amazon Web Services` por ejemplo. ✅
19. Subir la aplicación a `Google Play` (aunque sea en fase alpha o beta e invitar a los profesores) ❌

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
* 7. `com.google.code.gson`: `GSON` para obtener del fichero json los personajes (character).
* 8. `hilt`: para inyección de dependecias.
* 9. `retrofit`: para llamada a red/apis.
* 10. `datastore`: base de datos local para datos simples primitivos.

5. Añadir en el esqueleto (`punto 3.`) a aparte la que esta ya de personajes, las `secciones` de `episodio`, `citas` y `juego`.
En `1. DUDA`, explicado el esquema que he seguido para app (muy similar en personajes con las demás secciones que ahora implemento).

Ejemplo con la `sección episodios`: 😸
Ejemplo con la `sección citas`: 😸
Ejemplo con la `sección juego`: 😸

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
- VER VIDEO CLASE 2 DE ANTONIO LEIVA (Y VIDEOS DE MIMO POR EL SUBIDOS)
- REPASAR LAS CLASES DE ROBERTO
- PASAR A REALIZAR EL VIEW MODEL Y CORRUTINAS (ASINCRONÍA) **### DUDA 11.** Y LAS PANTALLAS BONITAS.

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
Y si en vez de combinar en el repositorio `los episodios de db` con los de `json` se realiza por `separado` y cada vez que se vea un elemento de episodio lo que haga sea llamar a la función de ver si esta en la BD si esta ver su `estado de fav y visto` y según ese mostrar de una forma u otra. Eso no seria más correcto o mejor en rendimiento? **¿Es mejor unir los datos de la `BD y JSON en el repositorio` o `consultarlos al mostrarlos`?**

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

### 8. DUDA
@@@@ DUDA @@@@
Segun me explicas en la sección de Quote si quiero crear la BD con Room tengo que tener las entidades Room (al estilo que en SwiftUI para tener las entidades SwiftData tenia que tener las entidades con su macro @Model)

Mi duda en el punto al igual de la integración con Room para la entidad QuoteEntity, entonces si quiero usar la misma BD de Room para persinajes y episodios tambien tendre que **¿crear sus entidades de ROOM verdad?**

@@@@ RESPUESTA GHAT GPT @@@@
Sí, si quieres usar Room para gestionar la persistencia de personajes (Character) y episodios (Episode), necesitas crear sus respectivas entidades de Room.

Cada entidad de Room representa una tabla en la base de datos, por lo que debes definir una entidad por cada tipo de dato que quieras almacenar en la BD.

1️⃣ Cómo estructurar las entidades en Room
Para seguir una estructura coherente, puedes hacer lo mismo que hiciste con QuoteEntity, creando una entidad para personajes (CharacterEntity) y otra para episodios (EpisodeEntity).

2️⃣ Definir RoomDatabase
Una vez definidas las entidades, necesitas un RoomDatabase que maneje la BD completa.
```kotlin
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [QuoteEntity::class, CharacterEntity::class, EpisodeEntity::class], // Agregamos todas las entidades
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun quoteDao(): QuoteDatabaseDao
    abstract fun characterDao(): CharacterDatabaseDao
    abstract fun episodeDao(): EpisodeDatabaseDao
}
```

3️⃣ Inicializar Room en Application
Para que Room funcione en toda la app, inicialízala en Application:
```kotlin
import android.app.Application
import androidx.room.Room

class MyApp : Application() {
    companion object {
        lateinit var database: AppDatabase
    }

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "app_database"
        ).build()
    }
}
```
✅ Explicación:
• Room.databaseBuilder() crea la BD.
• Se inicializa una única vez en toda la app.

### 9. DUDA
@@@@ DUDA @@@@
Le paso la sección de **casos de uso de `Quotes`** para que me **muestre posibles mejoras**

@@@@ RESPUESTA GHAT GPT @@@@

1️⃣ Usar operator `fun invoke()` en vez de `execute()`
• Es una buena práctica en casos de uso, ya que hace que el código sea más limpio y fácil de usar.
• En vez de useCase.execute(), puedes simplemente llamar useCase().

```kotlin
interface GetAllQuoteDbUseCase {
    operator fun invoke(): List<Quote>
}
```

```kotlin
val getAllQuoteDbUseCase = GetAllQuoteDbUseCaseImpl(repository) // Crear una instancia del UseCase correctamente
val quotes = getAllQuoteDbUseCase() // Dado que invoke() está sobrescrito, puedes llamarlo como una función normal
```

• Con la version antigua con `execute()` en vez de `invoke()`
```kotlin
interface GetAllEpisodesDbUseCase {
    fun execute(): List<Quote>
}
```
```kotlin
val getAllQuoteDbUseCase = GetAllQuoteDbUseCaseImpl(repository) // Crear una instancia del UseCase correctamente
val quotes = getAllQuoteDbUseCase.execute() // añadir la invocación con `.execute()`
```

2️⃣ Marcar los casos de uso como `suspend` si acceden a la `BD` o `API`, pero no si es `JSON`.
• Como `Room` y `API` son `operaciones asincrónicas`, lo correcto es hacerlas en un `hilo en segundo plano`.

Debes marcar un caso de uso con suspend si realiza una operación bloqueante o de larga duración, como:
✅ Llamadas a la API (Red)
✅ Consultas a la base de datos (Room, Firebase…)
✅ Operaciones pesadas (cálculos intensivos)

💡 Si accedes a un JSON en memoria (Mock de datos), no es necesario usar suspend porque la operación es rápida y no bloquea el hilo principal.

👀 Si `getEpisodeByIdUseCase` llama internamente a `getEpisodeByIdDbUseCase`, y este último es `suspend`, entonces `getEpisodeByIdUseCase` también debería ser `suspend`, porque una función normal no puede llamar directamente a una función suspend sin estar dentro de una corrutina.

📌 Reglas básicas
- Si una función accede a una `BD o API` → `suspend`
- Si una `función llama` a `otra suspend` → también `suspend`

• Modifica las interfaces y las implementaciones con suspend:

¿Cómo funciona `suspend`?
En Kotlin, suspend se usa en funciones que pausan su ejecución sin bloquear el hilo.
🚀 Se usan en `coroutines`, permitiendo `ejecutar código de forma asíncrona`.

 ¿Cómo funciona `viewModelScope.launch {}`?
`viewModelScope.launch` es una `corrutina vinculada al ciclo de vida del ViewModel`.
👉 Se `cancela` automáticamente cuando el `ViewModel` se `destruye`.

```kotlin
interface GetQuotesUseCase {
    suspend operator fun invoke(numElementos: Int, textPersonaje: String): List<Quote>
}

class GetQuotesUseCaseImpl(val repository: QuoteRepository): GetQuotesUseCase {
    override suspend fun invoke(numElementos: Int, textPersonaje: String): List<Quote>
        = repository.getQuotes(numElementos, textPersonaje)
}
```

🔹 Así podrás llamarlo desde una corrutina en un ViewModel:
```kotlin
viewModelScope.launch {
    val quotes = getQuotesUseCase(10, "Homer Simpson")
}
```

¿Por qué usar `viewModelScope.launch` y no `GlobalScope.launch`?
✅ `viewModelScope.launch` ➝ Mejor gestión del ciclo de vida, se cancela automáticamente.
❌ `GlobalScope.launch` ➝ No se cancela, puede causar memory leaks si la UI desaparece.

📌 Conclusión
✔ Si una función llama a otra suspend, debe ser suspend
✔ Si mezcla datos de una fuente no suspend (JSON) con una suspend (BD), debe ser suspend
✔ Si no accede a una BD/API ni llama a otra suspend, entonces no necesita ser suspend
✔ Ejecuta los suspend con viewModelScope.launch en ViewModel.
✔ Evita GlobalScope.launch para prevenir memory leaks.

3️⃣ Unificar el `nombre de los casos de uso` de la `BD` y `API`
• Para diferenciar mejor los casos de uso, puedes agregar un sufijo:
• Para la API: FetchQuotesUseCase
• Para la BD: LoadQuotesFromDbUseCase

✅ Más limpio: El código es más corto y más claro al usar invoke().
✅ Asincronía: Usar suspend evita bloqueos en la UI y mejora el rendimiento.
✅ Más idiomático: Seguirás las prácticas recomendadas de Kotlin.

### 10. DUDA
Como deberia implementar `la entidad y logica del juego`

1️⃣ Creación de la entidad Question en `domain/entities` con `Question.kt`
2️⃣ Creación del mapeo de Quote a Question en `domain/mappers` con `QuestionMapper.kt`
3️⃣ Creación del caso de uso en `domain/usecases/quote` y `domain/usecases/impl/quote`
4️⃣ Implementación del ViewModel en `presentation/viewmodel`

```kotlin
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import es.upsa.mimo.thesimpsonplace.domain.entities.Question
import es.upsa.mimo.thesimpsonplace.domain.usecases.GetQuestionsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class QuizViewModel(private val getQuestionsUseCase: GetQuestionsUseCase) : ViewModel() {

    private val _questions = MutableStateFlow<List<Question>>(emptyList())
    val questions: StateFlow<List<Question>> = _questions

    fun loadQuestions(numElements: Int) {
        viewModelScope.launch {
            _questions.value = getQuestionsUseCase.execute(numElements)
        }
    }
}

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import es.upsa.mimo.thesimpsonplace.presentation.viewmodel.QuizViewModel

@Composable
fun QuizScreen(viewModel: QuizViewModel = viewModel()) {
    val questions by viewModel.questions.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadQuestions(5) // Cargar 5 preguntas
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        questions.forEach { question ->
            Card(
                modifier = Modifier.fillMaxWidth().padding(8.dp),
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = question.cita, style = MaterialTheme.typography.headlineSmall)
                    Spacer(modifier = Modifier.height(8.dp))

                    val opciones = listOf(question.personajeCorrecto) + question.personajeIncorrectos
                    opciones.shuffled().forEach { opcion ->
                        ClickableText(
                            text = androidx.compose.ui.text.AnnotatedString(opcion),
                            onClick = { /* TODO: Manejar la respuesta */ }
                        )
                    }
                }
            }
        }
    }
}
```
✅ Utilizamos `StateFlow` para manejar el `estado reactivo` en la UI.
✅ `viewModelScope.launch` inicia una `corrutina` para llamadas suspendidas.

### 11. DUDA 📚
📌 Entendiendo los conceptos: `ViewModel`, `Holder Observables` y `Corrutinas`

Para que quede claro, vamos a separar y explicar cada concepto, cómo se relacionan y qué función cumplen en una aplicación con `Jetpack Compose`.

#### 1️⃣ `ViewModel`: Persistencia y Gestión de Estado

🔹 Propósito:
El `ViewModel` es una `clase diseñada` para sobrevivir a los `cambios de configuración` (por ejemplo, la rotación de pantalla) y `almacenar el estado y la lógica de negocio` de una `pantalla`.

🔹 Cómo ayuda a `la reactividad`:
• Contiene `datos que la UI` necesita (ejemplo: una `lista de elementos`).
• Expone esos datos con `Holder Observables (StateFlow, LiveData o MutableState)`.
• La `UI se suscribe a estos datos`, por lo que se `actualiza automáticamente cuando cambian`.

🔹 Ejemplo: `ViewModel` con `StateFlow`
```kotlin
class QuoteViewModel(private val getQuotesUseCase: GetQuotesUseCase) : ViewModel() {

    private val _quotes = MutableStateFlow<List<Quote>>(emptyList())
    val quotes: StateFlow<List<Quote>> = _quotes // UI observa este flujo

    fun loadQuotes(numElements: Int) {
        viewModelScope.launch { // Corrutina (asincronía)
            _quotes.value = getQuotesUseCase(numElements)
        }
    }
}
```

✅ Aquí usamos `StateFlow` para que la` UI sea reactiva` a los cambios en `_quotes`.
✅ `viewModelScope.launch` ejecuta el código en segundo plano (`corrutina`).

⸻

#### 2️⃣ `Holder Observables`: Estado Reactivo dentro de Compose

🔹 Propósito:
Un `Holder Observable (State, MutableState, StateFlow, LiveData)` mantiene `valores reactivos en la UI`.

🔹 `Diferencia` con `ViewModel`:
• Un `Holder Observable solo gestiona estado en la UI` (no persiste datos después de un cambio de configuración).
• Un `ViewModel` `persiste datos y maneja lógica de negocio.`

🔹 Ejemplo de `MutableState` dentro de una `Composable (sin ViewModel)`
```kotlin
@Composable
fun CounterScreen() {
    var counter by remember { mutableStateOf(0) }

    Column {
        Text("Contador: $counter")
        Button(onClick = { counter++ }) {
            Text("Incrementar")
        }
    }
}
```

✅ Aquí `counter` es `reactivo`: cada vez que cambia, la `UI se actualiza automáticamente`.
⚠️ Pero si `rotamos la pantalla`, el `contador` se `reinicia` porque `no hay ViewModel`.

#### 3️⃣ `Corrutinas`: Ejecución Asíncrona en Segundo Plano

🔹 Propósito:
Las `corrutinas` permiten realizar `operaciones asíncronas sin bloquear el hilo principal`.
• Perfectas para `llamadas a API`, base de `datos o tareas largas`.
• Se integran dentro de `ViewModel` con `viewModelScope.launch { }`.

🔹 Ejemplo de uso en `ViewModel`:
```kotlin
class QuoteViewModel(private val getQuotesUseCase: GetQuotesUseCase) : ViewModel() {

    private val _quotes = MutableStateFlow<List<Quote>>(emptyList())
    val quotes: StateFlow<List<Quote>> = _quotes

    fun loadQuotes(numElements: Int) {
        viewModelScope.launch(Dispatchers.IO) { // Llamada en segundo plano
            val result = getQuotesUseCase(numElements)
            _quotes.value = result // Actualizar UI
        }
    }
}
```

✅ La API se llama en Dispatchers.IO para no bloquear la UI.
✅ La UI se actualiza cuando _quotes cambia.

#### 📌 Diferencias clave entre ViewModel, Holder Observables y Corrutinas

| Concepto | Función | Reactivo | Asíncrono | Dónde usarlo |
|----------|---------|----------|-----------|--------------|
| **ViewModel** | Persiste datos y maneja lógica de negocio | ✅ Sí (con `StateFlow` o `LiveData`) | 🚫 No (pero puede usar corrutinas) | Lógica de la pantalla |
| **Holder Observables** (`State`, `MutableState`) | Estado reactivo dentro de la UI | ✅ Sí | 🚫 No | Dentro de una `@Composable` |
| **Corrutinas** | Tareas en segundo plano | 🚫 No (por sí solas) | ✅ Sí | Llamadas a API, BD |

📌 Conclusión
1️⃣ Para hacer la UI reactiva: usa ViewModel con StateFlow o LiveData.
2️⃣ Si los datos son locales a la pantalla: usa MutableState dentro de @Composable.
3️⃣ Para hacer llamadas asíncronas: usa viewModelScope.launch { } con corrutinas.

🚀 Uniendo todo, obtienes una UI reactiva, persistente y eficiente.

### 12. DUDA (NUEVA)
```kotlin
fun loadQuotes(numElements: Int) {
    viewModelScope.launch(Dispatchers.IO) { // Llamada en segundo plano
        val result = getQuotesUseCase(numElements) // función suspend
        _quotes.value = result // Actualizar UI
    }
}
```

Mis dudas son en que función debe de poner el `suspend` en la del dao (del data), en la que se pasa en el repositorio o en al que se pasa al caso de uso o en todas. Y luego en cual hay que poner el contexto `viewModelScope.launch(Dispatchers.IO)` en el ViewModel donde se llama la función, el componente (vista) donde se llama la función o en ambos.

EXPLICACIÓN MIA:
Basicamente para llamar a las funciones `suspend` en las `vistas` voy a tener que abrir un contructor (`launch` o `async`) de una corrutina (con su contexto (`scope`, adecuado)) para poder llamar a la funcion: (ESTO EN EL VIEW MODEL)
```kotlin
viewModelScope.launch(Dispatcher.Main) { // si no se pone nada por defecto en el hilo principal (UI). Falta ver cual es el 'scope'.
     performCpuTask() // funcion suspend (tengra el contexto incluido). Declarar en el View Model

    cpuResult = context.getString(R.string.cpu)
    isCpuLoading = true
}
```

Y en el el `View Model` se declarar la `función suspend` con el `contexto ya incluido`: (LO MISMO ESTO SERIA EN EL REPOSITORIO)
```kotlin
private suspend performCpuTask(): Int = withContext(Dispatcher.Default){ // función en el contexto del hilo secundario de Default
    var result = 0
    for(i in 1..1_000_000){
        result += i
    }
    result
}
```

Mis dudas es como se declara en los `casos de uso y mas abajo` si solo con la `funcion como suspend` o añadiendole el contexto, etc.

Y tambien que `Scope` tendre que usar. El `viewModelScope` usa por defecto el `Dispatcher.Main`.

¿`viewModelScope` en la `vista` o en el `View Model`? Y que queda la vista aun mas limpia: (ESOT YA EN LA VISTA)

```kotlin
@Composable
fun MiPantalla(viewModel: MiViewModel = viewModel()) {
    Button(onClick = { viewModel.realizarTareaLarga() }) {
        Text("Ejecutar tarea")
    }
}
```

### DUDA A CHAT GPT
Mi duda escomo aplicar corrutinas correctamente en mi App, el cual quiero tener una estructura que data -> domain -> presentation con la arquitectura MVVM, la cuestion es:
Por ejemplo:
- En data la entidad Character y la interfaz CharacterDao, la cual integra varias funciones para realizar a una API y CharacterDBDao con varias funciones para una BD. Y luego tengo la carpeta impl que tiene implementaciones de esta interfaces.

```kotlin
interface CharacterDatabaseDao {
    fun getAllCharactersDb(): List<Character> // Obtener todos los personajes de la base de datos
    ...
}

class CharacterDatabaseDaoRoom: CharacterDatabaseDao {
    override fun getAllCharactersDb(): List<Character> {
        TODO("Not yet implemented")
    }
    ...
}
```

¿Como lo tendria que definir como?
```kotlin
 suspend  fun getAllCharactersDb(): List<Character> 

    override suspend fun getAllCharactersDb(): List<Character> {
        TODO("Not yet implemented")
    }
```

- En  domain tengo la entity correspondiente de Character y un repository CharaterRepository  y CharaterRepositoryImpl, ¿esto seria asi?
```kotlin
interface CharaterRepository {
    // Casos de uso de la datos obtenido de la fuente principal
    suspend fun getAllCharacters(): List<Character>
    suspend fun getCharactersByName(name: String): List<Character> // Nueva: filtrar los personajes por nombre
...
}

class CharaterRepositoryImpl(val dao: CharacterDao, val databaseDao: CharacterDatabaseDao): CharaterRepository {

    override suspend fun getAllCharacters(): List<Character>{
        // 🚀 1️⃣ Obtener todos los personajes del JSON/API y mapearlos a la entidad Character
        val allCharactersDto = dao.getAllCharacters()
        val allCharacters = allCharactersDto.map { it.toCharacter() }

        // 🚀 2️⃣ Obtener los personajes favoritos de la BD y convertirlos en un Map para acceso rápido
        val favoriteCharactersMap = databaseDao.getAllCharactersDb().associateBy { it.id }

        // 🚀 3️⃣ Fusionar datos del JSON con la BD (si el personaje está en la BD, tomar esFavorito de ahí)
        return allCharacters.map { character ->
            val characterDb = favoriteCharactersMap[character.id] // Buscar personaje en la BD
            character.copy(
                esFavorito = characterDb?.esFavorito == true // Si está en la BD, usar su estado real
            )
        }
    }
}
```

Ademas tengo los caoss d euso con su interfaces y implementaciones igualmente:¿Serian asi?
```kotlin
interface FetchAllCharactersDbUseCase {
    suspend fun execute(): List<Character>
}

class FetchAllCharactersDbUseCaseImpl(val repository: CharaterRepository): FetchAllCharactersDbUseCase {
    override /*suspend*/ fun execute(): List<Character> = withContext(Dispatcher.IO){
        TODO("Not yet implemented")
    }
}
```

- Luego estarria la carpeta presenter con los viewmodes y views (composables):
Estaria el CharactersStateUI: data class CharactersStateUI (val chararacters:List<Character> = emptyList())
El ListCharacterApplication:
```kotlin
class ListCharacterApplication: Application() {

    // La implementacion en producción de Impl.
    private val characterDao: CharacterDao = CharacterDaoJson("mi_ruta_archivo_json")
    private val characterDatabaseDao: CharacterDatabaseDao = CharacterDatabaseDaoRoom()
    private val characterRepository: CharaterRepository = CharaterRepositoryImpl(characterDao, characterDatabaseDao)

    val getAllCharacters: GetAllCharactersUseCase = GetAllCharactersUseCaseImpl(characterRepository)
    val getCharactersByName: GetFilterNameCharactersUseCase = GetFilterNameCharactersUseCaseImpl(characterRepository)
    val getAllCharactersDb: FetchAllCharactersDbUseCase = FetchAllCharactersDbUseCaseImpl(characterRepository)
    val insertCharacterDb: InsertCharacterDbUseCase = InsertCharacterDbUseCaseImpl(characterRepository)
    val deleteCharacterDb: UpdateCharacterDbUseCase = UpdateCharacterDbUseCaseImpl(characterRepository)

    override fun onCreate(){
        super.onCreate()
    }
}
```

El view model:
```kotlin
class CharacterViewModel(val getAllCharacters: GetAllCharactersUseCase): ViewModel() {
    private val _characters: MutableStateFlow<CharactersStateUI> = MutableStateFlow(CharactersStateUI()) // Asincrono esta en un hilo secundario
    val characters: StateFlow<CharactersStateUI> = _characters.asStateFlow()

    // Hay que llamar a los casos de uso
    fun getAllCharacters(){
        val charactersList = getAllCharacters.execute() // recibimos la lista de los contactos

        // it es 'state.value' que es el valor actual de los contactos y lo actualizamos a 'contactsList'
        _characters.update {
            it.copy( charactersList )
        }
    }

    companion object {
        fun factory(): Factory = object : Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                // Accedemos al objeto Aplication con la implementacion (unica que tenemos)
                // A través de extras se puede acceder al Aplication (al igual que se podia al Bundle).
                val application = extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as ListCharacterApplication

                // Puedo acceder a través de 'application' al casos de uso que le pasamos por parámetro.
                return CharacterViewModel (application.getAllCharacters) as T
            }
        }
    }
}
```

Y la pantalla del listado de personajes:
```kotlin
@Composable
fun CharactersScreen(   navigateToFilterCharacters: () -> Unit
) {

    val viewModel: CharacterViewModel = viewModel(factory = CharacterViewModel.factory())
    val characters: State<CharactersStateUI> = viewModel.characters.collectAsState() // sincrono para manejarlo en la UI

    LazyColumn() {
                    items(items = characters){ character ->
                        Text(character.name)
                    }
                }
...
}
```

### TAREAS
HACER QUE REOCGA EL NOMBRE Y LA FOTO EL OTRO JSON Y LAS JUNTE EN LA ENTIDAD. ✅
HACER LOS JSON DE LOS EPISODIOS. ✅
VIDEOS VIDEOS VIDEOS DE EJEMPLOS. ❌
PASAR A LA VISUALIZACIONES. ✅

- VER LOGGER ✅
- VER PASO DE IDA NAVEGACION DEL EPISODE ✅
- VER HILT (inyeccion automatica) ✅
- VER PAGINACIÓN (Paging 3, carga diferida) ⚠️
- VER RETROFIT (llamada a API'S) ✅
- VER BD CON ROOMS ✅
- VER ANIMACIONES PARA EL JUEGO Y SPINNER DEL JUEGO GIRATORIO ⚠️

#### ✅ Cómo usar Hilt para inyectar dependencias en tu ViewModel

Hilt es la solución recomendada para manejar dependencias en Android. Te permitirá evitar la creación manual de objetos y simplificar tu código.

1️⃣ Agregar Hilt al proyecto
En el build.gradle (Project), añade el classpath de Hilt:
```grandle
dependencies {
    classpath "com.google.dagger:hilt-android-gradle-plugin:2.50"
}
```

Luego, en el build.gradle (Module), agrega las dependencias necesarias:
```grandle
plugins {
    id 'com.android.application'
    id 'kotlin-kapt'
    id 'dagger.hilt.android.plugin' // 🔥 Agregar Hilt Plugin
}

dependencies {
    implementation "com.google.dagger:hilt-android:2.50"
    kapt "com.google.dagger:hilt-android-compiler:2.50"
}
```

Y sincroniza el proyecto.

2️⃣ Configurar la Application con Hilt
Modifica tu TheSimpsonPlaceApp para que sea reconocida por Hilt:
```kotlin
@HiltAndroidApp
class TheSimpsonPlaceApp : Application()
```

3️⃣ Crear el Módulo de Hilt para las Dependencias

Crea una nueva clase llamada `AppModule.kt` donde definiremos las dependencias:
```kotlin
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideGetAllCharactersUseCase(): GetAllCharactersUseCase {
        return GetAllCharactersUseCase(/* Inyectar repositorio aquí */)
    }
}
```

✅ Ahora `Hilt` sabrá cómo crear `GetAllCharactersUseCase` y lo mantendrá en memoria como un `Singleton`.

4️⃣ Modificar el ViewModel para usar Hilt
En `ListCharactersViewModel`, cambia su `constructor` para que `Hilt` lo maneje:

```kotlin
@HiltViewModel
class ListCharactersViewModel @Inject constructor(
    private val getAllCharacters: GetAllCharactersUseCase
) : ViewModel() {

    private val _stateCharacter: MutableStateFlow<ListCharactersStateUI> = MutableStateFlow(ListCharactersStateUI())
    val stateCharacter: StateFlow<ListCharactersStateUI> = _stateCharacter.asStateFlow()

    fun getAllCharacters() {
        viewModelScope.launch {
            val charactersList = getAllCharacters.execute()
            _stateCharacter.update {
                it.copy(charactersList)
            }
        }
    }
}
```

💡 Nota:
• Se agrega @HiltViewModel para que Hilt lo reconozca.
• Se usa @Inject en el constructor, para que Hilt lo cree automáticamente.


5️⃣ Inyectar el ViewModel en el Composable
En tu Composable, usa hiltViewModel() para obtener el ViewModel:
```kotlin
@Composable
fun CharactersScreen(viewModel: ListCharactersViewModel = hiltViewModel()) {
    val state by viewModel.stateCharacter.collectAsState()

    LazyColumn {
        items(state.charactersList) { character ->
            Text(character.name)
        }
    }
}
```

✅ ¡Listo! Ahora el ViewModel se inyecta automáticamente sin necesidad de una factory() manual.

🔹 Resumen
	1.	Agregar Hilt al build.gradle
	2.	Anotar @HiltAndroidApp en la Application
	3.	Crear un @Module con @Provides para las dependencias
	4.	Anotar @HiltViewModel en el ViewModel y usar @Inject en su constructor
	5.	Usar hiltViewModel() en los Composables para obtener el ViewModel automáticamente

🚀 Ventajas de Hilt:
✅ No necesitas una factory() para cada ViewModel
✅ Maneja las dependencias automáticamente
✅ Mejora la escalabilidad del código
✅ Evita el uso manual de Application para gestionar dependencias

Si tienes dudas o errores al implementarlo, dime y te ayudo. 😉

#### ✅ VER PAGINACIÓN (Paging 3, carga diferida)
¿Mi app se podria mejorar en vez de llamando a todos los personajes o episodios de sus json usandolo con paging 3?

Sí, Paging 3 mejoraría el rendimiento de tu app, ya que actualmente estás cargando todos los personajes o episodios de una sola vez, lo que podría generar problemas de rendimiento y consumo de memoria, especialmente si el JSON es grande.

🚀 Beneficios de usar Paging 3

✔ Carga datos en páginas en lugar de cargar todo el JSON de golpe.
✔ Optimiza memoria y rendimiento al evitar mantener todos los elementos en RAM.
✔ Automatiza el paginado sin que tengas que gestionar la paginación manualmente.
✔ Soporte para Jetpack Compose y coroutines, ideal para tu arquitectura MVVM.

⸻

🔹 Cómo implementar Paging 3 en tu app

Te explico cómo adaptar tu app para cargar episodios de manera paginada con Paging 3.

1️⃣ Agregar dependencias de Paging 3
En build.gradle (Module: app), agrega:
```gladle
dependencies {
    implementation "androidx.paging:paging-runtime-ktx:3.2.1"
    implementation "androidx.paging:paging-compose:3.2.1" // Para Jetpack Compose
}
```

2️⃣ Crear PagingSource para cargar episodios
Debes crear una clase que extienda PagingSource<Int, EpisodeDto>, que se encargará de cargar episodios por páginas desde el JSON.
```kotlin
class EpisodePagingSource(
    private val context: Context,
    private val fileName: String
) : PagingSource<Int, EpisodeDto>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, EpisodeDto> {
        try {
            val page = params.key ?: 1 // Página actual, si es null, empieza en 1
            val pageSize = params.loadSize // Tamaño de la página

            // Cargar JSON completo (esto se optimiza si viene de una API)
            val jsonFormat = Json { ignoreUnknownKeys = true }
            val json = context.assets.open(fileName).bufferedReader().use { it.readText() }
            val episodesDto = jsonFormat.decodeFromString<EpisodesDto>(json)

            val episodes = episodesDto.episodios?.map { it.toEpisode() } ?: emptyList()

            // Calcular los índices de paginación
            val fromIndex = (page - 1) * pageSize
            val toIndex = minOf(fromIndex + pageSize, episodes.size)

            if (fromIndex >= episodes.size) return LoadResult.Page(emptyList(), null, null)

            return LoadResult.Page(
                data = episodes.subList(fromIndex, toIndex),
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (toIndex < episodes.size) page + 1 else null
            )

        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, EpisodeDto>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}
```

3️⃣ Crear EpisodeRepository para manejar paginación
```kotlin
class EpisodeRepository(private val context: Context) {
    fun getPagedEpisodes(fileName: String): Pager<Int, EpisodeDto> {
        return Pager(
            config = PagingConfig(
                pageSize = 10, // Tamaño de cada página
                enablePlaceholders = false
            ),
            pagingSourceFactory = { EpisodePagingSource(context, fileName) }
        )
    }
}
```

4️⃣ Modificar ListEpisodesViewModel para usar Paging 3
```kotlin
class ListEpisodesViewModel(private val repository: EpisodeRepository) : ViewModel() {
    val episodesFlow = repository.getPagedEpisodes("episodios_test.json")
        .flow
        .cachedIn(viewModelScope) // Optimiza la caché para recomposiciones
}
```

5️⃣ Mostrar los episodios en LazyColumn con collectAsLazyPagingItems
```kotlin
@Composable
fun EpisodesScreen(navigateToFilterEpisode: () -> Unit,
                   navigateToFavoriteEpisode: () -> Unit,
                   onEpisodeSelected: (Int) -> Unit,
                   navigationArrowBack: () -> Unit) {

    val viewModel: ListEpisodesViewModel = viewModel()
    val episodes = viewModel.episodesFlow.collectAsLazyPagingItems()

    LazyColumn {
        items(episodes) { episode ->
            episode?.let {
                Text(text = it.titulo)
            }
        }

        episodes.apply {
            when {
                loadState.refresh is LoadState.Loading -> {
                    item { CircularProgressIndicator() }
                }
                loadState.append is LoadState.Loading -> {
                    item { CircularProgressIndicator() }
                }
                loadState.refresh is LoadState.Error -> {
                    item { Text("Error al cargar los episodios") }
                }
            }
        }
    }
}
```

🚀 Resultado Final
Ahora, los episodios se cargarán de manera paginada en vez de traer todo el JSON de golpe. Esto hará que tu app sea más eficiente, rápida y escalable. 🎯

#### SEGUIMOS
...

- REALIZAR EL `HILT` PARA EL `TESTING` CON LOS `DATOS DE TEST` ⚠️
- HACER BIEN LA COMPROBACIÓN DE LOS `LOGGER` ⚠️
- ERRORES SON LAS DEPENDENCIAS QUE NO ME FUNCIONA EN KPT Y EL IDIOMA DEL DATASTORE POR DEFECTO ❌

- EMPEZAR VIDEO DE: `https://www.youtube.com/watch?v=lX7CeooKCcQ&t=13959s`

# EXPLICACIÓN DE INYECCIÓN DE DEPENDENCIAS (`HILT`) --> MIA

-  Cuando la implementación de mi dao, repositorio, casos de usos... no incluye `@Inject constructor()`, por ejemplo `CharacterDaoJson.kt`.
```kotlin
    class CharacterDaoJson (@ApplicationContext private val context: Context,
                            @Named("dataJson") private val dataJson: String,
                            @Named("imageJson") private val imagJson: String
                        ): CharacterDao {

        override suspend fun getAllCharacters(): List<CharacterDto> {
            try {
                // Abrir el archivo JSON desde los assets
                //...
            } catch (e: Exception) {
                // ...
            }
        }

        override suspend fun getCharactersByName(name: String): List<CharacterDto> {
        // ...
        }
    }
```

- Al no usar en esa implementación `@Inject constructor`, en mi `AppModule.kt` lo tengo que definir con `@Provides` para asignar dicha implementación manualmente al `CharacterDao`.
```kotlin
    @Provides // Añadir, al no usar '@Inject constructor()' en 'CharacterDaoJson', para saber que implementación usar automaticamente
    @Singleton
    fun provideCharacterDao(@ApplicationContext context: Context): CharacterDao {
        val test = false

        return if (test) {
            CharacterDaoJson(context, "personajes_test.json", "imagenes_test.json")
        } else {
            CharacterDaoJson(context, "personajes_data.json", "imagenes_data.json")
        }
    }
```

- Esto seria util si quiero `cambiar de implementación de los datos de forma sencilla` (de jsons, apis, db...). Se lo he aplicado a todos `los Dao` (tanto de personajes, episodio y citas) tanto de llamadas de json, api, o BD en Room (con su propio fichero esta ultima `DatabaseModule.kt`).

- Luego para los `repositorios` y `caso de uso` lo que quiero es que `siempre sean los mismo` por lo tanto les pongo el `@Inject constructor` para que automaticamente asigne dicho repositorio a su interfaz. Ejemplo de repositorio y caso de uso.
```kotlin
    class CharaterRepositoryImpl @Inject constructor (val dao: CharacterDao,
                                                    val databaseDao: CharacterDatabaseDao): CharaterRepository {

        override suspend fun getAllCharacters(): List<Character> {
            return withContext(Dispatchers.IO) {

                val allCharactersDto = dao.getAllCharacters()
                val allCharacters = allCharactersDto.map { it.toCharacter() }
                // ...
            }
        }
    }

    class GetAllCharactersUseCaseImpl @Inject constructor( private val repository: CharaterRepository ): GetAllCharactersUseCase {
        override suspend fun execute(): List<Character> {
            return repository.getAllCharacters()
        }
    }
```

- Y luego tengo que conectar a traves de un `@Binds` dicha implementación con su interfaz. Solo para los que se implementan de forma automatica, ultimos vistos con `@Inject constructor` ya que los otros usan `@Provides` que por defecto tienen `@Binds`. Lo añado en un clase abstracta `DomainModule.kt`.

```kotlin
    @Module
    @InstallIn(SingletonComponent::class)
    abstract class DomainModule {
        @Binds
        @Singleton
        abstract fun bindCharacterRepository(impl: CharaterRepositoryImpl): CharaterRepository

        @Binds
        abstract fun bindGetAllCharactersUseCase( useCaseImpl: GetAllCharactersUseCaseImpl ): GetAllCharactersUseCase

        // ...
    }
:...
```

- [DUDA]
📌 ¿Cuándo usar `@Singleton`?
Regla general: Usa `@Singleton` en aquellas clases que deban tener una única instancia en toda la aplicación.

📌 En tu caso:
1.	`DAO (CharacterDao)` -> ✅ @Singleton
•	Debe haber una única instancia de `CharacterDaoJson` o cualquier otro `DAO` para que no se creen múltiples accesos a los archivos `JSON` o la `BD`.
2.	`Repositorios (CharacterRepository)` -> ✅ @Singleton
•	Queremos una única instancia de `CharacterRepositoryImpl` para que las consultas a la `API/BD` sean centralizadas y no se creen múltiples instancias innecesarias.
3.	`Casos de uso (UseCase)` -> ❌ No poner `@Singleton`
•	No es necesario que los casos de uso sean singleton porque el `ViewModel` solo los usa mientras está en memoria. Hilt los manejará automáticamente sin duplicaciones.
4.	`ViewModels` -> ❌ No poner `@Singleton`
•	Los ViewModels ya tienen su propio ciclo de vida, y Hilt los maneja con `@HiltViewModel`, por lo que no deben ser singleton.
📌 Explicación:
•	`@Singleton` en el repositorio porque queremos que haya una única instancia durante toda la app.
•	Sin `@Singleton` en los casos de uso, porque no es necesario y `Hilt` ya gestiona su ciclo de vida correctamente.

✅ Resumen final
1.	Usa `@Provides` cuando la clase no tiene `@Inject constructor`, como `CharacterDaoJson`.
2.	Usa `@Binds` cuando quieres enlazar una implementación (`CharaterRepositoryImpl`) con su interfaz (`CharaterRepository`).
3.	Pon `@Singleton` en `DAOs` y `Repositorios`, pero NO en casos de uso ni `ViewModels`.

# EXPLICACIÓN LLAMADA A API (`RETROFIT`) --> MIA
- El `QuoteDto` se caracteriza por tener los atributos como `@SerializedName` y no como `@SerialName`.
- El `QuoteDao.kt` es la interfaz, hay que añadir el `método de la operación` (@Get) y sus posibles `querys`.
- No se necesita `QuoteDaoApi.kt` por que la implementación con la APi se hace atraves del fichero `NetworkModule.kt`.
- El `NetworkModule.kt` define `los providers` de `Retrofit` y de mi `Api` en concreto como si fiese el `QuoteDaoApi.kt`.
- En el `AppModule.kt` borramos el provider para el `QuoteDaoApi.kt` (que ya hemos borrado su implementacón por `NetworkModule.kt`).

# Diferencia entre usar `LiveData` y `StateFlow`. ¿Cuando usar cada cual? --> CHAT GPT
📌 ¿Cuándo usar LiveData y cuándo usar StateFlow?

✅ Usar LiveData cuando:
	1.	El estado depende del ciclo de vida de la UI
	•	LiveData se detiene cuando el LifecycleOwner (ejemplo: Activity o Fragment) está en onStop(), y se reinicia en onStart().
	2.	Ya tienes un código basado en LiveData
	•	Si estás migrando una app antigua con LiveData, usarlo puede hacer la transición más sencilla.
	3.	Estás en un ViewModel que usa LiveData en XML (DataBinding)
	•	LiveData es compatible con DataBinding y se puede conectar directamente a los elementos de la UI.

⸻

✅ Usar StateFlow cuando:
	1.	Estás trabajando con Jetpack Compose
	•	StateFlow se integra mejor con collectAsState(), eliminando la dependencia de LifecycleOwner.
	2.	Necesitas manejar estados de UI de forma reactiva
	•	Como en una pantalla de login donde el email y la contraseña están cambiando constantemente.
	3.	Quieres usar Flow en la arquitectura
	•	StateFlow permite usar operadores como map, filter, combine para procesar datos de manera eficiente.

⸻

📌 Aplicación en tu código

Tienes un ViewModel con:
	•	_email como StateFlow
	•	_password como LiveData

🔹 Problema: Mezcla de StateFlow y LiveData

No es un error, pero sería más consistente usar uno solo. Para Jetpack Compose, es mejor usar StateFlow.

🔹 Opción 1: Convertir todo a StateFlow (recomendado para Compose)

```kotlin
class LoginViewModel : ViewModel() {

    private val _email = MutableStateFlow("")
    val email: StateFlow<String> = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password.asStateFlow()

    fun updateEmail(newEmail: String) {
        _email.value = newEmail
    }

    fun updatePassword(newPassword: String) {
        _password.value = newPassword
    }
}

// En Compose:
@Composable
fun Login(viewModel: LoginViewModel) {
    val email by viewModel.email.collectAsState()
    val password by viewModel.password.collectAsState()

    TextField(value = email, onValueChange = { viewModel.updateEmail(it) })
    TextField(value = password, onValueChange = { viewModel.updatePassword(it) })
}
```

✅ Ventajas:
	•	Usa StateFlow, que es más eficiente en Compose.
	•	Evita la necesidad de observeAsState().
	•	Funciona bien con remember.

⸻

🔹 Opción 2: Mantener LiveData (si tu proyecto ya usa LiveData)

Si por alguna razón necesitas mantener LiveData, podrías hacer:
```kotlin
class LoginViewModel : ViewModel() {

    private val _email = MutableLiveData("")
    val email: LiveData<String> = _email

    private val _password = MutableLiveData("")
    val password: LiveData<String> = _password

    fun updateEmail(newEmail: String) {
        _email.value = newEmail
    }

    fun updatePassword(newPassword: String) {
        _password.value = newPassword
    }
}

// En Compose:
@Composable
fun Login(viewModel: LoginViewModel) {
    val email by viewModel.email.observeAsState("")
    val password by viewModel.password.observeAsState("")

    TextField(value = email, onValueChange = { viewModel.updateEmail(it) })
    TextField(value = password, onValueChange = { viewModel.updatePassword(it) })
}
```

❌ Desventajas:
	•	LiveData no está diseñado para Compose.
	•	No es tan eficiente como StateFlow en términos de asincronía.

⸻

📌 Conclusión

| Si usas Jetpack Compose → ✅ Usa StateFlow
| Si usas XML + ViewModel → ✅ Usa LiveData

Tu caso es Jetpack Compose, así que es mejor cambiar todo a StateFlow. 🚀

# ¿`Funciones suspend` en el `View Model` o antes (casos de uso, repositorio, dao...)? --> CHAT GPT
En general, no se recomienda tener funciones suspend dentro del ViewModel, a menos que sean necesarias. La mejor práctica en la arquitectura MVVM es delegar la lógica suspendida a los casos de uso (UseCases) o repositorios, y llamar a esas funciones suspendidas desde viewModelScope.launch.

⸻

📌 ¿Por qué evitar suspend en el ViewModel?
	1.	El ViewModel no es responsable de manejar la asincronía
	•	Su responsabilidad es preparar y exponer los datos a la UI.
	•	No debe manejar la lógica de negocio directamente.
	2.	Dificulta el testeo unitario
	•	suspend fun dentro del ViewModel requiere usar runBlockingTest o MainDispatcherRule para testear, lo que lo hace más complicado.
	•	Si delegamos a un UseCase, podemos testear más fácilmente sin depender del ViewModel.
	3.	Mejor separación de responsabilidades
	•	ViewModel → Se encarga de exponer datos a la UI.
	•	UseCase → Se encarga de la lógica de negocio.
	•	Repository → Se encarga de acceder a los datos de API o BD.

⸻

📌 Comparación de enfoques

🚫 Ejemplo INCORRECTO (Lógica suspendida en el ViewModel)

```kotlin
class LoginViewModel : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    suspend fun onLoginSelected() {
        _isLoading.value = true
        delay(4000)  // ❌ Manejo de asincronía dentro del ViewModel (no recomendado)
        _isLoading.value = false
    }
}
```

❌ Problemas:
	•	suspend en el ViewModel no permite llamarla directamente desde la UI sin usar viewModelScope.launch.
	•	Dificulta la reutilización y testeo.

⸻

✅ Ejemplo CORRECTO (Manejo de asincronía con viewModelScope.launch)
```kotlin
class LoginViewModel(private val loginUseCase: LoginUseCase) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun onLoginSelected() {
        viewModelScope.launch {
            _isLoading.value = true
            loginUseCase.execute()  // ✅ Llamamos al caso de uso suspendido
            _isLoading.value = false
        }
    }
}
```

Y en el UseCase (LoginUseCase):
```kotlin
class LoginUseCase(private val repository: UserRepository) {
    suspend fun execute() {
        delay(4000)  // Simulación de llamada de red
        repository.login()
    }
}
```

✅ Ventajas:
	•	ViewModel no maneja directamente la asincronía.
	•	UseCase encapsula la lógica suspendida.
	•	Testeo más fácil, porque podemos mockear LoginUseCase.

⸻

📌 ¿Cómo aplicar esto en tu código de Los Simpsons?

Tú ya lo tienes bien estructurado en tu app de Los Simpsons, donde:
	•	El ViewModel usa viewModelScope.launch
	•	El UseCase maneja la lógica suspendida

Ejemplo de tu código:
```kotlin
@HiltViewModel
class ListCharactersViewModel @Inject constructor(
    private val getAllCharacters: GetAllCharactersUseCase
) : ViewModel() {

    private val _stateCharacter = MutableStateFlow(ListCharactersStateUI())
    val stateCharacter: StateFlow<ListCharactersStateUI> = _stateCharacter.asStateFlow()

    fun getAllCharacters() {
        viewModelScope.launch {
            val charactersList = getAllCharacters.execute() // ✅ Llamada al caso de uso
            _stateCharacter.update { it.copy(charactersList) }
        }
    }
}
```

Y el UseCase:
```kotlin
class GetAllCharactersUseCase @Inject constructor(private val repository: CharacterRepository) {
    suspend fun execute(): List<Character> {
        return repository.getAllCharacters()  // ✅ Aquí ocurre la llamada suspendida
    }
}
```

✅ Ventajas de esta estructura:
	•	ViewModel solo se encarga de gestionar el estado.
	•	UseCase maneja la lógica de negocio suspendida.
	•	Repository accede a los datos reales (API/BD).
	•	Más modular y fácil de testear.

⸻

📌 Conclusión
	•	❌ suspend fun dentro del ViewModel → No recomendado.
	•	✅ viewModelScope.launch en el ViewModel y suspend en el UseCase → Mejor enfoque.
	•	Tu app de Los Simpsons ya sigue la mejor práctica. 🚀

Si en algún momento necesitas un suspend fun, agrégalo en el UseCase o el Repository, no en el ViewModel.

# Añadido `DataStore Preferences`
La base de datos (BD) en el data source generalmente se refiere a la capa donde almacenas y recuperas datos persistentes, como Room Database o DataStore en este caso.

En tu implementación, estás usando `DataStore Preferences`, que es ideal para almacenar pequeñas configuraciones como `estadísticas de juego y preferencias de usuario`. La estructura típica sería:
1.	`DataSource`: Se encarga de acceder a DataStore y gestionar los datos.
2.	`Repository`: Interactúa con el DataSource y proporciona datos a la capa de dominio.
3.	`ViewModel`: Recupera los datos del repositorio y los expone a la UI.

NO ME FUNCIONA PARA EL LENGUAJE DE FORMA AUTOMATICA ME TENGO QUE SALIR DEL ACTIVITY Y VOLVER A ENTRAR PARA QUE SE APLIQUE EL LENGUAJE ❌

# Añadido `Room`
- Entities: creamos las entidades (tablas) para la BD (las columnas para la BD) --> `CharacterDb.kt`, `EpisodeDb.kt`, `QuoteDb.kt`.
- Implementacion directa de los Daos (la propia interfaz es la iomplementación) --> `CharacterDatabaseDaoRoom.kt`, `EpisodeDatabaseDaoRoom.kt`, `QuoteDatabaseDaoRoom.kt`
- Mapeo de las entidades de la aplicacion (domain) a las de la base de datos (las entidades (tablas)) y viceversa --> `CharacterMapper.kt`, `EpisodeMapper.kt`, `QuoteMapper.kt`
- `TheSimpsonsDatabaseRoom.kt`: definimos la base de datos con las tablas (entidades) y los daos.
- Repositorio: con sus interfaces `CharaterRepository.kt`, `EpisodeRepository.kt`, `EpisodeRepository.kt` (que cuentan con las operaciones del json/api y de la base de datos) y sus implementaciones unicas `CharaterRepositoryImpl.kt`, `EpisodeRepositoryImpl.kt`, `EpisodeRepositoryImpl.kt`
- Casos de usos: todos interfaces e implementaciones de las funciones de forma individual como casos de uso. Tanto del json/api como base de datos.
- di: `DatabaseModule.kt` añado la inyección de dependencias de los daos de la base de datos. `DomainModule.kt` añado la conexión de la interfaz de los repositorios y los casos de uso con su implementaciones por defecto para tambien la inyección de dependencias.
- viewmodel: ...
- screens: ...

* Conversión de los datos fundamentales para las entidades de la BD a los datos que de verdad quiero utilizar para la aplicación.
Es la clase `Converters` definida dentro del fichero `TheSimpsonsDatabaseRoom.kt`.
La anotación `@TypeConverters(Converters::class)` le dice a Room que use la clase `Converters` para convertir tipos de datos que `no son compatibles de manera nativa`, como `List<String>`, en un formato que sí puede almacenar en la base de datos (por ejemplo, String en formato JSON).

¿Por qué es necesario?
Room no sabe cómo guardar una `List<String>`, `URL`, `Date`, ... en la base de datos porque `SQLite` solo soporta tipos básicos como `String`, `Int`, `Boolean`, etc.
Por lo tanto, necesitamos un `TypeConverter`, por ejemplo para en `List<String>`:
1. Convierta una `List<String>` en un `String` (para guardarla en `SQLite`).
2. Convierta un `String` de vuelta a una `List<String>` (cuando lo leamos desde la BD).

* Ejemplo de migración de BD para añadir nuevas tablas `episodes`y `quotes` a la base de datos que solo contenia `characters`.
    ✨ Pasos para la migración en Room
    1. Aumenta la versión de la base de datos (de version = 1 a version = 2). En 'TheSimpsonsDatabaseRoom'.
    2. Crea la migración Migration(1,2) para añadir las nuevas tablas. Variable 'MIGRATION_1_2', en este fichero.
    3. Registra la migración en Room.databaseBuilder.  Propiedad 'addMigrations' en este fichero (función 'initDatabase').

# AÑADIR DE LA PANTALLA DE `DETALLES EPISODIOS` A NAVEGAR A OTRAS DEMÁS PANTALLAS (EN FILTRO QUE SE QUEDE LOS DATOS FILTRADO)

¿Qué es SavedStateHandle?
Es un mecanismo que permite guardar y restaurar estado automáticamente en el ViewModel, incluso cuando el proceso se destruye y vuelve a crearse (por ejemplo, al rotar pantalla o volver atrás desde otra pantalla). Muy útil en Compose.

Guardar en SavedStateHandle estos valores:
• filterTitle
• filterMinDate
• filterMaxDate
• filterSeason
• filterEpisode
• isView
• isOrder

Con esto, los filtros se persistirán automáticamente en el SavedStateHandle. Cuando vuelvas desde la pantalla de detalles, tu ViewModel se restaurará con los mismos filtros sin necesidad de reconstruirlos desde cero. Esto preserva la experiencia del usuario y mejora el rendimiento.

🎯 Objetivo:

Mantener el filtro aplicado al volver atrás desde la pantalla de detalles y que se aplique automáticamente al regresar, sin necesidad de que el usuario toque nada.
⸻

🧠 ¿Por qué no se mantiene el filtro ahora?
Porque estás usando un var episodeFilter by remember { ... }, que solo guarda el estado mientras Composable esté en memoria.
Cuando vas a la pantalla de detalles y vuelves, Compose puede recomponer desde cero y el estado inicial de episodeFilter vuelve a ser el valor por defecto: EpisodeFilter().


Quiero la linea que te diga por donde vas en el listado. O ver como ahcer para que empiece por el principio del listado.
Quiero un boton que borre y deje por defecto todo el listado.

....




# Explicación `Build Types` y `Flavors` --> CHAT GPT
Vamos a desglosar Build Types y Product Flavors en Android Gradle para que entiendas bien qué son, cómo funcionan, y cuándo usarlos. Ambos permiten generar variantes de tu aplicación desde el mismo código base, pero con comportamientos distintos. Te dejo una explicación clara y directa.

🔷 ¿Qué son los Build Types?
Los Build Types definen cómo se debe construir tu app (modo debug, optimizada, con logs, sin logs, con proguard, etc.). Te permiten definir configuraciones diferentes en el mismo proyecto, como:
• Qué dependencias se usan.
• Qué variables están disponibles (como una URL base).
• Si se activa ProGuard o no.
• Qué recursos se incluyen o excluyen.

Android incluye por defecto dos:
• debug: usada mientras desarrollas.
• release: para publicar en Google Play.

📌 Para qué sirven:
• Cambiar comportamientos entre desarrollo y producción.
• Activar logs, analytics, tracking, debuggers, etc.
• Configurar Proguard y optimizaciones.

• Build Type
Que podrias hacer en tu app de Los Simpson
- debug: Mostrar logs de red, usar un servidor local, activar botones ocultos, etc.
- release: Ocultar herramientas internas, apuntar al servidor real, optimizar el rendimiento.

PREGUNTA 1. ¿Dónde poner los logs?
Te recomiendo esta guía rápida:
Capa            ¿Poner logs?    ¿Cuando?
Data            Si              Para ver llamadas a APIs, respuestas, errores de red.
Domain          Mejor evitar    Es la lógica de negocio y debe ser agnÃ³stica.
Presentation    Si              Para eventos de UI: clics, navegación, errores visuales.

Conclusión: lo más habitual es hacer logs en Data y Presentation

🔷 ¿Qué son los Product Flavors?
Los Flavors se usan para generar versiones completamente diferentes de tu app a nivel de funcionalidad o entorno (como mock vs remote, free vs paid, demo vs full).

📌 Para qué sirven:
• App mock que usa JSON local vs app remote que usa API.
• App gratuita con publicidad vs app premium sin anuncios.
• Versión interna para testers vs versión para producción.
• Diferentes endpoints, configuraciones o comportamientos.

🔁 ¿Y si los combino?
Puedes combinar Build Types y Flavors para generar todas las combinaciones posibles:
👉 Esto te permite cosas como:
• mockDebug: app con datos locales y logging.
• remoteRelease: app en producción con API real y Proguard.
```bash
mockDebug
mockRelease
remoteDebug
remoteRelease
```

🧠 ¿Cómo accedo a sus valores en el código?
Usando BuildConfig
```kotlin
if (BuildConfig.DATA_SOURCE == "mock") {
    // usa JSON local
}

if (BuildConfig.ENABLE_LOGGING) {
    Log.d("App", "Logging activo solo en debug")
}
```

📁 ¿Cómo organizar el código?
```plaintext
src/
├── main/                   -> código común
├── mock/                   -> código solo para flavor mock
├── remote/                 -> código solo para flavor remote
├── debug/                  -> código solo para build type debug
├── release/                -> código solo para build type release
```

🧪 ¿Y para testing?
¡También puedes tener pruebas específicas por variante!
```plaintext
src/
├── mockTest/               -> tests que solo corren en el flavor mock
├── remoteDebugTest/        -> tests que corren solo en remote+debug
```

## IMPLEMENTEACIÓN EN MI APP

### PARA EL `Build Types`

`build.glade.kts`
```kotlin
    buildTypes {
        debug {
            // Solo en desarrollo: activar logs, analytics, tracking, debuggers, etc.
            isDebuggable = true
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-debug"
            buildConfigField("Boolean", "ENABLE_LOGGING", "true")
            buildConfigField("Boolean", "JSON_TEST", "false")
//           isMinifyEnabled = false
//           signingConfigs = signingConfigs.debug
        }

        create("beta") {
            // Para beta testing
            initWith(getByName("debug")) // o "release" si quieres
            buildConfigField("Boolean", "ENABLE_LOGGING", "false")
            buildConfigField("Boolean", "JSON_TEST", "true")
            isDebuggable = true
        }

        release {
            // Para producción
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            buildConfigField("Boolean", "ENABLE_LOGGING", "false")
            signingConfig = signingConfigs.getByName("debug")
        }
    }
```

`Logger.kt`
```kotlin
    fun logVerbose( message:String ){
        if (BuildConfig.ENABLE_LOGGING)
            Log.v(tag, message)
    }
```

`DataModule.kt`
```kotlin
    @Singleton // ✅ Solo debe haber una instancia de CharacterDao en toda la app
    fun provideCharacterDao(@ApplicationContext context: Context): CharacterDao {

        return if (BuildConfig.JSON_TEST) { // Si el buildConfig es 'true' entonces se usa el de test, con un json más reducido
            CharacterDaoImpl(context, "personajes_test.json", "imagenes_test.json")
        } else {
            CharacterDaoImpl(context, "personajes_data.json", "imagenes_data.json")
        }
    }
```

`ListCharactersViewModel.kt`
```kotlin
@HiltViewModel
class ListCharactersViewModel @Inject constructor(val getAllCharactersUseCase: GetAllCharactersUseCase): ViewModel(), Logger {
    //...
    logInfo( "Cargando con existo los personajes" )
    //...
}
```

### PARA EL `Flavors`

`build.glade.kts`
```kotlin
flavorDimensions += "source"

    productFlavors {
        maybeCreate("mock").apply {
            dimension = "source"
            applicationIdSuffix = ".mock"
            buildConfigField("String", "DATA_SOURCE", "\"mock\"")
        }
        maybeCreate("remote").apply {
            dimension = "source"
            buildConfigField("String", "DATA_SOURCE", "\"remote\"")
        }
    }
```

He creado las ficheros en sus nuevas carpartes en la disposición de `Project`:
src/
├── main/ (aqui esta toda mi app, borro de aquí el `NetworkModule.kt`)
├── mock/
│   └── java/es/upsa/mimo/thesimpsonplace/di/`MockModule.kt`
├── remote/
│   └── java/es/upsa/mimo/thesimpsonplace/di/`NetworkModule.kt`


# Documentación general proyectos
+ Readme's
*	documentacion (/Users/carlosCG/Desktop/4_Android/miApp) (Mio)
*	TheMovieDB (/Users/carlosCG/Desktop/App_Android/TheMovieDB) (Fran)
*	proyectos (/Users/carlosCG/Desktop/4_Android/Clases/Parte 1 - Roberto (Introducción al desarrollo)/proyectos) (Roberto Berjon)
*	proyectos (/Users/carlosCG/Desktop/4_Android/Clases/Parte 2 - Antonio Leiva (Profundidad al desarrollo)/proyectos) (Antonio Leiva)

+ Apps realizadas vistas
    * (Mio)
        - miApp (TheSimpsonPlace)
    * (Roberto Berjon)
        - 01_primeraApp
        - 02_layouts
        - 03_ContadorApp
        - 04_ListadoContactos
    * (Antonio Leiva)
        - Framework-Samples-Compose-main
        - framework-samples-main
        - 3_Testing-expert-preview-screenshot
        - flow-workshop-master
        - app_compose
    * (Aris)
        - 0_Curso-3-app-en-1
        - 1_App_Paginacion
        - 2_ArquitecturaMVVM
    * (Fran)
        - TheMovieDB

# ...