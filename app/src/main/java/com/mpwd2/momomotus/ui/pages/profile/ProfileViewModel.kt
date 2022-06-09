package com.mpwd2.momomotus.ui.pages.profile

import androidx.lifecycle.ViewModel
import com.mpwd2.momomotus.data.entities.State
import com.mpwd2.momomotus.data.entities.User
import com.mpwd2.momomotus.data.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject


@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val mProfileViewModel = MutableStateFlow<State<User>>(State.loading())

    val profileState : StateFlow<State<User>>
        get() = mProfileViewModel

     fun getUser(): User {
         return userRepository.getCurrentUser()
     }
}