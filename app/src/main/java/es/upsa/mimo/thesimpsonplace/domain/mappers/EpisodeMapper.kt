package es.upsa.mimo.thesimpsonplace.domain.mappers

import es.upsa.mimo.thesimpsonplace.data.entities.episode.EpisodeDb
import es.upsa.mimo.thesimpsonplace.domain.entities.Episode

fun Episode.toEpisodeDb(isFav: Boolean = false,
                        isView: Boolean = false): EpisodeDb {
    return EpisodeDb( id = id,
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
