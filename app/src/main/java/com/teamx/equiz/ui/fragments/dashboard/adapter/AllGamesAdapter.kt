package com.teamx.equiz.ui.fragments.dashboard.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.teamx.equiz.databinding.GamesItemLayoutDashBinding

class AllGamesAdapter(
    private val addressArrayList: ArrayList<String>, val allGameInterface: AllGameInterface
) : RecyclerView.Adapter<AllGamesAdapterViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int
    ): AllGamesAdapterViewHolder {
        return AllGamesAdapterViewHolder(
            GamesItemLayoutDashBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    }

    override fun onBindViewHolder(holder: AllGamesAdapterViewHolder, position: Int) {

        val arrayData = addressArrayList[position]

        holder.itemView.setOnClickListener {
            allGameInterface.onClickGame(position)
        }

    }

    override fun getItemCount(): Int {
        
        return addressArrayList.size
    }
}
interface AllGameInterface{
    fun onClickGame(position: Int)
}
class AllGamesAdapterViewHolder(private var binding: GamesItemLayoutDashBinding) :
    RecyclerView.ViewHolder(binding.root) {
    val bind = binding
}