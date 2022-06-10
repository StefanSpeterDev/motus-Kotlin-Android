package com.mpwd2.momomotus.ui.pages.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mpwd2.momomotus.data.entities.State
import com.mpwd2.momomotus.data.entities.Word
import com.mpwd2.momomotus.data.repositories.WordRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(private val repository: WordRepository) : ViewModel() {

    init {
        getWordOfTheDay()
    }

    private val mState = MutableStateFlow<State<Word>>(State.loading())

    private lateinit var motATrouve: String

    val state: StateFlow<State<Word>>
        get() = mState

    // Mot que l'user tape
    var currentWord: String = ""

    private fun getWordOfTheDay() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
//                repository.getRandomWord().collect {
                    val abc = Word(name="poires", wordId="12",id="12")
                    motATrouve = abc.name
                    mState.value = State.success(abc)
//                }
            } catch (ex: Exception) {
                mState.value = State.failed(ex.localizedMessage)
            }
        }
    }

    fun checkWord() {
        if (mState.value is State.Success) {
            // Check si le mot est trouvé
            if (currentWord == motATrouve) {
                println("Found it!")
            }
            val word = motATrouve.toList()
            val line = currentWord.toList()
            for(i in 0..word.size-1){
                val motLettre = line[i]
                val motAtrouverLettre = word[i]
                if(motAtrouverLettre == motLettre){
                    println( motLettre+" bien placée")
                    //lettre bien placée
                }
                else if(word.contains(motLettre) && motAtrouverLettre != motLettre){
                    //lettre dans le mot mais pas bien placée
                    println(motLettre+"dans le mot")
                }
                else{
                    println(motLettre+" pas dans le mot")
                    //lettre pas dans mot
                }
            }
        }
    }

    fun resetWord() {
        currentWord = ""
    }

}