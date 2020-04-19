package com.project.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.project.R
import com.project.utils.CommonUtils
import kotlinx.android.synthetic.main.content_main.*

/**
 * Created by Priyanka on 15/04/2020
 */
abstract class BaseActivity : AppCompatActivity(), BaseView {
    private lateinit var alertDialog: AlertDialog

    @LayoutRes
    protected abstract fun getLayout(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.content_main)
        val view = LayoutInflater.from(this).inflate(getLayout(), null, false)
        container.addView(view, 0)
    }

    override fun baseApplication(): BaseApp {
        return application as BaseApp
    }

    override fun showError(error: String) {
        hideLoading()
        showDialog(getString(R.string.alert), error)
    }

    override fun showError(message: String, retry: () -> Unit) {
        hideLoading()
        showRetryDialog(getString(R.string.alert), message, retry)
    }

    override fun showLoading() {
        if (CommonUtils.isNetworkAvailable()) {
            progressBar.visibility = View.VISIBLE
        }
    }

    override fun hideLoading() {
        progressBar.visibility = View.GONE
    }

    override fun showNoInternetError(retry: () -> Unit) {
        hideLoading()
        showRetryDialog(
            getString(R.string.alert),
            resources.getString(R.string.error_internet),
            retry
        )
    }

    override fun showToast(message: String?) {
        message?.let {
            CommonUtils.toast(it)
        }
    }

    override fun showToast(message: Int?) {
        message?.let {
            CommonUtils.toast(it.toString())
        }
    }

    /**
     * show retry dialog
     *
     * @param title   title to show(Alert)
     * @param message message of error
     * @param retry   callback
     */
    private fun showRetryDialog(title: String, message: String, retry: () -> Unit) {
        hideLoading()
        val builder = AlertDialog.Builder(
            this
        )
        builder.setTitle(title)
            .setMessage(message)
            .setCancelable(false)
            .setPositiveButton(getString(R.string.retry)) { _, _ -> run(retry) }
            .setNegativeButton(getString(R.string.back)) { dialog, _ -> dialog.dismiss() }

        alertDialog = builder.create()
        if (!this.isFinishing && !alertDialog.isShowing) {
                alertDialog.show()
        }
    }

    private fun showDialog(title: String, message: String) {
        val builder = AlertDialog.Builder(
            this
        )
        builder.setTitle(title)
            .setMessage(message)
            .setCancelable(false)
            .setPositiveButton(getString(R.string.ok), null)

        alertDialog = builder.create()
        if (!this.isFinishing) {
            if (!alertDialog.isShowing)
                alertDialog.show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        alertDialog.dismiss()
    }
}