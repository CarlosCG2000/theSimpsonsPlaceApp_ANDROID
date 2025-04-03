package es.upsa.mimo.thesimpsonplace.presentation.ui.component.game

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

// 🔹 Modelo de datos para la gráfica
data class PieChartData(val percentage: Float, val color: Color)

@Composable
fun PieChart(successPercentage: Float, failurePercentage: Float, canvasSize: Dp) {
    val pieEntries = listOf(
        PieChartData(successPercentage, Color.Green),
        PieChartData(failurePercentage, Color.Red)
    )

    Box(
        modifier = Modifier
            .size(200.dp)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.size(canvasSize)) {
            val totalAngle = 360f
            val strokeWidth = 150f  // Grosor del círculo
            var startAngle = -90f   // Empezamos desde arriba
            pieEntries.forEach { entry ->
                val adjustedSweepAngle = ((entry.percentage / 100) * totalAngle)
                drawArc(
                    color = entry.color,
                    startAngle = startAngle,
                    sweepAngle = adjustedSweepAngle,
                    useCenter = false,
                    size = size,
                    style = Stroke(width = strokeWidth, cap = StrokeCap.Butt)
                )
                startAngle += adjustedSweepAngle // sweepAngle // 🔹 Avanzamos al siguiente segmento
            }
        }
    }
}
