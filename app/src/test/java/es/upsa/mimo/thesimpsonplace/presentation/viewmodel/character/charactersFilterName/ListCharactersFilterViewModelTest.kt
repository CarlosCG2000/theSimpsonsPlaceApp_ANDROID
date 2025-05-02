package es.upsa.mimo.thesimpsonplace.presentation.viewmodel.character.charactersFilterName

import es.upsa.mimo.thesimpsonplace.data.entities.character.Gender
import es.upsa.mimo.thesimpsonplace.domain.usescases.character.GetFilterNameCharactersUseCase
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import kotlin.test.Test
import es.upsa.mimo.thesimpsonplace.domain.models.Character
import io.mockk.coEvery
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle

@OptIn(ExperimentalCoroutinesApi::class)
class ListCharactersFilterViewModelTest {

    @RelaxedMockK
    private lateinit var getFilterNameCharactersUseCase: GetFilterNameCharactersUseCase // Caso de uso que estamos probando

    private lateinit var viewModel: ListCharactersFilterViewModel // ViewModel que estamos probando

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        MockKAnnotations.init(this) // Inicializa las anotaciones de MockK
        Dispatchers.setMain(testDispatcher) // Establece el dispatcher principal para las pruebas
        viewModel = ListCharactersFilterViewModel(getFilterNameCharactersUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // Restablece el dispatcher principal después de las pruebas
    }

    @Test
    fun `getFilterNameCharacters updates state with filtered characters`() = runTest {
        // Given
        val filteredCharacters = listOf( // Lista de personajes filtrados
            Character(id = 1, nombre = "Homer Simpson", genero = Gender.Male, imagen = "homer", esFavorito = false),
            Character(id = 1, nombre = "Marge Simpson", genero = Gender.Female, imagen = "marge", esFavorito = false)
        )

        coEvery { getFilterNameCharactersUseCase("homer") } returns filteredCharacters // ojo, returns no return, le dice al mock qué devolver cuando se haga esa llamada.

        // When
        viewModel.getFilterNameCharacters("homer")
        advanceUntilIdle() // Espera a que termine la coroutine

        // Then
        val state = viewModel.stateCharacterFilter.value // Obtenemos el estado actual del ViewModel
        assertEquals(false, state.isLoading) // Verificamos que no está en loading
        assertEquals(filteredCharacters, state.characters) // Verificamos que los personajes filtrados son los correctos
    }

    @Test
    fun `getFilterNameCharacters shows loading then updates`() = runTest {
        // Given
        val filteredCharacters = listOf(
            Character(id = 2, nombre = "Lisa Simpson", genero = Gender.Female, imagen = "lisa", esFavorito = false)
        )

        // Simulamos una pequeña demora
        coEvery { getFilterNameCharactersUseCase("lisa") } coAnswers {
            delay(100)
            filteredCharacters
        }

        // When
        viewModel.getFilterNameCharacters("lisa")

        // Dejamos avanzar un poco para que el update de isLoading=true se dispare
        advanceTimeBy(1)

        // Just after calling, we check loading state (before coroutine ends)
        val loadingState = viewModel.stateCharacterFilter.value
        assertTrue(loadingState.isLoading) // Aquí sí está en loading

        // Ahora dejamos que la corrutina acabe
        advanceUntilIdle()

        // Then: comprobamos el estado final
        val finalState = viewModel.stateCharacterFilter.value
        assertFalse(finalState.isLoading)
        assertEquals(filteredCharacters, finalState.characters)
    }
}