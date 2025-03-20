package es.upsa.mimo.thesimpsonplace.domain.entities

import java.net.URL
import java.util.UUID

data class Question(
    val id: UUID = UUID.randomUUID(),
    val cita: String,
    val personajeCorrecto: String,
    val imagen: URL, // URL en forma de String
    val personajeIncorrectos: List<String>
)