package es.upsa.mimo.thesimpsonplace.domain.usescases.impl.character

import es.upsa.mimo.thesimpsonplace.data.entities.character.Gender
import es.upsa.mimo.thesimpsonplace.domain.repository.CharaterRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.test.runTest
import org.junit.Before
import kotlin.test.Test
import es.upsa.mimo.thesimpsonplace.domain.models.Character

class DeleteCharacterDbUseCaseImplTest {
    @RelaxedMockK
    private lateinit var repository: CharaterRepository
    private lateinit var deleteCharacterDbUseCase: DeleteCharacterDbUseCaseImpl
    private val characterToDelete = Character(
        id = 1,
        nombre = "Homer Simpson",
        genero = Gender.Male,
        imagen = "homer",
        esFavorito = true
    )

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        deleteCharacterDbUseCase = DeleteCharacterDbUseCaseImpl(repository) // Inicializar el caso de uso con el repositorio simulado
    }

    @Test
    fun `test delete character from db`() = runTest {
        // Given
        coEvery { repository.deleteCharacterDb(characterToDelete) }

        // When
        deleteCharacterDbUseCase(characterToDelete)

        // Then
        coVerify(exactly = 1) { repository.deleteCharacterDb(characterToDelete) } // Se llamó exactamente una vez a repository.getAllCharacters().
    }

}
