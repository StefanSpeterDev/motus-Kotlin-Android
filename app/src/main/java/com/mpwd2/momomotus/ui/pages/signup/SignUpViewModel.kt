package com.mpwd2.momomotus.ui.pages.signup

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
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val mSignUpState = MutableStateFlow<State<Boolean>>(State.loading())

    val signUpState: StateFlow<State<Boolean>>
        get() = mSignUpState

    fun signUp(email :String, password: String, user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                authRepository.signUp(email, password).collect {
                    it?.let {
                        userRepository.addUser(it.uid, user).collect {
                            if(it) {
                                mSignUpState.value = State.success(it)
                            } else {
                                mSignUpState.value = State.failed("error")
                            }
                        }
                    }
                }
            } catch (ex: Exception) {
                mSignUpState.value = State.failed(ex.localizedMessage)
            }
        }
    }

}