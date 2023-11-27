package com.darren.slidingmaster.repository

import com.darren.slidingmaster.AppApplication
import com.darren.slidingmaster.entity.Path
import com.darren.slidingmaster.getGalleryPath
import kotlinx.coroutines.flow.Flow

class PathRepository(
    private val app: AppApplication
) {

    private val pathDao by lazy {
        app.db.pathDao()
    }

    /**
     * 手机硬盘中的相册目录
     */
    suspend fun getPathList(): List<Path> {
        return getGalleryPath()
    }

    /**
     * 正在使用的相册目录
     */
    fun getUsingPathList(): Flow<List<Path>> {
        return pathDao.getAll()
    }

    /**
     * 在正在使用的相册目录中添加一个目录
     */
    suspend fun addUsingPath(path: Path) {
        pathDao.insert(path)
    }

    companion object {

        @Volatile
        private var instance: PathRepository? = null

        fun getInstance(app: AppApplication) =
            instance ?: synchronized(this) {
                instance ?: PathRepository(app).also { instance = it }
            }
    }
}