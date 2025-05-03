package es.upsa.mimo.thesimpsonplace.data.daos.remote.impl

import android.content.Context
import android.content.res.AssetManager
import es.upsa.mimo.thesimpsonplace.data.daos.remote.CharacterDao
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import kotlin.test.Test

/**
@ExperimentalCoroutinesApi
class CharacterDaoImplTest {

    private lateinit var characterDao: CharacterDao

    private val mockContext = mockk<Context>()
    private val mockAssets = mockk<AssetManager>()

    @Before
    fun setup() {
        every { mockContext.assets } returns mockAssets

//        every { mockAssets.open("personajes_test.json") } returns charactersJson.byteInputStream()
//        every { mockAssets.open("imagenes_test.json") } returns imagesJson.byteInputStream()

        characterDao = CharacterDaoImpl(
            context = mockContext,
            dataJson = "characters_test.json",
            imagJson = "images_test.json"
        )
    }

    @Test
    fun getAllCharacters_returns_expected_data() = runTest {
        // Preparamos JSON de prueba simulado
        val charactersJson = """
        [
            {
                "id": 6660,
                "name": "Homer Simpson",
                "normalized_name": "homer simpson",
                "gender": "m"
            },
            {
                "id": 71,
                "name": "Marge Simpson",
                "normalized_name": "marge simpson",
                "gender": "f"
            },
            {
                "id": 184,
                "name": "Apu Nahasapeemapetilon",
                "normalized_name": "apu nahasapeemapetilon",
                "gender": "m"
            }
        ]
    """.trimIndent()

        val imagesJson = """
        [
            {
                "name": "Homer Simpson",
                "image": "Homer_Simpson"
            },
            {
                "name": "Marge Simpson",
                "image": "Marge"
            },
            {
                "name": "Bart Simpson",
                "image": "Bart"
            }
        ]
    """.trimIndent()

        // Simulamos InputStreams con los JSONs de prueba
        val mockAssetManager = mockk<AssetManager>()
        every { mockAssetManager.open("characters_test.json") } returns charactersJson.byteInputStream()
        every { mockAssetManager.open("images_test.json") } returns imagesJson.byteInputStream()

        val mockContext = mockk<Context>()
        every { mockContext.assets } returns mockAssetManager

        val dao = CharacterDaoImpl(
            context = mockContext,
            dataJson = "characters_test.json",
            imagJson = "images_test.json"
        )

        val result = dao.getAllCharacters()

        // 🧪 Comprobamos que hay 3 personajes como esperábamos
        assertEquals(3, result.size)

        // 🧪 Comprobamos que el primero es Homer
        assertEquals("Homer", result[0].nombre)
        assertEquals("homer.png", result[0].imagen)
    }
}
*/