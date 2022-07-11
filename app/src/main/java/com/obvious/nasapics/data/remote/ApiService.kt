package com.obvious.nasapics.data.remote

import com.obvious.nasapics.data.models.ImageResult
import retrofit2.http.GET

interface ApiService {
    @GET("/data.json")
    suspend fun getImages():List<ImageResult>
}