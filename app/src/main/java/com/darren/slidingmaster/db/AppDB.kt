package com.darren.slidingmaster.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.darren.slidingmaster.dao.ImageDao
import com.darren.slidingmaster.dao.PathDao
import com.darren.slidingmaster.entity.Image
import com.darren.slidingmaster.entity.Path

@Database(entities = [Image::class, Path::class], version = 1, exportSchema = false)
abstract class AppDB : RoomDatabase(){
    abstract fun imageDao() : ImageDao
    abstract fun pathDao() : PathDao
}