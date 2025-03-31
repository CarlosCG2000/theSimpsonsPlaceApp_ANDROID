package es.upsa.mimo.thesimpsonplace.domain.models

import java.util.Date

data class Episode(
    val id: String,
    val titulo: String,
    val temporada: Int,
    val episodio: Int,
    val lanzamiento: Date,
    val directores: List<String>,
    val escritores: List<String>,
    val descripcion: String,
    val valoracion: Boolean,
    val invitados: List<String>,
    var esVisto: Boolean = false,   // Atributo para saber si el episodio ya ha sido visto (persistente en la DB)
    var esFavorito: Boolean = false // Atributo para saber si el episodio es favorito (persistente en la DB)
)