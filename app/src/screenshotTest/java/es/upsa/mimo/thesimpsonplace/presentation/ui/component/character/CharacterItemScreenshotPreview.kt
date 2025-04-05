package es.upsa.mimo.thesimpsonplace.presentation.ui.component.character

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import es.upsa.mimo.thesimpsonplace.data.entities.character.Gender
import es.upsa.mimo.thesimpsonplace.domain.models.Character

// ############# Video de referencia de Antonio en Youtube: https://www.youtube.com/watch?v=w-Iu_-sl9n8 #############
// 1. Para ejecutar y se cree el ScreenShot en la terminal:  ./gradlew updateDebugScreenshotTest
// 2. Para ver los screenshots generados: debug/screenshotsTest/.../character/CharacterItemScreenshotPreview/CharacterItemScreenshotPreview.png
// 3. Ejecutar la tarea de que los test pasan, en el Gradle de Android Studio (el elefante, a la derecha de la pantalla, esquina superior derecha) buscamos 'verifyDebugScreenshotTest' y le pulsamos.
// 4. Comprobar el resultado del test en reports/screenshotTests/preview/...index.html y le damos a 'open in' en el navegador (Browser)
// 5.Imaginamos que cambiamos el 'CharacterItem' en nuestra aplicación y le damos al play del test para que lo ejecute ahora y lo compare con el screenshot que tenemos guardado ahora va a fallar y si vamos a reports/screenshotTests/preview/...index.html y le damos a 'open in' en el navegador (Browser) y vemos que ha fallado y nos dice que ha cambiado el screenshot y nos dice que lo actualicemos viendo las diferencias de las imágenes.
// 6. Si la ultima imagen es la que queremos guardar, realziamos el punto 1. para que se actualice el screenshot y lo guardemos.

class CharacterItemScreenshotPreview {
    @Preview(showBackground = true)
    @Composable
    private fun CharacterItemPreview() {

        CharacterItem(
            modifier = Modifier.fillMaxWidth()
                .padding(16.dp),

            character = Character(id = 1,
                nombre = "Homer Simpson",
                imagen = "homer_simpson",
                genero = Gender.Male,
                esFavorito = true),
            isFavorite = true,
            onToggleFavorite = {  }
        )

    }
}