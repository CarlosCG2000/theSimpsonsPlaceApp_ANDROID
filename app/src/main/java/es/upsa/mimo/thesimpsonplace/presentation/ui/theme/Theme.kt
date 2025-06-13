package es.upsa.mimo.thesimpsonplace.presentation.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

/** Theme.kt, asigna los colores a los atributos adecuados del colorScheme. */
private val DarkColorScheme = darkColorScheme(
    primary = BackgroundColor, // Color principal
    secondary = BackgroundComponentColor, // Color para elementos secundarios
    background = BackgroundColor, // Color de fondo de la app
    surface = BackgroundComponentColor, // Color de fondo de los componentes
    onPrimary = TextColor, // Color del texto sobre el color primario
    onSecondary = TextComponentColor, // Color del texto sobre el color secundario
    onBackground = TextComponentColor, // Color del texto sobre el fondo
    onSurface = TextComponentColor // Color del texto sobre los componentes
)

private val LightColorScheme = lightColorScheme(
    primary = BackgroundColorLight, // Color principal más claro
    secondary = BackgroundComponentColorLight, // Color para elementos secundarios más claro
    background = BackgroundColorLight, // Color de fondo de la app más claro
    surface = BackgroundComponentColorLight, // Color de fondo de los componentes más claro
    onPrimary = TextColorLight, // Color del texto sobre el color primario más claro
    onSecondary = TextComponentColorLight, // Color del texto sobre el color secundario más claro
    onBackground = TextComponentColorLight, // Color del texto sobre el fondo más claro
    onSurface = TextComponentColorLight // Color del texto sobre los componentes más claro
)

@Composable
fun TheSimpsonPlaceTheme(
    darkTheme: Boolean = isSystemInDarkTheme(), // Dynamic color is available on Android 12+
    dynamicColor: Boolean =  false, // Desactiva los colores dinámicos, usará exclusivamente los colores de DarkColorScheme y LightColorScheme.
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}