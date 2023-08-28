package com.teamx.equiz.ui.fragments.orders.processing

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.teamx.equiz.databinding.ItemOrderBinding


class ProcessingAdapter(
    val arrayList: ArrayList<String>) : RecyclerView.Adapter<ProcessingAdapter.TopProductViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopProductViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemTopProductBinding = ItemOrderBinding.inflate(inflater, parent, false)
        return TopProductViewHolder(itemTopProductBinding)

    }


    override fun onBindViewHolder(holder: TopProductViewHolder, position: Int) {


    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    class TopProductViewHolder(itemTopProductBinding: ItemOrderBinding) :
        RecyclerView.ViewHolder(itemTopProductBinding.root) {
        val binding = itemTopProductBinding

    }
}