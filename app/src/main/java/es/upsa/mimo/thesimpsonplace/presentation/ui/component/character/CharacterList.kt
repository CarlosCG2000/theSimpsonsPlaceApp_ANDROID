package es.upsa.mimo.thesimpsonplace.presentation.ui.component.character

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import es.upsa.mimo.thesimpsonplace.domain.models.Character

@Composable
fun CharacterList(modifier: Modifier = Modifier,
                  characters: List<Character>,
                  favoriteCharacters: Set<Int>,
                  onToggleFavorite: (Character) -> Unit) {

    LazyColumn( modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally) {
        items(characters) { character ->

            // val isFavorite = rememberUpdatedState(character.id in favoriteCharacters) // ✅ ¿que es 'rememberUpdatedState' y porque este tipo y no otro?
            /**
            •	rememberUpdatedState(value) mantiene actualizado un valor que cambia frecuentemente, evitando recomposiciones innecesarias.
            •	En este caso:
            •	character.id in favoriteCharacters puede cambiar cuando se agregan/eliminan favoritos.
            •	rememberUpdatedState garantiza que CharacterItem siempre tenga el valor más reciente sin recomposiciones extra.
            - Alternativa sin rememberUpdatedState (puede recomponer más de la cuenta)
             */
            val isFavorite = character.id in favoriteCharacters

            CharacterItem(
                modifier = Modifier.fillMaxWidth()
                    .padding(16.dp),
                character = character,
                isFavorite = isFavorite/*.value*/,
                onToggleFavorite = { onToggleFavorite(character) }
            )
        }
    }
}