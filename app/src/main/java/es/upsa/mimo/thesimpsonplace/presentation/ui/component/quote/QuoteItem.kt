package es.upsa.mimo.thesimpsonplace.presentation.ui.component.quote

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import es.upsa.mimo.thesimpsonplace.R
import es.upsa.mimo.thesimpsonplace.domain.models.Quote

@Composable
fun QuoteItem(quote: Quote,
              isFavorite: Boolean,
              onToggleFavorite: () -> Unit) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        //.background(MaterialTheme.colorScheme.secondary),
        colors = CardDefaults.cardColors(containerColor =MaterialTheme.colorScheme.secondary), // Azul oscuro
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(0.5f)
            ) {
                Text(
                    text = quote.cita,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSecondary
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = quote.personaje,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.7f)
                )
                Spacer(modifier = Modifier.height(8.dp))

                IconButton(onClick = { onToggleFavorite() }) {
                    Icon(
                        imageVector = Icons.Filled.Star, // Usa el ícono de estrella
                        contentDescription = stringResource(R.string.favorito),
                        tint = if (isFavorite) MaterialTheme.colorScheme.onPrimary
                        else Color.Red,
                        modifier = Modifier.size(38.dp) // Tamaño del icono
                    )
                }
            }

            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(quote.imagen.toString())
                    .crossfade(true)
                    .build(),
                contentDescription = stringResource(R.string.imagen_personaje),
                modifier = Modifier
                    .size(150.dp),
                contentScale = ContentScale.Fit
            )
        }
    }

}