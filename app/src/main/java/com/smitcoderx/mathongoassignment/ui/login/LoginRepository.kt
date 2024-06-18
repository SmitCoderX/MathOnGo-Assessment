package com.smitcoderx.mathongoassignment.ui.login

import android.os.Bundle
import android.util.Log
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.smitcoderx.mathongoassignment.models.user.UserData
import com.smitcoderx.mathongoassignment.utils.ResponseState
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




}