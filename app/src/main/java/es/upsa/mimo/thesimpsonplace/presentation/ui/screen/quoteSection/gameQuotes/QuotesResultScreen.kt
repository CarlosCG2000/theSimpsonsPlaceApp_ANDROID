package es.upsa.mimo.thesimpsonplace.presentation.ui.screen.quoteSection.gameQuotes

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.font.FontWeight.Companion.SemiBold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import es.upsa.mimo.thesimpsonplace.R
import es.upsa.mimo.thesimpsonplace.presentation.ui.component.ModifierContainer
import es.upsa.mimo.thesimpsonplace.presentation.ui.component.game.HistoryGameStatistics
import es.upsa.mimo.thesimpsonplace.presentation.viewmodel.quote.quotesGame.resultGame.ResultGameViewModel

@Composable
fun QuotesResultScreen(
    viewModel: ResultGameViewModel = hiltViewModel(),
    respuestasAciertos: Int,
    navigateToQuotes: () -> Unit,
    navigationToMenu:() -> Unit // Navegacion al menu principal
) {

    val gameStats = viewModel.gameStats.collectAsState()
    var showSheet by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }


    LaunchedEffect(Unit) {
        viewModel.updateStats(respuestasAciertos, 5)
    }

    Scaffold { paddingValues ->

        Box(modifier = ModifierContainer(paddingValues)) {

            if (!showSheet){
                Column(modifier = ModifierContainer(PaddingValues(0.dp)),
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
                        color = MaterialTheme.colorScheme.onSecondary,
                        fontWeight = Bold,
                        modifier = Modifier
                            .background(
                                MaterialTheme.colorScheme.secondary,
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
                        paddingText = 10.dp,
                        title = stringResource(R.string.estadisticas_de_la_partida)// stringResource(R.string.history_game_statistics)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Button(
                        onClick = { showDialog = true },
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

                    if (showDialog) {
                        AlertDialog(
                            onDismissRequest = { showDialog = false },
                            title = { Text(text = stringResource(R.string.ready_to_start_the_quiz_game),
                                fontWeight = SemiBold,
                                color = MaterialTheme.colorScheme.onSecondary,
                                textAlign = TextAlign.Center) },
                            containerColor = MaterialTheme.colorScheme.secondary,
                            shape = RoundedCornerShape(16.dp),
                            // confirmButton = { Button() {} },
                            // dismissButton = { Button() {} },
                            confirmButton = {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(bottom = 16.dp),
                                    horizontalArrangement = Arrangement.SpaceEvenly // Distribuye los botones equitativamente
                                ) {
                                    Button(
                                        onClick = {
                                            showDialog = false
                                            navigateToQuotes()
                                        },
                                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFC107))
                                    ) {
                                        Text(stringResource(R.string.aceptar_jugar), color = Color.Black)
                                    }
                                    Button(
                                        onClick = {
                                            showDialog = false
                                            navigationToMenu()
                                        },
                                        colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray)
                                    ) {
                                        Text(stringResource(R.string.ir_al_menu), color = Color.Black)
                                    }
                                }
                            }
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
                paddingText = 10.dp,
                title = stringResource(R.string.history_game_statistics)
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
    QuotesResultScreen(respuestasAciertos = 3, navigateToQuotes = {}, navigationToMenu = {})
}
