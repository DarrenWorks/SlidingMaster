package com.darren.slidingmaster.dialogs

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.DialogProperties
import com.darren.slidingmaster.viewModels.ActivityViewModel
import kotlin.system.exitProcess

@Composable
fun NoPermissionDialog(viewModel: ActivityViewModel, jumpToPermissionSetting: () -> Unit) {
    val onDismiss = {
        jumpToPermissionSetting()
        viewModel.dismissNoPermissionDialog()
    }

    AlertDialog(
        onDismissRequest = {
            onDismiss()
        },
        title = {
            Text(text = "无权限")
        },
        text = {
            Text(text = "请在设置中开启权限方可继续使用")
        },
        dismissButton = {
            TextButton(onClick = {
                exitProcess(0)
            }) {
                Text(text = "下次再说")
            }
        },
        confirmButton = {
            TextButton(onClick = {
                onDismiss()
            }) {
                Text(text = "去设置")
            }
        },
        properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
    )
}

@Composable
fun LoadingDialog(viewModel: ActivityViewModel) {
    AlertDialog(
        onDismissRequest = {
            viewModel.dismissLoadingDialog()
        },
        title = {
            Text(text = "加载中")
        },
        text = {
            Text(text = "请稍后")
        },
        dismissButton = {
        },
        confirmButton = {
        },
        properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
    )
}