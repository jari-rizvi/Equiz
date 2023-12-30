package com.teamx.equiz.ui.fragments.dashboard.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.play.integrity.internal.w
import com.squareup.picasso.Picasso
import com.teamx.equiz.data.models.topWinnerData.Game
import com.teamx.equiz.databinding.ItemWinnerBinding

class TopWinnersAdapter(
    private val addressArrayList: ArrayList<Game>, val quizesInterface: TopWinnerInterface
) : RecyclerView.Adapter<TopWinnerViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): TopWinnerViewHolder {
        return TopWinnerViewHolder(
            ItemWinnerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    }

    override fun onBindViewHolder(holder: TopWinnerViewHolder, position: Int) {

        val winners = addressArrayList[position]

        try {

            holder.bind.name.text = winners.name

            if (winners.image.isNotEmpty()) {
//                Picasso.get().load(winners.image).into(holder.binding.profilePicture)
                Glide.with(holder.binding.profilePicture.context).load(winners.image).into(holder.binding.profilePicture)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

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

class TopWinnerViewHolder(var binding: ItemWinnerBinding) :
    RecyclerView.ViewHolder(binding.root) {
    val bind = binding
}