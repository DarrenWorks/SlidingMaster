package com.darren.slidingmaster.repository

import com.darren.slidingmaster.AppApplication
import com.darren.slidingmaster.entity.Image
import com.darren.slidingmaster.getImages
import kotlinx.coroutines.flow.Flow

class ImageRepository(
    private val app: AppApplication
) {
    private val imageDao by lazy {
        app.db.imageDao()
    }

    /**
     * 手机硬盘中的图片
     */
    suspend fun getImageList(): MutableList<Image> {
        return getImages(app)
    }

    /**
     * 将要删除的图片
     */
    fun getToBeDeleteImageList(): Flow<List<Image>> {
        return imageDao.getAll()
    }

    companion object {
        @Volatile
        private var instance: ImageRepository? = null

        fun getInstance(app: AppApplication) =
            instance ?: synchronized(this) {
                instance ?: ImageRepository(app).also { instance = it }
            }
    }
}