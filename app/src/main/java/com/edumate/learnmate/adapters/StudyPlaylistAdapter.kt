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
import com.edumate.learnmate.models.StudyPlaylist

class StudyPlaylistAdapter(private val context: Context, private var studyPlaylist: List<StudyPlaylist>) :
    RecyclerView.Adapter<StudyPlaylistAdapter.StudyPlaylistViewHolder>(){

    inner class StudyPlaylistViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val studyPlaylistTitle: TextView = itemView.findViewById(R.id.studyPlaylistTxt)
    }

    fun setFilteredList(studyPlaylist: List<StudyPlaylist>) {
        this.studyPlaylist = studyPlaylist
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudyPlaylistViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.study_playlist_item, parent, false)
        return StudyPlaylistViewHolder(view)
    }

    override fun getItemCount(): Int {
        return studyPlaylist.size
    }

    override fun onBindViewHolder(holder: StudyPlaylistViewHolder, position: Int) {
        holder.studyPlaylistTitle.text = studyPlaylist[position].title

        holder.itemView.setOnClickListener {
            val uri = Uri.parse(studyPlaylist[position].url)
            val intent = Intent(Intent.ACTION_VIEW, uri)
            context.startActivity(intent)
        }
    }
}