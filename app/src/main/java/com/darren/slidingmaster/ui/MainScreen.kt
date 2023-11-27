@file:OptIn(ExperimentalFoundationApi::class)

package com.darren.slidingmaster.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.darren.slidingmaster.entity.Image
import com.darren.slidingmaster.entity.Path

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainScreen(
    imageListState: List<Image>,
    pathListState: List<Path>,
    moveToPath: (image: Image, path: Path) -> Unit,
    managePath: () -> Unit,
    onDelete: (index: Int) -> Unit,
) {
    val imagePagerState = rememberPagerState()
    Column {
        Box(
            modifier = Modifier
                .weight(1f)
                .padding(bottom = 16.dp)
        ) {
            ImagePageScreen(imageListState = imageListState, pagerState = imagePagerState, onDelete)
        }
        PathListScreen(
            pathList = pathListState,
            { path -> moveToPath(imageListState[imagePagerState.currentPage], path) },
            managePath
        )
    }
}

@Composable
fun ImagePageScreen(
    imageListState: List<Image>,
    pagerState: PagerState,
    onDelete: (index: Int) -> Unit
) {
    HorizontalPager(
        pageCount = imageListState.size,
        state = pagerState,
        modifier = Modifier.fillMaxSize()
    ) { page ->
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            ImageScreen(imagePath = imageListState[page].data) { onDelete(page) }
        }
    }
}

@Composable
fun PathListScreen(
    pathList: List<Path>, moveToPath: (path: Path) -> Unit,
    managePath: () -> Unit,
) {
    LazyRow {
        items(pathList.size) { index ->
            PathScreen(path = pathList[index], moveToPath, managePath)
        }
    }
}

@Composable
fun PathScreen(
    path: Path,
    moveToPath: (path: Path) -> Unit,
    managePath: () -> Unit,
) {
    Text(text = path.name, modifier = Modifier
        .padding(4.dp)
        .background(color = Color.Gray, shape = RoundedCornerShape(8.dp))
//        .border(1.dp, color = Color.Gray, shape = RoundedCornerShape(8.dp))
        .padding(8.dp)
        .clickable {
            if (path.isManage()) {
                managePath()
            } else {
                moveToPath(path)
            }
        })
}