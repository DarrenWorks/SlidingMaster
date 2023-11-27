package com.darren.slidingmaster.ui

import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.core.net.toFile
import coil.compose.AsyncImage
import java.io.File
import kotlin.math.absoluteValue
import kotlin.math.roundToInt

@Composable
fun ImageScreen(imagePath: String, onDelete: () -> Unit) {
    var offset by remember {
        mutableStateOf(0f)
    }

    AsyncImage(model = File(imagePath), contentDescription = null, modifier = Modifier
        .offset {
            IntOffset(0, offset.roundToInt())
        }
        .draggable(
            orientation = Orientation.Vertical,
            state = rememberDraggableState { delta ->
                Log.d("draggable", "drag delta: $delta")
                offset += delta
            },
            onDragStopped = { velocity ->
                if (velocity.absoluteValue > 2000) {
                    onDelete()
                }
            }
        ))

}