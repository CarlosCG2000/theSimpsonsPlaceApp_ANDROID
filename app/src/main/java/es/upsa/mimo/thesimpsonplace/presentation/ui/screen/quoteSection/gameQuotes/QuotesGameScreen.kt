package es.upsa.mimo.thesimpsonplace.presentation.ui.screen.quoteSection.gameQuotes

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import es.upsa.mimo.thesimpsonplace.R
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.SemiBold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import es.upsa.mimo.thesimpsonplace.presentation.ui.component.TopBarComponent
import es.upsa.mimo.thesimpsonplace.presentation.ui.component.quote.BottomBarQuoteComponent
import es.upsa.mimo.thesimpsonplace.presentation.ui.component.quote.BottomNavQuotesItem

@Composable
fun QuotesGameScreen(
    navigateToQuotes: () -> Unit,
    navigateToFilterQuotes: () -> Unit,
    navigateToFavoriteQuotes: () -> Unit,
    navigateToQuestionQuotes: () -> Unit,
    navigationArrowBack:() -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }

    Scaffold(
        bottomBar = {
            BottomBarQuoteComponent(
                selectedBarButtom = BottomNavQuotesItem.GAME,
                navigateToQuotes = navigateToQuotes,
                navigateToFiltersQuotes = navigateToFilterQuotes,
                navigateToFavoritesQuotes = navigateToFavoriteQuotes,
                navigateToGameQuotes = { }
            )
        },
        topBar = {
            TopBarComponent(
                title = stringResource(R.string.juego_de_citas),
                onNavigationArrowBack = navigationArrowBack
            )
        }

    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.primary),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.background_all_characters),
                contentDescription = stringResource(R.string.fondo_juego_de_los_simpsons),
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer(alpha = 0.7f),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier
                    .background(
                        color = Color(0xCC0F1A35), // Mantener este color es el fondo del circulo
                        shape = CircleShape
                    )
                    .padding(24.dp)
                    .size(300.dp), // Ajusta el tamaño del fondo redondeado
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Text(
                    text = stringResource(R.string.quiz_game),
                    color = Color(0xFFFFC107),
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = stringResource(R.string.detalles_del_juego),
                    color = Color.White,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { showDialog = true },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.onPrimary)
                ) {
                    Text(text = stringResource(R.string.start_quiz), color = Color.Black, fontWeight = FontWeight.Bold)
                }
            }
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
                                navigateToQuestionQuotes()
                          },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFC107))
                        ) {
                            Text(stringResource(R.string.start), color = Color.Black)
                        }
                        Button(
                            onClick = { showDialog = false },
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                        ) {
                            Text(stringResource(R.string.cancel), color = Color.Black)
                        }
                    }
                })
            /**
            confirmButton = {
                Button(
                    onClick = { /* Acción para iniciar el quiz */ },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFC107))
                ) {
                    Text("Start", color = Color.Black)
                }
            },
            dismissButton = {
                Button(
                    onClick = { showDialog = false },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                ) {
                    Text("Cancel", color = Color.White)
                }
            }
            */
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO, name = "Modo Claro")
@Composable
fun QuotesGameScreenPreview() {
    QuotesGameScreen({}, {}, {}, {},{})
}