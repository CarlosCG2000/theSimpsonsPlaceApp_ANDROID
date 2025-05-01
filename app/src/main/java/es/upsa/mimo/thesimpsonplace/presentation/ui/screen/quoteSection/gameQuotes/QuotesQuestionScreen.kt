package es.upsa.mimo.thesimpsonplace.presentation.ui.screen.quoteSection.gameQuotes

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import es.upsa.mimo.thesimpsonplace.R
import es.upsa.mimo.thesimpsonplace.presentation.ui.component.ModifierContainer
import es.upsa.mimo.thesimpsonplace.presentation.viewmodel.quote.quotesGame.questionGame.QuotesGameUI
import es.upsa.mimo.thesimpsonplace.presentation.viewmodel.quote.quotesGame.questionGame.QuotesGameViewModel
import kotlin.String

@Composable
fun QuotesQuestionScreen(
    viewModel: QuotesGameViewModel = hiltViewModel(),
    navigateToResultQuotes: (Int) -> Unit
) {
    val state: State<QuotesGameUI> = viewModel.stateQuestions.collectAsState() // sincrono para manejarlo en la UI

    var currentQuestionIndex by remember { mutableIntStateOf(0) }
    var selectedAnswer by remember { mutableStateOf<String?>(null) }
    var showDialog by remember { mutableStateOf(false) }
    var correctAnswers by remember { mutableIntStateOf(0) }

    // Si está cargando, mostrar un indicador
    if (state.value.isLoading) {
        Box(modifier = ModifierContainer(PaddingValues(0.dp)),
            contentAlignment = Alignment.Center) {
            CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.onPrimary
            )
        }
        return
    }

    if (state.value.questions.isEmpty()) { // Si no hay preguntas después de cargar, mostrar un mensaje de error
        Box(
            modifier = ModifierContainer(PaddingValues(0.dp)),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = stringResource(R.string.no_questions_available),
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { viewModel.getQuestions() },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary
                    )
                ) {
                    Text(stringResource(R.string.reiniciar), color = MaterialTheme.colorScheme.onSecondary)
                }
            }
        }
        return
    }

    val currentQuestion = state.value.questions[currentQuestionIndex]

    // La aleatorización ahora solo ocurre cuando el usuario avanza a la siguiente pregunta.
    val shuffledOptions = remember(currentQuestionIndex) {
        (currentQuestion.personajeIncorrectos + currentQuestion.personajeCorrecto).shuffled()
    }

    Box(
        modifier = ModifierContainer(PaddingValues(0.dp)),
        contentAlignment = Alignment.Center
    ) {

        Column(
            modifier = ModifierContainer(PaddingValues(16.dp)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(
                    R.string.quote_of,
                    currentQuestionIndex + 1,
                    state.value.questions.size
                ),
                fontSize = 20.sp,
                fontWeight = Bold,
                color = MaterialTheme.colorScheme.onSecondary
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "\"${currentQuestion.cita}\"",
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSecondary
            )

            Spacer(modifier = Modifier.height(16.dp))

            Column {
                LazyColumn {
                    items(shuffledOptions) { answer ->
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center // Centra cada botón dentro de su fila
                        ) {
                            Button(
                                onClick = { selectedAnswer = answer },
                                modifier = Modifier.padding(4.dp)
                                                   .width(200.dp), // Opcional: define un ancho fijo para uniformidad
                                colors = ButtonDefaults.buttonColors(
                                    containerColor =
                                        if (selectedAnswer == answer) MaterialTheme.colorScheme.onPrimary
                                        else MaterialTheme.colorScheme.secondary,
                                )
                            ) {
                                Text(text = answer,
                                    textAlign = TextAlign.Center,
                                    color =  if (selectedAnswer == answer) Color.Black
                                            else Color.White)
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(modifier = Modifier.padding(4.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor =  Color(0xFFAB82EE), // MaterialTheme.colorScheme.onPrimary
                        contentColor = Color.Black
                    ),
                    onClick = {
                        if (selectedAnswer == currentQuestion.personajeCorrecto) {
                            correctAnswers++
                        }
                        showDialog = true
                    },
                    enabled = selectedAnswer != null)
            {
                Text(text = stringResource(R.string.check_answer),
                    color = Color.Black)
            }
        }

        if (showDialog) {
            AlertDialog(
                onDismissRequest = {},
                confirmButton = {
                    Button(
                        onClick = {
                            showDialog = false
                            if (currentQuestionIndex < state.value.questions.size - 1) {
                                currentQuestionIndex++
                                selectedAnswer = null
                            } else {
                                navigateToResultQuotes(correctAnswers) // ENVIAR LAS PREGUNTAS ACERTADAS
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.Center)
                    ) {
                        Text(stringResource(R.string.next))
                    }
                },
                title = {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            if (selectedAnswer == currentQuestion.personajeCorrecto) stringResource(R.string.correct)
                                  else stringResource(R.string.wrong),
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.onSecondary
                        )
                    }
                },
                text = {
                    Row {
                        Box(
                            modifier = Modifier
                                .width(150.dp)
                                .height(180.dp),
                            contentAlignment = Alignment.Center  // Centra el contenido en el Box
                        ) {
                            Text(
                                text = stringResource(R.string.the_correct_answer_was) + currentQuestion.personajeCorrecto,
                                modifier = Modifier.padding(horizontal = 10.dp),
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colorScheme.onSecondary
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

//                        Image(
//                            painter = rememberAsyncImagePainter(currentQuestion.imagen.toString()),
//                            contentDescription = stringResource(R.string.character_image),
//                            modifier = Modifier
//                                .width(100.dp)
//                                .height(180.dp),
//                            contentScale = ContentScale.Crop
//                        )

                        val painter = rememberAsyncImagePainter(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(currentQuestion.imagen.toString())
                                .crossfade(true)
                                .build()
                        )

                        val painterState = painter.state

                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .size(150.dp)
                        ) {
                            if (painterState is AsyncImagePainter.State.Loading) {
                                CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                            }

                            Image(
                                painter = painter,
                                contentDescription = stringResource(R.string.imagen_personaje),
                                contentScale = ContentScale.Fit,
                                modifier = Modifier.matchParentSize()
                            )
                        }
                    }
                },
                containerColor = MaterialTheme.colorScheme.secondary
            )
        }
    }
}


@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO, name = "Modo Claro")
@Composable
fun QuotesQuestionScreenPreview() {
    QuotesQuestionScreen(navigateToResultQuotes = {})
}