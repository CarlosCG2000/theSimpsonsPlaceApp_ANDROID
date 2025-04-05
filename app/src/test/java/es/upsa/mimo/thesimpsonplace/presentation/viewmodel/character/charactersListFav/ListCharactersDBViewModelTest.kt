package es.upsa.mimo.thesimpsonplace.presentation.viewmodel.character.charactersListFav

import es.upsa.mimo.thesimpsonplace.data.entities.character.Gender
import es.upsa.mimo.thesimpsonplace.domain.models.Character
import es.upsa.mimo.thesimpsonplace.charactersFake
import es.upsa.mimo.thesimpsonplace.useCasesMockFake.DeleteCharacterDbUseCaseMock
import es.upsa.mimo.thesimpsonplace.useCasesMockFake.GetAllCharactersDbUseCaseMock
import es.upsa.mimo.thesimpsonplace.useCasesMockFake.GetCharacterDbByIdUseCaseMock
import es.upsa.mimo.thesimpsonplace.useCasesMockFake.InsertCharacterDbUseCaseMock
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ListCharactersDBViewModelTest {

    private lateinit var viewModel: ListCharactersDBViewModel // ViewModel que estamos probando

    // 🧪 1. Configura el dispatcher principal para pruebas
    @Before
    fun setUp() { // Configura el dispatcher principal para pruebas
        Dispatchers.setMain(StandardTestDispatcher()) // Establece el dispatcher principal para pruebas

        charactersFake.clear() // Limpiamos la base de datos falsa antes de cada prueba

        // Agregamos personajes de prueba, ya que si no al ejecutar el test se modifican los datos y no se pueden volver a usar con la fake database iniciar
        charactersFake.addAll(
            listOf(
                Character(id = 1, nombre = "Homer", genero = Gender.Male, imagen = "homer.webp", esFavorito = true),
                Character(id = 2, nombre = "Lisa", genero = Gender.Female, imagen = "lisa.webp", esFavorito = true),
                Character(id = 3, nombre = "Bart", genero = Gender.Male, imagen = "bart.webp", esFavorito = true)
            )
        )

        viewModel = ListCharactersDBViewModel( // Iniciamos el ViewModel con los casos de uso mockeados
            getAllCharactersUseCase = GetAllCharactersDbUseCaseMock(), // Simula la obtención de todos los personajes
            getCharacterByIdUseCase = GetCharacterDbByIdUseCaseMock(), // Simula la obtención de un personaje por ID
            insertCharacterUseCase = InsertCharacterDbUseCaseMock(), // Simula la inserción de un personaje
            deleteCharacterUseCase = DeleteCharacterDbUseCaseMock() // Simula la eliminación de un personaje
        )
    }

    // 🧪 2. Configura el dispatcher principal para pruebas
    @After
    fun tearDown() { // Limpia el dispatcher principal después de cada prueba
        Dispatchers.resetMain()
    }

    // 🧪 3. Pruebas unitarias
    @Test
    fun `test loadFavorites loads correct characters`() = runTest {
        // Given: Esperamos hasta que haya personajes cargados
        val state = viewModel.stateCharacterFav.first { it.characters.isNotEmpty() } // Espera a que la lista de personajes no esté vacía

        // When: Llamamos a loadFavorites (es privada, al obtener stateCharacterFav se realiza la llamada)

        // Then: Verificamos que la lista de personajes cargados es correcta
        assertEquals(3, state.characters.size) // Verifica que la lista de personajes tiene 3 elementos
        // Verifica que los nombres de los personajes son correctos
        val characterIds = state.characters.map { it.id } // Mapea los IDs de los personajes
        assertTrue(characterIds.contains(1)) // Verifica que el ID 1 está en la lista de IDs
    }

    @Test
    fun `test toggleFavorite deletes if exists`() = runTest {
        // Given: Esperamos hasta que haya personajes cargados
        val character = charactersFake[0] // Homer Simpson

        // When: Llamamos a toggleFavorite para eliminarlo
        viewModel.toggleFavorite(character) // Llama a toggleFavorite para eliminarlo
        advanceUntilIdle() // Espera a que se complete la coroutine

        // Then: Verificamos que el personaje ha sido eliminado
        assertFalse(charactersFake.contains(character)) // Verifica que Homer ya no está en la lista
    }

    @Test
    fun `test toggleFavorite inserts if not exists`() = runTest {
        val newCharacter = Character(id = 99, nombre = "Bart", genero = Gender.Male, imagen = "bart", esFavorito = true)
        viewModel.toggleFavorite(newCharacter) // Llama a toggleFavorite para insertarlo
        advanceUntilIdle() // Espera a que se complete la coroutine
        assertTrue(charactersFake.contains(newCharacter)) // Verifica que Bart está en la lista
    }
}