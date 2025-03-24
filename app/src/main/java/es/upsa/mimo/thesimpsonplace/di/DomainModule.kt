package es.upsa.mimo.thesimpsonplace.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import es.upsa.mimo.thesimpsonplace.domain.repository.* //CharaterRepository
import es.upsa.mimo.thesimpsonplace.domain.repository.impl.* //CharaterRepositoryImpl
import es.upsa.mimo.thesimpsonplace.domain.usescases.character.* //FetchAllCharactersDbUseCase
import es.upsa.mimo.thesimpsonplace.domain.usescases.episode.* //GetAllEpisodesDbUseCase
import es.upsa.mimo.thesimpsonplace.domain.usescases.impl.character.* //FetchAllCharactersDbUseCaseImpl
import es.upsa.mimo.thesimpsonplace.domain.usescases.impl.episode.* //GetAllEpisodesDbUseCaseImpl
import es.upsa.mimo.thesimpsonplace.domain.usescases.impl.quote.* //DeleteQuoteDbUseCaseImpl
import es.upsa.mimo.thesimpsonplace.domain.usescases.quote.* // DeleteQuoteDbUseCase
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
    abstract fun bindFetchAllCharactersDbUseCase( useCaseImpl: FetchAllCharactersDbUseCaseImpl ): FetchAllCharactersDbUseCase
    @Binds
    abstract fun bindInsertCharacterDbUseCase( useCaseImpl: InsertCharacterDbUseCaseImpl ): InsertCharacterDbUseCase
    @Binds
    abstract fun bindUpdateCharacterDbUseCase( useCaseImpl: UpdateCharacterDbUseCaseImpl ): UpdateCharacterDbUseCase

    @Binds
    @Singleton
    abstract fun  bindEpisodeRepository(impl: EpisodeRepositoryImpl): EpisodeRepository

    @Binds
    abstract fun  bindGetAllEpisodesUseCase(impl: GetAllEpisodesUseCaseImpl): GetAllEpisodesUseCase
    @Binds
    abstract fun  bindGetEpisodeByIdUseCase(impl: GetEpisodeByIdUseCaseImpl): GetEpisodeByIdUseCase
    @Binds
    abstract fun  bindGetEpisodesByTitleUseCase(impl: GetEpisodesByTitleUseCaseImpl): GetEpisodesByTitleUseCase
    @Binds
    abstract fun  bindGetEpisodesByDateUseCase(impl: GetEpisodesByDateUseCaseImpl): GetEpisodesByDateUseCase
    @Binds
    abstract fun  bindGetEpisodesBySeasonUseCase(impl: GetEpisodesBySeasonUseCaseImpl): GetEpisodesBySeasonUseCase
    @Binds
    abstract fun  bindGetEpisodesByChapterUseCase(impl: GetEpisodesByChapterUseCaseImpl): GetEpisodesByChapterUseCase

    @Binds
    abstract fun  bindGetAllEpisodesDbUseCase(impl: GetAllEpisodesDbUseCaseImpl): GetAllEpisodesDbUseCase
    @Binds
    abstract fun  bindGetEpisodeByIdDbUseCase(impl: GetEpisodeByIdDbUseCaseImpl): GetEpisodeByIdDbUseCase
    @Binds
    abstract fun  bindGetEpisodeByIdsDbUseCase(impl: GetEpisodeByIdsDbUseCaseImpl): GetEpisodeByIdsDbUseCase
    @Binds
    abstract fun  bindUpdateEpisodeDbUseCase(impl: UpdateEpisodeDbUseCaseImpl): UpdateEpisodeDbUseCase
    @Binds
    abstract fun  bindInsertEpisodeDbUseCase(impl: InsertEpisodeDbUseCaseImpl): InsertEpisodeDbUseCase

    @Binds
    @Singleton
    abstract fun  bindQuoteRepository(impl: QuoteRepositoryImpl): QuoteRepository

    @Binds
    abstract fun  bindGetQuotesUseCase(impl: GetQuotesUseCaseImpl): GetQuotesUseCase
    @Binds
    abstract fun  bindGetQuestionsUseCase(impl: GetQuestionsUseCaseImpl): GetQuestionsUseCase

    @Binds
    abstract fun  bindGetAllQuoteDbUseCase(impl: GetAllQuoteDbUseCaseImpl): GetAllQuoteDbUseCase
    @Binds
    abstract fun  bindDeleteQuoteDbUseCase(impl: DeleteQuoteDbUseCaseImpl): DeleteQuoteDbUseCase
    @Binds
    abstract fun  bindInsertQuoteDbUseCase(impl: InsertQuoteDbUseCaseImpl): InsertQuoteDbUseCase
}

