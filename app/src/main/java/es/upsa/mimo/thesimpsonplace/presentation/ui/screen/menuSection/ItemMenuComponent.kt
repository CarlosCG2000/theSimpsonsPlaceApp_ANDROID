package es.upsa.mimo.thesimpsonplace.presentation.ui.screen.menuSection

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Upgrade
import androidx.compose.material3.Badge
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ItemMenuComponent(navigateToCharacters: () -> Unit,
                      navigateToEpisodes: () -> Unit,
                      navigateToQuotes: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .background(MaterialTheme.colorScheme.surfaceVariant),
                contentAlignment = Alignment.Center // Centra el contenido (Text) dentro del Box
    ) {
        Text("MENÚ PRINCIPAL", fontSize = 32.sp, fontWeight = Bold)
    }

    Spacer(modifier = Modifier.height(16.dp))

    NavigationDrawerItem(
        label = { Text("Personajes" , fontSize = 16.sp) },
        icon = { Icon(imageVector = Icons.Default.Home, contentDescription = null) },
        selected = false, // true,
        onClick = { navigateToCharacters() })

    NavigationDrawerItem(
        label = { Text("Episodios", fontSize = 16.sp) },
        icon = { Icon(imageVector = Icons.Default.Upgrade, contentDescription = null) },
        badge = { Badge() { Text("3") } },
        selected = false,
        onClick = { navigateToEpisodes() })

    NavigationDrawerItem(
        label = { Text("Citas", fontSize = 16.sp) },
        icon = { Icon(imageVector = Icons.Default.Settings, contentDescription = null) },
        selected = false,
        onClick = { navigateToQuotes() })
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO, name = "Modo Claro")
@Composable
fun ItemMenuComponentPreview() {
    Column {
        ItemMenuComponent({}, {}, {})
    }
}