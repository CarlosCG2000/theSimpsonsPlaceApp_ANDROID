package es.upsa.mimo.thesimpsonplace.presentation.ui.component

import android.content.res.Resources
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FormatListNumbered
import androidx.compose.material.icons.filled.PlaylistRemove
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun NoContentComponent(modifier: Modifier = Modifier,
                       titleText: String,
                       infoText: String)
{
    Column(
        modifier = modifier, // Color de fondo similar al de la imagen
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Icon(
            imageVector = Icons.Default.PlaylistRemove, // Usa un ícono similar al de la imagen
            contentDescription = titleText,
            tint = MaterialTheme.colorScheme.onPrimary, // Amarillo similar
            modifier = Modifier.size(100.dp).padding(bottom = 10.dp))

        Text(
            text = titleText,
            fontWeight = Bold,
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier)

        Text(
            text = infoText,
            color = Color.White,
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(start = 32.dp, end = 32.dp, top = 4.dp))
    }
}
