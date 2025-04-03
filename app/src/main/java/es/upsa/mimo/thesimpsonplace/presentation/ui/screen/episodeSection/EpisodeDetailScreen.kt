package es.upsa.mimo.thesimpsonplace.presentation.ui.screen.episodeSection

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
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.MotionScene
import androidx.hilt.navigation.compose.hiltViewModel
import es.upsa.mimo.thesimpsonplace.R
import es.upsa.mimo.thesimpsonplace.domain.models.Episode
import es.upsa.mimo.thesimpsonplace.presentation.ui.component.ModifierContainer
import es.upsa.mimo.thesimpsonplace.presentation.ui.component.TopBarComponent
import es.upsa.mimo.thesimpsonplace.presentation.viewmodel.episode.episodeDetails.DetailsEpisodeStateUI
import es.upsa.mimo.thesimpsonplace.presentation.viewmodel.episode.episodeDetails.DetailsEpisodeViewModel
import es.upsa.mimo.thesimpsonplace.presentation.viewmodel.episode.episodesListFav.ListEpisodesDBViewModel
import es.upsa.mimo.thesimpsonplace.presentation.viewmodel.episode.episodesListFav.ListEpisodesDbStateUI
import es.upsa.mimo.thesimpsonplace.utils.toFormattedString

@Composable
fun EpisodeDetailScreen(viewModel: DetailsEpisodeViewModel = hiltViewModel(),
                        id: String,
                        navigationArrowBack:() -> Unit) {

    val state: State<DetailsEpisodeStateUI> = viewModel.episodeState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getEpisodeById(id)
    }

    Scaffold(
        topBar = {
            TopBarComponent(
                title = "${stringResource(R.string.episodio)} ${state.value.episode?.episodio} - ${stringResource(R.string.temporada)}  ${state.value.episode?.temporada}",
                onNavigationArrowBack = navigationArrowBack
            )
        }
    )
    { paddingValues ->
        Box(
            contentAlignment = Alignment.Center, // ✅ Asegura que el spinner esté centrado
            modifier = ModifierContainer(paddingValues)
        ) {
            if(state.value.isLoading) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.onPrimary
                )
            } else {
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
        modifier = Modifier.fillMaxSize() // Ocupa toda la pantalla
                            .background(MaterialTheme.colorScheme.primary),
        verticalArrangement = Arrangement.Center, // Centra verticalmente dentro de Column
        horizontalAlignment = Alignment.CenterHorizontally
    ) { // Centra horizontalmente
        // Título del episodio
        Text(
            text = episode.titulo,
            fontSize = 24.sp,
            fontWeight = Bold,
            color = MaterialTheme.colorScheme.onPrimary,
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Descripción del episodio
        Text(
            text = episode.descripcion,
            fontSize = 18.sp,
            color =  MaterialTheme.colorScheme.onSecondary,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 24.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Fecha de lanzamiento
        Text(
            text = "${stringResource(R.string.lanzamiento)} ${episode.lanzamiento.toFormattedString()}",
            fontSize = 20.sp,
            color = MaterialTheme.colorScheme.onSecondary
        )

        Spacer(modifier = Modifier.height(36.dp))

        // Fila para Favorito y Visto
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
                                .padding(horizontal = 32.dp)
        ) {
            // Favorito
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.Favorito), fontSize = 18.sp, fontWeight = Bold,
                    color = MaterialTheme.colorScheme.onSecondary,
                    textDecoration = if (isFavorite == true) TextDecoration.None else TextDecoration.LineThrough
                )

                Spacer(modifier = Modifier.width(8.dp))

                Switch(
                    checked = isFavorite == true,
                    onCheckedChange = {
                        viewModelDB.toggleFavoriteOrView(episode = episode, fav = !isFavorite, view = isView)
                    },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = MaterialTheme.colorScheme.onPrimary,
                        checkedTrackColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.2f) // Fondo amarillo con transparencia
                    )
                )
            }

            // Visto (Icono de Ojo)
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(stringResource(R.string.vista), fontSize = 18.sp, fontWeight = Bold,
                    color = MaterialTheme.colorScheme.onSecondary)

                Spacer(modifier = Modifier.width(8.dp))

                IconButton(onClick = {
                    viewModelDB.toggleFavoriteOrView(episode = episode, fav = isFavorite == true, view = !isView)
                }) {
                    Icon(
                        imageVector = if (isView == true) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                        contentDescription = stringResource(R.string.vista),
                        modifier = Modifier.size(50.dp),
                        tint = if (isView == true) MaterialTheme.colorScheme.onPrimary else Color.Red
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Secciones de escritores, directores e invitados
        SectionCard(stringResource(R.string.escritores), episode.escritores,
                    MaterialTheme.colorScheme.onPrimary, emptyMessage = stringResource(R.string.ning_n_escritor))

        SectionCard(stringResource(R.string.directores), episode.directores,
                    MaterialTheme.colorScheme.onPrimary, emptyMessage = stringResource( R.string.ning_n_director))

        SectionCard(stringResource(R.string.invitados), episode.invitados,
                    MaterialTheme.colorScheme.onPrimary, emptyMessage = stringResource(R.string.ningun_invitado_famoso))

        Spacer(modifier = Modifier.height(16.dp))

        // Icono de recomendación
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text =  if (episode.valoracion == true) stringResource(R.string.recomendado)
                        else stringResource(R.string.no_recomendado),
                color = MaterialTheme.colorScheme.onSecondary,
                fontSize = 16.sp
            )
            Spacer(modifier = Modifier.width(12.dp)) // Espaciado entre el icono y el texto

            Icon(
                imageVector = if (episode.valoracion == true) Icons.Filled.ThumbUp else Icons.Filled.ThumbDown,
                contentDescription = if (episode.valoracion == true) stringResource(R.string.recomendado)
                else stringResource(R.string.no_recomendado),
                tint = MaterialTheme.colorScheme.onSecondary
            )
        }

    }
}

// Composable para cada sección
@Composable
fun SectionCard(title: String,
                items: List<String>,
                titleColor: Color,
                emptyMessage: String = stringResource(R.string.sin_datos)) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondary)
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
                        Text(modifier = Modifier.padding(end = 16.dp), text = "• $item", color = MaterialTheme.colorScheme.onSecondary )
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