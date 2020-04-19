package com.project.utils

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jakewharton.rxbinding3.recyclerview.RecyclerViewScrollEvent
import io.reactivex.Observable
import com.jakewharton.rxbinding3.recyclerview.scrollEvents
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit

/**
 * Created by Priyanka on 16/04/2020
 */
object RecyclerViewLoadMoreListener {

    fun lastItemReached(recyclerView: RecyclerView): Observable<RecyclerViewScrollEvent> {
        return recyclerView
            .scrollEvents()
            .debounce(100, TimeUnit.MILLISECONDS)
            .filter { isLastItemDisplaying(recyclerView) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(AndroidSchedulers.mainThread())
    }

    private fun isLastItemDisplaying(recyclerView: RecyclerView): Boolean {
        val manager = recyclerView.layoutManager
        val visibleItemCount = recyclerView.childCount
        val totalItemCount = manager?.itemCount
        val firstVisibleItem = (manager as? LinearLayoutManager)?.findFirstVisibleItemPosition()

        return if (firstVisibleItem != null) {
            totalItemCount?.minus(visibleItemCount)!! <= firstVisibleItem + 2
        }else
            false
    }
}