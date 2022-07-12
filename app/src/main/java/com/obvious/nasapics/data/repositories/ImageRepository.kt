package com.obvious.nasapics.data.repositories

import android.util.Log
import com.obvious.nasapics.data.local.ImageResultDao
import com.obvious.nasapics.data.models.ImageResult
import com.obvious.nasapics.data.remote.ApiService
import javax.inject.Inject

class ImageRepository @Inject constructor(
    private val imageResultDao: ImageResultDao,
    private val apiService: ApiService
) {
    suspend fun getImages(refresh:Boolean=false):List<ImageResult>{
        try {
//            val imageList = imageResultDao.getImages()
//            if (!refresh && imageList.isNotEmpty())
//                return imageList
            val newImageList=apiService.getImages()
//            for(i in newImageList.indices){
//                Log.e("ImageRepo","imageList: ${newImageList[i]}")
//                imageResultDao.insertImage(newImageList[i])
//            }
            return newImageList
        }catch (e:Exception){
            e.printStackTrace()
            return ArrayList()
        }
    }
}