package com.project.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.project.R
import com.project.data.model.CuisineData
import com.project.utils.LoadMoreAdapter
import kotlinx.android.synthetic.main.item_cuisine.view.*

/**
 * Created by Priyanka on 15/04/2020
 */

class CuisineAdapter(var itemList: ArrayList<CuisineData>) : LoadMoreAdapter() {

    fun updateAdapter(list: List<CuisineData>) {
        if (list.isEmpty())
            setIsLoadingData(false)
        else
            itemList.addAll(list)
        notifyDataSetChanged()
    }

    fun clearAdapter() {
        itemList.clear()
        notifyDataSetChanged()
    }

    override fun getListSize(): Int {
        return itemList.size
    }

    override fun getViewType(position: Int): Int {
        return VIEW_TYPE_CUISINE
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_CUISINE) {
            val inflater =
                LayoutInflater.from(parent.context).inflate(R.layout.item_cuisine, parent, false)
            return ViewHolder(inflater)
        } else {
            super.onCreateViewHolder(parent, viewType)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            if (itemList.size > 0)
                holder.bind(itemList[position])
        } else {
            super.onBindViewHolder(holder, position)
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(cuisineData: CuisineData) {
            val restaurantAdapter = RestaurantAdapter(cuisineData.restaurants)
            val linearLayoutManager = LinearLayoutManager(
                itemView.rv_restaurants.context,
                LinearLayoutManager.VERTICAL,
                false
            )
            itemView.rv_restaurants.layoutManager = linearLayoutManager
            itemView.rv_restaurants.adapter = restaurantAdapter

            itemView.tv_cuisine_name.text = cuisineData.cuisine

        }
    }

    companion object {
        private const val VIEW_TYPE_CUISINE = 0x01
    }
}