package com.mpwd2.momomotus.ui.pages.game2

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
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameViewModel2 @Inject constructor(private val repository: WordRepository): ViewModel(){

    init{
        getWordOfTheDay()
    }

    lateinit var motAtrouve : String
    private val mState = MutableStateFlow<State<Word>>(State.loading())
    val state: StateFlow<State<Word>>
        get() = mState

    var currentWord = ""
    var mStateCurWord = MutableStateFlow<State<String>>(State.loading())
    val stateCurWord: StateFlow<State<String>>
        get() = mStateCurWord


    var mStateTest = MutableStateFlow<State<MutableList<MutableList<Letter>>>>(State.loading())
    val stateTest: StateFlow<State<MutableList<MutableList<Letter>>>>
        get() = mStateTest

    var letterTab: MutableList<MutableList<Letter>> = mutableListOf()

    fun initList(){
        for(y in 1 until 7){
            var listLetter : MutableList<Letter> = mutableListOf()
            for(i in 0 until motAtrouve.toList().size){
                if(y == 1)listLetter.add(Letter(Color.Blue, false, ""))else listLetter.add(
                    Letter(
                        Color.Blue, true, "")
                )
            }
            letterTab.add(listLetter)
        }
    }

    fun checkWin(row: Int, mContext: Context){
        if(mState.value is State.Success){
            if(motAtrouve.length == currentWord.length){
                if(motAtrouve == currentWord){
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

    fun checkLetter(row: Int){
        println("row:" +row.toString())
        var word = motAtrouve.toList()
        var line = currentWord.toList()
        for(i in 0..word.size-1){
            val motLettre = line[i]
            val motAtrouverLettre = word[i]
            if(motAtrouverLettre == motLettre){
                letterTab[row][i] = Letter(Color.Red, false, motLettre.toString())
            }
            else if(word.contains(motLettre) && motAtrouverLettre != motLettre){
                letterTab[row][i] = Letter(Color(0xFFC07504), false, motLettre.toString())
            }
            else{
                letterTab[row][i] = Letter(Color.Blue, false, motLettre.toString())
                println(motLettre+" pas dans le mot")
            }
        }
        mStateTest.value = State.success(letterTab)
    }

    private fun getWordOfTheDay(){
        viewModelScope.launch(Dispatchers.IO){
            val word = Word("poule", "1", "1")
            motAtrouve = word.name
            mState.value = State.success(word)
            initList()
//            try{
//                repository.getRandomWord().collect(){
//                    motAtrouve = it.name
//                    mState.value = State.success(it)
//                }
//            }catch(ex: Exception){
//                mState.value = State.failed(ex.localizedMessage)
//            }
        }
    }
}