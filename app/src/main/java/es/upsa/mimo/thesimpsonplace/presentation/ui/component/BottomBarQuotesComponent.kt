package es.upsa.mimo.thesimpsonplace.presentation.ui.component

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Games
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import es.upsa.mimo.thesimpsonplace.R

enum class BottomNavQuotesItem { MAIN, FILTERS, FAVORITES, GAME}

@Composable
fun BottomBarQuoteComponent(selectedBarButtom:BottomNavQuotesItem = BottomNavQuotesItem.MAIN,
                              navigateToQuotes: () -> Unit,
                              navigateToFiltersQuotes: () -> Unit,
                              navigateToFavoritesQuotes: () -> Unit,
                                navigateToGameQuotes: () -> Unit
                            ) {

    BottomAppBar { // Barra de navegación, ¿usar NavigationBar?

        NavigationBarItem( // Elemento de la barra de navegacion (la tenemos puesta en BottomBar)
            selected = selectedBarButtom == BottomNavQuotesItem.MAIN, // solo marcamos como seleccionada a un tipo (al principio el por defecto)
            icon = {
                Icon(imageVector = Icons.Default.Home,
                     contentDescription = stringResource(R.string.lista_completa))
            },
            label = {  Text(text = stringResource(R.string.lista_completa))  },
            onClick = { navigateToQuotes() },
        )

        NavigationBarItem(
            selected = selectedBarButtom == BottomNavQuotesItem.FILTERS,
            icon = {
                Icon(imageVector =  Icons.Default.Search,
                     contentDescription = stringResource(R.string.lista_filtro))
            },
            label = {  Text(text = stringResource(R.string.lista_filtro)) },
            onClick = { navigateToFiltersQuotes() },
        )

        NavigationBarItem( 
            selected = selectedBarButtom == BottomNavQuotesItem.FAVORITES, 
            icon = {
                Icon(imageVector = Icons.Default.Star,
                     contentDescription = stringResource(R.string.lista_fav))
            },
            label = { Text(text = stringResource(R.string.lista_fav)) },
            onClick = { navigateToFavoritesQuotes() },
        )

        NavigationBarItem(
            selected = selectedBarButtom == BottomNavQuotesItem.GAME,
            icon = {
                Icon(imageVector = Icons.Default.Games,
                     contentDescription = stringResource(R.string.juego))
            },
            label = { Text(text = stringResource(R.string.juego))  },
            onClick = { navigateToGameQuotes() },
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
