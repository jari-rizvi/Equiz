package com.teamx.equiz.ui.fragments.dashboard.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.teamx.equiz.databinding.ItemWinnerBinding

class TopWinnersAdapter(
    private val addressArrayList: ArrayList<String>, val quizesInterface: TopWinnerInterface
) : RecyclerView.Adapter<TopWinnerViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): TopWinnerViewHolder {
        return TopWinnerViewHolder(
            ItemWinnerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    }

    override fun onBindViewHolder(holder: TopWinnerViewHolder, position: Int) {

        val arrayData = addressArrayList[position]

        holder.itemView.setOnClickListener {
            quizesInterface.onWinnerClick(position)
        }

    }

    override fun getItemCount(): Int {
        
        return addressArrayList.size
    }
}

interface TopWinnerInterface {
    fun onWinnerClick(position: Int)
}

class TopWinnerViewHolder(private var binding: ItemWinnerBinding) :
    RecyclerView.ViewHolder(binding.root) {
    val bind = binding
}