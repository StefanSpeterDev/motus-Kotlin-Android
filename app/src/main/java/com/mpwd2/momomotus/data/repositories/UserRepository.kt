package com.mpwd2.momomotus.data.repositories

import android.os.Debug
import android.util.Log
import androidx.compose.animation.core.snap
import com.google.firebase.firestore.ktx.toObject
import com.mpwd2.momomotus.data.dataSources.remote.firebase.UserFirebase
import com.mpwd2.momomotus.data.entities.User
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collect
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(private val userFirebase: UserFirebase) {

    private lateinit var currentUser: User;

    fun getUserById(id: String): Flow<User?> = callbackFlow {
        val sub = userFirebase.getUserById(id).addSnapshotListener { snapshot, ex ->
            if (snapshot!!.exists()) {
                val snapshotUser = snapshot.toObject(User::class.java)
                if (snapshotUser != null) {
                    currentUser = snapshotUser;
                }
                trySend(snapshot.toObject(User::class.java)!!).isSuccess
            } else {
                trySend(null).isFailure
            }

        }
        awaitClose()
    }

    fun getCurrentUser() = currentUser;

    fun addUser(id: String, user: User): Flow<Boolean> = callbackFlow {
        val subscription = userFirebase.addUser(id, user).addOnCompleteListener {
            if (it.isSuccessful) {
                trySend(true).isSuccess
            } else {
                trySend(false).isFailure
                cancel(it.exception?.message.toString())
            }
        }
    }

    fun getAllUsers() = callbackFlow {
        val sub = userFirebase.getAllUsers().addSnapshotListener { snapshot, ex ->
            if (snapshot != null) {
                trySend(snapshot.documents.map { it -> it.toObject(User::class.java) }
                    .sortedBy { it?.score }).isSuccess
            } else {
                trySend(null).isFailure
            }
        }
        awaitClose()
    }

}