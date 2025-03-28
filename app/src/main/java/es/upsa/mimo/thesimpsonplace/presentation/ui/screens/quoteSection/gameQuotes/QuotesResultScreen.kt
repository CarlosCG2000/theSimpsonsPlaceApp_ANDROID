package es.upsa.mimo.thesimpsonplace.presentation.ui.screens.quoteSection.gameQuotes

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import es.upsa.mimo.thesimpsonplace.presentation.viewmodel.quote.quotesGame.resultGame.ResultGameViewModel

@Composable
fun QuotesResultScreen(
    viewModel: ResultGameViewModel = hiltViewModel(),
    respuestasAciertos: Int,
    navigateToQuotes: () -> Unit
) {

    val gameStats = viewModel.gameStats.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.updateStats(respuestasAciertos, 5)
    }

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column {
            Text("NÚMERO DE ACIERTOS ES $respuestasAciertos ")
            Text("DATOS GUARDADOS EN DATASTORE ${gameStats.value.result.first} y NÚMERO DE PREGUNTAS  ${gameStats.value.result.second}")

            Button(onClick = {  navigateToQuotes() }) { Text("NAVEGAR AL MENU")}
            Button(onClick = {
                /**Reiniciar estadísticas*/ viewModel.resetStats()
            }) { Text("BORRAR HISTORIAL DE RESULTADOS")}
        }

    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO, name = "Modo Claro")
@Composable
fun QuotesResultScreenPreview() {
    QuotesResultScreen(respuestasAciertos = 3, navigateToQuotes = {})
}
