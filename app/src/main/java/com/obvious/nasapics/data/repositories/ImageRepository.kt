package com.obvious.nasapics.data.repositories

import android.util.Log
import com.obvious.nasapics.data.local.ImageResultDao
import com.obvious.nasapics.data.models.ImageResult
import com.obvious.nasapics.data.remote.ApiService
import java.net.UnknownHostException
import javax.inject.Inject

class ImageRepository @Inject constructor(
    private val imageResultDao: ImageResultDao,
    private val apiService: ApiService
) {
    suspend fun getImages(networkAvailable:Boolean=false):List<ImageResult>{
        try {
            return if(networkAvailable) {
                val newImageList = apiService.getImages()
                if(newImageList.isNotEmpty()) {
                    imageResultDao.removeAll()
                    for (i in newImageList.indices) {
                        imageResultDao.insertImage(newImageList[i])
                    }
                }
                newImageList
            }else{
                imageResultDao.getImages()
            }

        }catch (unExp:UnknownHostException){
            unExp.printStackTrace()
        }
        catch (e:Exception){
            e.printStackTrace()
        }
        return ArrayList()
    }
}