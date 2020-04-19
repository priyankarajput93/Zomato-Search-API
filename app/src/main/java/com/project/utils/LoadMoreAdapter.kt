package com.project.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.project.R
import kotlinx.android.synthetic.main.layout_load_more.view.*
import timber.log.Timber

/**
 * Created by Priyanka on 16/04/2020
 */
abstract class LoadMoreAdapter() :
    RecyclerView.Adapter<ViewHolder>() {

    private var isLoadingData = false

    abstract fun getListSize(): Int

    override fun getItemCount(): Int {
        return getListSize() + 1
    }

    fun setIsLoadingData(isLoading: Boolean) {
        this.isLoadingData = isLoading
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (holder is LoadingMoreHolder) {
            bindLoadingViewHolder(holder)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return LoadingMoreHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_load_more, parent, false))
    }

    private fun bindLoadingViewHolder(holder: LoadingMoreHolder) {
        if (isLoadingData) {
            Timber.d("isLoadingData VISIBLE")
            holder.loading.visibility = View.VISIBLE
        } else {
            Timber.d("isLoadingData GONE")
            holder.loading.visibility = View.GONE
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (itemCount - 1 == position && itemCount > 0) TYPE_LOADING_MORE else getViewType(
            position
        )
    }

    abstract fun getViewType(position: Int): Int

    inner class LoadingMoreHolder (itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        internal var loading = itemView.progress_bar
    }

    companion object {
        protected val TYPE_LOADING_MORE = 0x10
    }
}

