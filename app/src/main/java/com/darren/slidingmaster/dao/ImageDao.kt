package com.darren.slidingmaster.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.darren.slidingmaster.entity.Image
import kotlinx.coroutines.flow.Flow

@Dao
interface ImageDao {
    @Query("SELECT * FROM image")
    fun getAll(): Flow<List<Image>>

    @Insert
    suspend fun insertAll(vararg images: Image)

    @Delete
    suspend fun delete(image: Image)

    @Query("DELETE FROM image")
    suspend fun deleteAll()
}