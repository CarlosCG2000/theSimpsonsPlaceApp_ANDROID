package es.upsa.mimo.thesimpsonplace.presentation.ui.screens.quoteSection.gameQuotes

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import es.upsa.mimo.thesimpsonplace.presentation.viewmodel.quote.quotesGame.questionGame.QuotesGameUI
import es.upsa.mimo.thesimpsonplace.presentation.viewmodel.quote.quotesGame.questionGame.QuotesGameViewModel
import kotlin.String

//val questions: List<Question> = listOf(
//    Question(
//        cita = "Cita 1",
//        personajeCorrecto = "Homer",
//        imagen = URL("https://cdn.glitch.com/3c3ffadc-3406-4440-bb95-d40ec8fcde72%2FHomerSimpson.png?1497567511939"),
//        personajeIncorrectos = listOf("Apu", "Marge", "Nelson")
//    ),
//    Question(
//        cita = "Cita 2",
//        personajeCorrecto = "Homer",
//        imagen = URL("https://cdn.glitch.com/3c3ffadc-3406-4440-bb95-d40ec8fcde72%2FHomerSimpson.png?1497567511939"),
//        personajeIncorrectos = listOf("Apu", "Marge", "Nelson")
//    ),
//    Question(
//        cita = "Cita 3",
//        personajeCorrecto = "Homer",
//        imagen = URL("https://cdn.glitch.com/3c3ffadc-3406-4440-bb95-d40ec8fcde72%2FHomerSimpson.png?1497567511939"),
//        personajeIncorrectos = listOf("Apu", "Marge", "Nelson")
//    ),
//    Question(
//        cita = "Cita 4",
//        personajeCorrecto = "Homer",
//        imagen = URL("https://cdn.glitch.com/3c3ffadc-3406-4440-bb95-d40ec8fcde72%2FHomerSimpson.png?1497567511939"),
//        personajeIncorrectos = listOf("Apu", "Marge", "Nelson")
//    ),
//    Question(
//        cita = "Cita 5",
//        personajeCorrecto = "Homer",
//        imagen = URL("https://cdn.glitch.com/3c3ffadc-3406-4440-bb95-d40ec8fcde72%2FHomerSimpson.png?1497567511939"),
//        personajeIncorrectos = listOf("Apu", "Marge", "Nelson")
//    ),
//)

@Composable
fun QuotesQuestionScreen(
    viewModel: QuotesGameViewModel = hiltViewModel(),
    navigateToResultQuotes: (Int) -> Unit
) {
    val state: State<QuotesGameUI> = viewModel.stateQuestions.collectAsState() // sincrono para manejarlo en la UI

    var currentQuestionIndex by remember { mutableStateOf(0) }
    var selectedAnswer by remember { mutableStateOf<String?>(null) }
    var showDialog by remember { mutableStateOf(false) }
    var correctAnswers by remember { mutableStateOf(0) }

//    if (questions.isEmpty()) {
//        viewModel.getQuestions()
//        return
//    }

    // ✅ Ejecutar getQuestions() solo una vez al iniciar
    LaunchedEffect(Unit) {
        if (state.value.questions.isEmpty()) {
            viewModel.getQuestions()
        }
    }

    // Si está cargando, mostrar un indicador
    if (state.value.isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    // Si no hay preguntas después de cargar, mostrar un mensaje de error
    if (state.value.questions.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("No questions available.", color = Color.White)
        }
        return
    }

    val currentQuestion = state.value.questions[currentQuestionIndex]

    // Se guarda el orden aleatorio de respuestas solo una vez por pregunta
    val shuffledOptions by remember(currentQuestion) {
        mutableStateOf((currentQuestion.personajeIncorrectos + currentQuestion.personajeCorrecto).shuffled())
    }

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Quote ${currentQuestionIndex + 1} of ${state.value.questions.size}",
                fontSize = 20.sp,
                fontWeight = Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(text = "\"${currentQuestion.cita}\"", fontSize = 18.sp, fontWeight = FontWeight.Medium)

            Spacer(modifier = Modifier.height(16.dp))

            Column {
                LazyColumn  {
                    items(shuffledOptions) {answer ->
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center // Centra cada botón dentro de su fila
                        ) {
                            Button(
                                onClick = { selectedAnswer = answer },
                                modifier = Modifier
                                    .padding(4.dp)
                                    .width(200.dp), // Opcional: define un ancho fijo para uniformidad
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (selectedAnswer == answer)
                                        MaterialTheme.colorScheme.primary
                                    else
                                        MaterialTheme.colorScheme.secondaryContainer
                                )
                            ) {
                                Text(text = answer)
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (selectedAnswer == currentQuestion.personajeCorrecto) {
                        correctAnswers++
                    }
                    showDialog = true
                },
                enabled = selectedAnswer != null
            ) {
                Text("Check Answer")
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
                        Text("Next")
                    }
                },
                title = {
                    Box(   modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center) {
                        Text(if (selectedAnswer == currentQuestion.personajeCorrecto) "Correct!" else "Wrong!",
                            textAlign = TextAlign.Center)
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
                                text = "The correct answer was ${currentQuestion.personajeCorrecto}",
                                modifier = Modifier.padding(horizontal = 10.dp),
                                textAlign = TextAlign.Center
                            )
                        }
//                        Text("The correct answer was ${currentQuestion.personajeCorrecto}",
//                            modifier = Modifier.padding(horizontal = 10.dp).width(150.dp).height(180.dp), textAlign = TextAlign.Center)

                        Spacer(modifier = Modifier.height(8.dp))

                        Image(
                            painter = rememberAsyncImagePainter(currentQuestion.imagen.toString()),
                            contentDescription = "Character Image",
                            modifier = Modifier
                                .width(100.dp)
                                .height(180.dp),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            )
        }
    }
}


@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO, name = "Modo Claro")
@Composable
fun QuotesQuestionScreenPreview() {
    QuotesQuestionScreen(navigateToResultQuotes = {})
}