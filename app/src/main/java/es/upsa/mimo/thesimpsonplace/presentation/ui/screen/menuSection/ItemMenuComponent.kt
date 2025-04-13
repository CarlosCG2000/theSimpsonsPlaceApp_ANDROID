package es.upsa.mimo.thesimpsonplace.presentation.ui.screen.menuSection

import android.content.res.Configuration
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.FormatQuote
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Tv
import androidx.compose.material.icons.filled.Upgrade
import androidx.compose.material3.Badge
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.font.FontWeight.Companion.SemiBold
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import es.upsa.mimo.thesimpsonplace.R
import es.upsa.mimo.thesimpsonplace.presentation.ui.screen.menuSection.NavDrawerItemComponent
import es.upsa.mimo.thesimpsonplace.presentation.viewmodel.character.charactersList.ListCharactersViewModel
import es.upsa.mimo.thesimpsonplace.presentation.viewmodel.character.charactersListFav.ListCharactersDBViewModel
import es.upsa.mimo.thesimpsonplace.presentation.viewmodel.character.charactersListFav.ListCharactersDbStateUI
import es.upsa.mimo.thesimpsonplace.presentation.viewmodel.episode.episodesList.ListEpisodesViewModel
import es.upsa.mimo.thesimpsonplace.presentation.viewmodel.episode.episodesListFav.ListEpisodesDBViewModel
import es.upsa.mimo.thesimpsonplace.presentation.viewmodel.episode.episodesListFav.ListEpisodesDbStateUI
import es.upsa.mimo.thesimpsonplace.presentation.viewmodel.quote.quotesListFav.ListQuotesDBViewModel
import es.upsa.mimo.thesimpsonplace.presentation.viewmodel.quote.quotesListFav.ListQuotesDbStateUI

@Composable
fun ItemMenuComponent(  viewModelCharactersDB: ListCharactersDBViewModel  = hiltViewModel(),
                        viewModelEpisodesDB: ListEpisodesDBViewModel = hiltViewModel(),
                        viewModelQuotesDB: ListQuotesDBViewModel = hiltViewModel(),
                        navigateToCharacters: () -> Unit,
                        navigateToEpisodes: () -> Unit,
                        navigateToQuotes: () -> Unit) {

    val stateCharacters: State<ListCharactersDbStateUI> = viewModelCharactersDB.stateCharacterFav.collectAsState()
    val stateEpisodes: State<ListEpisodesDbStateUI> = viewModelEpisodesDB.stateEpisodesFavOrView.collectAsState()
    val stateQuotes: State<ListQuotesDbStateUI> = viewModelQuotesDB.stateQuotesFav.collectAsState()

    Column( modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.primary),
                horizontalAlignment = Alignment.CenterHorizontally) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .background(MaterialTheme.colorScheme.secondary.copy(alpha = 0.15f)),
            contentAlignment = Alignment.Center // 🔹 Centra el texto dentro del Box
        ) {
            Text(
                text = stringResource(R.string.men_principal),
                fontSize = 32.sp,
                fontWeight = SemiBold,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        NavDrawerItemComponent(contentDescription = stringResource(R.string.personajes),
                                numFavorites = stateCharacters.value.charactersSet.size,
                                imageVector = Icons.Default.Person,
                                navigate = navigateToCharacters)

        NavDrawerItemComponent(contentDescription = stringResource(R.string.episodios),
                                numFavorites = stateEpisodes.value.episodesFavSet.size,
                                imageVector = Icons.Default.Tv,
                                navigate = navigateToEpisodes)

        NavDrawerItemComponent(contentDescription = stringResource(R.string.citas),
                                numFavorites = stateQuotes.value.quotesSet.size,
                                imageVector = Icons.Default.FormatQuote,
                                navigate = navigateToQuotes)
    }
}


@Composable
fun NavDrawerItemComponent(contentDescription: String, numFavorites: Int = 0,
                           imageVector: ImageVector, navigate: () -> Unit ) {
    NavigationDrawerItem(
        modifier = Modifier.fillMaxWidth()
            .padding(bottom = 10.dp)
            .padding(horizontal = 8.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.secondary),
        label = {
            Row(verticalAlignment = Alignment.CenterVertically ){ // 🔹 Alinea verticalmente
                Text(text = contentDescription,
                    fontSize = 16.sp,
                    fontWeight = SemiBold,
                    modifier = Modifier.width(100.dp))
                Spacer(modifier = Modifier.width(20.dp)) // 🔹 Espacio pequeño entre texto y badge
                Badge(
                    containerColor = MaterialTheme.colorScheme.onPrimary,
                    contentColor = Color.Black
                ) {
                    Row(
                        Modifier.padding(5.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp) // Espaciado entre icono y texto
                    ) {
                        Text(
                            text = numFavorites.toString(),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        },
        icon = { Icon(imageVector = imageVector,
                      contentDescription = contentDescription) },
        selected = false,
        onClick = { navigate() })
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO, name = "Modo Claro")
@Composable
fun ItemMenuComponentPreview() {
    Column {
        ItemMenuComponent(
            navigateToCharacters = {},
            navigateToEpisodes = {},
            navigateToQuotes = {})
    }
}