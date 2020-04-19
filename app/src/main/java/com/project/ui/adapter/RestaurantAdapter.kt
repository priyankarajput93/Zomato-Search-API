package com.project.ui.adapter

import android.content.Context
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.project.R
import com.project.data.model.RestaurantData
import com.project.utils.CircleTransformation
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_restaurant.view.*

/**
 * Created by Priyanka on 15/04/2020
 */

class RestaurantAdapter(var restaurants: List<RestaurantData?>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var context : Context? = null
    override fun getItemCount(): Int {
                return restaurants.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context
            val inflater =
                LayoutInflater.from(parent.context).inflate(R.layout.item_restaurant, parent, false)
            return ViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
                holder.bind(restaurants[position])
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(restaurant: RestaurantData?) {
            itemView.tv_restaurant_name.text = restaurant?.name
            itemView.tv_restaurant_timings.text = restaurant?.timings
            itemView.tv_restaurant_type.text = restaurant?.store_type
            itemView.tv_restaurant_address.text = restaurant?.location?.address

            if (restaurant?.user_rating?.aggregate_rating.toString()!=context?.getString(R.string.default_rating)){
                itemView.tv_restaurant_rating.text =
                    restaurant?.user_rating?.aggregate_rating.toString()
            }else{
                itemView.tv_restaurant_rating.text = context?.getString(R.string.not_rated)
            }

            if (restaurant?.average_cost_for_two!! > 0) {
                itemView.tv_restaurant_amount.visibility = View.VISIBLE
                itemView.tv_restaurant_amount.text = context?.getString(R.string.average_cost_for_two,restaurant.average_cost_for_two?.toString())
            } else itemView.tv_restaurant_amount.visibility = View.GONE
            val isDeliveryAvailable: Boolean = restaurant.is_delivering_now!! == 0
            if (isDeliveryAvailable) {
                itemView.tv_restaurant_order_now.background = (ContextCompat.getDrawable(
                    itemView.tv_restaurant_order_now.context,
                    R.drawable.bg_order_now
                ))
            } else {
                itemView.tv_restaurant_order_now.text =
                    itemView.tv_restaurant_order_now.context.getString(R.string.closed_for_ordering)
                itemView.tv_restaurant_order_now.background = (ContextCompat.getDrawable(
                    itemView.tv_restaurant_order_now.context,
                    R.drawable.bg_rating
                ))
            }
            if (!TextUtils.isEmpty(restaurant.thumb)) {
                Picasso.get().load(restaurant.thumb!!).placeholder(R.drawable.bg_rating)
                    .transform(CircleTransformation(8, 4)).into(itemView.iv_restaurant)
            }
        }
    }

}