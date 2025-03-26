package es.upsa.mimo.thesimpsonplace.presentation.ui.screens.episodeSection

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FormatListNumbered
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import es.upsa.mimo.thesimpsonplace.presentation.ui.components.BottomBarComponent
import es.upsa.mimo.thesimpsonplace.presentation.ui.components.TopBarComponent
import es.upsa.mimo.thesimpsonplace.presentation.viewmodel.episode.episodesList.ListEpisodesStateUI
import es.upsa.mimo.thesimpsonplace.presentation.viewmodel.episode.episodesList.ListEpisodesViewModel
import es.upsa.mimo.thesimpsonplace.presentation.viewmodel.episode.episodesListFav.ListEpisodesFilterStateUI
import es.upsa.mimo.thesimpsonplace.presentation.viewmodel.episode.episodesListFav.ListEpisodesFilterViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EpisodesFilterScreen(viewModelAllEpisodes: ListEpisodesViewModel = hiltViewModel(),
                         viewModel: ListEpisodesFilterViewModel = hiltViewModel(),
                         navigateToAllEpisodes: () -> Unit,
                         navigateToFavoriteEpisode: () -> Unit,
                         onEpisodeSelected: (String) -> Unit,
                         navigationArrowBack:() -> Unit) {

    // getEpisodesFilter(title = filterTitle, minDate = filterMinDate, maxDate = filterMaxDate, season = filterSeason, episode = filterEpisode, isView = isViewEnabled, order = isOrder)

    var stateAllEpisodes: State<ListEpisodesStateUI> = viewModelAllEpisodes.episodesState.collectAsState()
    var state: State<ListEpisodesFilterStateUI> = viewModel.stateEpisode.collectAsState() // pasa a ser sincrono para manejarlo en la UI

    // _____________________ FILTROS _____________________
    var filterTitle by remember { mutableStateOf(TextFieldValue("")) } // Estado del campo usuario
    var debounceJob by remember { mutableStateOf<Job?>(null) } // Para cancelar el debounce

    var defaultMinDate: Long = Calendar.getInstance().apply {set(1989, Calendar.DECEMBER, 16) }.timeInMillis
    val filterMinDate = rememberDatePickerState( initialSelectedDateMillis = defaultMinDate ) // ✅ Correcto: convertir a milisegundos
    var showDialogMinDate by remember { mutableStateOf(false) }

    var filterMaxDate = rememberDatePickerState(initialSelectedDateMillis = System.currentTimeMillis())// Estado del campo usuario
    var showDialogMaxDate by remember { mutableStateOf(false) }

    var isViewEnabled by remember { mutableStateOf(false) }

    var filterSeason by remember { mutableIntStateOf(0) }
    val uniqueSeasons = listOf(0) + stateAllEpisodes.value.episodes.map { it.temporada }.distinct()

    var filterEpisode by remember { mutableIntStateOf(0) }
    val uniqueEpisodes =  listOf(0) + stateAllEpisodes.value.episodes.map { it.episodio }.distinct()

    var isOrder by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModelAllEpisodes.getAllEpisodes() // cargue todos los episodios de primeras
    }

    LaunchedEffect(stateAllEpisodes.value.episodes) {
        // state.value.episodes = stateAllEpisodes.value.episodes
        viewModel.updateEpisodes(stateAllEpisodes.value.episodes)// cargue todos los episodios de primeras
    }

    Scaffold(
        bottomBar = {
            BottomBarComponent(
                2,
                navigateToAllEpisodes,
                { },
                navigateToFavoriteEpisode
            )
        },
        topBar = {
            TopBarComponent(
                title = "Listado de Episodios Fav",
                onNavigationArrowBack = navigationArrowBack
            )
        }
    ) { paddingValues ->

        ConstraintLayout(
            // contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues))
            //.background(Color.Blue))
        {

            val (spinner, listEpisodes, textFieldFilter, columnDate, columnPicker) = createRefs()

            // DEFINIR TODOS LOS FILTROS
            TextField(
                value = filterTitle,
                onValueChange = { newValue ->
                    filterTitle = newValue
                    debounceJob?.cancel() // Cancelamos la tarea anterior si hay una nueva entrada
                    debounceJob = viewModel.viewModelScope.launch {
                        delay(350) // Esperamos 500 ms antes de ejecutar el filtro
                        Log.i("EpisodeFilterScreen", newValue.text)
                        viewModel.getEpisodesFilter(title = newValue.text,
                                                    minDate = filterMinDate.selectedDateMillis?.let { Date(it) } ?: Date(defaultMinDate),
                                                    maxDate = filterMaxDate.selectedDateMillis?.let { Date(it) } ?: Date(),
                                                    season = filterSeason, episode = filterEpisode, isView = isViewEnabled, order = isOrder
                                                    )
                        }
                },
                label = { Text("Titulo del episodio") },
                placeholder = {Text("Homer, Smithers, Milhouse...")},
                trailingIcon = {
                    if (filterTitle.text.isNotEmpty()) {
                        IconButton(onClick = {
                            filterTitle = TextFieldValue("")
                            viewModel.getEpisodesFilter(title = "",
                                minDate = filterMinDate.selectedDateMillis?.let { Date(it) } ?: Date(defaultMinDate),
                                maxDate = filterMaxDate.selectedDateMillis?.let { Date(it) } ?: Date(),
                                season = filterSeason, episode = filterEpisode, isView = isViewEnabled, order = isOrder
                            )
                        }) {
                            Icon(imageVector = Icons.Filled.Close, contentDescription = "Borrar texto")
                        }
                    }
                },
                modifier = Modifier
                    .constrainAs(textFieldFilter) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                    }
                    .padding(horizontal = 10.dp, vertical = 5.dp)
            )

            Row (
                modifier = Modifier
                    .constrainAs(columnDate) {
                        top.linkTo(textFieldFilter.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                    }
                    .fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly,
                // verticalArrangement = Arrangement.SpaceBetween
            ) {
                // DatePickerDialog solo se muestra cuando showDialogMinDate es true
                if (showDialogMinDate) {
                    DatePickerDialog(
                        onDismissRequest = { showDialogMinDate = false },
                        confirmButton = {
                            TextButton(onClick = {
                                showDialogMinDate = false
                                viewModel.getEpisodesFilter(title = filterTitle.text,
                                    minDate = filterMinDate.selectedDateMillis?.let { Date(it) } ?: Date(defaultMinDate),
                                    maxDate = filterMaxDate.selectedDateMillis?.let { Date(it) } ?: Date(),
                                    season = filterSeason, episode = filterEpisode, isView = isViewEnabled, order = isOrder
                                )
                            }) {
                                Text("OK")
                            }
                        }
                    ) {
                        DatePicker(state = filterMinDate)
                    }
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Start Date: ", color = Color.Yellow)

                    // Botón para abrir el DatePickerDialog
                    OutlinedButton(onClick = { showDialogMinDate = true }) {
                        Text(
                            SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(
                                filterMinDate.selectedDateMillis // ✅ Obtener la fecha seleccionada
                                    ?: defaultMinDate
                            ),
                            color = Color.Yellow
                        )
                    }
                }

                if (showDialogMaxDate) {
                    DatePickerDialog(
                        onDismissRequest = { showDialogMaxDate = false },
                        confirmButton = {
                            TextButton(onClick = {
                                showDialogMaxDate = false
                                viewModel.getEpisodesFilter(title = filterTitle.text,
                                    minDate = filterMinDate.selectedDateMillis?.let { Date(it) } ?: Date(defaultMinDate),
                                    maxDate = filterMaxDate.selectedDateMillis?.let { Date(it) } ?: Date(),
                                    season = filterSeason, episode = filterEpisode, isView = isViewEnabled, order = isOrder
                                )
                            }) {
                                Text("OK")
                            }
                        }
                    ) {
                        DatePicker(state = filterMaxDate)
                    }
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("End Date: ", color = Color.Yellow)

                    // Botón para abrir el DatePickerDialog
                    OutlinedButton(onClick = { showDialogMaxDate = true }) {
                        Text(
                            SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(
                                filterMaxDate.selectedDateMillis // ✅ Obtener la fecha seleccionada
                                    ?: System.currentTimeMillis()
                            ),
                            color = Color.Yellow
                        )
                    }
                }

                // Switch para marcar si está visto
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("¿Visto?")
                    Switch(
                        checked = isViewEnabled,
                        onCheckedChange = {
                            isViewEnabled = !isViewEnabled
                            viewModel.getEpisodesFilter(title = filterTitle.text,
                                minDate = filterMinDate.selectedDateMillis?.let { Date(it) } ?: Date(defaultMinDate),
                                maxDate = filterMaxDate.selectedDateMillis?.let { Date(it) } ?: Date(),
                                season = filterSeason, episode = filterEpisode, isView = isViewEnabled, order = isOrder
                            )
                        }
                    )
                }
            }

            Row(
                modifier = Modifier
                    .constrainAs(columnPicker) {
                        top.linkTo(columnDate.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                    }
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {

                DropdownMenuComponent(
                    label = "Temporada",
                    selectedItem = filterSeason,
                    items = uniqueSeasons,
                    onItemSelected = { filterSeasonItem ->
                        filterSeason = filterSeasonItem

                        viewModel.getEpisodesFilter(title = filterTitle.text,
                            minDate = filterMinDate.selectedDateMillis?.let { Date(it) } ?: Date(defaultMinDate),
                            maxDate = filterMaxDate.selectedDateMillis?.let { Date(it) } ?: Date(),
                            season = filterSeasonItem, episode = filterEpisode, isView = isViewEnabled, order = isOrder
                        )
                    }
                )

                DropdownMenuComponent(
                    label = "Capitulo",
                    selectedItem = filterEpisode,
                    items = uniqueEpisodes,
                    onItemSelected = { filterEpisodeItem ->
                        filterEpisode = filterEpisodeItem

                        viewModel.getEpisodesFilter(title = filterTitle.text,
                            minDate = filterMinDate.selectedDateMillis?.let { Date(it) } ?: Date(defaultMinDate),
                            maxDate = filterMaxDate.selectedDateMillis?.let { Date(it) } ?: Date(),
                            season = filterSeason, episode = filterEpisodeItem, isView = isViewEnabled, order = isOrder
                        )
                    }
                )

                IconButton(onClick = {
                    isOrder = !isOrder


                }) {
                    Icon(
                        imageVector = if (isOrder) Icons.Default.ArrowUpward else Icons.Default.ArrowDownward,
                        contentDescription = "Sort Order"
                    )
                }

            }

            if(state.value.isLoading || stateAllEpisodes.value.isLoading){
                CircularProgressIndicator(
                    color = Color.Yellow,
                    modifier = Modifier.constrainAs(spinner) {
                        centerTo(parent)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                )
            }
            else {
                if (state.value.episodes.isEmpty()) {
                    Column(
                        modifier = Modifier
                            .constrainAs(listEpisodes) {
                                top.linkTo(columnPicker.bottom)
                                bottom.linkTo(parent.bottom)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            }
                            .fillMaxSize()
                            .background(Color(0xFF0F1A35)), // Color de fondo similar al de la imagen
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.FormatListNumbered, // Usa un ícono similar al de la imagen
                            contentDescription = "No episodes",
                            tint = Color(0xFFFFC107), // Amarillo similar
                            modifier = Modifier
                                .size(64.dp)
                                .padding(bottom = 20.dp)
                        )
                        Text(
                            text = "No episodes",
                            color = Color(0xFFFFC107),
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier
                                .padding(top = 8.dp)
                                .padding(bottom = 20.dp)
                        )
                        Text(
                            text = "There aren't episodes with that features. Change the episode's features.",
                            color = Color.White,
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(start = 32.dp, end = 32.dp, top = 4.dp)
                        )
                    }
                }
                else {
                    ListEpisodes(
                        modifier = Modifier.constrainAs(listEpisodes) {
                            top.linkTo(columnPicker.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        },
                        episodes = state.value.episodes,
                        allEpisodes = stateAllEpisodes.value.episodes,
                        onEpisodeSelected = onEpisodeSelected
                    )
                }
            }

        }
    }
}

@Composable
fun DropdownMenuComponent(
    label: String,
    selectedItem: Int,
    items: List<Int>,
    onItemSelected: (Int) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box ( modifier = Modifier.border(2.dp, Color.DarkGray, RoundedCornerShape(12.dp)) // ✅ Aplica el borde
                             // .background(Color.Black) // ✅ Fondo oscuro opcional
        ){
        val itemSelect = if (selectedItem == 0) "s" else " $selectedItem"

        TextButton(onClick = { expanded = true }) {
            Text(text = "$label$itemSelect", color = Color.Yellow)
            Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = "Expand")
        }

        DropdownMenu(expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
            // modifier = Modifier.border(10.dp, Color.DarkGray) // 🔹 Fondo oscuro
        ) {
            items.forEach { item ->
                val itemMenu = if (item == 0) "Todos" else "$label $item"

                DropdownMenuItem(
                    text = { Text(  text = itemMenu,
                                    color = Color.Blue,
                                    modifier = Modifier.fillMaxWidth(), // 🔹 Hace que ocupe todo el ancho
                                    textAlign = TextAlign.Center // 🔹 Centra el texto horizontalmente)
                                 )
                                 },
                    onClick = {
                        onItemSelected(item)
                        expanded = false
                    },
                )
            }
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO, name = "Modo Claro")
@Composable
fun EpisodesFilterScreenPreview() {
    Column {
        EpisodesFilterScreen( navigateToAllEpisodes = {},
                              navigateToFavoriteEpisode = {},
                              onEpisodeSelected= {},
                              navigationArrowBack = {})
    }
}