package es.upsa.mimo.thesimpsonplace.presentation.ui.component.character

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import es.upsa.mimo.thesimpsonplace.R
import es.upsa.mimo.thesimpsonplace.data.entities.character.Gender
import es.upsa.mimo.thesimpsonplace.domain.models.Character
import es.upsa.mimo.thesimpsonplace.presentation.ui.screen.menuSection.MenuScreen

@Composable
fun CharacterItem(modifier: Modifier,
                  character: Character,
                  isFavorite: Boolean,
                  onToggleFavorite: () -> Unit) {

    val context = LocalContext.current

    val imageResId = remember(character.imagen) {
        val id = context.resources.getIdentifier( // ⚠️ getIdentifier, esta deprecado pero aún funciona y sigue siendo la única opción dinámica.
            character.imagen?.lowercase(),
            "drawable",
            context.packageName
        )
        if (id == 0) R.drawable.not_specified else id
    }

    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondary), //if (isFavorite) Color.Gray else Color(0xFF2C3E72) )
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp)
    ) {
        Row(
            modifier = Modifier .fillMaxWidth()
                                .padding(8.dp)
        )
        {
            Image(
                painter = painterResource(id = imageResId),
                contentDescription = character.nombre,
                modifier = Modifier
                    .size(110.dp)
                    .clip(CircleShape)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(text = character.nombre,
                    fontWeight = Bold,
                    fontSize = 20.sp,
                    color =  MaterialTheme.colorScheme.onSecondary)

                Text(text = character.genero.toString(),
                    fontSize = 16.sp,
                    color =  MaterialTheme.colorScheme.onSecondary)

                IconButton(onClick = { onToggleFavorite() }) {
                    Icon(
                        imageVector = Icons.Filled.Star,
                        contentDescription = stringResource(R.string.favorito),
                        tint = if (isFavorite) Color.Yellow else Color.Gray,
                        modifier = Modifier.size(38.dp)
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun CharacterItemPreview() {

    CharacterItem(
        modifier = Modifier.fillMaxWidth()
                            .padding(16.dp),

        character = Character(id = 1,
                              nombre = "Homer Simpson",
                              imagen = "homer_simpson",
                              genero = Gender.Male,
                               esFavorito = true),
        isFavorite = true,
        onToggleFavorite = {  }
    )

}
