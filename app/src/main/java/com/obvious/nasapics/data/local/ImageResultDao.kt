package com.obvious.nasapics.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.obvious.nasapics.data.models.ImageResult

@Dao
interface ImageResultDao {

    @Query("SELECT * FROM image_results")
    suspend fun getImages():List<ImageResult>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertImage(imageResult: ImageResult)

    @Query("DELETE  FROM image_results")
    suspend fun removeAll()
}