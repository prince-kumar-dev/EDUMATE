package com.edumate.fellowmate.adapters

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.edumate.fellowmate.R
import com.edumate.fellowmate.models.CompleteChildCoursePackage

class CompleteCoursePackageChildAdapter (
    val context: Context,
    private val completeCoursePackageChildList: List<CompleteChildCoursePackage>
) :
    RecyclerView.Adapter<CompleteCoursePackageChildAdapter.CompleteCoursePackageChildViewHolder>() {

    inner class CompleteCoursePackageChildViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val courseTitle: TextView = itemView.findViewById(R.id.courseTitle)
        val courseImage: ImageView = itemView.findViewById(R.id.courseImage)
        val courseCardView: View = itemView.findViewById(R.id.coursesCardContainer)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CompleteCoursePackageChildViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.complete_package_child_item, parent, false)
        return CompleteCoursePackageChildViewHolder(view)
    }

    override fun getItemCount(): Int {
        return completeCoursePackageChildList.size
    }

    override fun onBindViewHolder(holder: CompleteCoursePackageChildViewHolder, position: Int) {
        val data = completeCoursePackageChildList[position]

        holder.courseTitle.text = data.title

        Glide.with(context)
            .load(data.image)
            .into(holder.courseImage)

        holder.courseCardView.setOnClickListener {
            val url = data.url
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            context.startActivity(intent)
        }
    }

}