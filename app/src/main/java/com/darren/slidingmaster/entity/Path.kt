package com.darren.slidingmaster.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Path(
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    val name : String,
    val fullPath : String?,
) {
    fun isManage(): Boolean {
        return fullPath == null
    }
}
