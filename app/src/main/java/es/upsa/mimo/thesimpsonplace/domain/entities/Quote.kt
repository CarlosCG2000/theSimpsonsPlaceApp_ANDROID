package es.upsa.mimo.thesimpsonplace.domain.entities

import java.net.URL
import java.util.UUID

data class Quote (
    val id: UUID = UUID.randomUUID(), // no puedes instanciar un UUID directamente con UUID(), ya que UUID no tiene un constructor sin parámetros.
    val cita: String,
    val personaje: String,
    val imagen: URL
)

