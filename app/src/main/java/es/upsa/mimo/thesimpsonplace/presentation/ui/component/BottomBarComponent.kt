package es.upsa.mimo.thesimpsonplace.presentation.ui.component

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
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

enum class BottomNavItem { ALL, FILTERS, FAVORITES }

@Composable
fun colorsTheme(): NavigationBarItemColors {
    return NavigationBarItemDefaults.colors(
        selectedIconColor = MaterialTheme.colorScheme.onPrimary,
        unselectedIconColor = MaterialTheme.colorScheme.onSecondary,
        selectedTextColor = MaterialTheme.colorScheme.onPrimary,
        unselectedTextColor =  MaterialTheme.colorScheme.onSecondary,
        indicatorColor = Color.Transparent // Fondo transparente en el botón seleccionado
    )
}

@Composable
fun BottomBarComponent(selectedBarButtom: BottomNavItem = BottomNavItem.ALL,
                       navigateToAllEpisodes: () -> Unit,
                       navigateToFiltersEpisode: () -> Unit,
                       navigateToFavoritesEpisode: () -> Unit) {

    BottomAppBar( // Barra de navegación, ¿usar NavigationBar?
        containerColor = MaterialTheme.colorScheme.surface
    ) {

        NavigationBarItem(
            // Elemento de la barra de navegacion (la tenemos puesta en BottomBar)
            selected = selectedBarButtom == BottomNavItem.ALL, // solo marcamos como seleccionada a un tipo (al principio el ALL por defecto)
            icon = {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = stringResource(R.string.lista_completa),
                    modifier = Modifier.size(35.dp) // Aumenta el tamaño del icono
                )
            },
            label = { Text(text = stringResource(R.string.lista_completa)) },
            onClick = { navigateToAllEpisodes() },
            colors = colorsTheme()
        )

        NavigationBarItem(
            selected = selectedBarButtom == BottomNavItem.FILTERS, // solo marcamos como seleccionada a un tipo
            icon = {
                Icon(imageVector = Icons.Default.Search,
                     contentDescription = stringResource(R.string.lista_filtro),
                    modifier = Modifier.size(35.dp))
            },
            label = {  Text(text = stringResource(R.string.lista_filtro)) },
            onClick = { navigateToFiltersEpisode() },
            colors = colorsTheme()
        )

        NavigationBarItem(
            selected = selectedBarButtom == BottomNavItem.FAVORITES, // solo marcamos como seleccionada a un tipo
            icon = {
                Icon( imageVector = Icons.Default.Star,
                    contentDescription = stringResource(R.string.lista_fav),
                    modifier = Modifier.size(35.dp))
            },
            label = {  Text(text = stringResource(R.string.lista_fav)) },
            onClick = { navigateToFavoritesEpisode() },
            colors = colorsTheme()
        )
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO, name = "Modo Claro")
@Composable
fun BottomBarEpisodeComponentPreview() {
    Column {
        BottomBarComponent(BottomNavItem.FILTERS, {}, {}, {})
    }
}
