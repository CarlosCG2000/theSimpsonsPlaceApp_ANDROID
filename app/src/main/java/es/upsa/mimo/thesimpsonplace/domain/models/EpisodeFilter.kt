package es.upsa.mimo.thesimpsonplace.domain.models

import androidx.compose.ui.text.input.TextFieldValue
import java.util.Calendar
import java.util.Date

// Filtros del episodio para simplificar en el ViewModel y en el UI
data class EpisodeFilter(
    val title: TextFieldValue = TextFieldValue(""),
    val minDate: Date = Date(Calendar.getInstance().apply { set(1988, Calendar.DECEMBER, 17) }.timeInMillis),
    val maxDate: Date = Date(),
    val season: Int = 0,
    val episode: Int = 0,
    val isViewEnabled: Boolean = false,
    val isOrderDesc: Boolean = false
)