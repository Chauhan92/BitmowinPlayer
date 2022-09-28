package com.bitmovin.trdemo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL
import javax.inject.Inject

@HiltViewModel
class TRPlayerViewModel @Inject constructor() : ViewModel() {
    private val url = "https://mole.prod.reuters.tv/rest/v2/android/users/5/content/reutersnow/region/US/duration/30/watched"
    val state = MutableLiveData<Response>()

    init {
        viewModelScope.launch { loadData() }
    }

    private suspend fun loadData() = withContext(Dispatchers.IO) {
        val text = URL(url).readText()
        val resp: Response = Gson().fromJson(text, Response::class.java)
        state.postValue(resp)
    }

    // data //
    data class Response(
        val entity: Entity
    )

    data class Entity(
        val stream: Stream,
        val items: List<Item>,
    )

    data class Stream(
        val uri: String,
    )

    data class Item(
        val duration: Double,
        val startTime: Double,
        val edition: String,
        val title: String,
        val resources: List<Resource>
    )

    data class Resource(
        val entityType: String,
        val uri: String,
    )

}