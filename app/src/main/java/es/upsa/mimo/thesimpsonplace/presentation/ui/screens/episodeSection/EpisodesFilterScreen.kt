package es.upsa.mimo.thesimpsonplace.presentation.ui.screens.episodeSection

import android.R.attr.height
import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
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
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import es.upsa.mimo.thesimpsonplace.presentation.ui.components.BottomBarComponent
import es.upsa.mimo.thesimpsonplace.presentation.ui.components.TopBarComponent
import es.upsa.mimo.thesimpsonplace.presentation.ui.screens.episodeSection.DatePickerDialogComponent
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
    // ___________________________________________________

    LaunchedEffect(Unit) {
        viewModelAllEpisodes.getAllEpisodes() // cargue todos los episodios de primeras
    }

    LaunchedEffect(stateAllEpisodes.value.episodes) {
        // state.value.episodes = stateAllEpisodes.value.episodes
        viewModel.updateEpisodes(episodes = stateAllEpisodes.value.episodes)// cargue todos los episodios de primeras
    }

    fun logicaFiltrado(title: String = filterTitle.text,
                      minDate: Date = filterMinDate.selectedDateMillis?.let { Date(it) } ?: Date(defaultMinDate),
                      maxDate: Date = filterMaxDate.selectedDateMillis?.let { Date(it) } ?: Date(),
                      season: Int = filterSeason,
                      episode: Int = filterEpisode,
                      isView: Boolean = isViewEnabled,
                      order: Boolean = isOrder
    ){
        viewModel.getEpisodesFilter(title = title,
                                    minDate = minDate,
                                    maxDate = maxDate,
                                    season = season,
                                    episode = episode,
                                    isView = isView,
                                    order = order)
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
            modifier = Modifier.fillMaxSize()
                              //.background(Color.Blue),
                               .padding(paddingValues),
            constraintSet = episodesFilterContraintSet()
        //  contentAlignment = Alignment.Center,
        ){
            // DEFINIR TODOS LOS FILTROS
            TextField(
                value = filterTitle,
                onValueChange = { newValue ->
                    filterTitle = newValue
                    debounceJob?.cancel() // Cancelamos la tarea anterior si hay una nueva entrada
                    debounceJob = viewModel.viewModelScope.launch {
                        delay(350) // Esperamos 500 ms antes de ejecutar el filtro
                        logicaFiltrado(title = newValue.text)
                    }
                },
                label = { Text("Titulo del episodio") },
                placeholder = {Text("Homer, Smithers, Milhouse...")},
                trailingIcon = {
                    if (filterTitle.text.isNotEmpty()) {
                        IconButton(onClick = {
                            filterTitle = TextFieldValue("")
                            logicaFiltrado(title = "")
                        }) {
                            Icon(imageVector = Icons.Filled.Close, contentDescription = "Borrar texto")
                        }
                    }
                },
                modifier = Modifier.layoutId("idTextFieldFilter")
                                   .padding(horizontal = 10.dp, vertical = 5.dp)
            )

            Row (
                modifier = Modifier.layoutId("idColumnDate")
                                   .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                // verticalArrangement = Arrangement.SpaceBetween
            ) {

                // DatePickerDialog solo se muestra cuando showDialogMinDate es true
                DatePickerDialogComponent(
                    showDialogDate = showDialogMinDate,
                    onDismissRequest = { showDialogMinDate = false },
                    onAdmitRequest = { showDialogMinDate = true },
                    filterDate = filterMinDate,
                    onClick = {
                        showDialogMinDate = false
                        logicaFiltrado()
                    },
                    title = "Start Date"
                )

                DatePickerDialogComponent(
                    showDialogDate = showDialogMaxDate,
                    onDismissRequest = { showDialogMaxDate = false },
                    onAdmitRequest = { showDialogMaxDate = true },
                    filterDate = filterMaxDate,
                    onClick = {
                        showDialogMaxDate = false
                        logicaFiltrado()
                    },
                    title = "End Date"
                )

                // Switch para marcar si está visto
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("¿Visto?")
                    Switch(
                        checked = isViewEnabled,
                        onCheckedChange = {
                            isViewEnabled = !isViewEnabled
                            logicaFiltrado()
                        },
                        colors = SwitchDefaults.colors(
                                checkedThumbColor = Color.Yellow,
                                checkedTrackColor = Color.Yellow.copy(alpha = 0.2f) // Fondo amarillo con transparencia
                        )
                    )
                }
            }

            Row(
                modifier = Modifier
                    .layoutId("idColumnPicker")
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {

                DropdownMenuComponent(
                    label = "Temporada",
                    selectedItem = filterSeason,
                    items = uniqueSeasons,
                    onItemSelected = { filterSeasonItem ->
                        filterSeason = filterSeasonItem
                        logicaFiltrado(season = filterSeasonItem)
                    }
                )

                DropdownMenuComponent(
                    label = "Capitulo",
                    selectedItem = filterEpisode,
                    items = uniqueEpisodes,
                    onItemSelected = { filterEpisodeItem ->
                        filterEpisode = filterEpisodeItem
                        logicaFiltrado(episode = filterEpisodeItem)
                    }
                )

                IconButton(onClick = {
                    isOrder = !isOrder
                    viewModel.getEpisodesOrder(isOrder)
                })
                {
                    Icon(
                        imageVector = if (isOrder) Icons.Default.ArrowUpward else Icons.Default.ArrowDownward,
                        contentDescription = "Sort Order"
                    )
                }

            }

            if(state.value.isLoading || stateAllEpisodes.value.isLoading){
                CircularProgressIndicator(
                    color = Color.Yellow,
                    modifier = Modifier.layoutId("idSpinner")
                )
            }
            else {
                if (state.value.episodes.isEmpty()) {
                    NoContentComponent(Modifier
                        .layoutId("idListEpisodes")
                        .padding(top = 10.dp)
                        .fillMaxSize()
                        .background(Color(0xFF0F1A35)))
                }
                else {
                    ListEpisodes(
                        modifier = Modifier.layoutId("idListEpisodes"),
                        episodes = state.value.episodes,
                        allEpisodes = stateAllEpisodes.value.episodes,
                        onEpisodeSelected = onEpisodeSelected
                    )
                }
            }
        }
    }
}

fun episodesFilterContraintSet(): ConstraintSet {
    return ConstraintSet() {
        // Creando referencias (ids)
        val (spinner, listEpisodes, textFieldFilter, columnDate, columnPicker, iconEmptyList, titleEmptyList, subtitleEmptyList) =
            createRefsFor("idSpinner", "idListEpisodes", "idTextFieldFilter", "idColumnDate", "idColumnPicker", "idIconEmptyList", "idTitleEmptyList", "idSubtitleEmptyList")

        // Creamos una cadena vertical para los dos botones
//        val verticalChain = createVerticalChain(textFieldFilter, columnDate, columnPicker, chainStyle = ChainStyle.Packed)

//        val horizontalChain = createHorizontalChain(iconEmptyList, titleEmptyList, subtitleEmptyList, chainStyle = ChainStyle.SpreadInside)
//
//        constrain(iconEmptyList) {
//            top.linkTo(parent.top)
//            bottom.linkTo(parent.bottom)
//        }
//
//        constrain(titleEmptyList) {
//            top.linkTo(iconEmptyList.bottom)
//            bottom.linkTo(parent.bottom)
//        }
//
//        constrain(subtitleEmptyList) {
//            top.linkTo(titleEmptyList.bottom)
//            bottom.linkTo(parent.bottom)
//        }

        constrain(textFieldFilter) {
            top.linkTo(parent.top)
            bottom.linkTo(columnDate.top)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            width = Dimension.fillToConstraints
        }

        constrain(columnDate) {
            top.linkTo(textFieldFilter.bottom)
            bottom.linkTo(columnPicker.top)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            width = Dimension.fillToConstraints
        }

        constrain(columnPicker) {
            top.linkTo(columnDate.bottom)
            bottom.linkTo(listEpisodes.top)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            width = Dimension.fillToConstraints
        }

        constrain(spinner) {
            top.linkTo(columnPicker.bottom)
            bottom.linkTo(parent.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }

        constrain(listEpisodes) {
            top.linkTo(columnPicker.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
        }
    }
}

@Composable
fun NoContentComponent(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier, // Color de fondo similar al de la imagen
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Default.FormatListNumbered, // Usa un ícono similar al de la imagen
            contentDescription = "No episodes",
            tint = Color(0xFFFFC107), // Amarillo similar
            modifier = Modifier
                // .layoutId("idIconEmptyList")
                .size(64.dp)
//                                .padding(bottom = 20.dp)

        )
        Text(
            text = "No episodes",
            color = Color(0xFFFFC107),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
            // .layoutId("idTitleEmptyList")
//                                .padding(top = 8.dp)
//                                .padding(bottom = 20.dp)
        )
        Text(
            text = "There aren't episodes with that features. Change the episode's features.",
            color = Color.White,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier
                // .layoutId("idSubtitleEmptyList")
                .padding(start = 32.dp, end = 32.dp, top = 4.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerDialogComponent(modifier: Modifier = Modifier,
                              showDialogDate: Boolean,
                              onDismissRequest: () -> Unit,
                              onAdmitRequest: () -> Unit,
                              filterDate: DatePickerState,
                              onClick: () -> Unit,
                              title: String,
                              defaultMinDate: Long = Calendar.getInstance().apply {set(1989, Calendar.DECEMBER, 16) }.timeInMillis
                              ) {
    if (showDialogDate) {
        DatePickerDialog(
            modifier = modifier,
            onDismissRequest = onDismissRequest,
            confirmButton = {
                TextButton(onClick = onClick) {
                    Text("OK")
                }
            }
        ) {
            DatePicker(state = filterDate)
        }
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(title, color = Color.Yellow)

        // Botón para abrir el DatePickerDialog
        OutlinedButton(onClick = onAdmitRequest) {
            Text(
                SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(
                    filterDate.selectedDateMillis // ✅ Obtener la fecha seleccionada
                        ?: defaultMinDate
                ),
                color = Color.Yellow
            )
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