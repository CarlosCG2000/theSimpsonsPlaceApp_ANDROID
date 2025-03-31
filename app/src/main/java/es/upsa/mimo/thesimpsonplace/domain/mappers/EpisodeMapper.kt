package es.upsa.mimo.thesimpsonplace.domain.mappers

import es.upsa.mimo.thesimpsonplace.data.entities.episode.EpisodeEntity
import es.upsa.mimo.thesimpsonplace.domain.entities.Episode

fun Episode.toEpisodeDb(isFav: Boolean = false,
                        isView: Boolean = false): EpisodeEntity {
    return EpisodeEntity( id = id,
        titulo =  titulo,
        temporada = temporada,
        episodio = episodio,
        lanzamiento = lanzamiento, // Se convierte de Date a Long,
        directores = directores,
        escritores = escritores,
        descripcion = descripcion,
        valoracion = valoracion,
        invitados = invitados,
        esFavorito = isFav,
        esVisto = isView)
}
