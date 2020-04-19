package com.project.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.project.R
import com.project.base.BaseActivity
import com.project.data.model.SearchRequest
import com.project.ui.adapter.CuisineAdapter
import com.project.utils.RecyclerViewLoadMoreListener.lastItemReached
import com.project.utils.widgets.SearchWidget
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel


/**
 * Created by Priyanka on 15/04/2020
 */

class RestaurantActivity : BaseActivity(), SwipeRefreshLayout.OnRefreshListener, SearchWidget.OnSearchClickListener {

    private val viewModel: RestaurantViewModel by viewModel()
    private var searchRequest = SearchRequest()
    private var count = 10
    private var totalItems = 0
    private var paginationLimit = 10
    private var initialPage = 1
    private val cuisineAdapter by lazy {
        CuisineAdapter(arrayListOf())
    }

    override fun getLayout(): Int {
        return R.layout.activity_main
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setListeners()
        setupRecyclerview()
        apiResponse()
    }

    private fun apiResponse() {
        viewModel.showLoading.observe(this, Observer { showLoading ->
            if (showLoading) {
                showLoading()
            } else {
                hideLoading()
            }
            viewModel.searchState.observe(this, Observer {
                val response = it
                response?.let { data ->
                    if (data.restaurants != null && data.restaurants?.isNotEmpty()!!) {
                        setVisibility(false)
                        if (data.results_found != null)
                            totalItems = data.results_found!!
                        cuisineAdapter.updateAdapter(data.getCuisineData())
                    } else {
                        setVisibility(true)
                    }
                }
            })
            viewModel.errorMessage.observe(this, Observer {
                showError(it)
            })
            viewModel.retry.observe(this, Observer {
                showNoInternetError(run { it })
            })

        })
    }

    @SuppressLint("CheckResult")
    private fun setupRecyclerview() {
        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rv_cuisine.layoutManager = linearLayoutManager
        rv_cuisine.adapter = cuisineAdapter
        lastItemReached(rv_cuisine).subscribe {
            count += paginationLimit
            cuisineAdapter.setIsLoadingData(true)
            searchRequest.start = cuisineAdapter.itemCount
            if (count < totalItems)
                viewModel.search(searchRequest, true)
        }
    }

    private fun setListeners() {
        swipe_to_refresh.setOnRefreshListener(this)
        layout_search.setSearchClickListener(this)
    }

    override fun onRefresh() {
        searchRequest.q = layout_search.getTextFromEditText()
        searchRequest.start = initialPage
        searchRequest.count = paginationLimit
        viewModel.search(searchRequest, true)
        cuisineAdapter.clearAdapter()
        swipe_to_refresh.isRefreshing = false
    }

    private fun setVisibility(isNoResultFound: Boolean) {
        if (isNoResultFound && searchRequest.start!! ==1) {
            rv_cuisine.visibility = View.GONE
            tv_no_data.visibility = View.VISIBLE
        } else {
            rv_cuisine.visibility = View.VISIBLE
            tv_no_data.visibility = View.GONE
        }
    }

    override fun onSearchTextChange(searchKeyword: String) {
        searchRequest.start = initialPage
        searchRequest.count = paginationLimit
        searchRequest.q = searchKeyword
        viewModel.search(searchRequest,false)
    }
}