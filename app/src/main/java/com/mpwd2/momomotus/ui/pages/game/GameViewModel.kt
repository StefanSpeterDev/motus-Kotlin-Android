package com.mpwd2.momomotus.ui.pages.game

import android.content.Context
import android.widget.Toast
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mpwd2.momomotus.data.entities.Letter
import com.mpwd2.momomotus.data.entities.State
import com.mpwd2.momomotus.data.entities.Word
import com.mpwd2.momomotus.data.repositories.WordRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(private val repository: WordRepository) : ViewModel() {

    init {
        getWordOfTheDay()
    }

    private val mState = MutableStateFlow<State<Word>>(State.loading())
    private val mRowState = MutableStateFlow<State<Int>>(State.loading())

    lateinit var motATrouve: String

    val state: StateFlow<State<Word>>
        get() = mState

    val rowState: StateFlow<State<Int>>
        get() = mRowState

    var mStateTest = MutableStateFlow<State<MutableList<MutableList<Letter>>>>(State.loading())
    val stateTest: StateFlow<State<MutableList<MutableList<Letter>>>>
        get() = mStateTest

    var letterTab: MutableList<MutableList<Letter>> = mutableListOf()

    // Mot que l'user tape
    var currentWord: String = "";
    var currentRow: Int = 0


    fun initList(){
        for(y in 1 until 7){
            var listLetter : MutableList<Letter> = mutableListOf()
            for(i in 0 until motATrouve.toList().size){
                if(y == 1)listLetter.add(Letter(Color.Blue, false, ""))else listLetter.add(Letter(Color.Blue, true, ""))
            }
            letterTab.add(listLetter)
        }
    }
    private fun getWordOfTheDay() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                /* repository.getRandomWord().collect {
                     motATrouve = it.name
                     mState.value = State.success(it)
                 }*/
                var abc = Word(name = "poulet", wordId = "12", id = "21")
                motATrouve = abc.name
                mState.value = State.success(abc)
                initList()
            } catch (ex: Exception) {
                mState.value = State.failed(ex.localizedMessage)
            }
        }
    }
/*
    fun checkWin() {
       // if (state.value is State.Success) {
            // Si leur length est égal cela veut dire qu'on est à la fin de la ligne
            if (currentWord.length == motATrouve.length) {
                if (currentWord == motATrouve) {
                    println("Found it!")
                } else {
                    checkWord()
                    currentWord = motATrouve[0].plus("")
                    currentRow += 1

                    mRowState.value = State.success(currentRow);
                    println("Length word : ${motATrouve.length}")
                    println("Current word in vm: $currentWord")

                }
            }
        //}
    }*/

    fun checkWin(row: Int, mContext: Context){
        if(mState.value is State.Success){
            if(motATrouve.length == currentWord.length){
                if(motATrouve == currentWord){
                    println("win")
                    val text = "Vous avez gagné !!"
                    val duration = 1200
                    val toast = Toast.makeText(mContext, text, duration)
                    toast.show()
                }else{
                    checkLetter(row)
                    //mStateTest.value = State.success(letterTab)
                    println("nextrow")
                    //letterTab[row][letter] = Letter(Color.Red, false)
                    currentWord = ""
                }
            }

        }
    }

    fun checkWord() {
        if (mState.value is State.Success) {

            var word = motATrouve.toList()
            var line = currentWord.toList()
            println("Word: $word")
            println("Line: $line")
            for (i in word.indices - 1) {
                val motLettre = line[i]
                val motAtrouverLettre = word[i]
                if (motAtrouverLettre == motLettre) {
                    println(motLettre + " bien placée")
                    //lettre bien placée
                } else if (word.contains(motLettre) && motAtrouverLettre != motLettre) {
                    //lettre dans le mot mais pas bien placée
                    println(motLettre + "dans le mot")
                } else {
                    println(motLettre + " pas dans le mot")
                    //lettre pas dans mot
                }
            }
        }
    }

    fun checkLetter(row: Int){
        println("row:" +row.toString())
        var word = motATrouve.toList()
        var line = currentWord.toList()
        for(i in 0..word.size-1){
            val motLettre = line[i]
            val motAtrouverLettre = word[i]
            if(motAtrouverLettre == motLettre){
                letterTab[row][i] = Letter(Color.Red, false, motLettre.toString())
            }
            else if(word.contains(motLettre) && motAtrouverLettre != motLettre){
                letterTab[row][i] = Letter(Color.Yellow, false, motLettre.toString())
            }
            else{
                letterTab[row][i] = Letter(Color.Blue, false, motLettre.toString())
                println(motLettre+" pas dans le mot")
            }
        }
        mStateTest.value = State.success(letterTab)
    }


}