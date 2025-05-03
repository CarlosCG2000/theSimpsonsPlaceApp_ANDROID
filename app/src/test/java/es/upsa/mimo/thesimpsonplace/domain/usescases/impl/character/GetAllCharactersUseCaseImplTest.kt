package es.upsa.mimo.thesimpsonplace.domain.usescases.impl.character

import es.upsa.mimo.thesimpsonplace.domain.repository.CharaterRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test
import es.upsa.mimo.thesimpsonplace.domain.models.Character
import es.upsa.mimo.thesimpsonplace.charactersFake
import kotlinx.coroutines.test.runTest

/**
 🔁 ¿Qué estás testeando realmente?
 1. Que el caso de uso llama correctamente al repositorio.
 2. Que devuelve lo que el repositorio le da, sin modificarlo.
 3. Que no llama otras funciones que no corresponden (por ejemplo, getCharacterDbById()).

 🧠 ¿Por qué esto es útil?
 • Aísla la lógica del caso de uso.
 • No depende de acceso a red, base de datos, etc.
 • Te da confianza de que tu dominio funciona bien antes de conectar con cosas externas.
*/

// Esta clase prueba el caso de uso GetAllCharactersUseCaseImpl, que tiene como responsabilidad obtener la lista de personajes desde el repositorio.
class GetAllCharactersUseCaseImplTest {

//   Aquí estás simulando (mockeando) el repositorio. Usas @RelaxedMockK, lo que significa:
//  • No necesitas definir el comportamiento de cada función obligatoriamente.
//  • Si llamas a una función que no has configurado (coEvery { ... }), simplemente devolverá valores “vacíos” por defecto (como null, false, 0, emptyList()… según el tipo de retorno).
//  Si usaras @MockK en vez de @RelaxedMockK, tendrías que configurar todo manualmente.
    @RelaxedMockK // o @MockK para un mock más estricto, lo que significa que tendrás que definir el comportamiento de cada función
    private lateinit var repository: CharaterRepository
    // Instancia del caso de uso a probar
    private lateinit var getAllCharactersUseCase: GetAllCharactersUseCaseImpl

    // 🔧 Setup inicial antes de cada test
    @Before
    fun setUp() {
        MockKAnnotations.init(this) // Inicializa las anotaciones de MockK.
        getAllCharactersUseCase = GetAllCharactersUseCaseImpl(repository) // Inicializar el caso de uso con el repositorio simulado
    }

//    Aquí puedes agregar tus pruebas unitarias para el caso de uso
//    • Aquí estás configurando el comportamiento del mock. Le dices:
//    “Cuando se llame a getAllCharacters() dentro de una coroutine, devuelve una lista vacía.”
    @Test
    fun `test Get All Characters with empty list`() = runTest {
        // Given
        coEvery { repository.getAllCharacters() } returns emptyList()

        // When
        val result = getAllCharactersUseCase()

        // Then
        coVerify(exactly = 1) { repository.getAllCharacters() } // Se llamó exactamente una vez a repository.getAllCharacters().
        assertEquals(emptyList<Character>(), result) // El resultado devuelto fue efectivamente una lista vacía.
    }

    @Test
    fun `test Get All Characters with Character`() = runTest {
        // Given
//      listOf<Character>(
//          Character(id = 1, nombre = "Homer Simpson", genero = Gender.Male, imagen = "homer", esFavorito = true),
//          Character(id = 2, nombre = "Marge Simpson", genero = Gender.Female, imagen = "marge", esFavorito = true),
//          Character(id = 3, nombre = "Lisa Simpson", genero = Gender.Female, imagen = "lisa", esFavorito = false)
//      )
        val myList = charactersFake

        coEvery { repository.getAllCharacters() } returns myList // Simulas una lista con 3 personajes

        // When
        val result = getAllCharactersUseCase()

        // Then: Ejecutas el caso de uso (igual que lo haría tu app).
        coVerify(exactly = 1) { repository.getAllCharacters() } // Verifica que se llame a getAllCharacters
        coVerify(exactly = 0) { repository.getCharacterDbById(3) } // Verifica que no se llame a getCharacterDbById
        assertEquals(myList, result)
        assertEquals(3, result.size) // Verifica que el tamaño de la lista sea 3
        assertEquals("Homer Simpson", result[0].nombre) // Verifica que el primer personaje sea Homer Simpson
        assertEquals("Marge Simpson", result[1].nombre) // Verifica que el segundo personaje sea Marge Simpson
        assertEquals("Lisa Simpson", result[2].nombre) // Verifica que el tercer personaje sea Lisa Simpson
    }

}

