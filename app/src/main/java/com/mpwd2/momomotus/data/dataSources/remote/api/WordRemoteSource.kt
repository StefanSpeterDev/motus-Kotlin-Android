package com.mpwd2.momomotus.data.dataSources.remote.api

import com.mpwd2.momomotus.data.entities.Word
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WordRemoteSource @Inject constructor(private val wordApi: WordApi) {

    private var cachedWords: List<Word>? = null

    suspend fun getFilteredWords(): List<Word> = withContext(Dispatchers.IO) {
        var cachedCategories = cachedWords
        if(cachedCategories == null) {
            cachedCategories = wordApi.getAll()
            cachedWords = cachedCategories.filter { word -> word.name.length in 5..7 }
        }
        return@withContext cachedWords!!
    }

}