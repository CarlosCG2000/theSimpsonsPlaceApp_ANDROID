package es.upsa.mimo.thesimpsonplace.presentation.ui.screen.quoteSection

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Addchart
import androidx.compose.material.icons.filled.Album
import androidx.compose.material.icons.filled.Factory
import androidx.compose.material.icons.filled.Filter
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun BottomBarQuoteComponent(selectedBarButtom:Int = 1,
                              navigateToQuotes: () -> Unit,
                              navigateToFiltersQuotes: () -> Unit,
                              navigateToFavoritesQuotes: () -> Unit,
                                navigateToGameQuotes: () -> Unit
                            ) {

    NavigationBar { // Barra de navegación

        NavigationBarItem( // Elemento de la barra de navegacion (la tenemos puesta en BottomBar)
            selected = selectedBarButtom == 1, // solo marcamos como seleccionada a un tipo (al principio el por defecto)
            icon = {
                Icon(   imageVector = Icons.Default.Addchart,
                        contentDescription = "Icono del Lista Completa")
            },
            label = { "Lista de 10 citas" },
            onClick = { navigateToQuotes() },
        )

        NavigationBarItem( // Elemento de la barra de navegacion (la tenemos puesta en BottomBar)
            selected = selectedBarButtom == 2, // solo marcamos como seleccionada a un tipo (al principio el por defecto)
            icon = {
                Icon(   imageVector = Icons.Default.Factory,
                    contentDescription = "Icono del Lista Filtro")
            },
            label = { "Lista Filtro" },
            onClick = { navigateToFiltersQuotes() },
        )

        NavigationBarItem( // Elemento de la barra de navegacion (la tenemos puesta en BottomBar)
            selected = selectedBarButtom == 3, // solo marcamos como seleccionada a un tipo (al principio el por defecto)
            icon = {
                Icon(   imageVector = Icons.Default.Filter,
                    contentDescription = "Icono del Lista Fav")
            },
            label = { "Lista Fav" },
            onClick = { navigateToFavoritesQuotes() },
        )

        NavigationBarItem( // Elemento de la barra de navegacion (la tenemos puesta en BottomBar)
            selected = selectedBarButtom == 4, // solo marcamos como seleccionada a un tipo (al principio el por defecto)
            icon = {
                Icon(   imageVector = Icons.Default.Album,
                    contentDescription = "Icono del Lista Fav")
            },
            label = { "Lista Fav" },
            onClick = { navigateToGameQuotes() },
        )

    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO, name = "Modo Claro")
@Composable
fun BottomBarQuoteComponentPreview() {
    Column {
        BottomBarQuoteComponent(1, {}, {}, {}, {})
    }
}
