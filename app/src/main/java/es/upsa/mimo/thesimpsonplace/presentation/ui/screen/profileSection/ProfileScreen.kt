package es.upsa.mimo.thesimpsonplace.presentation.ui.screen.profileSection

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import es.upsa.mimo.thesimpsonplace.presentation.viewmodel.profile.ProfileViewModel


@Composable
fun ProfileScreen(onNavigationProfileForm:() -> Unit,
                  navigationArrowBack:() -> Unit,
                  viewModel: ProfileViewModel = hiltViewModel()) {

    val userPreference by viewModel.userState.collectAsState()

    Scaffold(
        topBar = {
            TopBarProfileComponent(
                userName = userPreference.user.username,
                onNavigationProfileForm = onNavigationProfileForm,
                onNavigationArrowBack = navigationArrowBack
            )
        }) { paddingValues ->  // 👈 Recibe el padding generado por Scaffold

        Box(modifier = Modifier.fillMaxSize()
                                .background(Color.Cyan)
                                .padding(paddingValues), // 👈 Aplica el padding aquí
            contentAlignment = Alignment.Center) {
            Column(modifier = Modifier.fillMaxSize(), // Ocupa toda la pantalla
                verticalArrangement = Arrangement.Center, // Centra verticalmente dentro de Column
                horizontalAlignment = Alignment.CenterHorizontally){ // Centra horizontalmente

                Text("Tema aplicacion ${if (userPreference.user.darkMode) "Oscuro" else "Claro"}", fontSize = 24.sp, fontWeight = Bold)

            }
        }
    }

}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO, name = "Modo Claro")
@Composable
fun ProfileScreenPreview() {
    ProfileScreen({}, {})
}