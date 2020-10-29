package com.bmstest.app.adapter

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bmstest.app.R
import com.bmstest.app.model.CastDataModel
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.layout_cast_list_item.view.*

abstract class MovieCastListAdapter(
    var activity: Activity,
    var castList: ArrayList<CastDataModel>
) : RecyclerView.Adapter<MovieCastListAdapter.CastListHolder>() {

    class CastListHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastListHolder {
        val itemView =
            activity.layoutInflater.inflate(R.layout.layout_cast_list_item, parent, false)

        return CastListHolder(itemView)
    }

    override fun onBindViewHolder(holder: CastListHolder, position: Int) {
        Glide.with(activity)
            .load(castList[position].profile_path)
            .error(R.drawable.user_image_preview_bg)
            .placeholder(R.drawable.user_image_preview_bg)
            .into(holder.itemView.castIV)

        holder.itemView.castNameTV.text = castList[position].name

    }

    override fun getItemCount(): Int {
        return castList.size
    }
}