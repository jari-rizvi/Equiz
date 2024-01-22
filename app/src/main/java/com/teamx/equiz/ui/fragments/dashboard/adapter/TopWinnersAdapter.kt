package com.teamx.equiz.ui.fragments.dashboard.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.google.android.play.integrity.internal.w
import com.squareup.picasso.Picasso
import com.teamx.equiz.R
import com.teamx.equiz.data.models.topWinnerData.Game
import com.teamx.equiz.data.models.topWinnerData.GameModel
import com.teamx.equiz.databinding.ItemWinnerBinding

class TopWinnersAdapter(
    private val addressArrayList: ArrayList<GameModel>, val quizesInterface: TopWinnerInterface
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

            holder.bind.name.text = winners.userId.name

            if (winners.userId.image.isNotEmpty()) {
                Picasso.get().load(winners.userId.image).placeholder(R.drawable.baseline_person).error(R.drawable.baseline_person).resize(500, 500).into(holder.binding.profilePicture)
//                Glide.with(holder.binding.profilePicture.context).load(winners.userId.image).thumbnail(0.05f)
//                    .transition(DrawableTransitionOptions.withCrossFade())
//                    .into(holder.binding.profilePicture)/*.onLoadFailed(
//                        ContextCompat.getDrawable(
//                            holder.binding.profilePicture.context,
//                            R.drawable.baseline_person
//                        )
//                    )*/
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

class TopWinnerViewHolder(var binding: ItemWinnerBinding) : RecyclerView.ViewHolder(binding.root) {
    val bind = binding
}