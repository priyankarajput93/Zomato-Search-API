package com.project.api

import com.project.data.model.SearchResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Priyanka on 15/04/2020
 */


interface ApiService {

    @GET("search")
    open fun searchRestaurants(@Query("q") searchKey:String, @Query("start")start:Int,@Query("count") count:Int): Deferred<SearchResponse>

}