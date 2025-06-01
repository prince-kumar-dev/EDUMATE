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
import com.edumate.fellowmate.models.Course

class CourseAdapter(private var context: Context, private var courses: List<Course>) :
    RecyclerView.Adapter<CourseAdapter.CourseViewHolder>() {

    inner class CourseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val courseTitleTextView: TextView = itemView.findViewById(R.id.courseTitle)
        val courseLevelTextView: TextView = itemView.findViewById(R.id.courseLevel)
        val courseDurationTextView: TextView = itemView.findViewById(R.id.courseDuration)
        val courseImageView: ImageView = itemView.findViewById(R.id.courseImage)
        val courseCardView: View = itemView.findViewById(R.id.oneCourseCardView)
        val certificateCheck: ImageView = itemView.findViewById(R.id.certificateCheckImageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.one_course_item, parent, false)
        return CourseViewHolder(view)
    }

    override fun getItemCount(): Int {
        return courses.size
    }

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        val data = courses[position]
        holder.courseTitleTextView.text = data.title
        holder.courseLevelTextView.text = data.level
        holder.courseDurationTextView.text = data.duration

        if(data.certificate == "true") {
            holder.certificateCheck.setImageResource(R.drawable.ic_correct)
        } else {
            holder.certificateCheck.setImageResource(R.drawable.ic_wrong)
        }

        Glide.with(context)
            .load(data.image)
            .into(holder.courseImageView)

        holder.courseCardView.setOnClickListener {
            val url = data.url
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            context.startActivity(intent)
        }
    }
}