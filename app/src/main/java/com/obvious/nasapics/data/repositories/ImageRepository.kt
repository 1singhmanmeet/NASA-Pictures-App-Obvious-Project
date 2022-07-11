package com.obvious.nasapics.data.repositories

import com.obvious.nasapics.data.local.ImageResultDao
import com.obvious.nasapics.data.models.ImageResult
import com.obvious.nasapics.data.remote.ApiService
import javax.inject.Inject

class ImageRepository @Inject constructor(
    private val imageResultDao: ImageResultDao,
    private val apiService: ApiService
) {
    suspend fun getImages():List<ImageResult>{
        try {
            val imageList = imageResultDao.getImages()
            if (imageList.isNotEmpty())
                return imageList
            return apiService.getImages()
        }catch (e:Exception){
            e.printStackTrace()
            return ArrayList()
        }
    }
}