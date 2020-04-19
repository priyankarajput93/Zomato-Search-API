package com.project.ui

import androidx.lifecycle.MutableLiveData
import com.project.base.BaseViewModel
import com.project.data.model.SearchRequest
import com.project.data.model.SearchResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Created by Priyanka on 16/04/2020
 */

class RestaurantViewModel(val repository: RestaurantRepository) : BaseViewModel() {

    var searchState = MutableLiveData<SearchResponse>()

    fun search(request: SearchRequest, isFromRefresh: Boolean) {
        if (isValidNetwork {search(request, isFromRefresh)}) {
            if (!isFromRefresh) {
                showLoading.value = true
            }
            launch {
                val result =
                    withContext(Dispatchers.IO) {
                        repository.searchRestaurant(request)
                    }
                withContext(Dispatchers.Main) {
                    if (!isFromRefresh) {
                        showLoading.value = false
                    }
                    apiSuccess(searchState, result)
                }
            }
        }
    }
}