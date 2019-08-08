package com.example.tikitestv2.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.tikitestv2.util.breakLine
import com.example.tikitestv2.data.network.TikiApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

enum class ApiStatus { LOADING, ERROR, DONE }
class MainViewModel : ViewModel() {

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    private val _listKey = MutableLiveData<List<String>>()

    private val _status = MutableLiveData<ApiStatus>()

    val status: LiveData<ApiStatus>
        get() = _status

    val listKey: LiveData<List<String>>
        get() = _listKey

    fun getKeys() {
        uiScope.launch {
            val getPropertiesDeferred = TikiApi.retrofitService.getKeysAsync()
            try {
                _status.value = ApiStatus.LOADING
                val builder = StringBuilder()
                val result = getPropertiesDeferred.await().map { it.breakLine(builder) }
                _status.value = ApiStatus.DONE
                _listKey.value = result
            } catch (e: Exception) {
                _status.value = ApiStatus.ERROR
                _listKey.value = ArrayList()
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}