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
import com.edumate.fellowmate.models.RecommendedCourse

class RecommendedCourseAdapter(
    private var context: Context,
    private var recommendedCourseList: List<RecommendedCourse>
) :
    RecyclerView.Adapter<RecommendedCourseAdapter.RecommendedCourseViewHolder>() {

    inner class RecommendedCourseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.recommendedCourseTitle)
        val iconImageView: ImageView = itemView.findViewById(R.id.recommendedCourseImage)
        val cardView: androidx.cardview.widget.CardView = itemView.findViewById(R.id.recommendedCourseCardView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendedCourseViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recommended_course_item, parent, false)
        return RecommendedCourseViewHolder(view)
    }

    override fun getItemCount(): Int {
        return recommendedCourseList.size
    }

    override fun onBindViewHolder(holder: RecommendedCourseViewHolder, position: Int) {
        val data = recommendedCourseList[position]
        holder.titleTextView.text = data.title

        Glide.with(context)
            .load(data.iconUrl)
            .into(holder.iconImageView)

        holder.cardView.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(data.url))
            context.startActivity(intent)
        }

    }
}