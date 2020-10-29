package com.bmstest.app.adapter

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bmstest.app.R
import com.bmstest.app.model.CrewDataModel
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.layout_cast_list_item.view.*

abstract class MovieCrewListAdapter(
    var activity: Activity,
    var crewList: ArrayList<CrewDataModel>
) : RecyclerView.Adapter<MovieCrewListAdapter.MovieListHolder>() {

    class MovieListHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieListHolder {
        val itemView =
            activity.layoutInflater.inflate(R.layout.layout_cast_list_item, parent, false)

        return MovieListHolder(itemView)
    }

    override fun onBindViewHolder(holder: MovieListHolder, position: Int) {
        Glide.with(activity)
            .load(crewList[position].profile_path)
            .error(R.drawable.user_image_preview_bg)
            .placeholder(R.drawable.user_image_preview_bg)
            .into(holder.itemView.castIV)

        holder.itemView.castNameTV.text = crewList[position].name
    }

    override fun getItemCount(): Int {
        return crewList.size
    }

}