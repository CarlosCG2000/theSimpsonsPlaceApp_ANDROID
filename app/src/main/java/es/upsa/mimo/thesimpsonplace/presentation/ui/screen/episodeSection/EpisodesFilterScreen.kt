package es.upsa.mimo.thesimpsonplace.presentation.ui.screen.episodeSection

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerColors
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButtonDefaults.colors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import es.upsa.mimo.thesimpsonplace.R
import es.upsa.mimo.thesimpsonplace.presentation.ui.component.BottomBarComponent
import es.upsa.mimo.thesimpsonplace.presentation.ui.component.BottomNavItem
import es.upsa.mimo.thesimpsonplace.presentation.ui.component.NoContentComponent
import es.upsa.mimo.thesimpsonplace.presentation.ui.component.TopBarComponent
import es.upsa.mimo.thesimpsonplace.presentation.viewmodel.episode.episodesList.ListEpisodesStateUI
import es.upsa.mimo.thesimpsonplace.presentation.viewmodel.episode.episodesList.ListEpisodesViewModel
import es.upsa.mimo.thesimpsonplace.presentation.viewmodel.episode.episodesFilters.ListEpisodesFilterStateUI
import es.upsa.mimo.thesimpsonplace.presentation.viewmodel.episode.episodesFilters.ListEpisodesFilterViewModel
import es.upsa.mimo.thesimpsonplace.presentation.viewmodel.episode.episodesListFav.ListEpisodesDBViewModel
import es.upsa.mimo.thesimpsonplace.presentation.viewmodel.episode.episodesListFav.ListEpisodesDbStateUI
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EpisodesFilterScreen(viewModelAllEpisodes: ListEpisodesViewModel = hiltViewModel(),
                         viewModel: ListEpisodesFilterViewModel = hiltViewModel(),
                         viewModelDB: ListEpisodesDBViewModel = hiltViewModel(),
                         navigateToAllEpisodes: () -> Unit,
                         navigateToFavoriteEpisode: () -> Unit,
                         onEpisodeSelected: (String) -> Unit,
                         navigationArrowBack:() -> Unit) {

    var stateAllEpisodes: State<ListEpisodesStateUI> = viewModelAllEpisodes.episodesState.collectAsState()
    var state: State<ListEpisodesFilterStateUI> = viewModel.stateEpisode.collectAsState() // pasa a ser sincrono para manejarlo en la UI
    val stateFavOrView: State<ListEpisodesDbStateUI> = viewModelDB.stateEpisodesFavOrView.collectAsState()

    Log.i("EpisodesFilterScreen", "state.episodes size: ${state.value.episodes.size}")

    // _____________________ FILTROS _____________________
    var filterTitle by remember { mutableStateOf(TextFieldValue("")) } // Estado del campo usuario

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

    // **🛠 Depuración: Ver qué está pasando con los episodios**
    Log.i("EpisodesFilterScreen", "stateAllEpisodes.isLoading: ${stateAllEpisodes.value.isLoading}")
    Log.i("EpisodesFilterScreen", "stateAllEpisodes.episodes size: ${stateAllEpisodes.value.episodes.size}")

//    1.	Primer LaunchedEffect(Unit)
//    •	Se ejecuta al iniciarse el Composable.
//    •	Como stateAllEpisodes.value.episodes está vacío, llama a viewModelAllEpisodes.getAllEpisodes() para obtener los episodios.
//    •	Esto actualiza stateAllEpisodes, lo que desencadena el siguiente LaunchedEffect.
    LaunchedEffect(Unit) {
        if (stateAllEpisodes.value.episodes.isEmpty() && !stateAllEpisodes.value.isLoading) {
            viewModelAllEpisodes.getAllEpisodes() // cargue todos los episodios de primeras
            Log.i("EpisodesFilterScreen", "Llamando a getAllEpisodes()")
        }
    }

//    2.	Segundo LaunchedEffect(stateAllEpisodes.value.episodes)
//    •	Se ejecuta cuando stateAllEpisodes.value.episodes cambia.
//    •	Cuando la lista de episodios se llena, llama a viewModel.updateEpisodes(stateAllEpisodes.value.episodes), lo que posiblemente también esté actualizando el estado y provocando otro renderizado.
    LaunchedEffect(stateAllEpisodes.value.episodes) {
        if (stateAllEpisodes.value.episodes.isNotEmpty()){ //&& state.value.episodes != stateAllEpisodes.value.episodes) {
            Log.i("EpisodesFilterScreen", "Actualizando filtros con episodios cargados")
            viewModel.updateEpisodes(stateAllEpisodes.value.episodes)
        }
    }

//    3.	Tercer LaunchedEffect con filtros
//    •	Cualquier cambio en filterTitle, filterMinDate, filterMaxDate, etc., dispara otro LaunchedEffect, con un delay(350) de debounce.
//    •	Si stateAllEpisodes.value.episodes se actualiza varias veces en cascada, podría estar generando múltiples llamadas.
    LaunchedEffect(filterTitle.text, filterMinDate.selectedDateMillis, filterMaxDate.selectedDateMillis,
        filterSeason, filterEpisode, isViewEnabled, isOrder) {
        if (stateAllEpisodes.value.episodes.isNotEmpty()) { // Solo filtra si ya hay episodios cargados
            delay(300) // Debounce
            viewModel.getEpisodesFilter(
                title = filterTitle.text,
                minDate = filterMinDate.selectedDateMillis?.let { Date(it) }
                    ?: Date(defaultMinDate),
                maxDate = filterMaxDate.selectedDateMillis?.let { Date(it) } ?: Date(),
                season = filterSeason,
                episode = filterEpisode,
                isView = isViewEnabled,
                order = isOrder
            )
            Log.i("LaunchedEffect", "filterTitle: ${state.value.episodes}")
        }
    }

    fun logicaFiltrado(title: String = filterTitle.text,
                      minDate: Date = filterMinDate.selectedDateMillis?.let { Date(it) } ?: Date(defaultMinDate),
                      maxDate: Date = filterMaxDate.selectedDateMillis?.let { Date(it) } ?: Date(),
                      season: Int = filterSeason, episode: Int = filterEpisode,
                      isView: Boolean = isViewEnabled, order: Boolean = isOrder
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
                BottomNavItem.FILTERS,
                navigateToAllEpisodes,
                { /** es esta pantalla, no necesita navegar */ },
                navigateToFavoriteEpisode
            )
        },
        topBar = {
            TopBarComponent(
                title = stringResource(R.string.filtro_de_episodios),
                onNavigationArrowBack = navigationArrowBack
            )
        }
    ) { paddingValues ->

        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            constraintSet = episodesFilterContraintSet()
        ){
            // DEFINIR TODOS LOS FILTROS
            // _______________ TEXTFIELD _______________
            Box(modifier = Modifier
                .layoutId("idTextFieldFilter")
                .padding(horizontal = 10.dp, vertical = 5.dp)
                .background(
                    MaterialTheme.colorScheme.secondary,
                    shape = MaterialTheme.shapes.small
                )
                .padding(6.dp))
            {
                OutlinedTextField(
                    value = filterTitle,
                    onValueChange = { newValue ->
                        filterTitle = newValue
                    },
                    label = {
                        Text(
                            text = stringResource(R.string.nombre_del_personaje),
                            color = MaterialTheme.colorScheme.onSecondary
                        )
                    },
                    placeholder = {
                        Text(
                            text = stringResource(R.string.ejemplos_personajes),
                            color = MaterialTheme.colorScheme.onSecondary
                        )
                    },
                    trailingIcon = {
                        if (filterTitle.text.isNotEmpty()) {
                            IconButton(onClick = { filterTitle = TextFieldValue("") }) {
                                Icon(imageVector = Icons.Filled.Close, contentDescription = "Borrar texto")
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth() // 🔹 Para que ocupe todo el ancho del Box
                )
            }
            // _________________________________________
            // _______________ FILTRA 1 DE FILTRO: 2 DatePickerDialogComponent y Switch _______________
            Row (
                modifier = Modifier
                    .layoutId("idColumnDate")
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
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
                    title = stringResource(R.string.fecha_inicial)
                )

                DatePickerDialogComponent(
                    modifier = Modifier.background(MaterialTheme.colorScheme.secondary),
                    showDialogDate = showDialogMaxDate,
                    onDismissRequest = { showDialogMaxDate = false },
                    onAdmitRequest = { showDialogMaxDate = true },
                    filterDate = filterMaxDate,
                    onClick = {
                        showDialogMaxDate = false
                        logicaFiltrado()
                    },
                    title = stringResource(R.string.fechan_fin)
                )

                // Switch para marcar si está visto
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(stringResource(R.string.visto))
                    Switch(
                        checked = isViewEnabled,
                        onCheckedChange = {
                            isViewEnabled = !isViewEnabled // logicaFiltrado()
                        },
                        colors = SwitchDefaults.colors(
                                checkedThumbColor = MaterialTheme.colorScheme.onPrimary,
                                checkedTrackColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.2f) // Fondo amarillo con transparencia
                        )
                    )
                }
            }
            // _________________________________________
            // _______________ FILTRA 2 DE FILTRO: 2 DropdownMenuComponent y IconButton _______________
            Row(
                modifier = Modifier
                    .layoutId("idColumnPicker")
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {

                DropdownMenuComponent(
                    label = stringResource(R.string.temporada),
                    selectedItem = filterSeason,
                    items = uniqueSeasons,
                    onItemSelected = { filterSeasonItem ->
                        filterSeason = filterSeasonItem // logicaFiltrado(season = filterSeasonItem)
                    }
                )

                DropdownMenuComponent(
                    label = stringResource(R.string.capitulo),
                    selectedItem = filterEpisode,
                    items = uniqueEpisodes,
                    onItemSelected = { filterEpisodeItem ->
                        filterEpisode = filterEpisodeItem // logicaFiltrado(episode = filterEpisodeItem)
                    }
                )

                IconButton(onClick = {
                    isOrder = !isOrder
                    viewModel.getEpisodesOrder(isOrder)
                }, modifier = Modifier
                    .clip(RoundedCornerShape(50))
                    .background(MaterialTheme.colorScheme.secondary) )
                {
                    Icon(
                        tint = MaterialTheme.colorScheme.onSecondary, // Color correcto del icono
                        imageVector = if (isOrder) Icons.Default.ArrowUpward else Icons.Default.ArrowDownward,
                        contentDescription = stringResource(R.string.ordenar)
                    )
                }

            }
            // _________________________________________

            Box(
                modifier = Modifier
                    .layoutId("idListEpisodes")
                    .padding(bottom = 10.dp),
                //.fillMaxSize(), // 🔹 Se asegura de ocupar el espacio disponible
                // contentAlignment = Alignment.Center // 🔹 Centra el contenido
            ) {
                if(stateAllEpisodes.value.isLoading){ // state.value.isLoading ||
                    CircularProgressIndicator( color = MaterialTheme.colorScheme.onPrimary )
                }
                else {
                        if (state.value.episodes.isEmpty()) {
                            NoContentComponent(
                                modifier = Modifier
                                    .layoutId("idListEpisodes")
                                    .fillMaxSize()
                                    .background(Color(0xFF0F1A35)),
                                titleText = stringResource(R.string.title_episodes),
                                infoText = stringResource(R.string.details_episodes)
                            )
                        } else {
                            ListEpisodes(
                                modifier = Modifier.layoutId("idListEpisodes"),
                                episodes = state.value.episodes,
                                allEpisodes = stateAllEpisodes.value.episodes,
                                onEpisodeSelected = onEpisodeSelected,
                                episodesFavDbSet = stateFavOrView.value.episodesFavSet,
                                episodesViewDbSet = stateFavOrView.value.episodesViewSet
                            )
                        }
                    }
            }
        }
    }
}

fun episodesFilterContraintSet(): ConstraintSet {
    return ConstraintSet() {
        // Creando referencias (ids)
        val (listEpisodes, textFieldFilter, columnDate, columnPicker /*iconEmptyList, titleEmptyList, subtitleEmptyList*/) =
            createRefsFor( "idListEpisodes", "idTextFieldFilter", "idColumnDate", "idColumnPicker")

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

        constrain(listEpisodes) {
            top.linkTo(columnPicker.bottom, margin = 12.dp)
            bottom.linkTo(parent.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            height = Dimension.fillToConstraints
        }
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
            colors = DatePickerDefaults.colors(
                containerColor = Color.White // Fondo del diálogo en blanco
            ),
            modifier = modifier,
            onDismissRequest = onDismissRequest,
            confirmButton = {
                TextButton(onClick = onClick) {
                    Text(stringResource(R.string.ok))
                }
            }
        ) {
            DatePicker(state = filterDate,
//                     colors = DatePickerDefaults.colors(
//                          containerColor = Color.White // Fondo interno del DatePicker en blanco
//                    )
                )
        }
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(bottom = 10.dp)) {
        Text(title, color = MaterialTheme.colorScheme.onPrimary)

        // Botón para abrir el DatePickerDialog
        OutlinedButton(onClick = onAdmitRequest,
                    colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = MaterialTheme.colorScheme.secondary // Fondo del botón
                        )) // Evita que el fondo cubra el borde del botón)
        {
            Text(
                SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(
                    filterDate.selectedDateMillis // ✅ Obtener la fecha seleccionada
                        ?: defaultMinDate
                ),
                color = MaterialTheme.colorScheme.onSecondary
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

    Box ( modifier = Modifier//.border(2.dp, Color.DarkGray, RoundedCornerShape(12.dp)) // Primero el borde
                            .clip(RoundedCornerShape(12.dp)) // Recorta el contenido dentro de la forma
                            .background(MaterialTheme.colorScheme.secondary) // Aplica el fondo dentro del borde


        ){
        val itemSelect = if (selectedItem == 0) "s" else " $selectedItem"

        TextButton(onClick = { expanded = true }) {
            Text(text = "$label$itemSelect", color = MaterialTheme.colorScheme.onSecondary)
            Icon(imageVector = Icons.Default.ArrowDropDown, contentDescription = "Expand")
        }

        DropdownMenu(expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
            // modifier = Modifier.border(10.dp, Color.DarkGray) // 🔹 Fondo oscuro
        ) {
            items.forEach { item ->
                val itemMenu = if (item == 0) stringResource(R.string.todos) else "$label $item"

                DropdownMenuItem(
                    text = { Text(  text = itemMenu,
                                    color = MaterialTheme.colorScheme.onSecondary,
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




//@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO, name = "Modo Claro")
//@Composable
//fun EpisodesFilterScreenPreview() {
//    Column {
//        EpisodesFilterScreen( navigateToAllEpisodes = {},
//                              navigateToFavoriteEpisode = {},
//                              onEpisodeSelected= {},
//                              navigationArrowBack = {})
//    }
//}