package com.darren.slidingmaster

import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import com.darren.slidingmaster.entity.Image
import com.darren.slidingmaster.entity.Path

suspend fun getImages(context: Context): MutableList<Image> {
    val imageList = mutableListOf<Image>()

    val collection = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL)
    } else {
        MediaStore.Images.Media.EXTERNAL_CONTENT_URI
    }
    val projection = arrayOf(
        MediaStore.Images.Media._ID,
        MediaStore.Images.Media.DATA,
        MediaStore.Images.Media.DATE_MODIFIED
    )
    val selection: String? = null
    val selectionArgs = arrayOf<String>()
    val sortOrder: String = MediaStore.Images.Media.DATE_MODIFIED + " DESC"
    val query =
        context.contentResolver.query(collection, projection, selection, selectionArgs, sortOrder)

    query.use { cursor: Cursor? ->
        if (cursor == null) return imageList
        val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
        val dataColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        val dateModifyColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_MODIFIED)

        while (cursor.moveToNext()) {
            val id = cursor.getLong(idColumn)
            val data = cursor.getString(dataColumn)
            val dateModify = cursor.getLong(dateModifyColumn)
            val contentUri =
                ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id)

            imageList += Image(id, contentUri.toString(), data, dateModify)
        }
    }

    return imageList
}

suspend fun getGalleryPath(): List<Path> {
    val paths =
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).listFiles()
            ?.filter { it.isDirectory && !it.name.startsWith(".") }?.toMutableList()
            ?: mutableListOf()
    paths.addAll(
        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).listFiles()
            ?.filter { it.isDirectory && !it.name.startsWith(".") } ?: listOf())
    return paths.map { Path(name = it.name, fullPath = it.path) }
}