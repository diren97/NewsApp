package com.diren.newsapp.news

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.diren.newsapp.R
import com.diren.newsapp.news_detail.NewsDetailActivity
import com.diren.newsapp.model.Source
import kotlinx.android.synthetic.main.list_sources.view.*

class NewsSourceAdapter(private var list: List<Source>) :
        RecyclerView.Adapter<NewsSourceAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.list_sources, parent, false)
            )
        }

        override fun getItemCount(): Int {
            return list.size
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(position)
        }

        fun updateData(newList:List<Source>) {
            list = newList
            notifyDataSetChanged()
        }

        inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
            init {
                view.setOnClickListener {
                    list.getOrNull(adapterPosition)?.let { s ->
                        val intent = Intent(view.context, NewsDetailActivity::class.java)
                        intent.putExtra("source_ıd", s.id)
                        Log.d("SOURCEID",s.id)
                        it.context.startActivity(intent)
                    }
                }
            }

            fun bind(position: Int) {
                list.getOrNull(position)?.let { s ->
                    view.article_title.text = s.name
                    view.article_description.text = s.description
                }
            }
        }

}