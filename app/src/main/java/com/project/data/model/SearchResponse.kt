package com.project.data.model

import java.util.*

data class SearchResponse(
    var restaurants: List<Restaurant?>? = null,
    var results_found: Int? = null,
    var results_shown: Int? = null,
    var results_start: Int? = null
){
    fun getCuisineData():List<CuisineData> {
        val cuisineList: MutableSet<String> = HashSet()
        for (restaurant in restaurants!!) {
            val restaurant: RestaurantData? = restaurant?.restaurant
            if (restaurant != null) {
                cuisineList.addAll((restaurant.cuisines?.replace(" ", "")?.split(",")!!));
            }
        }
        val cuisineRes: MutableList<CuisineData> = ArrayList()
        for (s in cuisineList) {
            val cuisineData = CuisineData()
            cuisineData.cuisine = s
            for (i in restaurants!!.indices) {
                val restaurant: RestaurantData? = restaurants?.get(i)?.restaurant
                if (restaurant != null) {
                    if (restaurant.cuisines?.contains(s)!!) {
                        cuisineData.restaurants.add(restaurant)
                    }
                }
            }
            if (cuisineData.restaurants.isNotEmpty()) cuisineRes.add(cuisineData)
        }
        return cuisineRes
    }
}
data class CuisineData (
    var cuisine: String? = null,
    var restaurants: ArrayList<RestaurantData?> = ArrayList()
)

data class Title(
    var text: String? = null
)

data class UserRating(
    var aggregate_rating: Double? = null,
    var rating_color: String? = null,
    var rating_obj: RatingObj? = null,
    var rating_text: String? = null,
    var votes: Int? = null
)

data class Review(
    var review: List<Any?>? = null
)

data class RestaurantData(
    var R: R? = null,
    var all_reviews: AllReviews? = null,
    var all_reviews_count: Int? = null,
    var apikey: String? = null,
    var average_cost_for_two: Int? = null,
    var book_again_url: String? = null,
    var book_form_web_view_url: String? = null,
    var cuisines: String? = null,
    var currency: String? = null,
    var deeplink: String? = null,
    var establishment: List<String?>? = null,
    var establishment_types: List<Any?>? = null,
    var events_url: String? = null,
    var featured_image: String? = null,
    var has_online_delivery: Int? = null,
    var has_table_booking: Int? = null,
    var highlights: List<Any?>? = null,
    var id: String? = null,
    var include_bogo_offers: Boolean? = null,
    var is_book_form_web_view: Int? = null,
    var is_delivering_now: Int? = null,
    var is_table_reservation_supported: Int? = null,
    var is_zomato_book_res: Int? = null,
    var location: Location? = null,
    var menu_url: String? = null,
    var mezzo_provider: String? = null,
    var name: String? = null,
    var offers: List<Any?>? = null,
    var opentable_support: Int? = null,
    var phone_numbers: String? = null,
    var photo_count: Int? = null,
    var photos_url: String? = null,
    var price_range: Int? = null,
    var store_type: String? = null,
    var switch_to_order_menu: Int? = null,
    var thumb: String? = null,
    var timings: String? = null,
    var url: String? = null,
    var user_rating: UserRating? = null
)

data class Restaurant(
    var restaurant: RestaurantData? = null
)

data class RatingObj(
    var bg_color: BgColor? = null,
    var title: Title? = null
)

data class R(
    var has_menu_status: HasMenuStatus? = null,
    var res_id: Int? = null
)

data class Location(
    var address: String? = null,
    var city: String? = null,
    var city_id: Int? = null,
    var country_id: Int? = null,
    var latitude: String? = null,
    var locality: String? = null,
    var locality_verbose: String? = null,
    var longitude: String? = null,
    var zipcode: String? = null
)

data class HasMenuStatus(
    var delivery: Int? = null,
    var takeaway: Int? = null
)

data class BgColor(
    var tint: String? = null,
    var type: String? = null
)

data class AllReviews(
    var reviews: List<Review?>? = null
)