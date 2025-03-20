package es.upsa.mimo.thesimpsonplace.data.mappers

import es.upsa.mimo.thesimpsonplace.data.entities.episode.EpisodeDto
import es.upsa.mimo.thesimpsonplace.data.utils.toDate
import es.upsa.mimo.thesimpsonplace.domain.entities.Episode
import java.util.Date
import kotlin.String

// Función de Extensión para convertir EpisodeDto a Episode
fun EpisodeDto.toEpisode(): Episode{

    val idOpt: String = ((temporada ?: 0) * 3 + (episodio ?: 0)).toString()
    val lanzamientoDate: Date = lanzamiento?.toDate() ?: Date(0) // '.toDate()' es una extensión personalizada. Si es null, usa fecha 1970

    return Episode(
        id =  id ?: idOpt, // Si no existe (null) se calcula uno unico (basado en su capitulo y temporada),
        titulo=  titulo ?: "Unknown",
        temporada = temporada ?: 0,
        episodio = episodio ?: 0,
        lanzamiento = lanzamientoDate, // Se convierte de String a Date,
        directores = directores ?: emptyList(),
        escritores = escritores ?: emptyList(),
        descripcion = descripcion ?: "No description",
        valoracion = valoracion == true,
        invitados = invitados ?: emptyList()
//      esVisto = false,      // por defecto es 'false'
//      esFavorito = false    // por defecto es 'false'
    )
}

// ROOM
/**
    // Mapper: Convertir EpisodeEntity a Episode
    fun EpisodeEntity.toEpisode(): Episode {
    return Episode(
    id = id,
    titulo = titulo,
    temporada = temporada,
    episodio = episodio,
    lanzamiento = Date(lanzamiento), // Convertimos el timestamp a Date
    descripcion = descripcion,
    esFavorito = esFavorito,
    esVisto = esVisto
    )
    }

    // Mapper: Convertir Episode a EpisodeEntity
    fun Episode.toEpisodeEntity(): EpisodeEntity {
    return EpisodeEntity(
    id = id,
    titulo = titulo,
    temporada = temporada,
    episodio = episodio,
    lanzamiento = lanzamiento.time, // Convertimos Date a timestamp
    descripcion = descripcion,
    esFavorito = esFavorito,
    esVisto = esVisto
    )
 */
