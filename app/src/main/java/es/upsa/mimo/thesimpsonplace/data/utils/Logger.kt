package es.upsa.mimo.thesimpsonplace.data.utils

import android.util.Log

interface Logger {

    val tag: String // '= javaClass.simpleName', no se pueden añadir properties (estados) en interfaces
        get() = javaClass.simpleName

    // Verbose: para detalles muy extensos (diagnostico).
    fun logVerbose( message:String ){
        Log.v(tag, message)
    }

    // Debug: información util para depuracin.
    fun logDebug( message:String ){
        Log.d(tag, message)
    }

    // Info: informaciónn general sobre el flujo de la app.
    fun logInfo( message:String ){
        Log.i(tag, message)
    }

    // Warning: advertencias sobre posibles problemas.
    fun logWarning( message:String ){
        Log.w(tag, message)
    }

    // Error: errores que requieren atención.
    fun logError( message:String ){
        Log.e(tag, message)
    }

    // Assert: errores graves que no deberian ocurrir.
    fun logAssert( message:String ){
        Log.wtf(tag, message)
    }
}