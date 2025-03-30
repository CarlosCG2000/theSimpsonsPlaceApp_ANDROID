package es.upsa.mimo.thesimpsonplace.data.mappers

import androidx.room.TypeConverter
import es.upsa.mimo.thesimpsonplace.data.entities.character.CharacterDb
import es.upsa.mimo.thesimpsonplace.data.entities.episode.EpisodeDb
import es.upsa.mimo.thesimpsonplace.data.entities.episode.EpisodeDto
import es.upsa.mimo.thesimpsonplace.data.utils.toDate
import es.upsa.mimo.thesimpsonplace.domain.entities.Character
import es.upsa.mimo.thesimpsonplace.domain.entities.Episode
import java.util.Date
import kotlin.String

// Función de Extensión para convertir EpisodeDto a Episode
fun EpisodeDto.toEpisode(): Episode{

    val idOpt: String = ((temporada ?: 0) * 3 + (episodio ?: 0)).toString()
    val lanzamientoDate: Date = lanzamiento?.toDate() ?: Date(0) // '.toDate()' es una extensión personalizada. Si es null, usa fecha 1970

    return Episode(
        id =  id ?: idOpt, // Si no existe (null) se calcula uno unico (basado en su capitulo y temporada),
        titulo =  titulo ?: "Unknown",
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
fun toTimestamp(date: Date): Long {
    return date.time
}

// Lo pasamos de la fuente de información a la BD siendo (ambos falsos, uno de ello verdadero o ambos verdaderos). Ya que no es como en 'Character' y 'Quote' que si estaban en la BD es porque es favorito aqui pueden estar los episodios en la BD y luego no estar ni en favoritos ni en vistos o estar solo en una de las dos cosas.
fun Episode.toEpisodeDb(isFav: Boolean = false,
                        isView: Boolean = false): EpisodeDb {
    return EpisodeDb( id = id,
        titulo =  titulo,
        temporada = temporada,
        episodio = episodio,
        lanzamiento = toTimestamp(lanzamiento), // Se convierte de Date a Long,
        directores = directores,
        escritores = escritores,
        descripcion = descripcion,
        valoracion = valoracion,
        invitados = invitados,
        esFavorito = isFav,
        esVisto = isView)
}

fun toDate(timestamp: Long): Date {
    return Date(timestamp)
}

fun EpisodeDb.toEpisode(): Episode {
    return Episode( id = id,
        titulo =  titulo,
        temporada = temporada ,
        episodio = episodio,
        lanzamiento = toDate(lanzamiento), // Se convierte de String a Date,
        directores = directores ,
        escritores = escritores ,
        descripcion = descripcion ,
        valoracion = valoracion ,
        invitados = invitados,
        esFavorito = esFavorito,
        esVisto = esVisto)
}



//
//fun CharacterDb.toCharacter(): es.upsa.mimo.thesimpsonplace.domain.entities.Character {
//    return Character( id = id,
//        nombre = nombre,
//        genero = genero,
//        imagen = imagen,
//        esFavorito = true
//    )
//}