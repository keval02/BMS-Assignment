package com.bmstest.app.adapter

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bmstest.app.R
import com.bmstest.app.model.MovieListDataResultModel
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.layout_similar_moives_list_item.view.*

abstract class SimilarMoviesListingAdapter(
    var activity: Activity,
    var movieList: ArrayList<MovieListDataResultModel>
) : RecyclerView.Adapter<SimilarMoviesListingAdapter.MovieListHolder>() {

    class MovieListHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieListHolder {
        val itemView =
            activity.layoutInflater.inflate(R.layout.layout_similar_moives_list_item, parent, false)

        return MovieListHolder(itemView)
    }

    override fun onBindViewHolder(holder: MovieListHolder, position: Int) {
        Glide.with(activity)
            .load(movieList[position].poster_path)
            .error(R.drawable.image_preview_bg)
            .placeholder(R.drawable.image_preview_bg)
            .into(holder.itemView.moviePosterIV)

        holder.itemView.movieTitleTV.text = movieList[position].title
        holder.itemView.movieTotalVotesTV.text =
            movieList[position].vote_count.toString() + " Votes"
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

}