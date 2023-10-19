package com.parkeasy.android.data.auth.remote

import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.parkeasy.android.R
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRemoteDataSource @Inject constructor(private val auth: FirebaseAuth, @ApplicationContext private val context: Context) {
    /*private val googleSignInClient by lazy {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        GoogleSignIn.getClient(context, gso)
    }*/

    suspend fun register(email: String, password: String): String {
        val authResult = auth.createUserWithEmailAndPassword(email, password).await()
        return authResult.user?.uid ?: ""
    }

    suspend fun login(email: String, password: String): String {
        val authResult = auth.signInWithEmailAndPassword(email, password).await()
        return authResult.user?.uid ?: ""
    }

    suspend fun loginWithGoogle() {

    }

    fun logout() {
        auth.signOut()
    }
}