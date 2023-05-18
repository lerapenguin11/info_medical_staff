package com.example.infomedicalstaff.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.infomedicalstaff.R
import com.example.infomedicalstaff.business.model.NewsFeedModel

class NewsFeedAdapter(private val newsList : ArrayList<NewsFeedModel>) : RecyclerView.Adapter<NewsFeedAdapter.NewsFeedViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsFeedViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_news_feed, parent, false)

        return NewsFeedViewHolder(view)
    }

    override fun getItemCount(): Int = newsList.size

    override fun onBindViewHolder(holder: NewsFeedViewHolder, position: Int) {
        /*val news : NewsFeedModel = newsList[position]

        holder.description.text = news.description*/

        return holder.bind(newsList[position])

    }

    class NewsFeedViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val photo : ImageView = view.findViewById(R.id.new_photo)
        val description : TextView = view.findViewById(R.id.news_description)

        fun bind(news: NewsFeedModel) {
            Glide.with(itemView.context).load(news.photo).into(photo)
            description.text = news.description
        }
    }

}