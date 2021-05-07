package com.farhan.twitter.presentation.vm

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farhan.twitter.model.Response
import kotlinx.coroutines.cancel

/**
 * Created by Mohd Farhan on 5/6/2021.
 */
abstract class BaseVM : ViewModel() , LifecycleObserver {

    val liveData = MutableLiveData<Response<Any>>()

    fun responseLiveData() : MutableLiveData<Response<Any>> {
        return liveData
    }

    override fun onCleared() {
        viewModelScope.cancel()
        super.onCleared()
    }
}