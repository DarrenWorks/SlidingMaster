package com.darren.slidingmaster.entity

import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Image constructor(
    @PrimaryKey
    val id : Long,
    val uri : String,
    val data : String,
    val dateModify : Long,
)