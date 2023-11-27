package com.darren.slidingmaster.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.darren.slidingmaster.entity.Path
import kotlinx.coroutines.flow.Flow

@Dao
interface PathDao {
    @Query("SELECT * FROM path")
    fun getAll(): Flow<List<Path>>

    @Insert
    suspend fun insert(vararg images: Path)

    @Delete
    suspend fun delete(image: Path)

    @Query("DELETE FROM path")
    suspend fun deleteAll()
}