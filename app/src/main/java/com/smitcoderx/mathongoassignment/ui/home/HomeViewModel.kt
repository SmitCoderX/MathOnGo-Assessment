package com.smitcoderx.mathongoassignment.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smitcoderx.mathongoassignment.utils.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository
) : ViewModel() {

    private val _homePageUi = MutableLiveData<ResponseState<HomePageMapper>>()
    val homePageUi: LiveData<ResponseState<HomePageMapper>> = _homePageUi


    init {
        getRecipe()
    }

    private fun getRecipe() = viewModelScope.launch {
        _homePageUi.value = ResponseState.Loading()
        val user = homeRepository.getSignedInUser()
        if (user.data != null) {
            val popularRecipe = homeRepository.getPopularRecipes()
            val allRecipe = homeRepository.getAllRecipes()
            if (popularRecipe.data != null && allRecipe.data != null) {
                _homePageUi.value = ResponseState.Success(
                    HomePageMapper(
                        user.data, popularRecipe.data, allRecipe.data
                    )
                )
            } else {
                _homePageUi.value =
                    ResponseState.Error("User: ${user.message ?: "No Issue"}\nPopular Recipe: ${popularRecipe.message ?: "No Issue"}\n All Recipe: ${allRecipe.message ?: "No Issue"}")
            }
        } else {
            _homePageUi.value = ResponseState.Error("User Not LoggedIn")
        }
    }
}