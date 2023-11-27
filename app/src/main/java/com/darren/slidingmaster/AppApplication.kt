package com.darren.slidingmaster

import android.app.Application
import androidx.room.Room
import com.darren.slidingmaster.db.AppDB

class AppApplication : Application() {
   val db by lazy {
        Room.databaseBuilder(this, AppDB::class.java, "sliding-master")
            .build()
    }
}