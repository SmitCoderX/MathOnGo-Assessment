package com.smitcoderx.mathongoassignment.ui.favourite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smitcoderx.mathongoassignment.models.recipe.Recipe
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouriteViewModel @Inject constructor(private val favouriteRepository: FavouriteRepository) :
    ViewModel() {

    private val _favListLiveData = MutableLiveData<List<Recipe>>()
    val favListLiveData: LiveData<List<Recipe>> = _favListLiveData


    fun getSavedList() = viewModelScope.launch {
        val savedList = favouriteRepository.getSavedList()
        _favListLiveData.value = savedList
    }

}