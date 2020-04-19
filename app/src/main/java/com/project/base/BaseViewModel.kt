package com.project.base

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.project.utils.CommonUtils
import com.project.utils.UseCaseResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

/**
 * Created by Priyanka on 15/04/2020
 */

open class BaseViewModel : ViewModel(), CoroutineScope {
    private val job = Job()

    @RequiresApi(Build.VERSION_CODES.KITKAT)

    val errorMessage = MutableLiveData<String>()
    val retry = MutableLiveData<() -> Unit>()
    val showLoading = MutableLiveData<Boolean>()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

    fun isValidNetwork(retryInterface: () -> Unit): Boolean {
        val isAvailable = CommonUtils.isNetworkAvailable()
        if (!isAvailable) {
            retry.value = retryInterface
        }
        return isAvailable
    }

    fun <T : Any> apiSuccess(
        state: MutableLiveData<T>,
        result: UseCaseResult<T>
    ) {
        when (result) {
            is UseCaseResult.Success -> {
                state.value = result.data
            }
            is UseCaseResult.Exception -> errorMessage.value = result.exception.message
        }
    }
}
