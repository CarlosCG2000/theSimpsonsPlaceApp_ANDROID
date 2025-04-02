package es.upsa.mimo.thesimpsonplace.presentation.ui.screen.profileSection

import android.content.res.Configuration
import androidx.compose.foundation.Canvas
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import es.upsa.mimo.thesimpsonplace.R
import es.upsa.mimo.thesimpsonplace.presentation.viewmodel.character.charactersList.ListCharactersStateUI
import es.upsa.mimo.thesimpsonplace.presentation.viewmodel.character.charactersList.ListCharactersViewModel
import es.upsa.mimo.thesimpsonplace.presentation.viewmodel.character.charactersListFav.ListCharactersDBViewModel
import es.upsa.mimo.thesimpsonplace.presentation.viewmodel.character.charactersListFav.ListCharactersDbStateUI
import es.upsa.mimo.thesimpsonplace.presentation.viewmodel.episode.episodesList.ListEpisodesStateUI
import es.upsa.mimo.thesimpsonplace.presentation.viewmodel.episode.episodesList.ListEpisodesViewModel
import es.upsa.mimo.thesimpsonplace.presentation.viewmodel.episode.episodesListFav.ListEpisodesDBViewModel
import es.upsa.mimo.thesimpsonplace.presentation.viewmodel.episode.episodesListFav.ListEpisodesDbStateUI
import es.upsa.mimo.thesimpsonplace.presentation.viewmodel.profile.ProfileViewModel
import es.upsa.mimo.thesimpsonplace.presentation.viewmodel.quote.quotesListFav.ListQuotesDBViewModel
import es.upsa.mimo.thesimpsonplace.presentation.viewmodel.quote.quotesListFav.ListQuotesDbStateUI

@Composable
fun ProfileScreen(viewModel: ProfileViewModel = hiltViewModel(),
                  viewModelCharactersDB: ListCharactersDBViewModel  = hiltViewModel(),
                  viewModelEpisodesDB: ListEpisodesDBViewModel = hiltViewModel(),
                  viewModelQuotesDB: ListQuotesDBViewModel = hiltViewModel(),
                  viewModelCharacter: ListCharactersViewModel = hiltViewModel(),
                  viewModelEpisode: ListEpisodesViewModel = hiltViewModel(),
                  onNavigationProfileForm:() -> Unit,
                  navigationArrowBack:() -> Unit) {

    val userPreference by viewModel.userState.collectAsState()

    val stateCharactersDB: State<ListCharactersDbStateUI> = viewModelCharactersDB.stateCharacterFav.collectAsState()
    val stateEpisodesDB: State<ListEpisodesDbStateUI> = viewModelEpisodesDB.stateEpisodesFavOrView.collectAsState()
    val stateQuotesDB: State<ListQuotesDbStateUI> = viewModelQuotesDB.stateQuotesFav.collectAsState()

    val stateCharacters: State<ListCharactersStateUI> = viewModelCharacter.stateCharacter.collectAsState()
    val stateEpisodes: State<ListEpisodesStateUI> = viewModelEpisode.episodesState.collectAsState()

    LaunchedEffect(Unit) {
        if(stateCharacters.value.characters.isEmpty())
            viewModelCharacter.getAllCharacters()

        if(stateEpisodes.value.episodes.isEmpty())
            viewModelEpisode.getAllEpisodes()
    }

    Scaffold(
        topBar = {
            TopBarProfileComponent(
                userName = userPreference.user.username,
                onNavigationProfileForm = onNavigationProfileForm,
                onNavigationArrowBack = navigationArrowBack
            )
        }) { paddingValues ->  // 👈 Recibe el padding generado por Scaffold

        Box(modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
            .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            if (stateCharacters.value.isLoading || stateEpisodes.value.isLoading) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.onPrimary
                    )
            } else {

                Column(
                    modifier = Modifier.fillMaxSize(), // Ocupa toda la pantalla
                    verticalArrangement = Arrangement.Center, // Centra verticalmente dentro de Column
                    horizontalAlignment = Alignment.CenterHorizontally
                ) { // Centra horizontalmente

                    Text("Favorite characters: ${stateCharactersDB.value.charactersSet.size} of ${stateCharacters.value.characters.size} in total")
                    Text(" Favorite episodes: ${stateEpisodesDB.value.episodesSet.size} of ${stateEpisodes.value.episodes.size} in total")
                    Text(" Favorite quotes: ${stateQuotesDB.value.quotesSet.size}")

                    

                    //TopCharactersAndSeasons()

                    // HistoryGameStatistics(totalQuestions = 20, correctAnswers = 5)
                }
            }
        }
    }

}


//____________________ LISTADOS HORIZONTALES________________________
@Composable
fun TopCharactersAndSeasons() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0D1B2A)) // Fondo oscuro similar
            .padding(16.dp)
    ) {
        // 🔹 Top 3 Characters
        Text(
            text = "Characters - Top 3",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(sampleCharacters) { character ->
                CharacterCard(character)
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // 🔹 Top 3 Seasons
        Text(
            text = "Seasons - Top 3",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(sampleSeasons) { season ->
                SeasonCard(season)
            }
        }
    }
}

@Composable
fun CharacterCard(character: Character) {
    Column(
        modifier = Modifier
            .width(150.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFF1B263B)) // Color similar al de la imagen
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(character.imageRes), // Cargar imagen de recursos
            contentDescription = character.name,
            modifier = Modifier.size(100.dp),
            contentScale = ContentScale.Fit
        )
        Text(
            text = character.name,
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

@Composable
fun SeasonCard(season: Season) {
    Column(
        modifier = Modifier
            .width(150.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFF1B263B)) // Color similar al de la imagen
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Season ${season.number}",
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "${season.favoriteEpisodes} favorite episode${if (season.favoriteEpisodes > 1) "s" else ""}",
            color = Color.LightGray,
            fontSize = 14.sp
        )
    }
}

// 🔹 Datos de prueba
data class Character(val name: String, val imageRes: Int)
data class Season(val number: Int, val favoriteEpisodes: Int)

val sampleCharacters = listOf(
    Character("Bart Simpson", R.drawable.bart), // Cambia R.drawable.bart con tus imágenes
    Character("Homer Simpson", R.drawable.homer_simpson),
    Character("Apu Nahasapeemapetilon", R.drawable.apu_nahasapeemapetilon),
)

val sampleSeasons = listOf(
    Season(1, 4),
    Season(30, 2),
    Season(12, 1),
)

//_____________________ GRAFICA _______________________
@Composable
fun HistoryGameStatistics(totalQuestions: Int, correctAnswers: Int) {
    val successPercentage = (correctAnswers.toFloat() / totalQuestions) * 100
    val failurePercentage = 100 - successPercentage

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF3B4D8A)) // Color similar al fondo de la imagen
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 🔹 Botón para resetear historial
        Button(
            onClick = { /* Acción para resetear */ },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFC107)), // Color amarillo
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            Text(text = "Reset History", color = Color.Black, fontWeight = FontWeight.Bold)
        }

        // 🔹 Título
        Text(
            text = "History Game Statistics",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 🔹 Gráfico circular
        PieChart(
            successPercentage = successPercentage,
            failurePercentage = failurePercentage
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 🔹 Leyenda de estadísticas
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Circle,
                contentDescription = "Success",
                tint = Color.Green,
                modifier = Modifier.size(12.dp)
            )
            Text(
                text = " Successes - ${"%.1f".format(successPercentage)}%",
                color = Color.White,
                fontSize = 16.sp,
                modifier = Modifier.padding(start = 4.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Icon(
                imageVector = Icons.Default.Circle,
                contentDescription = "Failures",
                tint = Color.Red,
                modifier = Modifier.size(12.dp)
            )
            Text(
                text = " Failures - ${"%.1f".format(failurePercentage)}%",
                color = Color.White,
                fontSize = 16.sp,
                modifier = Modifier.padding(start = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 🔹 Resumen de respuestas correctas
        Text(
            text = "Correct Answers: $correctAnswers of $totalQuestions",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}

@Composable
fun PieChart(successPercentage: Float, failurePercentage: Float) {
    val pieEntries = listOf(
        PieChartData(successPercentage, Color.Green),
        PieChartData(failurePercentage, Color.Red)
    )

    Box(
        modifier = Modifier
            .size(200.dp)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.size(180.dp)) {
            val totalAngle = 360f
            val strokeWidth = 150f  // Grosor del círculo
            var startAngle = -90f   // Empezamos desde arriba
            pieEntries.forEach { entry ->
                val adjustedSweepAngle = ((entry.percentage / 100) * totalAngle)
                drawArc(
                    color = entry.color,
                    startAngle = startAngle,
                    sweepAngle = adjustedSweepAngle,
                    useCenter = false,
                    size = size,
                    style = Stroke(width = strokeWidth, cap = StrokeCap.Butt)
                )
                startAngle += adjustedSweepAngle // sweepAngle // 🔹 Avanzamos al siguiente segmento
            }
        }
    }
}

// 🔹 Modelo de datos para la gráfica
data class PieChartData(val percentage: Float, val color: Color)
//____________________________________________

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO, name = "Modo Claro")
@Composable
fun ProfileScreenPreview() {
    ProfileScreen(onNavigationProfileForm = {}, navigationArrowBack = {})
}