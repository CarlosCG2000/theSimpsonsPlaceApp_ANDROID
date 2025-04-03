package es.upsa.mimo.thesimpsonplace.presentation.ui.screen.profileSection

import android.content.res.Configuration
import android.graphics.Color.alpha
import android.util.Log
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
import androidx.compose.material3.Card
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.font.FontWeight.Companion.SemiBold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import es.upsa.mimo.thesimpsonplace.R
import es.upsa.mimo.thesimpsonplace.domain.models.Character
import es.upsa.mimo.thesimpsonplace.presentation.viewmodel.character.charactersList.ListCharactersStateUI
import es.upsa.mimo.thesimpsonplace.presentation.viewmodel.character.charactersList.ListCharactersViewModel
import es.upsa.mimo.thesimpsonplace.presentation.viewmodel.character.charactersListFav.ListCharactersDBViewModel
import es.upsa.mimo.thesimpsonplace.presentation.viewmodel.character.charactersListFav.ListCharactersDbStateUI
import es.upsa.mimo.thesimpsonplace.presentation.viewmodel.episode.episodesList.ListEpisodesStateUI
import es.upsa.mimo.thesimpsonplace.presentation.viewmodel.episode.episodesList.ListEpisodesViewModel
import es.upsa.mimo.thesimpsonplace.presentation.viewmodel.episode.episodesListFav.ListEpisodesDBViewModel
import es.upsa.mimo.thesimpsonplace.presentation.viewmodel.episode.episodesListFav.ListEpisodesDbStateUI
import es.upsa.mimo.thesimpsonplace.presentation.viewmodel.profile.ProfileViewModel
import es.upsa.mimo.thesimpsonplace.presentation.viewmodel.quote.quotesGame.resultGame.ResultGameUI
import es.upsa.mimo.thesimpsonplace.presentation.viewmodel.quote.quotesGame.resultGame.ResultGameViewModel
import es.upsa.mimo.thesimpsonplace.presentation.viewmodel.quote.quotesListFav.ListQuotesDBViewModel
import es.upsa.mimo.thesimpsonplace.presentation.viewmodel.quote.quotesListFav.ListQuotesDbStateUI
import java.util.Locale

@Composable
fun ProfileScreen(viewModel: ProfileViewModel = hiltViewModel(),
                  viewModelCharactersDB: ListCharactersDBViewModel  = hiltViewModel(),
                  viewModelEpisodesDB: ListEpisodesDBViewModel = hiltViewModel(),
                  viewModelQuotesDB: ListQuotesDBViewModel = hiltViewModel(),
                  viewModelCharacter: ListCharactersViewModel = hiltViewModel(),
                  viewModelEpisode: ListEpisodesViewModel = hiltViewModel(),
                  viewModelGane: ResultGameViewModel = hiltViewModel(),
                  onNavigationProfileForm:() -> Unit,
                  navigationArrowBack:() -> Unit) {

    val userPreference by viewModel.userState.collectAsState()

    val stateCharactersDB: State<ListCharactersDbStateUI> = viewModelCharactersDB.stateCharacterFav.collectAsState()
    val stateEpisodesDB: State<ListEpisodesDbStateUI> = viewModelEpisodesDB.stateEpisodesFavOrView.collectAsState()
    val stateQuotesDB: State<ListQuotesDbStateUI> = viewModelQuotesDB.stateQuotesFav.collectAsState()

    val stateCharacters: State<ListCharactersStateUI> = viewModelCharacter.stateCharacter.collectAsState()
    val stateEpisodes: State<ListEpisodesStateUI> = viewModelEpisode.episodesState.collectAsState()

    val gameStats: State<ResultGameUI> = viewModelGane.gameStats.collectAsState()


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

                    Spacer(modifier = Modifier.height(10.dp))

                    textoPrincipal(
                        text = stringResource(
                            R.string.favorite_characters_of_in_total,
                            stateCharactersDB.value.charactersSet.size,
                            stateCharacters.value.characters.size
                        )
                    )

                    textoPrincipal(text = stringResource(
                        R.string.favorite_episodes_of_in_total,
                        stateEpisodesDB.value.episodesFavSet.size,
                        stateEpisodes.value.episodes.size
                    ))

                    textoPrincipal(text = stringResource(
                        R.string.favorite_quotes,
                        stateQuotesDB.value.quotesSet.size
                    ))

                    stateCharactersDB.value.characters // Listado de personajes favoritas// nombre: String
                    stateQuotesDB.value.quotes // Listado de citas favoritas //  personaje: String

                    val quoteCounts = stateQuotesDB.value.quotes
                        .groupingBy { it.personaje }  // Agrupar por personaje
                        .eachCount()                   // Contar ocurrencias

                    // Paso 2: Filtrar solo los personajes favoritos y mapear con el conteo de citas
                    val topCharacters = stateCharactersDB.value.characters
                        .map { character ->
                            character to (quoteCounts[character.nombre] ?: 0) // Obtener cuántas citas tiene
                        }
                        .sortedByDescending { it.second } // Paso 3: Ordenar por cantidad de citas
                        .take(3) // Tomar los 3 primeros
                        .map { it.first }

                    // Obtener las 3 temporadas con mas episodios favoritos.
                    val topSeasons = stateEpisodesDB.value.episodesFav
                        .groupingBy { it.temporada }  // 🔹 Agrupar por temporada
                        .eachCount()  // 🔹 Contar cuántos episodios favoritos tiene cada temporada
                        .entries
                        .sortedByDescending { it.value }  // 🔹 Ordenar por cantidad de episodios favoritos
                        .take(3)  // 🔹 Tomar las 3 temporadas con más episodios favoritos
                        .map { it.key to it.value }  // 🔹 Obtener el nombre de la temporada y el número de episodios favoritos

                    TopCharactersAndSeasons(top3Characters = topCharacters, top3Seasons = topSeasons)

                    HistoryGameStatistics(totalQuestions = gameStats.value.result.second,
                                        correctAnswers = gameStats.value.result.first,
                                        size = 125.dp)
                }
            }
        }
    }

}

@Composable
fun textoPrincipal(text: String) {
    Text(
        text = text,
        fontWeight = SemiBold,
        fontSize = 20.sp, // 🔹 Usa `sp` en lugar de `dp` para fuentes
        color = Color.White, // MaterialTheme.colorScheme.onSecondary,
        textAlign = TextAlign.Start, // 🔹 Centra el texto horizontalmente
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                vertical = 8.dp,
                horizontal = 16.dp
            ) // 🔹 Añade espacio alrededor para mejor visualización
    )
}

//____________________ LISTADOS HORIZONTALES TOP________________________
@Composable
fun TopCharactersAndSeasons(top3Characters: List<Character>, top3Seasons: List<Pair<Int, Int>>) {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        // 🔹 Top 3 Characters
        Text(
            text = stringResource(R.string.characters_top_3),
            fontSize = 22.sp,
            fontWeight = Bold,
            color = Color.White,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(top3Characters) { character ->
                CharacterCard(character)
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // 🔹 Top 3 Seasons
        Text(
            text = stringResource(R.string.seasons_top_3),
            fontSize = 22.sp,
            fontWeight = Bold,
            color = Color.White,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(top3Seasons) { season ->
                SeasonCard(season)
            }
        }
    }
}

@Composable
fun CharacterCard(character: Character) {
    val context = LocalContext.current

    val imageResId = remember(character.imagen) {
        val id = context.resources.getIdentifier( // ⚠️ getIdentifier, esta deprecado pero aún funciona y sigue siendo la única opción dinámica.
            character.imagen?.lowercase(),
            "drawable",
            context.packageName
        )
        if (id == 0) R.drawable.not_specified else id
    }

    Column(
        modifier = Modifier
            .height(180.dp)
            .width(190.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.secondary) // Color similar al de la imagen
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(imageResId), // Cargar imagen de recursos
            contentDescription = character.nombre,
            modifier = Modifier.size(100.dp),
            contentScale = ContentScale.Fit
        )
        Text(
            text = character.nombre,
            color =  MaterialTheme.colorScheme.onSecondaryContainer,
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            fontWeight = Bold,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

@Composable
fun SeasonCard(season: Pair<Int, Int>) {
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.secondary) // Color similar al de la imagen
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.season, season.first),
            color = MaterialTheme.colorScheme.onSecondary,
            fontSize = 16.sp,
            fontWeight = Bold
        )
        Text(
            text = stringResource(
                R.string.favorite_episode,
                season.second,
                if (season.second > 1) "s" else ""
            ),
            color = MaterialTheme.colorScheme.onSecondary,
            fontSize = 14.sp
        )
    }
}

//_____________________ GRAFICA _______________________
@Composable
fun HistoryGameStatistics(totalQuestions: Int, correctAnswers: Int, size: Dp, paddingText: Dp = 0.dp) {
    val successPercentage = ((correctAnswers.toFloat() / totalQuestions) * 100).let {
        String.format(Locale.US, "%.2f", it).toFloat()
    }
    val failurePercentage = 100 - successPercentage

    Column(
        modifier = Modifier
            .padding(12.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.secondary) // Color similar al de la imagen
                            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
            // 🔹 Título
            Text(
                text = stringResource(R.string.history_game_statistics),
                fontSize = 20.sp,
                fontWeight = Bold,
                color = MaterialTheme.colorScheme.onSecondary,
                modifier = Modifier.padding(bottom = paddingText)
            )

            Spacer(modifier = Modifier.height(12.dp))

            // 🔹 Gráfico circular
            PieChart(
                successPercentage = successPercentage,
                failurePercentage = failurePercentage,
                canvasSize = size
            )

            Spacer(modifier = Modifier.height(12.dp))

            // 🔹 Leyenda de estadísticas
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Circle,
                    contentDescription = stringResource(R.string.success),
                    tint = Color.Green,
                    modifier = Modifier.size(12.dp)
                )
                Text(
                    text = stringResource(R.string.success)  + " $successPercentage %",
                    color = MaterialTheme.colorScheme.onSecondary,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(start = 4.dp)
                )

                Spacer(modifier = Modifier.width(16.dp))

                Icon(
                    imageVector = Icons.Default.Circle,
                    contentDescription = stringResource(R.string.failures),
                    tint = Color.Red,
                    modifier = Modifier.size(12.dp)
                )
                Text(
                    text = stringResource(  R.string.failures) + " $failurePercentage %",
                    color = MaterialTheme.colorScheme.onSecondary,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(start = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // 🔹 Resumen de respuestas correctas
            Text(
                text = stringResource(R.string.correct_answers_of, correctAnswers, totalQuestions),
                fontSize = 18.sp,
                fontWeight = Bold,
                color = MaterialTheme.colorScheme.onSecondary
            )
        }

}

@Composable
fun PieChart(successPercentage: Float, failurePercentage: Float, canvasSize: Dp) {
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
        Canvas(modifier = Modifier.size(canvasSize)) {
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