package es.upsa.mimo.thesimpsonplace.presentation.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Addchart
import androidx.compose.material.icons.filled.Filter
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun BottomBarComponent(selectedBarButtom:Int = 1,
                       navigateToAllEpisodes: () -> Unit,
                       navigateToFiltersEpisode: () -> Unit,
                       navigateToFavoritesEpisode: () -> Unit
                            ) {

    NavigationBar { // Barra de navegación

        NavigationBarItem( // Elemento de la barra de navegacion (la tenemos puesta en BottomBar)
            selected = selectedBarButtom == 1, // solo marcamos como seleccionada a un tipo (al principio el por defecto)
            icon = {
                Icon(   imageVector = Icons.Default.Addchart,
                        contentDescription = "Icono del Lista Completa")
            },
            label = { "Lista Completa" },
            onClick = { navigateToAllEpisodes() },
        )

        NavigationBarItem( // Elemento de la barra de navegacion (la tenemos puesta en BottomBar)
            selected = selectedBarButtom == 2, // solo marcamos como seleccionada a un tipo (al principio el por defecto)
            icon = {
                Icon(   imageVector = Icons.Default.Filter,
                    contentDescription = "Icono del Lista Filtro")
            },
            label = { "Lista Filtro" },
            onClick = { navigateToFiltersEpisode() },
        )

        NavigationBarItem( // Elemento de la barra de navegacion (la tenemos puesta en BottomBar)
            selected = selectedBarButtom == 3, // solo marcamos como seleccionada a un tipo (al principio el por defecto)
            icon = {
                Icon(   imageVector = Icons.Default.Filter,
                    contentDescription = "Icono del Lista Fav")
            },
            label = { "Lista Fav" },
            onClick = { navigateToFavoritesEpisode() },
        )

    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO, name = "Modo Claro")
@Composable
fun BottomBarEpisodeComponentPreview() {
    Column {
        BottomBarComponent(1, {}, {}, {})
    }
}
