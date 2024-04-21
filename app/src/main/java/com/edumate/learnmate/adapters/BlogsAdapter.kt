package com.edumate.learnmate.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.edumate.learnmate.R
import com.edumate.learnmate.activities.BlogView
import com.edumate.learnmate.models.Blogs
import com.edumate.learnmate.models.StudyPlaylist

class BlogsAdapter(private var blogs: List<Blogs>) :
    RecyclerView.Adapter<BlogsAdapter.BlogViewHolder>() {
    inner class BlogViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.blogName)

        fun bind(item: String, url: String) {
            titleTextView.text = item

            itemView.setOnClickListener {
                val context = itemView.context
                val intent = Intent(context, BlogView::class.java)
                intent.putExtra("URL", url)
                context.startActivity(intent)
            }
        }
    }

    fun setFilteredList(blogs: List<Blogs>) {
        this.blogs = blogs
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BlogViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.blog_item, parent, false)
        return BlogViewHolder(view)
    }

    override fun getItemCount(): Int {
        return blogs.size
    }

    override fun onBindViewHolder(holder: BlogViewHolder, position: Int) {
        val item = blogs[position].title
        val url = blogs[position].url
        holder.bind(item, url)
    }
}