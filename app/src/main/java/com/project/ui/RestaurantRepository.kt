package com.project.ui

import com.project.api.ApiService
import com.project.data.model.SearchRequest
import com.project.data.model.SearchResponse
import com.project.utils.UseCaseResult

/**
 * Created by Priyanka on 16/04/2020
 */

interface RestaurantRepository {
    suspend fun searchRestaurant(request: SearchRequest): UseCaseResult<SearchResponse>
}

class RestaurantRepositoryImpl(private val service: ApiService) : RestaurantRepository {
    override suspend fun searchRestaurant(request: SearchRequest): UseCaseResult<SearchResponse> {
        return try {
            val result = service.searchRestaurants(request.q!!, request.start!!, request.count!!).await()
            UseCaseResult.Success(result)
        } catch (e: Exception) {
            UseCaseResult.Exception(e)
        }
    }

}
