package es.upsa.mimo.thesimpsonplace.presentation.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.runtime.Composable

// Componsable personalizado del icono del ojo, que dependiendo del parámetro muestra uno u otro 'ojo'.
@Composable
fun PassVisibleIcon(visible: Boolean, onVisibleChange:(Boolean) -> Unit) {
    IconToggleButton(
        checked = visible,
        onCheckedChange = { onVisibleChange(it) }
    ) {
        Icon(
            imageVector = if (visible) {
                Icons.Default.Visibility
            } else {
                Icons.Default.VisibilityOff
            },
            contentDescription = "Icono del Ojo"
        )
    }
}