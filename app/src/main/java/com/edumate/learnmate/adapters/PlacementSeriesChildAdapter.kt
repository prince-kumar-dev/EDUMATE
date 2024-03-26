package com.edumate.learnmate.adapters

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.edumate.learnmate.R
import com.edumate.learnmate.models.PlacementChildItem

class PlacementSeriesChildAdapter(
    val context: Context,
    private val placementSeriesChildList: List<PlacementChildItem>
) :
    RecyclerView.Adapter<PlacementSeriesChildAdapter.PlacementSeriesChildViewHolder>() {

    inner class PlacementSeriesChildViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.placementSeriesChildTitleTv)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PlacementSeriesChildViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.child_item_placement_series, parent, false)
        return PlacementSeriesChildViewHolder(view)
    }

    override fun getItemCount(): Int {
        return placementSeriesChildList.size
    }

    override fun onBindViewHolder(holder: PlacementSeriesChildViewHolder, position: Int) {
        holder.title.text = placementSeriesChildList[position].title

        holder.itemView.setOnClickListener {
            val uri = Uri.parse(placementSeriesChildList[position].url)
            val intent = Intent(Intent.ACTION_VIEW, uri)
            context.startActivity(intent)
        }
    }
}