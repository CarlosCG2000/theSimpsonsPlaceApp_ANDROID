package es.upsa.mimo.thesimpsonplace.presentation.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ModifierContainer(paddingValues: PaddingValues): Modifier {
    return Modifier.fillMaxSize()
        .padding(paddingValues)
        .background(MaterialTheme.colorScheme.primary)
}