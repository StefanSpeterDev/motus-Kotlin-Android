package com.mpwd2.momomotus.data.repositories

import com.mpwd2.momomotus.data.dataSources.remote.api.WordRemoteSource
import com.mpwd2.momomotus.data.entities.Word
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.random.Random

@Singleton
class WordRepository @Inject constructor(private val remoteSource: WordRemoteSource) {

    fun getRandomWord(): Flow<Word> = flow {
        val words = remoteSource.getFilteredWords()
    emit(words[Random.nextInt(words.size)])
    }
}