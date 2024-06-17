package com.smitcoderx.mathongoassignment.ui.login

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.smitcoderx.mathongoassignment.BuildConfig
import com.smitcoderx.mathongoassignment.models.UserData
import com.smitcoderx.mathongoassignment.utils.ResponseState
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.tasks.await
import java.util.concurrent.CancellationException
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class LoginRepository @Inject constructor(
    private val auth: FirebaseAuth
) {

    suspend fun signInWithCredential(credential: Bundle): ResponseState<UserData> {
        val googleIdToken = GoogleIdTokenCredential.createFrom(credential)
        val googleCredential = GoogleAuthProvider.getCredential(googleIdToken.idToken, null)
        return try {
            val user = auth.signInWithCredential(googleCredential).await().user
            Log.d("TAG", "signInWithCredential: ${user?.displayName}")
            ResponseState.Success(
                user?.run {
                    UserData(
                        id = uid,
                        name = displayName,
                        email = email,
                        profileUrl = photoUrl?.toString()
                    )
                }
            )
        } catch (e: Exception) {
            e.printStackTrace()
            if (e is CancellationException) throw e
            ResponseState.Error(e.message)
        }
    }

    fun getSignedInUser(): ResponseState<UserData> {
        return if (auth.currentUser != null) {
            ResponseState.Success(auth.currentUser?.run {
                UserData(
                    id = uid,
                    name = displayName,
                    email = email,
                    profileUrl = photoUrl?.toString()
                )
            }
            )
        } else {
            ResponseState.Error("Something Went Wrong")
        }
    }


}