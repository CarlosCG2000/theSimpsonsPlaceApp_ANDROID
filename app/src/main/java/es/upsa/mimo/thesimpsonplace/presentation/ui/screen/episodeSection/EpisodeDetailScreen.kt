package es.upsa.mimo.thesimpsonplace.presentation.ui.screen.episodeSection

import android.content.res.Configuration
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ThumbDown
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.IconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import es.upsa.mimo.thesimpsonplace.domain.entities.Episode
import es.upsa.mimo.thesimpsonplace.presentation.ui.component.TopBarComponent
import es.upsa.mimo.thesimpsonplace.presentation.viewmodel.episode.episodeDetails.DetailsEpisodeStateUI
import es.upsa.mimo.thesimpsonplace.presentation.viewmodel.episode.episodeDetails.DetailsEpisodeViewModel
import es.upsa.mimo.thesimpsonplace.presentation.viewmodel.episode.episodesListFav.ListEpisodesDBViewModel
import es.upsa.mimo.thesimpsonplace.presentation.viewmodel.episode.episodesListFav.ListEpisodesDbStateUI
import es.upsa.mimo.thesimpsonplace.utils.toFormattedString

@Composable
fun EpisodeDetailScreen(
    viewModel: DetailsEpisodeViewModel = hiltViewModel(),// viewModel(factory = DetailsEpisodeViewModel.factory()),
    id: String,
    navigationArrowBack:() -> Unit) {

    val state: State<DetailsEpisodeStateUI> = viewModel.episodeState.collectAsState() // lo hago sincrono para usarlo en la pantalla

    LaunchedEffect(Unit) {
        viewModel.getEpisodeById(id)
    }

    Scaffold(
        topBar = {
            TopBarComponent(
                title = "Episodio ${state.value.episode?.episodio} - Temporada  ${state.value.episode?.temporada}",
                onNavigationArrowBack = navigationArrowBack
            )
        }
    )
    { paddingValues ->
        Box(
            contentAlignment = Alignment.Center, // ✅ Asegura que el spinner esté centrado
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                // .background(Color.White) // ✅ Fondo blanco para mejor visibilidad,
        ) {
            if(state.value.isLoading) {
                CircularProgressIndicator(
                    color = Color.Yellow // ✅ Cambia el color del spinner a amarillo
                )
            } else {
               // CharacterDetails( state.value.episode )
                state.value.episode?.let { episode ->
                    CharacterDetails(episode)
                }
            }
        }
    }
}

@Composable
fun CharacterDetails(episode: Episode,
                     viewModelDB: ListEpisodesDBViewModel = hiltViewModel()) {

    val stateFavOrView: State<ListEpisodesDbStateUI> = viewModelDB.stateEpisodesFavOrView.collectAsState()

    // Verificar si el episodio es favorito o visto en la BD
    val isFavorite = stateFavOrView.value.episodesFavSet.contains(episode.id)
    val isView = stateFavOrView.value.episodesViewSet.contains(episode.id)

    Column(
        modifier = Modifier.fillMaxSize(), // Ocupa toda la pantalla
        verticalArrangement = Arrangement.Center, // Centra verticalmente dentro de Column
        horizontalAlignment = Alignment.CenterHorizontally
    ) { // Centra horizontalmente
        // Título del episodio
        Text(
            text = episode?.titulo ?: "No titulo",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFFFC107) // Amarillo
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Descripción del episodio
        Text(
            text = episode?.descripcion ?: "No descripción",
            fontSize = 16.sp,
            color = Color.White,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 24.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Fecha de lanzamiento
        Text(
            text = "Release date: ${episode?.lanzamiento?.toFormattedString()}",
            fontSize = 18.sp,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Fila para Favorito y Visto
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp)
        ) {
            // Favorito
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Favorite",
                    color = Color.White,
                    textDecoration = if (isFavorite == true) TextDecoration.None else TextDecoration.LineThrough
                )

                Spacer(modifier = Modifier.width(8.dp))

                Switch(
                    checked = isFavorite == true,
                    onCheckedChange = {
                        viewModelDB.toggleFavoriteOrView(episode = episode, fav = !isFavorite, view = isView)
                    },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color.Yellow,
                        checkedTrackColor = Color.Yellow.copy(alpha = 0.2f) // Fondo amarillo con transparencia
                    )
                )
            }

            // Visto (Icono de Ojo)
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Vista", color = Color.White)

                Spacer(modifier = Modifier.width(8.dp))

                IconButton(onClick = {
                    viewModelDB.toggleFavoriteOrView(episode = episode, fav = isFavorite == true, view = !isView)
                }) {
                    Icon(
                        imageVector = if (isView == true) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                        contentDescription = "Visto",
                        Modifier.size(50.dp),
                        tint = if (isView == true) Color.Yellow else Color.Red
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Secciones de escritores, directores e invitados
        SectionCard("Escritores", episode?.escritores ?: emptyList(), Color(0xFFFFC107), emptyMessage = "Ningún escritor")
        SectionCard("Directores", episode?.directores ?: emptyList(), Color(0xFFFFC107), emptyMessage = "Ningún director")
        SectionCard("Invitados", episode?.invitados ?: emptyList(), Color(0xFFFFC107) /*Color.Red*/, emptyMessage = "Ningun invitado famoso")

        Spacer(modifier = Modifier.height(16.dp))

        // Icono de recomendación
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = if (episode?.valoracion == true) "Recomendado" else "No recomendado",
                color = Color.White,
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.width(12.dp)) // Espaciado entre el icono y el texto

            Icon(
                imageVector = if (episode?.valoracion == true) Icons.Filled.ThumbUp else Icons.Filled.ThumbDown,
                contentDescription = if (episode?.valoracion == true) "Recomendado" else "No recomendado",
                tint = Color.White
            )
        }

    }
}

// Composable para cada sección
@Composable
fun SectionCard(title: String, items: List<String>, titleColor: Color, emptyMessage: String = "No data") {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF2C3E72))
    ) {
        Column( modifier = Modifier.padding(16.dp)) {
            Text(
                text = title,
                fontSize = 18.sp,
                fontWeight = Bold,
                color = titleColor
            )
            Spacer(modifier = Modifier.height(8.dp))
            if (items.isEmpty()) {
                Text(text = emptyMessage, color = Color.Red)
            } else {
                LazyRow(
                    modifier = Modifier.fillMaxWidth(), //.horizontalScroll(rememberScrollState()),
                    contentPadding = PaddingValues(horizontal = 8.dp)
                ) {
                    // items.forEach { item ->
                    items(items){item ->
                        Text(modifier = Modifier.padding(end = 16.dp), text = "• $item", color = Color.White)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO, name = "Modo Claro")
@Composable
fun EpisodeDetailScreenPreview() {
        EpisodeDetailScreen(id = "1"){}
}