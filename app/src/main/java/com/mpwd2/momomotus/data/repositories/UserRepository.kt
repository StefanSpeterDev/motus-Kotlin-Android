package com.mpwd2.momomotus.data.repositories

import android.util.Log
import com.mpwd2.momomotus.data.dataSources.remote.firebase.UserFirebase
import com.mpwd2.momomotus.data.entities.User
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(private val userFirebase: UserFirebase) {

   fun getUserById(id: String): Flow<User> = callbackFlow {
        userFirebase.getUserById(id).addSnapshotListener { dr, ex ->
            dr?.toObject(User::class.java)
            Log.d("DR:", "get user by id $dr")
        }
    }

    fun addUser(id:String, user: User): Flow<Boolean> = callbackFlow {
        val subscription = userFirebase.addUser(id, user).addOnCompleteListener {
            if(it.isSuccessful) {
                trySend(true).isSuccess
            } else {
                trySend(false).isFailure
                cancel(it.exception?.message.toString())
            }
        }

        awaitClose { subscription.isComplete }
    }

}