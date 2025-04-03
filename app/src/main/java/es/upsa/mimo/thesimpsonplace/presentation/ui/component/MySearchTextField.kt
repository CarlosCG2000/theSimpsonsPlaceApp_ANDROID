package es.upsa.mimo.thesimpsonplace.presentation.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import es.upsa.mimo.thesimpsonplace.R

@Composable
fun MySearchTextField(nameFilter: TextFieldValue,
                      valueChange: (TextFieldValue) -> Unit) {
    OutlinedTextField(
        value = nameFilter,
        onValueChange = valueChange, // por defecto la propiedad onValueChange es un labmda (TextFieldValue) -> Unit
        modifier = Modifier.fillMaxWidth(),
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
            if (nameFilter.text.isNotEmpty()) {
                IconButton(onClick = { valueChange(TextFieldValue(text = "")) }) {
                    Icon(imageVector = Icons.Filled.Close, contentDescription = stringResource(R.string.borrar_texto ))
                }
            }
        }
    )
}