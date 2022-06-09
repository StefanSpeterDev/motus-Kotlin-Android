package com.mpwd2.momomotus.data.repositories

import com.google.firebase.auth.FirebaseUser
import com.mpwd2.momomotus.data.dataSources.remote.firebase.AuthFirebase
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(private val authFirebase: AuthFirebase) {

    fun signUp(email: String, password: String): Flow<FirebaseUser?> = callbackFlow {
        authFirebase.createUser(email, password).addOnCompleteListener {
            if (it.isComplete) {
                if (it.isSuccessful) {
                    trySend(it.result.user).isSuccess
                } else {
                    trySend(null).isFailure
                }
            } else {
                trySend(null).isFailure

            }
        }
        awaitClose()
    }

    fun login(email: String, password: String): Flow<FirebaseUser?> = callbackFlow {
        authFirebase.login(email, password).addOnCompleteListener {
            if(it.isComplete) {
                if(it.isSuccessful) {
                    trySend(it.result.user).isSuccess
                } else {
                    trySend(null).isFailure
                }
            } else {
                trySend(null).isFailure
            }
        }
        awaitClose()
    }

}