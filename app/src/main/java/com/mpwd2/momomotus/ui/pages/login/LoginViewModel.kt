package com.mpwd2.momomotus.ui.pages.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mpwd2.momomotus.data.entities.State
import com.mpwd2.momomotus.data.entities.User
import com.mpwd2.momomotus.data.repositories.AuthRepository
import com.mpwd2.momomotus.data.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val mLoginState = MutableStateFlow<State<User>>(State.loading())

    val loginState: StateFlow<State<User>>
        get() = mLoginState

    fun login(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                authRepository.login(email, password).collect {
                    it?.let {
                        userRepository.getUserById(it.uid).collect {
                            if (it != null) {
                                mLoginState.value = State.success(it)
                            } else {
                                mLoginState.value = State.failed("failed")
                            }
                            Log.d("LOGGED", "$it")

                        }
                    }

                }
            } catch (ex: Exception) {
                mLoginState.value = State.failed(ex.localizedMessage)
            }
        }
    }
}