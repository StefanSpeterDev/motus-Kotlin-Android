package com.mpwd2.momomotus.ui.pages.leaderboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mpwd2.momomotus.data.entities.State
import com.mpwd2.momomotus.data.entities.User
import com.mpwd2.momomotus.data.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LeaderboardViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val mLeaderboardViewModel = MutableStateFlow<State<List<User?>>>(State.loading())

    val leaderboardState: StateFlow<State<List<User?>>>
        get() = mLeaderboardViewModel

    init {
        getAllUsers()
    }

    private fun getAllUsers() {
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.getAllUsers().collect {
                if (it != null) {
                    mLeaderboardViewModel.value = State.success(it)
                } else {
                    mLeaderboardViewModel.value = State.failed("failed")
                }
            }
        }
    }
}