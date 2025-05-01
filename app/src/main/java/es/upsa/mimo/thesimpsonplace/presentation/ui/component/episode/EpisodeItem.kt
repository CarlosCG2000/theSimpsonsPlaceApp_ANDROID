package es.upsa.mimo.thesimpsonplace.presentation.ui.component.episode

import es.upsa.mimo.thesimpsonplace.R
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import es.upsa.mimo.thesimpsonplace.domain.models.Episode
import es.upsa.mimo.thesimpsonplace.utils.toFormattedString

@Composable
fun EpisodeItem(modifier: Modifier,
                indiceEpisodio: Int,
                episode: Episode,
                onEpisodeSelected: (String) -> Unit,
                isFavorite: Boolean,
                isView: Boolean) {

    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor =
            if (isFavorite) MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.2f)
            else MaterialTheme.colorScheme.secondary),
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
                                .padding(8.dp)
                                .clickable { onEpisodeSelected(episode.id) } // Ahora puedes obtener la posición del item
        ) {

            Text(text = "${indiceEpisodio + 1} - ${episode.titulo}", fontWeight = FontWeight.Bold, fontSize = 20.sp)

            Spacer(modifier = Modifier.width(16.dp))

            Row (modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically){

                Text(text = episode.lanzamiento.toFormattedString(), fontSize = 20.sp)

                Spacer(modifier = Modifier.width(16.dp))

                Text(text = stringResource(R.string.temporada)+" ${ episode.temporada }", fontSize = 20.sp)

                Spacer(modifier = Modifier.width(16.dp))

                Icon(
                    imageVector = if (isView) Icons.Filled.Visibility else Icons.Filled.VisibilityOff, // Usa el ícono de estrella
                    contentDescription = "View",
                    tint = if (isView) MaterialTheme.colorScheme.onPrimary else Color.Transparent, // Amarillo si es visto, rojo si no
                    modifier = Modifier.size(38.dp) // Tamaño del icono
                )
            }
        }
    }
}