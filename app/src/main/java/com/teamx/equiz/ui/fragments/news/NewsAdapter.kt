package com.teamx.equiz.ui.fragments.news

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.ui.input.key.Key.Companion.G
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.squareup.picasso.Picasso
import com.teamx.equiz.MainApplication.Companion.context
import com.teamx.equiz.R
import com.teamx.equiz.data.models.newsData.NewsDataX
import com.teamx.equiz.databinding.ItemNewsBinding
import com.teamx.equiz.databinding.ItemWishlistBinding


class NewsAdapter(
    val arrayList: ArrayList<NewsDataX>,
    val onNewslistner: onNewslistner
) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemNewsBinding = ItemNewsBinding.inflate(inflater, parent, false)
        return NewsViewHolder(itemNewsBinding)

    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {

        val news: NewsDataX = arrayList[position]



//        Picasso.get().load(news.image).resize(500,500).into(holder.binding.img)

        Glide.with(context).load(news.image).into(holder.binding.img);


        holder.itemView.setOnClickListener {
            onNewslistner.newsclick(position)
        }


    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    class NewsViewHolder(itemNewsBinding: ItemNewsBinding) :
        RecyclerView.ViewHolder(itemNewsBinding.root) {
        val binding = itemNewsBinding

    }
}

interface onNewslistner{
    fun newsclick(position: Int)
}