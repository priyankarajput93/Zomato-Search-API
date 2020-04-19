package com.project.base

import androidx.annotation.StringRes

/**
 * Created by Priyanka on 15/04/2020
 */

interface BaseView {

    fun baseApplication(): BaseApp
    fun showError(error: String)
    fun showError(message: String, retry: () -> Unit)
    fun showLoading()
    fun hideLoading()
    fun showNoInternetError(retry: () -> Unit)
    fun showToast(message: String?)
    fun showToast(@StringRes message: Int?)

}