package es.upsa.mimo.thesimpsonplace.presentation.ui.screen.characterSection

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import es.upsa.mimo.thesimpsonplace.data.entities.character.Gender
import es.upsa.mimo.thesimpsonplace.domain.models.Character

// PASO 2 ....
// MIRAR COMO FUNCIONA SU PROYECTO
// GUARDAR MI PROYECTO EN ZIP

// Creo un Preview para testear
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES, name = "Modo Oscuro")
@Composable
fun CharactersScreenPreview() {
    // Son datos simulados (mock), lo cual es ideal para ver la UI con contenido realista.
    val dummyCharacters = listOf(
        Character(1, "Homer Simpson", Gender.Male, "Homer_Simpson"),
        Character(2, "Marge Simpson", Gender.Female, "Marge"),
        Character(3, "Carl Carlson", Gender.Male, "Carl_Carlson", true),
    )

    CharactersScreenMock(
        characters = dummyCharacters,
        favoriteCharacters = setOf(1),
        isLoading = false,
        onToggleFavorite = {},
        onLoadCharacters = {},
        navigateToFilterCharacters = {},
        navigateToFavoriteCharacters = {},
        navigationArrowBack = {}
    )
}
