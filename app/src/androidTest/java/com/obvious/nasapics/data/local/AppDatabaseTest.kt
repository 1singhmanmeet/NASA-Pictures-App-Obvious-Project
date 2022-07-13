package com.obvious.nasapics.data.local

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.obvious.nasapics.data.models.ImageResult
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import com.google.common.truth.Truth.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
internal class AppDatabaseTest:TestCase() {

    private lateinit var appDatabase: AppDatabase
    private lateinit var imageResultDao: ImageResultDao

    @Before
    public override fun setUp(){
        val appContext=ApplicationProvider.getApplicationContext<Context>()
        appDatabase= Room.inMemoryDatabaseBuilder(appContext,
            AppDatabase::class.java).build()
        imageResultDao=appDatabase.imageResultDao()
    }

    @After
    fun closeDbConnection(){
        appDatabase.close()
    }

    @Test
    fun writeAndReadImageResult()= runBlocking {
        val imageResult=ImageResult(1,"nasa","2022-09-09",
            "explanation",
            "url",
            "Title",
            "google.com")

        imageResultDao.insertImage(imageResult)
        val imageResults=imageResultDao.getImages()
        assertThat(imageResults.contains(imageResult)).isTrue()
    }
}