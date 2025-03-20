package es.upsa.mimo.thesimpsonplace.domain.mappers

import es.upsa.mimo.thesimpsonplace.domain.entities.Question
import es.upsa.mimo.thesimpsonplace.domain.entities.Quote
import java.util.UUID

fun Quote.toQuestion(): Question {
    return Question(
        id = UUID.randomUUID(),
        cita = cita,
        personajeCorrecto = personaje,
        imagen = imagen,
        personajeIncorrectos = getRandomOptions(correctAnswer = personaje, from = answerPersonajes)
    )
}

// Lista de posibles respuestas (se puede mejorar obteniéndolos de la BD)
private val answerPersonajes = listOf(
    "Moe Szyslak", "Principal Skinner", "Abe Simpson", "Lisa Simpson", "Duffman",
    "Apu Nahasapeemapetilon", "Bart Simpson", "Ralph Wiggum", "Milhouse Van Houten",
    "Mr. Burns", "Comic Book Guy", "Mayor Quimby", "Chief Wiggum", "Dr. Nick",
    "Troy McClure", "Rainier Wolfcastle", "Groundskeeper Willie", "Homer Simpson",
    "Frank Grimes", "Nelson Muntz", "Waylon Smithers", "Marge Simpson"
)

// Función para obtener 3 opciones incorrectas aleatorias
fun getRandomOptions(correctAnswer: String, from: List<String>): List<String> {
    return from
        .filter { it != correctAnswer } // Excluir la respuesta correcta
        .shuffled() // Mezclar aleatoriamente los elementos
        .take(3) // Tomar los primeros 3 elementos
}