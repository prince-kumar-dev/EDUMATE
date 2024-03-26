package com.example.edumate.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.edumate.R
import com.example.edumate.models.PlacementParentItem
import com.example.edumate.models.StudyPlaylist

class PlacementSeriesParentAdapter(
    val context: Context,
    private var placementSeriesParentList: List<PlacementParentItem>
) :
    RecyclerView.Adapter<PlacementSeriesParentAdapter.PlacementSeriesParentViewHolder>() {

    inner class PlacementSeriesParentViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val logoIv: ImageView = itemView.findViewById(R.id.placementSeriesParentLogoIv)
        val titleTv: TextView = itemView.findViewById(R.id.placementSeriesParentTitleTv)
        val childRecyclerView: RecyclerView =
            itemView.findViewById(R.id.placementContentRecyclerView)
    }

    fun setFilteredList(placementSeriesParentList: List<PlacementParentItem>) {
        this.placementSeriesParentList = placementSeriesParentList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PlacementSeriesParentViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.parent_item_placement_series, parent, false)
        return PlacementSeriesParentViewHolder(view)
    }

    override fun getItemCount(): Int {
        return placementSeriesParentList.size
    }

    override fun onBindViewHolder(holder: PlacementSeriesParentViewHolder, position: Int) {
        val parentItem = placementSeriesParentList[position]
        holder.logoIv.setImageResource(parentItem.logo)
        holder.titleTv.text = parentItem.title

        holder.childRecyclerView.setHasFixedSize(true)
        holder.childRecyclerView.layoutManager = GridLayoutManager(holder.itemView.context, 2)
        val adapter = PlacementSeriesChildAdapter(context, parentItem.mList)
        holder.childRecyclerView.adapter = adapter
    }
}