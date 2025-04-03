package es.upsa.mimo.thesimpsonplace.presentation.ui.screen.quoteSection.gameQuotes

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import es.upsa.mimo.thesimpsonplace.R
import es.upsa.mimo.thesimpsonplace.presentation.ui.screen.profileSection.HistoryGameStatistics
import es.upsa.mimo.thesimpsonplace.presentation.viewmodel.quote.quotesGame.resultGame.ResultGameViewModel

@Composable
fun QuotesResultScreen(
    viewModel: ResultGameViewModel = hiltViewModel(),
    respuestasAciertos: Int,
    navigateToQuotes: () -> Unit
) {

    val gameStats = viewModel.gameStats.collectAsState()
    var showSheet by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.updateStats(respuestasAciertos, 5)
    }

    Scaffold { paddingValues ->

        Box(modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)) {

            if (!showSheet){
                Column(modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.primary),
                    horizontalAlignment = Alignment.CenterHorizontally,
                      verticalArrangement = Arrangement.Center) {

                    Spacer(modifier = Modifier.height(20.dp))

                    Button(
                        onClick = { showSheet = true },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.onPrimary
                        ),
                        modifier = Modifier.padding(bottom = 16.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.total_games_statistics),
                            color = Color.Black,
                            fontWeight = Bold
                        )
                    }

                    Text(
                        text = stringResource(R.string.finish),
                        fontSize = 40.sp,
                        color = Color.Black,
                        fontWeight = Bold,
                        modifier = Modifier
                            .background(
                                MaterialTheme.colorScheme.onPrimary,
                                shape = RoundedCornerShape(16.dp)
                            )
                            .padding(horizontal = 32.dp, vertical = 16.dp),
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    HistoryGameStatistics(
                        totalQuestions = 5,
                        correctAnswers = respuestasAciertos,
                        size = 250.dp,
                        paddingText = 10.dp
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Button(
                        onClick = { navigateToQuotes() },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.onPrimary // Color amarillo
                        ),
                        modifier = Modifier.padding(bottom = 16.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.jugar_de_nuevo),
                            color = Color.Black,
                            fontWeight = Bold
                        )
                    }
                }
            }
            else {
                viewModel.getStats()

                BottomSheetContent(closeSheet = { showSheet = false },
                                    reset = { viewModel.resetStats() },
                                    aciertos = gameStats.value.result.first,
                                    preguntas = gameStats.value.result.second)
            }
        }
    }

}

@Composable
fun BottomSheetContent(closeSheet: () -> Unit, reset: () -> Unit, aciertos:Int, preguntas:Int) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 25.dp, vertical = 100.dp)
            .clip(RoundedCornerShape(16.dp)) // Esquinas redondeadas
            .background(Color.Gray.copy(alpha = 0.4f))
            .padding(16.dp) // Padding interno opcional
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Button(
                onClick = { reset() },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFC107)), // Color amarillo
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                Text(text = stringResource(R.string.reset_history_statistics), color = Color.Black, fontWeight = Bold)
            }

            Spacer(modifier = Modifier.height(8.dp))

            HistoryGameStatistics(
                totalQuestions = preguntas,
                correctAnswers = aciertos,
                size = 200.dp,
                paddingText = 10.dp
            )

            Button(
                onClick = { closeSheet() },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFC107)), // Color amarillo
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                Text(text = stringResource(R.string.cerrar_historial), color = Color.Black, fontWeight = Bold)
            }
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO, name = "Modo Claro")
@Composable
fun QuotesResultScreenPreview() {
    QuotesResultScreen(respuestasAciertos = 3, navigateToQuotes = {})
}
