package com.mpwd2.momomotus.data.dataSources.remote.api

import com.mpwd2.momomotus.data.entities.Word
import retrofit2.http.GET
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WordApi @Inject constructor(private val service: Service) {

    suspend fun getAll(): List<Word> = service.getAll()

    interface Service {
        @GET("Word")
        suspend fun getAll() : List<Word>
    }

    companion object {
        const val API_URL = "https://frenchwordsapi.herokuapp.com/api/"
    }

}