package com.darren.slidingmaster.utils

import android.icu.text.CaseMap.Title
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Dialog

class DialogUtil {
    companion object {
        @Composable
        public fun showDialog(
            hasCancel: Boolean = true,
            title: String = "",
            content: String = "",
            onDismiss: () -> Unit = {},
            onConfirm: () -> Unit = {},
            cancelText: String = "取消",
            confirmText: String = "确定"
        ) {
            AlertDialog(onDismissRequest = {

            }, title = {
                Text(text = title)
            }, text = {
                Text(text = content)
            }, dismissButton = {
                if (hasCancel) {
                    TextButton(onClick = { onDismiss() }) {
                        Text(text = cancelText)
                    }
                }
            }, confirmButton = {
                TextButton(onClick = { onConfirm() }) {
                    Text(text = confirmText)
                }
            })
        }
    }

}