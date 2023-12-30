package com.teamx.equiz.ui.fragments.news.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.teamx.equiz.MainApplication.Companion.context
import com.teamx.equiz.data.models.newsData.NewsDataX
import com.teamx.equiz.databinding.ItemNewsBinding
import com.teamx.equiz.ui.fragments.news.NewsAdapter


class RecentNewsAdapter(
    val arrayList: ArrayList<NewsDataX>,
    val onNewslistner: OnRecentNewsListener
) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): com.teamx.equiz.ui.fragments.news.NewsAdapter.NewsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemNewsBinding = ItemNewsBinding.inflate(inflater, parent, false)
        return com.teamx.equiz.ui.fragments.news.NewsAdapter.NewsViewHolder(itemNewsBinding)

    }

    override fun onBindViewHolder(holder: NewsAdapter.NewsViewHolder, position: Int) {

        val news: NewsDataX = arrayList[position]


//        Picasso.get().load(news.image).resize(500,500).into(holder.binding.img)

        Glide.with(context).load(news.image).into(holder.binding.img);


        holder.itemView.setOnClickListener {
            onNewslistner.newsClick(position)
        }


    }

    override fun getItemCount(): Int {
        return arrayList.size
    }


}

interface OnRecentNewsListener {
    fun newsClick(position: Int)
}