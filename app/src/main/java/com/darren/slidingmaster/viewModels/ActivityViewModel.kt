package com.darren.slidingmaster.viewModels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class ActivityViewModel : ViewModel() {
    /**
     * 权限dialog
     */
   private var showNoPermissionDialog  = mutableStateOf(false)

    public fun getShowDialog() : Boolean {
        return showNoPermissionDialog.value
    }

    public fun showNoPermissionDialog() {
        showNoPermissionDialog.value = true
    }

    public fun dismissNoPermissionDialog() {
        showNoPermissionDialog.value = false
    }

    /**
     * 加载dialog
     */
    private var showLoadingDialog = mutableStateOf(false)

    public fun getShowLoadingDialog() : Boolean {
        return showLoadingDialog.value
    }

    public fun showLoadingDialog() {
        showLoadingDialog.value = true
    }

    public fun dismissLoadingDialog() {
        showLoadingDialog.value = false
    }


}