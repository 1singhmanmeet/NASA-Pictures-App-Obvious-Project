package com.obvious.nasapics.data.models

import androidx.room.Entity
import com.google.gson.annotations.SerializedName

@Entity(tableName = "image_results")
data class ImageResult(
    val copyright: String,
    val date: String,
    val explanation: String,
    @SerializedName("hdurl")
    val hdUrl: String,
    val title: String,
    val url: String
)