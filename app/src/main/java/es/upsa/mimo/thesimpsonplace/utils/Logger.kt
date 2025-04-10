package es.upsa.mimo.thesimpsonplace.utils

import android.util.Log
import es.upsa.mimo.thesimpsonplace.BuildConfig

class LoggerClass : Logger // para poder llamarla en funciones y utlizarla

interface Logger {

    val tag: String // '= javaClass.simpleName', no se pueden añadir properties (estados) en interfaces
        get() = javaClass.simpleName

    // Verbose: para detalles muy extensos (diagnostico).
    fun logVerbose( message:String ){
        if (BuildConfig.ENABLE_LOGGING)
            Log.v(tag, message)
    }

    // Debug: información util para depuracin.
    fun logDebug( message:String ){
        if (BuildConfig.ENABLE_LOGGING)
            Log.d(tag, message)
    }

    // Info: informaciónn general sobre el flujo de la app.
    fun logInfo( message:String ){
        if (BuildConfig.ENABLE_LOGGING)
            Log.i(tag, message)
    }

    // Warning: advertencias sobre posibles problemas.
    fun logWarning( message:String ){
        if (BuildConfig.ENABLE_LOGGING)
            Log.w(tag, message)
    }

    // Error: errores que requieren atención.
    fun logError( message:String ){
        if (BuildConfig.ENABLE_LOGGING)
            Log.e(tag, message)
    }

    // Assert: errores graves que no deberian ocurrir.
    fun logAssert( message:String ){
        if (BuildConfig.ENABLE_LOGGING)
            Log.wtf(tag, message)
    }
}

