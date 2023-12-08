package com.teamx.equiz.ui.fragments.games

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.teamx.equiz.databinding.GamesItemLayoutGmBinding
import com.teamx.equiz.ui.fragments.dashboard.GamesModel

class AllGamesAdapter(
    private val addressArrayList: ArrayList<GamesModel>, val allGameInterface: AllGameInterface
) : RecyclerView.Adapter<AllGamesAdapterViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): AllGamesAdapterViewHolder {
        return AllGamesAdapterViewHolder(
            GamesItemLayoutGmBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    }

    override fun onBindViewHolder(holder: AllGamesAdapterViewHolder, position: Int) {

        val arrayData = addressArrayList[position]

        holder.itemView.setOnClickListener {
            allGameInterface.onClickGame(position)
        }

        holder.bind.profilePicture.setImageResource(arrayData.image)

        holder.bind.titleGame.text = arrayData.name


    }

    override fun getItemCount(): Int {

        return addressArrayList.size
    }
}

interface AllGameInterface {
    fun onClickGame(position: Int)
}

class AllGamesAdapterViewHolder(private var binding: GamesItemLayoutGmBinding) :
    RecyclerView.ViewHolder(binding.root) {
    val bind = binding
}