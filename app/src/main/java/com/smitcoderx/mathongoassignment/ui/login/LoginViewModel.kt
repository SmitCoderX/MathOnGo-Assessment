package com.smitcoderx.mathongoassignment.ui.login

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smitcoderx.mathongoassignment.models.UserData
import com.smitcoderx.mathongoassignment.utils.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val repository: LoginRepository) : ViewModel() {

    private val _loginLiveData = MutableLiveData<ResponseState<UserData>?>()
    val loginLiveData: LiveData<ResponseState<UserData>?> = _loginLiveData


    fun getCredentialData(credential: Bundle) = viewModelScope.launch {
        _loginLiveData.value = ResponseState.Loading()
        val loginResult = repository.signInWithCredential(credential)
        _loginLiveData.value = loginResult
    }

}