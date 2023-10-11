package com.teamx.equiz.ui.fragments.news

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.teamx.equiz.data.models.getwalletData.Transaction
import com.teamx.equiz.data.models.newsdaya.New
import com.teamx.equiz.data.models.wishlistdata.Product
import com.teamx.equiz.databinding.ItemNewsBinding
import com.teamx.equiz.databinding.ItemWishlistBinding


class NewsAdapter(
    val arrayList: ArrayList<New>
) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemNewsBinding = ItemNewsBinding.inflate(inflater, parent, false)
        return NewsViewHolder(itemNewsBinding)

    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {

        val news: New = arrayList[position]

        try {

            Picasso.get().load(news.image).into(holder.binding.img)
        } catch (e: Exception) {

        }


        holder.itemView.setOnClickListener {}


    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    class NewsViewHolder(itemNewsBinding: ItemNewsBinding) :
        RecyclerView.ViewHolder(itemNewsBinding.root) {
        val binding = itemNewsBinding

    }
}