package com.teamx.equiz.ui.fragments.claimPrize.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.teamx.equiz.databinding.ItemClaimPrizeBinding
import com.teamx.equiz.ui.fragments.claimPrize.model.Raffle

class ClaimPrizeChancesAdapter(
    private val quizArrayList: ArrayList<Raffle>,
    private val claimPrizeInterface: ClaimPrizeInterface
) :
    RecyclerView.Adapter<ClaimPrizeChancesAdapterAdapterViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ClaimPrizeChancesAdapterAdapterViewHolder {
        return ClaimPrizeChancesAdapterAdapterViewHolder(
            ItemClaimPrizeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    }

    override fun onBindViewHolder(
        holder: ClaimPrizeChancesAdapterAdapterViewHolder,
        position: Int
    ) {

        val claimPrizeModel = quizArrayList[position]

        holder.bind.textView2754.text = claimPrizeModel.name

//



        holder.bind.textView2754.setOnClickListener {
//            holder.bind.textView2754.isChecked = true
            holder.bind.textView2754.isChecked = false
            claimPrizeInterface.claimPrize(position)
        }
        Log.d("winneriddsdsd", "isSelected: ${claimPrizeModel.isSelected}")
        holder.bind.textView2754.isChecked = claimPrizeModel.isSelected?:false

    }

    override fun getItemCount(): Int {
        return quizArrayList.size
    }
}

class ClaimPrizeChancesAdapterAdapterViewHolder(private var binding: ItemClaimPrizeBinding) :
    RecyclerView.ViewHolder(binding.root) {
    val bind = binding
}

interface ClaimPrizeInterface {
    fun claimPrize(position: Int)
}