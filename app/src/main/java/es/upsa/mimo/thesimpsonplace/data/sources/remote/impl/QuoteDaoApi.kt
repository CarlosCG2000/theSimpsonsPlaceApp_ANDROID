package es.upsa.mimo.thesimpsonplace.data.sources.remote.impl

//❌ Problema 1: QuoteDaoApi no es necesario,
//🔍 Causa:
//No necesitas QuoteDaoApi, porque Hilt con Retrofit ya genera una implementación de QuoteDao.
//✅ Solución:
//Elimina QuoteDaoApi y cambia provideQuoteDao en NetworkModule:

//_______________________________________________
//class QuoteDaoApi: QuoteDao {
//    private val retrofit = Retrofit.Builder()
//        .baseUrl("https://thesimpsonsquoteapi.glitch.me/")
//        .build()
//
//    private val service = retrofit.create(QuoteDao::class.java)
//
//    override suspend fun getQuotes(numElementos: Int, textPersonaje: String): List<QuoteDto> {
//        return service.getQuotes(numElementos, textPersonaje)
//    }
//}