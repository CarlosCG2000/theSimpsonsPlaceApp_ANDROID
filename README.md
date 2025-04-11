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
            - `Gender`

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
        Estos archivos (CharacterDtoMapper, EpisodeDtoMapper, QuoteDtoMapper) contienen funciones de extensión para convertir entre diferentes modelos de datos:
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
        Define un contrato que dice que el caso de uso debe devolver un Flow<List<Character>>. La UI solo usa esta interfaz, sin saber qué repositorio usa internamente.
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