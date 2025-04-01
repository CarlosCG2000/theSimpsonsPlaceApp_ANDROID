package es.upsa.mimo.thesimpsonplace.presentation.ui.component

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Games
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import es.upsa.mimo.thesimpsonplace.R

enum class BottomNavQuotesItem { MAIN, FILTERS, FAVORITES, GAME}

@Composable
fun BottomBarQuoteComponent(selectedBarButtom:BottomNavQuotesItem = BottomNavQuotesItem.MAIN,
                              navigateToQuotes: () -> Unit,
                              navigateToFiltersQuotes: () -> Unit,
                              navigateToFavoritesQuotes: () -> Unit,
                                navigateToGameQuotes: () -> Unit
                            ) {

    BottomAppBar( containerColor = MaterialTheme.colorScheme.surface) { // Barra de navegación, ¿usar NavigationBar?

        NavigationBarItem( // Elemento de la barra de navegacion (la tenemos puesta en BottomBar)
            selected = selectedBarButtom == BottomNavQuotesItem.MAIN, // solo marcamos como seleccionada a un tipo (al principio el por defecto)
            icon = {
                Icon(imageVector = Icons.Default.Home,
                     contentDescription = stringResource(R.string.lista_completa),
                    modifier = Modifier.size(35.dp))
            },
            label = {  Text(text = stringResource(R.string.lista_completa))  },
            onClick = { navigateToQuotes() },
            colors = colorsTheme()
        )

        NavigationBarItem(
            selected = selectedBarButtom == BottomNavQuotesItem.FILTERS,
            icon = {
                Icon(imageVector =  Icons.Default.Search,
                     contentDescription = stringResource(R.string.lista_filtro),
                    modifier = Modifier.size(35.dp))
            },
            label = {  Text(text = stringResource(R.string.lista_filtro)) },
            onClick = { navigateToFiltersQuotes() },
            colors = colorsTheme()
        )

        NavigationBarItem( 
            selected = selectedBarButtom == BottomNavQuotesItem.FAVORITES, 
            icon = {
                Icon(imageVector = Icons.Default.Star,
                     contentDescription = stringResource(R.string.lista_fav),
                    modifier = Modifier.size(35.dp))
            },
            label = { Text(text = stringResource(R.string.lista_fav)) },
            onClick = { navigateToFavoritesQuotes() },
            colors = colorsTheme()
        )

        NavigationBarItem(
            selected = selectedBarButtom == BottomNavQuotesItem.GAME,
            icon = {
                Icon(imageVector = Icons.Default.Games,
                     contentDescription = stringResource(R.string.juego),
                    modifier = Modifier.size(35.dp))
            },
            label = { Text(text = stringResource(R.string.juego))  },
            onClick = { navigateToGameQuotes() },
            colors = colorsTheme()
        )

    }
}



@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO, name = "Modo Claro")
@Composable
fun BottomBarQuoteComponentPreview() {
    Column {
        BottomBarQuoteComponent(BottomNavQuotesItem.GAME, {}, {}, {}, {})
    }
}
