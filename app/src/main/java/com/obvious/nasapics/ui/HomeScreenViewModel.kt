package com.obvious.nasapics.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.obvious.nasapics.data.models.ImageResult
import com.obvious.nasapics.data.repositories.ImageRepository
import com.obvious.nasapics.utils.Constants
import com.obvious.nasapics.utils.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val imageRepository: ImageRepository
):ViewModel() {

    private val _currentState=MutableLiveData<State>()
    val currentState:LiveData<State>
    get() = _currentState

    private val _errorMessage=MutableLiveData<String>()
    val errorMessage:LiveData<String>
    get()=_errorMessage

    private val _imageList=MutableLiveData<List<ImageResult>>()
    val imageList:LiveData<List<ImageResult>>
    get() = _imageList

    fun getImages(refresh:Boolean=false)=viewModelScope.launch(Dispatchers.Main)
    {
        try {
            _currentState.value=State.LOADING
            val imageList=withContext(Dispatchers.IO){
                imageRepository.getImages(refresh)
            }
            _imageList.value=imageList
            _currentState.value=State.LOADED
        }catch (e:Exception){
            e.printStackTrace()
            _errorMessage.value=Constants.ERROR_MESSAGE
        }
    }
}