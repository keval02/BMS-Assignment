package com.bmstest.app.adapter

import AppGlobal.changeDateFormat
import android.app.Activity
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bmstest.app.R
import com.bmstest.app.model.MovieListDataResultModel
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.layout_movie_list_items.view.*
import java.text.SimpleDateFormat
import kotlin.collections.ArrayList

abstract class MovieListingAdapter(
    var activity: Activity,
    var movieList: ArrayList<MovieListDataResultModel>
) : RecyclerView.Adapter<MovieListingAdapter.MovieListHolder>() {

    class MovieListHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieListHolder {
        val itemView =
            activity.layoutInflater.inflate(R.layout.layout_movie_list_items, parent, false)

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

        holder.itemView.movieReleaseDateTV.text = changeDateFormat(movieList[position].release_date)

        holder.itemView.movieItemCV.setOnClickListener {
            onMovieClick(movieList[position].id)
        }
    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    abstract fun onMovieClick(id: Int)
}