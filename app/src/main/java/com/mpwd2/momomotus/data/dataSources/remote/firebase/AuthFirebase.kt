package com.mpwd2.momomotus.data.dataSources.remote.firebase

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthFirebase @Inject constructor() {

    private var mAuth: FirebaseAuth = FirebaseAuth.getInstance()

    fun createUser(email: String, password: String): Task<AuthResult> {
        return mAuth.createUserWithEmailAndPassword(email, password)
    }

    //fun login(email: String, password: String): Task<AuthResult> {}

}