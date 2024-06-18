package com.smitcoderx.mathongoassignment.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smitcoderx.mathongoassignment.models.search.SearchData
import com.smitcoderx.mathongoassignment.utils.ResponseState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val homeRepository: SearchRepository
) : ViewModel() {

    private val _searchLiveData = MutableLiveData<ResponseState<SearchData>>()
    val searchLiveData: LiveData<ResponseState<SearchData>> = _searchLiveData


    fun getQueryRecipe(query: String) = viewModelScope.launch {
        _searchLiveData.value = ResponseState.Loading()
        val searchRecipe = homeRepository.getSearchQuery(query)
        if (searchRecipe.data != null) {
            _searchLiveData.value = ResponseState.Success(searchRecipe.data)
        } else {
            _searchLiveData.value =
                ResponseState.Error(searchRecipe.message)
        }
    }
}