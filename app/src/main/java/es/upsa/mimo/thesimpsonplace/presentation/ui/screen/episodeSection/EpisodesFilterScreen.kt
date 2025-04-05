package es.upsa.mimo.thesimpsonplace.presentation.ui.screen.episodeSection

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.DeleteSweep
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import es.upsa.mimo.thesimpsonplace.R
import es.upsa.mimo.thesimpsonplace.domain.models.EpisodeFilter
import es.upsa.mimo.thesimpsonplace.presentation.ui.component.BottomBarComponent
import es.upsa.mimo.thesimpsonplace.presentation.ui.component.BottomNavItem
import es.upsa.mimo.thesimpsonplace.presentation.ui.component.ModifierContainer
import es.upsa.mimo.thesimpsonplace.presentation.ui.component.MySearchTextField
import es.upsa.mimo.thesimpsonplace.presentation.ui.component.NoContentComponent
import es.upsa.mimo.thesimpsonplace.presentation.ui.component.TopBarComponent
import es.upsa.mimo.thesimpsonplace.presentation.ui.component.episode.ListEpisodes
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
import kotlin.String

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
    var state: State<ListEpisodesFilterStateUI> = viewModel.stateEpisode.collectAsState()
    val stateFavOrView: State<ListEpisodesDbStateUI> = viewModelDB.stateEpisodesFavOrView.collectAsState()

    // _____________________ FILTROS _____________________
    val episodeFilter by viewModel.episodeFilter.collectAsState()
    var showDialogMinDate by remember { mutableStateOf(false) }
    var showDialogMaxDate by remember { mutableStateOf(false) }
    val uniqueSeasons = listOf(0) + stateAllEpisodes.value.episodes.map { it.temporada }.distinct()
    val uniqueEpisodes =  listOf(0) + stateAllEpisodes.value.episodes.map { it.episodio }.distinct()
    val listState = rememberLazyListState()
    // ___________________________________________________

    LaunchedEffect(Unit) {
        if (stateAllEpisodes.value.episodes.isEmpty() && !stateAllEpisodes.value.isLoading) {
            viewModelAllEpisodes.getAllEpisodes()
        }
    }

    LaunchedEffect(stateAllEpisodes.value.episodes) {
        if (stateAllEpisodes.value.episodes.isNotEmpty()){
            viewModel.updateEpisodes(stateAllEpisodes.value.episodes)
        }
    }

    LaunchedEffect(episodeFilter) {
        if (stateAllEpisodes.value.episodes.isNotEmpty()) {
            delay(300) // Debounce
            viewModel.getEpisodesFilter(episodeFilter)

            // Scroll solo si hay elementos
            if (state.value.episodes.isNotEmpty()) {
                listState.scrollToItem(0) // vuelve arriba del todo en el listado
            }
        }
    }

    val minDateState = rememberDatePickerState(initialSelectedDateMillis = episodeFilter.minDate.time) // si salgo de la pantalla lo recuerda pero no actualiza el filtro
    val maxDateState = rememberDatePickerState(initialSelectedDateMillis = episodeFilter.maxDate.time)

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
            modifier = ModifierContainer(paddingValues),
            constraintSet = episodesFilterContraintSet()
        ){
            // @@@@@@@@@@@@ FILTROS @@@@@@@@@@@@
            // ______ TEXTFIELD ______
            Box(modifier = Modifier
                .layoutId("idTextFieldFilter")
                .padding(horizontal = 10.dp, vertical = 5.dp)
                .background(
                    MaterialTheme.colorScheme.secondary,
                    shape = MaterialTheme.shapes.small
                )
                .padding(6.dp))
            {
                MySearchTextField(nameFilter = episodeFilter.title,
                                    valueChange = { newValue -> viewModel.updateField { it.copy(title = newValue) } })
            }
            // ______ 2 DatePicker y 1 Switch ______
            Row (
                modifier = Modifier
                    .layoutId("idColumnDate")
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally)
            ) {

                // DatePickerDialog solo se muestra cuando showDialogMinDate es true
                MyDatePickerDialogComponent(
                    showDialogDate = showDialogMinDate,
                    onDismissRequest = { showDialogMinDate = false },
                    onAdmitRequest = { showDialogMinDate = true },
                    filterDate = minDateState,
                    onClick = {
                        // Actualizar el filtro con la nueva fecha seleccionada
                        minDateState.selectedDateMillis?.let { date ->
                            viewModel.updateField { it.copy(minDate = Date(date)) }
                        }
                        showDialogMinDate = false
                    },
                    title = stringResource(R.string.fecha_inicial)
                )

                MyDatePickerDialogComponent(
                    modifier = Modifier.background(MaterialTheme.colorScheme.secondary),
                    showDialogDate = showDialogMaxDate,
                    onDismissRequest = { showDialogMaxDate = false },
                    onAdmitRequest = { showDialogMaxDate = true },
                    filterDate = maxDateState,
                    onClick = {
                        // Actualizar el filtro con la nueva fecha seleccionada
                        maxDateState.selectedDateMillis?.let {date ->
                            viewModel.updateField { it.copy(maxDate = Date(date)) }
                        }
                        showDialogMaxDate = false
                    },
                    title = stringResource(R.string.fechan_fin)
                )

                // Switch para marcar si está visto
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(stringResource(R.string.visto))

                    Switch(
                        checked = episodeFilter.isViewEnabled,
                        onCheckedChange = {
                            viewModel.updateField { it.copy(isViewEnabled = !episodeFilter.isViewEnabled ) }
                        },
                        colors = SwitchDefaults.colors(
                                checkedThumbColor = MaterialTheme.colorScheme.onPrimary,
                                checkedTrackColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.2f) // Fondo amarillo con transparencia
                        )
                    )
                }
            }

            // ______ 2 DropdownMenu y 1 IconButton ______
            Row(
                modifier = Modifier
                    .layoutId("idColumnPicker")
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {

                MyDropdownMenuComponent(
                    label = stringResource(R.string.temporada),
                    selectedItem = episodeFilter.season,
                    items = uniqueSeasons,
                    onItemSelected = { filterSeasonItem ->
                        viewModel.updateField { it.copy( season = filterSeasonItem ) }
                    }
                )

                MyDropdownMenuComponent(
                    label = stringResource(R.string.capitulo),
                    selectedItem = episodeFilter.episode,
                    items = uniqueEpisodes,
                    onItemSelected = { filterEpisodeItem ->
                        viewModel.updateField { it.copy( episode = filterEpisodeItem) }
                    }
                )

                IconButton(onClick = {
                    viewModel.updateField { it.copy( isOrderDesc = !episodeFilter.isOrderDesc ) }
                }, modifier = Modifier
                    .clip(RoundedCornerShape(50))
                    .background(MaterialTheme.colorScheme.secondary) )
                {
                    Icon(
                        tint = MaterialTheme.colorScheme.onSecondary, // Color correcto del icono
                        imageVector = if (episodeFilter.isOrderDesc) Icons.Default.ArrowUpward else Icons.Default.ArrowDownward,
                        contentDescription = stringResource(R.string.ordenar)
                    )
                }

                IconButton(onClick = {
                    viewModel.updateField { EpisodeFilter() }
                }, modifier = Modifier
                    .clip(RoundedCornerShape(50))
                    .background(MaterialTheme.colorScheme.secondary) )
                {
                    Icon(
                        tint = MaterialTheme.colorScheme.onSecondary, // Color correcto del icono
                        imageVector = Icons.Default.DeleteSweep,
                        contentDescription = stringResource(R.string.reiniciar)
                    )
                }
            }

            // ______ Box principal ______
            Box(
                modifier = Modifier
                    .layoutId("idListEpisodes")
                    .padding(bottom = 10.dp)
            ) {
                if(stateAllEpisodes.value.isLoading || state.value.isLoading){
                    CircularProgressIndicator( color = MaterialTheme.colorScheme.onPrimary )
                }
                else {
                    if (state.value.episodes.isEmpty()) {
                        NoContentComponent(
                            modifier = Modifier
                                .layoutId("idListEpisodes")
                                .fillMaxSize()
                                .background(MaterialTheme.colorScheme.primary),
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
                            episodesViewDbSet = stateFavOrView.value.episodesViewSet,
                            listState = if (episodeFilter.isViewEnabled) LazyListState() else listState
                        )

                        // Mostrar posición actual en el listado
                        Text(
                            text = "Viendo ${listState.firstVisibleItemIndex + 1} de ${state.value.episodes.size}",
                            modifier = Modifier.align(Alignment.BottomEnd)
                                                .padding(10.dp) // separarlo del borde
                                                .background(
                                                    color = MaterialTheme.colorScheme.primary.copy(
                                                        alpha = 0.8f
                                                    ),
                                                    shape = RoundedCornerShape(12.dp)
                                                )
                                .padding(horizontal = 10.dp, vertical = 8.dp),
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontSize = 13.sp
                            ),
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
            }
        }
    }
}

fun episodesFilterContraintSet(): ConstraintSet {
    return ConstraintSet {
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
fun MyDatePickerDialogComponent(modifier: Modifier = Modifier,
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
fun MyDropdownMenuComponent(label: String,
                            selectedItem: Int,
                            items: List<Int>,
                            onItemSelected: (Int) -> Unit) {
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