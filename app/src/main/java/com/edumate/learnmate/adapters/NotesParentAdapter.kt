package com.edumate.learnmate.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.edumate.learnmate.R
import com.edumate.learnmate.models.NotesParentItem

class NotesParentAdapter(val context: Context, private val notesParentList: List<NotesParentItem>):
    RecyclerView.Adapter<NotesParentAdapter.NotesParentViewHolder>(){

    inner class NotesParentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val logoIv: ImageView = itemView.findViewById(R.id.notesParentLogoIv)
        val titleTv: TextView = itemView.findViewById(R.id.notesParentTitleTv)
        val childRecyclerView: RecyclerView = itemView.findViewById(R.id.langRecyclerView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesParentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.parent_item_notes, parent, false)
        return NotesParentViewHolder(view)
    }

    override fun getItemCount(): Int {
        return notesParentList.size
    }

    override fun onBindViewHolder(holder: NotesParentViewHolder, position: Int) {
        val parentItem = notesParentList[position]
        holder.logoIv.setImageResource(parentItem.logo)
        holder.titleTv.text = parentItem.title

        holder.childRecyclerView.setHasFixedSize(true)
        holder.childRecyclerView.layoutManager = LinearLayoutManager(holder.itemView.context)
        val adapter = NotesChildAdapter(context, parentItem.mList)
        holder.childRecyclerView.adapter = adapter
    }
}