package com.bmstest.app.adapter

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bmstest.app.R
import com.bmstest.app.model.ReviewResultDataModel
import kotlinx.android.synthetic.main.layout_reviews_list_items.view.*

abstract class MovieReviewsAdapter(
    var activity: Activity,
    var reviewList: ArrayList<ReviewResultDataModel>
) : RecyclerView.Adapter<MovieReviewsAdapter.MovieListHolder>() {

    class MovieListHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieListHolder {
        val itemView =
            activity.layoutInflater.inflate(R.layout.layout_reviews_list_items, parent, false)

        return MovieListHolder(itemView)
    }

    override fun onBindViewHolder(holder: MovieListHolder, position: Int) {

        holder.itemView.userNameTV.text = reviewList[position].author
        holder.itemView.reviewTV.text = reviewList[position].content.toString()

    }

    override fun getItemCount(): Int {
        return reviewList.size
    }

}