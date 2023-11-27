package com.darren.slidingmaster.viewModels

import android.content.Context
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.darren.slidingmaster.AppApplication
import com.darren.slidingmaster.R
import com.darren.slidingmaster.entity.Image
import com.darren.slidingmaster.entity.Path
import com.darren.slidingmaster.repository.ImageRepository
import com.darren.slidingmaster.repository.PathRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toCollection
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import java.util.Collections

/**
 * 管理图片
 */
class ImageViewModel(application: AppApplication) : AndroidViewModel(application) {
    val imageRepository by lazy {
        ImageRepository(application)
    }

    val pathRepository by lazy {
        PathRepository(application)
    }

    private val _imageList = mutableStateListOf<Image>()//手机中的图片
    val imageList: List<Image> = _imageList

    //将要删除的图片
    private val toBeDeleteImageList = imageRepository.getToBeDeleteImageList()

    private val _pathList = mutableStateListOf<Path>()//手机中的相册目录
    val pathList: List<Path> = _pathList

    //正在使用的相册目录
    val usingPathList = pathRepository.getUsingPathList()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            init()
        }
    }


    /**
     * 初始化数据
     */
    private suspend fun init() {
        initPath()
        initImage()
    }

    /**
     * 初始化相册路径列表
     */
    private suspend fun initPath() {
        _pathList.addAll(pathRepository.getPathList())
    }

    /**
     * 初始化图片列表
     */
    private suspend fun initImage() {
        _imageList.addAll(imageRepository.getImageList())
        val filter = flowOf<List<Image>>()
        toBeDeleteImageList.collect{
            filter.
        }
    }

    /**
     * 将指定图片移动到待删除目录
     */

    suspend fun addToBeDeleting(index: Int) {
        toBeDeleteImageList.add(_imageList[index])
        _imageList.removeAt(index)
    }

    /**
     * 添加一个目录
     */
    suspend fun addPath(path: Path) {
        pathRepository.addUsingPath(path)
    }

    class ViewModelFactory(private val application: AppApplication) :
        ViewModelProvider.AndroidViewModelFactory(application) {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ImageViewModel(application) as T
        }
    }
}