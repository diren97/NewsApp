package com.diren.newsapp.news_detail

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.diren.newsapp.R
import com.diren.newsapp.getDisplayDate
import com.diren.newsapp.model.Articles
import com.diren.newsapp.nonEmptyStringOrNull
import kotlinx.android.synthetic.main.list_news.view.*

class NewsDetailAdapter(private var list: List<Articles>) :
    RecyclerView.Adapter<NewsDetailAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.list_news, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    fun updateData(newList: List<Articles>) {
        list = newList
        notifyDataSetChanged()
    }

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        init {
            view.setOnClickListener {
                list.getOrNull(adapterPosition)?.let { article ->
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(article.url))
                    it.context.startActivity(intent)
                }
            }
        }
        fun bind(position: Int) {
            list.getOrNull(position)?.let { article ->
                view.txt_title.text = article.title
                view.txt_author.text = article.author
                view.txt_description.text = article.description
                view.txt_publish_date.text =
                    getDisplayDate(article.publishedAt).nonEmptyStringOrNull() ?: ""
            Glide.with(view.image).load(article.urlToImage).into(view.image)
            }
        }
    }

}