package es.upsa.mimo.thesimpsonplace.presentation.ui.component.game

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import es.upsa.mimo.thesimpsonplace.R
import java.util.Locale

@Composable
fun HistoryGameStatistics(totalQuestions: Int, correctAnswers: Int, size: Dp, paddingText: Dp = 0.dp, title:String) {
    val successPercentage = ((correctAnswers.toFloat() / totalQuestions) * 100).let {
        String.format(Locale.US, "%.2f", it).toFloat()
    }
    val failurePercentage = 100 - successPercentage

    Column(
        modifier = Modifier
            .padding(12.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.secondary) // Color similar al de la imagen
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 🔹 Título
        Text(
            text = title,
            fontSize = 20.sp,
            fontWeight = Bold,
            color = MaterialTheme.colorScheme.onSecondary,
            modifier = Modifier.padding(bottom = paddingText)
        )

        Spacer(modifier = Modifier.height(12.dp))

        // 🔹 Gráfico circular
        PieChart(
            successPercentage = successPercentage,
            failurePercentage = failurePercentage,
            canvasSize = size
        )

        Spacer(modifier = Modifier.height(12.dp))

        // 🔹 Leyenda de estadísticas
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Circle,
                contentDescription = stringResource(R.string.success),
                tint = Color.Green,
                modifier = Modifier.size(12.dp)
            )
            Text(
                text = stringResource(R.string.success)  + " $successPercentage %",
                color = MaterialTheme.colorScheme.onSecondary,
                fontSize = 16.sp,
                modifier = Modifier.padding(start = 4.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Icon(
                imageVector = Icons.Default.Circle,
                contentDescription = stringResource(R.string.failures),
                tint = Color.Red,
                modifier = Modifier.size(12.dp)
            )
            Text(
                text = stringResource(  R.string.failures) + " $failurePercentage %",
                color = MaterialTheme.colorScheme.onSecondary,
                fontSize = 16.sp,
                modifier = Modifier.padding(start = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // 🔹 Resumen de respuestas correctas
        Text(
            text = stringResource(R.string.correct_answers_of, correctAnswers, totalQuestions),
            fontSize = 18.sp,
            fontWeight = Bold,
            color = MaterialTheme.colorScheme.onSecondary
        )
    }

}
