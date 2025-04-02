package es.upsa.mimo.thesimpsonplace.presentation.ui.screen.profileSection

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import es.upsa.mimo.thesimpsonplace.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarProfileComponent(userName: String = stringResource(R.string.an_nimo),
                           onNavigationArrowBack:() -> Unit,
                           onNavigationProfileForm:() -> Unit /**Para el el icono del menú*/) {

    CenterAlignedTopAppBar( // Puede ser tambien: CenterAlignedTopAppBar, MediumTopAppBar, LargeTopAppBar

        title = { Text( text = stringResource(R.string.usuario, userName),
                        fontSize = 22.sp,
                        fontWeight = Bold,
                        textAlign = TextAlign.Center) },

        navigationIcon = { // Icono del menú
            IconButton(onClick = onNavigationArrowBack) {
                Icon(
                    imageVector = Icons.Default.ArrowBackIosNew,
                    contentDescription = stringResource(R.string.icono_de_regreso_al_men),
                )
            }
        },

        actions = {
            IconButton(onClick = { onNavigationProfileForm() }) {
                Icon(   imageVector = Icons.Default.Settings,
                        contentDescription = stringResource(R.string.icono_con_tres_puntitos)
                )
            }

        }
    )

}






@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO, name = "Modo Claro")
@Composable
fun TopBarProfileComponentPreview() {
    TopBarProfileComponent("Pepito ", {}, {})
}

