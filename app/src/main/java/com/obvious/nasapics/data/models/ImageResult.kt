package com.obvious.nasapics.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import javax.annotation.Nullable

@Entity(tableName = "image_results")
data class ImageResult(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    @Nullable
    var copyright: String?="",
    val date: String,
    @Nullable
    var explanation: String?="",
    @ColumnInfo(name = "hd_url")
    @SerializedName("hdurl")
    val hdUrl: String,
    val title: String,
    val url: String
)