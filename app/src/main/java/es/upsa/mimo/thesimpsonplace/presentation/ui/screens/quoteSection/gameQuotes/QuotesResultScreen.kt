package es.upsa.mimo.thesimpsonplace.presentation.ui.screens.quoteSection.gameQuotes

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@Composable
fun QuotesResultScreen(
    navigateToQuotes: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {

        Column(
            modifier = Modifier.fillMaxSize(), // Ocupa toda la pantalla
            verticalArrangement = Arrangement.Center, // Centra verticalmente dentro de Column
            horizontalAlignment = Alignment.CenterHorizontally
        ) { // Centra horizontalmente
            // LOGO SIMPSONS
            Text("JUEGO CITAS RESULTADO", fontSize = 24.sp, fontWeight = Bold)
            Button(onClick = navigateToQuotes) {
                Text("BOTON DE VOLVER AL MENU")
            }
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO, name = "Modo Claro")
@Composable
fun QuotesResultScreenPreview() {
    QuotesResultScreen({})
}