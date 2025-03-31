package es.upsa.mimo.thesimpsonplace.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import es.upsa.mimo.thesimpsonplace.data.daos.local.datastore.GameDao
import es.upsa.mimo.thesimpsonplace.data.daos.local.datastore.UserDao
import es.upsa.mimo.thesimpsonplace.data.daos.local.datastore.impl.GameDaoImpl
import es.upsa.mimo.thesimpsonplace.data.daos.local.datastore.impl.UserDaoImpl
import es.upsa.mimo.thesimpsonplace.domain.repository.* //CharaterRepository
import es.upsa.mimo.thesimpsonplace.domain.repository.impl.* //CharaterRepositoryImpl
import es.upsa.mimo.thesimpsonplace.domain.usescases.character.* //FetchAllCharactersDbUseCase
import es.upsa.mimo.thesimpsonplace.domain.usescases.episode.* //GetAllEpisodesDbUseCase
import es.upsa.mimo.thesimpsonplace.domain.usescases.game.GetGameStatsUseCase
import es.upsa.mimo.thesimpsonplace.domain.usescases.game.ResetStatsUseCase
import es.upsa.mimo.thesimpsonplace.domain.usescases.game.UpdateStatsUseCase
import es.upsa.mimo.thesimpsonplace.domain.usescases.impl.character.* //FetchAllCharactersDbUseCaseImpl
import es.upsa.mimo.thesimpsonplace.domain.usescases.impl.episode.* //GetAllEpisodesDbUseCaseImpl
import es.upsa.mimo.thesimpsonplace.domain.usescases.impl.game.GetGameStatsUseCaseImpl
import es.upsa.mimo.thesimpsonplace.domain.usescases.impl.game.ResetStatsUseCaseImpl
import es.upsa.mimo.thesimpsonplace.domain.usescases.impl.game.UpdateStatsUseCaseImpl
import es.upsa.mimo.thesimpsonplace.domain.usescases.impl.quote.* //DeleteQuoteDbUseCaseImpl
import es.upsa.mimo.thesimpsonplace.domain.usescases.impl.user.GetUserPreferencesUseCaseImpl
import es.upsa.mimo.thesimpsonplace.domain.usescases.impl.user.UpdateUserUseCaseImpl
import es.upsa.mimo.thesimpsonplace.domain.usescases.quote.* // DeleteQuoteDbUseCase
import es.upsa.mimo.thesimpsonplace.domain.usescases.user.GetUserPreferencesUseCase
import es.upsa.mimo.thesimpsonplace.domain.usescases.user.UpdateUserUseCase
import javax.inject.Singleton

// Un @Module no puede contener métodos abstractos (@Binds) y métodos concretos (@Provides) al mismo tiempo. Por ello se crea una nueva clase.
// Si se usan @Provides no necesitas @Binds porque ya lo estás proporcionando manualmente.
// ✔️ Si solo usas '@Inject constructor' en una clase, 'Hilt' solo sabe cómo construirla, pero no cómo enlazarla a su interfaz.
// ✔️ Si quieres que 'Hilt' la proporcione cuando alguien pida la interfaz, necesitas '@Binds' o '@Provides' (se podria crear en el 'AppModule' ya que no tendria porque ser abstracto)
@Module
@InstallIn(SingletonComponent::class)
abstract class DomainModule {

//    @Binds no necesita que le pases dependencias manualmente. Solo recibe la implementación (impl) y Hilt resuelve el resto.
//    CharaterRepositoryImpl ya tiene '@Inject constructor'(dao: CharacterDao, databaseDao: CharacterDatabaseDao), así que Hilt le pasará esos parámetros automáticamente.
//    @Binds solo permite un parámetro, pero aquí tienes dos (dao y databaseDao).
    @Binds
    @Singleton // ✅ Queremos que haya una única instancia de CharaterRepositoryImpl
    abstract fun bindCharacterRepository(impl: CharaterRepositoryImpl): CharaterRepository
    // Solos casos de uso que se no implementen de forma automatica con '@Inject constructor()' deberan ser '@Provides' y esos ya contienen '@Binds' y se definiran de forma manual por lo que no necesitar incluirse aqui.
    // Pero si son casos de uso con '@Inject constructor()', solo se necesitan conectar con su inferfaz (Binds) ya que de forma automatizada tienen un valor de Dao (el que tenga '@Inject constructor()')

    @Binds
    abstract fun bindGetAllCharactersUseCase( useCaseImpl: GetAllCharactersUseCaseImpl ): GetAllCharactersUseCase
    @Binds
    abstract fun bindGetFilterNameCharactersUseCase( useCaseImpl: GetFilterNameCharactersUseCaseImpl ): GetFilterNameCharactersUseCase

    @Binds
    abstract fun bindGetAllCharactersDbUseCase(impl: GetAllCharactersDbUseCaseImpl): GetAllCharactersDbUseCase
    @Binds
    abstract fun bindGetCharacterDbByIdUseCase(impl: GetCharacterDbByIdUseCaseImpl): GetCharacterDbByIdUseCase
    @Binds
    abstract fun bindInsertCharacterUseCase(impl: InsertCharacterDbUseCaseImpl): InsertCharacterDbUseCase
    @Binds
    abstract fun bindDeleteCharacterUseCase(impl: DeleteCharacterDbUseCaseImpl): DeleteCharacterDbUseCase

    @Binds
    @Singleton
    abstract fun bindEpisodeRepository(impl: EpisodeRepositoryImpl): EpisodeRepository

    @Binds
    abstract fun bindGetAllEpisodesUseCase(impl: GetAllEpisodesUseCaseImpl): GetAllEpisodesUseCase
    @Binds
    abstract fun bindGetEpisodeByIdUseCase(impl: GetEpisodeByIdUseCaseImpl): GetEpisodeByIdUseCase
    @Binds
    abstract fun bindGetEpisodesByTitleUseCase(impl: GetEpisodesByTitleUseCaseImpl): GetEpisodesByTitleUseCase
    @Binds
    abstract fun bindGetEpisodesByDateUseCase(impl: GetEpisodesByDateUseCaseImpl): GetEpisodesByDateUseCase
    @Binds
    abstract fun bindGetEpisodesBySeasonUseCase(impl: GetEpisodesBySeasonUseCaseImpl): GetEpisodesBySeasonUseCase
    @Binds
    abstract fun bindGetEpisodesByChapterUseCase(impl: GetEpisodesByChapterUseCaseImpl): GetEpisodesByChapterUseCase
    @Binds
    abstract fun bindGetEpisodesByViewUseCase(impl: GetEpisodesByViewUseCaseImpl): GetEpisodesByViewUseCase
    @Binds
    abstract fun bindGetEpisodesOrderUseCase(impl: GetEpisodesOrderUseCaseImpl): GetEpisodesOrderUseCase

    @Binds
    abstract fun bindGetAllEpisodesDbUseCase(impl: GetAllEpisodesDbUseCaseImpl): GetAllEpisodesDbUseCase
    @Binds
    abstract fun bindGetEpisodeByIdDbUseCase(impl: GetEpisodeDbByIdUseCaseImpl): GetEpisodeDbByIdUseCase
    @Binds
    abstract fun bindGetWatchedEpisodesUseCase(impl: GetWatchedEpisodesUseCaseImpl): GetWatchedEpisodesUseCase
    @Binds
    abstract fun bindIsEpisodeDbWatchedDbUseCase(impl: IsEpisodeDbWatchedUseCaseImpl): IsEpisodeDbWatchedUseCase
    @Binds
    abstract fun bindIsEpisodeDbFavoriteUseCase(impl: IsEpisodeDbFavoriteUseCaseImpl): IsEpisodeDbFavoriteUseCase
    @Binds
    abstract fun bindUpdateEpisodeDbUseCase(impl: UpdateEpisodeDbStatusUseCaseImpl): UpdateEpisodeDbStatusUseCase
    @Binds
    abstract fun bindInsertEpisodeDbUseCase(impl: InsertEpisodeDbUseCaseImpl): InsertEpisodeDbUseCase

    @Binds
    @Singleton
    abstract fun bindQuoteRepository(impl: QuoteRepositoryImpl): QuoteRepository

    @Binds
    abstract fun bindGetQuotesUseCase(impl: GetQuotesUseCaseImpl): GetQuotesUseCase
    @Binds
    abstract fun bindGetQuestionsUseCase(impl: GetQuestionsUseCaseImpl): GetQuestionsUseCase

    @Binds
    abstract fun bindGetAllQuoteDbUseCase(impl: GetAllQuoteDbUseCaseImpl): GetAllQuoteDbUseCase
    @Binds
    abstract fun bindGetQuoteDbByCitaUseCase(impl: GetQuoteDbByCitaUseCaseImpl): GetQuoteDbByCitaUseCase
    @Binds
    abstract fun bindDeleteQuoteDbUseCase(impl: DeleteQuoteDbUseCaseImpl): DeleteQuoteDbUseCase
    @Binds
    abstract fun bindInsertQuoteDbUseCase(impl: InsertQuoteDbUseCaseImpl): InsertQuoteDbUseCase

    @Binds
    @Singleton
    abstract fun bindGameDatastorePreferencesDao(impl: GameDaoImpl): GameDao

    @Binds
    @Singleton
    abstract fun bindGameDatastorePreferencesRepository(impl: GameRepositoryImpl): GameRepository

    @Binds
    abstract fun bindUpdateStatsUseCase(impl: UpdateStatsUseCaseImpl): UpdateStatsUseCase
    @Binds
    abstract fun bindResetStatsUseCase(impl: ResetStatsUseCaseImpl): ResetStatsUseCase
    @Binds
    abstract fun bindGetGameStatsUseCase(impl: GetGameStatsUseCaseImpl): GetGameStatsUseCase

    @Binds
    @Singleton
    abstract fun bindUserDatastorePreferencesDao(impl: UserDaoImpl): UserDao

    @Binds
    @Singleton
    abstract fun bindUserDatastorePreferencesRepository(impl: UserRepositoryImpl): UserRepository

    @Binds
    abstract fun bindGetUserPreferencesUseCase(impl: GetUserPreferencesUseCaseImpl): GetUserPreferencesUseCase
    @Binds
    abstract fun bindUpdateUserUseCase(impl: UpdateUserUseCaseImpl): UpdateUserUseCase
}

