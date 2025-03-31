package es.upsa.mimo.thesimpsonplace.data.entities.user

import android.content.Context
import es.upsa.mimo.thesimpsonplace.R

data class UserPreference (
    val username: String = "Anónimo", // context.getString(R.string.anonimo)
    val darkMode: Boolean = true,
    val language: Language = Language.SPANISH
) {
//    companion object {
//        // Recibir el Context en el constructor y usarlo dentro de un bloque init
//        fun default(context: Context) = UserPreference(
//            username = context.getString(R.string.anonimo)
//        )
//    }
}