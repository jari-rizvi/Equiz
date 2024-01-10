package com.teamx.equiz.ui.fragments.collectPrice.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.teamx.equiz.databinding.ItemRewardsBinding
import com.teamx.equiz.ui.fragments.chances.adapter.OnChanceListener
import com.teamx.equiz.ui.fragments.chances.data.ChancesTransaction
import com.teamx.equiz.ui.fragments.collectPrice.data.Raffle
import com.teamx.equiz.ui.fragments.collectPrice.data.WinnerData


class RewardsAdapter(
    val arrayList: ArrayList<Raffle>,
    private val onRewardClick: RewardsAdapter.OnRewardClick
) : RecyclerView.Adapter<RewardsAdapter.TopChanceViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopChanceViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemTopProductBinding = ItemRewardsBinding.inflate(inflater, parent, false)
        return TopChanceViewHolder(itemTopProductBinding)

    }

    override fun onBindViewHolder(holder: TopChanceViewHolder, position: Int) {


//        val product: Raffle = arrayList[position]

        try {
            val product: Raffle = arrayList[position]
            holder.binding.textView54.text = product.prize.title

            holder.binding.textView55.text = "+${product.prize.description}"

            Glide.with(holder.binding.container.context).load(product.prize.image.toString())
                .into(holder.binding.container)

        } catch (e: Exception) {
            e.printStackTrace()
        }


        holder.itemView.setOnClickListener {
            onRewardClick.onRewardClick(position)
        }


    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    class TopChanceViewHolder(itemProductBinding: ItemRewardsBinding) :
        RecyclerView.ViewHolder(itemProductBinding.root) {
        val binding = itemProductBinding

    }

    interface OnRewardClick {
        fun onRewardClick(position: Int)
    }
}