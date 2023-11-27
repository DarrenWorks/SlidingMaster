package com.darren.slidingmaster

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.darren.slidingmaster.dialogs.LoadingDialog
import com.darren.slidingmaster.dialogs.NoPermissionDialog
import com.darren.slidingmaster.entity.Image
import com.darren.slidingmaster.entity.Path
import com.darren.slidingmaster.ui.MainScreen
import com.darren.slidingmaster.ui.theme.SlidingMasterTheme
import com.darren.slidingmaster.viewModels.ActivityViewModel
import com.darren.slidingmaster.viewModels.ImageViewModel
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths


class MainActivity : ComponentActivity() {

    val activityViewModel by viewModels<ActivityViewModel>()
    val imageViewModel by viewModels<ImageViewModel> {
        ImageViewModel.ViewModelFactory(application as AppApplication)
    }

    val permissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                imageViewModel.update(activityViewModel)
            } else {
                activityViewModel.showNoPermissionDialog()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SlidingMasterTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.Black
                ) {
                    MainScreen(
                        imageViewModel.imageList,
                        imageViewModel.pathList,
                        { image, path -> moveToPath(image, path) },
                        { managePath() }, {deleteImage(it)})

                    if (activityViewModel.getShowDialog()) {
                        NoPermissionDialog(activityViewModel) { jumpToPermissionSetting() }
                    } else {
                        //loading仅在无其他弹窗时显示
                        if (activityViewModel.getShowLoadingDialog()) {
                            LoadingDialog(activityViewModel)
                        }
                    }
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()

        if (!activityViewModel.getShowDialog()) {
            permissionLauncher.launch(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
    }

    private fun moveToPath(image: Image, path: Path) {
        // TODO: 未完成
        var name = File(image.data).name
        var targetPath = path.fullPath + File.separator + name
        if (File(targetPath).exists()) {

        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Files.move(Paths.get(image.data), Paths.get(path.fullPath))
        } else {
            File(image.data).renameTo(File(path.fullPath!!))
        }
    }

    private fun managePath() {

    }

  private fun deleteImage(index : Int) {
      Toast.makeText(this, "删除图片", Toast.LENGTH_SHORT).show()
    imageViewModel.addToBeDeleting(index)
  }


    private fun jumpToPermissionSetting() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        intent.data = Uri.parse("package:$packageName")
        startActivity(intent)
    }
}
